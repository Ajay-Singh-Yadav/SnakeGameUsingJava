import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Board extends JPanel implements ActionListener {



    private  Image apple;
    private  Image head;
    private  Image dot;
    private  int dots;
    private  int appleX;
    private  int appleY;

    private Timer timer;

    private  int randomNo = 29;

    private  final int all_Dots = 900;
    private  final int dot_Size = 10;
    private  final  int[] x = new int[all_Dots];
    private  final  int[] y = new int[all_Dots];

    private boolean leftDirection = false;
    private  boolean rightDirection   =true;
    private boolean upDirection = false;
    private  boolean downDirection   = false;
    private  boolean inGame = true;

    Board(){

          addKeyListener(new TAdapter());

         setBackground(Color.BLACK);
         setPreferredSize(new Dimension(300,300)) ;
         setFocusable(true);
         loadImages();
         initGame();

    }

       public  void loadImages(){
           ImageIcon i1 =  new ImageIcon(ClassLoader.getSystemResource("icons/apple.png"));
           apple = i1.getImage();

           ImageIcon i2 =  new ImageIcon(ClassLoader.getSystemResource("icons/head.png"));
           head = i2.getImage();

           ImageIcon i3 =  new ImageIcon(ClassLoader.getSystemResource("icons/dot.png"));
            dot = i3.getImage();
       }
        public void initGame(){
            dots = 3;

            for (int i=0;i<dots;i++){

                 y[i] = 50;
                 x[i] = 50-i*dot_Size;

            }

            locateApple();

            timer = new Timer(140,this);
            timer.start();

        }

        public void locateApple(){

           int r =  (int)( Math.random()*randomNo);
           appleX = r*dot_Size;
           r =  (int)( Math.random()*randomNo);
           appleY = r*dot_Size;


        }


        public  void  paintComponent(Graphics g){

         super.paintComponent(g);
         draw(g);

        }

        public  void draw( Graphics g){

        if(inGame) {


            g.drawImage(apple, appleX, appleY, this);

            for (int i = 0; i < dots; i++) {
                if (i == 0) {
                    g.drawImage(head, x[i], y[i], this);

                } else {
                    g.drawImage(dot, x[i], y[i], this);
                }
            }

            Toolkit.getDefaultToolkit().sync();
        }else {
             gameOver(g);
         }
        }


        public  void gameOver(Graphics g){

           String msg = "Game Over";

           Font font =  new Font("SAN_SERIF",Font.BOLD,14);
           FontMetrics metrics = getFontMetrics(font);
           g.setColor(Color.white);
           g.setFont(font);
           g.drawString(msg,(300 - metrics.stringWidth(msg)) / 2,300/2);
        }
        public  void move(){
        for (int i = dots; i>0;i--){
            x[i]  = x[i-1];
            y[i]  = y[i-1];
        }


        if(leftDirection){
            x[0] = x[0] - dot_Size;
        }
        if(rightDirection){
                x[0] = x[0] + dot_Size;
            }
        if(upDirection){
                y[0] = y[0] - dot_Size;
            }
        if(downDirection){
                y[0] = y[0]  + dot_Size;
            }

//        x[0] +=dot_Size;
//        //y[0] +=dot_Size;
        }



          public  void  checkApple(){

          if((x[0] == appleX) && (y[0]== appleY)){
              dots++;
              locateApple();
          }

          }

          public  void checkCollision(){
              for (int z = dots ;z>0;z--){
                  if((z>4) && (x[0] == x[z]) && (y[0] == y[z])){

                      inGame = false;
                  }
              }
              if(y[0] >=300){
                  inGame  = false;
              }

              if(x[0] >=300){
                  inGame  = false;
              }
              if(y[0] < 0){
                  inGame  = false;
              }
              if(x[0] < 0){
                  inGame  = false;
              }

              if (!inGame){
                  timer.stop();
              }

          }
        public void actionPerformed(ActionEvent e){

          if(inGame){
              checkApple();
              checkCollision();
              move();
          }
          repaint();

        }

        public class TAdapter extends KeyAdapter{


          public  void keyPressed(KeyEvent e){

               int key  = e.getKeyCode();
               if(key == KeyEvent.VK_LEFT  && (!rightDirection)){
                   leftDirection = true;
                   upDirection = false;
                   downDirection  =false;
               }
              if(key == KeyEvent.VK_RIGHT  && (!leftDirection)){
                  rightDirection = true;
                  upDirection = false;
                  downDirection  =false;
              }

              if(key == KeyEvent.VK_UP  && (!downDirection)){

                  upDirection = true;
                  leftDirection = false;
                  rightDirection  =false;
              }

              if(key == KeyEvent.VK_DOWN  && (!upDirection)){
                  downDirection = true;
                  leftDirection = false;
                  rightDirection  =false;
              }
          }

        }
}
