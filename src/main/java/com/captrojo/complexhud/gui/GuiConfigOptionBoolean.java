package com.captrojo.complexhud.gui;

import com.captrojo.complexhud.api.config.ConfigOption;

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
		this.updateConfigs();
		return true;
	}

	@Override
	void updateUIWithValue()
	{
		this.button.displayString = Boolean.toString(this.cfg_optn.getBool());
	}
}
