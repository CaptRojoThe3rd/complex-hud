package com.captrojo.complexhud.element;

import org.lwjgl.opengl.GL11;

import com.captrojo.complexhud.api.IConfigEntry;
import com.captrojo.complexhud.api.PositionInfoXY2;
import com.captrojo.complexhud.api.PositionOperation;
import com.captrojo.complexhud.api.PositionOrigin;
import com.captrojo.complexhud.config.ConfigHeading;
import com.captrojo.complexhud.config.ConfigOption;
import com.captrojo.complexhud.config.ConfigOption.Type;
import com.captrojo.complexhud.config.TextAlignment;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.MathHelper;
import net.minecraftforge.client.GuiIngameForge;

public class ElementXPBar extends ElementFillBarBase
{
	static final int U_1ST = 0;
	static final int U_2ND = 20;
	static final int U_END = 181;

	static final int W_1ST = 20;
	static final int W_2ND = 142;
	static final int W_EDGE = W_1ST + W_2ND;

	static final int V_BG = 88;
	static final int V_FG = 93;

	ConfigOption cfg_text_alignment;
	ConfigOption cfg_bar_text_spacing;
	ConfigOption cfg_hide_for_jump_bar;

	TextAlignment text_alignment;
	int bar_text_spacing;
	boolean hide_for_jump_bar;

	int width;
	int height;

	public ElementXPBar()
	{
		this.cfg_text_alignment = new ConfigOption(TextAlignment.values(), "text_alignment", TextAlignment.CENTER);
		this.cfg_bar_text_spacing = new ConfigOption(Type.INT, "bar_text_spacing", -3);
		this.cfg_hide_for_jump_bar = new ConfigOption(Type.BOOLEAN, "hide_for_jump_bar", true);
	}

	@Override
	public String getUnlocalizedName()
	{
		return "hud.xp_bar";
	}

	@Override
	public int getDefaultPriority()
	{
		return 0;
	}

	@Override
	public PositionOrigin getDefaultPosOrigin()
	{
		return PositionOrigin.HOTBAR_TOP_BOTH;
	}

	@Override
	public PositionOperation getDefaultPosOperation()
	{
		return PositionOperation.DOWN;
	}

	@Override
	public int getDefaultBufferTopSize()
	{
		return 1;
	}

	@Override
	public int getDefaultBufferBottomSize()
	{
		return 1;
	}

	@Override
	public IConfigEntry[] getConfigOptions()
	{
		return new IConfigEntry[] {
			new ConfigHeading(null),
			new ConfigHeading(OVRD_CFG_HEADING),
			this.cfg_enabled,
			this.cfg_override_vanilla,

			new ConfigHeading(null),
			new ConfigHeading("options.complexhud.xp_bar_optns"),
			this.cfg_bar_width,
			this.cfg_text_alignment,
			this.cfg_bar_text_spacing,
			this.cfg_hide_for_jump_bar
		};
	}

	@Override
	public void onConfigUpdated()
	{
		super.onConfigUpdated();

		this.text_alignment = this.cfg_text_alignment.getEnum();
		this.bar_text_spacing = this.cfg_bar_text_spacing.getInt();
		this.hide_for_jump_bar = this.cfg_hide_for_jump_bar.getBool();
		
		int s = this.bar_width / W_2ND + 3;
		this.bar_sec_widths = new int[s];
		this.bar_sec_widths[0] = W_1ST;
		int px = this.bar_width - W_1ST;
		for (int i = 1; i < s; i++) {
			if (px < W_EDGE) {
				this.bar_sec_widths[i] = px;
				this.last_sec = i;
				break;
			}
			this.bar_sec_widths[i] = W_2ND;
			px -= W_2ND;
		}
	}

	@Override
	public int getWidth()
	{
		return this.width;
	}

	@Override
	public int getHeight()
	{
		return this.height;
	}

	@Override
	public boolean isToBeRendered()
	{
		if (GuiIngameForge.renderJumpBar && this.hide_for_jump_bar) {
			return false;
		}
		if (!this.mc.playerController.gameIsSurvivalOrAdventure()) {
			return false;
		}
		return GuiIngameForge.renderExperiance && this.enabled;
	}

	@Override
	public void doPreRenderWork()
	{
		this.mc.mcProfiler.startSection("xp_bar");
		
		this.width = this.bar_width;
		this.height = 5;

		this.mc.mcProfiler.endSection();
	}

	@Override
	public void render(ScaledResolution sr, int mouse_x, int mouse_y, float partial_ticks, PositionInfoXY2 pos)
	{
		this.mc.mcProfiler.startSection("xp_bar");
		GL11.glDisable(GL11.GL_BLEND);
		this.bindModIcons();

		float cap = (float) this.mc.thePlayer.xpBarCap();
		float xp = this.mc.thePlayer.experience;
		int filled_w = MathHelper.ceiling_float_int(xp * (float) this.bar_width);

		int v_bg = V_BG;
		int v_fg = V_FG;

		this.drawBar(pos, this.bar_width, v_bg, U_1ST, U_2ND, U_END, 0);
		this.drawBar(pos, filled_w, v_fg, U_1ST, U_2ND, U_END, 0);
		
		if (this.mc.thePlayer.experienceLevel > 0) {
			FontRenderer fr = this.mc.fontRenderer;
			String str = Integer.toString(this.mc.thePlayer.experienceLevel);
			int str_width = fr.getStringWidth(str);
			
			int text_x;
			switch (this.text_alignment) {
			default:
			case LEFT:
				text_x = pos.left_x;
				break;
			case CENTER:
				text_x = pos.left_x + (this.width / 2) - (str_width / 2);
				break;
			case RIGHT:
				text_x = pos.right_x - str_width;
				break;
			}
			int text_y = pos.top_y - fr.FONT_HEIGHT - this.bar_text_spacing;
			
			fr.drawString(str, text_x + 1, text_y, 0x000000);
			fr.drawString(str, text_x - 1, text_y, 0x000000);
			fr.drawString(str, text_x, text_y + 1, 0x000000);
			fr.drawString(str, text_x, text_y - 1, 0x000000);
			fr.drawString(str, text_x, text_y, 0x80ff20);
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		}

		GL11.glEnable(GL11.GL_BLEND);
		this.mc.mcProfiler.endSection();
	}

}
