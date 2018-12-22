package com.lgy.formation;

import com.lgy.annotation.ClassAnnotation;
import com.lgy.space.Square;

@ClassAnnotation(descrip = "雁行阵")
public class FormationYanXing extends Formation {
    public FormationYanXing(int x,int y,int z) {
        //x,y端点
        formation.add(new Square(x,y,null));
        formation.add(new Square(x-1,y+1,null));
        formation.add(new Square(x-2,y+2,null));
        formation.add(new Square(x-3,y+3,null));
        formation.add(new Square(x-1,y,null));
        formation.add(new Square(x-2,y+1,null));
        formation.add(new Square(x-3,y+2,null));
        formation.add(new Square(x-4,y+3,null));
    }
}
