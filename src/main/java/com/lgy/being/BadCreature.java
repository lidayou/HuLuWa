package com.lgy.being;


import com.lgy.annotation.ClassAnnotation;
import com.lgy.util.StyleImage;

@ClassAnnotation(descrip = "邪恶生物，继承自Creature，下面主要有小怪、蛇精、蝎子精")
public class BadCreature extends Creature{
    public BadCreature(int x, int y, StyleImage image,int hp,boolean alive,int battle,String name,int speed,int id){
        super(x,y,image,hp,alive,battle,name,speed,id);
    }
    @Override
    public Object clone() {
        BadCreature temp = null;
        temp = (BadCreature) super.clone();	//浅复制
        return temp;
    }

}
