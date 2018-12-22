package com.lgy.util;

import com.lgy.annotation.ClassAnnotation;

@ClassAnnotation(descrip = "描述方向向量 从而为具体的上下左右这些概念提供支撑")
public class DirectionVector {
    private int x;
    private int y;
    public DirectionVector(int t1,int t2){
        x=t1;
        y=t2;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
}

