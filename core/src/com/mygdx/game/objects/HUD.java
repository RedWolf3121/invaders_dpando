package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class HUD {

    Ship ship;
    AlienArmy alienArmy;
    BitmapFont bitmapFont;

    HUD(Ship ship, AlienArmy alienArmy){
        this.ship = ship;
        this.alienArmy = alienArmy;
        bitmapFont = new BitmapFont();
    }

    void render(SpriteBatch batch){
        bitmapFont.draw(batch, "Vidas: " + ship.life, 300, 20);
        bitmapFont.draw(batch, "Score: " + ship.points, 20, 20);
        bitmapFont.draw(batch, "Level: " + alienArmy.levelArmy, 20, 50);
        if (alienArmy.gameOver) {
            bitmapFont.draw(batch, "WINNER !!", 200, 200);
        }
    }
}
