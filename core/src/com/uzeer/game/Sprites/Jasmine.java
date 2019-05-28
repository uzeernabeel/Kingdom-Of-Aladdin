package com.uzeer.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.uzeer.game.FunGame;
import com.uzeer.game.Scenes.Hud;
import com.uzeer.game.Screens.PlayScreen;
import com.uzeer.game.Screens.SecondStage;

import static com.uzeer.game.Sprites.Jasmine.State.RUNNING;
import static com.uzeer.game.Sprites.Jasmine.State.STANDING;

/**
 * Created by Uzeer on 3/12/2017.
 */

public class Jasmine extends Sprite {

    public enum State {STANDING, RUNNING}
    private float stateTime;
    private Animation walkAnimation;
    private Animation standAnimation;
    private Array<TextureRegion> frames;
    private boolean runningRight;
    PlayScreen screen;
    SecondStage screen2;
    World world;
    Body b2body;
    Vector2 velocity;
    FixtureDef fdef;
    float x;
    float y;
    private float time;
    public Jasmine.State currentState;
    public Jasmine.State previousState;


    public Jasmine(PlayScreen screen, float x, float y) {
        this.screen = screen;
        this.world = screen.getWorld();
        this.x = x;
        this.y = y;
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTime = 0f;
        runningRight = true;
        velocity = new Vector2(.75f, 0);
        frames = new Array<TextureRegion>();

        for(int i = 2; i < 11; i++){
            //if(i == 1)
                //frames.add(new TextureRegion(screen.getAtlas2().findRegion("jasmine"), 446, 1, 64, 165));
            if(i == 2)
                frames.add(new TextureRegion(screen.getAtlas2().findRegion("jasmine"), 512, 1, 65, 165));
            else if(i == 3)
                frames.add(new TextureRegion(screen.getAtlas2().findRegion("jasmine"), 579, 1, 66, 165));
            else if(i == 4)
                frames.add(new TextureRegion(screen.getAtlas2().findRegion("jasmine"), 647, 1, 66, 165));
            else if(i == 5)
                frames.add(new TextureRegion(screen.getAtlas2().findRegion("jasmine"), 715, 1, 68, 165));
            else if(i == 6)
                frames.add(new TextureRegion(screen.getAtlas2().findRegion("jasmine"), 785, 1, 68, 165));
            else if(i == 7)
                frames.add(new TextureRegion(screen.getAtlas2().findRegion("jasmine"), 855, 1, 66, 165));
            else if(i == 8)
                frames.add(new TextureRegion(screen.getAtlas2().findRegion("jasmine"), 923, 1, 66, 165));
            else if(i == 9)
                frames.add(new TextureRegion(screen.getAtlas2().findRegion("jasmine"), 991, 1, 66, 165));
            else if(i == 10)
                frames.add(new TextureRegion(screen.getAtlas2().findRegion("jasmine"), 1059, 1, 66, 165));
            /*
            else if(i == 11)
                frames.add(new TextureRegion(screen.getAtlas2().findRegion("jasmine"), 195, 1, 62, 165));
            else if(i == 12)
                frames.add(new TextureRegion(screen.getAtlas2().findRegion("jasmine"), 260, 1, 61, 165));
            else if(i == 13)
                frames.add(new TextureRegion(screen.getAtlas2().findRegion("jasmine"), 321, 1, 61, 165));
            else if(i == 14)
                frames.add(new TextureRegion(screen.getAtlas2().findRegion("jasmine"), 382, 1, 62, 165));
                */
        }
        walkAnimation = new Animation(0.1f, frames);
        frames.clear();

        for(int i = 1; i < 6; i++){
            if(i == 1)
                frames.add(new TextureRegion(screen.getAtlas2().findRegion("jasmine"), 446, 1, 64, 165));
            else if(i == 2)
                frames.add(new TextureRegion(screen.getAtlas2().findRegion("jasmine"), 195, 1, 62, 165));
            else if(i == 3)
                frames.add(new TextureRegion(screen.getAtlas2().findRegion("jasmine"), 260, 1, 61, 165));
            else if(i == 4)
                frames.add(new TextureRegion(screen.getAtlas2().findRegion("jasmine"), 321, 1, 61, 165));
            else if(i == 5)
                frames.add(new TextureRegion(screen.getAtlas2().findRegion("jasmine"), 382, 1, 62, 165));
        }
        standAnimation = new Animation(0.2f, frames);
        frames.clear();

        TextureRegion region = walkAnimation.getKeyFrame(stateTime, true);

        /*if ((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()) {
            region.flip(true, false);
            runningRight = false;
        }
        else if ((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()) {
            region.flip(true, false);
            runningRight = true;
        }*/

        setBounds(getX(), getY(), 30 / FunGame.PPM, 45 / FunGame.PPM);

        defineJasmine();
    }

