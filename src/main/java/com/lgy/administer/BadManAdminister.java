package com.lgy.administer;

import com.lgy.annotation.ClassAnnotation;
import com.lgy.being.LittleMonster;
import com.lgy.being.Scorpion;
import com.lgy.being.SnakeEssence;
import com.lgy.util.StyleImage;

import java.util.ArrayList;

//管理妖精们
//6个小怪 1个蛇精 1个蝎子精
@ClassAnnotation(descrip = "管理敌方阵营包括6个小怪和1个蝎子精、1个蛇精")
public class BadManAdminister {
    //管理妖怪实体的数组
    private ArrayList<LittleMonster> monsters=new ArrayList<>();
    //管理蛇精
    private SnakeEssence snakeEssence;
    //管理蝎子精
    private Scorpion scorpion;
    //对成员变量初始化
    public BadManAdminister(){
        for(int i=0;i<6;i++)
            monsters.add(new LittleMonster(-1,-1, StyleImage.LITTLEMONSTER,50,true,10,"小怪"+i+"号",3400,9+i));
        snakeEssence=new SnakeEssence(-1,-1,StyleImage.SNAKE,70,true,20,"蛇精",2300,17);
        scorpion=new Scorpion(-1,-1,StyleImage.SCORP,50,true,30,"蝎子精",2800,18);
    }
    /**
     * @Description: 向外暴露，返回小怪数组
     * @Param: []
     * @return: java.util.ArrayList<com.lgy.being.LittleMonster>
     */
    public ArrayList<LittleMonster> getMonsters(){
        return monsters;
    }
    //同上 返回蛇精
    public SnakeEssence getSnakeEssence(){
        return snakeEssence;
    }
    //同上 返回蝎子精
    public Scorpion getScorpion(){
        return scorpion;
    }
    //同样对成员变量初始化
    public void initial(){
        monsters.clear();
        for(int i=0;i<6;i++)
            monsters.add(new LittleMonster(-1,-1, StyleImage.LITTLEMONSTER,50,true,10,"小怪"+i+"号",3400,9+i));
        snakeEssence=new SnakeEssence(-1,-1,StyleImage.SNAKE,70,true,20,"蛇精",2300,17);
        scorpion=new Scorpion(-1,-1,StyleImage.SCORP,50,true,30,"蝎子精",2800,18);
    }
    /**
     * @Description: 管理者判断当前是否所有坏人角色都死亡 这里是蛇精死亡 即刻结束
     * @Param: []
     * @return: boolean
     */
    public boolean isAllDie(){
        if(snakeEssence.isAlive()==false)
            return true;

        for(LittleMonster monster:monsters){
            if(monster.isAlive())
                return false;
        }
        if(snakeEssence.isAlive())
            return false;
        if(scorpion.isAlive())
            return false;
        return true;
    }
}
