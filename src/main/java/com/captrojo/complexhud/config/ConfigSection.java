package com.captrojo.complexhud.config;

import java.util.ArrayList;

import com.captrojo.complexhud.main.I18nHlpr;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class ConfigSection
{
	String key;
	String unlocalized_name;
	
	ArrayList<IConfigEntry> option_list;
	
	public ConfigSection(String key, String unlocalized_name)
	{
		this.key = key;
		this.unlocalized_name = unlocalized_name;
		this.option_list = new ArrayList<IConfigEntry>();
	}
	
	public void add(IConfigEntry option)
	{
		this.option_list.add(option);
	}
	
	public void addAll(IConfigEntry...options) {
		for (IConfigEntry optn : options) {
			this.add(optn);
		}
	}
	
	public IConfigEntry get(int idx)
	{
		return this.option_list.get(idx);
	}
	
	public int size()
	{
		return this.option_list.size();
	}
	
	public String getName()
	{
		return I18nHlpr.get(this.unlocalized_name);
	}
	
	public void loadFromJson(JsonObject root_obj)
	{
		JsonElement e = root_obj.get(this.key);
		if (e == null || !e.isJsonObject()) {
			return;
		}
		JsonObject section_obj = e.getAsJsonObject();
		for (IConfigEntry optn : this.option_list) {
			optn.loadFromJson(section_obj);
		}
	}
	
	public void saveToJson(JsonObject root_obj)
	{
		JsonObject section_obj = new JsonObject();
		for (IConfigEntry optn : this.option_list) {
			optn.saveToJson(section_obj);
		}
		root_obj.add(this.key, section_obj);
	}
}
