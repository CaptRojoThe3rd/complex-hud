package com.captrojo.complexhud.position;

public class PositionerBottomRight extends PositionerBase
{
	public PositionerBottomRight()
	{
		super("positioner.bottom_right", PositionerConfigEnum.BOTTOM_RIGHT);
	}

	@Override
	public void positionSections()
	{
		this.calcDims();
		this.positionXRight();
		this.positionYBottom();
	}

	@Override
	void alignElement(PositionedObject obj)
	{
		this.alignRightSideOfScreen(obj);
	}
}
