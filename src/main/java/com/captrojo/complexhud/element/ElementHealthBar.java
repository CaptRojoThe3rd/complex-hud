package com.captrojo.complexhud.element;

import org.lwjgl.opengl.GL11;

import com.captrojo.complexhud.api.IConfigEntry;
import com.captrojo.complexhud.api.PositionInfoXY2;
import com.captrojo.complexhud.api.PositionOperation;
import com.captrojo.complexhud.api.PositionOrigin;
import com.captrojo.complexhud.api.config.ConfigHeading;

import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.potion.Potion;
import net.minecraft.util.MathHelper;
import net.minecraftforge.client.GuiIngameForge;

public class ElementHealthBar extends ElementHealthBarBase
{
	static final int U_BG = 0;
	static final int U_FG = 27;

	static final int U_BG_HIGH_OFFS = 9;
	static final int U_BG_LOW_OFFS = 18;

	static final int U_FG_HIGH_OFFS = 36;
	static final int U_FG_HARD_OFFS = 72;

	static final int[] U_QUARTERS = {27, 18, 9, 0};

	static final int V_OVERLOAD = 9;
	static final int V_NORM = 18;
	static final int V_DROWNING = 27;
	static final int V_FROZEN = 36;
	static final int V_POISON = 45;
	static final int V_WITHER = 54;
	static final int V_ABSORB = 63;

	int width;
	int height;

	int health;
	int health_last;
	int health_max;
	int absorb;

	int health_rows;
	int row_height;

	@Override
	public String getUnlocalizedName()
	{
		return "hud.health_bar";
	}

	@Override
	public int getDefaultPriority()
	{
		return -100;
	}

