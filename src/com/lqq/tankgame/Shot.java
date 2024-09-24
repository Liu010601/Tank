package com.lqq.tankgame;

public class Shot implements Runnable {
    int x;  //子弹的横坐标
    int y;  //子弹的纵坐标
    int direction = 0;  //子弹的方向
    int speed = 10;  //子弹的速度
    boolean isLive = true;  //确定子弹是否存活以便于面板的绘制
    //构造器

    public Shot(int x, int y, int direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    //设置子弹的速度
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    @Override
    //射击行为
    public void run() {
        //子弹不断移动
        while (true) {
            //线程休眠，防止过快消失
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            //根据方向进行移动
            switch (direction) {
                case 0: //上
                    y -= speed;
                    break;
                case 1: //下
                    y += speed;
                    break;
                case 2: //左
                    x -= speed;
                    break;
                case 3: //右
                    x += speed;
                    break;
            }
            //当子弹移动到边界或碰到敌人坦克时，则会消失
            if (!(x >= 0 && x <= 1000 && y >= 0 && y <= 750 && isLive)) {
                isLive = false;
                break;
            }
        }
    }
}
