package com.example.seniorproject.framework;

import com.example.seniorproject.framework.Graphics.PixmapFormat;

public interface Pixmap
{
	public int getWidth();
	
	public int getHeight();
	
	public PixmapFormat getFormat();
	
	public void dispose();
}
