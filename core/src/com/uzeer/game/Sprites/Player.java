package com.uzeer.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.uzeer.game.FunGame;
import com.uzeer.game.Scenes.Hud;
import com.uzeer.game.Screens.PlayScreen;
import com.uzeer.game.Screens.SecondStage;

/**
 * Created by Uzeer on 12/25/2016.
 */

public class Player extends Sprite {

    public static float checkPointX;

    public enum State { FALLING, JUMPING, STANDING, RUNNING, STANDING2, DEAD, THROWING }
    public State currentState;
    public State previousState;
    public World world;
    public Body b2body;
    private Animation playerStand;
    private TextureRegion playerFalling;
    private TextureRegion playerDuck;
    private Animation playerIsDead;
    private Animation playerRun;
    private Animation playerJump;
    private Animation playerStand2;
    private Animation playerThrow;
    private float stateTimer;
    private boolean runningRight;
    protected Fixture fixture;
    public static boolean playerDead;
    public static boolean spacePressed;
    public boolean bulletMusic;
    private BodyDef bdef;
    float time;

    public Player(PlayScreen screen){
        this.world = screen.getWorld();
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0f;
        time = 0;
        runningRight = true;
        FunGame.lives = 3;

        playerDead = false;
        spacePressed = false;
        bulletMusic = false;

        Array<TextureRegion> frames = new Array<TextureRegion>();

        for(int i = 1; i < 10; i++) {
            if (i == 1)
                frames.add(new TextureRegion(screen.getTexture(), 0, 608, 85, 170));
            else if (i == 2)
                frames.add(new TextureRegion(screen.getTexture(), 147, 608, 85, 170));
            else if (i == 3)
                frames.add(new TextureRegion(screen.getTexture(), 298, 608, 85, 170));
            else if (i == 4)
                frames.add(new TextureRegion(screen.getTexture(), 415, 608, 85, 170));
            else if (i == 5)
                frames.add(new TextureRegion(screen.getTexture(), 557, 608, 85, 170));
            else if (i == 6)
                frames.add(new TextureRegion(screen.getTexture(), 705, 608, 85, 170));
            else if (i == 7)
                frames.add(new TextureRegion(screen.getTexture(), 869, 608, 85, 170));
            else if (i == 8)
                frames.add(new TextureRegion(screen.getTexture(), 1026, 608, 85, 170));
            else if (i == 9)
                frames.add(new TextureRegion(screen.getTexture(), 1165, 608, 85, 170));
        }
        playerRun = new Animation(0.1f, frames);
        frames.clear();

        for (int i = 1; i < 4; i++) {
            if (i == 1)
                frames.add(new TextureRegion(screen.getTexture(), 0, 408, 85, 170));
            else if (i == 2)
                frames.add(new TextureRegion(screen.getTexture(), 127, 408, 85, 170));
            else if (i == 3)
                frames.add(new TextureRegion(screen.getTexture(), 275, 408, 85, 170));
        }

        playerThrow = new Animation(0.1f, frames);
        frames.clear();

        for (int i = 1; i < 5; i++) {
            if (i == 1)
                frames.add(new TextureRegion(screen.getTexture(), 1202, 826, 85, 170));
            else if (i == 2)
                frames.add(new TextureRegion(screen.getTexture(), 1317, 826, 85, 170));
            else if (i == 3)
                frames.add(new TextureRegion(screen.getTexture(), 1432, 826, 85, 170));
            else if (i == 4)
                frames.add(new TextureRegion(screen.getTexture(), 1549, 826, 85, 170));
        }

        playerStand2 = new Animation(0.1f, frames);
        frames.clear();


        for(int i = 1; i < 11; i++) {
            if (i == 1)
                frames.add(new TextureRegion(screen.getTexture(), 0, 826, 85, 170));
            else if (i == 2)
                frames.add(new TextureRegion(screen.getTexture(), 119, 826, 85, 170));
            else if (i == 3)
                frames.add(new TextureRegion(screen.getTexture(), 247, 826, 85, 170));
            else if (i == 4)
                frames.add(new TextureRegion(screen.getTexture(), 367, 826, 85, 170));
            else if (i == 5)
                frames.add(new TextureRegion(screen.getTexture(), 481, 826, 85, 170));
            else if (i == 6)
                frames.add(new TextureRegion(screen.getTexture(), 602, 826, 85, 170));
            else if (i == 7)
                frames.add(new TextureRegion(screen.getTexture(), 714, 826, 85, 170));
            else if (i == 8)
                frames.add(new TextureRegion(screen.getTexture(), 837, 826, 85, 170));
            else if (i == 9)
                frames.add(new TextureRegion(screen.getTexture(), 955, 826, 85, 170));
            else if (i == 10)
                frames.add(new TextureRegion(screen.getTexture(), 1083, 826, 85, 170));
        }

        playerStand = new Animation(0.1f, frames);
        frames.clear();

        for(int i = 3; i < 11; i++) {
            if (i == 1)
                frames.add(new TextureRegion(screen.getTexture(), 0, 216, 85, 170));
            else if (i == 2)
                frames.add(new TextureRegion(screen.getTexture(), 118, 290, 85, 170));
            else if (i == 3)
                frames.add(new TextureRegion(screen.getTexture(), 280, 290, 85, 170));
            else if (i == 4)
                frames.add(new TextureRegion(screen.getTexture(), 413, 290, 85, 170));
            else if (i == 5)
                frames.add(new TextureRegion(screen.getTexture(), 528, 290, 85, 170));
            else if (i == 6)
                frames.add(new TextureRegion(screen.getTexture(), 657, 290, 85, 170));
            else if (i == 7)
                frames.add(new TextureRegion(screen.getTexture(), 778, 290, 85, 170));
            else if (i == 8)
                frames.add(new TextureRegion(screen.getTexture(), 887, 290, 85, 170));
            else if (i == 9)
                frames.add(new TextureRegion(screen.getTexture(), 1009, 290, 85, 170));
            else if (i == 10)
                frames.add(new TextureRegion(screen.getTexture(), 1125, 216, 85, 170));
        }


        playerJump = new Animation(0.1f, frames);
        frames.clear();

        for(int i = 1; i < 12; i++) {
            if (i == 1)
                frames.add(new TextureRegion(screen.getTexture(), 0, -8, 85, 170));
            else if (i == 2)
                frames.add(new TextureRegion(screen.getTexture(), 142, -8, 85, 170));
            else if (i == 3)
                frames.add(new TextureRegion(screen.getTexture(), 286, -8, 85, 170));
            else if (i == 4)
                frames.add(new TextureRegion(screen.getTexture(), 438, -8, 85, 170));
            else if (i == 5)
                frames.add(new TextureRegion(screen.getTexture(), 599, -8, 85, 170));
            else if (i == 6)
                frames.add(new TextureRegion(screen.getTexture(), 738, -8, 85, 170));
            else if (i == 7)
                frames.add(new TextureRegion(screen.getTexture(), 883, -8, 85, 170));
            else if (i == 8)
                frames.add(new TextureRegion(screen.getTexture(), 1028, -8, 85, 170));
            else if (i == 9)
                frames.add(new TextureRegion(screen.getTexture(), 1164, -8, 85, 170));
            else if (i == 10)
                frames.add(new TextureRegion(screen.getTexture(), 1281, -8, 87, 170));
            else if (i == 11)
                frames.add(new TextureRegion(screen.getTexture(), 1673, -8, 86, 170));
        }

        playerIsDead = new Animation(0.1f, frames);
        frames.clear();

        playerFalling = new TextureRegion(screen.getTexture(), 1125, 216, 85, 170);

        definePlayer();
        setBounds(0, 0, 37 / FunGame.PPM, 55 / FunGame.PPM);
    }

