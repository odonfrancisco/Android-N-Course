package com.odon.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

import sun.rmi.runtime.Log;

public class MyGdxGame extends ApplicationAdapter {
	private SpriteBatch batch;
	private Texture background;
	private ShapeRenderer shapeRenderer;

	private Texture[] birds;
	private int flapState = 0;
	private float birdY = 0;
	private float velocity = 0;
	private Circle birdCircle = new Circle();

	private Texture[] pipes;
	private float topPipeY;
	private int numberOfPipes = 4;
	private float[] pipeX = new float[numberOfPipes];
	private float[] tubeOffset = new float[numberOfPipes];
	private Rectangle[] bottomPipeRectangles = new Rectangle[numberOfPipes];
	private Rectangle[] topPipeRectangles = new Rectangle[numberOfPipes];

	private int gameState = 0;
	private float gravity = 3/2;
	private float pipeGap = 400;
	private float maxTubeOffset;
	private Random randomGenerator;
	private float tubeVelocity = 4;

	private float distanceBetweenTubes;


	@Override
	public void create () {
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();

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
			topPipeRectangles[i] = new Rectangle();
			bottomPipeRectangles[i] = new Rectangle();
		}
	}

	@Override
	public void render () {
		batch.begin();
		batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
//		shapeRenderer.setColor(Color.RED);

		birdCircle.set(Gdx.graphics.getWidth()/2, birdY + birds[flapState].getHeight() / 2, birds[flapState].getWidth() / 2);
//		shapeRenderer.circle(birdCircle.x, birdCircle.y, birdCircle.radius);

		if (gameState != 0) {

			if(Gdx.input.justTouched()){
				velocity = -30;
			}

			for (int i = 0; i < numberOfPipes; i++ ){
				if(pipeX[i] < - pipes[0].getWidth()){
					pipeX[i] += numberOfPipes * distanceBetweenTubes;
					tubeOffset[i] = (randomGenerator.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - pipeGap - 200);
				} else {
					pipeX[i] = pipeX[i] - tubeVelocity;
				}
				batch.draw(pipes[0], pipeX[i], topPipeY + tubeOffset[i]);
				batch.draw(pipes[1], pipeX[i], topPipeY - pipeGap - pipes[1].getHeight() + tubeOffset[i]);
//					shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
//					shapeRenderer.setColor(Color.MAGENTA);

				topPipeRectangles[i] = new Rectangle(pipeX[i], topPipeY + tubeOffset[i],
						pipes[0].getWidth(), pipes[0].getHeight());
				bottomPipeRectangles[i] = new Rectangle(pipeX[i], topPipeY - pipeGap - pipes[1].getHeight() + tubeOffset[i],
						pipes[1].getWidth(), pipes[1].getHeight());

//				shapeRenderer.rect(topPipeRectangles[i].x, topPipeRectangles[i].y,
//						pipes[0].getWidth(), pipes[0].getHeight());
//				shapeRenderer.rect(bottomPipeRectangles[i].x, bottomPipeRectangles[i].y,
//						pipes[1].getWidth(), pipes[0].getHeight());
//					shapeRenderer.end();

				if(Intersector.overlaps(birdCircle, topPipeRectangles[i]) || Intersector.overlaps(birdCircle, bottomPipeRectangles[i])){
					Gdx.app.log("Collision", "Collision truth");
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


		shapeRenderer.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		background.dispose();
	}
}
