package com.lgy.gui;

import com.lgy.administer.*;
import com.lgy.annotation.ClassAnnotation;
import com.lgy.being.*;
import com.lgy.formation.Formation;
import com.lgy.formation.FormationFactory;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import com.lgy.playback.Command;
import com.lgy.playback.PlayBacker;
import com.lgy.space.Board;
import com.lgy.util.GameStatus;

import java.io.File;
import java.util.ArrayList;

import static com.lgy.util.GameStatus.*;
import static com.lgy.util.GameStatus.GAMEOVER;

@ClassAnnotation(descrip = "主要描述一块画板 作为战场的绘制 继承自javafx中的Canvas")
public class FightPlace  extends Canvas {
    private GameStatus StausType=READY;   //记录游戏当前状态 初始为READY
    public GameStatus getGameStatus(){return StausType;}
    public void setGameStatus(GameStatus g){ StausType=g; }
    public long startTime;      //记录游戏开始时间
    private boolean isSavePlay=false; //是否保存记录以备回放
    private File playbackFile=null;        //记录要保存记录的文件

    private static final int BaseX=210; //记录背景图片中棋盘的初始位置
    private static final int BaseY=64;
    private static final int BlockXSize=68;//记录棋盘每一个方格大致的范围
    private static final int BlockYSize=85;

    private static final int row=5;         //行列数
    private static final int column=9;

    private GraphicsContext g =getGraphicsContext2D();




    //加载相应的图片信息
    private static Image imageBackground=new Image("/background.jpg",1000,600,true,false);
    private static Image imageGrandpa=new Image("/grandpa.png",83,83,true,true);
    private static Image imageHulu1=new Image("/hulu1.png",83,83,true,true);
    private static Image imageHulu2=new Image("/hulu2.png",83,83,true,true);
    private static Image imageHulu3=new Image("/hulu3.png",83,83,true,true);
    private static Image imageHulu4=new Image("/hulu4.png",83,83,true,true);
    private static Image imageHulu5=new Image("/hulu5.png",83,83,true,true);
    private static Image imageHulu6=new Image("/hulu6.png",83,83,true,true);
    private static Image imageHulu7=new Image("/hulu7.png",83,83,true,true);
    private static Image imageMonster=new Image("/monster.png",83,83,true,true);
    private static Image imageScorp=new Image("/scorp.png",83,83,true,true);
    private static Image imageSnake=new Image("/snake.png",83,83,true,true);
    private static Image imageGooddie=new Image("/gooddie.png",83,83,true,true);
    private static Image imageBaddie=new Image("/baddie2.png",83,83,true,true);
    private static Image imageGameOver=new Image("/gameover2.png",400,400,true,true);
    private static Image imageFire=new Image("/fennu.png",30,30,true,true);
    private static Image imageBullet=new Image("/fire1.png",30,30,true,true);

    //注:board中的数组表示坐标系与画图中的坐标系是相反的
    private Board board=new Board(row,column);
    private GoodManAdminister goodManAdminister=new GoodManAdminister();
    private BadManAdminister badManAdminister=new BadManAdminister();
    private FormationFactory formationFactory=new FormationFactory();
    private ThreadAdminister threadAdminister=new ThreadAdminister();
    private CommandsAdminister commandsAdminister=new CommandsAdminister();
    private FormationAdminister formationAdminister=new FormationAdminister();


    public CommandsAdminister getCommandsAdminister(){return commandsAdminister;}
    public FormationAdminister getFormationAdminister(){return formationAdminister;}

    public void setIsSavePlay(boolean s){
        isSavePlay=s;
    }
    public void setPlayBackFile(File f){
        playbackFile=f;
    }

    public FightPlace(double width, double height){
        super(width,height);
        getReady();
        paint();
    }

