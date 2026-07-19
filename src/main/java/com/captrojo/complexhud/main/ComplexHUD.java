package com.captrojo.complexhud.main;

import com.captrojo.complexhud.api.HUDAPI;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;

@Mod(
	modid = ComplexHUD.MOD_ID,
	version = ComplexHUD.VERSION
)
public class ComplexHUD
{
	public static final String MOD_ID = "complexhud";
	public static final String VERSION = "1.0.0";
	public static final int VERSION_NUM = 0x010000;
	public static final boolean DEBUG = true;
	
	public static String ident(String str)
	{
		if (str.contains(":")) {
			return str;
		}
		return MOD_ID + ":" + str;
	}
	
	public static ResourceLocation resource(String path)
	{
		return new ResourceLocation(ident(path));
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		if (event.getSide() == Side.SERVER) {
			return;
		}
		MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
		HUDElementList.init();
		
		if (DEBUG) {
			HUDAPI.registerElement(new DebugHUDElement(0, "([0])"));
			HUDAPI.registerElement(new DebugHUDElement(1, "[1]"));
			HUDAPI.registerElement(new DebugHUDElement(2, "[2]"));
			HUDAPI.registerElement(new DebugHUDElement(3, "[3]"));
		}
	}
}
