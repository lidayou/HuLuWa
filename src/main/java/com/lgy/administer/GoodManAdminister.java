package com.lgy.administer;

import com.lgy.annotation.ClassAnnotation;
import com.lgy.being.Creature;
import com.lgy.being.HuLuWa;
import com.lgy.being.Human;
import com.lgy.util.StyleImage;

import java.util.ArrayList;

//管理葫芦娃和爷爷
//7个葫芦娃 1个爷爷
@ClassAnnotation(descrip = "管理几方阵营，包括7个葫芦娃和1个爷爷")
public class GoodManAdminister {
    //管理huluwa七兄弟
    private ArrayList<HuLuWa> brothers=new ArrayList<>();
    //管理老人
    private Human grandpa;

    //对成员变量初始化
    public GoodManAdminister(){
        brothers.add(new HuLuWa(-1,-1, StyleImage.HULUWAONE,1,50,true,10,"红娃",2400,1));
        brothers.add(new HuLuWa(-1,-1,StyleImage.HULUWATWO,2,50,true,10,"橙娃",2400,2));
        brothers.add(new HuLuWa(-1,-1,StyleImage.HULUWATHREE,3,50,true,10,"黄娃",2400,3));
        brothers.add(new HuLuWa(-1,-1,StyleImage.HULUWAFOUR,4,50,true,10,"绿娃",2400,4));
        brothers.add(new HuLuWa(-1,-1,StyleImage.HULUWAFIVE,5,50,true,10,"青娃",2400,5));
        brothers.add(new HuLuWa(-1,-1,StyleImage.HULUWASIX,6,50,true,10,"蓝娃",2400,6));
        brothers.add(new HuLuWa(-1,-1,StyleImage.HULUWASEVEN,7,50,true,10,"紫娃",2400,7));
        grandpa=new Human(-1,1,StyleImage.GRANDPA,70,true,20,"爷爷",3700,8);
    }
    //向外界暴露 返回葫芦娃七兄弟
    public ArrayList<HuLuWa> getHuLuWas(){
        return brothers;
    }
    //返回老人
    public Human getGrandpa(){
        return grandpa;
    }
    //对成员变量初始化
    public void initial(){
        brothers.clear();
        brothers.add(new HuLuWa(-1,-1, StyleImage.HULUWAONE,1,50,true,10,"红娃",2400,1));
        brothers.add(new HuLuWa(-1,-1,StyleImage.HULUWATWO,2,50,true,10,"橙娃",2400,2));
        brothers.add(new HuLuWa(-1,-1,StyleImage.HULUWATHREE,3,50,true,10,"黄娃",2400,3));
        brothers.add(new HuLuWa(-1,-1,StyleImage.HULUWAFOUR,4,50,true,10,"绿娃",2400,4));
        brothers.add(new HuLuWa(-1,-1,StyleImage.HULUWAFIVE,5,50,true,10,"青娃",2400,5));
        brothers.add(new HuLuWa(-1,-1,StyleImage.HULUWASIX,6,50,true,10,"蓝娃",2400,6));
        brothers.add(new HuLuWa(-1,-1,StyleImage.HULUWASEVEN,7,50,true,10,"紫娃",2400,7));
        grandpa=new Human(-1,1,StyleImage.GRANDPA,50,true,20,"爷爷",3700,8);
    }
    /**
     * @Description: 管理者判断所有好人角色是否都已经死亡 这里是爷爷死亡 即刻结束
     * @Param: []
     * @return: boolean
     */
    public boolean isAllDie(){
        if(grandpa.isAlive()==false)
            return true;

        for(HuLuWa huluwa:brothers){
            if(huluwa.isAlive())
                return false;
        }
        if(grandpa.isAlive())
            return false;
        return true;
    }
}
