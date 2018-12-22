package com.lgy.space;

import com.lgy.annotation.ClassAnnotation;
import com.lgy.being.Being;

@ClassAnnotation(descrip = "描述二维战斗数组中每一个单元具体的类型")
public class Square<T extends Being>{
    private int x;
    private int y;
    private T being;
    public Square(int x,int y,T being) {
        this.x=x;
        this.y=y;
        this.being=being;

    }

    public Square(){
        this.x=0;
        this.y=0;
        being=null;
    }

    public int getX(){
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x){
        this.x=x;
    }
    public void setY(int y){
        this.y=y;
    }
    public Being getBeing(){
        return being;
    }

    public void setBeing(T result){
        being=result;
    }

}
