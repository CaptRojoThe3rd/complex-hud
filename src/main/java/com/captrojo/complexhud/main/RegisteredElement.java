package com.captrojo.complexhud.main;

import java.util.List;

import com.captrojo.complexhud.api.IComplexHUDElement;
import com.captrojo.complexhud.api.PositionInfoXY2;
import com.captrojo.complexhud.api.PositionOperation;
import com.captrojo.complexhud.api.PositionOrigin;
import com.captrojo.complexhud.config.ConfigOption;
import com.captrojo.complexhud.config.ConfigOption.Type;
import com.captrojo.complexhud.config.ConfigOptionSection;
import com.captrojo.complexhud.config.ModConfig;

import net.minecraft.client.Minecraft;

public class RegisteredElement implements Comparable<RegisteredElement>
{
	public IComplexHUDElement element;
	public PositionInfoXY2 pos;

	public String unlocalized_name;
	public String key;

	ConfigOption cfg_rndr_prio;
	ConfigOption cfg_fixed;
	ConfigOption cfg_pos_origin;
	ConfigOption cfg_pos_op;
	ConfigOption cfg_offs_x;
	ConfigOption cfg_offs_y;
	ConfigOption cfg_rndr_f3;
	ConfigOptionSection options_sec;
	
	/* Cache these values to avoid unnecessary processing every time we need them */
	public boolean to_be_rendered;
	public int width;
	public int height;
	
	public RegisteredElement(String mod_id, IComplexHUDElement element)
	{
		this.element = element;

		this.unlocalized_name = element.getUnlocalizedName();
		this.key = mod_id + ":" + this.unlocalized_name;
		
		this.cfg_rndr_prio = new ConfigOption(Type.INT, "priority", element.getDefaultPriority());
		this.cfg_fixed = new ConfigOption(Type.BOOLEAN, "fixed", element.getDefaultFixedSetting());
		this.cfg_pos_origin = new ConfigOption(PositionOrigin.values(), "origin", element.getDefaultPosOrigin());
		this.cfg_pos_op = new ConfigOption(PositionOperation.values(), "operation", element.getDefaultPosOperation());
		this.cfg_offs_x = new ConfigOption(Type.INT, "offset_x", element.getDefaultXOffs());
		this.cfg_offs_y = new ConfigOption(Type.INT, "offset_y", element.getDefaultYOffs());
		this.cfg_rndr_f3 = new ConfigOption(Type.BOOLEAN, "render_in_f3", element.getDefaultRenderInF3Setting());
		List<ConfigOption> extra_options = element.getConfigOptions();
		
		this.options_sec = new ConfigOptionSection(this.key, this.unlocalized_name);
		this.options_sec.addAll(
			this.cfg_rndr_prio,
			this.cfg_fixed,
			this.cfg_pos_origin,
			this.cfg_pos_op,
			this.cfg_offs_x,
			this.cfg_offs_y,
			this.cfg_rndr_f3
		);
		if (extra_options != null) {
			for (ConfigOption optn : extra_options) {
				this.options_sec.add(optn);
			}
		}
		
		this.options_sec.loadFromJson(ModConfig.element_config_obj);
		this.options_sec.saveToJson(ModConfig.element_config_obj);
		ModConfig.saveJson();
	}
	
	public void reloadValues1()
	{
		this.to_be_rendered = this.element.isToBeRendered();
		if (Minecraft.getMinecraft().gameSettings.showDebugInfo) {
			this.to_be_rendered &= this.shouldRenderInF3();
		}
	}
	
	public void reloadValues2()
	{
		this.width = this.element.getWidth();
		this.height = this.element.getHeight();
	}
	
	public int getRenderPriority()
	{
		return this.cfg_rndr_prio.getInt();
	}
	
	public boolean isFixed()
	{
		return this.cfg_fixed.getBool();
	}
	
	public PositionOrigin getPosOrigin()
	{
		return this.cfg_pos_origin.getEnum();
	}
	
	public PositionOperation getPosOp()
	{
		return this.cfg_pos_op.getEnum();
	}
	
	public int getXOffs()
	{
		return this.cfg_offs_x.getInt();
	}
	
	public int getYOffs()
	{
		return this.cfg_offs_y.getInt();
	}
	
	public boolean shouldRenderInF3()
	{
		return this.cfg_rndr_f3.getBool();
	}
	
	@Override
	public int compareTo(RegisteredElement o)
	{
		return this.getRenderPriority() - o.getRenderPriority();
	}
}
