package com.captrojo.complexhud.element;

import com.captrojo.complexhud.config.ConfigOption;
import com.captrojo.complexhud.config.ConfigOption.Type;

public abstract class ElementHealthBarBase extends OverriddenVanillaElement
{
	ConfigOption cfg_quarter_hearts;
	ConfigOption cfg_top_to_bottom;
	ConfigOption cfg_right_to_left;
	ConfigOption cfg_hearts_per_row;
	ConfigOption cfg_low_health_point;
	ConfigOption cfg_compress_rows;
	ConfigOption cfg_min_row_spacing;
	ConfigOption cfg_row_spacing_addend;
	ConfigOption cfg_heart_spacing;
	
	ConfigOption cfg_overloaded_hearts;
	ConfigOption cfg_hearts_per_load;
	ConfigOption cfg_overload_colors;
	
	boolean quarter_hearts;
	boolean top_to_bottom;
	boolean right_to_left;
	int hearts_per_row;
	int low_health_point;
	boolean compress_rows;
	int min_row_spacing;
	int row_spacing_addend;
	int heart_spacing;
	
	boolean overloaded_hearts;
	int hearts_per_load;
	float[][] overload_colors;
	
	public ElementHealthBarBase()
	{
		this.cfg_quarter_hearts = new ConfigOption(Type.BOOLEAN, "quarter_hearts", true);
		this.cfg_top_to_bottom = new ConfigOption(Type.BOOLEAN, "top_to_bottom", false);
		this.cfg_right_to_left = new ConfigOption(Type.BOOLEAN, "right_to_left", false);
		this.cfg_hearts_per_row = new ConfigOption(Type.INT, "hearts_per_row", 10);
		this.cfg_low_health_point = new ConfigOption(Type.DOUBLE, "low_health_point", 2.0);
		this.cfg_compress_rows = new ConfigOption(Type.BOOLEAN, "compress_rows", true);
		this.cfg_min_row_spacing = new ConfigOption(Type.INT, "min_row_spacing", 3);
		this.cfg_row_spacing_addend = new ConfigOption(Type.INT, "row_spacing", 0);
		this.cfg_heart_spacing = new ConfigOption(Type.INT, "heart_spacing", -1);
		
		this.cfg_overloaded_hearts = new ConfigOption(Type.BOOLEAN, "overloaded_hearts", false);
		this.cfg_hearts_per_load = new ConfigOption(Type.INT, "hearts_per_load", 10);
		this.cfg_overload_colors = new ConfigOption(Type.STRING, "overload_colors", "60ff00,00ff80,00c0ff");
	}

	@Override
	public boolean getDefaultFixedSetting()
	{
		return false;
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
	public int getDefaultBufferTopSize()
	{
		return 0;
	}

	@Override
	public int getDefaultBufferBottomSize()
	{
		return 0;
	}

	@Override
	public int getDefaultBufferLeftSize()
	{
		return 0;
	}

	@Override
	public int getDefaultBufferRightSize()
	{
		return 0;
	}

	@Override
	public boolean getDefaultRenderInF3Setting()
	{
		return true;
	}
	
	@Override
	public void onConfigUpdated()
	{
		super.onConfigUpdated();

		this.quarter_hearts = this.cfg_quarter_hearts.getBool();
		this.top_to_bottom = this.cfg_top_to_bottom.getBool();
		this.right_to_left = this.cfg_right_to_left.getBool();
		this.hearts_per_row = this.cfg_hearts_per_row.getInt();
		this.low_health_point = (int) (this.cfg_low_health_point.getDouble() * 4.0);
		this.compress_rows = this.cfg_compress_rows.getBool();
		this.min_row_spacing = this.cfg_min_row_spacing.getInt();
		this.row_spacing_addend = this.cfg_row_spacing_addend.getInt();
		this.heart_spacing = this.cfg_heart_spacing.getInt();
		
		this.overloaded_hearts = this.cfg_overloaded_hearts.getBool();
		this.hearts_per_load = this.cfg_hearts_per_load.getInt();
		this.overload_colors = this.parseColorListStr(this.cfg_overload_colors.getString());
		
		this.cfg_min_row_spacing.setEnabled(this.compress_rows);
		
		this.cfg_hearts_per_load.setEnabled(this.overloaded_hearts);
		this.cfg_overload_colors.setEnabled(this.overloaded_hearts);
		this.cfg_overload_colors.setInvalid(this.overload_colors.length == 0);
	}
}
