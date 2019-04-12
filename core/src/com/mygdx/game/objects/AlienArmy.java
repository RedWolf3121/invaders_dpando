package com.mygdx.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.AssetLoader;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Assets;
import com.mygdx.game.Timer;

import java.util.Random;

public class AlienArmy {

    int x, y, maxX;

    float speed = 8f;
    float speedDown = 3f;

    float moveTimerLimit = 0.8f;

    Array<Alien> aliens;
    Array<AlienShoot> shoots;

    Timer moveTimer, shootTimer;
    Random random = new Random();
    private int WORLD_HEIGHT;

    int levelArmy = 1;
    boolean gameOver = false;

    AlienArmy(int WORLD_WIDTH, int WORLD_HEIGHT){

        this.WORLD_HEIGHT = WORLD_HEIGHT;
        this.x = 0;
        this.y = WORLD_HEIGHT-30;
        this.maxX = 20;

        aliens = new Array<Alien>();
        shoots = new Array<AlienShoot>();

        moveTimer = new Timer(moveTimerLimit);
        shootTimer = new Timer(random.nextFloat()%5+1);

        positionAliens();
    }


    void render(SpriteBatch batch){
        for(Alien alien: aliens) {
            alien.render(batch);
        }

        for (AlienShoot shoot: shoots) {
            shoot.render(batch);
        }
    }

    public void update(float delta, Assets assets) {
        moveTimer.update(delta);
        shootTimer.update(delta);

        move();
        shoot(assets);

        for(Alien alien: aliens) {
            alien.update(delta, assets);
        }

        for(AlienShoot shoot: shoots){
            shoot.update(delta, assets);
        }

        removeDeadAliens();
        removeShoots();
    }


    void positionAliens(){
        for (int i = 0; i < 4; i++) {  // fila
            for (int j = 0; j < 12; j++) {  // columna
                aliens.add(new Alien(j*30 + 10, y - i*12));
            }
        }
    }

    void resetArmy(){
        aliens.clear();
        positionAliens();
        levelArmy++;
        x = 0;  // reset posición de aliens
        this.y = WORLD_HEIGHT-30;   // reset posición de aliens
        if (levelArmy > 5) {
            finish(gameOver);
        } else {
            moveTimerLimit *= 0.7f;
            moveTimer = new Timer(moveTimerLimit);
        }

    }

    private void finish(boolean gameOver) {
        aliens.clear();
        gameOver = true;
    }


    void move() {
        boolean downLine = false;

        if (moveTimer.check()){
            x += speed;

            if(x > maxX){
                downLine = true;
                x = maxX;
                speed *= -1;
            } else if(x < 0){
                downLine = true;
                x = 0;
                speed *= -1;
            }

            for (Alien alien : aliens) {
                if (downLine) {
                    alien.position.y -= speedDown;
                } else {
                    alien.position.x += speed;
                }
            }
        }
    }

    void shoot(Assets assets){
        if(shootTimer.check() && aliens.size > 0){
            int alienNum = random.nextInt(aliens.size);   // 5   0 1 2 3 4   // 0

            Alien alien = aliens.get(alienNum);

            shoots.add(new AlienShoot(new Vector2(alien.position)));

            assets.alienSound.play();

            shootTimer.set(random.nextFloat()%5+1);
        }

        if (aliens.size <= 0) {
            resetArmy();
        }
    }

    private void removeDeadAliens() {
        Array<Alien> aliensToRemove = new Array<Alien>();
        for(Alien alien: aliens){
            if(alien.state == Alien.State.DEAD){
                aliensToRemove.add(alien);
            }
        }

        for (Alien alien: aliensToRemove){
            aliens.removeValue(alien, true);
        }
    }
    public void removeShoots(){
        Array<AlienShoot> shootsToRemove = new Array<AlienShoot>();
        for(AlienShoot shoot:shoots){
            if(shoot.state == AlienShoot.State.TO_REMOVE){
                shootsToRemove.add(shoot);
            }
        }

        for (AlienShoot shoot: shootsToRemove){
            shoots.removeValue(shoot, true);
        }
    }

}
