package com.lgy.being;

import com.lgy.annotation.ClassAnnotation;
import com.lgy.util.StyleImage;

@ClassAnnotation(descrip = "表示正义生物，虽然目前没有额外增添属性，但使得类的关系层次化")
public class GoodCreature extends Creature{
    public GoodCreature(int x, int y, StyleImage image,int hp,boolean alive,int battle,String name,int speed,int id){
        super(x,y,image,hp,alive,battle,name,speed,id);
    }
    @Override
    public Object clone() {
        GoodCreature temp = null;
        temp = (GoodCreature) super.clone();	//浅复制
        return temp;
    }
}