package com.captrojo.complexhud.main;

import java.util.List;

import com.captrojo.complexhud.api.IComplexHUDElement;
import com.captrojo.complexhud.api.PositionInfoXY2;
import com.captrojo.complexhud.api.PositionOperation;
import com.captrojo.complexhud.api.PositionOrigin;
import com.captrojo.complexhud.config.ConfigOption;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;

public class DebugHUDElement implements IComplexHUDElement
{
	int priority;
	String text;
	PositionOrigin origin;
	
	public DebugHUDElement(int priority, String text)
	{
		this(priority, PositionOrigin.TOP_LEFT, text);
	}
	
	public DebugHUDElement(int priority, PositionOrigin origin, String text)
	{
		this.priority = priority;
		this.origin = origin;
		this.text = text;
	}
	
	@Override
	public String getUnlocalizedName()
	{
		return "Debug " + Integer.toString(this.priority);
	}

	@Override
	public int getDefaultPriority()
	{
		return this.priority;
	}

	@Override
	public boolean getDefaultFixedSetting()
	{
		return false;
	}
	
	@Override
	public PositionOrigin getDefaultPosOrigin()
	{
		return this.origin;
	}

	@Override
	public PositionOperation getDefaultPosOperation()
	{
		return PositionOperation.DOWN;
	}

	@Override
	public int getDefaultXOffs()
	{
		return 0;
	}

	@Override
	public int getDefaultYOffs()
	{
		return 0;
	}
	
	@Override
	public boolean getDefaultRenderInF3Setting()
	{
		return false;
	}
	
	@Override
	public ConfigOption[] getConfigOptions()
	{
		return null;
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
	public boolean isToBeRendered()
	{
		return true;
	}

	@Override
	public void doPreRenderWork()
	{
	}

	@Override
	public void render(ScaledResolution sr, int mouse_x, int mouse_y, float partial_ticks, PositionInfoXY2 pos)
	{
		FontRenderer fr = Minecraft.getMinecraft().fontRenderer;
		fr.drawStringWithShadow(this.text, pos.left_x, pos.top_y, 0xffffff);
	}
}
