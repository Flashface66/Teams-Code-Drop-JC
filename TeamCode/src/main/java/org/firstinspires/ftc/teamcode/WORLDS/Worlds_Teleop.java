package org.firstinspires.ftc.teamcode.WORLDS;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


@TeleOp(name = "Worlds Robot", group = "JC")

public class Worlds_Teleop extends LinearOpMode {



    private final boolean shouldMecanumDrive = true;

    private HardwareWorlds hw  = new HardwareWorlds();

    @Override
    public void runOpMode() {

        waitForStart();
        while (opModeIsActive()) {
            Movement();

            LiftControl();

            Intake();

            Arms();
        }
    }


    private void Movement() {
        if (shouldMecanumDrive) {
            // Convert joysticks to desired motion.
            Mecanum.Motion motion = Mecanum.joystickToMotion(
                    gamepad1.left_stick_x, gamepad1.left_stick_y,
                    gamepad1.right_stick_x, gamepad1.right_stick_y);

            // Convert desired motion to wheel powers, with power clamping.
            Mecanum.Wheels wheels = Mecanum.motionToWheels(motion);
            hw.FrontLeft.setPower(wheels.frontLeft);
            hw.FrontRight.setPower(wheels.frontRight);
            hw.BackLeft.setPower(wheels.backLeft);
            hw.BackRight.setPower(wheels.backRight);
        }

    }

    private void LiftControl() {
        if (gamepad1.right_trigger > 0.2){
            hw.Lift.setPower(1);}
        else if (gamepad1.left_trigger > 0.2){
            hw.Lift.setPower(-1);}
        else{
            hw.Lift.setPower(0.0);}
    }

    private void Intake() {
        //Spinny Part
        if (gamepad2.a) {
            hw.Spin1.setPosition(0);
            hw.Spin2.setPosition(1);
        }
        if (gamepad2.b) {
            hw.Spin1.setPosition(1);
            hw.Spin2.setPosition(0);
        }
        if (gamepad2.x){
            hw.Spin1.setPosition(0.5);
            hw.Spin2.setPosition(0.5);

        }

        //Lifts Spinny Part
        if (gamepad2.dpad_up) {
            hw.Intake1.setPosition(0);
            hw.Intake2.setPosition(1);
        }
        if (gamepad2.dpad_down) {
            hw.Intake1.setPosition(0);
            hw.Intake2.setPosition(1);
        }
    }

    private void Arms(){
        //Lifts to the Lander
         if (gamepad2.right_trigger >0.3){
           hw.Deposit.setPower(gamepad2.right_trigger);}
        else if (gamepad2.left_trigger >0.3){
            hw.Deposit.setPower(-gamepad2.left_trigger);}
        else{
            hw.Deposit.setPower(0.0);}

        //Extend into the Crater
        if (gamepad2.right_stick_y > 0.2){
            hw.Extend.setPower(1);}
        else if (gamepad2.right_stick_y < -0.2){
            hw.Extend.setPower(-1);}
        else{
            hw.Extend.setPower(0.0);}

            //Box Lift Servos
        if (gamepad2.right_bumper) {
            hw.BoxLift1.setPosition(0.2);
            hw.BoxLift2.setPosition(0.8);
        }
        if (gamepad2.left_bumper) {
            hw.BoxLift1.setPosition(0.8);
            hw.BoxLift2.setPosition(0.2);
        }

        }


    }



