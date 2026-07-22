package com.captrojo.complexhud.gui;

import com.captrojo.complexhud.api.IComplexHUDElement;
import com.captrojo.complexhud.config.ConfigOption;

public class GuiConfigOptionDouble extends GuiConfigOptionTextField
{
	GuiConfigOptionDouble(ConfigOption cfg_optn)
	{
		super(cfg_optn);
		this.updateUIWithValue();
	}

	@Override
	boolean setValueIfValid()
	{
		try {
			this.cfg_optn.set(Double.valueOf(this.text_field.getText()));
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	@Override
	void updateUIWithValue()
	{
		this.text_field.setText(Double.toString(this.cfg_optn.getDouble()));
	}
}