    public Player(SecondStage screen){
        super(screen.getAtlas().findRegion("player"));
        this.world = screen.getWorld();
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0f;
        runningRight = true;

        playerDead = false;
        spacePressed = false;

        Array<TextureRegion> frames = new Array<TextureRegion>();

        for(int i = 1; i < 10; i++) {
            if (i == 1)
                frames.add(new TextureRegion(screen.getTexture(), 0, 608, 85, 170));
            else if (i == 2)
                frames.add(new TextureRegion(screen.getTexture(), 147, 608, 85, 170));
            else if (i == 3)
                frames.add(new TextureRegion(screen.getTexture(), 298, 608, 85, 170));
            else if (i == 4)
                frames.add(new TextureRegion(screen.getTexture(), 415, 608, 85, 170));
            else if (i == 5)
                frames.add(new TextureRegion(screen.getTexture(), 557, 608, 85, 170));
            else if (i == 6)
                frames.add(new TextureRegion(screen.getTexture(), 705, 608, 85, 170));
            else if (i == 7)
                frames.add(new TextureRegion(screen.getTexture(), 869, 608, 85, 170));
            else if (i == 8)
                frames.add(new TextureRegion(screen.getTexture(), 1026, 608, 85, 170));
            else if (i == 9)
                frames.add(new TextureRegion(screen.getTexture(), 1165, 608, 85, 170));
        }
        playerRun = new Animation(0.1f, frames);
        frames.clear();

        for (int i = 1; i < 4; i++) {
            if (i == 1)
                frames.add(new TextureRegion(screen.getTexture(), 0, 408, 85, 170));
            else if (i == 2)
                frames.add(new TextureRegion(screen.getTexture(), 127, 408, 85, 170));
            else if (i == 3)
                frames.add(new TextureRegion(screen.getTexture(), 275, 408, 85, 170));
        }

        playerThrow = new Animation(0.1f, frames);
        frames.clear();

        for (int i = 1; i < 5; i++) {
            if (i == 1)
                frames.add(new TextureRegion(screen.getTexture(), 1202, 826, 85, 170));
            else if (i == 2)
                frames.add(new TextureRegion(screen.getTexture(), 1317, 826, 85, 170));
            else if (i == 3)
                frames.add(new TextureRegion(screen.getTexture(), 1432, 826, 85, 170));
            else if (i == 4)
                frames.add(new TextureRegion(screen.getTexture(), 1549, 826, 85, 170));
        }

        playerStand2 = new Animation(0.1f, frames);
        frames.clear();


        for(int i = 1; i < 11; i++) {
            if (i == 1)
                frames.add(new TextureRegion(screen.getTexture(), 0, 826, 85, 170));
            else if (i == 2)
                frames.add(new TextureRegion(screen.getTexture(), 119, 826, 85, 170));
            else if (i == 3)
                frames.add(new TextureRegion(screen.getTexture(), 247, 826, 85, 170));
            else if (i == 4)
                frames.add(new TextureRegion(screen.getTexture(), 367, 826, 85, 170));
            else if (i == 5)
                frames.add(new TextureRegion(screen.getTexture(), 481, 826, 85, 170));
            else if (i == 6)
                frames.add(new TextureRegion(screen.getTexture(), 602, 826, 85, 170));
            else if (i == 7)
                frames.add(new TextureRegion(screen.getTexture(), 714, 826, 85, 170));
            else if (i == 8)
                frames.add(new TextureRegion(screen.getTexture(), 837, 826, 85, 170));
            else if (i == 9)
                frames.add(new TextureRegion(screen.getTexture(), 955, 826, 85, 170));
            else if (i == 10)
                frames.add(new TextureRegion(screen.getTexture(), 1083, 826, 85, 170));
        }

        playerStand = new Animation(0.1f, frames);
        frames.clear();

        for(int i = 3; i < 11; i++) {
            if (i == 1)
                frames.add(new TextureRegion(screen.getTexture(), 0, 216, 85, 170));
            else if (i == 2)
                frames.add(new TextureRegion(screen.getTexture(), 118, 290, 85, 170));
            else if (i == 3)
                frames.add(new TextureRegion(screen.getTexture(), 280, 290, 85, 170));
            else if (i == 4)
                frames.add(new TextureRegion(screen.getTexture(), 413, 290, 85, 170));
            else if (i == 5)
                frames.add(new TextureRegion(screen.getTexture(), 528, 290, 85, 170));
            else if (i == 6)
                frames.add(new TextureRegion(screen.getTexture(), 657, 290, 85, 170));
            else if (i == 7)
                frames.add(new TextureRegion(screen.getTexture(), 778, 290, 85, 170));
            else if (i == 8)
                frames.add(new TextureRegion(screen.getTexture(), 887, 290, 85, 170));
            else if (i == 9)
                frames.add(new TextureRegion(screen.getTexture(), 1009, 290, 85, 170));
            else if (i == 10)
                frames.add(new TextureRegion(screen.getTexture(), 1125, 216, 85, 170));
        }

        /*for(int i = 3; i < 11; i++) {
            if (i == 1)
                frames.add(new TextureRegion(screen.getTexture(), 0, 216, 85, 170));
            else if (i == 2)
                frames.add(new TextureRegion(screen.getTexture(), 118, 216, 85, 170));
            else if (i == 3)
                frames.add(new TextureRegion(screen.getTexture(), 280, 216, 85, 170));
            else if (i == 4)
                frames.add(new TextureRegion(screen.getTexture(), 413, 216, 85, 170));
            else if (i == 5)
                frames.add(new TextureRegion(screen.getTexture(), 528, 216, 85, 170));
            else if (i == 6)
                frames.add(new TextureRegion(screen.getTexture(), 657, 216, 85, 170));
            else if (i == 7)
                frames.add(new TextureRegion(screen.getTexture(), 778, 216, 85, 170));
            else if (i == 8)
                frames.add(new TextureRegion(screen.getTexture(), 887, 216, 85, 170));
            else if (i == 9)
                frames.add(new TextureRegion(screen.getTexture(), 1009, 216, 85, 170));
            else if (i == 10)
                frames.add(new TextureRegion(screen.getTexture(), 1125, 216, 85, 170));
        } */

        playerJump = new Animation(0.1f, frames);
        frames.clear();

        for(int i = 1; i < 12; i++) {
            if (i == 1)
                frames.add(new TextureRegion(screen.getTexture(), 0, -8, 85, 170));
            else if (i == 2)
                frames.add(new TextureRegion(screen.getTexture(), 142, -8, 85, 170));
            else if (i == 3)
                frames.add(new TextureRegion(screen.getTexture(), 286, -8, 85, 170));
            else if (i == 4)
                frames.add(new TextureRegion(screen.getTexture(), 438, -8, 85, 170));
            else if (i == 5)
                frames.add(new TextureRegion(screen.getTexture(), 599, -8, 85, 170));
            else if (i == 6)
                frames.add(new TextureRegion(screen.getTexture(), 738, -8, 85, 170));
            else if (i == 7)
                frames.add(new TextureRegion(screen.getTexture(), 883, -8, 85, 170));
            else if (i == 8)
                frames.add(new TextureRegion(screen.getTexture(), 1028, -8, 85, 170));
            else if (i == 9)
                frames.add(new TextureRegion(screen.getTexture(), 1164, -8, 85, 170));
            else if (i == 10)
                frames.add(new TextureRegion(screen.getTexture(), 1281, -8, 87, 170));
            else if (i == 11)
                frames.add(new TextureRegion(screen.getTexture(), 1673, -8, 86, 170));
        }

        playerIsDead = new Animation(0.1f, frames);
        frames.clear();

        playerFalling = new TextureRegion(screen.getTexture(), 1125, 216, 85, 170);

        definePlayer();
        setBounds(0, 0, 37 / FunGame.PPM, 60 / FunGame.PPM);
    }


