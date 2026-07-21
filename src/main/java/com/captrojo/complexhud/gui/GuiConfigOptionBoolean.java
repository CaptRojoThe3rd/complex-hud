package com.captrojo.complexhud.gui;

import com.captrojo.complexhud.config.ConfigOption;
import com.captrojo.complexhud.main.HUDElementList;

import net.minecraft.client.Minecraft;

public class GuiConfigOptionBoolean extends GuiConfigOptionButton
{
	GuiConfigOptionBoolean(ConfigOption cfg_optn)
	{
		super(cfg_optn);
		this.updateUIWithValue();
	}

	@Override
	boolean mouseClicked(int mouse_x, int mouse_y)
	{
		if (!this.checkMousePos(mouse_x, mouse_y)) {
			return false;
		}
		this.cfg_optn.set(!this.cfg_optn.getBool());
		this.updateUIWithValue();
		this.playClickSound();
		HUDElementList.onSettingsChanged();
		return true;
	}

	@Override
	void updateUIWithValue()
	{
		this.button.displayString = Boolean.toString(this.cfg_optn.getBool());
	}
}
