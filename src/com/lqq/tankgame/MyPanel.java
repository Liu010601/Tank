package com.lqq.tankgame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Iterator;
import java.util.Vector;

//坦克大战的绘图区
public class MyPanel extends JPanel implements KeyListener, Runnable {
    //定义我的坦克
    MyTank myTank = null;
    //定义敌人坦克
    Vector<EnemyTank> enemyTanks = new Vector<>();
    //确定敌人坦克数量
    int enemyTankSize = 3;
    //存放炸弹
    Vector<Bomb> bombs = new Vector<>();
    Image image = null;

    public MyPanel() {
        //初始化我的坦克
        myTank = new MyTank(500, 500, 0, 10);
        //初始化敌人的坦克
        for (int i = 0; i < enemyTankSize; i++) {
            enemyTanks.add(new EnemyTank(100 * (i + 1), 0, (int) (Math.random() * 4), 5));
            //启动敌人坦克进程
            new Thread(enemyTanks.get(i)).start();
        }
        //初始化图片
        image = Toolkit.getDefaultToolkit().getImage(getClass().getResource("1.jpg"));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        //填充矩形，默认黑色
        g.fillRect(0, 0, 1000, 750);


        //画出敌人的坦克
        for (int i = 0; i < enemyTanks.size(); i++) {
            EnemyTank enemyTank = enemyTanks.get(i);
            //判断当前敌人坦克是否还存活
            if (enemyTank.isLive()) {
                drawTank(enemyTank.getX(), enemyTank.getY(), g, enemyTank.getDirection(), 1);
                //画出敌人坦克的子弹
                for (int j = 0; j < enemyTank.shots.size(); j++) {
                    Shot shot = enemyTank.shots.get(j);
                    if (shot.isLive) {
                        g.fillOval(shot.x, shot.y, 4, 4);
                    } else {
                        //从Vector移除
                        enemyTank.shots.remove(shot);
                    }
                }
            }
        }

        //画坦克
        if (myTank != null && myTank.isLive()) {
            drawTank(myTank.getX(), myTank.getY(), g, myTank.getDirection(), 0);
        }
        //画出子弹
        for (int i = 0; i < myTank.shots.size(); i++) {
            Shot shot = myTank.shots.get(i);
            if (shot != null && shot.isLive) {
                g.fillOval(shot.x, shot.y, 4, 4);
            } else {
                myTank.shots.remove(shot);
            }
        }
        //如果Bomb中有炸弹，代表有坦克被摧毁，需要绘制爆炸图片
        for (int i = 0; i < bombs.size(); i++) {
            Bomb bomb = bombs.get(i);
            g.drawImage(image, bomb.x, bomb.y, 60, 60, this);
            bombs.remove(bomb);
        }
    }

    //编写画坦克的方法

    /**
     * @param x:坦克的横坐标
     * @param y:坦克的纵坐标
     * @param g:画笔
     * @param direction:坦克的方向
     * @param type:标明坦克的种类
     */
    public void drawTank(int x, int y, Graphics g, int direction, int type) {
        switch (type) {
            case 0: //自己的坦克
                g.setColor(Color.cyan);
                break;
            case 1: //敌人的坦克
                g.setColor(Color.yellow);
                break;
        }

        //根据坦克的方向来绘制坦克
        switch (direction) {
            case 0: //向上的坦克
                //画出坦克左边的轮子
                g.fill3DRect(x, y, 10, 60, false);
                //画出坦克中间的身体
                g.fill3DRect(x + 10, y + 10, 20, 40, false);
                //画出坦克右边的轮子
                g.fill3DRect(x + 30, y, 10, 60, false);
                //画出坦克的圆形
                g.fillOval(x + 10, y + 20, 20, 20);
                //画出坦克的炮管
                g.drawLine(x + 20, y + 30, x + 20, y);
                break;
            case 1: //向下的坦克
                //画出坦克左边的轮子
                g.fill3DRect(x, y, 10, 60, false);
                //画出坦克中间的身体
                g.fill3DRect(x + 10, y + 10, 20, 40, false);
                //画出坦克右边的轮子
                g.fill3DRect(x + 30, y, 10, 60, false);
                //画出坦克的圆形
                g.fillOval(x + 10, y + 20, 20, 20);
                //画出坦克的炮管
                g.drawLine(x + 20, y + 30, x + 20, y + 60);
                break;
            case 2: //向左的坦克
                //画出坦克上边的轮子
                g.fill3DRect(x, y, 60, 10, false);
                //画出坦克中间的身体
                g.fill3DRect(x + 10, y + 10, 40, 20, false);
                //画出坦克下边的轮子
                g.fill3DRect(x, y + 30, 60, 10, false);
                //画出坦克的圆形
                g.fillOval(x + 20, y + 10, 20, 20);
                //画出坦克的炮管
                g.drawLine(x + 30, y + 20, x, y + 20);
                break;
            case 3: //向右的坦克
                //画出坦克上边的轮子
                g.fill3DRect(x, y, 60, 10, false);
                //画出坦克中间的身体
                g.fill3DRect(x + 10, y + 10, 40, 20, false);
                //画出坦克下边的轮子
                g.fill3DRect(x, y + 30, 60, 10, false);
                //画出坦克的圆形
                g.fillOval(x + 20, y + 10, 20, 20);
                //画出坦克的炮管
                g.drawLine(x + 30, y + 20, x + 60, y + 20);
                break;
            default:
        }
    }

