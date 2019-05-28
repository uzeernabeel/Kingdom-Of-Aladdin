package com.uzeer.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.uzeer.game.FunGame;
import com.uzeer.game.Scenes.Hud;
import com.uzeer.game.Screens.PlayScreen;
import com.uzeer.game.Screens.SecondStage;

/**
 * Created by Uzeer on 12/26/2016.
 */

public class Coin extends InteractiveTileObject{

    public Coin(PlayScreen screen, Rectangle bounds, String value) {
        super(screen, bounds, value);
        fixture.setUserData(this);
        setCategoryFilter(FunGame.COIN_BIT);

    }

    public Coin(SecondStage screen, Rectangle bounds, String value) {
        super(screen, bounds, value);
        fixture.setUserData(this);
        setCategoryFilter(FunGame.COIN_BIT);

    }


    @Override
    public void bodyHit() {
        setCategoryFilter(FunGame.DESTROYED_BIT);
        getCell().setTile(null);
        Hud.addScore(500);
        Hud.addCoin(4);
        FunGame.manager.get("sounds/coin.wav", Sound.class).play();
    }
}
