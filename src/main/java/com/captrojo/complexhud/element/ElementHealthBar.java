package com.captrojo.complexhud.element;

import org.lwjgl.opengl.GL11;

import com.captrojo.complexhud.api.PositionInfoXY2;
import com.captrojo.complexhud.api.PositionOperation;
import com.captrojo.complexhud.api.PositionOrigin;
import com.captrojo.complexhud.config.ConfigOption;
import com.captrojo.complexhud.config.ConfigOption.Type;

import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.potion.Potion;
import net.minecraft.util.MathHelper;
import net.minecraftforge.client.GuiIngameForge;

public class ElementHealthBar extends OverriddenVanillaElement
{	
	static final int BG_U0 = 0;
	static final int FG_U0 = 27;
	
	static final int U_FG_NORM = 27;
	static final int U_FG_HIGH = 63;
	static final int U_FG_HARD_OFFS = 72;
	
	static final int U_BG_NORM = 0;
	static final int U_BG_HIGH = 9;
	static final int U_BG_LOW = 18;
	
	static final int V_NORM = 0;
	static final int V_POISON = 9;
	static final int V_WITHER = 18;
	static final int V_ABSORB = 27;
	
	static final int[] U_QUARTERS = {246, 27, 18, 9, 0};
	
	ConfigOption cfg_top_to_bottom;
	ConfigOption cfg_right_to_left;
	ConfigOption cfg_hearts_per_row;
	ConfigOption cfg_low_health_point;
	ConfigOption cfg_compress_rows;
	ConfigOption cfg_row_spacing;
	ConfigOption cfg_heart_spacing;
	
	boolean top_to_bottom;
	boolean right_to_left;
	int hearts_per_row;
	int low_health_point;
	boolean compress_rows;
	int row_spacing;
	int heart_spacing;
	
	int width;
	int height;
	
	int health;
	int health_last;
	int health_max;
	int absorb;

	int health_rows;
	int row_height;
	
	public ElementHealthBar()
	{
		this.cfg_top_to_bottom = new ConfigOption(Type.BOOLEAN, "top_to_bottom", false);
		this.cfg_right_to_left = new ConfigOption(Type.BOOLEAN, "right_to_left", false);
		this.cfg_hearts_per_row = new ConfigOption(Type.INT, "hearts_per_row", 10);
		this.cfg_low_health_point = new ConfigOption(Type.DOUBLE, "low_health_point", 2.0);
		this.cfg_compress_rows = new ConfigOption(Type.BOOLEAN, "compress_rows", true);
		this.cfg_row_spacing = new ConfigOption(Type.INT, "row_spacing", 0);
		this.cfg_heart_spacing = new ConfigOption(Type.INT, "heart_spacing", -1);
	}

	@Override
	public String getUnlocalizedName()
	{
		return "hud.health_bar";
	}

	@Override
	public int getDefaultPriority()
	{
		return -1000;
	}

