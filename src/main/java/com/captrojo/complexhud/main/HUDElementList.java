package com.captrojo.complexhud.main;

import java.util.ArrayList;

import com.captrojo.complexhud.api.IComplexHUDElement;
import com.captrojo.complexhud.config.ConfigOptionSection;

public class HUDElementList
{
	static ArrayList<RegisteredElement> element_list;
	static boolean needs_sorting;
	
	static void init()
	{
		element_list = new ArrayList<RegisteredElement>();
	}
	
	static void sort()
	{
		element_list.sort(null);
		needs_sorting = false;
	}
	
	public static void onConfigUpdated()
	{
		needs_sorting = true;
		for (RegisteredElement re : element_list) {
			re.element.onConfigUpdated();
		}
	}
	
	public static void registerElement(String mod_id, IComplexHUDElement e)
	{
		RegisteredElement re = new RegisteredElement(mod_id, e);
		element_list.add(re);
		sort();
	}
	
	public static ArrayList<ConfigOptionSection> getAllOptions()
	{
		ArrayList<ConfigOptionSection> list = new ArrayList<ConfigOptionSection>();
		for (RegisteredElement re : element_list) {
			list.add(re.options_sec);
		}
		return list;
	}
}
