package com.odon.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

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
	int numberOfPipes = 4;
	float[] pipeX = new float[numberOfPipes];
	float[] tubeOffset = new float[numberOfPipes];

	int gameState = 0;
	float gravity = 3/2;
	float pipeGap = 400;
	float maxTubeOffset;
	Random randomGenerator;
	float tubeVelocity = 4;

	float distanceBetweenTubes;


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
		topPipeY = Gdx.graphics.getHeight() - 250;

		maxTubeOffset = Gdx.graphics.getHeight() / 2 - pipeGap - 100;
		randomGenerator = new Random();
		distanceBetweenTubes = Gdx.graphics.getWidth() * 3/4;

		for (int i = 0; i < numberOfPipes; i ++){
			tubeOffset[i] = (randomGenerator.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - pipeGap - 200);
			pipeX[i] = Gdx.graphics.getWidth() - pipes[1].getWidth() + i * distanceBetweenTubes ;

		}
	}

	@Override
	public void render () {
		batch.begin();
		batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		if (gameState != 0) {

			if(Gdx.input.justTouched()){
				velocity = -30;
			}

			for (int i = 0; i < numberOfPipes; i++ ){
				if(pipeX[i] < - pipes[0].getWidth()){
					pipeX[i] += numberOfPipes * distanceBetweenTubes;
				} else {
					pipeX[i] = pipeX[i] - tubeVelocity;
					batch.draw(pipes[0], pipeX[i], topPipeY + tubeOffset[i]);
					batch.draw(pipes[1], pipeX[i], topPipeY - pipeGap - pipes[1].getHeight() + tubeOffset[i]);
				}
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


		float birdx = Gdx.graphics.getWidth()/2 - birds[flapState].getWidth()/2;
		batch.draw(birds[flapState], birdx, birdY);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		background.dispose();
	}
}
