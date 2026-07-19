package com.captrojo.complexhud.position;

import com.captrojo.complexhud.main.RegisteredElement;

import net.minecraft.client.gui.ScaledResolution;

public class PositionerTopCenter extends PositionerBase
{
	@Override
	public void positionSections(ScaledResolution sr)
	{
		this.calcDims(sr);
		int middle_x = sr.getScaledWidth() / 2;
		int column_half = this.column_width / 2;
		
		if (this.getMode() == MODE_SINGLE_COLUMN) {
			this.sec_left.x = middle_x - column_half - this.sec_left.w;
			this.sec_left.y = 0;
			this.sec_top.x = middle_x - column_half;
			this.sec_top.y = 0;
			this.sec_bottom.x = middle_x - column_half;
			this.sec_bottom.y = this.sec_top.h;
			this.sec_right.x = middle_x + column_half;
			this.sec_right.y = 0;
		} else if (this.getMode() == MODE_SINGLE_ROW) {
			this.sec_top.x = middle_x - (this.sec_top.w / 2);
			this.sec_top.y = 0;
			this.sec_left.x = middle_x - this.sec_left.w;
			this.sec_left.y = this.sec_top.h;
			this.sec_right.x = middle_x;
			this.sec_right.y = this.sec_top.h;
			this.sec_bottom.x = middle_x - (this.sec_bottom.w / 2);
			this.sec_bottom.y = this.sec_top.h + this.row_height;
		}
	}

	@Override
	void alignElement(RegisteredElement re, ScaledResolution sr)
	{
		if (!re.pos_op.horz) {
			this.centerX(re, sr);
		}
	}
}
