package com.captrojo.complexhud.gui;

import com.captrojo.complexhud.config.ModConfig;
import com.captrojo.complexhud.main.ClientEventHandler;
import com.captrojo.complexhud.main.HUDElementList;
import com.captrojo.complexhud.main.I18nHlpr;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class GuiScreenSettingsHead extends GuiScreen
{
	private static final int BTN_DONE = 0;
	private static final int BTN_POS_OPTNS = 1;
	private static final int BTN_ELEMENT_OPTNS = 2;
	
	private GuiScreen parent_screen;
	private String title;

	public GuiScreenSettingsHead(GuiScreen parent)
	{
		this.parent_screen = parent;
		this.title = I18nHlpr.get("options.complex_hud");
	}

	@Override
	public void initGui()
	{
		super.initGui();
		
		this.buttonList.add(new GuiButton(
			BTN_POS_OPTNS,
			this.width / 2 - 75,
			this.height / 6 + 72,
			150, 20,
			I18nHlpr.get("options.complexhud.pos_optns")
		));
		this.buttonList.add(new GuiButton(
			BTN_ELEMENT_OPTNS,
			this.width / 2 - 75,
			this.height / 6 + 96,
			150, 20,
			I18nHlpr.get("options.complexhud.element_optns")
		));
		
		this.buttonList.add(new GuiButton(
			BTN_DONE,
			this.width / 2 - 75,
			this.height / 6 + 130,
			150, 20,
			I18nHlpr.get("gui.done")
		));
	}
	
	@Override
	protected void actionPerformed(GuiButton button)
	{
		if (button.id == BTN_DONE) {
			this.mc.displayGuiScreen(this.parent_screen);
		} else if (button.id == BTN_POS_OPTNS) {
			this.mc.displayGuiScreen(new GuiSectionedOptions(this, ModConfig.pos_config_obj, ClientEventHandler.getAllPosSettings()));
		} else if (button.id == BTN_ELEMENT_OPTNS) {
			this.mc.displayGuiScreen(new GuiSectionedOptions(this, ModConfig.element_config_obj, HUDElementList.getAllOptions()));
		}
	}

	@Override
	public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_)
	{
		this.drawDefaultBackground();
		this.drawCenteredString(this.fontRendererObj, this.title, this.width / 2, 15, 16777215);
		super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
	}
}
