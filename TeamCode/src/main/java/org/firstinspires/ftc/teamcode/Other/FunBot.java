package org.firstinspires.ftc.teamcode.Other;


import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import java.util.jar.Attributes;


@TeleOp(name = "MemeBot")
@Disabled
public class FunBot extends LinearOpMode {
    private DcMotor FrontLeft  = null;
    private DcMotor FrontRight = null;
    private DcMotor BackRight  = null;
    private DcMotor BackLeft   = null;



    @Override
    public void runOpMode() {
        telemetry.addData("Robot: Memebot", " Initialized");

        /*
          Mapping each hardware device to the phone configuration file
         */

        BackRight  = hardwareMap.get(DcMotor.class,"BackRight");
        BackLeft   = hardwareMap.get(DcMotor.class,"BackLeft");

         /*+
        Setting the stops for the Robot.
            This makes the motor's activity, once their value is zero, to act as a brake.
        */
        BackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

         /*
         Setting the Direction of each motor where needed
*/
        BackLeft.setDirection(DcMotor.Direction.REVERSE);//Forward
        BackRight.setDirection(DcMotor.Direction.FORWARD );//Reverse



        telemetry.update();

        waitForStart();
        while (opModeIsActive()){
            Movements();
            telemetry.update();
        }

    }


    private void Movements(){



        if(gamepad1.right_trigger >0){
            BackRight.setPower(-gamepad1.right_trigger);
            BackLeft.setPower(-gamepad1.right_trigger);


        } else
        if(gamepad1.left_trigger > 0){
            BackRight.setPower(gamepad1.left_trigger);
            BackLeft.setPower(gamepad1.left_trigger);
        }

        else{
            BackRight.setPower(0.0);
            BackLeft.setPower(0.0);

        }


        if (gamepad1.right_stick_x >0){
            BackRight.setPower(-gamepad1.right_stick_x);
            BackLeft.setPower(gamepad1.right_stick_x);
        }else
        if (gamepad1.right_stick_x <0){
            BackRight.setPower(gamepad1.right_stick_x );
            BackLeft.setPower(-gamepad1.right_stick_x );
        }
        else {
            BackRight.setPower(0.0);
            BackLeft.setPower(0.0);
        }
    }






}

