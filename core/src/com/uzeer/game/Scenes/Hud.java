package com.uzeer.game.Scenes;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.uzeer.game.FunGame;
import com.uzeer.game.Sprites.Player2;

/**
 * Created by Uzeer on 12/25/2016.
 */

public class Hud implements Disposable{
    public static boolean dead;
    public Stage stage;
    private Viewport viewport;

    private Integer worldTimer;
    private float timeCount;
    protected static Integer score;
    public static Integer coins;

    Label countLabel;
    static Label scoreLabel;
    Label timeLabel;
    Label levelLabel;
    Label worldLabel;
    Label GameLabel;
    Label coin;
    static Label coinLabel;

    Label lives;
    Label chances;
    Label livesNumber;
    static Label chancesNumber;
    private String levelNumber;

    static int chancesLeft;
    private Image pauseImg;


    public Hud(SpriteBatch sb){
        worldTimer = 250;
        timeCount = 0;
        score = 500;
        coins = 0;
        chancesLeft = 2;
        dead = false;

        viewport = new FitViewport(FunGame.V_WIDTH1, FunGame.V_HEIGHT1, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        Table table = new Table();

        table.top();
        table.setFillParent(true);

        countLabel = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel = new Label(String.format("%08d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        coinLabel = new Label(String.format("%03d", coins), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        chancesNumber = new Label(String.format("%01d", chancesLeft), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        livesNumber = new Label(String.format("%01d", FunGame.lives), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeLabel = new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        GameLabel = new Label("Fun", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        chances = new Label("Chances Left", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        coin = new Label("Stars: ", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        lives = new Label("Lives: ", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        table.add(GameLabel).expandX().padTop(3);
        table.add(timeLabel).expandX().padTop(3);
        table.add(chances).expandX().padTop(3);
        table.add(coin).expandX().padTop(3);
        table.row();
        table.add(scoreLabel).expandX();
        table.add(countLabel).expandX();
        table.add(chancesNumber).expandX();
        table.add(coinLabel).expandX();

        stage.addActor(table);

    }

    public void update(float dt){
        timeCount += dt;
        if(timeCount >= 1){
            worldTimer--;
            countLabel.setText(String.format("%03d", worldTimer));
            timeCount = 0;
        }

        if(worldTimer <= 0)
            dead = true;

    }

    public static void addScore(int value){
        score += value;
        scoreLabel.setText(String.format("%08d", score));
    }


    @Override
    public void dispose() {
        stage.dispose();
    }

    public static void addCoin(int value){
        coins += value;
        coinLabel.setText(String.format("%03d", coins));
    }

    public static void chances(int i) {
        chancesLeft = i;
        chancesNumber.setText(String.format("%01d", chancesLeft));
    }

    public void resize(int width, int height){
        viewport.update(width, height);
    }

    public static int highScore(){
        return score;
    }
}
