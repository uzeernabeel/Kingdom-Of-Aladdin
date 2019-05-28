package com.uzeer.game.Screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.uzeer.game.FunGame;
import com.uzeer.game.Scenes.Hud;
import com.uzeer.game.Sprites.BadGuy;
import com.uzeer.game.Sprites.BulletFinal;
import com.uzeer.game.Sprites.BulletFinal2;
import com.uzeer.game.Sprites.Enemy;
import com.uzeer.game.Sprites.Jasmine;
import com.uzeer.game.Sprites.Player;
import com.uzeer.game.Sprites.Player2;
import com.uzeer.game.Tools.B2WorldCreator;
import com.uzeer.game.Tools.Controller;
import com.uzeer.game.Tools.WorldContactListner;

import java.util.ArrayList;

import static com.badlogic.gdx.Gdx.app;

/**
 * Created by Uzeer on 1/27/2017.
 */

public class SecondStage implements Screen {
    private FunGame game;

    //Take in Texutres
    private TextureAtlas atlas;
    private TextureAtlas atlas2;
    private Texture texture;

    //game camera
    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private Hud hud;

    //Tiled Map items
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    //Box2d variables
    private World world;
    private Box2DDebugRenderer b2dr;
    private B2WorldCreator creator;

    //player and characters
    public Player player;
    public Player2 player2;
    private Jasmine jasmine;
    private BulletFinal bulletFinal;
    private BulletFinal2 bulletFinal2;

    //other stuff
    private Controller controller;
    private Music music;
    private boolean playerIsTouchingTheGround;
    private float maxPosition;
    private float minPosition;
    public static final float TIMER = 0.5f;
    private float shootTimer;
    private float time;



    public SecondStage(FunGame game){
        atlas = new TextureAtlas("sprite sheet2.pack");
        atlas2 = new TextureAtlas("stuff.pack");
        if(!FunGame.player2Selected)
            texture = new Texture("hero.png");
        this.game = game;

        gamecam = new OrthographicCamera();
        gamePort = new FitViewport(FunGame.V_WIDTH1 / FunGame.PPM, FunGame.V_HEIGHT1 / FunGame.PPM, gamecam);
        hud = new Hud(game.batch);

        mapLoader = new TmxMapLoader();

        FunGame.chances = 2; // chances = 2

        //stage selecting # 4, 5, 6.
        if(FunGame.secondScreenStages == 1)
            map = mapLoader.load("Small4.tmx");
        if(FunGame.secondScreenStages == 2)
            map = mapLoader.load("Small5.tmx");
        if(FunGame.secondScreenStages == 3)
            map = mapLoader.load("Small6.tmx");

        //renderer of the map
        renderer = new OrthogonalTiledMapRenderer(map, 1 / FunGame.PPM);
        gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2 , 0);
        gamecam.position.x = (gamePort.getScreenWidth() / 2) + 4.25f;

        world = new World(new Vector2(0, -10), true);
        b2dr = new Box2DDebugRenderer();

        controller = new Controller(game.batch);

        creator = new B2WorldCreator(this);

        //crating player and bullet
        if(FunGame.player2Selected) {
            player2 = new Player2(this);
            bulletFinal = new BulletFinal(this, 5, 70);
        }
        else {
            player = new Player(this);
            bulletFinal2 = new BulletFinal2(this, 5, 70);
        }

        world.setContactListener(new WorldContactListner());

        maxPosition = 0;
        minPosition = 0;


