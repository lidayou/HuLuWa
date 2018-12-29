package com.lgy.playback;

import com.lgy.annotation.ClassAnnotation;
import com.lgy.being.Creature;

@ClassAnnotation(descrip = "召唤行为")
public class CallBahave extends Behave{
    private Creature c;

    public CallBahave(int sleepTime,Creature c) {
        super(sleepTime);
        this.c=c;
    }
    @Override
    public Object clone() {
        CallBahave temp = null;
        temp = (CallBahave) super.clone();
        temp.c=(Creature)c.clone(); //深拷贝
        return temp;
    }
    public Creature getCreature(){return c;}
}
