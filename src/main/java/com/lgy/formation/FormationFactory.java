package com.lgy.formation;

import com.lgy.annotation.ClassAnnotation;

@ClassAnnotation(descrip = "用于生成阵型的工厂，formation包中主要通过create方法向外暴露")
public class FormationFactory{
    /**
     * @Description:采用工厂方法生成阵型
     * @Param: [i, arg1, arg2, type] i代表生成哪种阵型 arg1 arg2用于阵型的生成参数 type表示是敌方还是我方阵营
     * @return: com.lgy.formation.Formation
     */
    public static Formation  create(int i,int arg1,int arg2,int type) {
        i=i%8;
        switch (i) {
            case 0:return new FormationYanYi(arg1,arg2,type);
            case 1:return new FormationYanXing(arg1,arg2,type);
            case 2:return new FormationHengChe(arg1,arg2,type);
            case 3:return new FormationChangShe(arg1,arg2,type);
            case 4:return new FormationYuLin(arg1,arg2,type);
            case 5:return new FormationFangMen(arg1,arg2,type);
            case 6:return new FormationYanYue(arg1,arg2,type);
            case 7:return new FormationFengShi(arg1,arg2,type);
            default:
                break;
        }
        return null;
    }
}

