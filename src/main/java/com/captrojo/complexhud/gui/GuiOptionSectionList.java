package com.captrojo.complexhud.gui;

import java.util.ArrayList;

import com.captrojo.complexhud.config.ConfigOptionSection;
import com.captrojo.complexhud.main.I18nHlpr;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Tessellator;

public class GuiOptionSectionList extends GuiScrollingList2
{
	GuiSectionedOptions gui;
	ArrayList<ConfigOptionSection> option_sections;
	
	public GuiOptionSectionList(int x, int y, GuiSectionedOptions gui, ArrayList<ConfigOptionSection> option_sections)
	{
		super(Minecraft.getMinecraft(), 102, 222, y + 3, y + 225, x + 4, 20);
		this.gui = gui;
		this.option_sections = option_sections;

		this.scrollbar_color_bg = 0x404040;
		this.draw_edge_gradient = false;
		this.draw_gradient_rect = false;
		this.draw_list_background = false;
		
		this.selected_index = -1;
	}

	@Override
	protected int getSize()
	{
		return this.option_sections.size();
	}

	@Override
	protected void elementClicked(int idx, boolean double_click)
	{
		this.selected_index = idx;
		this.gui.setSelectedSection(idx);
	}

	@Override
	protected boolean isSelected(int idx)
	{
		return idx == this.selected_index;
	}

	@Override
	protected void drawBackground()
	{	
	}

	@Override
	protected void drawSlot(int idx, int element_right_x, int element_y, int element_h, Tessellator ts)
	{
		FontRenderer fr = Minecraft.getMinecraft().fontRenderer;
		
		int ylt = this.top_y + 4;
		int ylb = this.bottom_y - 4;
		int ey1 = element_y + fr.FONT_HEIGHT - 1;
		if (ey1 < ylt) {
			return;
		}
		if ((ey1) > ylb) {
			return;
		}
		
		ConfigOptionSection optn_sec = this.option_sections.get(idx);
		String s = optn_sec.getName();
		
		int font_color = this.isSelected(idx) ? 0xc0c0ff : 0xffffff;
		fr.drawStringWithShadow(s, this.left_x + 4, element_y + 4, font_color);
	}
}
