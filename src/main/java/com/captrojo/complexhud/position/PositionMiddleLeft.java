package com.captrojo.complexhud.position;

import com.captrojo.complexhud.api.PositionOperation;
import com.captrojo.complexhud.main.RegisteredElement;

import net.minecraft.client.gui.ScaledResolution;

public class PositionMiddleLeft extends PositionBase
{
	@Override
	public void calcOffset(ScaledResolution sr)
	{
		this.offs_x = -this.box.left_x;
		this.offs_y = (sr.getScaledHeight() / 2) - (this.box_height / 2);
	}

	@Override
	public void applyAllOffsets(RegisteredElement re, ScaledResolution sr, PositionOperation pos_op)
	{
		this.applyCalculatedOffs(re);
		if (pos_op.horz) {
			this.centerY(re, sr);
		}
	}
}
