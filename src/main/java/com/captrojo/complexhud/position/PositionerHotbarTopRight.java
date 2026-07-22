package com.captrojo.complexhud.position;

import com.captrojo.complexhud.main.RegisteredElement;

import net.minecraftforge.client.GuiIngameForge;

public class PositionerHotbarTopRight extends PositionerBase
{
	public PositionerHotbarTopRight()
	{
		super("positioner.hotbar_top_right");
	}

	@Override
	public void positionSections()
	{
		this.calcDims();
		this.positionXLeftSp(middle_x + 10);
		this.positionYBottomSp(end_y - GuiIngameForge.right_height + 9);
		int height = (this.getMode() == MODE_SINGLE_COLUMN) ? this.column_height : this.column_height + this.row_height;
		GuiIngameForge.right_height += height;
	}

	@Override
	void alignElement(RegisteredElement re)
	{
		if (!re.getPosOp().horz) {
			this.alignRightSideSp(re, middle_x + 91);
		}
	}
}