    public void getReady(){
        commandsAdminister.clearAll();
        board.clear();
        goodManAdminister.initial();
        badManAdminister.initial();
        threadAdminister.clearAll();

        int index1=formationAdminister.getBadZhenXingIndex();
        int index2=formationAdminister.getGoodZhenXingIndex();

        Formation formation =null;
        formation=formationFactory.create(formationAdminister.getBadZhenXingIndex(), formationAdminister.getArg1BadZhengXing(index1), formationAdminister.getArg2BadZhengXing(index1),-1);
        ArrayList<LittleMonster> monsters = badManAdminister.getMonsters();
        ArrayList<Creature> badones=new ArrayList<>();
        for(LittleMonster m:monsters)
            badones.add(m);
        badones.add(badManAdminister.getScorpion());
        badones.add((badManAdminister.getSnakeEssence()));
        formation.pubFormationOnBoard(board, badones);
        formation = formationFactory.create(formationAdminister.getGoodZhenXingIndex(), formationAdminister.getArg1GoodZhengXing(index2),formationAdminister.getArg2GoodZhengXing(index2) ,1);
        ArrayList<HuLuWa> brothers = goodManAdminister.getHuLuWas();
        ArrayList<Creature> goodones=new ArrayList<>();
        for(HuLuWa h:brothers)
            goodones.add(h);
        goodones.add(goodManAdminister.getGrandpa());
        formation.pubFormationOnBoard(board, goodones);
        for(Creature c:goodones)
            threadAdminister.addOne(new Thread(c));
        for(Creature c:badones)
            threadAdminister.addOne(new Thread(c));
        paint();
    }
    
    /**
     * @Description: 让全体角色抖动起来
     * @Param: []
     * @return: void
     */
    public void allMoveUp(){
        startTime=System.currentTimeMillis();
        threadAdminister.startAll();
    }
    
    /**
     * @Description: 判断游戏结束
     * @Param: []
     * @return: boolean
     */
    public boolean isOver(){
        if(goodManAdminister.isAllDie()||badManAdminister.isAllDie()){
            return true;
        }
        else{
            return false;
        }
    }


