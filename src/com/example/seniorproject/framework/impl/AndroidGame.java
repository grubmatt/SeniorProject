package com.example.seniorproject.framework.impl;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;

import com.example.seniorproject.framework.FileIO;
import com.example.seniorproject.framework.Game;
import com.example.seniorproject.framework.Graphics;
import com.example.seniorproject.framework.Input;
import com.example.seniorproject.framework.Screen;

public abstract class AndroidGame extends Activity implements Game {
	AndroidFastRenderView renderView;
	Graphics graphics;
	Input input;
	FileIO fileIO;
	Screen screen;
	WakeLock wakeLock;
	
	@SuppressWarnings("deprecation")
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// full screen
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		
		// finds orientation then sets bitmap accordingly
		boolean isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
		int frameBufferWidth = isLandscape ? 480 : 320;
		int frameBufferHeight = isLandscape ? 320 : 480;
		Bitmap frameBuffer = Bitmap.createBitmap(frameBufferWidth,
				frameBufferHeight, Config.RGB_565);
		
		// scales to screen size
		float scaleX = (float) frameBufferWidth
				/ getWindowManager().getDefaultDisplay().getWidth();
		float scaleY = (float) frameBufferHeight
				/ getWindowManager().getDefaultDisplay().getHeight();
		
		// Implements necessary parts
		renderView = new AndroidFastRenderView(this, frameBuffer);
		graphics = new AndroidGraphics(getAssets(), frameBuffer);
		fileIO = new AndroidFileIO(this);
		input = new AndroidInput(this, renderView, scaleX, scaleY);
		
		screen = getStartScreen();
		setContentView(renderView);
		
		// keeps screen from sleeping
		getWindow().addFlags(LayoutParams.FLAG_KEEP_SCREEN_ON);
	}
	
	public void onResume() {
		super.onResume();
		screen.resume();
		renderView.resume();
	}
	
	public void onPause() {
		super.onPause();
		renderView.pause();
		screen.pause();
		if (isFinishing())
			screen.dispose();
	}
	
	public Input getInput() {
		return input;
	}

	public FileIO getFileIO() {
		return fileIO;
	}

	public Graphics getGraphics() {
		return graphics;
	}
	
	public void setScreen(Screen screen) {
		if (screen == null)
			throw new IllegalArgumentException("Screen must not be null");
		this.screen.pause();
		this.screen.dispose();
		screen.resume();
		screen.update(0);
		this.screen = screen;
	}
	
	public Screen getCurrentScreen() {
		return screen;
	}
}