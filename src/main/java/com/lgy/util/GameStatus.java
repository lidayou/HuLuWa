package com.lgy.util;

import com.lgy.annotation.ClassAnnotation;

@ClassAnnotation(descrip = "描述当前游戏进行的状态 分为4中 准备、正在随机进行(多线程)、正在回放、结束")
public enum GameStatus {
    READY,DOINGRANDOM,DOINGPLAYBACK,GAMEOVER
}
