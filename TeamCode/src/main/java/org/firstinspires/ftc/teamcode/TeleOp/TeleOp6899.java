
package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;


@TeleOp(name = "TeleOp")


public class TeleOp6899 extends LinearOpMode {

    private     DcMotor     FrontLeft;
    private     DcMotor     BackLeft;
    private     DcMotor     FrontRight;
    private     DcMotor     BackRight;
    private     DcMotor     ChainLift;
    private     DcMotor     HookLift;
    private     Servo       trayDispL;
    private     Servo       trayDispR;
    private     Servo       IntakeR;
    private     Servo       IntakeL;

    @Override
    public void runOpMode() {

        //Wheel Motors
        FrontLeft   = hardwareMap.dcMotor.get("FrontLeft");
        FrontRight  = hardwareMap.dcMotor.get("FrontRight");
        BackLeft    = hardwareMap.dcMotor.get("BackLeft");
        BackRight   = hardwareMap.dcMotor.get("BackRight");
        BackRight.setDirection (DcMotor.Direction.REVERSE);
        FrontRight.setDirection(DcMotor.Direction.REVERSE);

        //Collection Devices
        HookLift    = hardwareMap.dcMotor.get("HookLift");
        ChainLift   = hardwareMap.dcMotor.get("ChainLift");
        trayDispL   = hardwareMap.servo.get  ("TrayDispL");
        trayDispR   = hardwareMap.servo.get  ("TrayDispR");
        trayDispL.setDirection(Servo.Direction.REVERSE);

        //Lowest Intake Servos
        IntakeL     = hardwareMap.servo.get  ("IntakeL");
        IntakeR     = hardwareMap.servo.get  ("IntakeR");

        //Occurs when nothing is Pressed
        FrontLeft.setZeroPowerBehavior  (DcMotor.ZeroPowerBehavior.BRAKE);
        FrontRight.setZeroPowerBehavior (DcMotor.ZeroPowerBehavior.BRAKE);
        BackLeft.setZeroPowerBehavior   (DcMotor.ZeroPowerBehavior.BRAKE);
        BackRight.setZeroPowerBehavior  (DcMotor.ZeroPowerBehavior.BRAKE);

        waitForStart();

        while (opModeIsActive()) {

            MovementSys();

            CollectionSys();

            trayControlSys();

            HookSys();

            telemetry.update();
        }

    }

    private void MovementSys() {

        double Power1;
        double Power2;

        Power1 = Range.clip(gamepad1.left_stick_y, -.7, .7);
        Power2 = Range.clip(gamepad1.right_stick_y, -.7, .7);


        //Powers Set for Wheel Motors
        FrontLeft.setPower (Power1);
        BackLeft.setPower  (Power1);
        BackRight.setPower (-Power2);
        FrontRight.setPower(-Power2);


    }

    private void CollectionSys() {

        if (gamepad2.right_trigger > 0.2) {

            telemetry.addLine("Intake");
            IntakeL.setPosition(-1);
            IntakeR.setPosition(1);

        }
        else if (gamepad2.left_trigger > .2) {

            telemetry.addLine("Intake");
            IntakeL.setPosition(1);
            IntakeR.setPosition(-1);

        }
        else {

            IntakeL.setPosition(0.5);
            IntakeR.setPosition(0.5);

        }
    }

    private void trayControlSys() {
        if (gamepad2.dpad_up) {

            telemetry.addLine("ChainLift");
            ChainLift.setPower(1);
        }
        else if (gamepad2.dpad_down) {

            telemetry.addLine("ChainLift");
            ChainLift.setPower(-1);

        }
        else {

            ChainLift.setPower(0);

        }


        if (gamepad2.b) {

            telemetry.addLine("Tray Dropper");
            trayDispL.setPosition(1.0);
            trayDispR.setPosition(1.0);
        }
        else if (gamepad2.a) {

            telemetry.addLine("Tray Dropper");
            trayDispL.setPosition(-1.0);
            trayDispR.setPosition(-1.0);

        }
        else {

            trayDispL.setPosition(0.5);
            trayDispR.setPosition(0.5);

        }
    }

    private void HookSys() {
        if (gamepad2.y) {

            telemetry.addLine("Hook Lift");
            HookLift.setPower(1);
        }
        else if (gamepad2.x) {
            telemetry.addLine("Hook Lift");
            HookLift.setPower(-1);

        }
        else {
            HookLift.setPower(0);
        }
    }
}