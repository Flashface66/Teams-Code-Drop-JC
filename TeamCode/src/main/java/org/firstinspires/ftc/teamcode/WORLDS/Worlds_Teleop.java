package org.firstinspires.ftc.teamcode.WORLDS;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


@TeleOp(name = "Worlds Robot", group = "JC")

public class Worlds_Teleop extends LinearOpMode {



    private final boolean shouldMecanumDrive = true;

    private HardwareWorlds RB  = new HardwareWorlds();


    @Override
    public void runOpMode() {
        RB.init(hardwareMap);


        waitForStart();
        while (opModeIsActive()) {
            Movement_System();

            LiftControl_System();

            MineralCollection_System();

            Arms();
        }
    }


    private void  Movement_System() {


        if (shouldMecanumDrive) {
            // Convert joysticks to desired motion.
            Mecanum.Motion motion = Mecanum.joystickToMotion(
                    gamepad1.left_stick_x, gamepad1.left_stick_y,
                    gamepad1.right_stick_x, gamepad1.right_stick_y);

            // Convert desired motion to wheel powers, with power clamping.
            Mecanum.Wheels wheels = Mecanum.motionToWheels(motion);
            RB.FrontLeft.setPower(wheels.frontLeft);
            RB.FrontRight.setPower(wheels.frontRight);
            RB.BackLeft.setPower(wheels.backLeft);
            RB.BackRight.setPower(wheels.backRight);
        }

    }

    private void LiftControl_System() {
        if (gamepad1.right_trigger > 0.2){
            RB.Lift.setPower(1);}
        else if (gamepad1.left_trigger > 0.2){
            RB.Lift.setPower(-1);}
        else{
            RB.Lift.setPower(0.0);}
    }

    private void MineralCollection_System() {
        //Rotational Collecting system
        if (gamepad2.a) {
            RB.Spin1.setPosition(0);
            RB.Spin2.setPosition(1);
        }
        if (gamepad2.b) {
            RB.Spin1.setPosition(1);
            RB.Spin2.setPosition(0);
        }
        if (gamepad2.x){
            RB.Spin1.setPosition(0.5);
            RB.Spin2.setPosition(0.5);

        }


    }

    private void Arms(){
        //Lifts to the Lander
         if (gamepad2.right_trigger >0.3){
           RB.Deposit.setPower(gamepad2.right_trigger);}
        else if (gamepad2.left_trigger >0.3){
            RB.Deposit.setPower(-gamepad2.left_trigger);}
        else{
            RB.Deposit.setPower(0.0);}

        //Extend into the Crater
        if (gamepad2.right_stick_y > 0.2){
            RB.Extend.setPower(1);}
        else if (gamepad2.right_stick_y < -0.2){
            RB.Extend.setPower(-1);}
        else{
            RB.Extend.setPower(0.0);}


        //Lifts Rotational Collection System
        if (gamepad2.dpad_up) {
            RB.Intake1.setPosition(0);
            RB.Intake2.setPosition(1);
        }
        if (gamepad2.dpad_down) {
            RB.Intake1.setPosition(1);
            RB.Intake2.setPosition(0);
        }

    }
}



