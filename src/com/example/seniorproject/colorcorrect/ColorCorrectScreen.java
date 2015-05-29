package com.example.seniorproject.colorcorrect;


import java.util.List;

import android.graphics.Color;
import android.util.Log;

import com.example.seniorproject.framework.Game;
import com.example.seniorproject.framework.Graphics;
import com.example.seniorproject.framework.Screen;
import com.example.seniorproject.framework.Input.TouchEvent;
import com.example.seniorproject.main.GameOverScreen;
import com.example.seniorproject.main.GameSelector;

public class ColorCorrectScreen extends Screen{
	ColorCorrect colorCorrect;
	boolean showColor;
	static float timeLeft;
	
	public ColorCorrectScreen(Game game)
	{
		super(game);
		colorCorrect = new ColorCorrect();
		showColor = true;
	}

	@Override
	public void update(float deltaTime) {	
		timeLeft -= .01;
		
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		game.getInput().getKeyEvents();
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type == TouchEvent.TOUCH_UP) {
				
				if(event.x> 15 && event.x<45 && event.y>440 && event.y<470)
					game.setScreen(new GameSelector(game));
				else if(showColor)
					showColor=false;
				else if(event.x>0 && event.x<160 && event.y>60 && event.y<220)
					colorCorrect.clear(0,0, game);
				else if(event.x>160 && event.x<320 && event.y>60 && event.y<220)
					colorCorrect.clear(0,1, game);
				else if(event.x>0 && event.x<160 && event.y>220 && event.y<380)
					colorCorrect.clear(1,0, game);
				else if(event.x>160 && event.x<320 && event.y>220 && event.y<380)
					colorCorrect.clear(1,1, game);
			}
		}	
		
		if(colorCorrect.checkComplete())
			showColor = true;
		if(timeLeft<=0)
			game.setScreen(new GameOverScreen(game, colorCorrect.getScore(), 2));			
}

	@Override
	public void present(float deltaTime) {
		Graphics g = game.getGraphics();
		g.clear(Color.WHITE);
		int size = 25;
		g.drawText("Color Correct", 15, 45, size);
		
		g.drawRect(0, 60, 321, 320, Color.BLACK);
		if(showColor)
		{
			timeLeft=colorCorrect.getTimerLength();
			colorCorrect.showColor(game);
		}
		else
			colorCorrect.showBoard(game);
		
		
		g.drawText("<-",15,470, size);
		g.drawText("Time Left: "+timeLeft, 5, 420, size);
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}
}
