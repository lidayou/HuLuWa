package com.lgy.being;


import com.lgy.annotation.ClassAnnotation;
import com.lgy.gui.MyTextArea;
import com.lgy.playback.AttackBehave;
import com.lgy.playback.CallBahave;
import com.lgy.playback.Command;
import com.lgy.playback.MoveBehave;
import com.lgy.sample.Main;
import com.lgy.space.Board;
import com.lgy.space.Square;
import com.lgy.util.StyleImage;

@ClassAnnotation(descrip = "描述人类")
public class Human extends GoodCreature {

    public Human(int x, int y, StyleImage image,int hp,boolean alive,int battle,String name,int speed,int id) {
        super(x,y,image,hp,alive,battle,name,speed,id);
    }
    @Override
    public Object clone() {
        Human temp = null;
        temp = (Human) super.clone();	//浅复制
        return temp;
    }

    public boolean callMonster(Board board, MyTextArea textArea, boolean isAddCommand) {
        synchronized (board){
            if(board.canPutCreature(x,y+1)==true){
                PeaPod peaPod=new PeaPod(x,y+1, com.lgy.util.StyleImage.PEAPOD,50,true,0,"peapod",100,20);
                Square temp=board.getSquare(x,y+1);
                temp.setBeing(peaPod);
                board.setSquare(temp);
                if(textArea!=null){
                    textArea.appendTextArea("召唤豌豆荚进入战场\n");
                }
                if(isAddCommand==true){
                    long curTime = System.currentTimeMillis();
                    //准备记录信息，写入数组之中
                    Command command = new Command(this, new CallBahave((int) (curTime - Main.fightPlace.startTime), peaPod));
                    synchronized (Main.fightPlace.getCommandsAdminister().getCommands()) {
                        Main.fightPlace.getCommandsAdminister().addOne(command);
                    }
                }
                if(textArea!=null && isAddCommand==true){
                    Thread t=new Thread(peaPod);
                    t.start();
                }

                return true;
            }
        }
        return false;
    }
}