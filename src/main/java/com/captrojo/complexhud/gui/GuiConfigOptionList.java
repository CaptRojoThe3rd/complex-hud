package com.captrojo.complexhud.gui;

import java.util.ArrayList;

import com.captrojo.complexhud.main.I18nHlpr;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Tessellator;

public class GuiConfigOptionList extends GuiScrollingList2
{	
	ArrayList<GuiConfigOption> option_list;
	
	public GuiConfigOptionList(int x, int y, ArrayList<GuiConfigOption> option_list)
	{
		super(Minecraft.getMinecraft(), 266, 222, y + 3, y + 225, x + 4, 20);
		this.option_list = option_list;
		
		this.scrollbar_color_bg = 0x404040;
		this.highlight_selected_element = false;
		this.draw_edge_gradient = false;
		this.draw_gradient_rect = false;
		this.draw_list_background = false;
		
		this.selected_index = -1;
	}
	
	void updateScreen()
	{
		for (GuiConfigOption gui : this.option_list) {
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
		GuiConfigOption optn = this.option_list.get(idx);
		optn.draw(this.left_x + 136, element_y, this.mouse_x, this.mouse_y);
		
		FontRenderer fr = this.mc.fontRenderer;
		String str = optn.cfg_optn.getName();
		fr.drawStringWithShadow(str, this.left_x, element_y + 4, 0xffffff);
	}
}
