package com.uzeer.game.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.uzeer.game.FunGame;
import com.uzeer.game.Scenes.Hud;
import com.uzeer.game.Screens.PlayScreen;
import com.uzeer.game.Sprites.BulletFinal;
import com.uzeer.game.Sprites.BulletFinal2;
import com.uzeer.game.Sprites.Enemy;
import com.uzeer.game.Sprites.InteractiveTileObject;
import com.uzeer.game.Sprites.Jasmine;
import com.uzeer.game.Sprites.Player;
import com.uzeer.game.Sprites.Player2;

/**
 * Created by Uzeer on 12/27/2016.
 */

public class WorldContactListner implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        //All possible collisions
        switch (cDef){
            case FunGame.ENEMY_HEAD_BIT | FunGame.PLAYER_BIT:
                if(fixA.getFilterData().categoryBits == FunGame.ENEMY_HEAD_BIT) {
                    ((Enemy) fixA.getUserData()).hitOnHead();
                    Hud.addScore(1000);
                }
                else {
                    ((Enemy) fixB.getUserData()).hitOnHead();
                    Hud.addScore(1000);
                }
                break;
            case FunGame.ENEMY_BIT | FunGame.GROUND_BIT:
                if(fixA.getFilterData().categoryBits == FunGame.ENEMY_BIT) {
                    ((Enemy) fixA.getUserData()).reverseVelocity(true, false);
                    //((Enemy) fixA.getUserData()).impulse();
                } else {
                    ((Enemy) fixB.getUserData()).reverseVelocity(true, false);
                    //((Enemy) fixB.getUserData()).impulse();
                }
                break;
            case FunGame.JASMINE_BIT | FunGame.GROUND_BIT:
                if(fixA.getFilterData().categoryBits == FunGame.JASMINE_BIT)
                    ((Jasmine)fixA.getUserData()).reverseVelocity(true, false);
                else
                    ((Jasmine)fixB.getUserData()).reverseVelocity(true, false);
                break;
            case FunGame.ENEMY_BIT | FunGame.ENEMY_BIT:
                    ((Enemy)fixA.getUserData()).reverseVelocity(true, false);
                    ((Enemy)fixB.getUserData()).reverseVelocity(true, false);
                break;
            case FunGame.PLAYER_BIT | FunGame.ENEMY_BIT:
                if(fixA.getFilterData().categoryBits == FunGame.PLAYER_BIT) {
                    if(FunGame.player2Selected)
                        ((Player2) fixA.getUserData()).hit();
                    else
                        ((Player) fixA.getUserData()).hit();
                }
                else {
                        if(FunGame.player2Selected)
                            ((Player2) fixB.getUserData()).hit();
                        else
                            ((Player) fixB.getUserData()).hit();
                }
                break;
            case FunGame.BULLET_BIT | FunGame.ENEMY_BIT:
                if(fixA.getFilterData().categoryBits == FunGame.ENEMY_BIT) {
                    ((Enemy) fixA.getUserData()).hitOnHead();
                    ((BulletFinal) fixB.getUserData()).destroyBullet();
                    Hud.addScore(1000);
                }
                else {
                    ((Enemy) fixB.getUserData()).hitOnHead();
                    ((BulletFinal)fixA.getUserData()).destroyBullet();
                    Hud.addScore(1000);
                }
                break;
            case FunGame.BULLET_BIT | FunGame.ENEMY_HEAD_BIT:
                if(fixA.getFilterData().categoryBits == FunGame.ENEMY_HEAD_BIT) {
                    ((Enemy) fixA.getUserData()).hitOnHead();
                    ((BulletFinal) fixB.getUserData()).destroyBullet();
                    Hud.addScore(1000);
                }
                else {
                    ((Enemy) fixB.getUserData()).hitOnHead();
                    ((BulletFinal)fixA.getUserData()).destroyBullet();
                    Hud.addScore(1000);
                }
                break;
            case FunGame.BULLET_BIT2 | FunGame.ENEMY_BIT:
                if(fixA.getFilterData().categoryBits == FunGame.ENEMY_BIT) {
                    ((Enemy) fixA.getUserData()).hitOnHead();
                    ((BulletFinal2) fixB.getUserData()).destroyBullet();
                    Hud.addScore(1000);
                }
                else {
                    ((Enemy) fixB.getUserData()).hitOnHead();
                    ((BulletFinal2)fixA.getUserData()).destroyBullet();
                    Hud.addScore(1000);
                }
                break;
            case FunGame.BULLET_BIT2 | FunGame.ENEMY_HEAD_BIT:
                if(fixA.getFilterData().categoryBits == FunGame.ENEMY_HEAD_BIT) {
                    ((Enemy) fixA.getUserData()).hitOnHead();
                    ((BulletFinal2) fixB.getUserData()).destroyBullet();
                    Hud.addScore(1000);
                }
                else {
                    ((Enemy) fixB.getUserData()).hitOnHead();
                    ((BulletFinal2)fixA.getUserData()).destroyBullet();
                    Hud.addScore(1000);
                }
                break;
            case FunGame.BULLET_BIT | FunGame.GROUND_BIT:
                if(fixA.getFilterData().categoryBits == FunGame.BULLET_BIT) {
                    ((BulletFinal) fixA.getUserData()).destroyBullet();
                } else {
                    ((BulletFinal) fixB.getUserData()).destroyBullet();
                }
                break;
            case FunGame.BULLET_BIT2 | FunGame.GROUND_BIT:
                if(fixA.getFilterData().categoryBits == FunGame.BULLET_BIT)
                    ((BulletFinal2)fixA.getUserData()).destroyBullet();
                else
                    ((BulletFinal2)fixB.getUserData()).destroyBullet();
                break;
            case FunGame.PLAYER_BIT | FunGame.COIN_BIT:
                if(fixA.getFilterData().categoryBits == FunGame.PLAYER_BIT)
                    ((InteractiveTileObject) fixB.getUserData()).bodyHit();
                else
                    ((InteractiveTileObject) fixA.getUserData()).bodyHit();
                break;
            case FunGame.JASMINE_BIT | FunGame.PLAYER_BIT:
                if (FunGame.player2Selected){
                    if (fixA.getFilterData().categoryBits == FunGame.PLAYER_BIT)
                        ((Player2) fixA.getUserData()).touch();
                        else
                        ((Player2) fixB.getUserData()).touch();
                } else {
                    if (fixA.getFilterData().categoryBits == FunGame.PLAYER_BIT)
                        ((Player) fixA.getUserData()).touch();
                    else
                        ((Player) fixB.getUserData()).touch();
                }
                break;
        }
    }

    @Override
    public void endContact(Contact contact) {
       // Gdx.app.log("End Contact", "here: ");
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
