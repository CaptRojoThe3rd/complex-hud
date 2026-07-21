package com.captrojo.complexhud.gui;

import com.captrojo.complexhud.config.ConfigOption;
import com.captrojo.complexhud.main.HUDElementList;

import net.minecraft.client.Minecraft;

public class GuiConfigOptionEnum extends GuiConfigOptionButton
{
	GuiConfigOptionEnum(ConfigOption cfg_optn)
	{
		super(cfg_optn);
		this.button.displayString = cfg_optn.getEnum().toString();
	}

	@Override
	boolean mouseClicked(int mouse_x, int mouse_y)
	{
		if (!this.checkMousePos(mouse_x, mouse_y)) {
			return false;
		}
		this.cfg_option.incEnum();
		this.button.displayString = this.cfg_option.getEnum().toString();
		this.button.func_146113_a(Minecraft.getMinecraft().getSoundHandler());
		HUDElementList.onSettingsChanged();
		return true;
	}
}
