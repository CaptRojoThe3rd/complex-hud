package com.captrojo.complexhud.gui;

import com.captrojo.complexhud.config.ConfigOption;

public class GuiConfigOptionInt extends GuiConfigOptionTextField
{
	GuiConfigOptionInt(ConfigOption cfg_optn)
	{
		super(cfg_optn);
		this.text_field.setText("" + cfg_optn.getInt());
	}

	@Override
	boolean setValueIfValid()
	{
		try {
			this.cfg_option.set(Integer.valueOf(this.text_field.getText()));
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}
