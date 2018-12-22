# Java 大作业说明

## 开发环境

* jdk=1.8.0_181_b13
* mvn=3.6.0

## 游戏说明

* `mvn clean test package`在target目录下得到jar包 可直接执行。
* 在初始状态下可以在菜单栏中为葫芦娃和妖怪选择阵型。提供8种阵型选择。
* 左侧为战斗场景，右侧文字框展现结果(包括角色移动和攻击)同时给出操作提示。
* 按空格键后，会弹出文件选择框以选择文件作为回放记录存储处。如果没有选择，则记录不会被保存下来。后续，双方会自动战斗(走动随机)。当爷爷或是蛇精被干掉，则游戏结束(擒贼先擒王)，弹出GameOver。整个战斗过程中，随着血量的减少，自身透明度会发生改变(为了不让死亡变得非常突兀)。当血量减少至一半时，会自动带上愤怒标签。当角色死亡后，会变成一块墓碑或十字架(同样可以被攻击，攻击两次后，死亡印记消失，这个角色就永远离开了)。整个游戏采用的策略比较单一，角色先判断能否对周围九宫格内进行攻击(包括死亡角色的墓碑)，若不能，则随机生成 一个方向向量(上下左右)进行行走(行走可能失败)。
* 通过按R键进行复位。
* 按L键同样弹出选择框，选择记录文件，随后自动开始回放。一份个人觉得比较精彩的记录放于Goodplayback目录下的data_7.dat中。

## 效果展示图

![GIF](C:\Users\13745\Desktop\GIF.gif)


## 包结构

由于设计的类比较多，按照包管理机制对类文件进行管理。主要包括如下内容.

* `administer`包用于对阵营、线程、记录、阵型初始化进行管理工作;

  * `BadManAdminister`用于对小怪、蛇精、蝎子精进行管理。主要对他们进行初始化以及判断他们的生死情况。
  * `GoodManAdminster`与之类似，管理爷爷和7个葫芦娃。
  * `ThreadAdminister`用于管理线程。
  * `CommandsAdminister`用于对记录进行管理。
  * `FormationAdminister`用于对不同阵型的初始化数据进行管理。

* `annotation`包用于管理自定义的注解类;

  * `ClassAnnotation`用于对类进行注解，该类型注解只保留在编译之前(源码阶段)，使用@Inherited使得子类能够继承父类的该类型注解。其包括开发人员姓名、版本、类描述属性。其中前两个都存在默认值。

* `being`包下包括所有的存在类型;

  ![1](C:\Users\13745\Desktop\project_pic\1.JPG)

  * Being作为该包中的总基类。实现Cloneable、Serializeable接口(用于支持保存文件进行回放)。其主要包括坐标信息以及图片信息。

  * Creature表示生物，扩充了如下信息。同时实现Runnable接口，从而可以作为载荷被运载火箭发射。

  | 属性             | 含义                             |
  | ---------------- | -------------------------------- |
  | int hp           | 生命值                           |
  | boolean alive    | 是否存活                         |
  | int battle       | 攻击值                           |
  | int numDisappear | 死亡状态下需要攻击多少次墓碑消失 |
  | int fullHp       | 满血时的hp                       |
  | int speed        | 每步决策速度                     |
  | int id           | 标号                             |

  * 同时包括两个基本函数`move(),attack()`来支持生物之间的交互。其余的子类中除了HuLuWa还包括额外属性rank之外，都没有增添额外的属性以及方法。虽然显得有些冗余，但这样的多级层次化结构在使用RTTI时还	是提供了很大的遍历。同时也为后期拓展提供了可能。

* `formation`包管理有关阵型的类;

![2](C:\Users\13745\Desktop\project_pic\2.JPG)

  Formation类为抽象类，不能显示实例化。其8个子类具体实现了某种阵型。同时FormationFactory类中使用工厂方法，生产出特定的阵型向外提供。外界只与该包中的工厂方法进行交互。

* `gui`包用于管理UI界面的类;

  * MyTextArea继承自JavaFx中的TextArea组件。实现`appendTextArea()`方法。因为组建自带的`appendText()`方法在更新太快时会出现些问题。

  * FightPlace继承自JavaFx中的Canvas组建。我把它当作画布使用。所有场景的绘制都在它上面进行。

