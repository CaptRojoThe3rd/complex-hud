package com.captrojo.complexhud.position;

import com.captrojo.complexhud.main.RegisteredElement;

import net.minecraft.client.gui.ScaledResolution;

public class PositionerMiddleCenter extends PositionerBase
{
	public PositionerMiddleCenter()
	{
		super("positioner.middle_center");
	}

	@Override
	public void positionSections()
	{
		this.calcDims();
		this.positionXCenter();
		this.positionYMiddle();
	}

	@Override
	void alignElement(RegisteredElement re)
	{
		if (!re.getPosOp().horz) {
			this.centerX(re);
		}
	}
}
