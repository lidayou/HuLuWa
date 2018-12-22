package com.lgy.being;


import com.lgy.annotation.ClassAnnotation;
import com.lgy.util.StyleImage;

@ClassAnnotation(descrip = "描述人类")
public class Human extends GoodCreature {
    public Human(int x, int y, StyleImage image,int hp,boolean alive,int battle,String name,int speed,int id) {
        super(x,y,image,hp,alive,battle,name,speed,id);
    }
    @Override
    public Object clone() {
        Human temp = null;
        temp = (Human) super.clone();	//浅复制
        return temp;
    }
}