package com.lqq.tankgame;

public class Tank {
    private int x;  //坦克的横坐标
    private int y;  //坦克的纵坐标
    private int direction;  //坦克的方向
    private int speed = 1;  //坦克的速度
    private boolean isLive = true;  //坦克是否存活

    //构造函数
    public Tank(int x, int y, int direction, int speed) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.speed = speed;
    }

    //上下左右移动
    public void tankMove() {
        if (y > 0 && direction == 0) {
            y -= speed;
        } else if (y < 690 && direction == 1) {
            y += speed;
        } else if (x > 0 && direction == 2) {
            x -= speed;
        } else if (x < 940 && direction == 3) {
            x += speed;
        }
    }

    //getter和setter方法
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public boolean isLive() {
        return isLive;
    }

    public void setLive(boolean live) {
        isLive = live;
    }
}
