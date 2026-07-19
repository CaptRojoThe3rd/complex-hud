package com.captrojo.complexhud.position;

import com.captrojo.complexhud.main.RegisteredElement;

import net.minecraft.client.gui.ScaledResolution;

public class PositionerMiddleLeft extends PositionerBase
{
	@Override
	public void positionSections()
	{
		this.calcDims();
		this.positionXLeft();
		this.positionYMiddle();
	}

	@Override
	void alignElement(RegisteredElement re)
	{
		if (re.pos_op.horz) {
			this.centerY(re);
		}
	}
}
