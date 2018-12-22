package com.lgy.formation;

import com.lgy.annotation.ClassAnnotation;
import com.lgy.space.Square;

@ClassAnnotation(descrip = "方钔阵")
public class FormationFangMen extends  Formation{
    public FormationFangMen(int x,int y,int z) {
        //8格 (x-2,y) (x+2,y) (x-1,y-1) (x+1,y-1) (x-1,y+1) (x+1,y+1) (x,y-2) (x,y+2)
        formation.add(new Square(x-2,y,null));
        formation.add(new Square(x+2,y,null));
        formation.add(new Square(x-1,y-1,null));
        formation.add(new Square(x+1,y-1,null));
        formation.add(new Square(x-1,y+1,null));
        formation.add(new Square(x+1,y+1,null));
        formation.add(new Square(x,y-2,null));
        formation.add(new Square(x,y+2,null));
    }
}