    public void update(float dt){
        time += dt;
        if(FunGame.PlayScreen) {
            if (runningRight)
                setPosition((b2body.getPosition().x - getWidth() / 2) + 5 / FunGame.PPM, (b2body.getPosition().y - getHeight() / 2) + 22 / FunGame.PPM);
            else
                setPosition((b2body.getPosition().x - getWidth() / 2) - 1 / FunGame.PPM, (b2body.getPosition().y - getHeight() / 2) + 22 / FunGame.PPM);
            setRegion(getFrame(dt));
        } else {
            if (runningRight)
                setPosition((b2body.getPosition().x - getWidth() / 2) + 5 / FunGame.PPM, (b2body.getPosition().y - getHeight() / 2) + 25 / FunGame.PPM);
            else
                setPosition((b2body.getPosition().x - getWidth() / 2) - 1 / FunGame.PPM, (b2body.getPosition().y - getHeight() / 2) + 25 / FunGame.PPM);
            setRegion(getFrame(dt));
        }

        if(b2body.getPosition().y < -1f)
            playerDead = true;

        if(Hud.dead)
            playerDead = true;
    }


    public TextureRegion getFrame(float dt) {
        currentState = getState();

        TextureRegion region;
        switch (currentState) {
            case JUMPING:
                region = playerJump.getKeyFrame(stateTimer, false);
                break;
            case RUNNING:
                region = playerRun.getKeyFrame(stateTimer, true);
                time = 0;
                break;
            case FALLING:
                region = playerFalling;
                break;
            case DEAD:
                region = playerIsDead.getKeyFrame(stateTimer, false);
                break;
            case THROWING:
                region = playerThrow.getKeyFrame(stateTimer, false);
                break;
            case STANDING2:
                region = playerStand2.getKeyFrame(stateTimer, false);
                break;
            case STANDING:
            default:
                region = playerStand.getKeyFrame(stateTimer, true);
                break;
        }
        if ((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()) {
            region.flip(true, false);
            runningRight = false;
        }
        else if ((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()) {
            region.flip(true, false);
            runningRight = true;
        }

        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;
        return region;
    }



    private State getState() {
        if(playerDead)
            return State.DEAD;
        else if(b2body.getLinearVelocity().y > 0 || (b2body.getLinearVelocity().y < 0 && previousState == State.JUMPING) )
            return State.JUMPING;
        else if(b2body.getLinearVelocity().y < 0)
            return State.FALLING;
        else if(b2body.getLinearVelocity().x != 0)
            return State.RUNNING;
        else if(FunGame.spacePressed)
            return State.THROWING;
        else if(b2body.getLinearVelocity().x == 0 && time > 6)
            return State.STANDING2;
        else
            return State.STANDING;
    }


    public void definePlayer() {
        bdef = new BodyDef();
        bdef.position.set(32 / FunGame.PPM, 32 / FunGame.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(8 / FunGame.PPM, 14 / FunGame.PPM, new Vector2(0 / FunGame.PPM, 10 / FunGame.PPM), 0);

        fdef.filter.categoryBits = FunGame.PLAYER_BIT;
        fdef.filter.maskBits = FunGame.DEFAULT_BIT |
                FunGame.COIN_BIT |
                FunGame.ENEMY_BIT |
                FunGame.GROUND_BIT |
                FunGame.JASMINE_BIT |
                FunGame.ENEMY_HEAD_BIT;

        fdef.shape = shape;

        b2body.createFixture(fdef).setUserData(this);

    }

    public boolean IsPlayerOnGround(){
        return !(b2body.getLinearVelocity().y > 0 || (b2body.getLinearVelocity().y < 0 && previousState == State.JUMPING));
    }

    public static int num = 0;

    public void hit() {
        Hud.addScore(-1000);
        FunGame.manager.get("sounds/hitByEnemy.wav", Sound.class).play();
        num++;
        //playerDead = true;
        Hud.addScore(-1000);
        FunGame.manager.get("sounds/hitByEnemy.wav", Sound.class).play();
        FunGame.chances--;
        if(FunGame.chances == 1) {
            Gdx.app.log("hit by Enemy: ", "1");
            Hud.chances(1);
            b2body.applyLinearImpulse(new Vector2(0, 4f), b2body.getWorldCenter(), true);
        }
        if(FunGame.chances == 0) {
            Gdx.app.log("hit by Enemy: ", "2");
            Hud.chances(0);
            b2body.applyLinearImpulse(new Vector2(0, 4f), b2body.getWorldCenter(), true);
            playerDead = true;
        }
    }

    public boolean isDead(){
        return playerDead;
    }

    public float getStateTimer(){
        return stateTimer;
    }

    public void touch() {
        FunGame.manager.get("sounds/checkPoint.wav", Sound.class).play();
    }

}
