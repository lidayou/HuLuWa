package com.lgy;

import com.lgy.being.HuLuWa;
import com.lgy.being.Human;
import com.lgy.being.LittleMonster;
import com.lgy.being.Scorpion;
import com.lgy.space.Board;
import com.lgy.util.StyleImage;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

//检测攻击行为
public class CreatureAttackTest {
    //不在范围内不会产生攻击效果
    @Test
    public void shouldnotAttack() throws Exception{
        Board board=new Board(5,9);
        HuLuWa hulu1=(new HuLuWa(-1,-1, StyleImage.HULUWAONE,1,50,true,10,"红娃",2400,1));
        Scorpion scorpion=new Scorpion(-1,-1,StyleImage.SCORP,50,true,30,"蝎子精",2800,18);
        board.putCreature(hulu1,0,1);
        board.putCreature(scorpion,2,1);
        scorpion.attack(hulu1,board,null,false);
        //葫芦娃不会受到攻击 因为不在距离范围内
        assertEquals(50,hulu1.getHp());
    }


    //检测掉血情况
    @Test
    public void shouldLossHp() throws Exception{
        Board board=new Board(5,9);
        HuLuWa hulu1=(new HuLuWa(-1,-1, StyleImage.HULUWAONE,1,50,true,10,"红娃",2400,1));
        Scorpion scorpion=new Scorpion(-1,-1,StyleImage.SCORP,50,true,80,"蝎子精",2800,18);
        board.putCreature(hulu1,0,1);
        board.putCreature(scorpion,1,1);
        scorpion.attack(hulu1,board,null,false);
        //葫芦娃血量为0了
        assertEquals(0,hulu1.getHp());
    }
}
