package com.example.seniorproject.framework.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.os.Environment;
import android.util.Log;

import com.example.seniorproject.framework.Graphics;
import com.example.seniorproject.framework.Pixmap;

public class AndroidGraphics implements Graphics{
	Canvas canvas;
	AssetManager assets;
	Bitmap frameBuffer;
	Paint paint;
	Rect srcRect = new Rect();
	Rect dstRect = new Rect();
	
	public AndroidGraphics(AssetManager assets, Bitmap frameBuffer) {
		this.assets = assets;
		this.frameBuffer = frameBuffer;
		this.canvas = new Canvas(frameBuffer);
		this.paint = new Paint();
	}
	
	public Pixmap newPixmap(String filename, PixmapFormat format) {
		Config config = null;
		if (format == PixmapFormat.RGB565)
			config = Config.RGB_565;
		else if (format == PixmapFormat.ARGB4444)
			config = Config.ARGB_4444;
		else
			config = Config.ARGB_8888;
		Options options = new Options();
		options.inPreferredConfig = config;
		Bitmap bitmap = null;
		
		File location = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+ filename);
		File dest = new File(location, filename);
		FileInputStream is;
		try {
			is = new FileInputStream(dest);
			bitmap = BitmapFactory.decodeStream(is);
			if (bitmap == null)
				throw new RuntimeException("Bitmap returning null");
		} catch (FileNotFoundException e) {
			throw new RuntimeException("Bitmap not found");
		}
		if (bitmap.getConfig() == Config.RGB_565)
			format = PixmapFormat.RGB565;
		else if (bitmap.getConfig() == Config.ARGB_4444)
			format = PixmapFormat.ARGB4444;
		else
			format = PixmapFormat.ARGB8888;
		return new AndroidPixmap(bitmap, format);
	}
	
	public void clear(int color) {
		canvas.drawRGB((color & 0xff0000) >> 16, (color & 0xff00) >> 8,
				(color & 0xff));
	}
	
	public void drawPixel(int x, int y, int color) {
		paint.setColor(color);
		canvas.drawPoint(x, y, paint);
	}
	
	public void drawText(String text, int x, int y, int size) {
		paint.setColor(Color.BLACK);
		paint.setTextSize(size);
		Typeface font = Typeface.createFromAsset(assets, "Fipps-Regular.otf");
		paint.setTypeface(font);
		canvas.drawText(text, x, y, paint);
	}
	
	public void drawLine(int x, int y, int x2, int y2, int color) {
		paint.setColor(color);
		paint.setStrokeWidth(4);
		canvas.drawLine(x, y, x2, y2, paint);
	}
	
	public void drawRect(int x, int y, int width, int height, int color) {
		paint.setColor(color);
		paint.setStyle(Style.FILL);
		canvas.drawRect(x, y, x + width - 1, y + height - 1, paint);
	}
	
	public void drawPixmap(Pixmap pixmap, int x, int y, int srcX, int srcY,
			int srcWidth, int srcHeight) {
		srcRect.left = srcX;
		srcRect.top = srcY;
		srcRect.right = srcX + srcWidth - 1;
		srcRect.bottom = srcY + srcHeight - 1;
		dstRect.left = x;
		dstRect.top = y;
		dstRect.right = x + srcWidth - 1;
		dstRect.bottom = y + srcHeight - 1;
		canvas.drawBitmap(((AndroidPixmap) pixmap).bitmap, srcRect, dstRect,
				null);
	}
	
	public void drawPixmap(Pixmap pixmap, int x, int y) {
		canvas.drawBitmap(((AndroidPixmap) pixmap).bitmap, x, y, null);
	}
	
	public int getWidth() {
		return frameBuffer.getWidth();
	}

	public int getHeight() {
		return frameBuffer.getHeight();
	}
}