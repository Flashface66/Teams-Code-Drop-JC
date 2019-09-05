package org.firstinspires.ftc.teamcode.Other;


import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.LED;

import java.util.jar.Attributes;


@TeleOp(name = "MemeBot")

public class FunBot extends LinearOpMode {
    private DcMotor Right = null;
    private DcMotor Left = null;

    double leftPower;
    double rightPower;



    @Override
    public void runOpMode() {
        telemetry.addData("Robot: Memebot", " Initialized");

        /*
          Mapping each hardware device to the phone configuration file
         */

        Right = hardwareMap.get(DcMotor.class,"Right");
        Left = hardwareMap.get(DcMotor.class,"Left");


         /*+
        Setting the stops for the Robot.
            This makes the motor's activity, once their value is zero, to act as a brake.
        */
        Right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Right.setDirection(DcMotor.Direction.REVERSE);
        Left.setDirection(DcMotor.Direction.FORWARD);





        telemetry.update();

        waitForStart();
        while (opModeIsActive()){



            Movements();
            telemetry.update();
        }

    }

        private void Movements() {
        //leftPower  = gamepad1.left_stick_y  ;
        //rightPower = gamepad1.right_stick_y ;

        leftPower  = gamepad1.left_stick_y + gamepad1.left_stick_x;
        rightPower = gamepad1.left_stick_y - gamepad1.left_stick_x;


        Right.setPower(rightPower);
        Left.setPower(leftPower);

    }


}

