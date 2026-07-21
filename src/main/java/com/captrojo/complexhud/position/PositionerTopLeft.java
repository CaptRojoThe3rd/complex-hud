package com.captrojo.complexhud.position;

import com.captrojo.complexhud.main.RegisteredElement;

import net.minecraft.client.gui.ScaledResolution;

public class PositionerTopLeft extends PositionerBase
{
	public PositionerTopLeft()
	{
		super("positioner.top_left");
	}

	@Override
	public void positionSections()
	{
		this.calcDims();
		this.positionXLeft();
		this.positionYTop();
	}

	@Override
	void alignElement(RegisteredElement re)
	{
	}
}
