package com.captrojo.complexhud.element;

import java.util.Random;

import com.captrojo.complexhud.api.IComplexHUDElement;
import com.captrojo.complexhud.main.ComplexHUD;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;

public abstract class OverriddenVanillaElement extends Gui implements IComplexHUDElement
{
	static final ResourceLocation MOD_ICONS = ComplexHUD.resource("textures/gui/icons.png");
	
	Minecraft mc;
	Random rand;
	
	int update_counter;
	
	OverriddenVanillaElement()
	{
		this.mc = Minecraft.getMinecraft();
		this.rand = new Random();
	}

	void bindVanillaIcons()
	{
		this.mc.getTextureManager().bindTexture(icons);
	}
	
	void bindModIcons()
	{
		this.mc.getTextureManager().bindTexture(MOD_ICONS);
	}

	@Override
	public void updateTick()
	{
		this.update_counter++;
	}
}
