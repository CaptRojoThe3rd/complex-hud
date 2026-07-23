package com.captrojo.complexhud.gui;

import com.captrojo.complexhud.api.IComplexHUDElement;
import com.captrojo.complexhud.config.ConfigOption;
import com.captrojo.complexhud.main.HUDElementList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiTextField;

public abstract class GuiConfigOptionTextField extends GuiConfigOption
{
	GuiTextField text_field;
	boolean is_input_valid;
	
	GuiConfigOptionTextField(ConfigOption cfg_optn)
	{
		super(cfg_optn);
		this.w = 120;
		this.h = 20;
		this.text_field = new GuiTextField(Minecraft.getMinecraft().fontRenderer, 0, 0, this.w - 2, this.h - 2);
		this.is_input_valid = true;
	}
	
	abstract boolean setValueIfValid();
	
	@Override
	void updateScreen()
	{
		this.text_field.updateCursorCounter();
	}

	@Override
	void draw(int x, int y, int mouse_x, int mouse_y)
	{
		super.draw(x, y, mouse_x, mouse_y);
		this.text_field.xPosition = x + 137;
		this.text_field.yPosition = y + 1;
		this.text_field.setTextColor(this.is_input_valid ? 0xffffff : 0xff2020);
		this.text_field.setEnabled(this.cfg_optn.isEnabled());
		this.text_field.drawTextBox();
	}

	@Override
	boolean mouseClicked(int mouse_x, int mouse_y)
	{
		this.text_field.mouseClicked(mouse_x, mouse_y, 0);
		return this.text_field.isFocused();
	}

	@Override
	void keyTyped(char c, int n)
	{
		this.text_field.textboxKeyTyped(c, n);
		this.is_input_valid = this.setValueIfValid();
		HUDElementList.onConfigUpdated();
	}
}
