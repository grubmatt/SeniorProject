package com.example.seniorproject.main;

import com.example.seniorproject.framework.Screen;
import com.example.seniorproject.framework.impl.AndroidGame;

public class AndroidArcade extends AndroidGame {
	public Screen getStartScreen() {
		return new MainMenu(this);
	}
}
