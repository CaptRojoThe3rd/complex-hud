package com.captrojo.complexhud.element;

import org.lwjgl.opengl.GL11;

import com.captrojo.complexhud.api.IConfigEntry;
import com.captrojo.complexhud.api.PositionInfoXY2;
import com.captrojo.complexhud.api.PositionOperation;
import com.captrojo.complexhud.api.PositionOrigin;
import com.captrojo.complexhud.config.ConfigHeading;
import com.captrojo.complexhud.config.ConfigOption;
import com.captrojo.complexhud.config.ConfigOption.Type;
import com.captrojo.complexhud.main.ComplexHUD;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.potion.Potion;
import net.minecraft.util.FoodStats;
import net.minecraft.util.MathHelper;
import net.minecraftforge.client.GuiIngameForge;
import squeek.applecore.api.AppleCoreAPI;

public class ElementHungerBar extends OverriddenVanillaElement
{
	static final int U_BG_NORM = 171;
	static final int U_BG_HIGH = 180;
	static final int U_BG_LOW = 189;

	static final int U_FG = 198;
	static final int U_FG_HALF_OFFS = 9;
	static final int U_FG_HIGH_OFFS = 18;
	
	static final int U_SAT = 171;
	static final int[] U_SAT_OFFS = {0, 9, 18, 27};
	static final int U_EXH = 234;
	
	static final int V_NORM = 9;
	static final int V_HUNGER = 27;
	static final int V_SAT_OFFS = 9;
	
	ConfigOption cfg_show_on_mount;
	ConfigOption cfg_icon_spacing;
	ConfigOption cfg_right_to_left;
	ConfigOption cfg_show_exh;
	ConfigOption cfg_show_sat;
	
	boolean show_on_mount;
	int icon_spacing;
	boolean right_to_left;
	
	boolean show_exh;
	boolean show_sat;
	
	int width;
	
	public ElementHungerBar()
	{
		this.cfg_show_on_mount = new ConfigOption(Type.BOOLEAN, "show_on_mount", false);
		this.cfg_icon_spacing = new ConfigOption(Type.INT, "icon_spacing", -1);
		this.cfg_right_to_left = new ConfigOption(Type.BOOLEAN, "right_to_left", true);
		
		this.cfg_show_exh = new ConfigOption(Type.BOOLEAN, "show_exhaustion", ComplexHUD.applecore_loaded);
		this.cfg_show_sat = new ConfigOption(Type.BOOLEAN, "show_saturation", ComplexHUD.applecore_loaded);
	}
	
	@Override
	public String getUnlocalizedName()
	{
		return "hud.hunger_bar";
	}

	@Override
	public int getDefaultPriority()
	{
		return -120;
	}

