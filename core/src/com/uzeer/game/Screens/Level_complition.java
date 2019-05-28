package com.uzeer.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.uzeer.game.FunGame;
import com.uzeer.game.Scenes.Hud;
import com.uzeer.game.Sprites.Player;
import com.uzeer.game.Sprites.ginnie;

/**
 * Created by uzeer on 1/31/2017.
 */
public class Level_complition implements Screen {
    private FunGame game;
    private float stateTime;
    private ginnie ginnies;
    private OrthographicCamera camera;
    private Viewport gamePort;
    Texture texture;
    private Music music;
    private Stage stage;
    private Viewport viewport;

    public Level_complition(FunGame game) {

        this.game = game;
        viewport = new FitViewport(FunGame.V_WIDTH1, FunGame.V_HEIGHT1, new OrthographicCamera());
        stage = new Stage(viewport, (game).batch);
        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.RED);

        Table table = new Table();
        table.center();
        table.setFillParent(true);

        Label highScore = new Label("High Score: " + String.valueOf(Hud.highScore()), font);
        Label coinsCollected = new Label("Coins Collected: " + String.valueOf(Hud.coins), font);
        Label gameOverLabel = new Label("Hurray Level Complete!", font);
        Label playAgainLabel = new Label("Click To Play Nex Level", font);

        table.bottom().padBottom(200);
        table.add(gameOverLabel).expandX();
        table.row();
        table.add(coinsCollected);
        table.row();
        table.row();
        table.add(highScore);
        table.row();
        table.add(playAgainLabel).expandX().padTop(10f);

        stage.addActor(table);

        texture = new Texture("levelComplete2.png");
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        camera = new OrthographicCamera();

        gamePort = new FitViewport(FunGame.V_WIDTH1 / FunGame.PPM, FunGame.V_HEIGHT1 / FunGame.PPM, camera);
        //gamePort = new FitViewport(FunGame.V_WIDTH1, FunGame.V_HEIGHT1, camera);

        camera.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);
        //camera.position.set(0, 0, 0);

        ginnies = new ginnie(this, (FunGame.V_WIDTH / 2) / FunGame.PPM, (FunGame.V_HEIGHT / 2) / FunGame.PPM);

        stateTime = 0;

        music = FunGame.manager.get("sounds/powerUp.mp3", Music.class);
        music.setVolume(.9f);
        music.setLooping(true);
        music.play();
    }

    @Override
    public void show() {

    }

    public void update(float dt){
        ginnies.update(dt);
    }


    @Override
    public void render(float delta) {
        update(delta);

        //clearing the screen
        Gdx.gl.glClearColor(0, 0, 0, 55);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.setProjectionMatrix(camera.combined);


        //drawing Ginnie
        game.batch.begin();

        game.batch.draw(texture, 0, 0, camera.viewportWidth, camera.viewportHeight);

        ginnies.draw(game.batch);

        game.batch.end();


        if(Gdx.input.justTouched() && FunGame.SecondScreen) {
            music.stop();
            game.setScreen(new SecondStage(game));
            dispose();
        }

        if(Gdx.input.justTouched() && FunGame.PlayScreen) {
            music.stop();
            game.setScreen(new PlayScreen(game));
            dispose();
        }

        stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
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
        ginnies.dispose();
        texture.dispose();
        stage.dispose();
    }
}
