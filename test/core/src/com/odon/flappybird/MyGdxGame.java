package com.odon.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import sun.rmi.runtime.Log;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;

	Texture[] birds;
	int flapState = 0;
	float birdY = 0;
	float velocity = 0;

	Texture[] pipes;
	float topPipeY;
	float pipeX;

	int gameState = 0;
	float gravity = 3/2;

	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("bg.png");
		birds = new Texture[2];
        birds[0] = new Texture("bird.png");
        birds[1] = new Texture("bird2.png");
		birdY = Gdx.graphics.getHeight()/2 - birds[flapState].getHeight()/2;
		pipes = new Texture[2];
		pipes[0] = new Texture("toptube.png");
		pipes[1] = new Texture("bottomtube.png");
		pipeX = Gdx.graphics.getWidth() - pipes[0].getWidth();
		topPipeY = Gdx.graphics.getHeight() - 250;
	}

	@Override
	public void render () {
		if (gameState != 0) {
			if(Gdx.input.justTouched()){
				velocity = -30;
			}

			if (birdY > 0 || velocity < 0) {
				velocity += gravity;
				birdY -= velocity;
			}

		} else {
			if(Gdx.input.justTouched()){
				gameState = 1;
			}
		}

		if (flapState == 0) {
			flapState = 1;
		} else {
			flapState = 0;
		}

		batch.begin();
		batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		float birdx = Gdx.graphics.getWidth()/2 - birds[flapState].getWidth()/2;
		batch.draw(birds[flapState], birdx, birdY);
		batch.draw(pipes[0], pipeX, topPipeY);
		batch.draw(pipes[1], pipeX, topPipeY - 400 - pipes[1].getHeight());
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		background.dispose();
	}
}
