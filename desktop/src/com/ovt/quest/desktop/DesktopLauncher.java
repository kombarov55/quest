package com.ovt.quest.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.ovt.quest.QuestGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 800 * 2;
		config.height = 960 * 2;
		config.title = "Quest";
		new LwjglApplication(new QuestGame(), config);
	}
}
