package org.firstinspires.ftc.teamcode.WORLDS;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * This is the hardware for the 3981 robot or Worlds' bot.
 * Add the necessary hardware required here following the format given
 * Do not edit unless you are sure of what you're doing - Chavaughn
 */

public class  HardwareWorlds {

    // TODO add the different components of the robots
    public DcMotor FrontLeft  = null;
    public DcMotor FrontRight = null;
    public DcMotor BackRight  = null;
    public DcMotor BackLeft   = null;
    public DcMotor Lift       = null;
    public DcMotor Deposit    = null;
    public DcMotor Extend     = null;
    public Servo   Spin1      = null;
    public Servo   Spin2      = null;
    public Servo   BoxLift1   = null;
    public Servo   BoxLift2   = null;
    public Servo   Intake1    = null;
    public Servo   Intake2    = null;
    
    public HardwareMap hwmap;
    
    HardwareWorlds(){

    }

    public void init(HardwareMap thehwmap){
        hwmap = thehwmap;
        //TODO Initialize new hardware as given below with the drive-train.
        FrontLeft  = hwmap.get(DcMotor.class, "Fleft");
        FrontRight = hwmap.get(DcMotor.class, "Fright");
        BackRight  = hwmap.get(DcMotor.class, "BRight");
        BackLeft   = hwmap.get(DcMotor.class, "BLeft");
        Lift       = hwmap.get(DcMotor.class, "Lift");
        Deposit    = hwmap.get(DcMotor.class, "Deposit");
        Extend     = hwmap.get(DcMotor.class, "Extend");
        BoxLift1   = hwmap.get(Servo.class,   "BoxLift1");
        BoxLift2   = hwmap.get(Servo.class,   "BoxLift2");
        Spin1      = hwmap.get(Servo.class,   "Spin1");
        Spin2      = hwmap.get(Servo.class,   "Spin2");
        Intake1    = hwmap.get(Servo.class,   "Intake1");
        Intake2    = hwmap.get(Servo.class,   "Intake2");


        //TODO Make sure directions are correct.
        FrontRight.setDirection(DcMotor.Direction.FORWARD);//Reverse
        FrontLeft.setDirection(DcMotor.Direction.FORWARD);//Forward
        BackLeft.setDirection(DcMotor.Direction.REVERSE);//Forward
        BackRight.setDirection(DcMotor.Direction.FORWARD);//Reverse


        //TODO For autonomous make sure to set to Run using encoders
        FrontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        FrontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BackLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BackRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        //The behaviour of the motors when it is set to 0 power
        //TODO Add the rest of the components
        FrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Deposit.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Extend.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
}
