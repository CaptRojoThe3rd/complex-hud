package com.captrojo.complexhud.gui;

import com.captrojo.complexhud.config.ConfigOption;

public class GuiConfigOptionDouble extends GuiConfigOptionTextField
{
	GuiConfigOptionDouble(ConfigOption cfg_optn)
	{
		super(cfg_optn);
		this.text_field.setText("" + cfg_optn.getDouble());
	}

	@Override
	boolean setValueIfValid()
	{
		try {
			this.cfg_option.set(Double.valueOf(this.text_field.getText()));
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}
