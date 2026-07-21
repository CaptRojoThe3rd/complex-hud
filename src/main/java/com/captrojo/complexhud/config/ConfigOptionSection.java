package com.captrojo.complexhud.config;

import java.util.ArrayList;

import com.captrojo.complexhud.main.I18nHlpr;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class ConfigOptionSection
{
	String key;
	String unlocalized_name;
	
	ArrayList<ConfigOption> option_list;
	
	public ConfigOptionSection(String key, String unlocalized_name)
	{
		this.key = key;
		this.unlocalized_name = unlocalized_name;
		this.option_list = new ArrayList<ConfigOption>();
	}
	
	public void add(ConfigOption option)
	{
		this.option_list.add(option);
	}
	
	public void addAll(ConfigOption...options) {
		for (ConfigOption optn : options) {
			this.add(optn);
		}
	}
	
	public ConfigOption get(int idx)
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
		for (ConfigOption optn : this.option_list) {
			optn.loadFromJson(section_obj);
		}
	}
	
	public void saveToJson(JsonObject root_obj)
	{
		JsonObject section_obj = new JsonObject();
		for (ConfigOption optn : this.option_list) {
			optn.saveToJson(section_obj);
		}
		root_obj.add(this.key, section_obj);
	}
}
