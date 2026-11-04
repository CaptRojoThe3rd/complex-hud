package com.captrojo.complexhud.position;

public class PositionerMiddleRight extends PositionerBase
{
	public PositionerMiddleRight()
	{
		super("positioner.middle_right", PositionerConfigEnum.MIDDLE_RIGHT);
	}

	@Override
	public void positionSections()
	{
		this.calcDims();
		this.positionXRight();
		this.positionYMiddle();
	}

	@Override
	void alignElement(PositionedObject obj)
	{
		this.alignRightSideOfScreen(obj);
	}
}
