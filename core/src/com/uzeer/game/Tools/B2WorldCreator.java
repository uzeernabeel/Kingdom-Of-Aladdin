package com.uzeer.game.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.uzeer.game.FunGame;
import com.uzeer.game.Screens.PlayScreen;
import com.uzeer.game.Screens.SecondStage;
import com.uzeer.game.Sprites.BadGuy;
import com.uzeer.game.Sprites.CheckPoints;
import com.uzeer.game.Sprites.Coin;
import com.uzeer.game.Sprites.Enemy;
import com.uzeer.game.Sprites.Flinkstone;
import com.uzeer.game.Sprites.Jasmine;
import com.uzeer.game.Sprites.Player;
import com.uzeer.game.Sprites.parrot;

/**
 * Created by Uzeer on 12/26/2016.
 */

public class B2WorldCreator {

    private Array<Flinkstone> flinkstone;
    private Array<BadGuy> badGuys;
    private Array<parrot> parrots;
    private Jasmine jasmine;

    public B2WorldCreator(PlayScreen screen){
        World world = screen.getWorld();
        TiledMap map = screen.getMap();
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body = null;

        //This is for Ground Layer # 2
        //for(MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
        for(MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / FunGame.PPM, (rect.getY() + rect.getHeight() / 2) / FunGame.PPM);

            //if(bdef.position.x < screen.player2.getX() + 1)
            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / FunGame.PPM, rect.getHeight() / 2 / FunGame.PPM);
            fdef.shape = shape;
            fdef.filter.categoryBits = FunGame.GROUND_BIT;
            fdef.filter.maskBits = FunGame.COIN_BIT |
                    FunGame.ENEMY_BIT |
                    FunGame.BULLET_BIT |
                    FunGame.BULLET_BIT2 |
                    FunGame.JASMINE_BIT |
                    FunGame.PLAYER_BIT;
            body.createFixture(fdef);
        }

        //This is for Coins Layer # 3
        for(MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new Coin(screen, rect, "Coins");
        }

        //creat flinkstone
            flinkstone = new Array<Flinkstone>();
            for(MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)){
                Rectangle rect = ((RectangleMapObject) object).getRectangle();
                flinkstone.add(new Flinkstone(screen, rect.getX() / FunGame.PPM, rect.getY() / FunGame.PPM));
        }

        //creat badGuy Layer
            badGuys = new Array<BadGuy>();
            for(MapObject object : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)){
                Rectangle rect = ((RectangleMapObject) object).getRectangle();
                badGuys.add(new BadGuy(screen, rect.getX() / FunGame.PPM, rect.getY() / FunGame.PPM));
        }

        //creat parrot Layer
            parrots = new Array<parrot>();
            for(MapObject object : map.getLayers().get(8).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            parrots.add(new parrot(screen, rect.getX() / FunGame.PPM, rect.getY() / FunGame.PPM));
        }

        //creat Jasmine
        for(MapObject object : map.getLayers().get(9).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            jasmine = new Jasmine(screen, rect.getX() / FunGame.PPM, rect.getY() / FunGame.PPM);
        }
    }

    public B2WorldCreator(SecondStage screen) {
        World world = screen.getWorld();
        TiledMap map = screen.getMap();
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        //This is for Ground Layer # 2
        for(MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / FunGame.PPM, (rect.getY() + rect.getHeight() / 2) / FunGame.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / FunGame.PPM, rect.getHeight() / 2 / FunGame.PPM);
            fdef.shape = shape;
            fdef.filter.categoryBits = FunGame.GROUND_BIT;
            fdef.filter.maskBits = FunGame.COIN_BIT |
                    FunGame.ENEMY_BIT |
                    FunGame.BULLET_BIT |
                    FunGame.BULLET_BIT2 |
                    FunGame.JASMINE_BIT |
                    FunGame.PLAYER_BIT;
            body.createFixture(fdef);
        }

        //This is for Coins Layer # 3
        for(MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new Coin(screen, rect, "Coins");
        }

        //creat flinkstone
        flinkstone = new Array<Flinkstone>();
        for(MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            flinkstone.add(new Flinkstone(screen, rect.getX() / FunGame.PPM, rect.getY() / FunGame.PPM));
        }

        //creat badGuy Layer
        badGuys = new Array<BadGuy>();
        for(MapObject object : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            badGuys.add(new BadGuy(screen, rect.getX() / FunGame.PPM, rect.getY() / FunGame.PPM));
        }

        //creat parrot Layer
        parrots = new Array<parrot>();
        for(MapObject object : map.getLayers().get(8).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            parrots.add(new parrot(screen, rect.getX() / FunGame.PPM, rect.getY() / FunGame.PPM));
        }

        //creat Jasmine
        for(MapObject object : map.getLayers().get(9).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            jasmine = new Jasmine(screen, rect.getX() / FunGame.PPM, rect.getY() / FunGame.PPM);
        }
    }

    public Array<Flinkstone> getFlinkstone() {
        return flinkstone;
    }

    public Array<BadGuy> getBadGuys(){
        return badGuys;
    }

    public Array<parrot> getParrots(){
        return parrots;
    }

    public Jasmine getJasmine(){
        return jasmine;
    }

    public Array<Enemy> getEnemies(){
        Array<Enemy> enemies = new Array<Enemy>();
        enemies.addAll(flinkstone);
        enemies.addAll(badGuys);
        enemies.addAll(parrots);
        return enemies;
    }
}
