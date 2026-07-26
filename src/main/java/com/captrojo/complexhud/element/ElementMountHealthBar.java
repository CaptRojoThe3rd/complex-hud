package com.captrojo.complexhud.element;

import org.lwjgl.opengl.GL11;

import com.captrojo.complexhud.api.IConfigEntry;
import com.captrojo.complexhud.api.PositionInfoXY2;
import com.captrojo.complexhud.api.PositionOperation;
import com.captrojo.complexhud.api.PositionOrigin;
import com.captrojo.complexhud.config.ConfigHeading;
import com.captrojo.complexhud.config.ConfigOption;
import com.captrojo.complexhud.config.ConfigOption.Type;

import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraftforge.client.GuiIngameForge;

public class ElementMountHealthBar extends ElementHealthBarBase
{
	static final int U_BG = 0;
	static final int U_FG = 27;
	
	static final int U_BG_HIGH_OFFS = 9;
	static final int U_BG_LOW_OFFS = 18;
	
	static final int U_FG_HIGH_OFFS = 36;
	static final int U_FG_OVERLOAD_OFFS = 72;
	
	static final int[] U_QUARTERS = {27, 18, 9, 0};
	
	static final int V_ALL = 0;
	
	ConfigOption cfg_show_mount_name;
	
	boolean show_mount_name;
	
	int width;
	int height;
	
	EntityLivingBase mount;
	int health;
	int health_max;
	
	int health_rows;
	int row_height;
	
	public ElementMountHealthBar()
	{
		this.cfg_right_to_left = new ConfigOption(Type.BOOLEAN, "right_to_left", true);
		this.cfg_show_mount_name = new ConfigOption(Type.BOOLEAN, "show_mount_name", false);
	}
	
	@Override
	public String getUnlocalizedName()
	{
		return "hud.mount_health_bar";
	}

	@Override
	public int getDefaultPriority()
	{
		return -115;
	}

	@Override
	public PositionOrigin getDefaultPosOrigin()
	{
		return PositionOrigin.HOTBAR_TOP_RIGHT;
	}

	@Override
	public PositionOperation getDefaultPosOperation()
	{
		return PositionOperation.UP;
	}

	@Override
	public IConfigEntry[] getConfigOptions()
	{
		return new IConfigEntry[] {
			new ConfigHeading(null),
			new ConfigHeading(OVRD_CFG_HEADING),
			this.cfg_enabled,
			this.cfg_override_vanilla,
			
			new ConfigHeading(null),
			new ConfigHeading("options.complexhud.mount_health_bar_std_optns"),
			this.cfg_quarter_hearts,
			this.cfg_top_to_bottom,
			this.cfg_right_to_left,
			this.cfg_hearts_per_row,
			this.cfg_low_health_point,
			this.cfg_compress_rows,
			this.cfg_min_row_spacing,
			this.cfg_row_spacing_addend,
			this.cfg_heart_spacing,
			this.cfg_show_mount_name,
			
			new ConfigHeading(null),
			new ConfigHeading("options.complexhud.mount_health_bar_overload_optns"),
			this.cfg_overloaded_hearts,
			this.cfg_hearts_per_load,
			this.cfg_overload_colors
		};
	}
	
	@Override
	public void onConfigUpdated()
	{
		super.onConfigUpdated();
		
		this.show_mount_name = this.cfg_show_mount_name.getBool();
	}

	@Override
	public int getWidth()
	{
		return this.width;
	}

	@Override
	public int getHeight()
	{
		return this.height;
	}

	@Override
	public boolean isToBeRendered()
	{
		return this.enabled && this.mc.playerController.shouldDrawHUD() && GuiIngameForge.renderHealthMount;
	}

	@Override
	public void doPreRenderWork()
	{
		this.mc.mcProfiler.startSection("mount_health");
		
		Entity e0 = this.mc.thePlayer.ridingEntity;
		if (!(e0 instanceof EntityLivingBase)) {
			this.mc.mcProfiler.endSection();
			return;
		}
		this.mount = (EntityLivingBase) e0;
		
		int icon_width = 9 + this.heart_spacing;
		this.width = icon_width * this.hearts_per_row - this.heart_spacing;
		
		this.health = MathHelper.ceiling_float_int(mount.getHealth() * 2.0f);
		this.health_max = MathHelper.ceiling_float_int(mount.getMaxHealth() * 2.0f);
		
		int heart_count = this.health_max / 4;
		if (this.overloaded_hearts) {
			heart_count = Math.min(heart_count, this.hearts_per_load);
		}
		
		this.health_rows = MathHelper.ceiling_float_int((float) heart_count / (float) this.hearts_per_row);
		if (this.compress_rows) {
			this.row_height = Math.max(10 - (this.health_rows - 2), this.min_row_spacing);
		} else {
			this.row_height = 10;
		}
		this.row_height += this.row_spacing_addend;
		
		this.height = this.health_rows * this.row_height;
		if (this.row_height != 10) {
			this.height += 10 - this.row_height;
		}
		if (this.show_mount_name) {
			this.height += this.mc.fontRenderer.FONT_HEIGHT;
		}
		
		this.mc.mcProfiler.endSection();
	}

