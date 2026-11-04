package com.captrojo.complexhud.position;

import com.captrojo.complexhud.api.PositionInfoXY2;
import com.captrojo.complexhud.api.PositionOperation;
import com.captrojo.complexhud.api.PositionOrigin;

import net.minecraft.client.Minecraft;

public abstract class PositionedObject
{
	public boolean to_be_rendered;
	public int adj_width;
	public int adj_height;
	public PositionInfoXY2 pos;

	public void reloadValues1()
	{
		this.to_be_rendered = this.isElementToBeRendered();
		if (Minecraft.getMinecraft().gameSettings.showDebugInfo) {
			this.to_be_rendered &= this.shouldRenderInF3();
		}
	}

	public void reloadValues2()
	{
		this.adj_width = this.getElementWidth();
		this.adj_width += this.getLeftBufferSize() + this.getRightBufferSize();
		this.adj_height = this.getElementHeight();
		this.adj_height += this.getTopBufferSize() + this.getBottomBufferSize();
	}
	
	public abstract int getElementWidth();
	
	public abstract int getElementHeight();
	
	public abstract boolean isElementToBeRendered();
	
	public abstract int getRenderPriority();

	public abstract boolean isFixed();

	public abstract PositionOrigin getPosOrigin();

	public abstract PositionOperation getPosOp();

	public abstract int getXOffs();

	public abstract int getYOffs();

	public abstract boolean shouldRenderInF3();

	public abstract int getTopBufferSize();

	public abstract int getBottomBufferSize();

	public abstract int getLeftBufferSize();

	public abstract int getRightBufferSize();
}
