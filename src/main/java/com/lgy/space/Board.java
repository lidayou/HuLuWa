package com.lgy.space;

import com.lgy.annotation.ClassAnnotation;
import com.lgy.being.BadCreature;
import com.lgy.being.Creature;
import com.lgy.being.GoodCreature;
import com.lgy.util.DirectionVector;
import com.lgy.util.SquareType;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static com.lgy.util.SquareType.NOTHING;

@ClassAnnotation(descrip = "描述二维的战斗数组信息")
public class Board{
    public Lock lock = new ReentrantLock();
    //存储上下左右方向
    public static ArrayList<DirectionVector> directions;
    static{
        directions=new ArrayList<>();
        directions.add(new DirectionVector(-1,0));  //上
        directions.add(new DirectionVector(1,0));   //下
        directions.add(new DirectionVector(0,-1));  //左
        directions.add(new DirectionVector(0,1));   //右
    }

    private Square[][] board;
    //private SquareType[][] typeboard;
    private int row;
    private int column;
    public int getRow(){return row;}
    public int getColumn(){return column;}

    public boolean isOutOfBound(int x,int y){
        if(x<0||x>=row)
            return true;
        if(y<0||y>=column)
            return true;
        return false;
    }
    public Board(int x,int y){
        row=x;
        column=y;
        board=new Square[row][column];
        for(int i=0;i<row;i++){
            for(int j=0;j<column;j++){
                board[i][j]=new Square(i,j,null);
            }
        }
//        typeboard=new SquareType[row][column];
//        for(int i=0;i<row;i++){
//            for(int j=0;j<column;j++){
//                typeboard[i][j]=NOTHING;
//            }
//        }
    }

//    public SquareType getSquareType(int x,int y){
//        try{
//            return typeboard[x][y];
//        }catch (Exception e){
//            e.printStackTrace();
//            return null;
//        }
//    }

//    public void setSquareType(int x,int y,SquareType s){
//        try{
//            typeboard[x][y]=s;
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }

    //判断某坐标位置是否有being
    public boolean isEmpty(int x, int y){
        try{
            if(board[x][y].getBeing()==null){
                return true;
            }
            else{
                return false;
            }
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public void setSquare(Square s){
        board[s.getX()][s.getY()]=s;
    }
    public Square getSquare(int x,int y){
        try{
            return board[x][y];
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    public boolean canPutCreature(int x,int y){
        if(!(x>=0&&x<row && y>=0&&y<column)){
            return false;
        }
        if(board[x][y].getBeing()==null)
            return true;
        else
            return false;
    }
    /**
     * @Description: 根据当前生物cur 在棋盘中以它坐标为中心 周围九宫格中进行搜索寻找敌人 注意这里敌人也可能是一块墓碑 这时也可以攻击
     * @Param: [cur]
     * @return: com.lgy.being.Creature
     */
    public Creature getEnemy(Creature cur){
        try{
            if(cur.isAlive()==false)
                return null;
            int x=cur.getX();
            int y=cur.getY();
            //判断周围8个点
            for(int i=x-1;i<=x+1;i++){
                for(int j=y-1;j<=y+1;j++){
                    if(i==x&&j==y)
                        continue;
                    if(i>=0&&i<row&&j>=0&&j<column){
                        Square temp= getSquare(i,j);
                        if(temp.getBeing()!=null && temp.getBeing() instanceof Creature){
                            Creature enemy=(Creature) temp.getBeing();
                            if(enemy.isAlive()==true){
                                if((cur instanceof GoodCreature && enemy instanceof BadCreature)||(cur instanceof BadCreature && enemy instanceof GoodCreature)){
                                    return enemy;
                                }
                            }
                            else{   //表示已经死亡了 可以破坏敌方的墓碑
                                if((cur instanceof GoodCreature && enemy instanceof BadCreature)||(cur instanceof BadCreature && enemy instanceof GoodCreature)){
                                    return enemy;
                                }
                            }
                        }
                    }
                }
            }
            return null;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @Description: 用于在坐标(x,y)上放置生物
     * @Param: [c, x, y]
     * @return: void
     */
    public void putCreature(Creature c,int x,int y){
        if(!(x>=0&&x<row&&y>=0&&y<column))
            return;
        if(board[x][y].getBeing()!=null)
            return;
        board[x][y].setBeing(c);
        c.setX(x);
        c.setY(y);
    }
    /**
     * @Description: 对棋盘重置
     * @Param: []
     * @return: void
     */
    public void clear(){
        for(int i=0;i<row;i++)
            for(int j=0;j<column;j++){
                board[i][j].setBeing(null);
            }
    }
    /**
     * @Description: 主要供回放时使用 因为反序列化后得到一个个对象 然后每一个对象去找棋盘中与他匹配的(根据id)
     * @Param: [temp]
     * @return: com.lgy.being.Creature
     * @See: playback
     */
    public Creature findCreature(Creature temp){
        for(int i=0;i<row;i++){
            for(int j=0;j<column;j++){
                if(board[i][j].getBeing()!=null && board[i][j].getBeing() instanceof  Creature){
                    Creature cur=(Creature)(board[i][j].getBeing());
                    if(cur.getId()==temp.getId()){
                        return cur;
                    }
                }
            }
        }
        return null;
    }
}

