package com.captrojo.complexhud.gui;

import java.util.ArrayList;

import com.captrojo.complexhud.main.I18nHlpr;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Tessellator;

public class GuiConfigOptionList extends GuiScrollingList2
{
	ArrayList<GuiConfigEntry> option_list;

	public GuiConfigOptionList(int x, int y, ArrayList<GuiConfigEntry> option_list)
	{
		super(Minecraft.getMinecraft(), 263, 222, y + 3, y + 225, x + 4, 20);
		this.option_list = option_list;

		this.scrollbar_color_bg = 0x404040;
		this.highlight_selected_element = false;
		this.draw_edge_gradient = false;
		this.draw_gradient_rect = false;
		this.draw_list_background = false;

		this.selected_index = -1;
		this.scroll_distance += 2;
	}

	void updateScreen()
	{
		for (GuiConfigEntry gui : this.option_list) {
			gui.updateScreen();
		}
	}

	@Override
	protected int getSize()
	{
		return this.option_list.size();
	}

	@Override
	protected void elementClicked(int idx, boolean double_click)
	{
	}

	@Override
	protected boolean isSelected(int idx)
	{
		return this.selected_index == idx;
	}

	@Override
	protected void drawBackground()
	{
	}

	@Override
	protected void drawSlot(int idx, int element_right_x, int element_y, int element_h, Tessellator ts)
	{
		int ylt = this.top_y;
		int ylb = this.bottom_y - 18;
		if (element_y < ylt || element_y > ylb) {
			return;
		}

		GuiConfigEntry entry = this.option_list.get(idx);
		entry.draw(this.left_x, element_y, this.mouse_x, this.mouse_y);
	}
}
