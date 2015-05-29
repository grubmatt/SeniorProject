package com.example.seniorproject.main;

import java.util.List;

import android.graphics.Color;

import com.example.seniorproject.colorcorrect.ColorCorrectScreen;
import com.example.seniorproject.falling.FallingScreen;
import com.example.seniorproject.framework.Game;
import com.example.seniorproject.framework.Graphics;
import com.example.seniorproject.framework.Screen;
import com.example.seniorproject.framework.Input.TouchEvent;
import com.example.seniorproject.main.GameSelector;
import com.example.seniorproject.snake.SnakeScreen;

public class GameOverScreen extends Screen {
	public int score, gameType;
	
	public GameOverScreen(Game game, int passedScore,int passedGameType) {
		super(game);
		score = passedScore;
		gameType = passedGameType;
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
					game.setScreen(new GameSelector(game));
				}
				if(event.x> 65 && event.x<275 && event.y>170 && event.y<200)
				{
					if(gameType==0)
						game.setScreen(new SnakeScreen(game));
					else if(gameType==1)
						game.setScreen(new FallingScreen(game));
					else if(gameType==2)
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
		g.drawText("Game Over", 15, 45, size);
		
		g.drawText("Replay?", 65, 200, size);
		if(score<10)
			g.drawText("Score: "+score, 65, 300, size);
		else if(score<100)
			g.drawText("Score: "+score, 55, 300, size);
		else
			g.drawText("Score: "+score, 45, 300, size);
		
		g.drawText("<-",15,470, size);

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}
}
