package com.uzeer.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.uzeer.game.FunGame;
import com.uzeer.game.Scenes.Hud;
import com.uzeer.game.Screens.PlayScreen;
import com.uzeer.game.Screens.SecondStage;

import static com.uzeer.game.Sprites.Jasmine.State.RUNNING;
import static com.uzeer.game.Sprites.Jasmine.State.STANDING;

/**
 * Created by Uzeer on 2/3/2017.
 */

public class BadGuy extends Enemy{
    public enum State {STANDING, RUNNING}
    private float stateTime;
    private Animation walkAnimation;
    private Animation standAnimation;
    private Array<TextureRegion> frames;
    private boolean setToDestroy;
    private boolean destroyed;
    private boolean runningRight;
    TextureRegion region;

    public State currentState;
    public State previousState;

    private Texture badGuyTexture;

    FixtureDef fdef;
    private float time;

    public BadGuy(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        runningRight = true;
        time = 0;
        currentState = State.STANDING;
        previousState = State.STANDING;
        badGuyTexture = new Texture("enemy.png");
        frames = new Array<TextureRegion>();
        for(int i = 1; i < 7; i++){
            if(i == 1)
                frames.add(new TextureRegion(badGuyTexture, 5, 134, 60, 58));
            else if(i == 2)
                frames.add(new TextureRegion(badGuyTexture, 67, 134, 56, 60));
            else if(i == 3)
                frames.add(new TextureRegion(badGuyTexture, 123, 134, 60, 60));
            else if(i == 4)
                frames.add(new TextureRegion(badGuyTexture, 246, 134, 61, 60));
            else if(i == 5)
                frames.add(new TextureRegion(badGuyTexture, 314, 134, 58, 60));
        }
        walkAnimation = new Animation(0.2f, frames);
        frames.clear();

        for(int i = 3; i < 5; i++){
            if(i == 1)
                frames.add(new TextureRegion(badGuyTexture, 1, 288, 60, 60));
            else if(i == 2)
                frames.add(new TextureRegion(badGuyTexture, 64, 138, 60, 50));
            else if(i == 3)
                frames.add(new TextureRegion(badGuyTexture, 2, 353, 60, 61));
            else if(i == 4)
                frames.add(new TextureRegion(badGuyTexture, 63, 353, 60, 61));
        }
        standAnimation = new Animation(0.3f, frames);
        frames.clear();

        //region = walkAnimation.getKeyFrame(stateTime, true);

        stateTime = 0;
        setBounds(getX(), getY(), 52 / FunGame.PPM, 39 / FunGame.PPM);

        setToDestroy = false;
        destroyed = false;
    }

    public BadGuy(SecondStage screen, float x, float y) {
        super(screen, x, y);
        runningRight = true;
        time = 0;
        currentState = State.STANDING;
        previousState = State.STANDING;
        badGuyTexture = new Texture("enemy.png");
        frames = new Array<TextureRegion>();
        for(int i = 1; i < 7; i++){
            if(i == 1)
                frames.add(new TextureRegion(badGuyTexture, 5, 134, 60, 58));
            else if(i == 2)
                frames.add(new TextureRegion(badGuyTexture, 67, 134, 56, 60));
            else if(i == 3)
                frames.add(new TextureRegion(badGuyTexture, 123, 134, 60, 60));
            else if(i == 4)
                frames.add(new TextureRegion(badGuyTexture, 246, 134, 61, 60));
            else if(i == 5)
                frames.add(new TextureRegion(badGuyTexture, 314, 134, 58, 60));
        }
        walkAnimation = new Animation(0.3f, frames);
        frames.clear();

        for(int i = 3; i < 5; i++){
            if(i == 1)
                frames.add(new TextureRegion(badGuyTexture, 1, 288, 60, 60));
            else if(i == 2)
                frames.add(new TextureRegion(badGuyTexture, 64, 138, 60, 50));
            else if(i == 3)
                frames.add(new TextureRegion(badGuyTexture, 2, 353, 60, 61));
            else if(i == 4)
                frames.add(new TextureRegion(badGuyTexture, 63, 353, 60, 61));
        }
        standAnimation = new Animation(0.2f, frames);
        frames.clear();

        //region = walkAnimation.getKeyFrame(stateTime, true);

        stateTime = 0;
        setBounds(getX(), getY(), 52 / FunGame.PPM, 39 / FunGame.PPM);

        setToDestroy = false;
        destroyed = false;
    }


