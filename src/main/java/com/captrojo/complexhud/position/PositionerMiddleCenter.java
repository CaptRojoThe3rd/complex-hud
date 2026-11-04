package com.captrojo.complexhud.position;

public class PositionerMiddleCenter extends PositionerBase
{
	public PositionerMiddleCenter()
	{
		super("positioner.middle_center", PositionerConfigEnum.MIDDLE_CENTER);
	}

	@Override
	public void positionSections()
	{
		this.calcDims();
		this.positionXCenter();
		this.positionYMiddle();
	}

	@Override
	void alignElement(PositionedObject obj)
	{
		if (!obj.getPosOp().horz) {
			this.centerX(obj);
		}
	}
}
