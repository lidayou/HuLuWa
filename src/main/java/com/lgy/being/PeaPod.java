package com.lgy.being;

import com.lgy.playback.BulletFireBehave;
import com.lgy.playback.CallBahave;
import com.lgy.playback.Command;
import com.lgy.sample.Main;
import com.lgy.util.DirectionVector;
import com.lgy.util.StyleImage;

public class PeaPod extends GoodCreature{

    public PeaPod(int x, int y, StyleImage image, int hp, boolean alive, int battle, String name, int speed, int id) {
        super(x,y,image,hp,alive,battle,name,speed,id);
    }

    @Override
    public Object clone() {
        PeaPod temp = null;
        temp = (PeaPod) super.clone();	//浅复制
        return temp;
    }

    @Override
    public  void run(){
        while(Main.fightPlace.isOver()==false) {
            if (this.alive == false)       //如果这个生物死了 这个线程也就不run了
                break;
            //移动的速度决定了睡眠时间 从而反应了生物单步决策需要的时间
            try {
                Thread.sleep(speed);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(Main.random.nextDouble()<0.1) {
                DirectionVector v = new DirectionVector(40, 0);
                int d_x = Main.fightPlace.BaseX + (y + 1) * Main.fightPlace.BlockXSize;
                int d_y = Main.fightPlace.BaseY + x * Main.fightPlace.BlockYSize + Main.fightPlace.BlockYSize / 2;
                Bullet bullet = new Bullet(0, 0, StyleImage.BULLET, "bullet", v, d_x, d_y);
                //if(Main.fightPlace.isSavePlay()==true){
                    long curTime = System.currentTimeMillis();
                    //准备记录信息，写入数组之中
                    System.out.println("write bullet");
                    Command command = new Command(this, new BulletFireBehave((int) (curTime - Main.fightPlace.startTime), bullet));
                    synchronized (Main.fightPlace.getCommandsAdminister().getCommands()) {
                        Main.fightPlace.getCommandsAdminister().addOne(command);
                    }
                //}
                Main.fightPlace.getBulletAdminister().addBullet(bullet);
                Main.fightPlace.paint();
            }
        }
    }
}
