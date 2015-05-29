package com.example.seniorproject.colorcorrect;

import android.graphics.Color;

import com.example.seniorproject.framework.Game;
import com.example.seniorproject.framework.Graphics;
import com.example.seniorproject.main.GameOverScreen;

public class ColorCorrect{
	
	static String colors[] = {"BLUE", "GREEN", "YELLOW", "RED","WHITE"};
	static int boardHeight, boardWidth, currentColor, score;
	static float timerLength;
	static int board[][];
	static boolean colorShown;
	
	public ColorCorrect() 
	{
		boardWidth = 2;
		boardHeight = 2;
		
		timerLength = 10;
		
		createNewColor();	
		createNewBoard();
		score = 0;
	}

	private static void createNewColor() 
	{
		currentColor = (int)(Math.random()*4);
	}

	private static void createNewBoard() 
	{
		board = new int[boardWidth][boardHeight];
		boolean colorInBoard = false;
		
		while(!colorInBoard)
		{
			for(int i=0;i<boardWidth;i++)
			{
				for(int j=0;j<boardHeight;j++)
					board[i][j] = (int)(Math.random()*4);
			}
			for(int i=0;i<boardWidth;i++)
			{
				for(int j=0;j<boardHeight;j++)
					if(board[i][j] == currentColor) colorInBoard = true;
			}	
		}
	}

	public void showColor(Game game) 
	{		
		Graphics g = game.getGraphics();
			
		g.drawRect(0, 60, 321, 320, Color.parseColor(colors[currentColor]));
	}


	public float getTimerLength() 
	{
		return timerLength;
	}

	public void showBoard(Game game) 
	{
		Graphics g = game.getGraphics();
		
		for(int i=0; i<boardHeight; i++)
		{
			for(int j=0; j<boardHeight; j++)
			{
				if(j<1 && i<1)
					g.drawRect(0, 60, 320/boardWidth, 320/boardHeight, Color.parseColor(colors[board[i][j]]));
				else if(j<1)
					g.drawRect(0, 60+320/(i*boardHeight), 320/boardWidth, 320/boardHeight, Color.parseColor(colors[board[i][j]]));
				else if(i<1)
					g.drawRect(320/(j*boardWidth), 60, 320/boardWidth, 320/boardHeight, Color.parseColor(colors[board[i][j]]));
				else
					g.drawRect(320/(j*boardWidth), 60+320/(i*boardHeight), 320/boardWidth, 320/boardHeight, Color.parseColor(colors[board[i][j]]));
			}
		}
	}

	public void clear(int i, int j, Game game) 
	{
		if(board[i][j] == currentColor || board[i][j]==4)
			board[i][j] = 4;	
		else
			game.setScreen(new GameOverScreen(game, score, 2));
	}

	public boolean checkComplete() 
	{
		for(int i=0; i<boardHeight; i++)
		{
			for(int j=0; j<boardHeight; j++)
			{
				if(board[i][j]==currentColor)
					return false;
			}
		}	
		score++;
		
		if(timerLength<=.5)	
		{} // does nothing
		else if(timerLength<=1)	
			timerLength-=.1;
		else
			timerLength = Math.round((float)(timerLength/1.2)*100)/100;
		createNewBoard();
		createNewColor();
		return true;
	}

	public int getScore() {
		return score;
	}
}

