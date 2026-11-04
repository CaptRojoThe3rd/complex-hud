package com.captrojo.complexhud.position;

public class PositionerTopCenter extends PositionerBase
{
	public PositionerTopCenter()
	{
		super("positioner.top_center", PositionerConfigEnum.TOP_CENTER);
	}

	@Override
	public void positionSections()
	{
		this.calcDims();
		this.positionXCenter();
		this.positionYTop();
	}

	@Override
	void alignElement(PositionedObject obj)
	{
		if (!obj.getPosOp().horz) {
			this.centerX(obj);
		}
	}
}
