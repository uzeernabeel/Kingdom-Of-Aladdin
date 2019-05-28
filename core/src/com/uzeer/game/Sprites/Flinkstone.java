package com.uzeer.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.uzeer.game.FunGame;
import com.uzeer.game.Scenes.Hud;
import com.uzeer.game.Screens.PlayScreen;
import com.uzeer.game.Screens.SecondStage;

/**
 * Created by Uzeer on 1/5/2017.
 */

public class Flinkstone extends Enemy {

    private float stateTime;
    private Animation walkAnimation;
    private Array<TextureRegion> frames;
    private boolean setToDestroy;
    private boolean destroyed;
    private boolean runningRight;
    TextureRegion region;

    FixtureDef fdef;

    public Flinkstone(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        runningRight = true;
        frames = new Array<TextureRegion>();
        for(int i = 1; i < 9; i++){
            if(i == 1)
                frames.add(new TextureRegion(screen.getAtlas().findRegion("enemy"), 0, 0, 45, 52));
            else if(i == 2)
                frames.add(new TextureRegion(screen.getAtlas().findRegion("enemy"), 48, 0, 40, 52));
            else if(i == 3)
                frames.add(new TextureRegion(screen.getAtlas().findRegion("enemy"), 91, 0, 45, 52));
            else if(i == 4)
                frames.add(new TextureRegion(screen.getAtlas().findRegion("enemy"), 136, 0, 45, 52));
            else if(i == 5)
                frames.add(new TextureRegion(screen.getAtlas().findRegion("enemy"), 0, 52, 45, 52));
            else if(i == 6)
                frames.add(new TextureRegion(screen.getAtlas().findRegion("enemy"), 51, 52, 50, 52));
            else if(i == 7)
                frames.add(new TextureRegion(screen.getAtlas().findRegion("enemy"), 113, 52, 49, 51));
            else if(i == 8)
                frames.add(new TextureRegion(screen.getAtlas().findRegion("enemy"), 171, 52, 49, 51));
        }
        walkAnimation = new Animation(0.3f, frames);
        frames.clear();

        TextureRegion region = walkAnimation.getKeyFrame(stateTime, true);

        if ((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()) {
            region.flip(true, false);
            runningRight = false;
        }
        else if ((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()) {
            region.flip(true, false);
            runningRight = true;
        }

        stateTime = 0;
        setBounds(getX(), getY(), 30 / FunGame.PPM, 41 / FunGame.PPM);

        setToDestroy = false;
        destroyed = false;
    }

    public Flinkstone(SecondStage screen, float x, float y) {
        super(screen, x, y);
        runningRight = true;
        frames = new Array<TextureRegion>();
        for(int i = 1; i < 9; i++){
            if(i == 1)
                frames.add(new TextureRegion(screen.getAtlas().findRegion("enemy"), 0, 0, 45, 52));
            else if(i == 2)
                frames.add(new TextureRegion(screen.getAtlas().findRegion("enemy"), 48, 0, 40, 52));
            else if(i == 3)
                frames.add(new TextureRegion(screen.getAtlas().findRegion("enemy"), 91, 0, 45, 52));
            else if(i == 4)
                frames.add(new TextureRegion(screen.getAtlas().findRegion("enemy"), 136, 0, 45, 52));
            else if(i == 5)
                frames.add(new TextureRegion(screen.getAtlas().findRegion("enemy"), 0, 52, 45, 52));
            else if(i == 6)
                frames.add(new TextureRegion(screen.getAtlas().findRegion("enemy"), 51, 52, 50, 52));
            else if(i == 7)
                frames.add(new TextureRegion(screen.getAtlas().findRegion("enemy"), 113, 52, 49, 51));
            else if(i == 8)
                frames.add(new TextureRegion(screen.getAtlas().findRegion("enemy"), 171, 52, 49, 51));
        }
        walkAnimation = new Animation(0.3f, frames);
        frames.clear();

        TextureRegion region = walkAnimation.getKeyFrame(stateTime, true);

        if ((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()) {
            region.flip(true, false);
            runningRight = false;
        }
        else if ((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()) {
            region.flip(true, false);
            runningRight = true;
        }

        stateTime = 0;
        setBounds(getX(), getY(), 30 / FunGame.PPM, 41 / FunGame.PPM);

        setToDestroy = false;
        destroyed = false;
    }


    public void update(float dt){
        stateTime += dt;
        if(setToDestroy && !destroyed){
            world.destroyBody(b2body);
            destroyed = true;
            if(FunGame.SecondScreen)
                setRegion(new TextureRegion(screen1.getAtlas().findRegion("enemy"), 48, 0, 40, 52));
            if(FunGame.PlayScreen)
                setRegion(new TextureRegion(screen.getAtlas().findRegion("enemy"), 48, 0, 40, 52));
            stateTime = 0;
            fdef.filter.maskBits = FunGame.DESTROYED_BIT;
        }
        else if(!destroyed) {
            b2body.setLinearVelocity(velocity);
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2 + 10 / FunGame.PPM);
           // setRegion(walkAnimation.getKeyFrame(stateTime, true));
            setRegion(getFrame(stateTime));
        }

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
        //shape.setAsBox(9 / FunGame.PPM, 14/ FunGame.PPM, new Vector2(0 / FunGame.PPM, 10 / FunGame.PPM), 0);

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
        TextureRegion region;
        region = walkAnimation.getKeyFrame(stateTime, true);

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

    @Override
    public void hitOnHead() {
        setToDestroy = true;
        FunGame.manager.get("sounds/enemyHit1.wav", Sound.class).play();
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

    }

}
