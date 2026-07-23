package com.captrojo.complexhud.gui;

import java.util.ArrayList;

import com.captrojo.complexhud.config.ConfigHeading;
import com.captrojo.complexhud.config.ConfigOption;
import com.captrojo.complexhud.config.ConfigSection;
import com.captrojo.complexhud.config.IConfigEntry;

public abstract class GuiConfigEntry
{
	static GuiConfigEntry createFrom(IConfigEntry cfg_entry)
	{
		if (cfg_entry instanceof ConfigHeading) {
			return new GuiConfigHeading((ConfigHeading) cfg_entry);
		}
		if (cfg_entry instanceof ConfigOption) {
			ConfigOption cfg_optn = (ConfigOption) cfg_entry;
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
			default:
				return null;
			}
		}
		return null;
	}
	
	static ArrayList<GuiConfigEntry> createFrom(ConfigSection section)
	{
		ArrayList<GuiConfigEntry> list = new ArrayList<GuiConfigEntry>();
		for (int i = 0; i < section.size(); i++) {
			list.add(createFrom(section.get(i)));
		}
		return list;
	}
	
	IConfigEntry cfg_entry;
	
	int x;
	int y;
	int w;
	int h;
	
	public GuiConfigEntry(IConfigEntry entry)
	{
		this.cfg_entry = entry;
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
}
