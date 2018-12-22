package com.lgy.administer;

import com.lgy.annotation.ClassAnnotation;

import java.util.ArrayList;

@ClassAnnotation(descrip = "负责准备运载火箭 发射载荷")
public class ThreadAdminister {

    private ArrayList<Thread> threads=new ArrayList<>();
    public void addOne(Thread t){
        threads.add(t);
    }
    public void clearAll(){
        threads.clear();
    }
    //发射
    public void startAll(){
        for(Thread t:threads)
            t.start();
    }
    /**
     * @Description: 主动去中断线程 主要供游戏结束使用
     * @Param: []
     * @return: void
     */
    public void interruptAll(){
        for(Thread t:threads){
            t.interrupt();
        }
    }
}
