package com.captrojo.complexhud.position;

import com.captrojo.complexhud.api.PositionInfoXY2;
import com.captrojo.complexhud.api.PositionInfoXYWH;
import com.captrojo.complexhud.main.RegisteredElement;

import net.minecraft.client.gui.ScaledResolution;

/* How positioning works:
 * 
 * 1. The positioner's values are reset.
 * 
 * 2. The positioner is given each element to calculate how big each of its 'sections' (left,
 * right, top, bottom) are.
 * 
 * 3. The positioner calculates the position of each section.
 * 
 * 4. The positioner adds the position of an element's section to that element's position.
 */
public abstract class PositionerBase
{
	static final int MODE_SINGLE_COLUMN = 0;
	static final int MODE_SINGLE_ROW = 1;
	
	PositionInfoXYWH sec_left;
	PositionInfoXYWH sec_right;
	PositionInfoXYWH sec_top;
	PositionInfoXYWH sec_bottom;
	
	int column_width;
	int row_height;

	int column_right_edge;

	public PositionerBase()
	{
		this.sec_left = new PositionInfoXYWH();
		this.sec_right = new PositionInfoXYWH();
		this.sec_top = new PositionInfoXYWH();
		this.sec_bottom = new PositionInfoXYWH();
	}
	
	int getMode()
	{
		return MODE_SINGLE_COLUMN;
	}
	
	public void reset()
	{
		this.sec_left.set(0, 0, 0, 0);
		this.sec_right.set(0, 0, 0, 0);
		this.sec_top.set(0, 0, 0, 0);
		this.sec_bottom.set(0, 0, 0, 0);
		
		this.column_width = 0;
		this.row_height = 0;
	}
	
	public void addToPosCalc(RegisteredElement re)
	{
		re.pos = new PositionInfoXY2();
		
		switch (re.pos_op) {
		case LEFT:
			re.pos.left_x = this.sec_left.w;
			re.pos.top_y = 0;
			this.sec_left.w += re.width;
			if (re.height > this.sec_left.h) {
				this.sec_left.h = re.height;
			}
			break;
		case RIGHT:
			re.pos.left_x = this.sec_right.w;
			re.pos.top_y = 0;
			this.sec_right.w += re.width;
			if (re.height > this.sec_right.h) {
				this.sec_right.h = re.height;
			}
			break;
		case DOWN:
			re.pos.left_x = 0;
			re.pos.top_y = this.sec_bottom.h;
			this.sec_bottom.h += re.height;
			if (re.width > this.sec_bottom.w) {
				this.sec_bottom.w = re.width;
			}
			break;
		case UP:
			re.pos.left_x = 0;
			re.pos.top_y = this.sec_top.h;
			this.sec_top.h += re.height;
			if (re.width > this.sec_top.w) {
				this.sec_top.w = re.width;
			}
			break;
		}
	}
	
	void calcDims(ScaledResolution sr)
	{
		this.column_width = Math.max(this.sec_top.w, this.sec_bottom.w);
		this.row_height = Math.max(this.sec_left.h, this.sec_right.h);
		
		this.column_right_edge = sr.getScaledWidth();
		if (this.getMode() == MODE_SINGLE_COLUMN) {
			this.column_right_edge -= this.sec_right.w;
		}
	}
	
	public abstract void positionSections(ScaledResolution sr);
	
	public void positionElement(RegisteredElement re, ScaledResolution sr)
	{
		switch (re.pos_op) {
		case LEFT:
			re.pos.left_x += this.sec_left.x;
			re.pos.top_y += this.sec_left.y;
			break;
		case RIGHT:
			re.pos.left_x += this.sec_right.x;
			re.pos.top_y += this.sec_right.y;
			break;
		case DOWN:
			re.pos.left_x += this.sec_bottom.x;
			re.pos.top_y += this.sec_bottom.y;
			break;
		case UP:
			re.pos.left_x += this.sec_top.x;
			re.pos.top_y += this.sec_top.y;
			break;
		}
		this.alignElement(re, sr);
		re.pos.right_x = re.pos.left_x + re.width - 1;
		re.pos.bottom_y = re.pos.top_y + re.height - 1;
	}
	
	abstract void alignElement(RegisteredElement re, ScaledResolution sr);
	
	void centerX(RegisteredElement re, ScaledResolution sr)
	{
		re.pos.left_x = (sr.getScaledWidth() / 2) - (re.width / 2);
	}
	
	void centerY(RegisteredElement re, ScaledResolution sr)
	{
		re.pos.top_y = (sr.getScaledHeight() / 2) - (re.height / 2);
	}
	
	void alignRight(RegisteredElement re, int right_x)
	{
		re.pos.left_x = right_x - re.width;
	}
}
