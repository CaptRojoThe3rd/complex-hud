package com.captrojo.complexhud.element;

import org.lwjgl.opengl.GL11;

import com.captrojo.complexhud.api.IConfigEntry;
import com.captrojo.complexhud.api.PositionInfoXY2;
import com.captrojo.complexhud.api.PositionOperation;
import com.captrojo.complexhud.api.PositionOrigin;
import com.captrojo.complexhud.config.ConfigHeading;
import com.captrojo.complexhud.config.ConfigOption;
import com.captrojo.complexhud.config.ConfigOption.Type;

import net.minecraft.block.material.Material;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.MathHelper;
import net.minecraftforge.client.GuiIngameForge;

public class ElementAirBar extends OverriddenVanillaElement
{
	static final int U_FULL = 198;
	static final int U_POPPING = 207;
	static final int U_EMPTY = 216;
	
	static final int V_ALL = 0;
	
	ConfigOption cfg_icon_spacing;
	ConfigOption cfg_right_to_left;

	int icon_spacing;
	boolean right_to_left;
	
	int width;
	
	public ElementAirBar()
	{
		this.cfg_icon_spacing = new ConfigOption(Type.INT, "icon_spacing", -1);
		this.cfg_right_to_left = new ConfigOption(Type.BOOLEAN, "right_to_left", true);
	}
	
	@Override
	public String getUnlocalizedName()
	{
		return "hud.air_bar";
	}

	@Override
	public int getDefaultPriority()
	{
		return -130;
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
			new ConfigHeading("options.complexhud.air_bar_optns"),
			this.cfg_icon_spacing,
			this.cfg_right_to_left
		};
	}
	
	@Override
	public void onConfigUpdated()
	{
		super.onConfigUpdated();
		
		this.icon_spacing = this.cfg_icon_spacing.getInt();
		this.right_to_left = this.cfg_right_to_left.getBool();
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
		if (!this.mc.thePlayer.isInsideOfMaterial(Material.water)) {
			return false;
		}
		return this.enabled && this.mc.playerController.shouldDrawHUD() && GuiIngameForge.renderAir;
	}

	@Override
	public void doPreRenderWork()
	{
		this.width = 10 * (9 + this.icon_spacing) - this.icon_spacing;
	}

	@Override
	public void render(ScaledResolution sr, int mouse_x, int mouse_y, float partial_ticks, PositionInfoXY2 pos)
	{
		this.mc.mcProfiler.startSection("air");
		this.bindModIcons();
		GL11.glEnable(GL11.GL_BLEND);
		
		int air = this.mc.thePlayer.getAir();
		int full = MathHelper.ceiling_float_int((float) (air - 2) / 30.0f);
		int partial = MathHelper.ceiling_float_int((float) (air) / 30.0f);
		
		for (int i = 0; i < 10; i++) {
			int u;
			if (i < full) {
				u = U_FULL;
			} else if (i < partial) {
				u = U_POPPING;
			} else {
				u = U_EMPTY;
			}
			
			int draw_idx = this.right_to_left ? (9 - i) : i;
			int x = pos.left_x + draw_idx * (9 + this.icon_spacing);
			
			this.drawTexturedModalRect(x, pos.top_y, u, V_ALL, 9, 9);
		}

		GL11.glDisable(GL11.GL_BLEND);
		this.mc.mcProfiler.endSection();
	}
}
