package com.example.seniorproject.main;

import java.util.List;

import android.graphics.Color;
import android.util.Log;

import com.example.seniorproject.colorcorrect.ColorCorrectScreen;
import com.example.seniorproject.falling.FallingScreen;
import com.example.seniorproject.framework.Game;
import com.example.seniorproject.framework.Graphics;
import com.example.seniorproject.framework.Screen;
import com.example.seniorproject.framework.Input.TouchEvent;
import com.example.seniorproject.snake.SnakeScreen;

public class GameSelector extends Screen {

	public GameSelector(Game game) {
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
				else if(event.x> 65 && event.x<215 && event.y>170 && event.y<190)
				{
					game.setScreen(new SnakeScreen(game));
				}
				else if(event.x> 65 && event.x<275 && event.y>230 && event.y<250)
				{
					game.setScreen(new FallingScreen(game));
				}
				else if(event.x> 65 && event.x<335 && event.y>290 && event.y<355)
				{
					Log.d("SELECTOR", "COLOR PRESSED");
					game.setScreen(new ColorCorrectScreen(game));
				}
			}
		}
	}

	@Override
	public void present(float deltaTime) {
		Graphics g = game.getGraphics();
		g.clear(Color.WHITE);
		
		int size = 30;
		g.drawText("Games", 15, 45, size);
		g.drawText("Snake", 65, 190, size);
		g.drawText("Falling", 65, 250, size);
		g.drawText("Color", 65, 310, size);
		g.drawText("  Correct", 65, 355, size);

		g.drawText("<-",15,470, size);
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}
}