    public Jasmine(SecondStage screen, float x, float y) {
        this.screen2 = screen;
        this.world = screen.getWorld();
        this.x = x;
        this.y = y;
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTime = 0f;
        runningRight = true;
        velocity = new Vector2(.75f, 0);
        frames = new Array<TextureRegion>();

        for (int i = 2; i < 11; i++) {
            //if(i == 1)
            //frames.add(new TextureRegion(screen.getAtlas2().findRegion("jasmine"), 446, 1, 64, 165));
            if (i == 2)
                frames.add(new TextureRegion(screen.getAtlas2().findRegion("jasmine"), 512, 1, 65, 165));
            else if (i == 3)
                frames.add(new TextureRegion(screen.getAtlas2().findRegion("jasmine"), 579, 1, 66, 165));
            else if (i == 4)
                frames.add(new TextureRegion(screen.getAtlas2().findRegion("jasmine"), 647, 1, 66, 165));
            else if (i == 5)
                frames.add(new TextureRegion(screen.getAtlas2().findRegion("jasmine"), 715, 1, 68, 165));
            else if (i == 6)
                frames.add(new TextureRegion(screen.getAtlas2().findRegion("jasmine"), 785, 1, 68, 165));
            else if (i == 7)
                frames.add(new TextureRegion(screen.getAtlas2().findRegion("jasmine"), 855, 1, 66, 165));
            else if (i == 8)
                frames.add(new TextureRegion(screen.getAtlas2().findRegion("jasmine"), 923, 1, 66, 165));
            else if (i == 9)
                frames.add(new TextureRegion(screen.getAtlas2().findRegion("jasmine"), 991, 1, 66, 165));
            else if (i == 10)
                frames.add(new TextureRegion(screen.getAtlas2().findRegion("jasmine"), 1059, 1, 66, 165));
        }
        walkAnimation = new Animation(0.1f, frames);
        frames.clear();

        for(int i = 1; i < 6; i++){
            if(i == 1)
                frames.add(new TextureRegion(screen.getAtlas2().findRegion("jasmine"), 446, 1, 64, 165));
            else if(i == 2)
                frames.add(new TextureRegion(screen.getAtlas2().findRegion("jasmine"), 195, 1, 62, 165));
            else if(i == 3)
                frames.add(new TextureRegion(screen.getAtlas2().findRegion("jasmine"), 260, 1, 61, 165));
            else if(i == 4)
                frames.add(new TextureRegion(screen.getAtlas2().findRegion("jasmine"), 321, 1, 61, 165));
            else if(i == 5)
                frames.add(new TextureRegion(screen.getAtlas2().findRegion("jasmine"), 382, 1, 62, 165));
        }
        standAnimation = new Animation(0.2f, frames);
        frames.clear();

        TextureRegion region = walkAnimation.getKeyFrame(stateTime, true);

        setBounds(getX(), getY(), 30 / FunGame.PPM, 48 / FunGame.PPM);

        defineJasmine();
    }


    public void update(float dt){
        stateTime += dt;
        time += dt;
        b2body.setLinearVelocity(velocity);
        if(FunGame.PlayScreen)
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2 + 17 / FunGame.PPM);
        if(FunGame.SecondScreen)
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2 + 18 / FunGame.PPM);

        setRegion(getFrame(stateTime));

        if(time > 5)
            velocity.x = 0;

        if(time > 7)
            if(runningRight)
            velocity.x = 0.75f;
            else
            velocity.x = -0.75f;

        if(time > 15)
            time = 0;
    }

    protected void defineJasmine() {

        //Body
        BodyDef bdef = new BodyDef();
        bdef.position.set(x, y);
        //bdef.position.set(55 / FunGame.PPM, 160 / FunGame.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);
        b2body.setGravityScale(10);


        //Fixture
        fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        fdef.shape = shape;

        fdef.filter.categoryBits = FunGame.JASMINE_BIT;
        fdef.filter.maskBits = FunGame.DEFAULT_BIT |
                FunGame.COIN_BIT |
                FunGame.ENEMY_BIT |
                FunGame.GROUND_BIT |
                FunGame.PLAYER_BIT;

        shape.setAsBox(9 / FunGame.PPM, 14/ FunGame.PPM, new Vector2(0 / FunGame.PPM, 10 / FunGame.PPM), 0);
        b2body.createFixture(fdef).setUserData(this);
    }

    public void draw(Batch batch){
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
        if(velocity.x == 0)
            return STANDING;
        else
            return RUNNING;
    }

    public void dispose(){

    }

    public void reverseVelocity(boolean x, boolean y){
        if(x)
            velocity.x = -velocity.x;
        if(y)
            velocity.y = -velocity.y;
    }

    public void touch(){
        FunGame.manager.get("sounds/checkPoint.wav", Sound.class).play();
    }


}
