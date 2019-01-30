package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

@TeleOp
public class ProdigyTesting extends LinearOpMode {
    private DcMotor FrontLeft, FrontRight,
            BackRight, BackLeft, Lift,
            Joint,Joint2;
    private Servo Phone, ClawL,ClawR,Latch,
            IntakeR,IntakeL;
    private double FRP,FLP,BRP,BLP;
    private double x,y,CL = 0,CR = 0;
    private final boolean shouldMecanumDrive = true;

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData("Robot:", " Initialized");

        /*
          Mapping each hardware device to the phone configuration file
         */
        FrontLeft = hardwareMap.dcMotor.get("Fleft");
        FrontRight = hardwareMap.dcMotor.get("Fright");
        BackRight = hardwareMap.dcMotor.get("BackRight");
        BackLeft = hardwareMap.dcMotor.get("BackLeft");
        Joint = hardwareMap.dcMotor.get("Joint");
        Joint2 = hardwareMap.dcMotor.get("Joint2");
        Lift = hardwareMap.dcMotor.get("Lift");
        IntakeL = hardwareMap.servo.get("IntakeL");
        IntakeR = hardwareMap.servo.get("IntakeR");
        Latch = hardwareMap.servo.get("Latch");
         /*
        Setting the stops for the Robot.
            This makes the motor's activity, once their value is zero, to act as a brake.
        */
        FrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Joint.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Joint2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
         /*
         Setting the Direction of each motor where needed
         */
        FrontRight.setDirection(DcMotorSimple.Direction.FORWARD);//Reverse
        FrontLeft.setDirection(DcMotorSimple.Direction.REVERSE);//Forward
        BackLeft.setDirection(DcMotorSimple.Direction.REVERSE);//Forward
        BackRight.setDirection(DcMotorSimple.Direction.FORWARD);//Reverse
         /*
         Initializing the Encoders.
            Resetting them and setting their run mode.
         */
        FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Joint.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Joint2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        FrontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BackLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BackRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        Lift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        Joint.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        telemetry.update();

        waitForStart();
        while (opModeIsActive()){

            Servos();
            Movements();
            telemetry.update();
        }

    }

    private void Servos(){
        /*
        Servo movements for the robot

        Using the dpad to incrementally map the position of the Phone's servo with each press of the
        dpad.
         */
        if (gamepad2.a){
            IntakeL.setPosition(x);
            IntakeR.setPosition(y);
            x = Range.clip(x+0.08,0.002,1);
            y = Range.clip(y-0.08,0.002,1);
            telemetry.addData("Position",IntakeR.getPosition());
            telemetry.addData("Position",IntakeL.getPosition());
        }
        if (gamepad2.b){
            IntakeL.setPosition(x);
            IntakeR.setPosition(y);
            x = Range.clip(x-0.08,0.002,1);
            y = Range.clip(y+0.08,0.002,1);
            telemetry.addData("Position",IntakeL.getPosition());
            telemetry.addData("Position",IntakeR.getPosition());
        }if (gamepad2.y){
            IntakeL.setPosition(x);
            IntakeR.setPosition(y);
            x = Range.clip(x+0.08,0.002,1);
            telemetry.addData("Position",IntakeL.getPosition());
            telemetry.addData("Position",IntakeR.getPosition());
        }if (gamepad2.x){
            IntakeL.setPosition(x);
            IntakeR.setPosition(y);
            y = Range.clip(y-0.08,0.002,1);
            telemetry.addData("Position",IntakeL.getPosition());
            telemetry.addData("Position",IntakeR.getPosition());
        }
        /*
        Similar to the movement of the phone's servo as it incrementally increases and decreases the
        position of the servo on the Claw Arm.
         */
        /*
        The Latch is the servo that keeps the robot connected to the launcher.
         */
        if (gamepad1.y){
            Latch.setPosition(0);
        }
        if (gamepad1.x){
            Latch.setPosition(1);
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
        // Determining the power of the Lift if the left trigger passes .6
        if (gamepad1.left_trigger >0.6){
            Lift.setPower(gamepad1.left_trigger);
        }else
            // Having it go in the other direction.
            if (gamepad1.right_trigger > 0.6){
            Lift.setPower(-gamepad1.right_trigger);
            }else{
                Lift.setPower(0.0);
            }
        //Condition for the arm for the servo arm's motor.
//        if (gamepad2.right_bumper){
//            Joint.setPower(0.6);
//        }else
//        if (gamepad2.left_bumper){
//            Joint.setPower(-0.6);
//        }else
//        {
//            Joint.setPower(0.0);
//        }
        if (gamepad2.left_stick_y != 0){
            Joint.setPower(gamepad2.left_stick_y);
        }
        else {
            Joint.setPower(0.01);
        }
         if (gamepad2.right_stick_y != 0){
            Joint2.setPower(gamepad2.right_stick_y);
        }
        else
         {
             Joint2.setPower(0.01);
         }
    }
}
