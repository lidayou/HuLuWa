package com.lgy.util;

import com.lgy.annotation.ClassAnnotation;
import com.lgy.being.Being;

@ClassAnnotation(descrip = "描述方向向量 从而为具体的上下左右这些概念提供支撑")
public class DirectionVector implements  Cloneable,java.io.Serializable{
    private double x;
    private double y;
    public DirectionVector(double t1,double t2){
        x=t1;
        y=t2;
    }
    public double getX(){
        return x;
    }
    public double getY(){
        return y;
    }
    @Override
    public Object clone() {
        DirectionVector temp = null;
        try{
            temp = (DirectionVector)super.clone();	//浅复制
        }catch(CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return temp;
    }
}

