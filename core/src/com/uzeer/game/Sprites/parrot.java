package com.uzeer.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
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


public class parrot extends Enemy {

    private float stateTime;
    private Animation walkAnimation;
    private Array<TextureRegion> frames;
    private boolean setToDestroy;
    private boolean destroyed;
    private boolean runningRight;
    private PlayScreen screen;
    private SecondStage screen2;
    Texture region;
    FixtureDef fdef;

    public parrot(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        this.screen = screen;
        runningRight = false;
        frames = new Array<TextureRegion>();
        for(int i = 1; i < 5; i++){
            if(i == 1)
                frames.add(new TextureRegion(screen.getAtlas2().findRegion("parrot"), 3, 2, 28, 22));
            else if(i == 2)
                frames.add(new TextureRegion(screen.getAtlas2().findRegion("parrot"), 32, 2, 28, 22));
            else if(i == 3)
                frames.add(new TextureRegion(screen.getAtlas2().findRegion("parrot"), 63, 2, 28, 22));
            else if(i == 4)
                frames.add(new TextureRegion(screen.getAtlas2().findRegion("parrot"), 98, 2, 29, 22));
        }
        walkAnimation = new Animation(0.2f, frames);
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
        setBounds(getX(), getY(), 30 / FunGame.PPM, 25 / FunGame.PPM);

        setToDestroy = false;
        destroyed = false;
    }

    public parrot(SecondStage screen, float x, float y) {
        super(screen, x, y);
        this.screen2 = screen;
        runningRight = true;
        frames = new Array<TextureRegion>();
        for(int i = 1; i < 5; i++){
            if(i == 1)
                frames.add(new TextureRegion(screen.getAtlas2().findRegion("parrot"), 3, 2, 28, 22));
            else if(i == 2)
                frames.add(new TextureRegion(screen.getAtlas2().findRegion("parrot"), 32, 2, 28, 22));
            else if(i == 3)
                frames.add(new TextureRegion(screen.getAtlas2().findRegion("parrot"), 63, 2, 28, 22));
            else if(i == 4)
                frames.add(new TextureRegion(screen.getAtlas2().findRegion("parrot"), 98, 2, 29, 22));
        }
        walkAnimation = new Animation(0.2f, frames);
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
        setBounds(getX(), getY(), 30 / FunGame.PPM, 25 / FunGame.PPM);

        setToDestroy = false;
        destroyed = false;
    }

    public void update(float dt){
        stateTime += dt;
        if(setToDestroy && !destroyed){
            world.destroyBody(b2body);
            setBounds(getX(), getY(), 30 / FunGame.PPM, 25 / FunGame.PPM);
            if(FunGame.PlayScreen)
                setRegion(new TextureRegion(screen.getAtlas2().findRegion("parrot"), 40, 31, 28, 24));
            else
                setRegion(new TextureRegion(screen2.getAtlas2().findRegion("parrot"), 40, 31, 28, 24));
            stateTime = 0;
            fdef.filter.maskBits = FunGame.DESTROYED_BIT;
            destroyed = true;
        }
        else if(!destroyed) {
            b2body.setLinearVelocity(velocity);
            setPosition(b2body.getPosition().x + getWidth() / 2 / FunGame.PPM, b2body.getPosition().y + getHeight() / 2 / FunGame.PPM);
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
        b2body.setGravityScale(0);
        //b2body.setGravityScale(10);

        fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        //Rectangle shape = new Rectangle();
        //CircleShape shape = new CircleShape();
        //shape.setRadius(8 / FunGame.PPM);
        shape.setAsBox(10 / FunGame.PPM, 10/ FunGame.PPM, new Vector2(10 / FunGame.PPM, 10 / FunGame.PPM), 0);

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
        vertice[0] = new Vector2(-1, 24).scl(1 / FunGame.PPM);
        vertice[1] = new Vector2(20, 24).scl(1 / FunGame.PPM);
        vertice[2] = new Vector2(4, 20).scl(1 / FunGame.PPM);
        vertice[3] = new Vector2(12, 20).scl(1 / FunGame.PPM);
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

        if ((b2body.getLinearVelocity().x < 0 || !runningRight) && region.isFlipX()) {
            region.flip(true, false);
            runningRight = false;
        }
        else if ((b2body.getLinearVelocity().x > 0 || runningRight) && !region.isFlipX()) {
            region.flip(true, false);
            runningRight = true;
        }

        return region;
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

    }

}
