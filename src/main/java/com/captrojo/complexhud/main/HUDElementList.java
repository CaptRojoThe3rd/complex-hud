package com.captrojo.complexhud.main;

import java.util.ArrayList;

import com.captrojo.complexhud.api.IComplexHUDElement;
import com.captrojo.complexhud.config.ConfigSection;

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
	
	public static boolean replaceElement(String mod_id, String name, IComplexHUDElement new_element)
	{
		for (int i = 0; i < element_list.size(); i++) {
			RegisteredElement old_re = element_list.get(i);
			if (old_re.mod_id.equals(mod_id) && old_re.unlocalized_name.equals(name)) {
				RegisteredElement new_re = new RegisteredElement(mod_id, new_element);
				element_list.set(i, new_re);
				return true;
			}
		}
		return false;
	}
	
	public static boolean removeElement(String mod_id, String name)
	{
		for (int i = 0; i < element_list.size(); i++) {
			RegisteredElement old_re = element_list.get(i);
			if (old_re.mod_id.equals(mod_id) && old_re.unlocalized_name.equals(name)) {
				element_list.remove(i);
				return true;
			}
		}
		return false;
	}
	
	public static ArrayList<ConfigSection> getAllOptions()
	{
		ArrayList<ConfigSection> list = new ArrayList<ConfigSection>();
		for (RegisteredElement re : element_list) {
			list.add(re.options_sec);
		}
		return list;
	}
}
