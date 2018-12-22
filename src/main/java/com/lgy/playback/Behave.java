package com.lgy.playback;

import com.lgy.annotation.ClassAnnotation;

@ClassAnnotation(descrip = "描述存档的每一条记录中的具体行为 该类作为基类 下分具体的类别行为")
public class Behave implements java.io.Serializable,Cloneable{
    private int sleepTime;  //表示一次行为需要的时间
    public Behave(int sleepTime){
        this.sleepTime=sleepTime;
    }
    @Override
    public Object clone() {
        Behave temp = null;
        try{
            temp = (Behave)super.clone();	//浅复制
        }catch(CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return temp;
    }
    public int getSleepTime(){return sleepTime;}
}
