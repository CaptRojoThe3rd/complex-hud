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
import net.minecraft.util.MathHelper;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.common.ForgeHooks;

public class ElementArmorBar extends OverriddenVanillaElement
{
	static final int U_BG = 171;
	static final int U_FG_HALF = 180;
	static final int U_FG_FULL = 189;
	static final int V_ALL = 0;
	
	ConfigOption cfg_icon_count;
	ConfigOption cfg_icon_spacing;
	ConfigOption cfg_show_empty;
	ConfigOption cfg_right_to_left;
	
	ConfigOption cfg_overloaded;
	ConfigOption cfg_overload_colors;
	
	int icon_count;
	int icon_spacing;
	boolean show_empty;
	boolean right_to_left;
	
	boolean overloaded;
	float[][] overload_colors;
	
	int width;
	int height;
	
	int icons;
	int armor;
	
	public ElementArmorBar()
	{
		this.cfg_icon_count = new ConfigOption(Type.INT, "icon_count", 10);
		this.cfg_icon_spacing = new ConfigOption(Type.INT, "icon_spacing", -1);
		this.cfg_show_empty = new ConfigOption(Type.BOOLEAN, "show_empty_icons", false);
		this.cfg_right_to_left = new ConfigOption(Type.BOOLEAN, "right_to_left", false);
		
		this.cfg_overloaded = new ConfigOption(Type.BOOLEAN, "overloaded_armor", true);
		this.cfg_overload_colors = new ConfigOption(Type.STRING, "overload_colors", "ffff80,7fffff,424043");
	}
	
	@Override
	public String getUnlocalizedName()
	{
		return "hud.armor_bar";
	}

	@Override
	public int getDefaultPriority()
	{
		return -110;
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
	public IConfigEntry[] getConfigOptions()
	{
		return new IConfigEntry[] {
			new ConfigHeading(null),
			new ConfigHeading(OVRD_CFG_HEADING),	
			this.cfg_enabled,
			this.cfg_override_vanilla,
			
			new ConfigHeading(null),
			new ConfigHeading("options.complexhud.armor_bar_std_optns"),
			this.cfg_icon_count,
			this.cfg_icon_spacing,
			this.cfg_show_empty,
			this.cfg_right_to_left,
			
			new ConfigHeading(null),
			new ConfigHeading("options.complexhud.armor_bar_overload_optns"),
			this.cfg_overloaded,
			this.cfg_overload_colors
		};
	}

	@Override
	public void onConfigUpdated()
	{
		super.onConfigUpdated();
		
		this.icon_count = this.cfg_icon_count.getInt();
		this.icon_spacing = this.cfg_icon_spacing.getInt();
		this.show_empty = this.cfg_show_empty.getBool();
		this.right_to_left = this.cfg_right_to_left.getBool();
		
		this.overloaded = this.cfg_overloaded.getBool();
		this.overload_colors = this.parseColorListStr(this.cfg_overload_colors.getString());
		
		this.cfg_overload_colors.setEnabled(this.overloaded);
		this.cfg_overload_colors.setInvalid(this.overload_colors.length == 0);
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
		return this.enabled && this.mc.playerController.shouldDrawHUD() && GuiIngameForge.renderArmor;
	}

	@Override
	public void doPreRenderWork()
	{
		this.mc.mcProfiler.startSection("armor");
		
		this.armor = ForgeHooks.getTotalArmorValue(this.mc.thePlayer);
		this.icons = this.show_empty ? this.icon_count : MathHelper.ceiling_float_int((float) this.armor / 2.0f);
		this.width = icons * (9 + this.icon_spacing) - this.icon_spacing;
		
		this.height = 9;
		
		this.mc.mcProfiler.endSection();
	}

	@Override
	public void render(ScaledResolution sr, int mouse_x, int mouse_y, float partial_ticks, PositionInfoXY2 pos)
	{
		this.mc.mcProfiler.startSection("armor");
	        GL11.glEnable(GL11.GL_BLEND);
	        this.bindModIcons();
		
		int icon = 0;
		int overload = 0;
		int armor_to_draw = this.armor;
		
		for (int idx = 0; idx < this.icons; idx++) {
			int draw_icon = icon;
			if (this.right_to_left) {
				draw_icon = (this.icon_count - 1) - draw_icon;
			}
			
			int x = pos.left_x + draw_icon * (9 + this.icon_spacing);
			int y = pos.top_y;
			
			if (overload == 0) {
				this.drawTexturedModalRect(x, y, U_BG, V_ALL, 9, 9);
			}
			
			if (armor_to_draw > 0) {
				int u = (armor_to_draw == 1) ? U_FG_HALF : U_FG_FULL;
				if (overload > 0) {
					float color[];
					if (overload > this.overload_colors.length) {
						color = COLOR_WHITE;
					} else {
						color = this.overload_colors[overload - 1];
					}
					GL11.glColor4f(color[0], color[1], color[2], 1.0f);
				}
				if (this.right_to_left) {
					this.drawTexturedModalRectFlippedHorz(x, y, u, V_ALL, 9, 9);
				} else {
					this.drawTexturedModalRect(x, y, u, V_ALL, 9, 9);
				}
				if (overload > 0) {
					GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
				}
			}
			
			icon++;
			if (this.overloaded && icon == this.icon_count) {
				icon = 0;
				overload++;
			}
			armor_to_draw -= 2;
		}

	        GL11.glDisable(GL11.GL_BLEND);
		this.mc.mcProfiler.endSection();
	}
}
