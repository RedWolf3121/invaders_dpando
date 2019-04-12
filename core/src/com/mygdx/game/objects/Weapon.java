package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Assets;
import com.mygdx.game.Timer;

public class Weapon {

    Array<Shoot> shoots;

    Timer shootTimer;
    float shootRate = 0.1f;

    Weapon(){
        shoots = new Array<Shoot>();
        shootTimer = new Timer(shootRate);
    }

    void render(SpriteBatch batch){
        for (Shoot shoot: shoots) {
            shoot.render(batch);
        }
    }

    public void update(float delta, Assets assets) {
        shootTimer.update(delta);
        for(Shoot shoot: shoots){
            shoot.update(delta, assets);
        }
        removeShoots();
    }

    public void shoot(float position){
        if(shootTimer.check()){
            shoots.add(new Shoot(position));
        }
    }

    public void removeShoots(){
        Array<Shoot> shootsToRemove = new Array<Shoot>();
        for(Shoot shoot:shoots){
            if(shoot.state == Shoot.State.TO_REMOVE){
                shootsToRemove.add(shoot);
            }
        }

        for (Shoot shoot: shootsToRemove){
            shoots.removeValue(shoot, true);
        }
    }
}