* `playback`用于管理跟回放相关的类;

  存储运行信息我是采用了序列化技术，将对象存于文件中。读取文件就是反序列化。具体回放是其实就是一个while循环。

  * Command表示存进文件的一个记录。其包括两个属性Creature和Behave。Creature表示行为的主体。Behave表示行为本身。

  * Behave表示一个行为。作为基类，下有战斗行为和移动行为(AttackBehave和MoveBehave)。

  * PlayBacker主要是进行文件存取。包括`writeCommands()`和`readCommands()`方法。

    ![3](C:\Users\13745\Desktop\project_pic\3.JPG)

* `sample`包中包含程序入口`Main`;

  主要完成布局设置，GUI初始化以及监听处理事件的设置。

* `space`包用于管理表示二维地图;

  * Square表示二维地图的一个单元。有坐标属性以及Being句柄。
  * Board为一个二维地图，通过Square合成。

* `util`包主要包括一些进行辅助功能的类。

  * DirectionVector表示一个方向向量。由此可以定义上下左右。

  * GameStatus作为一个枚举类表示游戏当前的状态。有四种状态，READY、DOINGRANDOM、DOINGPLAYBACK、GAMEOVER。状态转移图如下所示。

    ![4](C:\Users\13745\Desktop\project_pic\4.JPG)

  * StyleImage用于给Being贴一层皮。我的想法是不需要每一个being实例化后都直接拥有一个image，如此实现StyleImage到image的映射。这样多个being可以通过StyleImage共享一个image。



## 设计理念

### 封装

将实现细节封装在黑箱之中，只暴露接口供外界使用。封装是为了复用。具体包括两种，过程式封装和类封装。过程式封装其实就是函数，它完成了对一组操作的封装。而类封装不只看到了操作，它将数据与操作有机结合在了一起，数据也被隐藏起来了，这种封装实际上就导致了对象概念的产生。在具体实现中，这两种封装无处不在，不加赘述。

### 继承

继承就是描述IS-A的关系。他可以使得类之间有一种层次化的结构关系。最重要的是他也实现了复用。子类直接复用了基类的代码，从而不用从零开发。我在being，formation，playback大量使用了继承。但是继承也间接了破坏了封装，基类数据存在向子类暴露的风险。故`protected`关键字使得设计者能够解决数据的封装性存在的问题。在描述基类属性中，我基本都用`protected`进行描述。例如being中的坐标信息，因为我觉得子类是有资格对这些属性进行访问的。

### 多态

因为继承就决定出了多态的产生。父类向外暴露的接口，子类也拥有。这种接口的统一性使得我们能够不关心具体的类型，而只需关注接口。同时动态绑定机制使得接口能够自动得和其具体的实现连接。例如creature能够战斗，那么它所有的子类都可以战斗，但不同子类的战斗可以千变万化。同时多态也产生了很多奇妙的概念、设计。例如子类型的概念，在父类出现的场所中，都可以用子类替换完成，这里要求他们完成的动作相同。但这种子类型需要开发者自己去维护。再例如模板方法，模板方法中抽象出了一组操作序列，而不同的对象完全可以通过多态实现同一种方法的不同结果，模板方法正是利用了多态完成了一种复用。

在creature包中，我的代码逻辑部分主要在creature类中。creature的子类都通过其父类的逻辑进行移动以及攻击操作。

在formation包中，虽然Formation作为一个抽象类不能被显示实例化，但他完成了`putFormationOnBoard()`接口逻辑，而它的所有子类都可以作为它的子类型。

在playback包中，Behave作为一个基类。由于我是采用序列化写文件的方式，故每一个实现序列化的类最好都实现深拷贝。如此Behave就需要实现Cloneable接口。`clone()`方法就是接口，Behave和它的子类都拥有这个接口，但这个接口的实现应该是不同的，因为Behave、AttackBehave、MoveBehave有着不同的属性。

## 基本要求的解决方案

