package com.captrojo.complexhud.main;

import java.util.ArrayList;

import com.captrojo.complexhud.api.PositionInfoXY2;
import com.captrojo.complexhud.config.ConfigOptionSection;
import com.captrojo.complexhud.config.ModConfig;
import com.captrojo.complexhud.gui.GuiScreenSettingsHead;
import com.captrojo.complexhud.position.PositionerBase;
import com.captrojo.complexhud.position.PositionerBottomCenter;
import com.captrojo.complexhud.position.PositionerBottomLeft;
import com.captrojo.complexhud.position.PositionerBottomRight;
import com.captrojo.complexhud.position.PositionerHotbarSideLeft;
import com.captrojo.complexhud.position.PositionerHotbarSideRight;
import com.captrojo.complexhud.position.PositionerHotbarTopLeft;
import com.captrojo.complexhud.position.PositionerHotbarTopRight;
import com.captrojo.complexhud.position.PositionerMiddleCenter;
import com.captrojo.complexhud.position.PositionerMiddleLeft;
import com.captrojo.complexhud.position.PositionerMiddleRight;
import com.captrojo.complexhud.position.PositionerTopCenter;
import com.captrojo.complexhud.position.PositionerTopLeft;
import com.captrojo.complexhud.position.PositionerTopRight;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.client.event.GuiScreenEvent.ActionPerformedEvent;
import net.minecraftforge.client.event.GuiScreenEvent.InitGuiEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.common.ForgeHooks;

public class ClientEventHandler
{
	static PositionerBase[] apos = new PositionerBase[] {
		new PositionerHotbarSideLeft(),
		new PositionerHotbarSideRight(),
		
		new PositionerHotbarTopLeft(),
		new PositionerHotbarTopRight(),
		
		new PositionerTopLeft(),
		new PositionerTopCenter(),
		new PositionerTopRight(),

		new PositionerMiddleLeft(),
		new PositionerMiddleCenter(),
		new PositionerMiddleRight(),

		new PositionerBottomLeft(),
		new PositionerBottomCenter(),
		new PositionerBottomRight()
	};
	
	public static ArrayList<ConfigOptionSection> getAllPosSettings()
	{
		ArrayList<ConfigOptionSection> list = new ArrayList<ConfigOptionSection>();
		for (PositionerBase pb : apos) {
			list.add(pb.options_sec);
		}
		return list;
	}
	
	@SubscribeEvent
	public void onRenderGameOverlayPre(RenderGameOverlayEvent.Pre event)
	{
		if (event.type == ElementType.ALL) { 
			if (HUDElementList.needs_sorting) {
				HUDElementList.sort();
			}
		}
	}

	@SubscribeEvent
	public void onRenderGameOverlayPost(RenderGameOverlayEvent.Post event)
	{
		if (event.type == ElementType.ARMOR) {
			if (ModConfig.armor_bar_y_fix.getBool()) {
				EntityPlayer player = Minecraft.getMinecraft().thePlayer;
				if (ForgeHooks.getTotalArmorValue(player) == 0) {
					GuiIngameForge.left_height -= 10;
				}
			}
			return;
		}
		
		if (event.type == ElementType.ALL) {
			PositionerBase.reset(event.resolution);
			for (PositionerBase pb : apos) {
				if (pb != null) {
					pb.reset();
				}
			}
			
			for (RegisteredElement re : HUDElementList.element_list) {
				re.element.doPreRenderWork();
				re.reloadValues1();
				if (!re.to_be_rendered) {
					continue;
				}
				re.reloadValues2();
				if (re.isFixed()) {
					re.pos = new PositionInfoXY2(0, re.width - 1, 0, re.height - 1);
					continue;
				}
				int i = re.getPosOrigin().ordinal();
				apos[i].addToPosCalc(re);
			}
			
			for (PositionerBase pb : apos) {
				if (pb != null) {
					pb.positionSections();
				}
			}
			
			for (RegisteredElement re : HUDElementList.element_list) {
				if (!re.to_be_rendered) {
					continue;
				}
				int i = re.getPosOrigin().ordinal();
				apos[i].positionElement(re);
				re.element.render(event.resolution, event.mouseX, event.mouseY, event.partialTicks, re.pos);
			}
			return;
		}
	}
	
	@SubscribeEvent
	public void onGuiActionPre(ActionPerformedEvent.Pre event)
	{
		if (!(event.gui instanceof GuiOptions)) {
			return;
		}
		
		if (event.button.id == ModConfig.optn_id.getInt()) {
			Minecraft.getMinecraft().displayGuiScreen(new GuiScreenSettingsHead(event.gui));
		}
	}
	
	@SubscribeEvent
	public void onPostGuiInit(InitGuiEvent.Post event)
	{
		if (!(event.gui instanceof GuiOptions)) {
			return;
		}
		
		for (Object o : event.buttonList) {
			GuiButton b = (GuiButton) o;
			if (b.id == 200) {
				b.yPosition += ModConfig.done_y_offs.getInt();
				break;
			}
		}

		if (!ModConfig.optn_enabled.getBool()) {
			return;
		}
		GuiButton button = new GuiButton(
			ModConfig.optn_id.getInt(),
			event.gui.width / 2 + ModConfig.optn_x_offs.getInt(),
			event.gui.height / 6 + ModConfig.optn_y_offs.getInt(),
			150,
			20,
			I18nHlpr.get("options.complex_hud")
		);
		event.buttonList.add(button);
	}
}
