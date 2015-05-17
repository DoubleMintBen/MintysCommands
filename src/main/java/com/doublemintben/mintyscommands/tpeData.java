package com.doublemintben.mintyscommands;
import java.util.Date;

import net.minecraft.util.ChunkCoordinates;
import net.minecraftforge.event.CommandEvent;
import net.minecraft.entity.player.EntityPlayerMP;

public class tpeData
{
	private CommandEvent ce;
	private ChunkCoordinates cc;
	EntityPlayerMP ep;
	Date cd;
	boolean inWater;
	
	public tpeData()
	{
	}
	public tpeData(CommandEvent ce, ChunkCoordinates cc, EntityPlayerMP ep, Date cd, boolean inWater)
	{
		this.ce = ce;
		this.cc = cc;
		this.ep = ep;
		this.cd = cd;
		this.inWater = inWater;
	}
	
	//setters
	public void setCE(CommandEvent ce)
	{
		this.ce = ce;
	}
	public void setCC(ChunkCoordinates cc)
	{
		this.cc = cc;
	}
	public void setEP(EntityPlayerMP ep)
	{
		this.ep = ep;
	}
	public void setDate(Date cd)
	{
		this.cd = cd;
	}
	public void setInWater(boolean inWater)
	{
		this.inWater = inWater;
	}
	
	//getters
	public CommandEvent getCE()
	{
		return this.ce;
	}
	public ChunkCoordinates getCC()
	{
		return this.cc;
	}
	public EntityPlayerMP getEP()
	{
		return this.ep;
	}
	public Date getCD()
	{
		return this.cd;
	}
	public boolean getInWater()
	{
		return this.inWater;
	}
	
	//output names
	public String[] getNames()
	{
		return new String[] {ce.sender.getCommandSenderName().toString(), ep.getDisplayName().toString()};
	}
}
