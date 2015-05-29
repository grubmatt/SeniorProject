package com.example.seniorproject.main;

import java.util.List;

import android.graphics.Color;
import android.util.Log;

import com.example.seniorproject.framework.Graphics;
import com.example.seniorproject.framework.Input.TouchEvent;
import com.example.seniorproject.framework.Game;
import com.example.seniorproject.framework.Screen;

public class MainMenu extends Screen {
	public MainMenu(Game game) {
		super(game);
	}

	@Override
	public void update(float deltaTime) {
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		game.getInput().getKeyEvents();
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type == TouchEvent.TOUCH_UP) {
				if(event.x> 100 && event.x<220 && event.y>355 && event.y<400)
				{
					game.setScreen(new GameSelector(game));
				}
				else if(event.x> 55 && event.x< 295 && event.y>405 && event.y<450)
				{
					game.setScreen(new SettingsScreen(game));
				}
			}
		}
	}

	@Override
	public void present(float deltaTime) {
		Graphics g = game.getGraphics();
		g.clear(Color.WHITE); 
		
		int size = 45;
		g.drawText("Android", 15, 70, size);
		g.drawText("Arcade", 50, 135, size);
		
		size = 30;
		g.drawText("Play", 100, 400, size);
		g.drawText("Settings", 55, 450, size);
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

}
