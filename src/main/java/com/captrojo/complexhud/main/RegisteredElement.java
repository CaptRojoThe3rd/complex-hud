package com.captrojo.complexhud.main;

import com.captrojo.complexhud.api.IComplexHUDElement;
import com.captrojo.complexhud.api.PositionInfoXY2;
import com.captrojo.complexhud.api.PositionOperation;

import net.minecraft.client.Minecraft;

public class RegisteredElement implements Comparable<RegisteredElement>
{
	public IComplexHUDElement element;
	public PositionInfoXY2 pos;
	
	/* Cache these values to avoid unnecessary processing every time we need them */
	public boolean to_be_rendered;
	public PositionOperation pos_op;
	public int width;
	public int height;
	
	public RegisteredElement(IComplexHUDElement element)
	{
		this.element = element;
	}
	
	public void reloadValues1()
	{
		this.to_be_rendered = this.element.isToBeRendered();
		if (Minecraft.getMinecraft().gameSettings.showDebugInfo) {
			this.to_be_rendered &= this.element.renderInF3();
		}
	}
	
	public void reloadValues2()
	{
		this.pos_op = this.element.getPosOperation();
		this.width = this.element.getWidth();
		this.height = this.element.getHeight();
	}
	
	@Override
	public int compareTo(RegisteredElement o)
	{
		return this.element.getRenderPriority() - o.element.getRenderPriority();
	}
}
