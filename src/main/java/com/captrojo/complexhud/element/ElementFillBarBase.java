package com.captrojo.complexhud.element;

import com.captrojo.complexhud.api.IConfigEntry;
import com.captrojo.complexhud.api.PositionInfoXY2;
import com.captrojo.complexhud.api.PositionOperation;
import com.captrojo.complexhud.api.PositionOrigin;
import com.captrojo.complexhud.config.ConfigOption;
import com.captrojo.complexhud.config.ConfigOption.Type;

import net.minecraft.client.gui.ScaledResolution;

public abstract class ElementFillBarBase extends OverriddenVanillaElement
{
	ConfigOption cfg_bar_width;
	
	int bar_width;
	
	int[] bar_sec_widths;
	int last_sec;
	
	public ElementFillBarBase()
	{
		this.cfg_bar_width = new ConfigOption(Type.INT, "bar_width", 182);
	}
	
	void drawBar(PositionInfoXY2 pos, int width, int v, int u_1st, int u_2nd, int u_end, int y_offs)
	{
		int dw = width;
		int x1 = 0;
		int l = this.last_sec + 1;
		int y = pos.top_y + y_offs;
		for (int i = 0; i < l; i++) {
			int x = pos.left_x + x1;
			int w = this.bar_sec_widths[i];
			if (w == 0) {
				break;
			}
			int u;
			if (i == 0) {
				u = u_1st;
			} else if (i == this.last_sec) {
				u = u_end - w + 1;
			} else {
				u = u_2nd;
			}
			if (w > dw) {
				w = dw;
			}
			dw -= w;
			x1 += w;
			this.drawTexturedModalRect(x, y, u, v, w, 5);
		}
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
		
		this.bar_width = this.cfg_bar_width.getInt();
	}
}
