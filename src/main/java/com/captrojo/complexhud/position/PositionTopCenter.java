package com.captrojo.complexhud.position;

import com.captrojo.complexhud.api.PositionOperation;
import com.captrojo.complexhud.main.RegisteredElement;

import net.minecraft.client.gui.ScaledResolution;

public class PositionTopCenter extends PositionBase
{
	@Override
	public void calcOffset(ScaledResolution sr)
	{
		this.offs_x = (sr.getScaledWidth() / 2) - (this.max_column_width / 2);
		this.offs_y = -this.box.top_y;
	}

	@Override
	public void applyAllOffsets(RegisteredElement re, ScaledResolution sr, PositionOperation pos_op)
	{
		this.applyCalculatedOffs(re);
		if (!pos_op.horz) {
			this.centerX(re, sr);
		}
	}
}
