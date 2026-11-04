package com.captrojo.complexhud.gui;

import com.captrojo.complexhud.api.config.ConfigOption;
import com.captrojo.complexhud.config.ModConfig;
import com.captrojo.complexhud.main.HUDElementList;

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
	
	void updateConfigs()
	{
		HUDElementList.instance.onConfigUpdated();
		ModConfig.updatePositionerConfig();
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

		if (fr.getStringWidth(str) > 134) {
			str += "...";
			while (fr.getStringWidth(str) > 134) {
				str = str.substring(0, str.length() - 4);
				str += "...";
			}
		}

		fr.drawStringWithShadow(str, x, y + 5, this.cfg_optn.isEnabled() ? 0xffffff : 0xc0c0c0);
	}
}
