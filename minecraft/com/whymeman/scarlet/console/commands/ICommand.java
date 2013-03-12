package com.whymeman.scarlet.console.commands;

public interface ICommand 
{
	String getCommand();
	void onCmd(String[] cmd);
	void showHelp(String[] cmd);
}
