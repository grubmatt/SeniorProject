package com.example.seniorproject.framework;

public abstract class Screen {
	protected final Game game;
	
	public Screen(Game game)
	{
		this.game = game;
	}
	
	public abstract void update(float deltaTime);
	
	public abstract void present(float deltaTime);
	
	public void pause()
	{
		dispose();
	}
	
	public abstract void resume();
	
	public void dispose()
	{
		System.gc();
	}
}
