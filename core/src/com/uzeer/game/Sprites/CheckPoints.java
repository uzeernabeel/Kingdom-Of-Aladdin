package com.uzeer.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Rectangle;
import com.uzeer.game.FunGame;
import com.uzeer.game.Screens.PlayScreen;
import com.uzeer.game.Screens.SecondStage;

/**
 * Created by Uzeer on 2/17/2017.
 */

public class CheckPoints extends InteractiveTileObject {

    public CheckPoints(PlayScreen screen, Rectangle bounds, String value) {
        super(screen, bounds, value);
        setCategoryFilter(FunGame.CHECK_POINT_BIT);
        fixture.setUserData(this);
    }

    public CheckPoints(SecondStage screen, Rectangle bounds, String value) {
        super(screen, bounds, value);
        fixture.setUserData(this);
        setCategoryFilter(FunGame.CHECK_POINT_BIT);
    }

    @Override
    public void bodyHit() {
        FunGame.manager.get("sounds/checkPoint.wav", Sound.class).play();
        if(FunGame.player2Selected)
            Player2.checkPointX = body.getPosition().x;
        else
            Player.checkPointX = body.getPosition().x;
    }
}
