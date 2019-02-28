package org.firstinspires.ftc.teamcode.TeleOp6899;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * This is the hardware for the 6899 robot or Myles' bot.
 * Add the necessary hardware required here following the format given
 * Do not edit unless you are sure of what you're doing - Chavaughn
 */
public class Hardware6899 {

    // TODO add the different components of the robots
    public DcMotor   FrontLeft      = null;
    public DcMotor   FrontRight     = null;
    public DcMotor   BackLeft       = null;
    public DcMotor   BackRight      = null;
    public DcMotor   ChainLift      = null;
    public DcMotor   HookLift       = null;

    public Servo     trayDispL      = null;
    public Servo     trayDispR      = null;
    public Servo     IntakeL        = null;
    public Servo     IntakeR        = null;

    HardwareMap      hwmap          = null;

    public Hardware6899(){

    }

    public void init(HardwareMap thehwmap){
        hwmap = thehwmap;

        //TODO Initialize them as given below with the drive-train
        FrontLeft = hwmap.get(DcMotor.class,"FrontLeft");
        FrontRight = hwmap.get(DcMotor.class,"FrontRight");
        BackLeft = hwmap.get(DcMotor.class,"BackLeft");
        BackRight = hwmap.get(DcMotor.class,"BackRight");

        FrontRight.setDirection(DcMotor.Direction.REVERSE);
        BackRight.setDirection(DcMotor.Direction.REVERSE);

        //Collection Devices
        HookLift = hwmap.get(DcMotor.class,"HookLift");
        ChainLift = hwmap.get(DcMotor.class,"ChainLift");
        trayDispL = hwmap.get(Servo.class,"TrayDispL");
        trayDispR = hwmap.get(Servo.class,"TrayDispR");
        trayDispL.setDirection(Servo.Direction.REVERSE);

        //Lowest Intake Servos
        IntakeL = hwmap.get(Servo.class,"IntakeL");
        IntakeR = hwmap.get(Servo.class, "IntakeR");

        //The Encoder settings - Current run using them
        FrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        FrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        ChainLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //The behaviour of the motors when it is set to 0 power
        FrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        ChainLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        FrontLeft.setPower(0);
        FrontRight.setPower(0);
        BackLeft.setPower(0);
        BackLeft.setPower(0);
    }
}
