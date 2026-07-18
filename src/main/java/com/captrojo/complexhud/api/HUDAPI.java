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
	 * Format: MJ.MN.PT -> 0xMJMNPT (1.0.0 -> 0x010000)
	 */
	public static int getVersionNumber()
	{
		return ComplexHUD.VERSION_NUM;
	}
	
	/* Register a HUD element. 
	 * There is no requirement that a HUD element ever be rendered.
	 */
	public static void registerElement(IComplexHUDElement element)
	{
		HUDElementList.registerElement(element);
	}
}
