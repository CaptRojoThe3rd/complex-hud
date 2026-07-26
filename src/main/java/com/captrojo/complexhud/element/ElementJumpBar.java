package com.captrojo.complexhud.element;

import org.lwjgl.opengl.GL11;

import com.captrojo.complexhud.api.IConfigEntry;
import com.captrojo.complexhud.api.PositionInfoXY2;
import com.captrojo.complexhud.api.PositionOperation;
import com.captrojo.complexhud.api.PositionOrigin;
import com.captrojo.complexhud.config.ConfigHeading;

import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.GuiIngameForge;

public class ElementJumpBar extends ElementFillBarBase
{
	static final int U_1ST = 0;
	static final int U_2ND = 20;
	static final int U_END = 181;

	static final int V_BG = 98;
	static final int V_FG = 103;
	
	public ElementJumpBar()
	{
		this.bar_sec_widths = new int[] {20, 162, 20};
		this.last_sec = 2;
	}
	
	@Override
	public String getUnlocalizedName()
	{
		return "hud.jump_bar";
	}

	@Override
	public int getDefaultPriority()
	{
		return 10;
	}

	@Override
	public PositionOrigin getDefaultPosOrigin()
	{
		return PositionOrigin.HOTBAR_TOP_BOTH;
	}

	@Override
	public PositionOperation getDefaultPosOperation()
	{
		return PositionOperation.DOWN;
	}

	@Override
	public int getDefaultBufferTopSize()
	{
		return 1;
	}

	@Override
	public int getDefaultBufferBottomSize()
	{
		return 1;
	}

	@Override
	public IConfigEntry[] getConfigOptions()
	{
		return new IConfigEntry[] {
			new ConfigHeading(null),
			new ConfigHeading(OVRD_CFG_HEADING),
			this.cfg_enabled,
			this.cfg_override_vanilla,
		};
	}

	@Override
	public int getWidth()
	{
		return 182;
	}

	@Override
	public int getHeight()
	{
		return 5;
	}

	@Override
	public boolean isToBeRendered()
	{
		return GuiIngameForge.renderJumpBar && this.enabled;
	}

	@Override
	public void doPreRenderWork()
	{
	}

	@Override
	public void render(ScaledResolution sr, int mouse_x, int mouse_y, float partial_ticks, PositionInfoXY2 pos)
	{
		this.mc.mcProfiler.startSection("jump_bar");
		GL11.glDisable(GL11.GL_BLEND);
		this.bindModIcons();
		
		float charge = this.mc.thePlayer.getHorseJumpPower();
		int fill_w = (int) (charge * 182);
		
		this.drawBar(pos, 182, V_BG, U_1ST, U_2ND, U_END, 0);
		this.drawBar(pos, fill_w, V_FG, U_1ST, U_2ND, U_END, 0);

		GL11.glEnable(GL11.GL_BLEND);
		this.mc.mcProfiler.endSection();
	}
}
