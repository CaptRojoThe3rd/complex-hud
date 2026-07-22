package com.captrojo.complexhud.gui;

import com.captrojo.complexhud.api.IComplexHUDElement;
import com.captrojo.complexhud.config.ConfigOption;
import com.captrojo.complexhud.main.HUDElementList;

public class GuiConfigOptionEnum extends GuiConfigOptionButton
{
	GuiConfigOptionEnum(ConfigOption cfg_optn)
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
		this.cfg_optn.incEnum();
		this.updateUIWithValue();
		this.playClickSound();
		HUDElementList.onConfigUpdated();
		return true;
	}

	@Override
	void updateUIWithValue()
	{
		this.button.displayString = this.cfg_optn.getEnum().toString();
	}
}
