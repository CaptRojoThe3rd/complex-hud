package com.captrojo.complexhud.position;

public class PositionerMiddleLeft extends PositionerBase
{
	public PositionerMiddleLeft()
	{
		super("positioner.middle_left", PositionerConfigEnum.MIDDLE_LEFT);
	}

	@Override
	public void positionSections()
	{
		this.calcDims();
		this.positionXLeft();
		this.positionYMiddle();
	}

	@Override
	void alignElement(PositionedObject obj)
	{
		if (obj.getPosOp().horz) {
			this.centerY(obj);
		}
	}
}
