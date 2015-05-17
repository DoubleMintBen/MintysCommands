package com.doublemintben.mintyscommands;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = MintysCommands.MODID, name=MintysCommands.MODNAME, version = MintysCommands.VERSION, acceptableRemoteVersions="*")

public class MintysCommands
{
	public static final String MODID = "mintyscommands";
    public static final String MODNAME = "Minty's Commands";
    public static final String VERSION = "1.0_1.7.10";
    
    tpEnhance tpe = new tpEnhance();
    public static Configuration config;
    
    @Instance(value = MintysCommands.MODID)
    public static MintysCommands Instance;
    
    @SidedProxy(clientSide="com.doublemintben.mintyscommands.client.ClientProxy",
				serverSide="com.doublemintben.mintyscommands.CommonProxy")
//    @SidedProxy(clientSide="com.doublemintben.mintysarmors.client.ClientProxy",
//				serverSide="com.doublemintben.mintysarmors.CommonProxy")
    public static CommonProxy proxy;
    
    @EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		FMLCommonHandler.instance().bus().register(tpe);
    	MinecraftForge.EVENT_BUS.register(tpe);
    	
		config = new Configuration(event.getSuggestedConfigurationFile());
		mintyConfig.syncConfig();
	}
    
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		//do nothing
	}
 
	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		//do nothing
	}
	
	@EventHandler
	public void serverStarting(FMLServerStartingEvent event)
	{
		event.registerServerCommand(new tpEnhance());
	}

    @SubscribeEvent
    public void configChange(ConfigChangedEvent.OnConfigChangedEvent event)
    {
    	if(event.modID.equals(MODID))
    	{
    		mintyConfig.syncConfig();
    	}
    }
}
