package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Servos {
    //This class will control the wobble grabber which has 1 motor and 1 servo
    //This class will include methods for opening and closing gripper & moving the arm up and down

    /*Public OpMode Members.*/
    public Servo Output = null;
//    public Servo gripWrist  = null;
//    public Servo gripServo = null;

    HardwareMap hwMap = null;

    /* Constructor */
    public Servos() {

    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;
        Output = hwMap.servo.get("Intake");

        // Define and Initialize Motors
//        gripWrist = hwMap.get(Servo.class, "grip_wrist");
//        gripServo = hwMap.get(Servo.class, "grip_servo");

        //Set initial positions of servos
//        gripWrist.setPosition(.23);
//        gripServo.setPosition(.77);
        Output.setPosition(0);


    }

    /**
     * This method lowers the grabber
     */
    public void changePos(double x){
        Output.setPosition(x);
    }
//    public void lowerGripper() {
//        gripWrist.setPosition(.54);
//    }

    /**
     * This method passes a position value to the grabber servo to open and close it.
     * @param position
     */
//    public void gripperPosition(double position) {
//        gripServo.setPosition(position);
//    }
}
