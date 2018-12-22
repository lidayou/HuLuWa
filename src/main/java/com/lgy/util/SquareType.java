package com.lgy.util;

import com.lgy.annotation.ClassAnnotation;

//这个类被废弃 因为后来没有用到
@Deprecated
@ClassAnnotation(descrip = "描述每一个战斗格子的状态 BEINGDIE 表示刚有一个角色死亡 BEINGMOVE 表示刚有一个角色产生移动 NOTHING 表示空状态")
public enum SquareType {
    BEINGDIE,BEINGMOVE,NOTHING
}
