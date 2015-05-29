package com.example.seniorproject.framework;

public interface Game 
{
	public Input getInput();
	
	public FileIO getFileIO();
	
	public Graphics getGraphics();
	
	public void setScreen(Screen screen);
	
	public Screen getCurrentScreen();
	
	public Screen getStartScreen();
}
