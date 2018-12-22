package com.lgy.being;


import com.lgy.annotation.ClassAnnotation;
import com.lgy.util.StyleImage;

@ClassAnnotation(descrip = "表示实体，作为being包中的总基类")
public class Being implements Cloneable,java.io.Serializable{
    protected String name;  //表示名称
    protected int x,y;    //表示坐标
    protected StyleImage image;        //图片
    public Being(int x,int y,StyleImage image,String name){
        this.x=x;
        this.y=y;
        this.image=image;
        this.name=name;
    }
    public StyleImage getStyleImage(){
        return image;
    }
    public void setX(int x){
        this.x=x;
    }
    public void setY(int y){
        this.y=y;
    }
    public int getX(){return x;}
    public int getY(){return y;}
    public String getName(){return name;}
    @Override
    public Object clone() {
        Being temp = null;
        try{
            temp = (Being)super.clone();	//浅复制
        }catch(CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return temp;
    }
}
