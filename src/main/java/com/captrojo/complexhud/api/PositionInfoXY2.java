package com.captrojo.complexhud.api;

public class PositionInfoXY2
{
	public int left_x;
	public int right_x;
	public int top_y;
	public int bottom_y;
	
	public PositionInfoXY2()
	{
	}
	
	public PositionInfoXY2(int left_x, int right_x, int top_y, int bottom_y)
	{
		this.set(left_x, right_x, top_y, bottom_y);
	}
	
	public void set(int left_x, int right_x, int top_y, int bottom_y)
	{
		this.left_x = left_x;
		this.right_x = right_x;
		this.top_y = top_y;
		this.bottom_y = bottom_y;
	}
	
	public void add(int left_x, int right_x, int top_y, int bottom_y)
	{
		this.left_x += left_x;
		this.right_x += right_x;
		this.top_y += top_y;
		this.bottom_y += bottom_y;
	}
}
