package com.captrojo.complexhud.main;

import org.lwjgl.input.Keyboard;

import com.captrojo.complexhud.gui.GuiScreenSettingsHead;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;

public class ModKeyInput
{
	static KeyBinding k_open_settings = new KeyBinding("Complex HUD config", Keyboard.KEY_H, "key.categories.misc");
	
	static void registerKeybinds()
	{
		ClientRegistry.registerKeyBinding(k_open_settings);
	}
	
	@SubscribeEvent
	public void onKeyInput(KeyInputEvent event)
	{
		Minecraft mc = Minecraft.getMinecraft();
		if (k_open_settings.isPressed() && mc.currentScreen == null) {
			mc.displayGuiScreen(new GuiScreenSettingsHead(null));
		}
	}
}
