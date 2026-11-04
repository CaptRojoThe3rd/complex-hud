package com.captrojo.complexhud.position;

public class PositionerHotbarSideLeft extends PositionerBase
{
	public PositionerHotbarSideLeft()
	{
		super("positioner.hotbar_side_left", PositionerConfigEnum.HOTBAR_SIDE_LEFT);
	}

	@Override
	public void positionSections()
	{
		this.calcDims();
		this.positionXRightSp(middle_x - 91);
		this.positionYBottom();
	}

	@Override
	void alignElement(PositionedObject obj)
	{
		this.alignRightSideSp(obj, middle_x - 91);
	}
}
