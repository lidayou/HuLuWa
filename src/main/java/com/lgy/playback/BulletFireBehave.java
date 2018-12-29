package com.lgy.playback;

import com.lgy.being.Bullet;

public class BulletFireBehave extends Behave{
    private Bullet bullet;

    public BulletFireBehave(int sleepTime,Bullet bullet) {
        super(sleepTime);
        this.bullet=(Bullet)bullet.clone();
    }
    //需要进行深拷贝
    @Override
    public Object clone() {
        BulletFireBehave temp = null;
        temp = (BulletFireBehave)super.clone();	//浅复制
        temp.bullet = (Bullet) bullet.clone();	//深度复制 若不进行 temp.enemy和enemy.enemy指向的是同一块区域
        return temp;
    }
    public Bullet getBullet(){
        return bullet;
    }
}
