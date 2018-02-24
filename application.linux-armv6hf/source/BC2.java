import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import processing.serial.*; 
import gifAnimation.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class BC2 extends PApplet {




Serial myPort;  
char val; 
PImage [] imgs = new PImage[8];

int begin; 
int duration = 4;
int countdown=22;
int time = 20;
int num=0;
int test=2;
int card=1;
boolean a,b,grey;
boolean trans,idle;//state bools... should become enums
int state, pendingState;
int tState; //0 = nothing, 1 = up, -1 is down
PImage currentImage;
Gif myAnimation, arrow, logo, logo_alt, corner_logo, timer, check_mark;
int alpha = 25, delta = 20;

public void setup()
{
  frameRate(15);
  
  printArray(Serial.list());
  for(int i =0;i<imgs.length;i++){
    //imgs[i]=loadImage(i+".png");
    imgs[i]=loadImage("processing_"+ i +".png");
  }
  // boolean portNr = activateSerialPort("tty.usbmodem", 115200); // Desktop
  boolean portNr = activateSerialPort("ACM", 115200); // Raspbian
  b=true;
  grey = true;
  trans = false; //transition state
  idle = false;
  state = -1; //start state
  tState = 1;
  
  
  arrow = new Gif(this, "Arrow.gif");
  logo = new Gif(this, "LogoGreen.gif");
  logo_alt = new Gif(this, "LogoWhite.gif");
  corner_logo = new Gif(this, "small_green.gif");
  timer = new Gif(this, "countdown_crop.gif");
  check_mark = new Gif(this, "Arrow.gif");
}

public boolean activateSerialPort(String partOfPortName, int SerialSpeed) {
  boolean portIsActivated = false;
  int portIndex = -1;
  
  for(int i = 0; i < Serial.list().length; i++) {   // go thru all serial ports
    print(i);    
    
    if(Serial.list()[i].indexOf(partOfPortName) > 0) { // chech if name match
      portIndex = i;
      print("*");
    }
    println("\t" + Serial.list()[i]);
  }
  
  if(portIndex < 0 ) {
    println("Error: No serial port found - " + partOfPortName);
  }else{
    myPort = new Serial(this, Serial.list()[portIndex], SerialSpeed);
    println("Serial conection: " + Serial.list()[portIndex] + " speed: " + SerialSpeed);  // activate
  }
  return portIsActivated;  
}

public void draw()
{  read();
  noCursor();
  switch (num){
    case 0:  
    landing(); //landing  
    break;
    case 1:
    analisis(); //analizing
    break;
    case 2:
    results(); //results
    break;
    case 3:
    encryption();
    break;
    case 4:
    printing(); ///printing
    break; 
    case 5:
    //myPort.write('0');
    bye(); ///goodbye
    b=true;
    break;
    }
    if (idle == false){
      fade();
    }
    transitionHandler(state);
}
public void keyPressed() {
  if( (key == 'a') || (key ==  '1') ){
    println("a");
     num=1;
     begin=millis();
  }
  if( (key == 'b') || (key ==  '3') ){
    println("b");
    num=3;
    begin=millis();
  }
   if(  (key == 'r') || (key ==  '0') ){
    println("b");
    num=0;
    begin=millis();
  }
  if( (key == '2') || (key ==  '2') ){
    println("c");
    num=2;
    begin=millis();
  }
   if (key == '4'){
     num = 4;
     begin=millis();
   }
}

public void fade(){
   if (alpha <= 0 || alpha >= 255) { 
      delta= -delta; 
    }
    alpha += delta; 
}

public void transitionHandler(int val){
   transition(state);
}

public void transition(int pageContents){
  switch (pageContents) {
    case 0:
      landingContents();
    break;
    case 1:
      analysisContents();
    break;
    case 2:
      resultsContents();
    break;
    case 3:
      encryptionContents();
    break;
    case 4:
      printingContents();
    break;
    case 5:
      byeContents();
    break;
  }
}
  
public void doneTransPage(){
 idle = true;
 tState = -1;
 alpha = 254;
 delta = -delta;
}

public void pageChange(){
  if (alpha >= 255){
    doneTransPage();
  } else if (alpha <= 0){
    state = pendingState;
  }
}

public void landing(){
  if (state == -1){
    state = 0;
    idle = false;
    transition(state);
    myPort.write('I');
    println("init");
  }
  if( (idle == true) && (state != 0) ){
        pendingState = 0;
        idle =false;
        transition(state);
        myPort.write('I');
        println("init");
      }
}
public void landingContents(){
  image(imgs[7],0,0);
  pushStyle();
    tint(255, alpha);
    image(imgs[0],0,0);
    arrow.play();
    if (alpha>250){ arrow.play(); image(arrow, width/2 - 15, height-100); } else {arrow.stop(); }
    if (alpha> 150){ logo.play(); image(logo, width/2 - 60, height/2 -285); } else { logo.stop(); }
  popStyle();
  pageChange();
}



public void analisis(){
  if( (idle == true) && (state != 1) ){
        pendingState = 1;
        idle = false;
        transition(state);
        time = 20;
      }
  if (time > 0){  
      time = duration - (millis() - begin)/1000;  
  }
  else{
    num=2;
    begin=millis();
    time=200;    
  }    
}
public void analysisContents(){
 // println("render analysis");
  image(imgs[6],0,0);
  pushStyle();
    tint(255, alpha);
    image(imgs[1],0,0);
    if (alpha> 150){ logo_alt.play(); image(logo_alt, width/2 -60, height/2 -245); } else { logo_alt.stop(); }
  popStyle();
  pageChange();
}

public void results(){
  println(time);
  if( (idle == true) && (state != 2)){
        pendingState = 2;
         idle = false;
        transition(state);
      }
  if (time > 0){
    time = countdown - (millis() - begin)/1000;
  }else{
    num = 5;
    myPort.write('E');
     delay(10);
    time=2;
    begin=millis();
  }
}
public void resultsContents(){
  //println("render results");
 
  image(imgs[7],0,0);
  pushStyle();
    tint(255, alpha);
    image(imgs[2],0,0);
     if (alpha> 250){ timer.play(); image(timer, width-150, 30); } else { timer.stop(); }
    if (alpha> 250){ corner_logo.play(); image(corner_logo, 35, 37); } else { corner_logo.stop(); }
    if (alpha>200){ arrow.play(); image(arrow, 50, height-100); } else {arrow.stop(); }
  popStyle();
  pageChange();
}

public void encryption(){
  if( (idle == true) && (state != 3)){
        pendingState = 3;
         idle = false;
         delay(1500);
         myPort.write('P');
        transition(state);
      }
  if (time > 0){          
    time = duration+2 - (millis() - begin)/1000;
  } else {
    num = 4;
    begin=millis();
  }
}
public void encryptionContents(){
  //println("render crypto");
  image(imgs[6],0,0);
  pushStyle();
    tint(255, alpha);
    image(imgs[3],0,0);
    if (alpha> 150){ logo_alt.play(); image(logo_alt, width/2 -60, height/2 -245); } else { logo_alt.stop(); }
  popStyle();
  pageChange();
}


public void printing(){
 if( (idle == true) && (state != 4)){
        pendingState = 4;
         idle = false;
        transition(state);
        time = 20;
      }
  if (time > 0){          
    time = duration+2 - (millis() - begin)/1000;
  } else {
    num = 5;
    begin=millis();
  }
}
public void printingContents(){
  //println("render printing");
  image(imgs[7],0,0);
  pushStyle();
    tint(255, alpha);
    image(imgs[4],0,0);
        if (alpha> 250){ corner_logo.play(); image(corner_logo, 35, 37); } else { corner_logo.stop(); }
      if (alpha> 150){ logo.play(); image(logo, width/2 -60, height/2 -285); } else { logo.stop(); }
  popStyle();
  pageChange();
}


public void bye(){
  if( (idle == true) && (state != 5)){
        pendingState = 5;
         idle = false;
        transition(state);
        time = 20;
      }
  if (time > 0){          
   time = duration - (millis() - begin)/1000;
  } else {
    num = 0;
 
    
  }
}

public void byeContents(){
  image(imgs[6],0,0);
  pushStyle();
    tint(255, alpha);
    image(imgs[5],0,0);
    if (alpha> 250){ logo_alt.play(); image(logo_alt, width/2 -60, height/2 -245); } else { logo_alt.stop(); }
  popStyle();
     myPort.write('I');
     delay(10);
    myPort.write('L');
  pageChange();
}


public void read(){
  if ( myPort.available() > 0) {  
    val = myPort.lastChar();
    println(val);
  }
  if (val == 'X' ) { 
    num=1; //analsis + result
    begin=millis();
    println("test");
  }
   if (val == 'Y' && state==2) {    
    num=3; //resutls + print + goodby
    begin=millis();
    println("card");
  }
  val = '_';
}
  public void settings() {  size(1280,1024); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "--present", "--window-color=#666666", "--hide-stop", "BC2" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
