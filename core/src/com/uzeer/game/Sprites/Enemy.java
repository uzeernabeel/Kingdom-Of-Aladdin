package com.uzeer.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.uzeer.game.Screens.PlayScreen;
import com.uzeer.game.Screens.SecondStage;

/**
 * Created by Uzeer on 1/5/2017.
 */

public abstract class Enemy extends Sprite {

    protected PlayScreen screen;
    protected SecondStage screen1;
    protected World world;
    public Body b2body;
    public Vector2 velocity;
    public Vector2 velocity2;
    public Vector2 NegVelocity2;


    public Enemy(PlayScreen screen, float x, float y){
        this.world = screen.getWorld();
        this.screen = screen;
        setPosition(x, y);
        defineEnemy();
        velocity = new Vector2(1f, 0f);
        velocity2 = new Vector2(1.5f, 0f);
        NegVelocity2 = new Vector2(-1.5f, 0f);
        //b2body.setActive(false);
    }

    public Enemy(SecondStage screen, float x, float y){
        this.world = screen.getWorld();
        this.screen1 = screen;
        setPosition(x, y);
        defineEnemy();
        velocity = new Vector2(1f, 0f);
        velocity2 = new Vector2(1.5f, 0f);
        NegVelocity2 = new Vector2(-1.5f, 0f);
        //b2body.setActive(false);
    }


    protected abstract void defineEnemy();
    public abstract void hitOnHead();
    public abstract void update(float dt);
    public abstract void hitByEnemy(Player userData);

    public void reverseVelocity(boolean x, boolean y){
        if(x)
            velocity.x = -velocity.x;
        if(y)
            velocity.y = -velocity.y;
    }


    public void impulse() {
        b2body.applyLinearImpulse(new Vector2(0, 3f), b2body.getWorldCenter(), true);
    }
}
