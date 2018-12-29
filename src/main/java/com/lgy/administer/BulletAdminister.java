package com.lgy.administer;

import com.lgy.annotation.ClassAnnotation;
import com.lgy.being.BadCreature;
import com.lgy.being.Being;
import com.lgy.being.Bullet;
import com.lgy.sample.Main;

import java.util.ArrayList;

@ClassAnnotation(descrip = "对子弹进行管理 每次线程run 都会调整当前保管的子弹信息")
public class BulletAdminister implements Runnable {
    ArrayList<Bullet> bullets=new ArrayList<>();
    @Override
    public void run() {
        while(Main.fightPlace.isOver()==false){
            //睡一会
            try {
                Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }
            synchronized (bullets){
                //移动 对出界的清除
                for(int i=0;i<bullets.size();i++){
                    Bullet b=bullets.get(i);
                    b.move();
                    if(b.getD_x()<210||b.getD_x()>822||b.getD_y()<64||b.getD_y()>489){  //判断出界情况
                        //flag存在的目的是在remove一个数组元素后对游标进行调整
                        boolean flag=false;
                        if(i!=bullets.size()-1){
                            flag=true;
                        }
                        bullets.remove(i);
                        if(flag==true){
                            i--;
                        }
                    }
                }
                //触碰到怪物的 展现攻击效果
                for(int i=0;i<bullets.size();i++){
                    Bullet b=bullets.get(i);
                    synchronized (Main.fightPlace.getBoard()){
                        Being being=Main.fightPlace.getBoard().getBeing(b.getD_x(),b.getD_y());
                        if(being instanceof BadCreature){
                            synchronized (being){
                                if(((BadCreature) being).isAlive()==true){
                                    ((BadCreature) being).setHp(((BadCreature) being).getHp()-b.getAttack());
                                    if(((BadCreature) being).getHp()<=0){
                                        ((BadCreature) being).setHp(0);
                                        ((BadCreature) being).setAlive(false);
                                    }
                                    boolean flag=false;
                                    if(i!=bullets.size()-1){
                                        flag=true;
                                    }
                                    bullets.remove(i);
                                    if(flag==true){
                                        i--;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            Main.fightPlace.paint();
        }
    }

    public void addBullet(Bullet b){
        synchronized (bullets){
            bullets.add(b);
        }
    }
    public ArrayList<Bullet> getBullets(){
        return bullets;
    }
    public void clear(){
        bullets.clear();
    }
}
