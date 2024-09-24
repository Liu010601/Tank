package com.lqq.tankgame;

import java.util.Vector;

public class MyTank extends Tank {
    //射击行为
    private Shot shot = null;

    //子弹集合
    Vector<Shot> shots = new Vector<>();

    public MyTank(int x, int y, int direction, int speed) {
        super(x, y, direction, speed);
    }

    public Shot getShot() {
        return shot;
    }

    //射击
    public void shootEnemy() {
        //根据当前坦克位置和方向创建
        switch (getDirection()) {
            case 0: //上
                shot = new Shot(getX() + 20, getY(), 0);
                break;
            case 1: //下
                shot = new Shot(getX() + 20, getY() + 60, 1);
                break;
            case 2: //左
                shot = new Shot(getX(), getY() + 20, 2);
                break;
            case 3: //右
                shot = new Shot(getX() + 60, getY() + 20, 3);
                break;
        }
        shots.add(shot);
        //启动子弹线程
        new Thread(shot).start();
    }
}
