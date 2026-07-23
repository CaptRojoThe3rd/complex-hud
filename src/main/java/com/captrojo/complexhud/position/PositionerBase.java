package com.captrojo.complexhud.position;

import com.captrojo.complexhud.api.PositionInfoXY2;
import com.captrojo.complexhud.api.PositionInfoXYWH;
import com.captrojo.complexhud.config.ConfigOption;
import com.captrojo.complexhud.config.ConfigOption.Type;
import com.captrojo.complexhud.config.ConfigSection;
import com.captrojo.complexhud.config.ModConfig;
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
	
	ConfigOption cfg_offs_x;
	ConfigOption cfg_offs_y;
	ConfigOption cfg_alignment_enabled;
	public ConfigSection options_sec;
	
	PositionInfoXYWH sec_left;
	PositionInfoXYWH sec_right;
	PositionInfoXYWH sec_top;
	PositionInfoXYWH sec_bottom;
	
	int column_width;
	int column_height;
	int column_right_edge;
	
	int row_height;
	int row_width;

	public PositionerBase(String unlocalized_name)
	{
		this.cfg_offs_x = new ConfigOption(Type.INT, "offset_x", 0);
		this.cfg_offs_y = new ConfigOption(Type.INT, "offset_y", 0);
		this.cfg_alignment_enabled = new ConfigOption(Type.BOOLEAN, "alignment_enabled", true);
		this.options_sec = new ConfigSection(unlocalized_name, unlocalized_name);
		this.options_sec.addAll(this.cfg_offs_x, this.cfg_offs_y, this.cfg_alignment_enabled);
		this.options_sec.loadFromJson(ModConfig.cfgobj_positioning);
		this.options_sec.saveToJson(ModConfig.cfgobj_positioning);
		
		this.sec_left = new PositionInfoXYWH();
		this.sec_right = new PositionInfoXYWH();
		this.sec_top = new PositionInfoXYWH();
		this.sec_bottom = new PositionInfoXYWH();
	}
	
	int getMode()
	{
		return MODE_SINGLE_ROW;
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
	
	public void addToPosCalc(RegisteredElement re)
	{
		re.pos = new PositionInfoXY2();
		
		switch (re.getPosOp()) {
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
	
	public void positionElement(RegisteredElement re)
	{
		switch (re.getPosOp()) {
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
		if (this.cfg_alignment_enabled.getBool()) {
			this.alignElement(re);
		}
		
		re.pos.left_x += re.getXOffs() + this.cfg_offs_x.getInt();
		re.pos.left_x += re.element.getDefaultBufferLeftSize();
		re.pos.top_y += re.getYOffs() + this.cfg_offs_y.getInt();
		re.pos.top_y += re.element.getDefaultBufferTopSize();
		
		re.pos.right_x = re.pos.left_x + re.width - 1;
		re.pos.bottom_y = re.pos.top_y + re.height - 1;
	}
	
	abstract void alignElement(RegisteredElement re);
	
	void centerX(RegisteredElement re)
	{
		re.pos.left_x = (sr.getScaledWidth() / 2) - (re.width / 2);
	}
	
	void centerY(RegisteredElement re)
	{
		re.pos.top_y = (sr.getScaledHeight() / 2) - (re.height / 2);
	}
	
	void alignRight(RegisteredElement re, int right_x)
	{
		re.pos.left_x = right_x - re.width;
	}
	
	void alignRightSideOfScreen(RegisteredElement re)
	{
		this.alignRightSideSp(re, end_x);
	}
	
	void alignRightSideSp(RegisteredElement re, int end_x_sp)
	{
		if (!re.getPosOp().horz) {
			if (this.getMode() == MODE_SINGLE_ROW) {
				this.alignRight(re, end_x_sp);
			} else {
				this.alignRight(re, end_x_sp - this.sec_right.w);
			}
		}
	}
}
