package com.lgy.administer;

import com.lgy.annotation.ClassAnnotation;
import com.lgy.formation.Formation;
import com.lgy.formation.FormationFactory;

import java.awt.*;
import java.util.ArrayList;

@ClassAnnotation(descrip = "对两个阵营8种阵型的初始化信息进行管理")
public class FormationAdminister {
    private  int goodZhenXingIndex=0; //记录敌我双方阵型的编号
    private  int badZhenXingIndex=0;

    private static ArrayList<Point> goodStartPoint=new ArrayList<>();   //记录敌我双方8种阵型对应的参数
    private static ArrayList<Point> badStartPoint=new ArrayList<>();
    public static ArrayList<Point> getGoodStartPoint(){return goodStartPoint;}
    public static ArrayList<Point> getBadStartPoint(){return badStartPoint;}

    public  int getGoodZhenXingIndex(){return goodZhenXingIndex;}
    public  int getBadZhenXingIndex(){return badZhenXingIndex;}
    static {
        goodStartPoint.add(new Point(2,0));
        goodStartPoint.add(new Point(4,0));
        goodStartPoint.add(new Point(2,1));
        goodStartPoint.add(new Point(0,0));
        goodStartPoint.add(new Point(2,3));
        goodStartPoint.add(new Point(2,2));
        goodStartPoint.add(new Point(2,0));
        goodStartPoint.add(new Point(2,3));

        badStartPoint.add(new Point(2,8));
        badStartPoint.add(new Point(4,5));
        badStartPoint.add(new Point(2,7));
        badStartPoint.add(new Point(4,1));
        badStartPoint.add(new Point(2,5));
        badStartPoint.add(new Point(2,6));
        badStartPoint.add(new Point(2,8));
        badStartPoint.add(new Point(2,5));
    }

    //用于设置阵型
    public  void setGoodZhenXingIndex(int index) {
        index%=8;
        //需要检测
        Formation f1= FormationFactory.create(index, (int)goodStartPoint.get(index).getX(),(int)goodStartPoint.get(index).getY() ,1);;
        Formation f2=FormationFactory.create(badZhenXingIndex, (int)badStartPoint.get(badZhenXingIndex).getX(),(int)badStartPoint.get(badZhenXingIndex).getY() ,-1);;
        if(f1.checkWhetherCrash(f2)==false)
            goodZhenXingIndex=index%8;
    }
    //用于设置阵型信息
    public  void setBadZhenXingIndex(int index){
        index%=8;
        //需要检测
        Formation f1=FormationFactory.create(goodZhenXingIndex, (int)goodStartPoint.get(goodZhenXingIndex).getX(),(int)goodStartPoint.get(goodZhenXingIndex).getY() ,1);;
        Formation f2=FormationFactory.create(index, (int)badStartPoint.get(index).getX(),(int)badStartPoint.get(index).getY() ,-1);;
        if(f1.checkWhetherCrash(f2)==false)
            badZhenXingIndex=index%8;
    }

    public  int getArg1GoodZhengXing(int index){
        return (int)goodStartPoint.get(index).getX();
    }
    public  int getArg2GoodZhengXing(int index){
        return (int)goodStartPoint.get(index).getY();
    }
    public  int getArg1BadZhengXing(int index){
        return (int)badStartPoint.get(index).getX();
    }
    public  int getArg2BadZhengXing(int index){
        return (int)badStartPoint.get(index).getY();
    }
}
