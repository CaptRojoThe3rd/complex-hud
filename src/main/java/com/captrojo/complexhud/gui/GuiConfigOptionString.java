package com.captrojo.complexhud.gui;

import com.captrojo.complexhud.config.ConfigOption;

public class GuiConfigOptionString extends GuiConfigOptionTextField
{
	GuiConfigOptionString(ConfigOption cfg_optn)
	{
		super(cfg_optn);
		this.text_field.setText(cfg_optn.getString());
	}

	@Override
	boolean setValueIfValid()
	{
		this.cfg_option.set(this.text_field.getText());
		return true;
	}
}
