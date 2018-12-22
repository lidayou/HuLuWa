package com.lgy.formation;

import com.lgy.annotation.ClassAnnotation;
import com.lgy.space.Square;

@ClassAnnotation(descrip = "衡伡阵")
public class FormationHengChe extends Formation{
    public FormationHengChe(int x,int y,int z) {
        //x,y中间节点
        formation.add(new Square(x,y,null));
        formation.add(new Square(x-2,y,null));
        formation.add(new Square(x+2,y,null));
        formation.add(new Square(x,y-1*z,null));
        formation.add(new Square(x-1,y-1*z,null));
        formation.add(new Square(x-2,y-1*z,null));
        formation.add(new Square(x+1,y-1*z,null));
        formation.add(new Square(x+2,y-1*z,null));
    }
}
