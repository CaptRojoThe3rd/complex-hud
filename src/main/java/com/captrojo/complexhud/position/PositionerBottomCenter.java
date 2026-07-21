package com.captrojo.complexhud.position;

import com.captrojo.complexhud.main.RegisteredElement;

import net.minecraftforge.client.GuiIngameForge;

public class PositionerBottomCenter extends PositionerBase
{
	public PositionerBottomCenter()
	{
		super("positioner.bottom_center");
	}

	@Override
	public void positionSections()
	{
		this.calcDims();
		this.positionXCenter();
		int hotbar_height = Math.max(GuiIngameForge.left_height, GuiIngameForge.right_height) - 9;
		this.positionYBottomSp(end_y - hotbar_height);
	}

	@Override
	void alignElement(RegisteredElement re)
	{
		if (!re.getPosOp().horz) {
			this.centerX(re);
		}
	}
}