* 要求每一个生物体实现为一个线程，并通过同步机制实现共享变量的互斥访问。

  Creature实现Runnable接口。在设计方案中，由于二维地图board是一个共享变量，故需要考虑对它进行加锁。

  ```java
  public class Creature extends Being implements Runnable{
  	@Override
      public void run() {
      	while (!Thread.currentThread().isInterrupted() ) { //游戏结束时外界会进行强制打断
              if(this.alive==false)       //如果这个生物死了 这个线程也就不run了
                  break;
             	...							//sleep一会 作为一次决策的时间
              ...							//决定是move 还是 attack
              move();
              ...
              attack();
      }
  }
  ```

  共享变量访问问题主要存在于move()和attack()函数中。故只需要在其函数中进行加锁，而不需要在run下面的逻辑中整体加锁，这样粒度太大了。

  ```java
  public void move(int dx,int dy,Board board){
      synchronized(board){	//这里进行加锁
          if(board.canPutCreature(x+dx,y+dy)==false)
              return;
          else{
              ...
          }
      }
  }
  ```

  ```java
  public void attack(Creature c,Board board){
      synchronized(board){
          if(c.isAlive()==false){	//敌人已经死亡
              ...
          }
          else{
           	...   
          }
      }
  }
  ```

  我在多线程协作上出了满多问题，主要是对共享变量的考虑不够仔细。一开始我甚至是忘了对全局存储记录数据的数组加锁，导致将记录写入时顺序出现不一致。再有就是由于我判断游戏结束时会主动打断所有的线程，这个打断动作完成后，有些线程还在继续往全局存储回放记录的数组里写东西。而如果我此时打断动作做完直接去将记录数组写进文件中，这时会直接报一个ConcurrentModificationException异常。我的解决方案是打断动作做完后睡一会，等待其写入数组完成。

* 图形化

  我只使用了javaFx中两个组件，Canvas用于图形的绘制而TextArea用于文本信息输出。当每次内部出现事件(移动、攻击)，都会通知Canvas进行重绘。每次重绘都是先刷背景，然后扫描board，每个生物挨个绘制。在与同学的交流中，发现有两种思路。一种是内部事物直接通知UI进行绘制。第二种是不管内部事物，UI进行周期性的冲刷。似乎后者更加体现了MVC模式，实现业务逻辑、界面的分离。但我觉得前者似乎也没啥不妥。在MFC的文档-视图分离实现中，也是在文档感知到有事件导致内部数据发生变化时去通知视图去变化。所以我觉得第一种方案也体现了UI与内部数据的隔离。

* 回放实现

  在游戏进行中，我会维持一个`ArrayList<Command>`数组存储每一个记录。Command包括一个Creature和一个Behave。例如蓝娃移动到(3,3)，这就构成了一个记录。故每一个生物线程中完成一次活动，都会向数组中写入一个Command记录。同时每一个Behave还有一个额外属性用于表示时间。每次线程完成一个活动后，都会计算当前时间与游戏开始时间的差值，并将这个时间戳记录进Behave中。

  回放就是一个while循环。循环读取Command数组，然后执行每一条Command指令。同时通过Behave上记录的时间戳，从而确定执行指令的时间。

  ```java
  while(cur<commands.size()){
      Command command=Commands.get(cur);
      placeBackCurTime=System.currentTimeMillis();
      if(command.getBehave().getSleepTime()<=(placeBackCurTime-placeBackStartTime)){
          ...	//指令执行
      }
      // 继续循环
  }
  ```

  其实本质上就是将多线程下执行的操作序列记录成一个串行序列，然后回放就是对这个串行序列顺序执行。


* 异常处理机制

  我主要是在文件存取时用到了异常处理。在写入文件时主要捕捉的是IOException，而在文件读取时，由于采用的是序列化技术，读取的是一个个对象，故处理有IOException还可能有ClassNotFoundException(因为我们需要将读到的Object类型塑形为Command)。同时由于采用的是循环readObject的方式，故读取到最后无法继续读取时就会报一个IO异常，这是符合预期的。

