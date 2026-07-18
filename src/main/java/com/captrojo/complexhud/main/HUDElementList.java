package com.captrojo.complexhud.main;

import java.util.ArrayList;

import com.captrojo.complexhud.api.IComplexHUDElement;

public class HUDElementList
{
	static ArrayList<RegisteredElement> element_list;
	
	static void init()
	{
		element_list = new ArrayList<RegisteredElement>();
	}
	
	public static void registerElement(IComplexHUDElement e)
	{
		RegisteredElement re = new RegisteredElement(e);
		element_list.add(re);
		element_list.sort(null);
	}
}
