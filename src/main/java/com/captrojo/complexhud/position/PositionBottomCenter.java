package com.captrojo.complexhud.position;

import com.captrojo.complexhud.api.PositionOperation;
import com.captrojo.complexhud.main.RegisteredElement;

import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.GuiIngameForge;

public class PositionBottomCenter extends PositionBase
{
	int hotbar_height;
	
	@Override
	public void calcOffset(ScaledResolution sr)
	{
		this.offs_x = (sr.getScaledWidth() / 2) - (this.max_column_width / 2);
		this.hotbar_height = Math.max(GuiIngameForge.left_height, GuiIngameForge.right_height) - 9;
		this.offs_y = sr.getScaledHeight() - this.box_height - this.hotbar_height;
	}

	@Override
	public void applyAllOffsets(RegisteredElement re, ScaledResolution sr, PositionOperation pos_op)
	{
		this.applyCalculatedOffs(re);
		if (!pos_op.horz) {
			this.centerX(re, sr);
		} else {
			re.pos.top_y = sr.getScaledHeight() - this.hotbar_height - re.height;
		}
	}
}
