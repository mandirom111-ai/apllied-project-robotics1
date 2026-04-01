package src;

import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.utility.Delay;

public class moter {
    public static void main(String[] args) {
        // creating motor objects
        EV3LargeRegulatedMotor leftMotor = new EV3LargeRegulatedMotor(MotorPort.A);
        EV3LargeRegulatedMotor rightMotor = new EV3LargeRegulatedMotor(MotorPort.D);

        System.out.println("motor test starting...");

        // move motors forward
        leftMotor.setSpeed(360);   // degrees per second
        rightMotor.setSpeed(360);
        leftMotor.forward();
        rightMotor.forward();
        Delay.msDelay(3000);       // 3 secs

        leftMotor.rotate(720);
        rightMotor.rotate(-720);

        LCD.drawString("Reached halfway, returning to base", 0, 1);

        // Move motors backward
        // leftMotor.backward();
        // rightMotor.backward();
        // Delay.msDelay(4000);    
        
        leftMotor.forward();
        rightMotor.forward();
        Delay.msDelay(3000);       // 3 secs


        // Stop motors
        leftMotor.stop();
        rightMotor.stop();

        // Close motors
        leftMotor.close();
        rightMotor.close();

        System.out.println("test completed. Motors shoulve been working");
    }
}