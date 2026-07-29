package com.captrojo.complexhud.main;

import com.captrojo.complexhud.api.HUDAPI;
import com.captrojo.complexhud.api.PositionOrigin;
import com.captrojo.complexhud.config.ModConfig;
import com.captrojo.complexhud.element.ElementAirBar;
import com.captrojo.complexhud.element.ElementArmorBar;
import com.captrojo.complexhud.element.ElementBossHealth;
import com.captrojo.complexhud.element.ElementHealthBar;
import com.captrojo.complexhud.element.ElementHungerBar;
import com.captrojo.complexhud.element.ElementJumpBar;
import com.captrojo.complexhud.element.ElementMountHealthBar;
import com.captrojo.complexhud.element.ElementXPBar;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
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
	public static final String VERSION = "0.0.1";
	
	public static boolean applecore_loaded;
	
	public static String config_dir;
	
	static ElementHealthBar e_health_bar;
	static ElementArmorBar e_armor_bar;
	static ElementHungerBar e_hunger_bar;
	static ElementAirBar e_air_bar;
	static ElementMountHealthBar e_mount_health_bar;
	
	static ElementBossHealth e_boss_health;
	static ElementXPBar e_xp_bar;
	static ElementJumpBar e_jump_bar;
	
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
		
		applecore_loaded = Loader.isModLoaded("AppleCore");
		
		config_dir = event.getSuggestedConfigurationFile().getParent();
		ModConfig.init(event.getSuggestedConfigurationFile());
		
		ModKeyInput.registerKeybinds();
		FMLCommonHandler.instance().bus().register(new ModKeyInput());
		
		ClientEventHandler.instance = new ClientEventHandler();
		FMLCommonHandler.instance().bus().register(ClientEventHandler.instance);
		MinecraftForge.EVENT_BUS.register(ClientEventHandler.instance);
		
		HUDElementList.init();
		
		e_health_bar = new ElementHealthBar();
		e_armor_bar = new ElementArmorBar();
		e_hunger_bar = new ElementHungerBar();
		e_air_bar = new ElementAirBar();
		e_mount_health_bar = new ElementMountHealthBar();
		
		e_boss_health = new ElementBossHealth();
		e_xp_bar = new ElementXPBar();
		e_jump_bar = new ElementJumpBar();
		
		HUDAPI.registerElement(MOD_ID, e_health_bar);
		HUDAPI.registerElement(MOD_ID, e_armor_bar);
		HUDAPI.registerElement(MOD_ID, e_hunger_bar);
		HUDAPI.registerElement(MOD_ID, e_air_bar);
		HUDAPI.registerElement(MOD_ID, e_mount_health_bar);
		
		HUDAPI.registerElement(MOD_ID, e_boss_health);
		HUDAPI.registerElement(MOD_ID, e_xp_bar);
		HUDAPI.registerElement(MOD_ID, e_jump_bar);
		
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
