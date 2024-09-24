package com.lqq.tankgame;

public class Bomb {
    int x, y;    //炸弹动画的坐标
    int life = 9;   //炸弹动画的消失时间
    boolean isLive = true;  //是否需要停止动画播放

    public Bomb(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void lifeDown() {
        if (life > 0) {
            life--;
        } else {
            isLive = false;
        }
    }
}
