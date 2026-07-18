package com.captrojo.complexhud.position;

import com.captrojo.complexhud.api.PositionInfo;
import com.captrojo.complexhud.api.PositionOperation;
import com.captrojo.complexhud.main.RegisteredElement;

import net.minecraft.client.gui.ScaledResolution;

/* How positioning works:
 * 
 * 1. The position class's 'box' is reset.
 * 
 * 2. All element positions are first calculated as if all position classes were the 'top left'
 * class. The base position class just keeps track of how big the 'box' of elements is. This means
 * that position classes that are right-aligned or start from the bottom will initially assign
 * negative coordinates.
 * 
 * 3. Each position class calculates an offset needed to move its 'box' to the correct place on
 * the screen.
 * 
 * 4. Each position class can apply that offset as well as any other adjustments (such as right-
 * aligning or centering elements) to each element.
 */
public abstract class PositionBase
{
	PositionInfo box;
	int max_row_height;
	int max_column_width;
	
	int box_width;
	int box_height;
	
	int offs_x;
	int offs_y;
	
	public PositionBase()
	{
		this.box = new PositionInfo();
	}
	
	public void reset()
	{
		this.box.set(0, 0, 0, 0);
		this.max_row_height = 0;
		this.max_column_width = 0;
	}
	
	public void setInitialPos(RegisteredElement re)
	{
		PositionOperation pos_op = re.element.getPosOperation();
		re.pos = new PositionInfo();
		
		switch (pos_op) {
		case LEFT:
			re.pos.left_x = this.box.left_x - re.width;
			re.pos.top_y = 0;
			this.box.left_x -= re.width;
			break;
		case RIGHT:
			re.pos.left_x = this.box.right_x;
			re.pos.top_y = 0;
			this.box.right_x += re.width;
			break;
		case DOWN:
			re.pos.left_x = 0;
			re.pos.top_y = this.box.bottom_y;
			this.box.bottom_y += re.height;
			break;
		case UP:
			re.pos.left_x = 0;
			re.pos.top_y = this.box.top_y - re.height;
			this.box.top_y -= re.height;
			break;
		}
		
		if (pos_op.horz) {
			if (re.height > this.max_row_height) {
				this.max_row_height = re.height;
			}
		} else {
			if (re.width > this.max_column_width) {
				this.max_column_width = re.width;
			}
		}
	}
	
	public void calcDims()
	{
		this.box_width = this.box.right_x - this.box.left_x;
		this.box_height = this.box.bottom_y - this.box.top_y;
	}
	
	public abstract void calcOffset(ScaledResolution sr);
	
	public abstract void applyAllOffsets(RegisteredElement re, ScaledResolution sr, PositionOperation pos_op);
	
	void applyCalculatedOffs(RegisteredElement re)
	{
		re.pos.left_x += this.offs_x;
		re.pos.top_y += this.offs_y;
	}
	
	void centerX(RegisteredElement re, ScaledResolution sr)
	{
		re.pos.left_x = (sr.getScaledWidth() / 2) - (re.width / 2);
	}
	
	void centerY(RegisteredElement re, ScaledResolution sr)
	{
		re.pos.top_y = (sr.getScaledHeight() / 2) - (re.height / 2);
	}
	
	void alignRight(RegisteredElement re, ScaledResolution sr)
	{
		re.pos.left_x = sr.getScaledWidth() - re.width;
	}
}
