package com.lgy.playback;

import com.lgy.annotation.ClassAnnotation;
import com.lgy.being.Creature;

@ClassAnnotation(descrip = "描述攻击行为")
public class AttackBehave extends Behave{
    private Creature enemy;

    public AttackBehave(int sleepTime,Creature enemy) {
        super(sleepTime);
        this.enemy=(Creature) enemy.clone();
    }
    //需要进行深拷贝
    @Override
    public Object clone() {
        AttackBehave temp = null;
        temp = (AttackBehave)super.clone();	//浅复制
        temp.enemy = (Creature)enemy.clone();	//深度复制 若不进行 temp.enemy和enemy.enemy指向的是同一块区域
        return temp;
    }
    public Creature getEnemy(){return enemy;}
}
