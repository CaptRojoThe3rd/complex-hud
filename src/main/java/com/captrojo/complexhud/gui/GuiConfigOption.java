package com.captrojo.complexhud.gui;

import java.util.ArrayList;

import com.captrojo.complexhud.api.IComplexHUDElement;
import com.captrojo.complexhud.config.ConfigOption;
import com.captrojo.complexhud.config.ConfigOptionSection;

public abstract class GuiConfigOption
{
	static GuiConfigOption createFrom(ConfigOption cfg_optn)
	{
		switch (cfg_optn.type) {
		case BOOLEAN:
			return new GuiConfigOptionBoolean(cfg_optn);
		case ENUM:
			return new GuiConfigOptionEnum(cfg_optn);
		case INT:
			return new GuiConfigOptionInt(cfg_optn);
		case DOUBLE:
			return new GuiConfigOptionDouble(cfg_optn);
		case STRING:
			return new GuiConfigOptionString(cfg_optn);
		}
		return null;
	}
	
	static ArrayList<GuiConfigOption> createFrom(ConfigOptionSection section)
	{
		ArrayList<GuiConfigOption> list = new ArrayList<GuiConfigOption>();
		for (int i = 0; i < section.size(); i++) {
			list.add(createFrom(section.get(i)));
		}
		return list;
	}
	
	ConfigOption cfg_optn;
	
	int x;
	int y;
	int w;
	int h;
	
	GuiConfigOption(ConfigOption cfg_optn)
	{
		this.cfg_optn = cfg_optn;
	}
	
	boolean checkMousePos(int mouse_x, int mouse_y)
	{
		if (mouse_x < this.x || mouse_x > (this.x + this.w)) {
			return false;
		}
		if (mouse_y < this.y || mouse_y > (this.y + this.h)) {
			return false;
		}
		return true;
	}
	
	void updateScreen()
	{
	}
	
	void draw(int x, int y, int mouse_x, int mouse_y)
	{
		this.x = x;
		this.y = y;
	}
	
	abstract boolean mouseClicked(int mouse_x, int mouse_y);
	
	abstract void keyTyped(char c, int n);
	
	abstract void updateUIWithValue();
}
