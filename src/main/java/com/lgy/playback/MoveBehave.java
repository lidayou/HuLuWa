package com.lgy.playback;

import com.lgy.annotation.ClassAnnotation;

@ClassAnnotation(descrip = "描述移动的行为")
public class MoveBehave extends Behave{
    private int dx;
    private int dy;

    public MoveBehave(int sleepTime,int dx,int dy) {
        super(sleepTime);
        this.dx=dx;
        this.dy=dy;
    }
    //由于都是原生数据类型，只进行浅拷贝
    @Override
    public Object clone() {
        MoveBehave temp = null;
        temp = (MoveBehave) super.clone();
        return temp;
    }
    public int getDx(){return dx;}
    public int getDy(){return dy;}
}
