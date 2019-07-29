
package simple;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class elevator extends JFrame{
    int user_floor=0;
    int user = 0;
    int [] users = new int [3];
    int AC = 0;
    int [] ACs = new int [3];
    int nearest_Ele1=0;
    int nearest_Ele2=0;
    int nearest_Ele3=0;
     Timer t1 ;
     Timer t2 ;
     Timer t3 ;
    int current1 =0;
    int current2 =0;
    int current3 =0;

    boolean x1 = true;
    boolean x2 = true;
    boolean x3 = true;
    //creating the array of the buttons that represent the  floor where the user is in 
    JButton []b=new JButton [10];
    //creating the array of the buttons that represent the elevator 
    JButton [][]l=new JButton [10][3];
    //creating the array of textfield that represent the destination
    JTextField []go = new JTextField[10]; 
    JPanel p1= new JPanel(new GridLayout(10,1,5,10));    //floors of the user 
    JPanel p2= new JPanel(new GridLayout(10,3,5,10));                                             //2D array for 3 Elevators
    JPanel p3= new JPanel(new GridLayout(10,1,20,10));   //texts of the distination 
    JPanel p4= new JPanel(new GridLayout(1,3,20,10));                                             //Panel to contain 1,3,5 panels 
    JPanel p5= new JPanel();                             // panel just to make the right shape :) 
    int floor[]= new int[3];       //array to save current floor for each elevator 
    boolean [] E_state =new boolean [3];   //the state of each elevator whether it's moving (true) or not moving (false)
    int []near = new int [3];
    int w=0;

    
     elevator(){
         
    //set the states of elevators all not moving = false  as nothing happened yet 
    for(int i=0;i<=2;i++){ E_state[i]= false ;}
    //set Timers Specifications     
     t1 = new Timer (500,new timerListener());
     t2 = new Timer (500,new timerListener());
     t3 = new Timer (500,new timerListener());

     //adding the the buttons which represent the place of the user on the panel p1
     for(int i=9; i>=0 ; i-- ){
    int floor_name = (i+1);
    b[i]=new JButton("floor " +floor_name );
    p1.add(b[i]);
    b[i].addActionListener(new buttonListener());
     }
     
     //adding the buttons that represent the elevator on the panel p2
     for (int i=9;i>=0;i-- ){
        for(int j=0;j<=2;j++){
            int floor_name = (i+1);
            l[i][j]=new JButton ( "Ele " + (j+1)+ " : "+ floor_name );
            l[i][j].setBackground(Color.red);
            p2.add(l[i][j]);

        }
    }
    
     //adding the textfield that represent the destination
            for(int i=9; i>=0 ; i-- )
           {
             go[i]=new JTextField("0");
             p3.add(go[i]);
           }
     
      //creating random places of the three elevators
      for(int i = 0; i <= 2; i++) {
        floor[i]=(int)((Math.random())*10);    
        l[floor[i]][i].setBackground(Color.cyan);
      }
   
     // adding all the component on the frame
     this.setLayout(new GridLayout(1,2,5,5));
     p4.add(p1);
     p4.add(p3);
     p4.add(p5);
     this.add(p4);
     this.add(p2);
    }
  
     /*-----------------------------------------now we finished the GUI of the elevator -------------------------*/
    
            //the method which choose nearest Elevator
             public int thesmallestfromarray(int x[]){
                                int y =0;
                                int min=10;
                                for(int i=0;i<x.length;i++){
                                    if(x[i]<min){
                                       min=x[i];
                                       y=i;
                                    }
                                }
                                return y;
                          }
       
     // creating the event of the buttons 
         class buttonListener implements ActionListener {      
           public void actionPerformed(ActionEvent e){
             
                for ( int u =9; u>=0 ; u--){
                   if (e.getSource()== b[u]){
                    user = u ;

                   // taking the value of the textfield which represent the destination  AC
                    int ac =Integer.parseInt(go[user].getText()); 
                     if (ac >10 ){
                        ac = 10;
                        System.out.println("the higher you can reach is floor ten"); 
                     }
                    else if (ac < 1){
                        ac = 1;
                        System.out.println("the lower you can reach is floor one");
                    }
                     AC = (ac-1);       //ex : ac=10 then AC=9 .. then the user is in the 10'th floor
                   int h0 = Math.abs(user - floor[0]);
                   int h1 = Math.abs(user - floor[1]);
                   int h2 = Math.abs(user - floor[2]);

              ///// all Elevators moving (don't do anything )
                    if(E_state[0] && E_state[1] && E_state[2]){ System.out.println("all elevators busy");}

                    ///// all Elevator are not moving (free)
                    else if (E_state[0]  |!  E_state[1]  |!  E_state[2]){
                         //check which elevator near to the user
                        for (int i=0; i<=2;i++){
                         w = user - floor[i];
                         near[i]= Math.abs(w);     
                         }
                        nearest_Ele1 = thesmallestfromarray(near);
                        users[0] = user ;
                        ACs[0] = AC ; 
                        t1.start();
                    }

            ////only one elevator moving(compare the two others and move the nearest )    
                    else if ( E_state[0] ^  E_state[1]  ^  E_state[2]){
                 
                        if (E_state[0]==true &  E_state[1]== false &  E_state[2]== false ){

                            if (h1<h2){
                              nearest_Ele2 = 1 ;
                              users[1] = user ;
                              ACs[1] = AC ; 
                              t2.start();
                            }
                            else if (h1>h2){
                              nearest_Ele2 = 2 ;
                              users[1] = user ;
                              ACs[1] = AC ; 
                              t2.start();
                            }
                        }
                        else if (E_state[0]==false &&  E_state[1]== true &&  E_state[2]== false ){
                 
                            if (h0<h2){
                              nearest_Ele2 = 0 ;
                              users[1] = user ;
                              ACs[1] = AC ; 
                              t2.start();
                            }
                            else if (h0>h2){
                              nearest_Ele2 = 2 ;
                              users[1] = user ;
                              ACs[1] = AC ; 
                              t2.start();
                            }
                        }
                        else if (E_state[0]== false &&  E_state[1]== false &&  E_state[2]== true ){
                 
                            if (h0<h1){
                              nearest_Ele2 = 0 ;
                              users[1] = user ;
                              ACs[1] = AC ; 
                              t2.start();
                            }
                            else if (h0>h1){
                              nearest_Ele2 = 1 ;
                              users[1] = user ;
                              ACs[1] = AC ; 
                              t2.start();
                            }
                        }
            }
                 
          // two Elevator is moving , one is free (just move it ) 
            else {

                if( E_state[0]==false &&  E_state[1]==true &&  E_state[2]==true ){
                nearest_Ele3 = 0 ;
                users[2]= user ; 
                ACs[2]= AC ;
                t3.start();                     
                }
              else if ( E_state[0]==true &&  E_state[1]==false &&  E_state[2]==true ){
                nearest_Ele3 = 1 ;
                users[2]= user ; 
                ACs[2]= AC ;
                t3.start();                     
              }
              else if ( E_state[0]==true &&  E_state[1]==true &&  E_state[2]==false ){
                nearest_Ele3 = 2 ;
                users[2]= user ; 
                ACs[2]= AC ;
                t3.start();                     
              }
            }
        }
    }
 }
}
         
         //creating the class of the timer
         class timerListener implements ActionListener{
             public void actionPerformed(ActionEvent ae){
                 
                 if (ae.getSource()==t1){
                 E_state[nearest_Ele1]=true;
                 if (x1==true){ 
                   to_user1();
                 }
                 else
                   to_destination1();
                 }
                 
                 else if (ae.getSource()==t2){
                 E_state[nearest_Ele2]=true;
                 if (x2==true){ 
                   to_user2();
                 }
                 else
                   to_destination2();
                 } 
                 
                else if (ae.getSource()==t3){
                 E_state[nearest_Ele3]=true;
                 if (x3==true){ 
                   to_user3();
                 }
                 else
                   to_destination3();
                 } 
         }
    }
         
 public void to_user1(){

current1=floor[nearest_Ele1];
//check if the user is Lower than the choosen elevator 
if (Math.min(floor[nearest_Ele1],users[0])== users[0]){

    if (current1==users[0]){
        System.out.println("the user 1 now in the elevator ");
        l[users[0]][nearest_Ele1].setBackground(Color.blue);
        x1 = false;
        t1.start();
    }
    else{
        //move the elevator down (the user is lower than current)   
         --current1;
         l[current1][nearest_Ele1].setBackground(Color.green);
         l[current1+1][nearest_Ele1].setBackground(Color.red);

         floor[nearest_Ele1]=current1;

         //check if the elevator in the same floor of the user
         if(current1== users[0]-1){
         l[users[0]][nearest_Ele1].setBackground(Color.PINK);
         x1 = false;
         t1.start();
         }
    }
}
//check if the user is lower than the choosen elevator 
else if (Math.min(floor[nearest_Ele1],users[0])==floor[nearest_Ele1]){
    //move the elevator up (user is higher than current ) 
     ++current1;
     l[current1][nearest_Ele1].setBackground(Color.green);
     l[current1-1][nearest_Ele1].setBackground(Color.red);

    floor[nearest_Ele1]=current1;

    //check if the elevator in tha same floor of the user
    if (current1==users[0]+1){
     l[users[0]][nearest_Ele1].setBackground(Color.PINK);
     x1 = false;
     t1.start();
    }
}
}
 
public void to_destination1(){
//second move (destination)
if(Math.min(current1,ACs[0])==ACs[0]){
    //check if the destinatin is the same floor  of the user  
    if(current1 == ACs[0]){
      l[ACs[0]][nearest_Ele1].setBackground(Color.black); 
      System.out.println("the user 1 reached the destination ");            
      t1.stop();
      x1 = true;
      E_state[nearest_Ele1] = false;
      }
    else { 
         //move the elevator down  
          --current1;      
          l[current1][nearest_Ele1].setBackground(Color.green);
          l[current1+1 ][nearest_Ele1].setBackground(Color.red);

          floor[nearest_Ele1]=current1;

           //check if elevator reaches the destination (AC)
           if(current1==ACs[0]){
             l[ACs[0]][nearest_Ele1].setBackground(Color.black);
             System.out.println("the user 1 reached the destination ");
             t1.stop();
             x1 = true;
             E_state[nearest_Ele1] =false;
            }
    }
}        

else if(Math.min(current1,ACs[0])==current1){

   //check if the user has reached the destination yet
    if(current1==ACs[0]){
         l[AC][nearest_Ele1].setBackground(Color.black);
         System.out.println("the user 1 reached the destination ");
         t1.stop();
         x1 = true;
         E_state[nearest_Ele1] = false ;
       }
    else { 
         //move the elevator up 
         current1++;      
         l[current1][nearest_Ele1].setBackground(Color.green);
         l[current1-1 ][nearest_Ele1].setBackground(Color.red);

         floor[nearest_Ele1]=current1;      

         //check if elevator reaches the destination (AC)
           if(current1==ACs[0]){
             l[ACs[0]][nearest_Ele1].setBackground(Color.black);
             System.out.println("the user 1 reached the destination ");
             t1.stop();
             x1 = true;
             E_state[nearest_Ele1] =false;
            }
    }
}
}
        
 public void to_user2(){

current2=floor[nearest_Ele2];
//check if the user is Lower than the choosen elevator 
if (Math.min(floor[nearest_Ele2],users[1])== users[1]){

    if (current2 == users[1]){
        System.out.println("the user 2 now in the elevator ");
        l[users[1]][nearest_Ele2].setBackground(Color.blue);
        x2 = false;
        t2.start();
    }
    else{
        //move the elevator down (the user is lower than current)   
         --current2;
         l[current2][nearest_Ele2].setBackground(Color.green);
         l[current2+1][nearest_Ele2].setBackground(Color.red);

         floor[nearest_Ele2]=current2;

         //check if the elevator in the same floor of the user
         if(current2== users[1]-1){
         l[users[1]][nearest_Ele2].setBackground(Color.PINK);
         x2 = false;
         t2.start();
         }
    }
}
//check if the user is lower than the choosen elevator 
else if (Math.min(floor[nearest_Ele2],users[1])==floor[nearest_Ele2]){
    //move the elevator up (user is higher than current ) 
     ++current2;
     l[current2][nearest_Ele2].setBackground(Color.green);
     l[current2-1][nearest_Ele2].setBackground(Color.red);

    floor[nearest_Ele2]=current2;

    //check if the elevator in tha same floor of the user
    if (current2==users[1]+1){
     l[users[1]][nearest_Ele2].setBackground(Color.PINK);
     x2 = false;
     t2.start();
    }
}
}
 
public void to_destination2(){
//second move (destination)
if(Math.min(current2,ACs[1])==ACs[1]){
    //check if the destinatin is the same floor  of the user  
    if(current2 == ACs[1]){
      l[ACs[1]][nearest_Ele2].setBackground(Color.black); 
      System.out.println("the user 2 reached the destination ");            
      t2.stop();
      x2 = true;
      E_state[nearest_Ele2] = false;
      }
    else { 
         //move the elevator down  
          --current2;      
          l[current2][nearest_Ele2].setBackground(Color.green);
          l[current2+1][nearest_Ele2].setBackground(Color.red);

          floor[nearest_Ele2]=current2;

           //check if elevator reaches the destination (AC)
           if(current2==ACs[1]){
             l[ACs[1]][nearest_Ele2].setBackground(Color.black);
             System.out.println("the user 2 reached the destination ");
             t2.stop();
             x2 = true;
             E_state[nearest_Ele2] =false;
            }
    }
}        

else if(Math.min(current2,ACs[1])==current2){

   //check if the user has reached the destination yet
    if(current2==ACs[1]){
         l[ACs[1]][nearest_Ele2].setBackground(Color.black);
         System.out.println("the user 2 reached the destination ");
         t2.stop();
         x2 = true;
         E_state[nearest_Ele2] = false ;
       }
    else { 
         //move the elevator up
         current2++;
         l[current2][nearest_Ele2].setBackground(Color.green);
         l[current2-1][nearest_Ele2].setBackground(Color.red);

         floor[nearest_Ele2]=current2;      

         //check if elevator reaches the destination (AC)
           if(current2==ACs[1]){
             l[ACs[1]][nearest_Ele2].setBackground(Color.black);
             System.out.println("the user 2 reached the destination ");
             t2.stop();
             x2 = true;
             E_state[nearest_Ele2] =false;
            }
    }
}
}

public void to_user3(){

current3=floor[nearest_Ele3];
//check if the user is Lower than the choosen elevator 
if (Math.min(floor[nearest_Ele3],users[2])== users[2]){

    if (current3==users[2]){
        System.out.println("the user 2 now in the elevator ");
        l[users[2]][nearest_Ele3].setBackground(Color.blue);
        x3 = false;
        t3.start();
    }
    else{
        //move the elevator down (the user is lower than current)   
         --current3;
         l[current3][nearest_Ele3].setBackground(Color.green);
         l[current3+1][nearest_Ele3].setBackground(Color.red);

         floor[nearest_Ele3]=current3;

         //check if the elevator in the same floor of the user
         if(current3== users[2]-1){
         l[users[1]][nearest_Ele3].setBackground(Color.PINK);
         x3 = false;
         t3.start();
         }
    }
}
//check if the user is lower than the choosen elevator 
else if (Math.min(floor[nearest_Ele2],users[1])==floor[nearest_Ele2]){
    //move the elevator up (user is higher than current ) 
     ++current3;
     l[current3][nearest_Ele3].setBackground(Color.green);
     l[current3-1][nearest_Ele3].setBackground(Color.red);

    floor[nearest_Ele3]=current3;

    //check if the elevator in tha same floor of the user
    if (current3 ==users[2]+1){
     l[users[2]][nearest_Ele3].setBackground(Color.PINK);
     x3 = false;
     t3.start();
    }
}
}
 
public void to_destination3(){
//second move (destination)
if(Math.min(current3,ACs[2])==ACs[2]){
    //check if the destinatin is the same floor  of the user  
    if(current3 == ACs[2]){
      l[ACs[2]][nearest_Ele3].setBackground(Color.black); 
      System.out.println("the user 3 reached the destination ");            
      t3.stop();
      x3 = true;
      E_state[nearest_Ele3] = false;
    }
    else { 
         //move the elevator down 
          --current3; 
          l[current3][nearest_Ele3].setBackground(Color.green);
          l[current3+1][nearest_Ele3].setBackground(Color.red);

          floor[nearest_Ele3]=current3;

           //check if elevator reaches the destination (AC)
           if(current3==ACs[2]){
             l[ACs[2]][nearest_Ele3].setBackground(Color.black);
             System.out.println("the user 3 reached the destination ");
             t3.stop();
             x3 = true;
             E_state[nearest_Ele3] =false;
            }
    }
}        

else if(Math.min(current3,ACs[3])==current3){

   //check if the user has reached the destination yet
    if(current3==ACs[2]){
        l[ACs[2]][nearest_Ele3].setBackground(Color.black);
        System.out.println("the user 3 reached the destination ");
        t3.stop();
        x3 = true;
        E_state[nearest_Ele3] = false ;
    }
    else { 
         //move the elevator up 
         current3++;
         l[current3][nearest_Ele3].setBackground(Color.green);
         l[current3-1 ][nearest_Ele3].setBackground(Color.red);

         floor[nearest_Ele3]=current3;      

         //check if elevator reaches the destination (AC)
           if(current3==ACs[2]){
             l[ACs[2]][nearest_Ele3].setBackground(Color.black);
             System.out.println("the user 3 reached the destination ");
             t3.stop();
             x3 = true;
             E_state[nearest_Ele3] =false;
            }
    }
}
}
   
     
}