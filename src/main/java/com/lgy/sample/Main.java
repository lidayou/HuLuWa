package com.lgy.sample;

import com.lgy.annotation.ClassAnnotation;
import com.lgy.gui.FightPlace;
import com.lgy.gui.MyTextArea;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.Random;

import static com.lgy.util.GameStatus.*;


@ClassAnnotation(descrip = "Main所在 完成gui布局的加载启动")
public class Main extends Application {


    //左边战场的大小
    private static int FieldWidth=1000;
    private static int FieldHeight=521;
    private Stage stage;

    //右边提示框的大小
    private int BoxWidth=250;
    private int BoxHeight=521;

    public static Random random=new Random();   //初始化随机种子

    //右侧提示框
    public static MyTextArea textArea;
    static{
        if(textArea==null)
            textArea=new MyTextArea();
        textArea.setEditable(false);    //提示框不可被编辑
    }
    //左侧战斗地图
    public static FightPlace fightPlace=new FightPlace(FieldWidth,FieldHeight);

    public void clearTextArea(){
        //完成为textArea的清空
        //打印必要提示信息
        textArea.setText("");
        textArea.appendText("按空格键运行\n");
        textArea.appendText("按L键选择文件回放\n");
        textArea.appendText("按R键进行重置(每次运行完都需要重置)\n");
    }

