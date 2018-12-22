package com.lgy.formation;

import com.lgy.annotation.ClassAnnotation;
import com.lgy.space.Square;

@ClassAnnotation(descrip = "长蛇阵")
public class FormationChangShe extends Formation{
    public FormationChangShe(int x,int y,int z) {
        //8格 (x,y) (x,y+1) (x,y+2) (x,y+3) (x,y+4) (x,y+5) (x,y+6) (x,y+7)
        formation.add(new Square(x,y,null));
        formation.add(new Square(x,y+1,null));
        formation.add(new Square(x,y+2,null));
        formation.add(new Square(x,y+3,null));
        formation.add(new Square(x,y+4,null));
        formation.add(new Square(x,y+5,null));
        formation.add(new Square(x,y+6,null));
        formation.add(new Square(x,y+7,null));
    }
}
