package com.captrojo.complexhud.position;

import com.captrojo.complexhud.main.RegisteredElement;

import net.minecraft.client.gui.ScaledResolution;

public class PositionerMiddleRight extends PositionerBase
{
	@Override
	public void positionSections()
	{
		this.calcDims();
		this.positionXRight();
		this.positionYMiddle();
	}

	@Override
	void alignElement(RegisteredElement re)
	{
		this.alignRightSideOfScreen(re);
	}
}