	@Override
	public void render(ScaledResolution sr, int mouse_x, int mouse_y, float partial_ticks, PositionInfoXY2 pos)
	{
		this.mc.mcProfiler.startSection("mount_health");
		GL11.glEnable(GL11.GL_BLEND);
		
		int y0 = pos.top_y;
		
		if (this.show_mount_name) {
			String name = this.mount.getCommandSenderName();
			int x = this.right_to_left ? (pos.right_x - this.mc.fontRenderer.getStringWidth(name)) : pos.left_x;
			this.mc.fontRenderer.drawStringWithShadow(name, x, y0, 0xffffff);
			y0 += this.mc.fontRenderer.FONT_HEIGHT;
		}

		/* Do this after any calls to fontRenderer */
		this.bindModIcons();
		
		boolean highlight = false;
		if (this.mount.hurtResistantTime >= 10) {
			highlight = ((this.mount.hurtResistantTime / 3) % 2) == 1;
		}
		
		int u_bg = U_BG;
		int u_fg = U_FG;
		
		if (highlight) {
			u_bg += U_BG_HIGH_OFFS;
			u_fg += U_FG_HIGH_OFFS;
		} else if ((this.update_counter & 0x4) != 0 && this.health <= this.low_health_point) {
			u_bg += U_BG_LOW_OFFS;
		}
		
		int regular_heart_count = MathHelper.ceiling_float_int((float) this.health_max / 4.0f);
		
		int health_to_draw = this.health;

		int overload = 0;
		int row = 0;
		int column = 0;
		
		for (int heart_idx = 0; heart_idx < regular_heart_count; heart_idx++) {
			int y_bump = 0;
			if (this.health <= this.low_health_point) {
				y_bump += this.rand.nextInt(2);
			}
			
			int p = health_to_draw;
			if (!this.quarter_hearts && (p == 1 || p == 3)) {
				p++;
			}
			health_to_draw -= 4;
			
			int draw_column = this.right_to_left ? ((this.hearts_per_row - 1) - column) : column;
			int draw_row = this.top_to_bottom ? row : ((this.health_rows - 1) - row);
			
			int x = draw_column * (9 + this.heart_spacing) + pos.left_x;
			int y = draw_row * this.row_height + y_bump + y0 + 1;
			if (!this.top_to_bottom) {
				y -= this.row_spacing_addend;
			}
			
			if (overload == 0) {
				this.drawTexturedModalRect(x, y, u_bg, V_ALL, 9, 9);
			}
			
			if (p > 0) {
				if (this.overloaded_hearts && overload > 0) {
					u_fg += U_FG_OVERLOAD_OFFS;
					float[] color;
					if (overload < this.overload_colors.length) {
						color = this.overload_colors[overload - 1];
					} else {
						color = COLOR_WHITE;
					}
					GL11.glColor4f(
						color[0],
						color[1],
						color[2],
						1.0f
					);
				}
				if (!this.right_to_left) {
					this.drawTexturedModalRectFlippedHorz(x, y, u_fg + U_QUARTERS[Math.min(p, 4) - 1], V_ALL, 9, 9);
				} else {
					this.drawTexturedModalRect(x, y, u_fg + U_QUARTERS[Math.min(p, 4) - 1], V_ALL, 9, 9);
				}
				if (this.overloaded_hearts && overload > 0) {
					GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
				}
			}
			
			column++;
			if (column == this.hearts_per_row) {
				column = 0;
				row++;
			}
			if (this.overloaded_hearts && (row * this.hearts_per_row + column) == this.hearts_per_load) {
				row = 0;
				column = 0;
				overload++;
			}
		}

		GL11.glDisable(GL11.GL_BLEND);
		this.mc.mcProfiler.endSection();
	}
}
