package com.lgy.being;

import com.lgy.annotation.ClassAnnotation;
import com.lgy.gui.MyTextArea;
import com.lgy.playback.AttackBehave;
import com.lgy.playback.Command;
import com.lgy.playback.MoveBehave;
import com.lgy.sample.Main;
import com.lgy.space.Board;
import com.lgy.space.Square;
import com.lgy.util.DirectionVector;
import com.lgy.util.StyleImage;


@ClassAnnotation(descrip = "表示一个生物体，扩充了作为being的属性")
public class Creature extends Being implements Runnable{
    //需要添加更多属性
    protected int hp; //生命值
    protected boolean alive;  //是否存活
    protected int battle; //攻击值
    protected int numDisappear=2; //在死亡状态下需要攻击多少次墓碑才会消失
    protected int fullHp;   //满血时的hp
    protected int speed;    //移动速度
    protected int id;       //每一个生物都有一个独特的id作为主属性标识
    public int getId(){return id;}
    public void setSleepTime(int time){speed=time;}
    public int getSleepTime(){return speed;}
    public Creature(int x, int y, StyleImage image,int hp,boolean alive,int battle,String name,int speed,int id) {
        super(x,y,image,name);
        this.hp=hp;
        this.alive=alive;
        this.battle=battle;
        this.fullHp=hp;
        this.speed=speed;
        this.id=id;
    }
    @Override
    public Object clone() {
        Creature temp = null;
        temp = (Creature) super.clone();	//浅复制
        return temp;
    }

    public int getHp(){return hp;}
    public int getFullHp(){return fullHp;}
    public int getBattle(){return battle;}
    public boolean isAlive(){return alive;}

    public void setHp(int hp){this.hp=hp;}
    public void setBattle(int battle){this.battle=battle;}
    public void setAlive(boolean alive){this.alive=alive;}

    /**
     * @Description: 生物根据相对位移进行移动，会对位移合法性进行判断，同时讲结果写入textarea中。注:设计到对共享变量board的加锁
     * @Param: [dx, dy, board, textArea]
     * @return: void
     */
    public void move(int dx, int dy, Board board, MyTextArea textArea,boolean isAddCommand) {
        synchronized(board) {
            synchronized (this) {
                if (isAlive() == false)
                    return;
            }
            if(board.canPutCreature(x+dx,y+dy)==false)
                return;
            else {
                //-------------->
                if(textArea!=null && isAddCommand==true) {
                    long curTime = System.currentTimeMillis();
                    //准备记录信息，写入数组之中
                    Command command = new Command(this, new MoveBehave((int) (curTime - Main.fightPlace.startTime), dx, dy));
                    synchronized (Main.fightPlace.getCommandsAdminister().getCommands()) { //对共享变量commands加锁
                        Main.fightPlace.getCommandsAdminister().addOne(command);
                    }
                }
                //<--------------
                if(textArea!=null )
                    textArea.appendTextArea(getName()+"移动到("+(x+dx)+","+(y+dy)+")\n");
                Being being=board.getSquare(x,y).getBeing();
                board.getSquare(x+dx,y+dy).setBeing(being);
                board.getSquare(x,y).setBeing(null);
                this.setX(x+dx);
                this.setY(y+dy);
            }
        }
    }

