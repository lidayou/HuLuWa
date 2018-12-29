package com.lgy.being;

import com.lgy.annotation.ClassAnnotation;
import com.lgy.util.DirectionVector;
import com.lgy.util.StyleImage;

@ClassAnnotation(descrip = "子弹 只继承自Being")
public class Bullet extends  Being {

    private DirectionVector moveDirection;
    //图形上的像素坐标
    private double d_x;
    private double d_y;
    private int speed=80;   //暂时没有用到
    private int attack=10;
    public Bullet(int x, int y, StyleImage image, String name,DirectionVector moveDirection,double d_x,double d_y) {
        super(x, y, image, name);
        this.moveDirection=moveDirection;
        this.d_x=d_x;
        this.d_y=d_y;
    }

    public void move(){
        d_x+=moveDirection.getX();
        d_y+=moveDirection.getY();
    }
    public double getD_x(){
        return d_x;
    }
    public double getD_y(){
        return d_y;
    }
    public int getAttack(){
        return attack;
    }

    @Override
    public Object clone() {
        Bullet temp = null;
        temp = (Bullet) super.clone();	//浅复制
        return temp;
    }
}
