package com.captrojo.complexhud.api;

/* The effect a HUD element will have on the positions of other HUD elements.
 * 
 * LEFT/RIGHT - The HUD element will be placed to the left or right of other HUD elements, and
 * will affect the positions of other HUD elements who share the element's origin and operation.
 * 
 * DOWN/UP - The HUD element will be placed to the bottom or top of other HUD elements, and
 * will affect the positions of other HUD elements who share the element's origin and operation.
 */
public enum PositionOperation
{
	LEFT(true),
	RIGHT(true),
	DOWN(false),
	UP(false);
	
	public final boolean horz;
	
	private PositionOperation(boolean horz)
	{
		this.horz = horz;
	}
}
