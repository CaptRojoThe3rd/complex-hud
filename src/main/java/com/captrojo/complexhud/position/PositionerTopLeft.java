package com.captrojo.complexhud.position;

public class PositionerTopLeft extends PositionerBase
{
	public PositionerTopLeft()
	{
		super("positioner.top_left", PositionerConfigEnum.TOP_LEFT);
	}

	@Override
	public void positionSections()
	{
		this.calcDims();
		this.positionXLeft();
		this.positionYTop();
	}

	@Override
	void alignElement(PositionedObject obj)
	{
	}
}
