package com.captrojo.complexhud.gui;

import net.minecraft.client.gui.GuiScreen;

public class GuiScreenEmpty extends GuiScreen
{
	GuiScreen parent_screen;
	
	public GuiScreenEmpty(GuiScreen parent)
	{
		this.parent_screen = parent;
	}
	
	@Override
	protected void keyTyped(char c, int n)
	{
		this.mc.displayGuiScreen(this.parent_screen);
	}
	
	@Override
	protected void mouseClicked(int mouse_x, int mouse_y, int mouse_button)
	{
		this.mc.displayGuiScreen(this.parent_screen);
	}
	
	@Override
	public void drawDefaultBackground()
	{
	}
}
