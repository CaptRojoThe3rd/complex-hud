package com.captrojo.complexhud.api;

public interface IHUDElementList
{
	void registerElement(String mod_id, IComplexHUDElement element);

	boolean replaceElement(String mod_id, String unlocalized_name, IComplexHUDElement element);

	boolean removeElement(String mod_id, String unlocalized_name);
}