* 集合类型机制

  二维地图我没有使用集合类型，因为java不支持操作符重载，二维数组取元素不太方便。感觉应该问题不大。集合类型中我主要使用了ArrayList作为数组使用(动态增长)。BadManAdminister会管理一个小怪数组，而GoodManAdminister管理葫芦娃数组。而回放的每一条记录也使用此进行存储。其相较原生数组而言，可动态增长，同时提供大量接口供使用者使用。

* 泛型机制

  即将类型进行参数化。在实验中遇到跟课件中类似的问题，特此记录。

  ```java
  public void putFormationOnBoard(Board board,ArrayList<Creature> creatures){
      ...
  }
  //调用
  monsters=badManAdminister.getMonsters();//monsters类型为ArrayList<LittleMonster>
  formation.putFormationOnBoard(board,monsters);//error
  ```

  这样就出错了，小怪是一个生物。但装小怪的篮子不是装生物的篮子。需要将形参增加通配符，使得形参可以指向更多类型的篮子。改为`ArrayList<? extends Creature>`。

  利用泛型显示指出Square与Being的关系。

  ```java
  public class Square<T extends Being>{
      private T being;
  }
  ```

* 注解机制

  只添加了自定义的类注解。其在源代码中保留，包括开发者姓名、版本、类描述属性。也增加了些基本的注解，如@Override，@Deprecated(我用它标记了一些废弃的函数，如定时刷新界面的函数等)

* 输入输出机制

  主要就是用了序列化与反序列化使得对象与字节流相互之间转换。

* 单元测试

  主要书写了四个测试类。FormationTest用于检测初始化敌我阵型是否会碰撞。CreatureMoveTest用于检测生物在四周都是生物的情况下是否会移动，侵占他人的区域。CreatureAttackTest用于检测生物的攻击逻辑。PlayBackTest则读取resources资源下的一个简单回放文件。对序列进行检查。一边仿真一边检查，监测点包括当前次序下生物是否存在，以及攻击行为发生的合法性等。该检测时间要一分钟左右。

## 再谈设计

本学期介绍了一些重要的设计原则以及常见的设计模式，在作业中也体现出一些。

1. 单一职责原则

   在adminster包中，每一个类的职责都非常清晰。其管理主要涉及的都是比较重要的存储变量。如果没有这些类，那么对这些存储变量的访问、修改操作就会散落在项目的各个角落中，不利于维护。

2. 开放封闭原则

   Formation类中有一个putFormationOnBoard方法。当后期我们增加更多的阵型时，这个方法不需要改变，我们只需要增加子类即可，体现了对拓展开放、对修改封闭。

3. 里氏替换原则

   里氏原则体现的就是子类型的概念。如果我们要符合这种原则，就必须在子类中小心不去覆盖父类的方法(非抽象)。去刻意符合这种原则的原因保证基类实现接口内容的不变性。实现中在formation包中，是符合子类型的。

4. 合成、聚合复用原则

   例如Board由多个Square合成。而FightPlace拥有着Board以及administer包中的所有的管理员类，实现了黑箱复用。


5. 组合模式

   在UI中BorderPane由Canvas和TextArea组合而成。

6. 工厂方法

   FormationFactory通过create方法提供一个阵型。

## 学期总结

本学期C++和Java两门同上，感觉体验不错。两种语言为了实现面向对象思想提供的解决方案在大体上可以说是差不多的，但感觉Java写起来更舒服点。总体来说，通过这个学期，我对OOP有了更深的理解。以前我认为，写个class就是OO了，还get不到多态的奇妙之处等等。慢慢体验下来，其实感觉很多事情确实是很自然的。抽象、封装、复用，这些词绝不是随口说说就好。在开放大型软件项目，OO中的诸多特性都能帮助我们去降低问题的复杂度，使得问题规模可控。同时，课上的那句话“变是不变的真理”这句话也给我很大的触动，以后工作中，我们写的每一行代码或许都要经过各种回炉再造，对自己代码的不负责或许最后这个坑还得自己来填。至于课上说的设计原则和设计模式，偏形而上一点，结合案例去体会也有收获甚至有点小小的震撼，今后我也会以此为要求来写代码，因为这一切的受益者都是自己！最后感谢两位老师以及助教的工作！