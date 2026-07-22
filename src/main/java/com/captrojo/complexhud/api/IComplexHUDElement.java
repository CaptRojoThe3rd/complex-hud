package com.captrojo.complexhud.api;

import java.util.List;

import com.captrojo.complexhud.config.ConfigOption;

import net.minecraft.client.gui.ScaledResolution;

public interface IComplexHUDElement
{
	/* Get the unlocalized name of the element.
	 * Used for the settings screen.
	 */
	public String getUnlocalizedName();
	
	/* Get default priority of the element. */
	public int getDefaultPriority();
	
	/* Get default setting for whether the element should be fixed in position or not. */
	public boolean getDefaultFixedSetting();
	
	/* Get default position origin of the element. */
	public PositionOrigin getDefaultPosOrigin();
	
	/* Get default position operation of the element. */
	public PositionOperation getDefaultPosOperation();
	
	/* Get default x offset. */
	public int getDefaultXOffs();
	
	/* Get default y offset. */
	public int getDefaultYOffs();
	
	/* Get default top buffer size. */
	public int getDefaultBufferTopSize();
	
	/* Get default bottom buffer size. */
	public int getDefaultBufferBottomSize();
	
	/* Get default left buffer size. */
	public int getDefaultBufferLeftSize();
	
	/* Get default right buffer size. */
	public int getDefaultBufferRightSize();
	
	/* Get default setting for whether the element should render in F3. */
	public boolean getDefaultRenderInF3Setting();
	
	/* Get any extra config options associated with this element.
	 * Return null if there aren't any.
	 */
	public ConfigOption[] getConfigOptions();
	
	/* Called when the config is updated. Use this if you want to cache values to speed up
	 * rendering.
	 */
	public void onConfigUpdated();
	
	/* Get the width of the HUD element. */
	public int getWidth();
	
	/* Get the height of the HUD element. */
	public int getHeight();
	
	/* Get whether the HUD element will be rendered or not. */
	public boolean isToBeRendered();
	
	/* Called every tick. */
	public void updateTick();
	
	/* Do any pre-render work. You can do literally anything here, as long as the getWidth,
	 * getHeight, and isToBeRendered methods are ready to be called afterwards.
	 */
	public void doPreRenderWork();
	
	/* Render the HUD element. */
	public void render(ScaledResolution sr, int mouse_x, int mouse_y, float partial_ticks, PositionInfoXY2 pos);
}
