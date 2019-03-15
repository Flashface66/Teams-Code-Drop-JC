package org.firstinspires.ftc.teamcode.WORLDS;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Worlds Robot", group = "JC")

public class Worlds_Teleop extends LinearOpMode {

    private HardwareWorlds Hardware = new HardwareWorlds();

    private boolean shouldMecanumDrive = true;

    @Override
    public void runOpMode(){

         // Using the Hardware class HardwareWorlds to initialize the hardware of the robot
        Hardware.init(hardwareMap);

        waitForStart();
        while (opModeIsActive()){
            Movement();
        }
    }


    private void Movement(){
        /*Button used to switch between mecanum driving program and a normal driving program
            without all the actual function of mecanum
        */
        if (gamepad1.right_bumper){
            shouldMecanumDrive = false;
        }
        if (!gamepad1.right_bumper){
            shouldMecanumDrive = true;
        }
        if (shouldMecanumDrive) {
            // Convert joysticks to desired motion.
            Mecanum.Motion motion = Mecanum.joystickToMotion(
                    gamepad1.left_stick_x, gamepad1.left_stick_y,
                    gamepad1.right_stick_x, gamepad1.right_stick_y);

            // Convert desired motion to wheel powers, with power clamping.
            Mecanum.Wheels wheels = Mecanum.motionToWheels(motion);
            Hardware.FrontLeft.setPower(wheels.frontLeft);
            Hardware.FrontRight.setPower(wheels.frontRight);
            Hardware.BackLeft.setPower(wheels.backLeft);
            Hardware.BackRight.setPower(wheels.backRight);
        }
        //If the right bumper is pressed
        if (!shouldMecanumDrive){
            if (gamepad1.right_stick_x == 0) {
                Hardware.FrontLeft.setPower(gamepad1.left_stick_y);
                Hardware.FrontRight.setPower(gamepad1.left_stick_y);
                Hardware.BackLeft.setPower(gamepad1.left_stick_y);
                Hardware.BackRight.setPower(gamepad1.left_stick_y);
            }else if (gamepad1.left_stick_x > 0.3 || gamepad1.left_stick_x < -0.3){
                Hardware.FrontLeft.setPower(-gamepad1.left_stick_y);
                Hardware.FrontRight.setPower(gamepad1.left_stick_y);
                Hardware.BackLeft.setPower(gamepad1.left_stick_y);
                Hardware.BackRight.setPower(-gamepad1.left_stick_y);
            }else {
                Hardware.FrontLeft.setPower(gamepad1.left_stick_y);
                Hardware.FrontRight.setPower(-gamepad1.left_stick_y);
                Hardware.BackLeft.setPower(gamepad1.left_stick_y);
                Hardware.BackRight.setPower(-gamepad1.left_stick_y);
            }
        }


    }
}
