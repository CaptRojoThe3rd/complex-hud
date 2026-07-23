package com.captrojo.complexhud.gui;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import com.captrojo.complexhud.config.ConfigSection;
import com.captrojo.complexhud.config.ModConfig;
import com.captrojo.complexhud.main.ComplexHUD;
import com.captrojo.complexhud.main.I18nHlpr;
import com.google.gson.JsonObject;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

public class GuiSectionedOptions extends GuiScreen
{
	static final ResourceLocation BG_TEXTURE = ComplexHUD.resource("textures/gui/sectioned_menu.png");

	static final int BTN_DONE = 0;
	static final int BTN_APPLY = 1;
	static final int BTN_CANCEL = 2;
	static final int BTN_UNDO = 3;
	static final int BTN_TEST = 4;

	int x;
	int y;

	GuiScreen parent_screen;
	
	GuiOptionSectionList section_list;
	GuiConfigOptionList option_list;

	JsonObject root_obj;
	ArrayList<ConfigSection> option_sections;
	int selected_idx;
	
	boolean show_hud_button;

	public GuiSectionedOptions(
		GuiScreen parent,
		JsonObject root_obj,
		ArrayList<ConfigSection> option_sections,
		boolean show_hud_button
	)
	{
		this.parent_screen = parent;
		
		this.root_obj = root_obj;
		this.option_sections = option_sections;
		this.selected_idx = -1;
		
		this.show_hud_button = show_hud_button && Minecraft.getMinecraft().theWorld != null;
	}
	
	void setSelectedSection(int idx)
	{
		if (idx < 0) {
			return;
		}
		ConfigSection sec = this.option_sections.get(idx);
		this.option_list = new GuiConfigOptionList(this.x + 113, this.y, GuiConfigEntry.createFrom(sec));
		this.section_list.selected_index = idx;
		this.selected_idx = idx;
	}
	
	void loadSettings()
	{
		for (ConfigSection optn_sec : this.option_sections) {
			optn_sec.loadFromJson(this.root_obj);
		}
	}
	
	void saveSettings()
	{
		for (ConfigSection optn_sec : this.option_sections) {
			optn_sec.saveToJson(this.root_obj);
		}
		ModConfig.save();
	}
	
	@Override
	public void updateScreen()
	{
		if (this.option_list != null) {
			this.option_list.updateScreen();
		}
	}

	@Override
	public void initGui()
	{
		this.x = this.width / 2 - 192;
		this.y = this.height / 2 - 128;
		
		this.section_list = new GuiOptionSectionList(this.x, this.y, this, this.option_sections);
		
		this.buttonList.add(new GuiButton(
			BTN_DONE,
			this.x + 380 - 70,
			this.y + 232,
			70,
			20,
			I18nHlpr.get("gui.done")
		));
		this.buttonList.add(new GuiButton(
			BTN_APPLY,
			this.x + 380 - 142,
			this.y + 232,
			70,
			20,
			I18nHlpr.get("gui.apply")
		));
		this.buttonList.add(new GuiButton(
			BTN_CANCEL,
			this.x + 380 - 214,
			this.y + 232,
			70,
			20,
			I18nHlpr.get("gui.cancel")
		));
		this.buttonList.add(new GuiButton(
			BTN_UNDO,
			this.x + 380 - 286,
			this.y + 232,
			70,
			20,
			I18nHlpr.get("gui.undo")
		));
		
		if (this.show_hud_button) {
			this.buttonList.add(new GuiButton(
				BTN_TEST,
				this.x + 4,
				this.y + 232,
				70,
				20,
				I18nHlpr.get("gui.show_hud")
			));
		}
	}
	
	@Override
	protected void keyTyped(char c, int n)
	{
		super.keyTyped(c, n);
		
		if (this.option_list != null) {
			for (GuiConfigEntry optn : this.option_list.option_list) {
				optn.keyTyped(c, n);
			}
		}
	}
	
	@Override
	protected void mouseClicked(int mouse_x, int mouse_y, int button)
	{
		super.mouseClicked(mouse_x, mouse_y, button);
		
		if (this.option_list != null) {
			for (GuiConfigEntry optn : this.option_list.option_list) {
				optn.mouseClicked(mouse_x, mouse_y);
			}
		}
	}

	@Override
	protected void actionPerformed(GuiButton button)
	{
		if (button.id == BTN_DONE) {
			this.saveSettings();
			this.mc.displayGuiScreen(this.parent_screen);
		} else if (button.id == BTN_APPLY) {
			this.saveSettings();
		} else if (button.id == BTN_CANCEL) {
			this.loadSettings();
			this.mc.displayGuiScreen(this.parent_screen);
		} else if (button.id == BTN_UNDO) {
			this.loadSettings();
			this.mc.displayGuiScreen(this);
			this.setSelectedSection(this.selected_idx);
		} else if (button.id == BTN_TEST) {
			this.mc.displayGuiScreen(new GuiScreenEmpty(this));
		}
	}

	@Override
	public void drawScreen(int mouse_x, int mouse_y, float partial_ticks)
	{
		this.drawDefaultBackground();

		this.section_list.drawScreen(mouse_x, mouse_y, partial_ticks);
		if (this.option_list != null) {
			this.option_list.drawScreen(mouse_x, mouse_y, partial_ticks);
		}
		
		for (int i = 0; i < this.buttonList.size(); i++) {
			GuiButton button = (GuiButton) this.buttonList.get(i);
			button.drawButton(this.mc, mouse_x, mouse_y);
		}
	}

	@Override
	public void drawDefaultBackground()
	{
		super.drawDefaultBackground();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		this.mc.getTextureManager().bindTexture(BG_TEXTURE);
		func_146110_a(this.x, this.y, 0, 0, 384, 256, 384f, 256f);
	}
}
