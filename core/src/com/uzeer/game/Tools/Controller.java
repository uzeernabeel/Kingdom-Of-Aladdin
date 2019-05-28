package com.uzeer.game.Tools;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.uzeer.game.FunGame;

/**
 * Created by Uzeer on 2/17/2017.
 */

public class Controller {
    private Viewport viewport;
    private Stage stage;
    private boolean rightPressed, leftPressed, jumpPressed, bulletPressed;
    private OrthographicCamera cam;

    public Controller(Batch batch){
        cam = new OrthographicCamera();
        viewport = new FitViewport(FunGame.V_WIDTH2, FunGame.V_HEIGHT2, cam);
        stage = new Stage(viewport, batch);
        if(Gdx.app.getType() == Application.ApplicationType.Android)
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();


        Image leftImg = new Image(new Texture("controller/left.png"));
        leftImg.setSize(95, 95);
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

        Image rightImg = new Image(new Texture("controller/right.png"));
        rightImg.setSize(95, 95);
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

        final Image jumpImg = new Image(new Texture("controller/jump.png"));
        jumpImg.setSize(95, 95);
        jumpImg.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                jumpPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                jumpPressed = false;
            }
        });

        Image bulletImg = new Image(new Texture("controller/bullet.png"));
        bulletImg.setSize(95, 95);
        bulletImg.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                bulletPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                bulletPressed = false;
            }
        });

        table.left().bottom();
        table.add(leftImg).size(leftImg.getWidth(), leftImg.getHeight());
        table.padLeft(15);
        table.add().padRight(25);

        table.add(rightImg).size(rightImg.getWidth(), rightImg.getHeight());
        table.add().padRight(300);

        table.padLeft(15);
        table.padBottom(5);

        //table.right().bottom();
        table.add(jumpImg).size(jumpImg.getWidth(), jumpImg.getHeight());
        table.add().padRight(25);
        table.add(bulletImg).size(bulletImg.getWidth(), bulletImg.getHeight());

        table.padRight(10);
        table.padBottom(5);


        stage.addActor(table);
    }

    public void draw(){
        stage.draw();
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    public boolean isJumpPressed() {
        return jumpPressed;
    }

    public boolean isBulletPressed() {
        return bulletPressed;
    }

    public void resize(int width, int height){
        viewport.update(width, height);
    }
}
