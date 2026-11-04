package com.captrojo.complexhud.position;

public class PositionerHotbarSideRight extends PositionerBase
{
	public PositionerHotbarSideRight()
	{
		super("positioner.hotbar_side_right", PositionerConfigEnum.HOTBAR_SIDE_RIGHT);
	}

	@Override
	public void positionSections()
	{
		this.calcDims();
		this.positionXLeftSp(middle_x + 91);
		this.positionYBottom();
	}

	@Override
	void alignElement(PositionedObject obj)
	{
	}
}
