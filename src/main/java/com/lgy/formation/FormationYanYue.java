package com.lgy.formation;

import com.lgy.annotation.ClassAnnotation;
import com.lgy.space.Square;

@ClassAnnotation(descrip = "偃月阵")
public class FormationYanYue extends Formation{
    public FormationYanYue(int x,int y,int z){
        formation.add(new Square(x,y,null));
        formation.add(new Square(x-1,y,null));
        formation.add(new Square(x+1,y,null));
        formation.add(new Square(x,y+1*z,null));
        formation.add(new Square(x-1,y+1*z,null));
        formation.add(new Square(x-2,y+1*z,null));
        formation.add(new Square(x+1,y+1*z,null));
        formation.add(new Square(x+2,y+1*z,null));
    }
}
