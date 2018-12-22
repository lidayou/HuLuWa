package com.lgy;

import com.lgy.administer.FormationAdminister;
import com.lgy.formation.Formation;
import com.lgy.formation.FormationFactory;
import com.lgy.gui.FightPlace;
import com.lgy.sample.Main;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class FormationTest {
    //检查初始化时两个阵型是否会crash
    @Test
    public void shouldnotCrash() throws Exception{
        FormationAdminister formationAdminister=new FormationAdminister();
        int index1=formationAdminister.getBadZhenXingIndex();
        int index2=formationAdminister.getGoodZhenXingIndex();
        Formation formation1=FormationFactory.create(index1, formationAdminister.getArg1BadZhengXing(index1), formationAdminister.getArg2BadZhengXing(index1),-1);
        Formation formation2=FormationFactory.create(index2, formationAdminister.getArg1GoodZhengXing(index2), formationAdminister.getArg2GoodZhengXing(index2),1);
        assertEquals(false,formation1.checkWhetherCrash(formation2));
    }
}
