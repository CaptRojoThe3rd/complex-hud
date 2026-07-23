package com.captrojo.complexhud.gui;

import com.captrojo.complexhud.config.ConfigHeading;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

public class GuiConfigHeading extends GuiConfigEntry
{
	ConfigHeading cfg_heading;
	
	public GuiConfigHeading(ConfigHeading cfg_heading)
	{
		super(cfg_heading);
		this.cfg_heading = cfg_heading;
	}
	
	@Override
	void draw(int x, int y, int mouse_x, int mouse_y)
	{
		super.draw(x, y, mouse_x, mouse_y);
		
		String str = this.cfg_heading.getName();
		FontRenderer fr = Minecraft.getMinecraft().fontRenderer;

		int x1 = 128 - (fr.getStringWidth(str) / 2);
		
		fr.drawStringWithShadow(str, x + 4 + x1, y + 4, 0xffffff);
	}

	@Override
	boolean mouseClicked(int mouse_x, int mouse_y)
	{
		return false;
	}

	@Override
	void keyTyped(char c, int n)
	{
	}
}