    /**
     * @Description: 在当前状态为READY和GAMEOVER时可以切换阵型
     * @Param: []
     * @return: void
     */
    private void clickChangeZhenXing(){
        if(fightPlace.getGameStatus()==GAMEOVER){
            fightPlace.setGameStatus(READY);
            fightPlace.getReady();
            clearTextArea();
        }
        else if(fightPlace.getGameStatus()==READY){
            fightPlace.getReady();
            clearTextArea();
        }
    }
    @Override
    public void start(Stage primaryStage) throws Exception{
        BorderPane border = new BorderPane();
        stage=primaryStage;
        border.setLeft(fightPlace);
        border.setRight(textArea);

        //添加菜单栏
        MenuBar menuBar = new MenuBar();
        menuBar.prefWidthProperty().bind(primaryStage.widthProperty());
        border.setTop(menuBar);
        Menu goodMenu = new Menu("葫芦娃阵型");

        Menu badMenu = new Menu("妖怪阵型");

        addItems(goodMenu,badMenu);

        menuBar.getMenus().addAll(goodMenu,badMenu);

        fightPlace.setFocusTraversable(true);

        //After you click your canvas, the canvas will request the focus and recognice key events.
        fightPlace.addEventFilter(MouseEvent.ANY, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                fightPlace.requestFocus();
            }
        });
        clearTextArea();

        fightPlace.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                if(e.getCode()==KeyCode.SPACE){  //按下空格键
                    //从READY状态进入DOINGRANDOM状态
                    if(fightPlace.getGameStatus()==READY){
//                        //首先显示文件选择框
//                        FileChooser fileChooser = new FileChooser();
//                        fileChooser.getExtensionFilters().addAll(
//                                new FileChooser.ExtensionFilter("Binary", "*.dat")
//                        );
//                        fileChooser.setTitle("保存战斗记录");
//                        File file = fileChooser.showSaveDialog(stage);
//                        if (file != null) {
//                            fightPlace.setIsSavePlay(true);
//                            fightPlace.setPlayBackFile(file);
//                        }
//                        else{
//                            fightPlace.setIsSavePlay(false);
//                            fightPlace.setPlayBackFile(null);
//                        }
                        fightPlace.setGameStatus(DOINGRANDOM);
                        fightPlace.allMoveUp();
                    }
                    else if(fightPlace.getGameStatus()==GAMEOVER){
                        //首先显示文件选择框
                        FileChooser fileChooser = new FileChooser();
                        fileChooser.getExtensionFilters().addAll(
                                new FileChooser.ExtensionFilter("Binary", "*.dat")
                        );
                        fileChooser.setTitle("保存战斗记录");
                        File file = fileChooser.showSaveDialog(stage);
                        if (file != null) {
                            fightPlace.setIsSavePlay(true);
                            fightPlace.setPlayBackFile(file);
                            fightPlace.saveFile(file);
                        }
                        else{
                            fightPlace.setIsSavePlay(false);
                            fightPlace.setPlayBackFile(null);
                        }
                    }
                }
                else if(e.getCode()==KeyCode.R){  //复位 从GameOver状态变为READY状态
                    if(fightPlace.getGameStatus()==GAMEOVER){
                        fightPlace.setGameStatus(READY);
                        fightPlace.getReady();
                        clearTextArea();
                    }
                }
                else if(e.getCode()==KeyCode.L){ //回放 从READY状态变为 DOINGPLAYBACK
                    if(fightPlace.getGameStatus()==READY){
                        FileChooser fileChooser = new FileChooser();
                        fileChooser.getExtensionFilters().addAll(
                                new FileChooser.ExtensionFilter("Binary", "*.dat")

                        );
                        fileChooser.setTitle("选择战斗记录进行回放");
                        File file = fileChooser.showOpenDialog(stage);
                        if (file != null) {
                            fightPlace.setGameStatus(DOINGPLAYBACK);
                            fightPlace.playback(file);
                        }
                    }
                }
            }
        });


        primaryStage.setScene(new Scene(border,FieldWidth+BoxWidth,FieldHeight+25));
        primaryStage.setTitle("HuLuWa Game");

        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }

    private void addItems(Menu goodMenu,Menu badMenu){
        MenuItem gzhenxing1 = new MenuItem("鹤翼");
        gzhenxing1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(fightPlace.getGameStatus()==READY || fightPlace.getGameStatus()==GAMEOVER){
                    fightPlace.getFormationAdminister().setGoodZhenXingIndex(0);
                    clickChangeZhenXing();
                }
            }
        });
        MenuItem gzhenxing2 = new MenuItem("雁行");
        gzhenxing2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(fightPlace.getGameStatus()==READY || fightPlace.getGameStatus()==GAMEOVER) {
                    fightPlace.getFormationAdminister().setGoodZhenXingIndex(1);
                    clickChangeZhenXing();
                }
            }
        });
        MenuItem gzhenxing3 = new MenuItem("衡輀");
        gzhenxing3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(fightPlace.getGameStatus()==READY || fightPlace.getGameStatus()==GAMEOVER) {
                    fightPlace.getFormationAdminister().setGoodZhenXingIndex(2);
                    clickChangeZhenXing();
                }
            }
        });
        MenuItem gzhenxing4 = new MenuItem("长蛇");
        gzhenxing4.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(fightPlace.getGameStatus()==READY || fightPlace.getGameStatus()==GAMEOVER) {
                    fightPlace.getFormationAdminister().setGoodZhenXingIndex(3);
                    clickChangeZhenXing();
                }
            }
        });
        MenuItem gzhenxing5 = new MenuItem("鱼鳞");
        gzhenxing5.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(fightPlace.getGameStatus()==READY || fightPlace.getGameStatus()==GAMEOVER) {
                    fightPlace.getFormationAdminister().setGoodZhenXingIndex(4);
                    clickChangeZhenXing();
                }
            }
        });
        MenuItem gzhenxing6 = new MenuItem("方门");
        gzhenxing6.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(fightPlace.getGameStatus()==READY || fightPlace.getGameStatus()==GAMEOVER) {
                    fightPlace.getFormationAdminister().setGoodZhenXingIndex(5);
                    clickChangeZhenXing();
                }
            }
        });
        MenuItem gzhenxing7 = new MenuItem("偃月");
        gzhenxing7.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(fightPlace.getGameStatus()==READY || fightPlace.getGameStatus()==GAMEOVER) {
                    fightPlace.getFormationAdminister().setGoodZhenXingIndex(6);
                    clickChangeZhenXing();
                }
            }
        });
        MenuItem gzhenxing8 = new MenuItem("锋矢");
        gzhenxing8.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(fightPlace.getGameStatus()==READY || fightPlace.getGameStatus()==GAMEOVER) {
                    fightPlace.getFormationAdminister().setGoodZhenXingIndex(7);
                    clickChangeZhenXing();
                }
            }
        });
        goodMenu.getItems().addAll(gzhenxing1,gzhenxing2,gzhenxing3,gzhenxing4,gzhenxing5,gzhenxing6,gzhenxing7,gzhenxing8);

        MenuItem bzhenxing1 = new MenuItem("鹤翼");
        bzhenxing1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(fightPlace.getGameStatus()==READY || fightPlace.getGameStatus()==GAMEOVER) {
                    fightPlace.getFormationAdminister().setBadZhenXingIndex(0);
                    clickChangeZhenXing();
                }
            }
        });
        MenuItem bzhenxing2 = new MenuItem("雁行");
        bzhenxing2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(fightPlace.getGameStatus()==READY || fightPlace.getGameStatus()==GAMEOVER) {
                    fightPlace.getFormationAdminister().setBadZhenXingIndex(1);
                    clickChangeZhenXing();
                }
            }
        });
        MenuItem bzhenxing3 = new MenuItem("衡輀");
        bzhenxing3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(fightPlace.getGameStatus()==READY || fightPlace.getGameStatus()==GAMEOVER) {
                    fightPlace.getFormationAdminister().setBadZhenXingIndex(2);
                    clickChangeZhenXing();
                }
            }
        });
        MenuItem bzhenxing4 = new MenuItem("长蛇");
        bzhenxing4.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(fightPlace.getGameStatus()==READY || fightPlace.getGameStatus()==GAMEOVER) {
                    fightPlace.getFormationAdminister().setBadZhenXingIndex(3);
                    clickChangeZhenXing();
                }
            }
        });
        MenuItem bzhenxing5 = new MenuItem("鱼鳞");
        bzhenxing5.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(fightPlace.getGameStatus()==READY || fightPlace.getGameStatus()==GAMEOVER) {
                    fightPlace.getFormationAdminister().setBadZhenXingIndex(4);
                    clickChangeZhenXing();
                }
            }
        });
        MenuItem bzhenxing6 = new MenuItem("方门");
        bzhenxing6.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(fightPlace.getGameStatus()==READY || fightPlace.getGameStatus()==GAMEOVER) {
                    fightPlace.getFormationAdminister().setBadZhenXingIndex(5);
                    clickChangeZhenXing();
                }
            }
        });
        MenuItem bzhenxing7 = new MenuItem("偃月");
        bzhenxing7.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(fightPlace.getGameStatus()==READY || fightPlace.getGameStatus()==GAMEOVER) {
                    fightPlace.getFormationAdminister().setBadZhenXingIndex(6);
                    clickChangeZhenXing();
                }
            }
        });
        MenuItem bzhenxing8 = new MenuItem("锋矢");
        bzhenxing8.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(fightPlace.getGameStatus()==READY || fightPlace.getGameStatus()==GAMEOVER) {
                    fightPlace.getFormationAdminister().setBadZhenXingIndex(7);
                    clickChangeZhenXing();
                }
            }
        });
        badMenu.getItems().addAll(bzhenxing1,bzhenxing2,bzhenxing3,bzhenxing4,bzhenxing5,bzhenxing6,bzhenxing7,bzhenxing8);
    }
}
