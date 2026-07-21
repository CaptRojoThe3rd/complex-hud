package com.captrojo.complexhud.main;

import com.captrojo.complexhud.api.PositionInfoXY2;
import com.captrojo.complexhud.config.ModConfig;
import com.captrojo.complexhud.gui.GuiScreenSettingsHead;
import com.captrojo.complexhud.position.PositionerBase;
import com.captrojo.complexhud.position.PositionerBottomCenter;
import com.captrojo.complexhud.position.PositionerBottomLeft;
import com.captrojo.complexhud.position.PositionerBottomRight;
import com.captrojo.complexhud.position.PositionerHotbarSideLeft;
import com.captrojo.complexhud.position.PositionerHotbarSideRight;
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
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.client.event.GuiScreenEvent.ActionPerformedEvent;
import net.minecraftforge.client.event.GuiScreenEvent.InitGuiEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;

public class ClientEventHandler
{
	static PositionerBase[] apos = new PositionerBase[] {
		new PositionerHotbarSideLeft(),
		new PositionerHotbarSideRight(),
		
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
	
	@SubscribeEvent
	public void onRenderGameOverlayPre(RenderGameOverlayEvent.Pre event)
	{
		if (event.type != ElementType.ALL) { 
			return;
		}
		if (HUDElementList.needs_sorting) {
			HUDElementList.sort();
		}
	}

	@SubscribeEvent
	public void onRenderGameOverlayPost(RenderGameOverlayEvent.Post event)
	{
		if (event.type != ElementType.ALL) { 
			return;
		}
		
		PositionerBase.reset(event.resolution);
		for (PositionerBase pb : apos) {
			if (pb != null) {
				pb.reset();
			}
		}
		
		for (RegisteredElement re : HUDElementList.element_list) {
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
	}
	
	@SubscribeEvent
	public void onGuiActionPre(ActionPerformedEvent.Pre event)
	{
		if (!(event.gui instanceof GuiOptions)) {
			return;
		}
		
		if (event.button.id == ModConfig.optn_id) {
			GuiScreen settings_head = new GuiScreenSettingsHead(event.gui);
			Minecraft.getMinecraft().displayGuiScreen(settings_head);
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
				b.yPosition += ModConfig.done_y_offs;
				break;
			}
		}

		if (!ModConfig.optn_enabled) {
			return;
		}
		GuiButton button = new GuiButton(
			ModConfig.optn_id,
			event.gui.width / 2 + ModConfig.optn_x_offs,
			event.gui.height / 6 + ModConfig.optn_y_offs,
			150,
			20,
			I18nHlpr.get("options.complex_hud_settings")
		);
		event.buttonList.add(button);
	}
}
