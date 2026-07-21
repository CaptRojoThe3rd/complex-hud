package com.captrojo.complexhud.position;

import com.captrojo.complexhud.main.RegisteredElement;

public class PositionerHotbarSideLeft extends PositionerBase
{
	public PositionerHotbarSideLeft()
	{
		super("positioner.hotbar_side_left");
	}

	@Override
	public void positionSections()
	{
		this.calcDims();
		this.positionXRightSp(middle_x - 91);
		this.positionYBottom();
	}

	@Override
	void alignElement(RegisteredElement re)
	{
		this.alignRightSideSp(re, middle_x - 91);
	}
}
