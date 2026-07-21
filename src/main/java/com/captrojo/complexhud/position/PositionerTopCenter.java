package com.captrojo.complexhud.position;

import com.captrojo.complexhud.main.RegisteredElement;

import net.minecraft.client.gui.ScaledResolution;

public class PositionerTopCenter extends PositionerBase
{
	@Override
	public void positionSections()
	{
		this.calcDims();
		this.positionXCenter();
		this.positionYTop();
	}

	@Override
	void alignElement(RegisteredElement re)
	{
		if (!re.getPosOp().horz) {
			this.centerX(re);
		}
	}
}
