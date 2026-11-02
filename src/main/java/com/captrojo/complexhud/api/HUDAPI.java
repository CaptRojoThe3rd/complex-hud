package com.captrojo.complexhud.api;

public class HUDAPI
{
	public static IHUDElementList hud_element_list;
	
	/* Get the mod version. */
	public static String getVersion()
	{
		return "0.1.0";
	}
	
	/* Register a HUD element. 
	 * There is no requirement that a HUD element ever be rendered.
	 */
	public static void registerElement(String mod_id, IComplexHUDElement element)
	{
		if (hud_element_list == null) {
			return;
		}
		hud_element_list.registerElement(mod_id, element);
	}
	
	/* Replace a HUD element.
	 * Returns whether the specified element was found. If it wasn't, the provided new
	 * element will not be added to the list of elements.
	 */
	public static boolean replaceElement(String mod_id, String unlocalized_name, IComplexHUDElement element)
	{
		if (hud_element_list == null) {
			return false;
		}
		return hud_element_list.replaceElement(mod_id, unlocalized_name, element);
	}
	
	/* Remove a HUD element. Not sure why you would need this.
	 * Returns whether the element was there in the first place.
	 */
	public static boolean removeElement(String mod_id, String unlocalized_name)
	{
		if (hud_element_list == null) {
			return false;
		}
		return hud_element_list.removeElement(mod_id, unlocalized_name);
	}
}
