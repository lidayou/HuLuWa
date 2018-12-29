package com.lgy;

import com.lgy.administer.BadManAdminister;
import com.lgy.administer.FormationAdminister;
import com.lgy.administer.GoodManAdminister;
import com.lgy.being.BadCreature;
import com.lgy.being.Creature;
import com.lgy.being.HuLuWa;
import com.lgy.being.LittleMonster;
import com.lgy.formation.Formation;
import com.lgy.formation.FormationFactory;
import com.lgy.gui.FightPlace;
import com.lgy.playback.AttackBehave;
import com.lgy.playback.Command;
import com.lgy.playback.MoveBehave;
import com.lgy.playback.PlayBacker;
import com.lgy.sample.Main;
import com.lgy.space.Board;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;

public class PlayBackTest {
    //对回放文件中每一条记录进行模拟 对于attack行为 不会出现攻击不在范围的情况
    @Test
    public void shouldnotCrash() throws Exception{
        GoodManAdminister goodManAdminister=new GoodManAdminister();
        BadManAdminister badManAdminister=new BadManAdminister();
        FormationAdminister formationAdminister=new FormationAdminister();
        ArrayList<Command> commands=new ArrayList<>();
        ArrayList<Integer> index=new ArrayList<>();
        File file=new File(System.getProperty("user.dir")+"/src/main/resources/data_6.dat");    //使用相对路径
        PlayBacker.readCommands(commands,index, file);
        int index1=index.get(0);
        int index2=index.get(1);
        Board board=new Board(5,9);

        Formation formation =null;
        formation=FormationFactory.create(index2,formationAdminister.getArg1BadZhengXing(index2) , formationAdminister.getArg2BadZhengXing(index2),-1);
        ArrayList<LittleMonster> monsters = badManAdminister.getMonsters();
        ArrayList<Creature> badones=new ArrayList<>();
        for(LittleMonster m:monsters)
            badones.add(m);
        badones.add(badManAdminister.getScorpion());
        badones.add((badManAdminister.getSnakeEssence()));
        formation.pubFormationOnBoard(board, badones);

        formation = FormationFactory.create(index1, formationAdminister.getArg1GoodZhengXing(index1),formationAdminister.getArg2GoodZhengXing(index1) ,1);
        ArrayList<HuLuWa> brothers = goodManAdminister.getHuLuWas();
        ArrayList<Creature> goodones=new ArrayList<>();
        for(HuLuWa h:brothers)
            goodones.add(h);
        goodones.add(goodManAdminister.getGrandpa());
        formation.pubFormationOnBoard(board, goodones);

        int cur=0;
        long placeBackStartTime=System.currentTimeMillis();
        long placeBackCurTime=0;
        System.out.println(commands.size());
        while (cur<commands.size() ) {
            //挨个处理指令
            Command command = commands.get(cur);
            placeBackCurTime = System.currentTimeMillis();    //当前时间
            if (command.getBehave().getSleepTime() <= (placeBackCurTime - placeBackStartTime)) {
                //指令执行
                Creature temp = board.findCreature(command.getCreature());
                if (temp != null) {
                    if (command.getBehave() instanceof MoveBehave) {
                        MoveBehave moveBehave = (MoveBehave) command.getBehave();
                        temp.move(moveBehave.getDx(), moveBehave.getDy(),board,null,false);
                    } else if (command.getBehave() instanceof AttackBehave) {
                        AttackBehave attackBehave = (AttackBehave) command.getBehave();
                        Creature enemy = board.findCreature(attackBehave.getEnemy());

                        if (enemy != null) {
                            boolean flag=true;
                            if(Math.abs(enemy.getX()-temp.getX())>=2)
                                flag=false;
                            if(Math.abs(enemy.getY()-temp.getY())>=2)
                                flag=false;
                            //System.out.println(temp.getName()+"("+temp.getX()+","+temp.getY()+")"+"攻击"+enemy.getName()+"("+enemy.getX()+","+enemy.getY()+")");

                            assertEquals(true,flag);
                            temp.attack(enemy,board,null,false);
                        } else {
                            //敌人不存在 显然也是error
                            assertTrue( false );
                        }
                    }
                    cur++;
                } else {
                    //没有找到棋盘对应的creature
                    System.out.println(commands.size());
                    System.out.println(cur);
                    System.out.println(command.getCreature().getName());
                    assertTrue( false );
                }
            }
        }

    }
}
