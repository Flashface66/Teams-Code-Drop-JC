package org.firstinspires.ftc.teamcode.WORLDS;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * This is the hardware for the 3981 teams robot or Worlds' bot.
 * This stroes the each harware component for the program and the configuration of them
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
    public Servo   Intake1    = null;
    public Servo   Intake2    = null;
    
    public HardwareMap hwmap;


    HardwareWorlds(){

    }

    public void init(HardwareMap thehwmap){
        hwmap = thehwmap;
        //Initializes the motors and servos for the robpot.
        FrontLeft    = hwmap.get(DcMotor.class, "Fleft");
        FrontRight   = hwmap.get(DcMotor.class, "Fright");
        BackRight    = hwmap.get(DcMotor.class, "BRight");
        BackLeft     = hwmap.get(DcMotor.class, "BLeft");
        Lift         = hwmap.get(DcMotor.class, "Lift");
        Deposit      = hwmap.get(DcMotor.class, "Deposit");
        Extend       = hwmap.get(DcMotor.class, "Extend");
        Spin1        = hwmap.get(Servo.class,   "Spin1");
        Spin2        = hwmap.get(Servo.class,   "Spin2");
        Intake1      = hwmap.get(Servo.class,   "Intake1");
        Intake2      = hwmap.get(Servo.class,   "Intake2");



        //Sets the right direction for the Motors in the drivetrain.
        FrontRight.setDirection(DcMotor.Direction.REVERSE);//Reverse
        FrontLeft.setDirection(DcMotor.Direction.FORWARD);//Forward
        BackLeft.setDirection(DcMotor.Direction.FORWARD);//Forward
        BackRight.setDirection(DcMotor.Direction.REVERSE);//Reverse


        //For autonomous make sure to set to Run using encoders.
        FrontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        FrontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BackLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BackRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        //The behaviour of the motors when it is set to 0 power.
        FrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Deposit.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Extend.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
}
