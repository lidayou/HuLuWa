package com.lgy.administer;

import com.lgy.annotation.ClassAnnotation;
import com.lgy.playback.Command;

import java.util.ArrayList;

@ClassAnnotation(descrip = "负责管理存放回放记录的历史信息数组")
public class CommandsAdminister {
    ArrayList<Command> commands=new ArrayList<>();
    public void addOne(Command c){
        commands.add(c);
    }
    public ArrayList<Command> getCommands(){return commands;}
    public void clearAll(){commands.clear(); }
    public void setCommands(ArrayList<Command> commands){
        this.commands=commands;
    }
}
