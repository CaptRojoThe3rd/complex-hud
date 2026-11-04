package com.captrojo.complexhud.position;

public class PositionerTopRight extends PositionerBase
{
	public PositionerTopRight()
	{
		super("positioner.top_right", PositionerConfigEnum.TOP_RIGHT);
	}

	@Override
	public void positionSections()
	{
		this.calcDims();
		this.positionXRight();
		this.positionYTop();
	}

	@Override
	void alignElement(PositionedObject obj)
	{
		this.alignRightSideOfScreen(obj);
	}
}
