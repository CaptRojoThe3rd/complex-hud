package com.captrojo.complexhud.gui;

import com.captrojo.complexhud.api.IComplexHUDElement;
import com.captrojo.complexhud.config.ConfigOption;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;

public abstract class GuiConfigOptionButton extends GuiConfigOption
{
	GuiButton button;
	
	GuiConfigOptionButton(ConfigOption cfg_optn)
	{
		super(cfg_optn);
		this.w = 120;
		this.h = 20;
		this.button = new GuiButton(0, 0, 0, this.w, this.h, "");
	}

	void playClickSound()
	{
		this.button.func_146113_a(Minecraft.getMinecraft().getSoundHandler());
	}
	
	@Override
	void draw(int x, int y, int mouse_x, int mouse_y)
	{
		super.draw(x, y, mouse_x, mouse_y);
		
		FontRenderer fr = Minecraft.getMinecraft().fontRenderer;
		
		this.button.xPosition = x;
		this.button.yPosition = y;
		this.button.drawButton(Minecraft.getMinecraft(), mouse_x, mouse_y);
	}
	
	@Override
	void keyTyped(char c, int n)
	{
	}
}
