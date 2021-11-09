package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.VoltageSensor;
public class MotorControl {
    public DcMotor rotater  = null;
    HardwareMap hwMap = null;
    public DcMotor intakeOne  = null;
    public MotorControl() {
        //Constructor
    }
    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;

        // Define and Initialize Motor
        rotater = hwMap.get(DcMotor.class, "rotater");
        intakeOne = hwMap.get(DcMotor.class, "intakeOne");
        //define motor direction
        rotater.setDirection(DcMotor.Direction.REVERSE);
        intakeOne.setDirection(DcMotor.Direction.REVERSE);

        rotater.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        intakeOne.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        // Set all motors to zero power
        rotater.setPower(0);
        intakeOne.setPower(0);

        // Set all motors to run without encoders.
        // May want to use RUN_USING_ENCODERS if encoders are installed.
        rotater.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        intakeOne.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    }
    public void rotaterPower(double power) {
        rotater.setPower(-power);
    }
    public double rotaterPower() {

        return rotater.getPower();
    }
    public void intakeOnePower(double power) {

        intakeOne.setPower(power);
    }
    public double intakeOnePower() {

        return intakeOne.getPower();
    }
}
