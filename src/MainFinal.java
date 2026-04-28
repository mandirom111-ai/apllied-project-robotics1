package src;

import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.lcd.LCD;
import lejos.hardware.Button;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;


class SharedData {
    public static float distance = 0.0f;
    public static int lightIntensity = 0;
    public static boolean objectDetected = false; 
}

// Thread foe Ultrasonic Sensor
class UltrasonicReader implements Runnable {
    @Override
    public void run() {
        EV3UltrasonicSensor ultrasonicSensor = new EV3UltrasonicSensor(SensorPort.S2);
        SampleProvider distanceMode = ultrasonicSensor.getDistanceMode();
        float[] sample = new float[distanceMode.sampleSize()];
    
        while (!Button.ESCAPE.isDown()) {
            distanceMode.fetchSample(sample, 0);
            SharedData.distance = sample[0];
            
            // Check for obstacles within 15cm
            if (SharedData.distance < 0.15f) {
                SharedData.objectDetected = true;
            } else {
                SharedData.objectDetected = false;
            }

            Delay.msDelay(50);
        }
        ultrasonicSensor.close();
    }
}

// Thread for Light Sensor
class LightSensorReader implements Runnable {
    @Override
    public void run() {
        EV3ColorSensor colorSensor = new EV3ColorSensor(SensorPort.S4);
        SampleProvider lightMode = colorSensor.getRedMode();
        float[] sample = new float[lightMode.sampleSize()];
        
        while (!Button.ESCAPE.isDown()) {
            lightMode.fetchSample(sample, 0);
            SharedData.lightIntensity = (int)(sample[0] * 100);
            
            Delay.msDelay(50);
        }
        colorSensor.close();
    }
}

public class MainFinal {
    public static void main(String[] args) {
        LCD.clear();

        // Initialize and start the sensor threads
        Thread ultrasonicThread = new Thread(new UltrasonicReader());
        Thread lightThread = new Thread(new LightSensorReader());

        ultrasonicThread.start();
        lightThread.start();

        
        while (!Button.ESCAPE.isDown()) {
            LCD.clear();
            LCD.drawString("Distance: " + SharedData.distance, 0, 0);
            LCD.drawString("Light: " + SharedData.lightIntensity + "%", 0, 1);
            
            if (SharedData.objectDetected) {
                LCD.drawString("STATUS: STOPPED", 0, 3);
            } else {
                LCD.drawString("STATUS: RUNNING", 0, 3);
            }
            
            Delay.msDelay(150);
        }
        
        LCD.clear();
    }
}