        FunGame.manager.get("sounds/welcome.mp3", Sound.class).play();
        music = FunGame.manager.get("sounds/FinalGameBackground.mp3", Music.class);
        music.setVolume(.9f);
        music.setLooping(true);
        music.play();
    }

    public TextureAtlas getAtlas(){
        return atlas;
    }

    public TextureAtlas getAtlas2(){
        return atlas2;
    }

    public Texture getTexture(){
        return texture;
    }


    private void handleInput(float dt) {
        // android specific code
        if (FunGame.player2Selected) {
            if (player2.currentState != Player2.State.DEAD) {
                if (controller.isRightPressed() && player2.b2body.getLinearVelocity().x <= 2.5)
                    player2.b2body.applyLinearImpulse(new Vector2(0.125f, 0), player2.b2body.getWorldCenter(), true);
                if (controller.isLeftPressed() && player2.b2body.getLinearVelocity().x >= -2.5)
                    player2.b2body.applyLinearImpulse(new Vector2(-0.125f, 0), player2.b2body.getWorldCenter(), true);
                if(player2.IsPlayerOnGround()) {
                    if (controller.isJumpPressed()) {
                        if (FunGame.PlayScreen)
                            player2.b2body.applyLinearImpulse(new Vector2(0, 4.4f), player2.b2body.getWorldCenter(), true);
                        else
                            player2.b2body.applyLinearImpulse(new Vector2(0, 4.85f), player2.b2body.getWorldCenter(), true);
                    }
                }
                if (controller.isBulletPressed() && shootTimer >= TIMER) {
                    shootTimer = 0;
                    FunGame.spacePressed = true;
                    time = 0;
                    if(player2.isFlipX())
                        bulletFinal = new BulletFinal(this, player2.b2body.getPosition().x - .3f, player2.b2body.getPosition().y + .2f);
                    else
                        bulletFinal = new BulletFinal(this, player2.b2body.getPosition().x + .2f, player2.b2body.getPosition().y + .2f);
                }

                if ((player2.IsPlayerOnGround())) {
                    if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
                        if (FunGame.PlayScreen)
                            player2.b2body.applyLinearImpulse(new Vector2(0, 4.4f), player2.b2body.getWorldCenter(), true);
                        else
                            player2.b2body.applyLinearImpulse(new Vector2(0, 4.85f), player2.b2body.getWorldCenter(), true);
                    }
                }
                if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player2.b2body.getLinearVelocity().x <= 2.5)
                    player2.b2body.applyLinearImpulse(new Vector2(0.125f, 0), player2.b2body.getWorldCenter(), true);
                if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && player2.b2body.getLinearVelocity().x >= -2.5)
                    player2.b2body.applyLinearImpulse(new Vector2(-0.125f, 0), player2.b2body.getWorldCenter(), true);
                if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN))
                    player2.b2body.applyLinearImpulse(new Vector2(0, -2f), player2.b2body.getWorldCenter(), true);
                if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && shootTimer >= TIMER) {
                    shootTimer = 0;
                    FunGame.spacePressed = true;
                    time = 0;
                    if(player2.isFlipX())
                        bulletFinal = new BulletFinal(this, player2.b2body.getPosition().x - .3f, player2.b2body.getPosition().y + .2f);
                    else
                        bulletFinal = new BulletFinal(this, player2.b2body.getPosition().x + .2f, player2.b2body.getPosition().y + .2f);
                }
            }
        } else {
            if (player.currentState != Player.State.DEAD) {
                if (controller.isRightPressed() && player.b2body.getLinearVelocity().x <= 2.5)
                    player.b2body.applyLinearImpulse(new Vector2(0.125f, 0), player.b2body.getWorldCenter(), true);
                if (controller.isLeftPressed() && player.b2body.getLinearVelocity().x >= -2.5)
                    player.b2body.applyLinearImpulse(new Vector2(-0.125f, 0), player.b2body.getWorldCenter(), true);
                if(player.IsPlayerOnGround()) {
                    if (controller.isJumpPressed()) {
                        if (FunGame.PlayScreen)
                            player.b2body.applyLinearImpulse(new Vector2(0, 4.4f), player.b2body.getWorldCenter(), true);
                        else
                            player.b2body.applyLinearImpulse(new Vector2(0, 4.85f), player.b2body.getWorldCenter(), true);
                    }
                }
                if (controller.isBulletPressed() && shootTimer >= TIMER) {
                    FunGame.spacePressed = true;
                    time = 0;
                    shootTimer = 0;
                    //player2.fire();
                    if(player.isFlipX())
                        bulletFinal2 = new BulletFinal2(this, player.b2body.getPosition().x - .3f, player.b2body.getPosition().y + .2f);
                    else
                        bulletFinal2 = new BulletFinal2(this, player.b2body.getPosition().x + .2f, player.b2body.getPosition().y + .2f);
                }

                if ((player.IsPlayerOnGround())) {
                    if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
                        if (FunGame.PlayScreen)
                            player.b2body.applyLinearImpulse(new Vector2(0, 4.4f), player.b2body.getWorldCenter(), true);
                        else
                            player.b2body.applyLinearImpulse(new Vector2(0, 4.85f), player.b2body.getWorldCenter(), true);
                    }
                }
                if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 2.5)
                    player.b2body.applyLinearImpulse(new Vector2(0.125f, 0), player.b2body.getWorldCenter(), true);
                if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= -2.5)
                    player.b2body.applyLinearImpulse(new Vector2(-0.125f, 0), player.b2body.getWorldCenter(), true);
                if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN))
                    player.b2body.applyLinearImpulse(new Vector2(0, -2f), player.b2body.getWorldCenter(), true);
                if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && shootTimer >= TIMER) {
                    shootTimer = 0;
                    FunGame.spacePressed = true;
                    time = 0;
                    if(player.isFlipX())
                        bulletFinal2 = new BulletFinal2(this, player.b2body.getPosition().x - .3f, player.b2body.getPosition().y + .2f);
                    else
                        bulletFinal2 = new BulletFinal2(this, player.b2body.getPosition().x + .2f, player.b2body.getPosition().y + .2f);
                }

            }
        }
    }

    public void update(float dt){
        handleInput(dt);
        time += dt;

        world.step(1 / 60f, 6, 2); //important method to calculate all the number every second

        if(FunGame.player2Selected){  //Stage selecting (if) statements and deciding when to complete.
            player2.update(dt);
            if(FunGame.secondScreenStages == 1)
                if (player2.b2body.getPosition().y > 22.965 && player2.b2body.getPosition().x < .35) {// 173
                    FunGame.playScreenStages = 2;
                    levelComplete();
                }
            if(FunGame.secondScreenStages == 2)
                if (player2.b2body.getPosition().y > 20.767 && player2.b2body.getPosition().x < .485) {
                    FunGame.playScreenStages = 3;
                    levelComplete();
                }
            if(FunGame.secondScreenStages == 3)
                if (player2.b2body.getPosition().y > 21.2 && player2.b2body.getPosition().x >= 4.19 && player2.b2body.getPosition().x <= 4.24) {
                    levelComplete();
                }
        } else {
            player.update(dt);
            if(FunGame.secondScreenStages == 1)
                if (player.b2body.getPosition().y > 22.965 && player.b2body.getPosition().x < .35) {// 173
                    FunGame.playScreenStages = 2;
                    levelComplete();
                }
            if(FunGame.secondScreenStages == 2)
                if (player.b2body.getPosition().y > 20.767 && player.b2body.getPosition().x < .485) {
                    FunGame.playScreenStages = 3;
                    levelComplete();
                }
            if(FunGame.secondScreenStages == 3)
                if (player.b2body.getPosition().y > 21.2 && player.b2body.getPosition().x >= 4.19 && player.b2body.getPosition().x <= 4.24) {
                    levelComplete();
                }
        }

        for(Enemy enemy : creator.getEnemies()) {
            enemy.update(dt);
            /*if(FunGame.player2Selected) {
                if (enemy.getY() < player2.getY() + (540 / FunGame.PPM) || enemy.getX() < player2.getX() - (540 / FunGame.PPM))
                enemy.b2body.setActive(true);
            } else {
                if (enemy.getY() < player.getY() + (540 / FunGame.PPM) || enemy.getX() < player.getX() - (540 / FunGame.PPM))
                enemy.b2body.setActive(true);
            }*/
        }

        creator.getJasmine().update(dt);

        if(FunGame.player2Selected)
            bulletFinal.update(dt);
        else
            bulletFinal2.update(dt);

        hud.update(dt);
        shootTimer += dt;

        if(FunGame.player2Selected) {
            if (player2.currentState != Player2.State.DEAD)
                gamecam.position.y = player2.b2body.getPosition().y;

        } else {
            if (player.currentState != Player.State.DEAD)
                gamecam.position.y = player.b2body.getPosition().y;
        }


        gamecam.update();
        renderer.setView(gamecam);

        if(time > 0.3)
            FunGame.spacePressed = false;
    }


    @Override
    public void show() {

    }

    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();
        //b2dr.render(world, gamecam.combined);

        game.batch.setProjectionMatrix(gamecam.combined);

        game.batch.begin(); // Begin

        if(FunGame.player2Selected) {
            player2.draw(game.batch);
            bulletFinal.draw(game.batch);
        }
        else {
            player.draw(game.batch);
            bulletFinal2.draw(game.batch);
        }

        for (Enemy enemy : creator.getEnemies())
            enemy.draw(game.batch);

        creator.getJasmine().draw(game.batch);

        game.batch.end(); // End

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();


        if(Gdx.app.getType() == Application.ApplicationType.Android)
            controller.draw();

        if(gameOver()){
            music.stop();
            game.setScreen(new GameOverScreen(game));
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
        hud.resize(width, height);
        controller.resize(width, height);
    }

    public TiledMap getMap(){
        return map;
    }

    public World getWorld(){
        return world;
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
        map.dispose();
        //renderer.dispose();
        //world.dispose();
        //b2dr.dispose();
        hud.dispose();
        atlas.dispose();
        atlas2.dispose();
    }

    public boolean gameOver(){
        if(FunGame.player2Selected)
            return player2.currentState == Player2.State.DEAD && player2.getStateTimer() > 3;
        else
            return player.currentState == Player.State.DEAD && player.getStateTimer() > 3;
    }

    public void levelComplete(){
        music.stop();
        if(FunGame.secondScreenStages == 3) {
            if(app.getType() == Application.ApplicationType.Desktop) {
                FileHandle file = Gdx.files.local("saveData.txt");
                    file.writeString("6", false);
            }
            if(app.getType() == Application.ApplicationType.Android) {
                FunGame.prefs.putInteger("level", 6);
                FunGame.prefs.flush();
            }
            game.setScreen(new FinishGame(game));
            dispose();
        }
        if(FunGame.secondScreenStages == 2) {
            FunGame.playScreenStages = 3;
            if(app.getType() == Application.ApplicationType.Desktop) {
                FileHandle file = Gdx.files.local("saveData.txt");
                if (!(file.readString().contains("6")))
                    file.writeString("5", false);
            }
            if(app.getType() == Application.ApplicationType.Android) {
                if(!(FunGame.prefs.getInteger("level") == 6)) {
                    FunGame.prefs.putInteger("level", 5);
                    FunGame.prefs.flush();
                }
            }
            game.setScreen(new Level_complition(game));
            dispose();
        }
        if(FunGame.secondScreenStages == 1) {
            FunGame.playScreenStages = 2;
            if(app.getType() == Application.ApplicationType.Desktop) {
                FileHandle file = Gdx.files.local("saveData.txt");
                if (!(file.readString().contains("6")))
                    file.writeString("3", false);
            }
            if(app.getType() == Application.ApplicationType.Android) {
                if(!(FunGame.prefs.getInteger("level") == 6)) {
                    FunGame.prefs.putInteger("level", 3);
                    FunGame.prefs.flush();
                }
            }
            game.setScreen(new Level_complition(game));
            dispose();
        }

        FunGame.PlayScreen = true;
        FunGame.SecondScreen = false;

    }

    public SecondStage getScreen(){
        return this;
    }
}
