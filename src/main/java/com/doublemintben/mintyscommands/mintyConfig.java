package com.doublemintben.mintyscommands;


import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraftforge.common.config.Configuration;

//test code (once I get more advanced and add other things, might need config https://youtu.be/iT-RMZP-acI?t=31m34s
public class mintyConfig
{
    public static int timer;
    public static final int defaultTimer = 30;
    public static final String timerName = "Configure how long a player has (In seconds) to /rts a user once teleported to";
    
    public static boolean shouldBurn;
    public static final boolean defaultShouldBurn = true;
    public static final String burnName = "Should the offender burn";
    
    public static int burnTime;
    public static final int defaultBurnTime = 1;
    public static final String btName = "How long should they burn";

    public static void syncConfig()
    {
        FMLCommonHandler.instance().bus().register(MintysCommands.Instance);
        
        final String TPENHANCE = MintysCommands.config.CATEGORY_GENERAL + MintysCommands.config.CATEGORY_SPLITTER + "TP Enhance";
        MintysCommands.config.addCustomCategoryComment(TPENHANCE, "Configure tp enhance stats");   
        timer = MintysCommands.config.get(TPENHANCE, timerName, defaultTimer).getInt(defaultTimer);
        shouldBurn = MintysCommands.config.get(TPENHANCE, burnName, defaultShouldBurn).getBoolean(defaultShouldBurn);
        burnTime = MintysCommands.config.get(TPENHANCE, btName, defaultBurnTime).getInt(defaultBurnTime);
        		
        if(MintysCommands.config.hasChanged())
        {
        	MintysCommands.config.save();
        }
    }
}
