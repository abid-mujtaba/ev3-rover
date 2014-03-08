/*
 *  Copyright 2014 Abid Hasan Mujtaba
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.Motor;
import lejos.robotics.RegulatedMotor;

/**
 * Main (robotic) class for the Rover robot.
 *
 * The "Rover" consists of two Primary motors to drive it in a differential fashion.
 */



public class Rover
{
    static RegulatedMotor motorR = Motor.A;
    static RegulatedMotor motorL = Motor.D;


    public static void main(String[] args)
    {
        // Initialize LCD screen and set motor speeds
        initialize();

        // The pattern we will implement is forward, right, backward, left.
        forward();
        delay(3000);

        right_forward();
        delay(1600);

        forward();
        delay(5000);

        left_forward();
        delay(1600);

        backward();
        delay(3000);

        stop();
        delay(3000);
    }


    private static void initialize()
    {
        LCD.clear();

        motorR.setSpeed(400);
        motorL.setSpeed(400);
    }


    private static void stop()          // Stops both motors to stop the rover
    {
        print("STOP");

        motorR.stop();
        motorL.stop();
    }


    private static void forward()
    {
        print("FORWARD");

        motorR.forward();
        motorL.forward();
    }


    private static void backward()
    {
        print("BACKWARD");

        motorR.backward();
        motorL.backward();
    }


    private static void right_forward()
    {
        print("FORWARD RIGHT");

        motorR.stop();
        motorL.forward();
    }


    private static void left_forward()
    {
        print("FORWARD LEFT");

        motorL.stop();
        motorR.forward();
    }


    private static void right_backward()
    {
        print("BACKWARD RIGHT");

        motorR.stop();
        motorL.backward();
    }


    private static void left_backward()
    {
        print("BACKWARD_LEFT");

        motorL.stop();
        motorR.backward();
    }


    private static void print(String message)
    {
        LCD.drawString(message, 5, 5);
    }


    private static void delay(long interval)
    {
        try
        {
            Thread.sleep(interval);
        }
        catch (InterruptedException e) {}
    }
}