    /**
     * @Description: 将整个场景画出来 实现内部数组到ui的映射
     * @Param: []
     * @return: void
     */
    private void mypaint(){
        g.clearRect(0,0,getWidth(),getHeight());
        g.drawImage(imageBackground,0,0);
        for(int i=0;i<row;i++){
            for(int j=0;j<column;j++){
                Being temp=board.getSquare(i,j).getBeing();
                if(temp!=null){
                    Image var=null;
                    switch (temp.getStyleImage()){
                        case HULUWAONE:var=imageHulu1;break;
                        case HULUWATWO:var=imageHulu2;break;
                        case HULUWATHREE:var=imageHulu3;break;
                        case HULUWAFOUR:var=imageHulu4;break;
                        case HULUWAFIVE:var=imageHulu5;break;
                        case HULUWASIX:var=imageHulu6;break;
                        case HULUWASEVEN:var=imageHulu7;break;
                        case GRANDPA:var=imageGrandpa;break;
                        case LITTLEMONSTER:var=imageMonster;break;
                        case SCORP:var=imageScorp;break;
                        case SNAKE:var=imageSnake;break;

                        default:assert(1==0);break;
                    }

                    if(temp instanceof BadCreature){
                        BadCreature badtemp=(BadCreature)temp;
                        if(badtemp.isAlive()==false){
                            var=imageBaddie;
                        }
                    }
                    if(temp instanceof GoodCreature){
                        GoodCreature goodtemp=(GoodCreature)temp;
                        if(goodtemp.isAlive()==false){
                            var=imageGooddie;
                        }
                    }
                    //画怒气
                    double opacity = 1;
                    if(var!=imageBaddie && var!=imageGooddie){
                        Creature creaturetemp=(Creature)temp;
                        opacity=(double)creaturetemp.getHp()/creaturetemp.getFullHp();
                        if(opacity<=0.3)
                            opacity=0.3;
                    }
                    g.setGlobalAlpha(opacity);
                    g.drawImage(var, BaseX+j*BlockXSize, BaseY+i*BlockYSize);
                    g.setGlobalAlpha(1);
                    if(opacity<0.5){
                        g.drawImage(imageFire,BaseX+j*BlockXSize+3*BlockXSize/5,BaseY+i*BlockYSize+BlockYSize/7);
                    }

                    //画血条
                    if(temp instanceof Creature){
                        Creature creature=(Creature)temp;
                        if(creature.isAlive()==true){
                            g.setFill(Color.RED);
                            double rate=creature.getHp()/((double)creature.getFullHp());
                            g.fillRect(BaseX+j*BlockXSize+BlockXSize/2,BaseY+i*BlockYSize,(int)(rate*(BlockXSize/3)),8);
                            g.setFill(Color.BLACK);
                            g.strokeRect(BaseX+j*BlockXSize+BlockXSize/2,BaseY+i*BlockYSize,BlockXSize/3,8);
                        }
                    }
                }
            }
        }
        if(goodManAdminister.isAllDie()||badManAdminister.isAllDie()) {
            synchronized (StausType) {  //增加同步控制
                if (StausType == DOINGRANDOM) {
                    StausType = GAMEOVER;   //调整次序
                    gameSuspend(); //刻意中断线程
                    //在去中断各个生物线程时 他们可能还在往commands里写东西 故我们需要等一会 让可能正在写的写完
                    try {           //等待中断
                        Thread.sleep(200);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    saveFile(playbackFile);
                    try {
                        Thread.sleep(100);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    g.drawImage(imageGameOver, BaseX + 3 * BlockXSize, BaseY + BlockYSize / 4);
                    //等待gameover的绘图
                    try {
                        Thread.sleep(100);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    StausType = GAMEOVER;
                } else if (StausType == DOINGPLAYBACK) {
                    try {
                        Thread.sleep(100);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    g.drawImage(imageGameOver, BaseX + 3 * BlockXSize, BaseY + BlockYSize / 4);
                    StausType = GAMEOVER;
                } else if (StausType == GAMEOVER) {
                    try {
                        Thread.sleep(100);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    g.drawImage(imageGameOver, BaseX + 3 * BlockXSize, BaseY + BlockYSize / 4);
                }
            }
        }
    }
    public void paint(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                mypaint();
            }
        });
    }
    public Board getBoard(){
        return board;
    }
    public void gameSuspend(){  //游戏停止 刻意去中断所有线程
        threadAdminister.interruptAll();
    }
    
    /**
     * @Description: 将当前存储的阵型信息以及commands记录写入进文件中
     * @Param: [file]
     * @return: void
     */
    public void saveFile(File file){
        //把当前所有数据写入文件
        if(isSavePlay==true && file!=null){
            ArrayList<Integer> index=new ArrayList<>();
            index.add(formationAdminister.getGoodZhenXingIndex());
            index.add(formationAdminister.getBadZhenXingIndex());
            //PlayBacker.writeCommands(commands,index,file);
            PlayBacker.writeCommands(commandsAdminister.getCommands(),index,file);
        }
        isSavePlay=false;
        playbackFile=null;
    }


    //下面被废弃
    @Deprecated
    public void gameContinue(){ //游戏继续
        threadAdminister.clearAll();
        for (LittleMonster a : badManAdminister.getMonsters()) {
            threadAdminister.addOne(new Thread(a));
        }
        for (HuLuWa a : goodManAdminister.getHuLuWas()) {
            threadAdminister.addOne(new Thread(a));
        }
        threadAdminister.addOne(new Thread(goodManAdminister.getGrandpa()));
        threadAdminister.addOne(new Thread(badManAdminister.getScorpion()));
        threadAdminister.addOne(new Thread(badManAdminister.getSnakeEssence()));
        threadAdminister.startAll();
    }


    //回放功能
    public void playback(File file){
        ArrayList<Integer> index=new ArrayList<>();
        ArrayList<Command> commandsTemp=new ArrayList<>();
        PlayBacker.readCommands(commandsTemp,index,file);
        formationAdminister.setGoodZhenXingIndex(index.get(0));
        formationAdminister.setBadZhenXingIndex(index.get(1));
        getReady();
        commandsAdminister.setCommands(commandsTemp);
        //commands=commandsTemp;
        System.err.println("playback commands size:"+commandsTemp.size());
        PlayBacker p=new PlayBacker(commandsAdminister.getCommands(),board);
        Thread t=new Thread(p);
        t.start();
    }

    //两种视图思路 下面的实现被废弃
    @Deprecated
    class PaintAll extends  Thread{
        @Override
        public void run() {
            while(isOver()==false){

                //当前的一次绘制
                mypaint();
                try{
                    Thread.sleep(800);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            mypaint();
            //等待最后的绘制完成
            try{
                Thread.sleep(1000);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
