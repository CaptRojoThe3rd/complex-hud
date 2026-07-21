package com.captrojo.complexhud.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.captrojo.complexhud.main.ComplexHUD;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class ModConfig
{
	static Gson gson = new GsonBuilder().setPrettyPrinting().create();
	
	static File pos_config_file;
	static File element_config_file;
	
	public static JsonObject pos_config_obj;
	public static JsonObject element_config_obj;
	
	static Configuration _config;
	static String _category;
	
	public static int done_y_offs = 0;
	
	public static boolean optn_enabled = true;
	public static int optn_id = 500;
	public static int optn_x_offs = -155;
	public static int optn_y_offs = 42;
	
	public static void init(File file)
	{	
		pos_config_file = new File(ComplexHUD.config_dir + File.separator + "complexhud_positioning.json");
		if (!pos_config_file.exists()) {
			writeDefaultJson(pos_config_file);
		}
		element_config_file = new File(ComplexHUD.config_dir + File.separator + "complexhud_elements.json");
		if (!element_config_file.exists()) {
			writeDefaultJson(element_config_file);
		}
		
		try {
			pos_config_obj = gson.fromJson(new FileReader(pos_config_file), JsonObject.class);
			element_config_obj = gson.fromJson(new FileReader(element_config_file), JsonObject.class);
		} catch (FileNotFoundException e) {
			throw new IllegalStateException("Failed to load json config");
		}
		
		
		load(file);
		
		_category = "general";
		
		done_y_offs = getInt("done_y_offs", "Y offset for the 'Done' button in the vanilla options menu", done_y_offs);
		
		optn_enabled = getBool("optn_btn_enabled", "Enable button for Complex HUD options in the vanilla options menu", optn_enabled);
		optn_id = getInt("optn_btn_id", "ID for Complex HUD options menu button", optn_id);
		optn_x_offs = getInt("optn_btn_x_offs", "X offset for Complex HUD options menu button", optn_x_offs);
		optn_y_offs = getInt("optn_btn_y_offs", "Y offset for Complex HUD options menu button", optn_y_offs);
		
		save();
	}
	
	public static void load(File file)
	{
		_config = new Configuration(file);
	}
	
	public static void save()
	{
		_config.save();
	}
	
	public static void saveJson()
	{
		
		try {
			FileWriter fw = new FileWriter(pos_config_file);
			JsonWriter jw = new JsonWriter(fw);
			gson.toJson(pos_config_obj, jw);
			jw.close();
			fw.close();
			
			fw = new FileWriter(element_config_file);
			jw = new JsonWriter(fw);
			gson.toJson(element_config_obj, new JsonWriter(fw));
			jw.close();
			fw.close();
		} catch (IOException e) {
		}
	}
	
	protected static void setCategoryComment(String comment)
	{
		_config.setCategoryComment(_category, comment);
	}
	
	protected static int getInt(String name, String comment, int default_value)
	{
		Property prop = _config.get(_category, name, default_value);
		prop.comment = comment;
		return prop.getInt();
	}

	protected static double getDouble(String name, String comment, double default_value)
	{
		Property prop = _config.get(_category, name, default_value);
		prop.comment = comment;
		return prop.getDouble();
	}

	protected static boolean getBool(String name, String comment, boolean default_value)
	{
		Property prop = _config.get(_category, name, default_value);
		prop.comment = comment;
		return prop.getBoolean();
	}

	protected static String getString(String name, String comment, String default_value)
	{
		Property prop = _config.get(_category, name, default_value);
		prop.comment = comment;
		return prop.getString();
	}
	
	protected static String[] getStringList(String name, String comment, String[] default_value)
	{
		Property prop = _config.get(_category, name, default_value);
		prop.comment = comment;
		return prop.getStringList();
	}
	
	static void writeDefaultJson(File file)
	{
		try {
			JsonWriter writer = new JsonWriter(new FileWriter(file));
			writer.setIndent("\t");
			writer.beginObject();
			writer.endObject();
			writer.close();
		} catch (IOException e) {
			throw new IllegalStateException("Failed to initialize json config");
		}
	}
}
