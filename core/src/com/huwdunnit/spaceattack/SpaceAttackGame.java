package com.huwdunnit.spaceattack;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.huwdunnit.spaceattack.screens.GameScreen;

public class SpaceAttackGame extends Game {

	@Override
	public void create() {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		setScreen(new GameScreen());
	}
}
