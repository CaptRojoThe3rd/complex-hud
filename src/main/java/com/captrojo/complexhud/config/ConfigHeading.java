package com.captrojo.complexhud.config;

import com.captrojo.complexhud.main.I18nHlpr;
import com.google.gson.JsonObject;

public class ConfigHeading implements IConfigEntry
{
	String unlocalized_name;
	
	public ConfigHeading(String unlocalized_name)
	{
		this.unlocalized_name = unlocalized_name;
	}
	
	public String getName()
	{
		if (this.unlocalized_name == null) {
			return "";
		}
		return I18nHlpr.get(this.unlocalized_name);
	}
	
	@Override
	public void loadFromJson(JsonObject section_obj)
	{
	}

	@Override
	public void saveToJson(JsonObject section_obj)
	{
	}
}
