package com.captrojo.complexhud.gui;

import com.captrojo.complexhud.config.ConfigOption;
import com.captrojo.complexhud.main.HUDElementList;

import net.minecraft.client.Minecraft;

public class GuiConfigOptionBoolean extends GuiConfigOptionButton
{
	GuiConfigOptionBoolean(ConfigOption cfg_optn)
	{
		super(cfg_optn);
		this.button.displayString = "" + cfg_optn.getBool();
	}

	@Override
	boolean mouseClicked(int mouse_x, int mouse_y)
	{
		if (!this.checkMousePos(mouse_x, mouse_y)) {
			return false;
		}
		this.cfg_option.set(!this.cfg_option.getBool());
		this.button.displayString = Boolean.toString(this.cfg_option.getBool());
		this.button.func_146113_a(Minecraft.getMinecraft().getSoundHandler());
		HUDElementList.onSettingsChanged();
		return true;
	}
}
