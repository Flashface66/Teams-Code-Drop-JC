package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.WORLDS.Mecanum;


@TeleOp
public class Pancake extends LinearOpMode {
    private DcMotor FrontLeft  = null;
    private DcMotor FrontRight = null;
    private DcMotor BackRight  = null;
    private DcMotor BackLeft   = null;
    private DcMotor Lift       = null;
    private DcMotor Arm        = null;
    private DcMotor Extend     = null;
    private Servo   Intake     = null;
    private Servo   Lid        = null;


    private final boolean shouldMecanumDrive = true;

    @Override
    public void runOpMode() {
        telemetry.addData("Robot: Pancake", " Initialized");

        /*
          Mapping each hardware device to the phone configuration file
         */
        FrontLeft  = hardwareMap.get(DcMotor.class,"Fleft");
        FrontRight = hardwareMap.get(DcMotor.class,"Fright");
        BackRight  = hardwareMap.get(DcMotor.class,"BackRight");
        BackLeft   = hardwareMap.get(DcMotor.class,"BackLeft");
        Arm        = hardwareMap.get(DcMotor.class,"Arm");
        Lift       = hardwareMap.get(DcMotor.class,"Lift");
        Extend     = hardwareMap.get(DcMotor.class,"Extend");
        Intake     = hardwareMap.get(Servo.class, "Intake");
        Lid        = hardwareMap.get(Servo.class, "Lid");
         /*
        Setting the stops for the Robot.
            This makes the motor's activity, once their value is zero, to act as a brake.
        */
        FrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Extend.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
         /*
         Setting the Direction of each motor where needed
*/
        FrontRight.setDirection(DcMotor.Direction.FORWARD);//Reverse
        FrontLeft.setDirection(DcMotor.Direction.REVERSE);//Forward
        BackLeft.setDirection(DcMotor.Direction.FORWARD);//Forward
        BackRight.setDirection(DcMotor.Direction.REVERSE);//Reverse

         /*
         Initializing the Encoders.
            Resetting them and setting their run mode.
         */
        FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Extend.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        FrontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BackLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BackRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        Lift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        Arm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        Extend.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        telemetry.update();

        waitForStart();
        while (opModeIsActive()){
            Intake();
            Movements();
            telemetry.update();
        }

    }


    private void Movements(){
        if (shouldMecanumDrive) {
            // Convert joysticks to desired motion.
            Mecanum.Motion motion = Mecanum.joystickToMotion(
                    gamepad1.left_stick_x, gamepad1.left_stick_y,
                    gamepad1.right_stick_x, gamepad1.right_stick_y);

            // Convert desired motion to wheel powers, with power clamping.
            Mecanum.Wheels wheels = Mecanum.motionToWheels(motion);
            FrontLeft.setPower(wheels.frontLeft);
            FrontRight.setPower(wheels.frontRight);
            BackLeft.setPower(wheels.backLeft);
            BackRight.setPower(wheels.backRight);
        }

    }


    private void Intake(){
        //Condition for the arm for the servo arm's motor.
        if (gamepad2.left_trigger >0.9){
            Arm.setPower(gamepad2.left_trigger);
        }else
            if (gamepad2.right_trigger > 0.9){
                Arm.setPower(-gamepad2.right_trigger);
            }else{
                Arm.setPower(0.0);
            }
        /// Determining the power of the Lift if the left trigger passes .6
        if (gamepad1.left_trigger >0.6){
            Lift.setPower(gamepad1.left_trigger);
        }else
            // Having it go in the other direction.
        if (gamepad1.right_trigger > 0.6){
            Lift.setPower(-gamepad1.right_trigger);
        }else{
            Lift.setPower(0.0);
        }

        if (gamepad2.left_stick_y !=0){
            Extend.setPower(gamepad2.left_stick_y);
        }else
            if (gamepad1.right_stick_y !=0){
                Extend.setPower(-gamepad2.left_stick_y );
            }else{
                Extend.setPower(0.0);
            }

        if (gamepad2.dpad_left){
            Intake.setPosition(0.7);
        }
        if (gamepad2.dpad_right){
            Intake.setPosition(0.3);
        }
        if (gamepad2.dpad_down){
            Intake.setPosition(0.5);
        }


        if (gamepad2.a){
            Lid.setPosition(0.9);
        }


        if (gamepad2.b){
            Lid.setPosition(0.5);
        }


    }

}
