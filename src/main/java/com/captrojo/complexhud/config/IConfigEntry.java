package com.captrojo.complexhud.config;

import com.google.gson.JsonObject;

public interface IConfigEntry
{
	public void loadFromJson(JsonObject section_obj);
	
	public void saveToJson(JsonObject section_obj);
}