	@Override
	public boolean getDefaultFixedSetting()
	{
		return false;
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
	public IConfigEntry[] getConfigOptions()
	{
		return new IConfigEntry[] {
			new ConfigHeading(null),
			new ConfigHeading(OVRD_CFG_HEADING),
			this.cfg_enabled,
			this.cfg_override_vanilla,
			
			new ConfigHeading(null),
			new ConfigHeading("options.complexhud.hunger_bar_optns"),
			this.cfg_show_on_mount,
			this.cfg_icon_spacing,
			this.cfg_right_to_left,
			
			new ConfigHeading(null),
			new ConfigHeading("options.complexhud.appleskin_hunger_bar_optns"),
			this.cfg_show_exh,
			this.cfg_show_sat
		};
	}
	
	@Override
	public void onConfigUpdated()
	{
		super.onConfigUpdated();
		
		this.show_on_mount = this.cfg_show_on_mount.getBool();
		this.icon_spacing = this.cfg_icon_spacing.getInt();
		this.right_to_left = this.cfg_right_to_left.getBool();
		
		this.show_exh = this.cfg_show_exh.getBool() && ComplexHUD.applecore_loaded;
		/* The saturation values are still accessible without AppleCore, but they
		 * seem buggy when AppleCore isn't installed. It is as if the game does not
		 * send updates about saturation frequently unless AppleCore is handling
		 * things. So, I will not allow it to be enabled unless AppleCore is present.
		 */
		this.show_sat = this.cfg_show_sat.getBool() && ComplexHUD.applecore_loaded;
		
		this.cfg_show_exh.setEnabled(ComplexHUD.applecore_loaded);
		this.cfg_show_sat.setEnabled(ComplexHUD.applecore_loaded);
	}

	@Override
	public int getWidth()
	{
		return this.width;
	}

	@Override
	public int getHeight()
	{
		return 9;
	}

	@Override
	public boolean isToBeRendered()
	{
		if (!this.mc.playerController.shouldDrawHUD() || !this.enabled) {
			return false;
		}
		if (this.show_on_mount && this.mc.thePlayer.ridingEntity != null) {
			return true;
		}
		return GuiIngameForge.renderFood;
	}

	@Override
	public void doPreRenderWork()
	{
		this.width = 10 * (9 + this.icon_spacing) - this.icon_spacing;
	}

	@Override
	public void render(ScaledResolution sr, int mouse_x, int mouse_y, float partial_ticks, PositionInfoXY2 pos)
	{
		this.mc.mcProfiler.startSection("hunger");
		this.bindModIcons();
		GL11.glEnable(GL11.GL_BLEND);
		this.rand.setSeed((long) (this.update_counter * 312871));
		
		FoodStats stats = this.mc.thePlayer.getFoodStats();
		int food = stats.getFoodLevel();
		float sat = stats.getSaturationLevel() * 2.0f;
		
		int u1_bg = U_BG_NORM;
		int u1_fg = U_FG;
		if (this.mc.thePlayer.isPotionActive(Potion.field_76443_y /* Saturation */)) {
			u1_bg = U_BG_HIGH;
			u1_fg += U_FG_HIGH_OFFS;
		} else if (food <= 0) {
			if ((this.update_counter & 0x4) != 0) {
				u1_bg = U_BG_LOW;
			}
		} else if (food <= 6) {
			if ((this.update_counter & 0xf) < 4) {
				u1_bg = U_BG_LOW;
			}
		}
		
		int v1;
		if (this.mc.thePlayer.isPotionActive(Potion.hunger)) {
			v1 = V_HUNGER;
		} else {
			v1 = V_NORM;
		}
		
		if (this.show_exh) {
			float exh = AppleCoreAPI.accessor.getExhaustion(Minecraft.getMinecraft().thePlayer);
			float pxf = exh / 4.0f * (float) this.width;
			int px = (int) pxf + 1;
			int cnt = MathHelper.ceiling_float_int(pxf / 10.0f);
			for (int idx = 0; idx < cnt && px > 0; idx++) {
				int x = pos.left_x;
				int y = pos.top_y;
				int u = U_EXH;
				int w = Math.min(px, 10);
				if (this.right_to_left) {
					x = pos.right_x - 9 - (idx * 10);
					x += (10 - w);
					u += (10 - w);
				} else {
					x += (idx * 10);
				}
				this.drawTexturedModalRect(x, y, u, v1, w, 9);
				px -= 10;
			}
		}
		
		int food_to_draw = food;
		for (int idx = 0; idx < 10; idx++) {
			int icon = idx;
			if (this.right_to_left) {
				icon = 9 - icon;
			}
			
			int y_bump = 0;
			if (sat <= 0.0f && (this.update_counter % (food * 3 + 1)) == 0) {
				y_bump += this.rand.nextInt(3) - 1;
			}
			
			int x = pos.left_x + icon * (9 + this.icon_spacing);
			int y = pos.top_y + y_bump;
			
			this.drawTexturedModalRectCF(x, y, u1_bg, v1, 9, 9, !this.right_to_left, false);
			
			if (food_to_draw <= 0) {
				continue;
			}
			int u = (food_to_draw == 1) ? (u1_fg + U_FG_HALF_OFFS) : u1_fg;
			this.drawTexturedModalRectCF(x, y, u, v1, 9, 9, !this.right_to_left, false);
			
			food_to_draw -= 2;
		}
		
		if (this.show_sat) {
			int saturation_to_draw = (int) sat;
			for (int idx = 0; idx < 10 && saturation_to_draw > 0; idx++) {
				int icon = idx;
				if (this.right_to_left) {
					icon = 9 - icon;
				}
				
				int x = pos.left_x + icon * (9 + this.icon_spacing);
				int y = pos.top_y;
				
				int u = U_SAT;
				int v = v1 + V_SAT_OFFS;
				
				int p = Math.min(saturation_to_draw, 4);
				if (p > 0) {
					u += U_SAT_OFFS[p - 1];
				}
				
				this.drawTexturedModalRectCF(x, y, u, v, 9, 9, !this.right_to_left, false);
				
				saturation_to_draw -= 4;
				if (saturation_to_draw <= 0) {
					break;
				}
			}
		}
		
		GL11.glDisable(GL11.GL_BLEND);
		this.mc.mcProfiler.endSection();
	}
}
