package com.whymeman.scarlet.console.commands;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import net.minecraft.client.Minecraft;
import net.minecraft.src.Enchantment;
import net.minecraft.src.EnchantmentDamage;
import net.minecraft.src.EntityClientPlayerMP;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Packet;
import net.minecraft.src.StringTranslate;
import com.whymeman.scarlet.console.Console;

public class CreativeEnchant implements ICommand
{
	private String command = "enchant";
	
	public void onCmd(String[] cmd) 
	{
		try {
			EntityClientPlayerMP thePlayer = Minecraft.getMinecraft().thePlayer;
		if (thePlayer.capabilities.isCreativeMode)
		{
			ItemStack stack = thePlayer.inventory.getCurrentItem();
			enchantStack(stack,cmd[2],Integer.parseInt(cmd[3]));
		}
		else
			Console.acm("You must be in creative mode to force enchant");
		} catch (Exception e) { Console.acm("Invalid Syntax!"); }
		
	}
	private void enchantStack(ItemStack i, String ench, int level) {
        for(Enchantment e : Enchantment.enchantmentsList) {
            if(e == null) {
                continue;
            }
            
            String en = e.getName();
            
            if(en != null) {
                String ename = StringTranslate.getInstance().translateKey(en);
                if(ename.replaceAll(" ", "").equalsIgnoreCase(ench)) {
                    i.addEnchantment(e, level);
                }
            }
        }
        
        this.writeStack(i);
    }
	private void writeStack(ItemStack i) {
        ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
        DataOutputStream datastream = new DataOutputStream(bytestream);

        try {
            Packet.writeItemStack(i, datastream);
        } catch(Exception e) {
            //lolol
        }
    }
	public String getCommand()
	{
		return command;
	}
	public void showHelp(String[] cmd)
	{
		Console.acm("Usage: ");
		Console.acm("Definition: ");
	}
}
