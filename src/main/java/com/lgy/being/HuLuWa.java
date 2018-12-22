package com.lgy.being;


import com.lgy.annotation.ClassAnnotation;
import com.lgy.util.StyleImage;

@ClassAnnotation(descrip = "描述葫芦娃")
public class HuLuWa extends GoodCreature{
    //额外增添属性rank 用于排序整理操作
    private int rank;
    public HuLuWa(int x, int y, StyleImage image, int rank,int hp,boolean alive,int battle,String name,int speed,int id) {
        super(x,y,image,hp,alive,battle,name,speed,id);
        this.rank=rank;
    }
    public int getRank(){
        return rank;
    }

    @Override
    public Object clone() {
        HuLuWa temp = null;
        temp = (HuLuWa) super.clone();	//浅复制
        return temp;
    }
}
