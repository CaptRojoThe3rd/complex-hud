package com.captrojo.complexhud.position;

import com.captrojo.complexhud.main.RegisteredElement;

public class PositionerHotbarSideRight extends PositionerBase
{
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
