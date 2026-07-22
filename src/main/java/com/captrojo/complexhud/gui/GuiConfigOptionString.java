package com.captrojo.complexhud.gui;

import com.captrojo.complexhud.api.IComplexHUDElement;
import com.captrojo.complexhud.config.ConfigOption;

public class GuiConfigOptionString extends GuiConfigOptionTextField
{
	GuiConfigOptionString(ConfigOption cfg_optn)
	{
		super(cfg_optn);
		this.updateUIWithValue();
	}

	@Override
	boolean setValueIfValid()
	{
		this.cfg_optn.set(this.text_field.getText());
		return true;
	}

	@Override
	void updateUIWithValue()
	{
		this.text_field.setText(this.cfg_optn.getString());
	}
}
