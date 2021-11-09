package org.firstinspires.ftc.teamcode;

import android.widget.Spinner;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="Mecanum_Control_V2", group="Testier")
public class MecanumControlV2 extends OpMode {
/*
This entire program acts like a psuedo-main class, Put all code during TeleOp in this class. 
*/
    MecanumDrive robot    = new MecanumDrive();
//    Motors shooter       = new Motors();
//    Intake  intake        = new Intake();
    Servos spinny = new Servos();
    MotorControl Motor = new MotorControl();

    double driveSpeed;
    double turnSpeed;
    double direction;
    double shooterPower = -1;

    boolean isSpinnerOn  = false;
    boolean isSpinnerOf = true;
    boolean isIntakeOn  = false;
    boolean isIntakeOf = true;
    boolean wasPowerIncreased;
    boolean wasPowerDecreased;
    double spinnerChange = .07;//5 increments of change in power of shooter, with given code at bottom(Lower=-1;Upper=-.65)

    boolean highGoalMode = true;
    boolean powerShotMode = false;

    boolean autoPower = true;
    boolean manualPower = false;

//    private ElapsedTime period  = new ElapsedTime();
//    private double runtime = 0;

    @Override
    public void init() {
        robot.init(hardwareMap);
        Motor.init(hardwareMap);
        spinny.init(hardwareMap);
//        grabber.init(hardwareMap);

        msStuckDetectInit = 18000;
        msStuckDetectLoop = 18000;

        telemetry.addData("Hello","be ready");
        telemetry.addData("Loop_Timeout",msStuckDetectLoop);
        telemetry.update();
    }
    @Override
    public void loop() {
        telemetry.addData("isSpinnerOn", isSpinnerOn);
        telemetry.addData("highGoalMode", highGoalMode);
        telemetry.addData("Automatic Power", autoPower);
        telemetry.addData("Spinner Power", Motor.rotaterPower());
        telemetry.addData("Intake Power", Motor.intakeOnePower());
        //telemetry.addData("Drive Speed",driveSpeed);
        //telemetry.addData("Direction",direction);
        //telemetry.addData("Turn Speed", turnSpeed);
        //telemetry.addData("LB",robot.getLBencoder());
        //telemetry.addData("RB",robot.getRBencoder());
        //telemetry.addData("LF",robot.getLFencoder());
        //telemetry.addData("RF",robot.getRFencoder());
        telemetry.update();

        //Speed control (turbo/slow mode) and direction of stick calculation
        if (gamepad1.left_bumper) {
            driveSpeed = Math.hypot(-gamepad1.left_stick_x, -gamepad1.left_stick_y);
            direction = Math.atan2(gamepad1.left_stick_y, -gamepad1.left_stick_x) - Math.PI / 4;
            turnSpeed = gamepad1.right_stick_x;
        }else if (gamepad1.right_bumper) {
            driveSpeed = Math.hypot(-gamepad1.left_stick_x, -gamepad1.left_stick_y)*.4;
            direction = Math.atan2(gamepad1.left_stick_y, -gamepad1.left_stick_x) - Math.PI / 4;
            turnSpeed = gamepad1.right_stick_x*.4;
        }else {
            driveSpeed = Math.hypot(-gamepad1.left_stick_x, -gamepad1.left_stick_y)*.7;
            direction = Math.atan2(gamepad1.left_stick_y, -gamepad1.left_stick_x) - Math.PI / 4;
            turnSpeed = gamepad1.right_stick_x*.7;
        }
        //set power and direction of drive motors
        if (turnSpeed == 0) {
            robot.MecanumController(driveSpeed,direction,0);
        }

        //control of turning
        if (gamepad1.right_stick_x != 0 && driveSpeed == 0) {
            robot.leftFront.setPower(-turnSpeed);
            robot.leftBack.setPower(-turnSpeed);
            robot.rightFront.setPower(turnSpeed);
            robot.rightBack.setPower(turnSpeed);
        }

        //Turn Output on
        if (gamepad2.a && !isSpinnerOn) {
            isSpinnerOn = true;
        }else if (!gamepad2.a && isSpinnerOn) {
            isSpinnerOf = false;
        }
        //Turn Output off
        if (gamepad2.a && !isSpinnerOf) {
            isSpinnerOn = false;
        }else if (!gamepad2.a && !isSpinnerOn) {
            isSpinnerOf = true;
        }


        //Turn Intake on
        if (gamepad1.a && !isIntakeOn) {
            isIntakeOn = true;
        }else if (!gamepad2.a && isIntakeOn) {
            isIntakeOf = false;
        }
        //Turn Intake off
        if (gamepad1.a && !isIntakeOf) {
            isIntakeOn = false;
        }else if (!gamepad1.a && !isIntakeOn) {
            isIntakeOf = true;
        }
        //Motors into PowerShot mode
//        if (gamepad2.b && !powerShotMode) {
//            powerShotMode = true;
//        }else if (!gamepad2.b && powerShotMode) {
//            highGoalMode = false;
//        }
//        //Motors into HighGoal mode
//        if (gamepad2.b && !highGoalMode) {
//            powerShotMode = false;
//        }else if (!gamepad2.b && !powerShotMode) {
//            highGoalMode = true;
//        }
//        //Motors into Manual mode
//        if (gamepad2.x && !manualPower) {
//            manualPower = true;
//        }else if (!gamepad2.x && manualPower) {
//            autoPower = false;
//        }
//        //Motors into Automatic mode
//        if (gamepad2.x && !autoPower) {
//            manualPower = false;
//        }else if (!gamepad2.x && !manualPower) {
//            autoPower = true;
//        }

        //Set Power of the intake

        if(isIntakeOn){
            Motor.intakeOnePower(.5);
        }
        else{
            Motor.intakeOnePower(0);
        }

        //Set power of the output

        if (isSpinnerOn) {
            if (isSpinnerOn) {
                Motor.rotaterPower(shooterPower);
            } else {
                Motor.rotaterPower(0);
            }

            } else {
                Motor.rotaterPower(0);
            }

            //Change shooter power
            if (gamepad2.dpad_up) {
                wasPowerIncreased = true;
        }else if (!gamepad2.dpad_up && wasPowerIncreased) {
            shooterPower -= spinnerChange;
            if (shooterPower <= -1.01) {
                shooterPower = -.65;
            }
            wasPowerIncreased = false;
        }
        if (gamepad2.dpad_down) {
            wasPowerDecreased = true;
        }else if (!gamepad2.dpad_down && wasPowerDecreased) {
            shooterPower += spinnerChange;
            if (shooterPower >= -.62) {
                shooterPower = -1;
            }
            wasPowerDecreased = false;
        }

        //Control intake
//        intake.intakePower(-gamepad2.left_stick_y);

        //Control shooter servo
//        if (gamepad2.left_bumper) {
//            shooter.shooterSwitch.setPosition(.63);
//        }else {
//            shooter.shooterSwitch.setPosition(1);
//        }

        //Control Output Servos, initial middle and final positions
        if (gamepad2.x) {
            spinny.Output.setPosition(0);
        }
        else if(gamepad2.y){
            spinny.Output.setPosition(.55);
        }
        else if (gamepad2.b) {
            spinny.Output.setPosition(.85);
        }

        //Control grabber servo
//        if (gamepad1.b) {
//            spinny.gripperPosition(.77);

//        }else if (gamepad1.dpad_up) {
//            spinny.gripperPosition(0);
//        }
    }
}
