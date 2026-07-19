package com.captrojo.complexhud.main;

import com.captrojo.complexhud.api.IComplexHUDElement;
import com.captrojo.complexhud.api.PositionInfoXY2;
import com.captrojo.complexhud.api.PositionOperation;
import com.captrojo.complexhud.api.PositionOrigin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;

public class DebugHUDElement implements IComplexHUDElement
{
	int priority;
	String text;
	
	public DebugHUDElement(int priority, String text)
	{
		this.priority = priority;
		this.text = text;
	}
	
	@Override
	public int getRenderPriority()
	{
		return this.priority;
	}

	@Override
	public int getWidth()
	{
		FontRenderer fr = Minecraft.getMinecraft().fontRenderer;
		return fr.getStringWidth(this.text);
	}

	@Override
	public int getHeight()
	{
		FontRenderer fr = Minecraft.getMinecraft().fontRenderer;
		return fr.FONT_HEIGHT;
	}

	@Override
	public PositionOrigin getPosOrigin()
	{
		return PositionOrigin.TOP_CENTER;
	}

	@Override
	public PositionOperation getPosOperation()
	{
		switch (this.priority) {
		case 0:
			return PositionOperation.DOWN;
		case 1:
			return PositionOperation.LEFT;
		case 2:
			return PositionOperation.UP;
		case 3:
			return PositionOperation.RIGHT;
		}
		return PositionOperation.DOWN;
	}

	@Override
	public boolean isFixed()
	{
		return false;
	}

	@Override
	public boolean isToBeRendered()
	{
		return true;
	}
	
	@Override
	public boolean renderInF3()
	{
		return false;
	}

	@Override
	public void render(ScaledResolution sr, int mouse_x, int mouse_y, float partial_ticks, PositionInfoXY2 pos)
	{
		FontRenderer fr = Minecraft.getMinecraft().fontRenderer;
		fr.drawStringWithShadow(this.text, pos.left_x, pos.top_y, 0xffffff);
	}
}
