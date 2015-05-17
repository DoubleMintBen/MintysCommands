package com.doublemintben.mintyscommands;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.command.ServerCommand;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.event.CommandEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.stats.Achievement;

public class tpEnhance extends CommandBase
{
	static List<tpeData> tpList = new ArrayList<tpeData>();
	CommandEvent event;
	ChunkCoordinates cc;
	EntityPlayerMP epmp;
	tpeData tped;
	Date d;
	boolean inWater;
	
	private List aliases;
	public tpEnhance()
	{
		this.aliases = new ArrayList();
		this.aliases.add("rts");
	}
	
	@SubscribeEvent
	public void opTP(CommandEvent event)
	{
		if(event.command.getCommandName() == "tp" && event.parameters.length != 3)
		{
			d = new Date();
			cc = event.sender.getPlayerCoordinates();
			//get name of person being teleported to
			if(event.parameters.length == 1) //if /tp <other player>
			{
				epmp = MinecraftServer.getServer().getConfigurationManager().func_152612_a(event.parameters[0]);
			}
			else if(event.sender.getCommandSenderName().contains(event.parameters[0])) //if /tp <tp abuser> (aka command sender) <other player>
			{
				epmp = MinecraftServer.getServer().getConfigurationManager().func_152612_a(event.parameters[1]);
			}
			
			if(epmp != null)
			{
				inWater = epmp.isInWater();
				tped = new tpeData(event, this.cc, epmp, d, inWater);
				tpList.add(tped);
				
			}
		}
	}
	
	@Override
	public int compareTo(Object o)
	{
		return 0;
	}

	@Override
	public String getCommandName()
	{
		return "rts";
	}

	@Override
	public String getCommandUsage(ICommandSender icommandsender)
	{
		return "rts <username>";
	}

	@Override
	public List getCommandAliases()
	{
		return this.aliases;
	}

	@Override
	public void processCommand(ICommandSender icommandsender, String[] astring)
	{
		int listIndex;

		if(this.tpList.size() < 1)
		{
			icommandsender.addChatMessage(new ChatComponentText("Nobody has teleported to you"));
			return;
		}
		else if(astring.length != 1)
		{
			throw new WrongUsageException(getCommandUsage(icommandsender), new Object[0]);
		}
		else
		{
			listIndex = findIndex(icommandsender, astring);
			
			if(listIndex >= 0)
			{
				runCommand(listIndex, icommandsender, astring);
			}
			else
			{
				icommandsender.addChatMessage(new ChatComponentText(astring[0] + " has not teleported to you!"));
				return;
			}
		}
	}
	
	private int findIndex(ICommandSender icommandsender, String [] astring)
	{
		for(int x = 0; x < tpList.size(); x++)
		{
			String names[] = tpList.get(x).getNames(); //SENDER RECEIVER
			if(icommandsender.getCommandSenderName().contains(names[1]) && astring[0].contains(names[0]))
			{
				return x;
			}
		}
		return -1;
	}

	private void runCommand(int x, ICommandSender icommandsender, String [] astring)
	{
		d = new Date();
		long timeDif;
		timeDif = d.getTime() - tpList.get(x).getCD().getTime();
		EntityPlayerMP entityplayermp = CommandBase.getPlayer(tpList.get(x).getCE().sender, astring[0]);
		
		if(entityplayermp == null)
		{
			throw new PlayerNotFoundException();
		}
		
		if(TimeUnit.MILLISECONDS.toSeconds(timeDif) <= mintyConfig.timer)
		{
			icommandsender.addChatMessage(new ChatComponentText("Returning to sender!"));
			entityplayermp.mountEntity((Entity)null);
			entityplayermp.setPositionAndUpdate(tpList.get(x).getCC().posX + .5, tpList.get(x).getCC().posY + .1, tpList.get(x).getCC().posZ + .5);
			
			if(mintyConfig.shouldBurn)
			{
				if(!tpList.get(x).getInWater())
				{
					entityplayermp.setFire(mintyConfig.burnTime);
				}
				else
				{
					entityplayermp.attackEntityFrom(DamageSource.inFire, (float)mintyConfig.burnTime);
				}
				entityplayermp.addChatMessage(new ChatComponentText("You've been op SMACKED!"));
			}
			tpList.remove(x);
		}
		else
		{
			icommandsender.addChatMessage(new ChatComponentText("Nobody has teleported to you in the last " + mintyConfig.timer + " seconds!"));
			tpList.remove(x);
		}	
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender icommandsender)
	{
		return true;
	}

	@Override
	public List addTabCompletionOptions(ICommandSender icommandsender,
			String[] astring)
	{
		return astring.length != 1 && astring.length != 2 ? null : CommandBase.getListOfStringsMatchingLastWord(astring, MinecraftServer.getServer().getAllUsernames());
	}

	@Override
	public boolean isUsernameIndex(String[] astring, int i)
	{
		return false;
	}

	
}
