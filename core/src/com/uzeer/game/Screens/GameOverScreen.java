package com.uzeer.game.Screens;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.uzeer.game.FunGame;
import com.uzeer.game.Scenes.Hud;
import com.uzeer.game.Sprites.Player;
import com.uzeer.game.Sprites.Player2;

/**
 * Created by uzeer on 1/24/2017.
 */
public class GameOverScreen implements Screen {

    private Viewport viewport;
    private Stage stage;
    private FunGame game;
    BitmapFont fontHa;
    String string;
    private Music music;


    public GameOverScreen(FunGame game) {
        this.game = game;
        fontHa = new BitmapFont();
        viewport = new FitViewport(FunGame.V_WIDTH1, FunGame.V_HEIGHT1, new OrthographicCamera());
        stage = new Stage(viewport, (game).batch);

        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.RED);

        string = "Game Over! press to play again.";
        Table table = new Table();
        table.center();
        table.setFillParent(true);

        Label gameOverLabel = new Label("You Are Dead. But, Show Them What You Are Made Of!", font);
        Label playAgainLabel = new Label("Click To Play Again", font);

        table.add(gameOverLabel).expandX();
        table.row();
        table.add(playAgainLabel).expandX().padTop(10f);

        stage.addActor(table);

        Player.num = 0;

        FunGame.manager.get("sounds/Decline.wav", Sound.class).loop();
        FunGame.manager.get("sounds/Decline.wav", Sound.class).play();


    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        if(Gdx.input.justTouched()) {
            FunGame.manager.get("sounds/Decline.wav", Sound.class).stop();
            if(FunGame.PlayScreen)
                game.setScreen(new PlayScreen(game));
            if(FunGame.SecondScreen)
                game.setScreen(new SecondStage(game));

            dispose();
        }

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {

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
