package com.captrojo.complexhud.position;

import com.captrojo.complexhud.main.RegisteredElement;

import net.minecraft.client.gui.ScaledResolution;

public class PositionerTopRight extends PositionerBase
{
	@Override
	public void positionSections(ScaledResolution sr)
	{
		this.calcDims(sr);
		if (this.getMode() == MODE_SINGLE_COLUMN) {
			this.sec_right.x = sr.getScaledWidth() - this.sec_right.w;
			this.sec_right.y = 0;
			this.sec_top.x = this.sec_right.x - this.column_width;
			this.sec_top.y = 0;
			this.sec_bottom.x = this.sec_top.x;
			this.sec_bottom.y = this.sec_top.h;
			this.sec_left.x = this.sec_top.x - this.sec_left.w;
			this.sec_left.y = 0;
		} else if (this.getMode() == MODE_SINGLE_ROW) {
			this.sec_top.x = sr.getScaledWidth() - this.sec_top.w;
			this.sec_top.y = 0;
			this.sec_right.x = sr.getScaledWidth() - this.sec_right.w;
			this.sec_right.y = this.sec_top.h;
			this.sec_left.x = this.sec_right.x - this.sec_left.w;
			this.sec_left.y = this.sec_right.y;
			this.sec_bottom.x = sr.getScaledWidth() - this.sec_bottom.w;
			this.sec_bottom.y = this.sec_left.y + this.sec_left.h;
		}
	}

	@Override
	void alignElement(RegisteredElement re, ScaledResolution sr)
	{
		if (!re.pos_op.horz) {
			if (this.getMode() == MODE_SINGLE_ROW) {
				this.alignRight(re, sr.getScaledWidth());
			} else {
				this.alignRight(re, sr.getScaledWidth() - this.sec_right.w);
			}
		}
	}
}
