package com.captrojo.complexhud.position;

import com.captrojo.complexhud.main.RegisteredElement;

import net.minecraft.client.gui.ScaledResolution;

public class PositionerTopRight extends PositionerBase
{
	public PositionerTopRight()
	{
		super("positioner.top_right");
	}

	@Override
	public void positionSections()
	{
		this.calcDims();
		this.positionXRight();
		this.positionYTop();
	}

	@Override
	void alignElement(RegisteredElement re)
	{
		this.alignRightSideOfScreen(re);
	}
}
