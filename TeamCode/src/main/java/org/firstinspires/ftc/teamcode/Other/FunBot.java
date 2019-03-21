package org.firstinspires.ftc.teamcode.Other;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import java.util.jar.Attributes;


@TeleOp(name = "MemeBot")

public class FunBot extends LinearOpMode {
    private DcMotor FrontLeft  = null;
    private DcMotor FrontRight = null;
    private DcMotor BackRight  = null;
    private DcMotor BackLeft   = null;



    private double Overdrive = 0;
    private double x = 0;






    @Override
    public void runOpMode() {
        telemetry.addData("Robot: Memebot", " Initialized");

        /*
          Mapping each hardware device to the phone configuration file
         */
        FrontLeft  = hardwareMap.get(DcMotor.class,"Fleft");
        FrontRight = hardwareMap.get(DcMotor.class,"Fright");
        BackRight  = hardwareMap.get(DcMotor.class,"BackRight");
        BackLeft   = hardwareMap.get(DcMotor.class,"BackLeft");

         /*+
        Setting the stops for the Robot.
            This makes the motor's activity, once their value is zero, to act as a brake.
        */
        FrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

         /*
         Setting the Direction of each motor where needed
*/
        FrontRight.setDirection(DcMotor.Direction.FORWARD);//Reverse
        FrontLeft.setDirection(DcMotor.Direction.REVERSE);//Forward
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
            FrontLeft.setPower(Overdrive);
            FrontRight.setPower(Overdrive);
            BackRight.setPower(Overdrive);
            BackLeft.setPower(Overdrive);


        } else
            if(gamepad1.left_trigger > 0){
            FrontLeft.setPower(-Overdrive);
            FrontRight.setPower(-Overdrive);
            BackRight.setPower(-Overdrive);
            BackLeft.setPower(-Overdrive);
        }

        else{
            FrontLeft.setPower(0.0);
            FrontRight.setPower(0.0);
            BackRight.setPower(0.0);
            BackLeft.setPower(0.0);

        }

        if (gamepad1.a){
            Overdrive=Overdrive+0.1;
        }

        if (gamepad1.b){
            Overdrive=Overdrive -0.1;
        }

        if (gamepad1.right_stick_x >0){
            FrontLeft.setPower(Overdrive);
            FrontRight.setPower(-Overdrive);
            BackRight.setPower(-Overdrive);
            BackLeft.setPower(Overdrive);
        }else
            if (gamepad1.right_stick_x <0){
                FrontLeft.setPower(-Overdrive);
                FrontRight.setPower(Overdrive);
                BackRight.setPower(Overdrive);
                BackLeft.setPower(-Overdrive);
            }
            else {
                FrontLeft.setPower(0.0);
                FrontRight.setPower(0.0);
                BackRight.setPower(0.0);
                BackLeft.setPower(0.0);
            }
    }






    }


