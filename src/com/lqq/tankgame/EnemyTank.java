package com.lqq.tankgame;

import java.util.Map;
import java.util.Vector;

public class EnemyTank extends Tank implements Runnable {
    Vector<Shot> shots = new Vector<>();
    Shot shot = null;

    public EnemyTank(int x, int y, int direction, int speed) {
        super(x, y, direction, speed);
    }

    @Override
    public void run() {
        while (true) {
            //射击
            if (isLive() && shots.size() < 3) {
                //根据坦克方向创建子弹
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
                new Thread(shot).start();
            }
            //根据坦克的方向进行移动
            for (int i = 0; i < 30; i++) {
                tankMove();
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            //随机改变方向
            double randomDirection = Math.random();
            if (randomDirection <= 0.25) {
                this.setDirection(0);
            } else if (randomDirection <= 0.5) {
                this.setDirection(1);
            } else if (randomDirection <= 0.75) {
                this.setDirection(2);
            } else {
                this.setDirection(3);
            }
            if (!isLive()) {
                break;
            }
        }
    }
}
