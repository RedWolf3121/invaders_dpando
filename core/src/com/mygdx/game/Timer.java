package com.mygdx.game;

public class Timer {
    float time;
    float limit;

    public Timer(float limit){
        this.limit = limit;
    }

    public void update(float delta){
        time += delta;
    }

    public boolean check(){
        if(time >= limit){
            time = 0;
            return true;
        }
        return false;
    }

    public void set(float limit){
        this.limit = limit;
    }
}