    /**
     * @Description: 当前角色攻击角色c，也会对合法性进行判断。只能在九宫格范围内攻击 攻击设计到墓碑的处理 同样需要采用同步机制
     * @Param: [c, board, textArea,isAddCommand] isAddCommand表示是否需要将记录放入数组中 在回放情况下是不需要的
     * @return: void
     */
    public void attack(Creature c,Board board,MyTextArea textArea,boolean isAddCommand){
        synchronized(board){
            synchronized (this) {
                if (isAlive() == false)
                    return;
            }
            synchronized (c) {
                if (Math.abs(c.getX() - getX()) > 1)
                    return;
                if (Math.abs(c.getY() - getY()) > 1)
                    return;
                if (c.isAlive() == false) { //敌方已经死亡 只是一个墓碑
                    if (c.numDisappear > 0) {
                        //--------->
                        if (textArea != null && isAddCommand==true) {
                            long curTime = System.currentTimeMillis();
                            //准备记录信息，写入数组之中
                            Command command = new Command(this, new AttackBehave((int) (curTime - Main.fightPlace.startTime), c));
                            synchronized (Main.fightPlace.getCommandsAdminister().getCommands()) {
                                Main.fightPlace.getCommandsAdminister().addOne(command);
                            }
                        }
                        //<---------
                        c.numDisappear--;
                        if (textArea != null)
                            textArea.appendTextArea(this.getName() + "攻击" + c.getName() + "的墓碑\n");
                        if (c.numDisappear == 0) {
                            if (textArea != null)
                                textArea.appendTextArea(c.getName() + "的墓碑被摧毁\n");
                            board.setSquare(new Square(c.getX(), c.getY(), null));
                        }
                    }
                    return;
                }
                int result = c.getHp() - this.getBattle();
                //--------->
                if (textArea != null && isAddCommand==true) {
                    long curTime = System.currentTimeMillis();
                    //准备记录信息，写入数组之中
                    Command command = new Command(this, new AttackBehave((int) (curTime - Main.fightPlace.startTime), c));
                    synchronized (Main.fightPlace.getCommandsAdminister().getCommands()) {
                        Main.fightPlace.getCommandsAdminister().addOne(command);
                    }
                }
                //<---------
                if (result <= 0) {
                    if (textArea != null)
                        textArea.appendTextArea(this.getName() + "攻击" + c.getName() + ",造成其" + c.getHp() + "点伤害，死亡\n");
                    c.setAlive(false);
                    c.setHp(0);
                } else {
                    if (textArea != null)
                        textArea.appendTextArea(this.getName() + "攻击" + c.getName() + ",造成其" + this.getBattle() + "点伤害\n");
                    c.setHp(result);
                }
            }
        }
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted() ) {  //外界可以对线程进行强制打断 比如比赛结束时
            if(this.alive==false)       //如果这个生物死了 这个线程也就不run了
                break;
            //移动的速度决定了睡眠时间 从而反应了生物单步决策需要的时间
            try {
                Thread.sleep(speed);
            }catch (InterruptedException e) {
                System.err.println("interrupt");    //从此跳出while循环
                //抛出InterruptedException后中断标志被清除，标准做法是再次调用interrupt恢复中断
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                e.printStackTrace();
            }
            //随机生成一个方向向量进行行走
            DirectionVector d= Board.directions.get(Main.random.nextInt(Board.directions.size()));
            //如果当前其周围有一个对手则不进行移动，进行攻击
            Creature enemy=Main.fightPlace.getBoard().getEnemy(this);
            if(enemy!=null){
                //把增加记录的动作放入attack函数中进行
//                long curTime=System.currentTimeMillis();
//                //准备记录信息，写入数组之中
//                Command command=new Command(this,new AttackBehave((int)(curTime-Main.fightPlace.startTime),enemy));
//                synchronized (Main.fightPlace.commands) {
//                    Main.fightPlace.addCommand(command);
//                    System.out.println(Main.fightPlace.commands.size());
//                }
                this.attack(enemy,Main.fightPlace.getBoard(),Main.textArea,true);
                Main.fightPlace.paint();
            }
            else{
                //把增加记录的动作放入move函数中进行
//                long curTime=System.currentTimeMillis();
//                //准备记录信息，写入数组之中
//                Command command=new Command(this,new MoveBehave((int)(curTime-Main.fightPlace.startTime),d.getX(),d.getY()));
//                synchronized (Main.fightPlace.commands){
//                    Main.fightPlace.addCommand(command);
//                    System.out.println(Main.fightPlace.commands.size());
//                }
                this.move(d.getX(),d.getY(),Main.fightPlace.getBoard(),Main.textArea,true);
                Main.fightPlace.paint();
            }
        }

    }
}
