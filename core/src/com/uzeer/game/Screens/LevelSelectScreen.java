package com.uzeer.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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


public class LevelSelectScreen implements Screen {

    private Game game;
    private Viewport viewport;
    private Stage stage;

    private boolean onePressed;
    private boolean twoPressed;
    private boolean threePressed;
    private boolean fourPressed;
    private boolean fivePressed;
    private boolean sixPressed;

    private Image oneImg;
    private Image twoImg;
    private Image threeImg;
    private Image fourImg;
    private Image fiveImg;
    private Image sixImg;
    private Texture texture;
    private Texture texture2;

    private String string;
    private float time;

    public LevelSelectScreen(Game game){
    this.game = game;
    viewport = new FitViewport(FunGame.V_WIDTH1, FunGame.V_HEIGHT1, new OrthographicCamera());
    stage = new Stage(viewport, ((FunGame)game).batch);
    Gdx.input.setInputProcessor(stage);
        string = "Previous Levels not Completed";

    texture = new Texture("levelSelect.png");
    texture2 = new Texture("levelSelect1.png");

    Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.RED);

    Table table = new Table();
    table.center();
    table.setFillParent(true);


        Label gameOverLabel = new Label("Select The Level", font);
        Label notYet = new Label(string, font);
        table.add(gameOverLabel).center();
        table.row();
        Label note = new Label("Hard Levels", font);



    oneImg = new Image(new TextureRegion(texture, 0, 0, 242, 314));
    oneImg.setSize(175, 205);
    oneImg.addListener(new InputListener(){
        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            onePressed = true;
            return true;
        }

        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            onePressed = false;
        }
    });

        twoImg = new Image(new TextureRegion(texture, 243, 0, 242, 314));
        twoImg.setSize(175, 205);
        twoImg.addListener(new InputListener(){
        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            twoPressed = true;
            return true;
        }

        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            twoPressed = false;
        }
    });

        threeImg = new Image(new TextureRegion(texture, 0, 315, 242, 314));
        threeImg.setSize(175, 205);
        threeImg.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                threePressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                threePressed = false;
            }
        });

        fourImg = new Image(new TextureRegion(texture, 243, 315, 242, 314));
        fourImg.setSize(175, 205);
        fourImg.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                fourPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                fourPressed = false;
            }
        });

        fiveImg = new Image(new TextureRegion(texture2, 0, 380, 175, 175));
        fiveImg.setSize(175, 205);
        fiveImg.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                fivePressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                fivePressed = false;
            }
        });

        sixImg = new Image(new TextureRegion(texture2, 195, 380, 175, 175));
        sixImg.setSize(175, 205);
        sixImg.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                sixPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                sixPressed = false;
            }
        });

        table.add(oneImg).size(oneImg.getWidth(), oneImg.getHeight()).padRight(15).padLeft(10).padBottom(15);
        table.add(twoImg).size(twoImg.getWidth(), twoImg.getHeight()).padBottom(15);
        table.add(threeImg).size(threeImg.getWidth(), threeImg.getHeight()).padRight(15).padLeft(15).padBottom(15);
        table.add(fourImg).size(fourImg.getWidth(), fourImg.getHeight()).padBottom(15).padLeft(15);
        table.add().row();
        table.add().row();
        table.add(note);
        table.add(fiveImg).size(fiveImg.getWidth(), fiveImg.getHeight()).padRight(15).padBottom(15).padLeft(40);
        table.add(sixImg).size(sixImg.getWidth(), sixImg.getHeight()).padBottom(15);

        stage.addActor(table);

        Player.num = 0;
}

    @Override
    public void show() {

    }

    public void update(float dt){
        time += dt;

    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();

        if(onePressed) {
            if(FunGame.LEVEL1) {
                FunGame.playScreenStages = 1;
                FunGame.PlayScreen = true;
                FunGame.SecondScreen = false;
                game.setScreen(new PlayScreen((FunGame) game));
                dispose();
            }
        } else if(twoPressed) {
            if(FunGame.LEVEL2) {
                FunGame.secondScreenStages = 1;
                FunGame.SecondScreen = true;
                FunGame.PlayScreen = false;
                game.setScreen(new SecondStage((FunGame) game));
                dispose();
            }
        } else if(threePressed) {
            if(FunGame.LEVEL3) {
                FunGame.playScreenStages = 2;
                FunGame.PlayScreen = true;
                FunGame.SecondScreen = false;
                game.setScreen(new PlayScreen((FunGame) game));
                dispose();
            }
        } else if(fourPressed) {
            if(FunGame.LEVEL4) {
                FunGame.secondScreenStages = 2;
                FunGame.SecondScreen = true;
                FunGame.PlayScreen = false;
                game.setScreen(new SecondStage((FunGame) game));
                dispose();
            }
        } else if(fivePressed) {
            if (FunGame.LEVEL5) {
                FunGame.playScreenStages = 3;
                FunGame.SecondScreen = false;
                FunGame.PlayScreen = true;
                game.setScreen(new PlayScreen((FunGame) game));
                dispose();
            }
        } else if(sixPressed) {
            if (FunGame.LEVEL6) {
                FunGame.secondScreenStages = 3;
                FunGame.SecondScreen = true;
                FunGame.PlayScreen = false;
                game.setScreen(new SecondStage((FunGame) game));
                dispose();
            }
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