	@Override
	public PositionOrigin getDefaultPosOrigin()
	{
		return PositionOrigin.HOTBAR_TOP_LEFT;
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
			new ConfigHeading("options.complexhud.health_bar_std_optns"),
			this.cfg_quarter_hearts,
			this.cfg_top_to_bottom,
			this.cfg_right_to_left,
			this.cfg_hearts_per_row,
			this.cfg_low_health_point,
			this.cfg_compress_rows,
			this.cfg_min_row_spacing,
			this.cfg_row_spacing_addend,
			this.cfg_heart_spacing,

			new ConfigHeading(null),
			new ConfigHeading("options.complexhud.health_bar_overload_optns"),
			this.cfg_overloaded_hearts,
			this.cfg_hearts_per_load,
			this.cfg_overload_colors
		};
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
		return this.enabled && this.mc.playerController.shouldDrawHUD() && GuiIngameForge.renderHealth;
	}

	@Override
	public void doPreRenderWork()
	{
		this.mc.mcProfiler.startSection("health");

		int icon_width = 9 + this.heart_spacing;
		this.width = icon_width * this.hearts_per_row - this.heart_spacing;

		IAttributeInstance attr_max_health = this.mc.thePlayer.getEntityAttribute(SharedMonsterAttributes.maxHealth);
		this.health = MathHelper.ceiling_float_int(this.mc.thePlayer.getHealth() * 2);
		this.health_last = MathHelper.ceiling_float_int(this.mc.thePlayer.prevHealth * 2);
		this.health_max = MathHelper.ceiling_double_int(attr_max_health.getAttributeValue() * 2);
		this.absorb = MathHelper.ceiling_float_int(this.mc.thePlayer.getAbsorptionAmount() * 2);

		int health_count = (this.health_max + this.absorb) / 4;
		if (this.overloaded_hearts) {
			health_count = Math.min(health_count, this.hearts_per_load + this.absorb);
		}

		this.health_rows = MathHelper.ceiling_float_int((float) health_count / (float) this.hearts_per_row);
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

		this.mc.mcProfiler.endSection();
	}

	@Override
	public void render(ScaledResolution sr, int mouse_x, int mouse_y, float partial_ticks, PositionInfoXY2 pos)
	{
		this.mc.mcProfiler.startSection("health");
		this.bindModIcons();
		GL11.glEnable(GL11.GL_BLEND);
		this.rand.setSeed((long) (this.update_counter * 312871));

		boolean highlight = false;
		if (this.mc.thePlayer.hurtResistantTime >= 10) {
			highlight = ((this.mc.thePlayer.hurtResistantTime / 3) % 2) == 1;
		}

		int u_bg = U_BG;
		int u_fg = U_FG;
		int v1;

		if (highlight) {
			u_bg += U_BG_HIGH_OFFS;
			u_fg += U_FG_HIGH_OFFS;
		} else if ((this.update_counter & 0x4) != 0 && this.health <= this.low_health_point) {
			u_bg += U_BG_LOW_OFFS;
		}

		if (this.mc.theWorld.getWorldInfo().isHardcoreModeEnabled()) {
			u_fg += U_FG_HARD_OFFS;
		}

		if (this.mc.thePlayer.isPotionActive(Potion.wither)) {
			v1 = V_WITHER;
		} else if (this.mc.thePlayer.getAir() <= 0) {
			v1 = V_DROWNING;
		} else if (mc.thePlayer.isPotionActive(Potion.poison)) {
			v1 = V_POISON;
		} else {
			v1 = V_NORM;
		}

		int regular_heart_count = MathHelper.ceiling_float_int((float) this.health_max / 4.0F);
		int absorb_heart_count = MathHelper.ceiling_float_int((float) this.absorb / 4.0F);
		int total_heart_count = regular_heart_count + absorb_heart_count;

		int regen_bump_idx = -1;
		if (this.mc.thePlayer.isPotionActive(Potion.regeneration)) {
			regen_bump_idx = this.update_counter % (total_heart_count + 15);
		}

		int health_to_draw = this.health;
		int absorb_to_draw = this.absorb;

		int overload = 0;
		int row = 0;
		int column = 0;

		for (int heart_idx = 0; heart_idx < regular_heart_count; heart_idx++) {
			int y_bump = 0;
			if (this.health <= this.low_health_point) {
				y_bump += this.rand.nextInt(2);
			}
			if (heart_idx == regen_bump_idx) {
				y_bump -= 2;
			}

			int v = v1;
			int p = health_to_draw;
			if (!this.quarter_hearts && (p == 1 || p == 3)) {
				p++;
			}
			health_to_draw -= 4;

			int draw_column = this.right_to_left ? ((this.hearts_per_row - 1) - column) : column;
			int draw_row = this.top_to_bottom ? row : ((this.health_rows - 1) - row);

			int x = draw_column * (9 + this.heart_spacing) + pos.left_x;
			int y = draw_row * this.row_height + y_bump + pos.top_y + 1;
			if (!this.top_to_bottom) {
				y -= this.row_spacing_addend;
			}

			if (overload == 0) {
				this.drawTexturedModalRect(x, y, u_bg, v, 9, 9);
			}
			if (p > 0) {
				if (this.overloaded_hearts && overload > 0 && v == V_NORM) {
					v = V_OVERLOAD;
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
				if (this.right_to_left) {
					this.drawTexturedModalRectFlippedHorz(x, y, u_fg + U_QUARTERS[Math.min(p, 4) - 1], v, 9, 9);
				} else {
					this.drawTexturedModalRect(x, y, u_fg + U_QUARTERS[Math.min(p, 4) - 1], v, 9, 9);
				}
				if (this.overloaded_hearts && overload > 0) {
					GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
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

		int heart_offs = this.overloaded_hearts ? Math.min(regular_heart_count, this.hearts_per_load) : regular_heart_count;
		row = heart_offs / this.hearts_per_row;
		column = heart_offs % this.hearts_per_row;

		for (int heart_idx = 0; heart_idx < absorb_heart_count; heart_idx++) {
			int actual_heart_idx = heart_idx + regular_heart_count;

			int y_bump = 0;
			if (this.health <= this.low_health_point) {
				y_bump += this.rand.nextInt(2);
			}
			if (actual_heart_idx == regen_bump_idx) {
				y_bump -= 2;
			}

			int v = V_ABSORB;
			int p = absorb_to_draw;
			if (!this.quarter_hearts && (p == 1 || p == 3)) {
				p++;
			}
			absorb_to_draw -= 4;

			int draw_column = this.right_to_left ? ((this.hearts_per_row - 1) - column) : column;
			int draw_row = this.top_to_bottom ? row : ((this.health_rows - 1) - row);

			int x = draw_column * (9 + this.heart_spacing) + pos.left_x;
			int y = draw_row * this.row_height + y_bump + pos.top_y + 1;
			if (!this.top_to_bottom) {
				y -= this.row_spacing_addend;
			}

			this.drawTexturedModalRect(x, y, u_bg, v, 9, 9);
			if (this.right_to_left) {
				this.drawTexturedModalRectFlippedHorz(x, y, u_fg + U_QUARTERS[Math.min(p, 4) - 1], v, 9, 9);
			} else {
				this.drawTexturedModalRect(x, y, u_fg + U_QUARTERS[Math.min(p, 4) - 1], v, 9, 9);
			}

			column++;
			if (column == this.hearts_per_row) {
				column = 0;
				row++;
			}
		}

		GL11.glDisable(GL11.GL_BLEND);
		this.mc.mcProfiler.endSection();
	}
}
