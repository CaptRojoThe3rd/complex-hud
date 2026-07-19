package com.captrojo.complexhud.position;

import com.captrojo.complexhud.main.RegisteredElement;

public class PositionerBottomLeft extends PositionerBase
{
	@Override
	public void positionSections()
	{
		this.calcDims();
		this.positionXLeft();
		this.positionYBottom();
	}

	@Override
	void alignElement(RegisteredElement re)
	{
	}
}
