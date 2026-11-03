package com.captrojo.complexhud.main;

import java.util.ArrayList;

import com.captrojo.complexhud.api.IComplexHUDElement;
import com.captrojo.complexhud.api.IHUDElementList;
import com.captrojo.complexhud.config.ConfigSection;

public class HUDElementList implements IHUDElementList
{
	public static final HUDElementList instance = new HUDElementList();

	ArrayList<RegisteredElement> element_list = new ArrayList<RegisteredElement>();
	boolean needs_sorting;

	void sort()
	{
		this.element_list.sort(null);
		this.needs_sorting = false;
	}

	public void onConfigUpdated()
	{
		this.needs_sorting = true;
		for (RegisteredElement re : element_list) {
			re.element.onConfigUpdated();
		}
	}

	@Override
	public void registerElement(String mod_id, IComplexHUDElement e)
	{
		RegisteredElement re = new RegisteredElement(mod_id, e);
		this.element_list.add(re);
		this.sort();
	}

	@Override
	public boolean replaceElement(String mod_id, String name, IComplexHUDElement new_element)
	{
		for (int i = 0; i < this.element_list.size(); i++) {
			RegisteredElement old_re = this.element_list.get(i);
			if (old_re.mod_id.equals(mod_id) && old_re.unlocalized_name.equals(name)) {
				RegisteredElement new_re = new RegisteredElement(mod_id, new_element);
				this.element_list.set(i, new_re);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean removeElement(String mod_id, String name)
	{
		for (int i = 0; i < this.element_list.size(); i++) {
			RegisteredElement old_re = this.element_list.get(i);
			if (old_re.mod_id.equals(mod_id) && old_re.unlocalized_name.equals(name)) {
				this.element_list.remove(i);
				return true;
			}
		}
		return false;
	}

	public ArrayList<ConfigSection> getAllOptions()
	{
		ArrayList<ConfigSection> list = new ArrayList<ConfigSection>();
		for (RegisteredElement re : this.element_list) {
			list.add(re.options_sec);
		}
		return list;
	}
}
