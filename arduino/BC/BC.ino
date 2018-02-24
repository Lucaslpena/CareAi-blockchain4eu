#include <Adafruit_NeoPixel.h>
#include "Adafruit_Thermal.h"
#include "l.h"
#include "d.h"
#include "q.h"
///LEDS INITIALITZATION
#ifdef __AVR__
  #include <avr/power.h>
#endif
#define PIN            13
#define NUMPIXELS      6
Adafruit_NeoPixel pixels = Adafruit_NeoPixel(NUMPIXELS, PIN, NEO_GRB + NEO_KHZ800);
///THERMAL PRINTER INITIALITAZION
#include "SoftwareSerial.h"
#define TX_PIN 6 // Arduino transmit  YELLOW WIRE  labeled RX on printer
#define RX_PIN 5 // Arduino receive   GREEN WIRE   labeled TX on printer
SoftwareSerial mySerial(RX_PIN, TX_PIN); // Declare SoftwareSerial obj first
Adafruit_Thermal printer(&mySerial); 
//VARIABLES

int leds = 13; ///PIN LEDS
int card = 0; ///PIN PHOTORESISTOR CARD
int tst = 1; ///PIN PHOTORESISTOR TEST
int prevCard, prevTest; ///READING THE AMBIENT LIGHT AT THE START
char val; ///VALUE COMING FROM PROCESSING FOR PRINTIN IF ITS 'P'
boolean readTest = true;
boolean readCard = false;
///TIMINGS
int duration = 8;
int t = 20;
long start;
int n=3; //0 == readingCard; 1 == readingTest; 3 == idle state, listening. 4 == print();

void setup() {
  pinMode(card, INPUT);
  pinMode(tst, INPUT);              
  Serial.begin(115200); 
  pixels.begin(); 
  for(int i=0;i<NUMPIXELS;i++){ //TURN ON THE LIGHTS  
    pixels.setPixelColor(i, pixels.Color(0,150,0)); 
    pixels.show();
  }
  mySerial.begin(19200);
  printer.begin();
  readSensor();
  readyTest();
}

void readSensor(){
  prevCard = analogRead(card);
  prevTest = analogRead(tst);
  
  String myString = String(analogRead(prevCard));
   Serial.println(myString);
   myString = String(analogRead(prevTest));
   Serial.println(myString);
}


void loop() {
  // readSensor(); 
  switch(n){
    case 1:   
    blinkCard();
    break;
    case 2:  
    blinkTest();  
    break;
    case 3:    
    sensors();
    break;
    case 4: //and state == 3   
    printing();
    break; 
  }
  reading(); 
  //Serial.println(readCard);
}

void readyTest(){ readTest = true; readCard = false; }
void testState(){ n=2; }
void readyCard(){  readTest = false; readCard = true; }
void cardState(){ n=1; }
void readyPrint(){ readTest = false; readCard = false; }
void setPrint(){ n=4; }
void setIdle(){ n=3; }

void reading(){
 while (Serial.available()) { 
   val = Serial.read(); 
 }
 if (val == 'P') {
  setPrint();
 }
 if ((readTest == false) && (readCard == false) && (val == 'I') ){
  delay(1000);
  readyTest();
 }
 if (val == 'E') {
  readyPrint();
    String myString = "DE";
  Serial.println(myString);
 }
 val = ' ';
}

void sensors(){
  if((analogRead(tst) < prevTest-200) && (readTest == true)) {
    t=20;
    start=millis();
    Serial.write('X');
    testState();
  } else if((analogRead(card) < prevCard-200) && (readCard == true) ){//} && readCard){
    t=20;
    start=millis();
    Serial.write('Y');
    cardState();
  }
  delay(50);                                 
}

void blinkCard(){
  if (t > 0 ){          
    t = duration - (millis() - start)/100;         
  }else{
    readyPrint();
    setIdle();
    t=20;   
  }    
  if(t%2==0){
     for(int i=0;i<NUMPIXELS/2;i++){    
      pixels.setPixelColor(i, pixels.Color(0,150,0)); 
      pixels.show();
    }
  }else{
      for(int i=0;i<NUMPIXELS/2;i++){   
      pixels.setPixelColor(i, pixels.Color(0,0,0)); 
      pixels.show();
    }
  }
  
}

void blinkTest(){
  if (t > 0 ){          
    t = duration - (millis() - start)/100;         
  }else{
    readyCard();
    setIdle();
    t=20;    
  }    
  if(t%2==0){
     for(int i=NUMPIXELS/2;i<NUMPIXELS;i++){   
      pixels.setPixelColor(i, pixels.Color(0,150,0)); 
      pixels.show();
    }
  }else{
      for(int i=NUMPIXELS/2;i<NUMPIXELS;i++){    
      pixels.setPixelColor(i, pixels.Color(0,0,0)); 
      pixels.show();
    }
  } 
}

void printing(){
  printer.wake();
  printer.feed(2);
  printer.printBitmap(l_width,l_height,l_data);
  printer.println(F(" .............................."));
  printer.feed(1);
  printer.printBitmap(d_width,d_height,d_data);
  printer.justify('C');
  printer.println(F("91% confidence"));
  printer.println(F(" .............................."));
  printer.feed(1);
  printer.printBitmap(q_width,q_height,q_data);
  printer.feed(1);
  printer.setSize('S');
  printer.println(F(" .............................."));
  printer.feed(1);
  printer.println(F("Your anonymised data has paid"));
  printer.println(F("for the medical attention"));
  printer.println(F("you will receive."));
  printer.feed(5);
  printer.sleep();
  readTest = true;
  readyTest();
  setIdle();
}
 



