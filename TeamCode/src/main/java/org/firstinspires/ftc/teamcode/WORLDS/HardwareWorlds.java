package org.firstinspires.ftc.teamcode.WORLDS;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * This is the hardware for the 6899 robot or Myles' bot.
 * Add the necessary hardware required here following the format given
 * Do not edit unless you are sure of what you're doing - Chavaughn
 */
public class HardwareWorlds {

    // TODO add the different components of the robots
    public DcMotor   FrontLeft      = null;
    public DcMotor   FrontRight     = null;
    public DcMotor   BackLeft       = null;
    public DcMotor   BackRight      = null;

    HardwareWorlds(){

    }

    public void init(HardwareMap thehwmap){

        //TODO Initialize new hardware as given below with the drive-train.
        FrontLeft = thehwmap.get(DcMotor.class,"FrontLeft");
        FrontRight = thehwmap.get(DcMotor.class,"FrontRight");
        BackLeft = thehwmap.get(DcMotor.class,"BackLeft");
        BackRight = thehwmap.get(DcMotor.class,"BackRight");

        //TODO Make sure directions are correct.
        FrontRight.setDirection(DcMotor.Direction.FORWARD);//Reverse
        FrontLeft.setDirection(DcMotor.Direction.FORWARD);//Forward
        BackLeft.setDirection(DcMotor.Direction.FORWARD);//Forward
        BackRight.setDirection(DcMotor.Direction.REVERSE);//Reverse

        //TODO For autonomous make sure to set to Run using encoders
        FrontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        FrontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BackLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BackRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        //The behaviour of the motors when it is set to 0 power
        //TODO Add the rest of the components
        FrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //Setting the initial power of the Motors to 0.
        FrontLeft.setPower(0);
        FrontRight.setPower(0);
        BackLeft.setPower(0);
        BackLeft.setPower(0);



    }
}
