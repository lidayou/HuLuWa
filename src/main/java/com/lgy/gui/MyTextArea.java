package com.lgy.gui;

import com.lgy.annotation.ClassAnnotation;
import javafx.application.Platform;
import javafx.scene.control.TextArea;

@ClassAnnotation(descrip = "继承JavaFx中的组件 用于自定义appendText方法")
public class MyTextArea extends TextArea {
    public MyTextArea(){
        super();
    }
    /**
     * @Description: 解决原有appendText输出太快出现的问题
     * @Param: [s]
     * @return: void
     */
    public  void appendTextArea(final String s){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                appendText(s);
            }
        });
    }
}
