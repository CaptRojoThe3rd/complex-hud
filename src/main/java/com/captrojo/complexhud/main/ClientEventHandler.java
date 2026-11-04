package com.captrojo.complexhud.main;

import com.captrojo.complexhud.config.ModConfig;
import com.captrojo.complexhud.gui.GuiScreenSettingsHead;
import com.captrojo.complexhud.position.PositioningHandler;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.profiler.Profiler;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.client.event.GuiScreenEvent.ActionPerformedEvent;
import net.minecraftforge.client.event.GuiScreenEvent.InitGuiEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.common.ForgeHooks;

public class ClientEventHandler
{
	static ClientEventHandler instance;

	@SubscribeEvent
	public void onClientTick(TickEvent.ClientTickEvent event)
	{
		if (event.phase == Phase.START && !Minecraft.getMinecraft().isGamePaused()) {
			for (RegisteredElement re : HUDElementList.instance.element_list) {
				re.element.updateTick();
			}
		}
	}

	@SubscribeEvent
	public void onRenderGameOverlayPre(RenderGameOverlayEvent.Pre event)
	{
		if (event.type == ElementType.HEALTH) {
			event.setCanceled(ComplexHUD.e_health_bar.override_vanilla);
			return;
		}
		if (event.type == ElementType.ARMOR) {
			event.setCanceled(ComplexHUD.e_armor_bar.override_vanilla);
			return;
		}
		if (event.type == ElementType.FOOD) {
			event.setCanceled(ComplexHUD.e_hunger_bar.override_vanilla);
			return;
		}
		if (event.type == ElementType.AIR) {
			event.setCanceled(ComplexHUD.e_air_bar.override_vanilla);
			return;
		}
		if (event.type == ElementType.HEALTHMOUNT) {
			event.setCanceled(ComplexHUD.e_mount_health_bar.override_vanilla);
			return;
		}

		if (event.type == ElementType.BOSSHEALTH) {
			event.setCanceled(ComplexHUD.e_boss_health.override_vanilla);
			return;
		}
		if (event.type == ElementType.EXPERIENCE) {
			event.setCanceled(ComplexHUD.e_xp_bar.override_vanilla);
			return;
		}
		if (event.type == ElementType.JUMPBAR) {
			event.setCanceled(ComplexHUD.e_jump_bar.override_vanilla);
			return;
		}

		if (event.type == ElementType.CROSSHAIRS) {
			Profiler pf = Minecraft.getMinecraft().mcProfiler;
			pf.startSection("complexhud");

			GuiIngameForge.left_height += ModConfig.left_height_offs_pre.getInt();
			GuiIngameForge.right_height += ModConfig.right_height_offs_pre.getInt();

			if (HUDElementList.instance.needs_sorting) {
				HUDElementList.instance.sort();
			}

			pf.startSection("prerender");
			for (RegisteredElement re : HUDElementList.instance.element_list) {
				re.element.doPreRenderWork();
			}
			pf.endSection();
			
			PositioningHandler.positionElements(HUDElementList.instance.obj_list, event.resolution);

			GuiIngameForge.left_height += ModConfig.left_height_offs_post.getInt();
			GuiIngameForge.right_height += ModConfig.right_height_offs_post.getInt();

			pf.startSection("render");
			for (RegisteredElement re : HUDElementList.instance.element_list) {
				if (!re.to_be_rendered) {
					continue;
				}
				re.element.render(event.resolution, event.mouseX, event.mouseY, event.partialTicks, re.pos);
			}

			pf.endSection();
			pf.endSection();
			return;
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

	@SuppressWarnings("unchecked")
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
