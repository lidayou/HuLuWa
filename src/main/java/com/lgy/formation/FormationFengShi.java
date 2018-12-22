package com.lgy.formation;

import com.lgy.annotation.ClassAnnotation;
import com.lgy.space.Square;

@ClassAnnotation(descrip = "锋矢阵")
public class FormationFengShi extends Formation{
    public FormationFengShi(int x,int y,int z) {
        //x,y表示箭头
        formation.add(new Square(x,y,null));
        formation.add(new Square(x,y-1*z,null));
        formation.add(new Square(x,y-2*z,null));
        formation.add(new Square(x,y-3*z,null));
        formation.add(new Square(x-1,y-1*z,null));
        formation.add(new Square(x+1,y-1*z,null));
        formation.add(new Square(x-2,y-2*z,null));
        formation.add(new Square(x+2,y-2*z,null));
    }
}