	@Override
	public boolean getDefaultFixedSetting()
	{
		return false;
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
	public int getDefaultXOffs()
	{
		return 0;
	}

	@Override
	public int getDefaultYOffs()
	{
		return 0;
	}

	@Override
	public int getDefaultBufferTopSize()
	{
		return 0;
	}

	@Override
	public int getDefaultBufferBottomSize()
	{
		return 0;
	}

	@Override
	public int getDefaultBufferLeftSize()
	{
		return 0;
	}

	@Override
	public int getDefaultBufferRightSize()
	{
		return 0;
	}

	@Override
	public boolean getDefaultRenderInF3Setting()
	{
		return true;
	}

	@Override
	public ConfigOption[] getConfigOptions()
	{
		return new ConfigOption[] {
			this.cfg_top_to_bottom,
			this.cfg_right_to_left,
			this.cfg_hearts_per_row,
			this.cfg_low_health_point,
			this.cfg_compress_rows,
			this.cfg_row_spacing,
			this.cfg_heart_spacing
		};
	}
	
	@Override
	public void onConfigUpdated()
	{
		this.top_to_bottom = this.cfg_top_to_bottom.getBool();
		this.right_to_left = this.cfg_right_to_left.getBool();
		this.hearts_per_row = this.cfg_hearts_per_row.getInt();
		this.low_health_point = (int) (this.cfg_low_health_point.getDouble() * 4.0);
		this.compress_rows = this.cfg_compress_rows.getBool();
		this.row_spacing = this.cfg_row_spacing.getInt();
		this.heart_spacing = this.cfg_heart_spacing.getInt();
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
		return this.mc.playerController.shouldDrawHUD() && GuiIngameForge.renderHealth;
	}
	
	@Override
	public void updateTick()
	{
		super.updateTick();
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

		this.health_rows = MathHelper.ceiling_float_int((this.health_max + this.absorb) / 4.0f / (float) this.hearts_per_row);
		if (this.compress_rows) {
			this.row_height = Math.max(10 - (this.health_rows - 2), 3);
		} else {
			this.row_height = 10;
		}
		this.row_height += this.row_spacing;
		
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

		boolean highlight = this.mc.thePlayer.hurtResistantTime / 3 % 2 == 1;
		if (this.mc.thePlayer.hurtResistantTime < 10) {
			highlight = false;
		}
		
		int u_bg;
		int u1;
		int v1;
		
		if (highlight) {
			u_bg = U_BG_HIGH;
			u1 = U_FG_HIGH;
		} else if ((this.update_counter & 0x4) != 0 && this.health <= this.low_health_point) {
			u_bg = U_BG_LOW;
			u1 = U_FG_NORM;
		} else {
			u_bg = U_BG_NORM;
			u1 = U_FG_NORM;
		}
		
		if (this.mc.theWorld.getWorldInfo().isHardcoreModeEnabled()) {
			u1 += U_FG_HARD_OFFS;
		}

		if (this.mc.thePlayer.isPotionActive(Potion.poison)) {
			v1 = V_POISON;
		} else if (mc.thePlayer.isPotionActive(Potion.wither)) {
			v1 = V_WITHER;
		} else {
			v1 = V_NORM;
		}
		
		int regular_heart_count = MathHelper.ceiling_float_int((float) this.health_max / 4.0f);
		int absorb_heart_count = MathHelper.ceiling_float_int((float) this.absorb / 4.0f);
		int total_heart_count = regular_heart_count + absorb_heart_count;
		
		int regen_bump_idx = -1;
		if (this.mc.thePlayer.isPotionActive(Potion.regeneration)) {
			regen_bump_idx = this.update_counter % (total_heart_count + 15);
		}
		
		int health_to_draw = this.health;
		int absorb_to_draw = (int) this.absorb;
		
		for (int heart_idx = 0; heart_idx < total_heart_count; heart_idx++) {
			int column = heart_idx % this.hearts_per_row;
			if (this.right_to_left) {
				column = (this.hearts_per_row - 1) - column;
			}
			int row = heart_idx / this.hearts_per_row;
			if (!this.top_to_bottom) {
				row = (this.health_rows - 1) - row;
			}
			
			int y_bump = 0;
			if (this.health <= this.low_health_point) {
				y_bump += this.rand.nextInt(2);
			}
			if (heart_idx == regen_bump_idx) {
				y_bump -= 2;
			}
			
			int v;
			int p;
			if (heart_idx >= regular_heart_count) {
				v = V_ABSORB;
				p = absorb_to_draw;
				absorb_to_draw -= 4;
			} else {
				v = v1;
				p = health_to_draw;
				health_to_draw -= 4;
			}
			int u = u1;
			if (p > 0) {
				u += U_QUARTERS[Math.min(p, 4)];
			} else {
				u = 246;
			}
			
			int x = column * (9 + this.heart_spacing) + pos.left_x;
			int y = row * this.row_height + y_bump + pos.top_y + 1;
			if (!this.top_to_bottom) {
				y -= this.row_spacing;
			}
			
			this.drawTexturedModalRect(x, y, u_bg, v, 9, 9);
			this.drawTexturedModalRect(x, y, u, v, 9, 9);
			
		}
		GL11.glDisable(GL11.GL_BLEND);
		this.mc.mcProfiler.endSection();
	}
}
