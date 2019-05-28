package com.uzeer.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.uzeer.game.FunGame;
import com.uzeer.game.Sprites.Player;

import static com.badlogic.gdx.utils.Align.center;

/**
 * Created by Uzeer on 2/13/2017.
 */

public class StartScreen implements Screen {

    private Game game;
    private BitmapFont fontHa;
    private Viewport viewport;
    private Stage stage;

    boolean leftPressed;
    boolean rightPressed;
    Image rightImg;
    Image leftImg;

    public StartScreen(Game game){
        this.game = game;
        viewport = new FitViewport(FunGame.V_WIDTH1, FunGame.V_HEIGHT1, new OrthographicCamera());
        stage = new Stage(viewport, ((FunGame)game).batch);
        Gdx.input.setInputProcessor(stage);

        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.RED);

        Table table = new Table();
        table.center();
        table.setFillParent(true);


        Label gameOverLabel = new Label("Select The Character", font);
        table.add(gameOverLabel).padLeft(300).padBottom(15);
        table.row();


        leftImg = new Image(new Texture("sub.png"));
        leftImg.setSize(280, 450);
        leftImg.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                leftPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                leftPressed = false;
            }
        });

        rightImg = new Image(new Texture("ala.png"));
        rightImg.setSize(280, 450);
        rightImg.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                rightPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                rightPressed = false;
            }
        });


        table.add(leftImg).size(leftImg.getWidth(), leftImg.getHeight()).padRight(25);
        table.add(rightImg).size(rightImg.getWidth(), rightImg.getHeight());

        stage.addActor(table);

        Player.num = 0;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();

        if(rightPressed) {
            FunGame.player2Selected = true;
            game.setScreen(new LevelSelectScreen(game));
            dispose();
        } else if(leftPressed) {
            FunGame.player2Selected = false;
            game.setScreen(new LevelSelectScreen(game));
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }

}
