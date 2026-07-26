package com.captrojo.complexhud.element;

import org.lwjgl.opengl.GL11;

import com.captrojo.complexhud.api.IConfigEntry;
import com.captrojo.complexhud.api.PositionInfoXY2;
import com.captrojo.complexhud.api.PositionOperation;
import com.captrojo.complexhud.api.PositionOrigin;
import com.captrojo.complexhud.config.ConfigHeading;
import com.captrojo.complexhud.config.ConfigOption;
import com.captrojo.complexhud.config.TextAlignment;
import com.captrojo.complexhud.config.ConfigOption.Type;

import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.util.MathHelper;
import net.minecraftforge.client.GuiIngameForge;

public class ElementBossHealth extends ElementFillBarBase
{
	static final int U_1ST = 0;
	static final int U_2ND = 20;
	static final int U_END = 181;
	
	static final int W_1ST = 20;
	static final int W_2ND = 142;
	static final int W_EDGE = W_1ST + W_2ND;
	
	static final int V_BG = 108;
	static final int V_FG = 113;
	
	ConfigOption cfg_text_alignment;
	ConfigOption cfg_bar_text_spacing;
	
	TextAlignment text_alignment;
	int bar_text_spacing;
	
	int width;
	int height;
	
	int name_str_width;
	
	public ElementBossHealth()
	{
		this.cfg_text_alignment = new ConfigOption(TextAlignment.values(), "text_aligment", TextAlignment.CENTER);
		this.cfg_bar_text_spacing = new ConfigOption(Type.INT, "bar_text_spacing", 2);
	}

	@Override
	public String getUnlocalizedName()
	{
		return "hud.boss_health";
	}

	@Override
	public int getDefaultPriority()
	{
		return 0;
	}

	@Override
	public boolean getDefaultFixedSetting()
	{
		return false;
	}

	@Override
	public PositionOrigin getDefaultPosOrigin()
	{
		return PositionOrigin.TOP_CENTER;
	}

	@Override
	public PositionOperation getDefaultPosOperation()
	{
		return PositionOperation.DOWN;
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
	public IConfigEntry[] getConfigOptions()
	{
		return new IConfigEntry[] {
			new ConfigHeading(null),
			new ConfigHeading(OVRD_CFG_HEADING),
			this.cfg_enabled,
			this.cfg_override_vanilla,
			
			new ConfigHeading(null),
			new ConfigHeading("options.complexhud.boss_health_optns"),
			this.cfg_text_alignment,
			this.cfg_bar_width,
			this.cfg_bar_text_spacing
		};
	}
	
	@Override
	public void onConfigUpdated()
	{
		super.onConfigUpdated();
		
		this.text_alignment = this.cfg_text_alignment.getEnum();
		this.bar_text_spacing = this.cfg_bar_text_spacing.getInt();
		
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
		return GuiIngameForge.renderBossHealth && BossStatus.bossName != null && BossStatus.statusBarTime > 0;
	}

	@Override
	public void doPreRenderWork()
	{
		this.mc.mcProfiler.startSection("boss_health");
		
		this.name_str_width = this.mc.fontRenderer.getStringWidth(BossStatus.bossName);
		this.width = Math.max(this.bar_width, this.name_str_width);
		this.height = this.mc.fontRenderer.FONT_HEIGHT + this.bar_text_spacing + 5;
		
		this.mc.mcProfiler.endSection();
	}

	@Override
	public void render(ScaledResolution sr, int mouse_x, int mouse_y, float partial_ticks, PositionInfoXY2 pos)
	{
		this.mc.mcProfiler.startSection("boss_health");
		GL11.glEnable(GL11.GL_BLEND);
		
		BossStatus.statusBarTime--;
		
		int text_x;
		switch (this.text_alignment) {
		default:
		case LEFT:
			text_x = pos.left_x;
			break;
		case CENTER:
			text_x = pos.left_x + (this.width / 2) - (this.name_str_width / 2);
			break;
		case RIGHT:
			text_x = pos.right_x - this.name_str_width;
			break;
		}
		this.mc.fontRenderer.drawStringWithShadow(BossStatus.bossName, text_x, pos.top_y, 0xffffff);
		
		this.bindModIcons();
		
		int v_bg = V_BG;
		int v_fg = V_FG;
		
		int y_offs = this.mc.fontRenderer.FONT_HEIGHT + this.bar_text_spacing;
		
		this.drawBar(pos, this.bar_width, v_bg, U_1ST, U_2ND, U_END, y_offs);
		int filled_w = MathHelper.ceiling_float_int(BossStatus.healthScale * (float) this.bar_width);
		this.drawBar(pos, filled_w, v_fg, U_1ST, U_2ND, U_END, y_offs);
		
		GL11.glDisable(GL11.GL_BLEND);
		this.mc.mcProfiler.endSection();
	}

}
