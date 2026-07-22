package com.captrojo.complexhud.position;

import com.captrojo.complexhud.main.RegisteredElement;

import net.minecraftforge.client.GuiIngameForge;

public class PositionerHotbarTopLeft extends PositionerBase
{
	public PositionerHotbarTopLeft()
	{
		super("positioner.hotbar_top_left");
	}

	@Override
	public void positionSections()
	{
		this.calcDims();
		this.positionXLeftSp(middle_x - 91);
		this.positionYBottomSp(end_y - GuiIngameForge.left_height + 9);
		int height = (this.getMode() == MODE_SINGLE_COLUMN) ? this.column_height : this.column_height + this.row_height;
		GuiIngameForge.left_height += height;
	}

	@Override
	void alignElement(RegisteredElement re)
	{
	}
}
