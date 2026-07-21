package com.captrojo.complexhud.gui;

import com.captrojo.complexhud.config.ConfigOption;

public class GuiConfigOptionInt extends GuiConfigOptionTextField
{
	GuiConfigOptionInt(ConfigOption cfg_optn)
	{
		super(cfg_optn);
		this.updateUIWithValue();
	}

	@Override
	boolean setValueIfValid()
	{
		try {
			this.cfg_optn.set(Integer.valueOf(this.text_field.getText()));
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	@Override
	void updateUIWithValue()
	{
		this.text_field.setText(Integer.toString(this.cfg_optn.getInt()));
	}
}
