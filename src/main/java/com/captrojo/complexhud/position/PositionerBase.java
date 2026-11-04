package com.captrojo.complexhud.position;

import com.captrojo.complexhud.api.PositionInfoXY2;

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

	public static ScaledResolution sr;
	static int end_x;
	static int end_y;
	static int middle_x;
	static int middle_y;

	public static void reset(ScaledResolution resolution)
	{
		sr = resolution;
		end_x = sr.getScaledWidth();
		end_y = sr.getScaledHeight();
		middle_x = sr.getScaledWidth() / 2;
		middle_y = sr.getScaledHeight() / 2;
	}
	
	PositionerConfigEnum config_enum;

	PositionInfoXYWH sec_left;
	PositionInfoXYWH sec_right;
	PositionInfoXYWH sec_top;
	PositionInfoXYWH sec_bottom;

	int column_width;
	int column_height;
	int column_right_edge;

	int row_height;
	int row_width;

	public PositionerBase(String unlocalized_name, PositionerConfigEnum config_enum)
	{
		this.config_enum = config_enum;
		this.sec_left = new PositionInfoXYWH();
		this.sec_right = new PositionInfoXYWH();
		this.sec_top = new PositionInfoXYWH();
		this.sec_bottom = new PositionInfoXYWH();
	}
	
	int getOffsX()
	{
		return this.config_enum.offs_x;
	}
	
	int getOffsY()
	{
		return this.config_enum.offs_y;
	}
	
	boolean isAlignmentEnabled()
	{
		return this.config_enum.alignment_enabled;
	}

	int getMode()
	{
		return this.config_enum.mode.ordinal();
	}

	public void reset()
	{
		this.sec_left.set(0, 0, 0, 0);
		this.sec_right.set(0, 0, 0, 0);
		this.sec_top.set(0, 0, 0, 0);
		this.sec_bottom.set(0, 0, 0, 0);

		this.column_width = 0;
		this.column_height = 0;
		this.column_right_edge = 0;
		this.row_height = 0;
	}

	public void addToPosCalc(PositionedObject re)
	{
		if (re.pos == null) {
			re.pos = new PositionInfoXY2();
		} else {
			re.pos.set(0, 0, 0, 0);
		}

		switch (re.getPosOp()) {
		case LEFT:
			re.pos.left_x = this.sec_left.w;
			re.pos.top_y = 0;
			this.sec_left.w += re.adj_width;
			if (re.adj_height > this.sec_left.h) {
				this.sec_left.h = re.adj_height;
			}
			break;
		case RIGHT:
			re.pos.left_x = this.sec_right.w;
			re.pos.top_y = 0;
			this.sec_right.w += re.adj_width;
			if (re.adj_height > this.sec_right.h) {
				this.sec_right.h = re.adj_height;
			}
			break;
		case DOWN:
			re.pos.left_x = 0;
			re.pos.top_y = this.sec_bottom.h;
			this.sec_bottom.h += re.adj_height;
			if (re.adj_width > this.sec_bottom.w) {
				this.sec_bottom.w = re.adj_width;
			}
			break;
		case UP:
			re.pos.left_x = 0;
			re.pos.top_y = this.sec_top.h;
			this.sec_top.h += re.adj_height;
			if (re.adj_width > this.sec_top.w) {
				this.sec_top.w = re.adj_width;
			}
			break;
		}
	}

	void calcDims()
	{
		this.column_width = Math.max(this.sec_top.w, this.sec_bottom.w);
		this.column_height = this.sec_top.h + this.sec_bottom.h;
		this.column_right_edge = sr.getScaledWidth();
		if (this.getMode() == MODE_SINGLE_COLUMN) {
			this.column_right_edge -= this.sec_right.w;
		}

		this.row_height = Math.max(this.sec_left.h, this.sec_right.h);
		this.row_width = this.sec_left.w + this.sec_right.w;
	}

	public abstract void positionSections();

	void positionXLeft()
	{
		if (this.getMode() == MODE_SINGLE_COLUMN) {
			this.sec_left.x = 0;
			this.sec_top.x = this.sec_left.w;
			this.sec_bottom.x = this.sec_left.w;
			this.sec_right.x = this.sec_top.x + this.column_width;
		} else if (this.getMode() == MODE_SINGLE_ROW) {
			this.sec_top.x = 0;
			this.sec_left.x = 0;
			this.sec_right.x = this.sec_left.w;
			this.sec_bottom.x = 0;
		}
	}

	void positionXLeftSp(int begin_x_sp)
	{
		if (this.getMode() == MODE_SINGLE_COLUMN) {
			this.sec_left.x = begin_x_sp;
			this.sec_top.x = begin_x_sp + this.sec_left.w;
			this.sec_bottom.x = this.sec_top.x;
			this.sec_right.x = this.sec_top.x + this.column_width;
		} else if (this.getMode() == MODE_SINGLE_ROW) {
			this.sec_top.x = begin_x_sp;
			this.sec_left.x = begin_x_sp;
			this.sec_right.x = this.sec_left.x + this.sec_left.w;
			this.sec_bottom.x = begin_x_sp;
		}
	}

	void positionXCenter()
	{
		if (this.getMode() == MODE_SINGLE_COLUMN) {
			int column_w_half = this.column_width / 2;
			this.sec_left.x = middle_x - column_w_half - this.sec_left.w;
			this.sec_top.x = middle_x - column_w_half;
			this.sec_bottom.x = middle_x - column_w_half;
			this.sec_right.x = middle_x + column_w_half;
		} else if (this.getMode() == MODE_SINGLE_ROW) {
			this.sec_top.x = middle_x - (this.sec_top.w / 2);
			this.sec_left.x = middle_x - this.row_width / 2;
			this.sec_right.x = sec_left.x + this.sec_left.w;
			this.sec_bottom.x = middle_x - (this.sec_bottom.w / 2);
		}
	}

	void positionXRight()
	{
		this.positionXRightSp(end_x);
	}

	void positionXRightSp(int end_x_sp)
	{
		if (this.getMode() == MODE_SINGLE_COLUMN) {
			this.sec_right.x = end_x_sp - this.sec_right.w;
			this.sec_top.x = this.sec_right.x - this.column_width;
			this.sec_bottom.x = this.sec_top.x;
			this.sec_left.x = this.sec_top.x - this.sec_left.w;
		} else if (this.getMode() == MODE_SINGLE_ROW) {
			this.sec_top.x = end_x_sp - this.sec_top.w;
			this.sec_right.x = end_x_sp - this.sec_right.w;
			this.sec_left.x = this.sec_right.x - this.sec_left.w;
			this.sec_bottom.x = end_x_sp - this.sec_bottom.w;
		}
	}

	void positionYTop()
	{
		if (this.getMode() == MODE_SINGLE_COLUMN) {
			this.sec_left.y = 0;
			this.sec_top.y = 0;
			this.sec_bottom.y = this.sec_top.h;
			this.sec_right.y = 0;
		} else if (this.getMode() == MODE_SINGLE_ROW) {
			this.sec_top.y = 0;
			this.sec_left.y = this.sec_top.h;
			this.sec_right.y = this.sec_top.h;
			this.sec_bottom.y = this.sec_left.y + this.sec_left.h;
		}
	}

	void positionYMiddle()
	{
		if (this.getMode() == MODE_SINGLE_COLUMN) {
			this.sec_left.y = middle_y - (this.sec_left.h / 2);
			this.sec_top.y = middle_y - (this.column_height / 2);
			this.sec_bottom.y = this.sec_top.y + this.sec_top.h;
			this.sec_right.y = middle_y - (this.sec_right.h / 2);
		} else if (this.getMode() == MODE_SINGLE_ROW) {
			this.sec_top.y = middle_y - (this.row_height / 2) - this.sec_top.h;
			this.sec_left.y = this.sec_top.y + this.sec_top.h;
			this.sec_right.y = this.sec_left.y;
			this.sec_bottom.y = this.sec_left.y + this.row_height;
		}
	}

	void positionYBottom()
	{
		this.positionYBottomSp(end_y);
	}

	void positionYBottomSp(int end_y_sp)
	{
		if (this.getMode() == MODE_SINGLE_COLUMN) {
			this.sec_left.y = end_y_sp - this.sec_left.h;
			this.sec_right.y = end_y_sp - this.sec_right.h;
			this.sec_top.y = end_y_sp - this.column_height;
			this.sec_bottom.y = this.sec_top.y + this.sec_top.h;
		} else if (this.getMode() == MODE_SINGLE_ROW) {
			this.sec_bottom.y = end_y_sp - this.sec_bottom.h;
			this.sec_left.y = this.sec_bottom.y - this.row_height;
			this.sec_right.y = this.sec_left.y;
			this.sec_top.y = this.sec_left.y - this.sec_top.h;
		}
	}

	public void positionElement(PositionedObject obj)
	{
		switch (obj.getPosOp()) {
		case LEFT:
			obj.pos.left_x += this.sec_left.x;
			obj.pos.top_y += this.sec_left.y;
			break;
		case RIGHT:
			obj.pos.left_x += this.sec_right.x;
			obj.pos.top_y += this.sec_right.y;
			break;
		case DOWN:
			obj.pos.left_x += this.sec_bottom.x;
			obj.pos.top_y += this.sec_bottom.y;
			break;
		case UP:
			obj.pos.left_x += this.sec_top.x;
			obj.pos.top_y += this.sec_top.y;
			break;
		}
		if (this.isAlignmentEnabled()) {
			this.alignElement(obj);
		}

		obj.pos.left_x += obj.getXOffs() + this.getOffsX();
		obj.pos.left_x += obj.getLeftBufferSize();
		obj.pos.top_y += obj.getYOffs() + this.getOffsY();
		obj.pos.top_y += obj.getTopBufferSize();

		/* We want to use non-adjusted width and height here, since the element
		 * is unaware of buffer sizes.
		 */
		obj.pos.right_x = obj.pos.left_x + obj.getElementWidth() - 1;
		obj.pos.bottom_y = obj.pos.top_y + obj.getElementHeight() - 1;
	}

	abstract void alignElement(PositionedObject obj);

	void centerX(PositionedObject obj)
	{
		obj.pos.left_x = (sr.getScaledWidth() / 2) - (obj.adj_width / 2);
	}

	void centerY(PositionedObject obj)
	{
		obj.pos.top_y = (sr.getScaledHeight() / 2) - (obj.adj_height / 2);
	}

	void alignRight(PositionedObject obj, int right_x)
	{
		obj.pos.left_x = right_x - obj.adj_width;
	}

	void alignRightSideOfScreen(PositionedObject obj)
	{
		this.alignRightSideSp(obj, end_x);
	}

	void alignRightSideSp(PositionedObject obj, int end_x_sp)
	{
		if (!obj.getPosOp().horz) {
			if (this.getMode() == MODE_SINGLE_ROW) {
				this.alignRight(obj, end_x_sp);
			} else {
				this.alignRight(obj, end_x_sp - this.sec_right.w);
			}
		}
	}
}
