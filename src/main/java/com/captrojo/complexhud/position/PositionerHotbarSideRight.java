package com.captrojo.complexhud.position;

import com.captrojo.complexhud.main.RegisteredElement;

public class PositionerHotbarSideRight extends PositionerBase
{
	public PositionerHotbarSideRight()
	{
		super("positioner.hotbar_side_right");
	}

	@Override
	public void positionSections()
	{
		this.calcDims();
		this.positionXLeftSp(middle_x + 91);
		this.positionYBottom();
	}

	@Override
	void alignElement(RegisteredElement re)
	{
	}
}
