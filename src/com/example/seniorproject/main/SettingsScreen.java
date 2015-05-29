package com.example.seniorproject.main;

import java.util.List;

import android.graphics.Color;

import com.example.seniorproject.framework.Game;
import com.example.seniorproject.framework.Graphics;
import com.example.seniorproject.framework.Screen;
import com.example.seniorproject.framework.Input.TouchEvent;

public class SettingsScreen extends Screen {

	public SettingsScreen(Game game) {
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
				if(event.x> 15 && event.x<45 && event.y>440 && event.y<470)
				{
					game.setScreen(new MainMenu(game));
				}
			}
		}

	}

	@Override
	public void present(float deltaTime) {
		Graphics g = game.getGraphics();
		g.clear(Color.WHITE);
		
		int size = 30;
		g.drawText("Settings", 15, 45, size);
		g.drawText("<-",15,470, size);
	}


	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}
}
