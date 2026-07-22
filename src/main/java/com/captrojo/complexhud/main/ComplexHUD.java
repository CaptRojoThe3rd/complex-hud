package com.captrojo.complexhud.main;

import com.captrojo.complexhud.api.HUDAPI;
import com.captrojo.complexhud.api.PositionOrigin;
import com.captrojo.complexhud.config.ModConfig;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;

@Mod(
	name = ComplexHUD.NAME,
	modid = ComplexHUD.MOD_ID,
	version = ComplexHUD.VERSION
)
public class ComplexHUD
{
	public static final String MOD_ID = "complexhud";
	public static final String NAME = "Complex HUD";
	public static final String VERSION = "1.0.0";
	public static final int VERSION_NUM = 0x010000;
	
	public static String config_dir;
	
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
	
	public static String[] convertEnumToNames(Enum[] en)
	{
		String[] names = new String[en.length];
		for (int i = 0; i < en.length; i++) {
			names[i] = en[i].name().toLowerCase();
		}
		return names;
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		if (event.getSide() == Side.SERVER) {
			return;
		}
		
		config_dir = event.getSuggestedConfigurationFile().getParent();
		ModConfig.init(event.getSuggestedConfigurationFile());
		
		ModKeyInput.registerKeybinds();
		FMLCommonHandler.instance().bus().register(new ModKeyInput());
		
		MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
		HUDElementList.init();
		
		if (Boolean.getBoolean("complexhud.debug")) {
			for (PositionOrigin o : PositionOrigin.values()) {
				HUDAPI.registerElement(MOD_ID, new DebugHUDElement(o.ordinal(), o, o.toString()));
			}
			for (int i = 0; i < 15; i++) {
				HUDAPI.registerElement(MOD_ID, new DebugHUDElement(i + 100, "[" + i + "]"));
			}
		}
	}
}
