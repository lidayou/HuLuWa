package com.lgy.formation;

import com.lgy.annotation.ClassAnnotation;
import com.lgy.space.Square;

@ClassAnnotation(descrip = "雁翼阵")
public class FormationYanYi extends Formation{
    public FormationYanYi(int x,int y,int z) {
        //x,y端点
        formation.add(new Square(x,y,null));
        formation.add(new Square(x-1,y+1*z,null));
        formation.add(new Square(x+1,y+1*z,null));
        formation.add(new Square(x,y+2*z,null));
        formation.add(new Square(x-1,y+2*z,null));
        formation.add(new Square(x-2,y+2*z,null));
        formation.add(new Square(x+1,y+2*z,null));
        formation.add(new Square(x+2,y+2*z,null));
    }
}
