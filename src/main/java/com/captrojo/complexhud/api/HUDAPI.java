package com.captrojo.complexhud.api;

import com.captrojo.complexhud.main.ComplexHUD;
import com.captrojo.complexhud.main.HUDElementList;

public class HUDAPI
{
	/* Get the mod version. */
	public static String getVersion()
	{
		return ComplexHUD.VERSION;
	}
	
	/* Get a number representing the mod version.
	 * Format: MJ.MN.PT -> 0xMJMNPT (1.2.3 -> 0x010203)
	 */
	public static int getVersionNumber()
	{
		return ComplexHUD.VERSION_NUM;
	}
	
	/* Register a HUD element. 
	 * There is no requirement that a HUD element ever be rendered.
	 */
	public static void registerElement(String mod_id, IComplexHUDElement element)
	{
		HUDElementList.registerElement(mod_id, element);
	}
}
