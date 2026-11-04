package com.captrojo.complexhud.position;

public enum PositionerConfigEnum
{
	HOTBAR_SIDE_LEFT,
	HOTBAR_SIDE_RIGHT,
	
	HOTBAR_TOP_BOTH,
	HOTBAR_TOP_LEFT,
	HOTBAR_TOP_RIGHT,
	
	TOP_LEFT,
	TOP_CENTER,
	TOP_RIGHT,
	
	MIDDLE_LEFT,
	MIDDLE_CENTER,
	MIDDLE_RIGHT,
	
	BOTTOM_LEFT,
	BOTTOM_CENTER,
	BOTTOM_RIGHT;
	
	public final String name;
	
	public int offs_x = 0;
	public int offs_y = 0;
	public boolean alignment_enabled = true;
	public PosModeEnum mode = PosModeEnum.SINGLE_ROW;
	
	PositionerConfigEnum()
	{
		this.name = this.name().toLowerCase();
	}
}
