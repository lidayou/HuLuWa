package com.lgy.being;

import com.lgy.annotation.ClassAnnotation;
import com.lgy.util.StyleImage;

@ClassAnnotation(descrip = "描述蛇精")
public class SnakeEssence extends BadCreature {

    public SnakeEssence(int x, int y , StyleImage image,int hp,boolean alive,int battle,String name,int speed,int id) {
        super(x,y,image,hp,alive,battle,name,speed,id);
    }
    @Override
    public Object clone() {
        SnakeEssence temp = null;
        temp = (SnakeEssence) super.clone();	//浅复制
        return temp;
    }
}
