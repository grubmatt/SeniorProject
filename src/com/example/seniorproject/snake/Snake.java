package com.example.seniorproject.snake;

import java.util.ArrayList;

import android.graphics.Color;

import com.example.seniorproject.framework.Game;
import com.example.seniorproject.framework.Graphics;
import com.example.seniorproject.main.GameOverScreen;

public class Snake {
	public static int[][] board;
	public static int[] tail;
	public static int movementDir;
	public static int lastMove;
	public static int snakeLength;
	public static int score;
	public static ArrayList<Integer> tailDir;
	boolean fruitEaten;
	
	public Snake()
	{
		// initialize board
		// 0 = empty space / 1 = body / 2 = head
		board = new int[10][10];
		for(int i=0;i<10;i++)
		{
			for(int j=0;j<10;j++)
			{
				board[i][j] = 0;
			}
		}
		board[5][5] = 2;
		tail = new int[2];
		tail[0] = 5;
		tail[1] = 5;
		movementDir = 0;
		lastMove = movementDir;
		snakeLength = 0;
		tailDir = new ArrayList<Integer>();
		tailDir.add(0);
		fruitEaten = true;
		score = 0;
	}

	public void setMovement(String direction) 
	{
		if (direction.equals("up"))
			movementDir = 0;
		else if (direction.equals("right"))
			movementDir = 1;
		else if (direction.equals("down"))
			movementDir = 2;
		else if (direction.equals("left"))
			movementDir = 3;
	}
	
	public void updateBoard(Game game)
	{
		boolean headFound = false;
		
		if (fruitEaten) 
		{
			int[] fruitPosition = new int[2];
			while (true) 
			{
				fruitPosition[0] = (int) (Math.random() * 10);
				fruitPosition[1] = (int) (Math.random() * 10);
				if (board[fruitPosition[0]][fruitPosition[1]] == 0) 
				{
					board[fruitPosition[0]][fruitPosition[1]] = 3;
					break;
				}
			}
			fruitEaten=false;
		}
		     
		for(int i=0;i<10;i++)
		{
			for(int j=0;j<10;j++)
			{
				if(board[i][j] == 2)
				{
					try {
						if(snakeLength==1)
						{
							tailDir.add(movementDir);
						}
						else if(tailDir.size()==1)
						{
							tailDir.remove(0);
							tailDir.add(movementDir);
						}
						else
						{
							tailDir.add(movementDir);
						}
	
						if (movementDir == 0)
						{
							if(board[i - 1][j] == 3)
								eatFruit(i,j);
							else if(board[i - 1][j] == 1)
								outOfBounds(game);
							else 
								board[i - 1][j] = 2;
						}
						else if (movementDir == 1)
						{
							if(board[i][j + 1] == 3)
								eatFruit(i,j);
							else if(board[i][j + 1] == 1)
								outOfBounds(game);
							else
								board[i][j + 1] = 2;
						}
						else if (movementDir == 2)
						{
							if(board[i + 1][j] == 3)
								eatFruit(i,j);
							else if(board[i + 1][j] == 1)
								outOfBounds(game);
							else
								board[i + 1][j] = 2;
						}
						else if (movementDir == 3)
						{
							if(board[i][j - 1] == 3)
								eatFruit(i,j);
							else if(board[i][j - 1] == 1)
								outOfBounds(game);
							else
								board[i][j - 1] = 2;
						}	
						
						board[i][j] = 1;
						board[tail[0]][tail[1]] = 0;

						if (!fruitEaten) {
							try {
								if (tailDir.get(0) == 0)
									tail[0] -= 1;
								else if (tailDir.get(0) == 1)
									tail[1] += 1;
								else if (tailDir.get(0) == 2)
									tail[0] += 1;
								else if (tailDir.get(0) == 3)
									tail[1] -= 1;

								tailDir.remove(0);
							} catch (IndexOutOfBoundsException e) {
								outOfBounds(game);
							}
						}
					} catch (ArrayIndexOutOfBoundsException e){
						outOfBounds(game);
					} 
					headFound = true;
					break;
				}
			}
			if(headFound)
			{
				break;
			}
		}
		if(!headFound)
			outOfBounds(game);
	}

	private void outOfBounds(Game game) {
		game.setScreen(new GameOverScreen(game, score, 0));
	}

	public void drawBoard(Game game) {
		Graphics g = game.getGraphics();
		
		int color = Color.BLACK;
		g.drawRect(10, 70, 300, 300,color);
		
		for(int i=0;i<10;i++)
		{
			for(int j=0;j<10;j++)
			{
				color = Color.WHITE;
				if(board[i][j]==1)
					g.drawRect(10+(j*30),70+(i*30),30,30, color);
				else if(board[i][j]==2)
				{
					g.drawRect(10+(j*30),70+(i*30),30,30, color);
					color = Color.BLACK;
					g.drawLine(25+(j*30), 85+(i*30), 40+(j*30), 85+(i*30), color);
				}
				else if(board[i][j]==3)
				{
					color = Color.RED;
					g.drawLine(25+(j*30), 85+(i*30), 40+(j*30), 85+(i*30), color);
				}
			}
		}
	}
	
	public void eatFruit(int i, int j)
	{			
		if (movementDir == 0)
		{
			board[i - 1][j] = 2;
		}
		else if (movementDir == 1)
		{
			board[i][j + 1] = 2;
		}
		else if (movementDir == 2)
		{
			board[i + 1][j] = 2;
		}
		else if (movementDir == 3)
		{
			board[i][j - 1] = 2;
		}
		fruitEaten=true;
		snakeLength++;
		score ++;
	}

	public String getScore() {
		// casts score to string
		return "" + score;
	}
}
