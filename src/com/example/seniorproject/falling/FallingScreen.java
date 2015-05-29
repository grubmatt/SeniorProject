package com.example.seniorproject.falling;

import java.util.List;

import android.graphics.Color;
import android.util.Log;

import com.example.seniorproject.framework.Game;
import com.example.seniorproject.framework.Graphics;
import com.example.seniorproject.framework.Screen;
import com.example.seniorproject.framework.Input.TouchEvent;
import com.example.seniorproject.main.GameSelector;

public class FallingScreen extends Screen{
	Falling falling;
	int currentPointer;
	TouchEvent nextEvent;
	
	public FallingScreen(Game game)
	{
		super(game);
		falling = new Falling();
		currentPointer = 0;
		nextEvent = null;
	}

	@Override
	public void update(float deltaTime) {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		game.getInput().getKeyEvents();
		int len = touchEvents.size();
				
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			
			if(event.x> 15 && event.x<45 && event.y>440 && event.y<470)
			{
				game.setScreen(new GameSelector(game));
				i=len;
			}
			if(event.pointer!=currentPointer)
			{
				if(event.type == TouchEvent.TOUCH_DRAGGED ||event.type == TouchEvent.TOUCH_DOWN)
				{
					nextEvent = event;
				}
				else if(event.type == TouchEvent.TOUCH_UP)
				{
					nextEvent = null;
				}
			}	
			else if (event.type == TouchEvent.TOUCH_DOWN || event.type == TouchEvent.TOUCH_DRAGGED) 
			{
				if(falling.getMovement() == 0)
				{
					if (event.x <= 160) 
						falling.setMovement("left");
					else if (event.x > 160) 
						falling.setMovement("right");
				}
			}
			else if (event.type == TouchEvent.TOUCH_UP) 
			{
				falling.setMovement("none");
				if(currentPointer == 1)
					currentPointer = 0;
				if(nextEvent != null)
				{
					currentPointer = nextEvent.pointer;
					touchEvents.remove(i);
					touchEvents.add(i, nextEvent);
					nextEvent = null;
					i--;
				}
			}
		}
		
		falling.updateBoard(game);
		
	}

	@Override
	public void present(float deltaTime) {
		Graphics g = game.getGraphics();
		g.clear(Color.WHITE);
		
		int size = 30;
		g.drawText("Falling", 15, 45, size);
		falling.drawBoard(game);
		
		g.drawLine(0, 60, 320, 60, Color.BLACK);
		g.drawLine(0, 380, 320, 380, Color.BLACK);
		
		g.drawText("Score: "+falling.getScore(), 15, 420, size);
		g.drawText("<-",15,470, size);
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}
}
