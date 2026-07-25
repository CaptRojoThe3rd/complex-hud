package com.captrojo.complexhud.element;

import java.util.Random;

import com.captrojo.complexhud.api.IComplexHUDElement;
import com.captrojo.complexhud.config.ConfigOption;
import com.captrojo.complexhud.config.ConfigOption.Type;
import com.captrojo.complexhud.main.ComplexHUD;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;

public abstract class OverriddenVanillaElement extends Gui implements IComplexHUDElement
{
	static final ResourceLocation MOD_ICONS = ComplexHUD.resource("textures/gui/icons.png");
	static final String OVRD_CFG_HEADING = "options.complexhud.overridden_element_optns";
	static final float[] COLOR_WHITE = {1.0f, 1.0f, 1.0f};
	
	Minecraft mc;
	Random rand;
	
	ConfigOption cfg_enabled;
	ConfigOption cfg_override_vanilla;
	
	public boolean enabled;
	public boolean override_vanilla;
	
	int update_counter;
	
	OverriddenVanillaElement()
	{
		this.mc = Minecraft.getMinecraft();
		this.rand = new Random();
		
		this.cfg_enabled = new ConfigOption(Type.BOOLEAN, "enabled", true);
		this.cfg_override_vanilla = new ConfigOption(Type.BOOLEAN, "override_vanilla", true);
	}

	void bindVanillaIcons()
	{
		this.mc.getTextureManager().bindTexture(icons);
	}
	
	void bindModIcons()
	{
		this.mc.getTextureManager().bindTexture(MOD_ICONS);
	}
	
	float[][] parseColorListStr(String str)
	{
		String[] indvs = str.split(",");
		float[][] colors = new float[indvs.length][];
		for (int i = 0; i < colors.length; i++) {
			try {
				int color = Integer.valueOf(indvs[i], 16);
				float r = (float) ((color >> 16) & 0xff) / 256.0f;
				float g = (float) ((color >> 8) & 0xff) / 256.0f;
				float b = (float) (color & 0xff) / 256.0f;
				colors[i] = new float[] {r, g, b};
			} catch (NumberFormatException e) {
				return new float[0][];
			}
		}
		return colors;
	}
	
	void drawTexturedModalRectCF(int x, int y, int u, int v, int w, int h, boolean horz, boolean vert)
	{
		if (horz) {
			this.drawTexturedModalRectFlippedHorz(x, y, u, v, w, h);
		} else {
			this.drawTexturedModalRect(x, y, u, v, w, h);
		}
	}

	void drawTexturedModalRectFlippedHorz(int x, int y, int u, int v, int w, int h)
	{
		float uf = 0.00390625f;
		float vf = 0.00390625f;
		Tessellator ts = Tessellator.instance;
		ts.startDrawingQuads();
		ts.addVertexWithUV((double) (x + 0), (double) (y + h), (double) this.zLevel, (double) ((float) (u + w) * uf), (double) ((float) (v + h) * vf));
		ts.addVertexWithUV((double) (x + w), (double) (y + h), (double) this.zLevel, (double) ((float) (u + 0) * uf), (double) ((float) (v + h) * vf));
		ts.addVertexWithUV((double) (x + w), (double) (y + 0), (double) this.zLevel, (double) ((float) (u + 0) * uf), (double) ((float) (v + 0) * vf));
		ts.addVertexWithUV((double) (x + 0), (double) (y + 0), (double) this.zLevel, (double) ((float) (u + w) * uf), (double) ((float) (v + 0) * vf));
		ts.draw();
	}
	
	@Override
	public void onConfigUpdated()
	{
		this.enabled = this.cfg_enabled.getBool();
		this.override_vanilla = this.cfg_override_vanilla.getBool();
	}

	@Override
	public void updateTick()
	{
		this.update_counter++;
	}
}
