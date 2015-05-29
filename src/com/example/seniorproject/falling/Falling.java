package com.example.seniorproject.falling;

import android.graphics.Color;
import android.util.Log;

import com.example.seniorproject.framework.Game;
import com.example.seniorproject.framework.Graphics;
import com.example.seniorproject.main.GameOverScreen;

public class Falling {
	public static int[][] board;
	public static int ROW,COL;
	public static int score;
	public static int movementDir;
	public static int loopNum;
	public static int pieceWidth, pieceHeight;

	public Falling()
	{
		// initialize board
		// 0 = empty space / 1 = body / 2 = head
		ROW = 62;
		COL = 62;
		pieceWidth = 6;
		pieceHeight = 6;
		board = new int[ROW][COL];
		for(int i=0;i<ROW;i++)
		{
			for(int j=0;j<COL;j++)
			{
				board[i][j] = 0;
			}
		}
		
		board[0][0] = 1;		
		
		score = 0;
		movementDir = 0;
		loopNum=8;
	}

	public void setMovement(String direction) {
		if (direction.equals("right"))
			movementDir = 2;
		else if (direction.equals("left"))
			movementDir = -2;
		else if (direction.equals("none"))
			movementDir = 0;
	}

	public String getScore() {
		return score+"";
	}

	public void updateBoard(Game game) 
	{
		if(loopNum%8==0)
		{
			generateObstacles();
			score+=1;
		}
		
		boolean fallerFound = false;
		boolean blockBot = false, blockSide = false;
		for(int i=0;i<ROW;i++)
		{
			for(int j=0;j<COL;j++)
			{
				if(board[i][j]==1)
				{
					board[i][j] = 0;
					if(j+movementDir<0||j+movementDir>COL-pieceWidth)
					{
						board[i][j-movementDir] = 1;
						movementDir = 0;
					}
					else
					{
						try {
							for(int k=0;k<pieceWidth;k++)
							{
								for(int l=0;l<pieceHeight;l++)
								{
									if(l==pieceHeight-1 && board[i+l][j+movementDir+k]!=0)
									{
										blockBot = true;
										break;
									}
									else if(board[i+l][j+movementDir+k]!=0)
									{
										blockSide = true;
										break;
									}
								}
								if(blockBot || blockSide)
									break;
							}
							if(blockBot)
								board[i][j+movementDir] = 1;
							else if(blockSide)
								board[i+2][j] = 1;
							else
								board[i+2][j+movementDir] = 1;
							
						} catch (ArrayIndexOutOfBoundsException e) {
							outOfBounds(game);
						}
					}
					fallerFound = true;
					break;
				}
			}
			if(fallerFound)
				break;
		}
		
		if(!fallerFound)
			outOfBounds(game);
		
		for(int i=0;i<ROW-1;i++)
		{ 	
			for(int j=0; j<COL; j++)
				board[i][j] = board[i+1][j];
		}
		for(int i=0;i<COL;i++)
			board[ROW-1][i]=0;
		
		loopNum++;
	}
	
	private void generateObstacles() {
		
		int obstacleColor = 2+(int)(Math.random()*3);
		
		for(int i=0;i<COL;i++)
		{
			board[ROW-1][i] = obstacleColor; 
		}
		
		int numHoles = 3;
		if(score>15)
		{
			numHoles = 2;
		}
		while(numHoles>0)
		{
			int randCol = (int)(Math.random()*(COL-pieceWidth));
			for(int i=randCol;i<randCol+pieceWidth+1;i++)
			{
				board[ROW-1][i] = 0;
			}
			numHoles-=1;
		}
		
		
	}

	private void outOfBounds(Game game) {
		game.setScreen(new GameOverScreen(game,score,1));
		
	}

	public void drawBoard(Game game) {
		Graphics g = game.getGraphics();
		
		int color = Color.RED;
		
		for(int i=0;i<ROW;i++)
		{
			for(int j=0;j<COL;j++)
			{
				if(board[i][j]==1)
				{
					color = Color.RED;
					g.drawRect(j*5, 60+i*5, 30, 30, color);
				}
				else if(board[i][j]==2)
				{
					color = Color.GREEN;
					g.drawRect(j*5, 65+i*5, 8, 10, color);
				}
				else if(board[i][j]==3)
				{
					color = Color.CYAN;
					g.drawRect(j*5, 65+i*5, 8, 10, color);
				}
				else if(board[i][j]==4)
				{
					color = Color.BLUE;
					g.drawRect(j*5, 65+i*5, 8, 10, color);
				}
				else if(board[i][j]==5)
				{
					color = Color.YELLOW;
					g.drawRect(j*5, 65+i*5, 8, 10, color);
				}
				
			}
		}
		
	}

	public int getMovement() {
		return movementDir;
	}
}
