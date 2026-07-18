package com.captrojo.complexhud.api;

import net.minecraft.client.gui.ScaledResolution;

public interface IComplexHUDElement
{
	/* Get the render priority of the element.
	 * 
	 * Lower values mean the element is rendered earlier. For example, if the origin is set
	 * to TOP_LEFT, then an element with a low value will be rendered closer to the top left
	 * of the screen.
	 */
	public int getRenderPriority();
	
	/* Get the width of the HUD element. */
	public int getWidth();
	
	/* Get the height of the HUD element. */
	public int getHeight();
	
	/* Get the origin of the position of the HUD element. */
	public PositionOrigin getPosOrigin();
	
	/* Get the position operation of the HUD element. */
	public PositionOperation getPosOperation();
	
	/* Get whether the element is fixed on the screen.
	 * 
	 * false - The element's position is affected by other elements, and the element affects
	 * the position of other elements who share this element's origin position.
	 * 
	 * true - The element is not affected by and will not affect other elements.
	 */
	public boolean isFixed();
	
	/* Get whether the HUD element will be rendered or not. */
	public boolean isToBeRendered();
	
	/* Whether the HUD element should render while the F3 menu is active. */
	public boolean renderInF3();
	
	/* Render the HUD element. */
	public void render(ScaledResolution sr, int mouse_x, int mouse_y, float partial_ticks, PositionInfo pos);
}
