package com.captrojo.complexhud.gui;

import java.util.ArrayList;

import com.captrojo.complexhud.config.ConfigOption;
import com.captrojo.complexhud.config.ConfigSection;
import com.captrojo.complexhud.config.IConfigEntry;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

public abstract class GuiConfigOption extends GuiConfigEntry
{
	ConfigOption cfg_optn;
	
	GuiConfigOption(ConfigOption cfg_optn)
	{
		super(cfg_optn);
		this.cfg_optn = cfg_optn;
	}
	
	boolean checkMousePos(int mouse_x, int mouse_y)
	{
		this.x += 136;
		if (mouse_x < this.x || mouse_x > (this.x + this.w)) {
			return false;
		}
		if (mouse_y < this.y || mouse_y > (this.y + this.h)) {
			return false;
		}
		this.x -= 136;
		return true;
	}
	
	abstract void updateUIWithValue();
	
	@Override
	void draw(int x, int y, int mouse_x, int mouse_y)
	{
		super.draw(x, y, mouse_x, mouse_y);
		
		String str = this.cfg_optn.getName();
		FontRenderer fr = Minecraft.getMinecraft().fontRenderer;

		if (fr.getStringWidth(str) > 125) {
			while (fr.getStringWidth(str) > 125) {
				str = str.substring(0, str.length() - 1);
			}
			str += "...";
		}
		
		fr.drawStringWithShadow(str, x + 4, y + 4, this.cfg_optn.isEnabled() ? 0xffffff : 0xc0c0c0);
	}
}
