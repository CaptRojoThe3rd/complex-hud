package com.captrojo.complexhud.position;

public class PositionerBottomLeft extends PositionerBase
{
	public PositionerBottomLeft()
	{
		super("positioner.bottom_left", PositionerConfigEnum.BOTTOM_LEFT);
	}

	@Override
	public void positionSections()
	{
		this.calcDims();
		this.positionXLeft();
		this.positionYBottom();
	}

	@Override
	void alignElement(PositionedObject obj)
	{
	}
}
