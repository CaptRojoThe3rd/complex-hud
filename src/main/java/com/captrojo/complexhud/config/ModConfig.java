package com.captrojo.complexhud.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.captrojo.complexhud.config.ConfigOption.Type;
import com.captrojo.complexhud.main.ComplexHUD;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;

public class ModConfig
{
	static Gson gson = new GsonBuilder().setPrettyPrinting().create();

	static File cfgfile_general;
	static File cfgfile_positioning;
	static File cfgfile_elements;
	
	public static JsonObject cfgobj_general;
	public static JsonObject cfgobj_positioning;
	public static JsonObject cfgobj_elements;
	
	public static ConfigOption done_y_offs;
	public static ConfigOption optn_enabled;
	public static ConfigOption optn_id;
	public static ConfigOption optn_x_offs;
	public static ConfigOption optn_y_offs;
	public static ConfigSection cfgsec_menu_settings;
	
	public static ConfigOption armor_bar_y_fix;
	public static ConfigSection cfgsec_fixes;
	
	public static ArrayList<ConfigSection> cfg_sections;
	
	public static void init(File cfg_file)
	{	
		load();
		
		done_y_offs = new ConfigOption(Type.INT, "done_btn_y_offs", 0);
		optn_enabled = new ConfigOption(Type.BOOLEAN, "optn_btn_enabled", true);
		optn_id = new ConfigOption(Type.INT, "optn_btn_id", 500);
		optn_x_offs = new ConfigOption(Type.INT, "optn_btn_x_offs", -155);
		optn_y_offs = new ConfigOption(Type.INT, "optn_btn_y_offs", 42);
		
		cfgsec_menu_settings = new ConfigSection("menu_settings", "options.complexhud.menu_settings");
		cfgsec_menu_settings.addAll(
			done_y_offs,
			optn_enabled,
			optn_id,
			optn_x_offs,
			optn_y_offs
		);
		cfgsec_menu_settings.loadFromJson(cfgobj_general);
		cfgsec_menu_settings.saveToJson(cfgobj_general);
		
		armor_bar_y_fix = new ConfigOption(Type.BOOLEAN, "armor_bar_y_fix", true);
		
		cfgsec_fixes = new ConfigSection("fixes", "options.complexhud.fixes");
		cfgsec_fixes.addAll(
			armor_bar_y_fix
		);
		
		cfg_sections = new ArrayList<ConfigSection>();
		cfg_sections.add(cfgsec_menu_settings);
		cfg_sections.add(cfgsec_fixes);
		
		save();
	}
	
	public static void load()
	{
		cfgfile_general = new File(ComplexHUD.config_dir + File.separator + "complexhud_general.json");
		if (!cfgfile_general.exists()) {
			writeDefaultJson(cfgfile_general);
		}
		cfgfile_positioning = new File(ComplexHUD.config_dir + File.separator + "complexhud_positioning.json");
		if (!cfgfile_positioning.exists()) {
			writeDefaultJson(cfgfile_positioning);
		}
		cfgfile_elements = new File(ComplexHUD.config_dir + File.separator + "complexhud_elements.json");
		if (!cfgfile_elements.exists()) {
			writeDefaultJson(cfgfile_elements);
		}
		
		try {
			cfgobj_general = gson.fromJson(new FileReader(cfgfile_general), JsonObject.class);
			cfgobj_positioning = gson.fromJson(new FileReader(cfgfile_positioning), JsonObject.class);
			cfgobj_elements = gson.fromJson(new FileReader(cfgfile_elements), JsonObject.class);
		} catch (FileNotFoundException e) {
			throw new IllegalStateException("Failed to load json config");
		}
	}
	
	public static void save()
	{
		try {
			final File[] files = {cfgfile_general, cfgfile_positioning, cfgfile_elements};
			final JsonObject[] objs = {cfgobj_general, cfgobj_positioning, cfgobj_elements};
			
			for (int i = 0; i < files.length; i++) {
				FileWriter fw = new FileWriter(files[i]);
				JsonWriter jw = new JsonWriter(fw);
				gson.toJson(objs[i], jw);
				jw.close();
				fw.close();
			}
		} catch (IOException e) {
		}
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
