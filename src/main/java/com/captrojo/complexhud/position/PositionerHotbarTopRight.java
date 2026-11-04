package com.captrojo.complexhud.position;

import net.minecraftforge.client.GuiIngameForge;

public class PositionerHotbarTopRight extends PositionerBase
{
	public PositionerHotbarTopRight()
	{
		super("positioner.hotbar_top_right", PositionerConfigEnum.HOTBAR_TOP_RIGHT);
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
	void alignElement(PositionedObject obj)
	{
		if (!obj.getPosOp().horz) {
			this.alignRightSideSp(obj, middle_x + 91);
		}
	}
}
