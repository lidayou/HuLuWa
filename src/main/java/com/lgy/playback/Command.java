package com.lgy.playback;

import com.lgy.annotation.ClassAnnotation;
import com.lgy.being.Creature;

@ClassAnnotation(descrip = "描述存档的每一条记录。记录包括一个主体，和具体的行为")
public class Command implements java.io.Serializable{
    private Creature creature;  //实施的主体
    private Behave behave;  //行为 分为攻击行为 移动行为
    public Command(Creature creature,Behave behave){
        this.creature=(Creature) creature.clone();
        this.behave=(Behave) behave.clone();
    }
    public Creature getCreature(){return creature;}
    public Behave getBehave(){return behave;}

}
