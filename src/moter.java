package src;

import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.utility.Delay;
import lejos.hardware.Button;

public class moter implements Runnable {
    @Override
    public void run() {
        // creating motor objects
        EV3LargeRegulatedMotor leftMotor = new EV3LargeRegulatedMotor(MotorPort.A);
        EV3LargeRegulatedMotor rightMotor = new EV3LargeRegulatedMotor(MotorPort.D);
        int baseSpeed = 250;

        System.out.println("motor test starting...");

        while (!Button.ESCAPE.isDown()) {
            
            
            if (SharedData.objectDetected) {
                // Stop and perform avoidance maneuver
                leftMotor.stop(true); 
                rightMotor.stop();
                
                leftMotor.setSpeed(baseSpeed); 
                rightMotor.setSpeed(baseSpeed / 2);
                leftMotor.forward(); 
                rightMotor.forward();
                
                
                Delay.msDelay(2500); 
            } else {
                
                if (SharedData.lightIntensity < 25) { 
                    leftMotor.setSpeed(baseSpeed); 
                    rightMotor.setSpeed(baseSpeed / 5);
                } else {
                    leftMotor.setSpeed(baseSpeed / 5); 
                    rightMotor.setSpeed(baseSpeed);
                }
                leftMotor.forward(); 
                rightMotor.forward();
                
            }
            Delay.msDelay(10);
        }
            


        // Stop motors
        leftMotor.stop();
        rightMotor.stop();

        // Close motors
        leftMotor.close();
        rightMotor.close();

        System.out.println("test completed. Motors shoulve been working smmoothly");
    }
}
