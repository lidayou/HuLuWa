package com.lgy.being;


import com.lgy.annotation.ClassAnnotation;
import com.lgy.util.StyleImage;

@ClassAnnotation(descrip = "描述小怪")
public class LittleMonster extends BadCreature{
    public LittleMonster(int x, int y, StyleImage image,int hp,boolean alive,int battle,String name,int speed,int id) {
        super(x,y,image,hp,alive,battle,name,speed,id);
    }
    @Override
    public Object clone() {
        LittleMonster temp = null;
        temp = (LittleMonster) super.clone();	//浅复制
        return temp;
    }
}
