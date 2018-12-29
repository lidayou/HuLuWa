package com.lgy.playback;

import com.lgy.annotation.ClassAnnotation;
import com.lgy.being.Creature;
import com.lgy.being.Human;
import com.lgy.being.PeaPod;
import com.lgy.sample.Main;
import com.lgy.space.Board;

import java.io.*;
import java.util.ArrayList;

@ClassAnnotation(descrip = "回放机 主要通过public static方法向外暴露 主要完成读取文件记录至一个记录数组中 以及将一个记录数组写入文件中")
public class PlayBacker implements Runnable{
    private ArrayList<Command> commands;
    private Board board;
    public PlayBacker(ArrayList<Command> commands,Board board){
        this.commands=commands;
        this.board=board;
    }
    /**
     * @Description: 通过一个while循环实现对记录的回放
     * @Param: []
     * @return: void
     */
    @Override
    public void run() {
        int cur=0;
        long placeBackStartTime=System.currentTimeMillis();
        long placeBackCurTime=0;
        Main.textArea.appendText("开始回放啦\n");
        while (cur<commands.size() && !Thread.interrupted()) {
            //System.out.println(cur);
            //挨个处理指令
            //System.out.println("here1"+commands.size());
            Command command = commands.get(cur);
            placeBackCurTime = System.currentTimeMillis();    //当前时间
            if (command.getBehave().getSleepTime() <= (placeBackCurTime - placeBackStartTime)) {
                //指令执行
                Creature temp = Main.fightPlace.getBoard().findCreature(command.getCreature());
                if(command.getBehave() instanceof BulletFireBehave){
                    BulletFireBehave fireBehave=(BulletFireBehave)command.getBehave();
                    Main.fightPlace.getBulletAdminister().addBullet(fireBehave.getBullet());
                    System.out.println("add bullet");
                    cur++;
                }
                else if (temp != null) {
                    if (command.getBehave() instanceof MoveBehave) {
                        MoveBehave moveBehave = (MoveBehave) command.getBehave();
                        temp.move(moveBehave.getDx(), moveBehave.getDy(),board,Main.textArea,false);
                    } else if (command.getBehave() instanceof AttackBehave) {
                        AttackBehave attackBehave = (AttackBehave) command.getBehave();
                        Creature enemy = Main.fightPlace.getBoard().findCreature(attackBehave.getEnemy());
                        if (enemy != null) {
                            temp.attack(enemy,board,Main.textArea,false);
                        } else {
                            System.err.println("attackbehave error");
                        }
                    }else if(command.getBehave() instanceof CallBahave){
                        CallBahave callBehave = (CallBahave) command.getBehave();
                        Creature peapot=callBehave.getCreature();
                        if(peapot instanceof PeaPod && temp instanceof Human){
                            ((Human) temp).callMonster(board,Main.textArea,false);
                        }
                        else{
                            System.err.println("callbehave error");
                        }
                    }
                    cur++;
                }
                else{
                    System.out.println(command.getCreature().getName());
                    System.out.println(cur);
                    if(command.getBehave() instanceof AttackBehave)
                        System.err.println("attack");
                    if(command.getBehave() instanceof MoveBehave)
                        System.err.println("move");
                    System.err.println("id not exists");
                }
                Main.fightPlace.paint();
            }
        }
    }

    /**
     * @Description: 将commands数组中的每一条信息都写入file中。同时先写两个阵营的阵型信息(存在index中)
     * @Param: [commands, index, file]
     * @return: void
     */
    public static void writeCommands(ArrayList<Command> commands,ArrayList<Integer> index,File file){
        try{
            FileOutputStream fileOut = new FileOutputStream(file);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(index.get(0));    //写入两个阵型信息
            out.writeObject(index.get(1));
            for(Command command:commands){
                out.writeObject(command);
            }
            System.out.println("------->write commands size:"+commands.size());
            int count=0;
            for(Command command:commands){
                if(command.getBehave() instanceof AttackBehave){
                    count++;
                }
            }
            System.err.println("write commands size:"+commands.size());
            out.close();
            fileOut.close();
        }catch(IOException i)
        {
            i.printStackTrace();
        }catch (Exception i){
            i.printStackTrace();
        }
    }
    /**
     * @Description: 先读入阵型信息 然后再循环读取文件中的每一条记录至commands数组中
     * @Param: [commands, index, file]
     * @return: void
     */
    public static void readCommands(ArrayList<Command> commands,ArrayList<Integer> index,File file){
        commands.clear();
        index.clear();
        try
        {
            FileInputStream fileIn = new FileInputStream(file);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            int index1=(Integer) in.readObject();
            int index2=(Integer) in.readObject();
            index.add(index1);
            index.add(index2);
            Command command=null;
            while((command=(Command)in.readObject())!=null){
                commands.add(command);
            }
            in.close();
            fileIn.close();
        }catch(IOException i)
        {
            i.printStackTrace();
            return;
        }catch(ClassNotFoundException c)
        {
            System.out.println("Command class not found");
            c.printStackTrace();
            return;
        }
    }

}
