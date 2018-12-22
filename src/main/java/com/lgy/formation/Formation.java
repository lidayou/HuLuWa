package com.lgy.formation;

import com.lgy.annotation.ClassAnnotation;
import com.lgy.being.Creature;
import com.lgy.space.Board;
import com.lgy.space.Square;

import java.util.ArrayList;

@ClassAnnotation(descrip = "作为所有基础阵型的基类 同时作为一个抽象类 不能被实例化 所有阵型类通过一组参数确定下来")
public abstract class Formation {

    //管理阵型的数组信息
    protected ArrayList<Square> formation=new ArrayList<>();

    //实际上抽象类不能显示实例化
    public Formation() {
    }

    private ArrayList<Square> getSquare(){
        return formation;
    }
    
    /**
     * @Description: 根据当前formation信息将creatures放入board中
     * @Param: [board, creatures]
     * @return: void
     */
    public void pubFormationOnBoard(Board board,ArrayList<? extends Creature> creatures){
        assert(formation.size()<=creatures.size());
        int i=0;
        for(Square s:formation){
            if(board.isEmpty(s.getX(),s.getY())){
                //System.out.println("should here");
                s.setBeing(creatures.get(i));
                creatures.get(i).setX(s.getX());
                creatures.get(i).setY(s.getY());
                i++;
                board.setSquare(s);
            }
        }
    }
    /**
     * @Description: 用于检测两个阵型是否会发生碰撞的情况
     * @Param: [a]
     * @return: boolean
     */
    public boolean checkWhetherCrash(Formation a){
        //System.out.println(formation.size());
        //System.out.println(a.formation.size());
        for(Square temp1:a.getSquare()){
            for(Square temp2:getSquare()){
                if(temp1.getX()==temp2.getX()&&temp1.getY()==temp2.getY()){
                    //System.err.println("crash");
                    return true;
                }
            }
        }
        //System.err.println("not crash");
        return false;
    }
}
