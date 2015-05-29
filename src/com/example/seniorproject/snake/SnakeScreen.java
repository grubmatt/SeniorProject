package com.example.seniorproject.snake;

import java.util.List;

import android.graphics.Color;
import android.util.Log;

import com.example.seniorproject.framework.Game;
import com.example.seniorproject.framework.Graphics;
import com.example.seniorproject.framework.Screen;
import com.example.seniorproject.framework.Input.TouchEvent;
import com.example.seniorproject.main.GameSelector;

public class SnakeScreen extends Screen{
	Snake snake;
	
	public SnakeScreen(Game game) {
		super(game);
		snake = new Snake();
	}

	@Override
	public void update(float deltaTime) {
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		game.getInput().getKeyEvents();
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type == TouchEvent.TOUCH_UP) {
				if(event.x> 15 && event.x<45 && event.y>440 && event.y<470)
				{
					game.setScreen(new GameSelector(game));
				}
				else if(event.y<75 || (event.x> 106 && event.x<212 && event.y<240))
				{
					snake.setMovement("up");
				}
				else if(event.y>365 || (event.x> 106 && event.x<212 && event.y>240))
				{
					snake.setMovement("down");
				}
				else if(event.x<106 && event.y<440)
				{
					snake.setMovement("left");
				}
				else if(event.x>212)
				{
					snake.setMovement("right");
				}
			}
		}
		snake.updateBoard(game);
	}

	@Override
	public void present(float deltaTime) {
		Graphics g = game.getGraphics();
		g.clear(Color.WHITE);
		
		int size = 30;
		g.drawText("Snake", 15, 45, size);
		snake.drawBoard(game);
		
		g.drawText("Score: "+snake.getScore(), 15, 420, size);
		g.drawText("<-",15,470, size);
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}
}
