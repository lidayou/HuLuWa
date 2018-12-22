package com.lgy;

import com.lgy.being.HuLuWa;
import com.lgy.being.Human;
import com.lgy.space.Board;
import com.lgy.util.StyleImage;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;


public class CreatureMoveTest {

    //检查中间一个生物体 被周围四个生物体包围 不会进行移动
    @Test
    public void shouldnotMove() throws Exception{
        Board board=new Board(5,9);
        HuLuWa hulu1=(new HuLuWa(-1,-1, StyleImage.HULUWAONE,1,50,true,10,"红娃",2400,1));
        HuLuWa hulu2=(new HuLuWa(-1,-1,StyleImage.HULUWATWO,2,50,true,10,"橙娃",2400,2));
        HuLuWa hulu3=(new HuLuWa(-1,-1,StyleImage.HULUWATHREE,3,50,true,10,"黄娃",2400,3));
        HuLuWa hulu4=(new HuLuWa(-1,-1,StyleImage.HULUWAFOUR,4,50,true,10,"绿娃",2400,4));
        Human grandpa=new Human(-1,1,StyleImage.GRANDPA,50,true,20,"爷爷",3700,8);
        board.putCreature(hulu1,0,1);
        board.putCreature(hulu2,2,1);
        board.putCreature(hulu3,1,0);
        board.putCreature(hulu4,1,2);
        board.putCreature(grandpa,1,1);
        grandpa.move(1,0,board,null,false);
        //爷爷应该不产生移动
        assertEquals(1,grandpa.getX());
        assertEquals(1,grandpa.getY());
    }
}
