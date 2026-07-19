package com.captrojo.complexhud.position;

import com.captrojo.complexhud.main.RegisteredElement;

public class PositionerBottomRight extends PositionerBase
{
	@Override
	public void positionSections()
	{
		this.calcDims();
		this.positionXRight();
		this.positionYBottom();
	}

	@Override
	void alignElement(RegisteredElement re)
	{
		this.alignRightSideOfScreen(re);
	}
}