    public void update(float dt){
        stateTime += dt;
        time += dt;
        if(setToDestroy && !destroyed){
            world.destroyBody(b2body);
            destroyed = true;
            setBounds(getX(), getY(), 42 / FunGame.PPM, 50 / FunGame.PPM);
            setRegion(new TextureRegion(badGuyTexture, 5, 424, 45, 62));
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2 + 14 / FunGame.PPM);
            //setRegion(new TextureRegion(screen.getAtlas().findRegion("enemy"), 113, 52, 49, 51));
            stateTime = 0;
            fdef.filter.maskBits = FunGame.DESTROYED_BIT;
            //dispose();
        }
        else if(!destroyed) {
            b2body.setLinearVelocity(velocity);
            if(currentState == State.RUNNING)
                setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2 + 6 / FunGame.PPM);
            else
                setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2 + 10 / FunGame.PPM);
            // setRegion(walkAnimation.getKeyFrame(stateTime, true));
            setRegion(getFrame(stateTime));
        }

        if(time > 5)
            velocity.x = 0;

        if(time > 10)
            if(runningRight)
            velocity.x = 1f;
            else
            velocity.x = -1f;

        if(time > 15)
            time = 0;

        if(b2body.getLinearVelocity().x == 0 && time > 3)
            velocity.x = -velocity.x;

    }

    @Override
    protected void defineEnemy() {

        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);
        b2body.setGravityScale(10);

        fdef = new FixtureDef();
        //PolygonShape shape = new PolygonShape();
        //Rectangle shape = new Rectangle();
        CircleShape shape = new CircleShape();
        shape.setRadius(10 / FunGame.PPM);
        //shape.setAsBox(10 / FunGame.PPM, 14/ FunGame.PPM, new Vector2(0 / FunGame.PPM, 10 / FunGame.PPM), 0);

        fdef.filter.categoryBits = FunGame.ENEMY_BIT;
        fdef.filter.maskBits = FunGame.DEFAULT_BIT |
                FunGame.COIN_BIT |
                FunGame.ENEMY_BIT |
                FunGame.GROUND_BIT |
                FunGame.BULLET_BIT |
                FunGame.BULLET_BIT2 |
                FunGame.PLAYER_BIT;

        fdef.shape = shape;

        b2body.createFixture(fdef).setUserData(this);

        //creat the head here
        PolygonShape head = new PolygonShape();
        Vector2[] vertice = new Vector2[4];
        vertice[0] = new Vector2(-6, 27).scl(1 / FunGame.PPM);
        vertice[1] = new Vector2(6, 27).scl(1 / FunGame.PPM);
        vertice[2] = new Vector2(-3, 21).scl(1 / FunGame.PPM);
        vertice[3] = new Vector2(3, 21).scl(1 / FunGame.PPM);
        head.set(vertice);

        fdef.shape = head;
        fdef.restitution = 0.7f;
        fdef.filter.categoryBits = FunGame.ENEMY_HEAD_BIT;
        fdef.filter.maskBits = FunGame.BULLET_BIT | FunGame.PLAYER_BIT | FunGame.BULLET_BIT2;
        b2body.createFixture(fdef).setUserData(this);

    }

    public void draw(Batch batch){
        if(!destroyed || stateTime < 0.7f)
            super.draw(batch);
    }


    public TextureRegion getFrame(float dt) {
        currentState = getState();
        TextureRegion region;
        switch (currentState) {
            case RUNNING:
                region = walkAnimation.getKeyFrame(stateTime, true);
                break;
            case STANDING:
                region = standAnimation.getKeyFrame(stateTime, true);
                break;
            default:
                region = standAnimation.getKeyFrame(stateTime, true);
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

        return region;
    }

    private State getState() {
        if(velocity.x == 0 || b2body.getLinearVelocity().x == 0)
            return State.STANDING;
        else
            return State.RUNNING;
    }

    @Override
    public void hitOnHead() {
        setToDestroy = true;
        FunGame.manager.get("sounds/enemyHit2.wav", Sound.class).play();
    }

    public static int hit = 0;

    @Override
    public void hitByEnemy(Player userData) {
        hit++;
        Hud.addScore(-1000);
        if(hit > 3){
            Gdx.app.log("Game", "End");
        }
    }

    public void dispose(){
        badGuyTexture.dispose();
    }
}
