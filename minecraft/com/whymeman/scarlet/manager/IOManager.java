package com.whymeman.scarlet.manager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;

import net.minecraft.client.Minecraft;

import com.whymeman.scarlet.util.mc.Friend;

public class IOManager extends Manager
{
	private static ArrayList<Friend> friends = new ArrayList<Friend>();
	public IOManager()
	{
		super("IOManager");
		readFriends();
	}
	public static ArrayList<Friend> getFriends()
	{
		return friends;
	}
	public void addFriend(String s, String n)
	{
		friends.add(new Friend(s,n));
		this.writeFriends();
	}
	public void readFriends()
	{
		try {
			File friendFile = new File(Minecraft.getMinecraftDir(), "friends.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(friendFile), "UTF-8"));
            for (String line; (line = reader.readLine()) != null;) 
            {
                String[] split = line.split(":");
                if (split.length == 2)
                	friends.add(new Friend(split[0],split[1]));
                else
                	friends.add(new Friend(split[0]));
                
            }
            
            reader.close();
		} catch (Exception e) { }
	}
	public void writeFriends()
	{
		try {
			//Removes all old friends from list
			File temp = new File(Minecraft.getMinecraftDir(), "friends.txt");
			temp.delete();
			
			File friendFile = new File(Minecraft.getMinecraftDir(), "friends.txt");
            BufferedWriter friendSave = new BufferedWriter(new FileWriter(friendFile, true));

            for (Friend f : friends)
            {
            	friendSave.write(f.getName() + ":" + f.getNick());

                friendSave.newLine();
            }
            
            friendSave.close();
		} catch (Exception e) { }
	}
}