    //编写子弹打到敌人坦克
    public boolean hitEnemyTank(Shot shot, Tank tank) {
        if (!tank.isLive()) {
            return false;
        }
        boolean hit = false;
        switch (tank.getDirection()) {
            case 0: //面向上方的坦克被击中
            case 1: //面向下方的坦克被击中
                hit = (shot.x > tank.getX() && shot.x < tank.getX() + 40
                        && shot.y > tank.getY() && shot.y < tank.getY() + 60);
                break;
            case 2: //面向左方的坦克被击中
            case 3: //面向右方的坦克被击中
                hit = (shot.x > tank.getX() && shot.x < tank.getX() + 60
                        && shot.y > tank.getY() && shot.y < tank.getY() + 40);
                break;
        }
        if (hit) {
            shot.isLive = false;
            tank.setLive(false);
            Bomb bomb = new Bomb(tank.getX(), tank.getY());
            bombs.add(bomb);
        }
        return hit;
    }

    //编写子弹打到自己坦克
    public void hitMyTank() {
        for (EnemyTank enemyTank : enemyTanks) {
            for (Shot shot1 : enemyTank.shots) {
                if (shot1.isLive && myTank.isLive()) {
                    hitEnemyTank(shot1, myTank);
                }
            }
        }
    }

    //用来控制坦克移动
    @Override
    public void keyPressed(KeyEvent e) {
        //根据按键进行不同的移动
        if (e.getKeyCode() == KeyEvent.VK_W) {
            myTank.tankMove();
            if (myTank.getDirection() != 0) {
                myTank.setDirection(0);
            }
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            myTank.tankMove();
            if (myTank.getDirection() != 1) {
                myTank.setDirection(1);
            }
        } else if (e.getKeyCode() == KeyEvent.VK_A) {
            myTank.tankMove();
            if (myTank.getDirection() != 2) {
                myTank.setDirection(2);
            }
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            myTank.tankMove();
            if (myTank.getDirection() != 3) {
                myTank.setDirection(3);
            }
        }
        //如果用户按下J响应射击
        if (e.getKeyCode() == KeyEvent.VK_J && myTank.shots.size() < 5 && myTank.isLive()) {
            myTank.shootEnemy();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void run() {
        Iterator<EnemyTank> iterator = enemyTanks.iterator();
        while (true) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            //重绘
            this.repaint();
            //判断是否击中了敌人坦克
            for (Shot shot : myTank.shots) {
                if (shot != null && shot.isLive) {
                    while (iterator.hasNext()) {
                        EnemyTank enemyTank = iterator.next();
                        if (hitEnemyTank(shot, enemyTank)) {
                            iterator.remove();
                            break;
                        }
                    }
                }
            }
            //判断是否被敌人子弹击中
            hitMyTank();
        }
    }
}
