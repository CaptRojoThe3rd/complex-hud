package com.captrojo.complexhud.position;

import com.captrojo.complexhud.main.ComplexHUD;
import com.captrojo.complexhud.main.RegisteredElement;

import net.minecraftforge.client.GuiIngameForge;

public class PositionerHotbarTopBoth extends PositionerBase
{
	public PositionerHotbarTopBoth()
	{
		super("positioner.hotbar_top_both");
	}

	@Override
	public void positionSections()
	{
		this.calcDims();
		this.positionXCenter();
		
		int gui_height = Math.max(GuiIngameForge.left_height, GuiIngameForge.right_height);
		this.positionYBottomSp(end_y - gui_height + 9);
		int height = (this.getMode() == MODE_SINGLE_COLUMN) ? this.column_height : this.column_height + this.row_height;
		gui_height += height;
		GuiIngameForge.left_height = gui_height;
		GuiIngameForge.right_height = gui_height;
	}

	@Override
	void alignElement(RegisteredElement re)
	{
		if (!re.getPosOp().horz) {
			this.centerX(re);
		}
	}
}
