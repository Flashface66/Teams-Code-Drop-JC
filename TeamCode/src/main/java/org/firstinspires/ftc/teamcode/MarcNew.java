package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="MarcNew")
public class MarcNew extends LinearOpMode {
    private DcMotor FrontLeft, FrontRight,
            BackRight, BackLeft, Lift,
            Joint;
    private Servo Phone, ClawL,ClawR,Latch;
    private double FRP,FLP,BRP,BLP;
    private double x,CL,CR = 0;
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
        Lift = hardwareMap.dcMotor.get("Lift");
        Phone = hardwareMap.servo.get("Camera");
        ClawR = hardwareMap.servo.get("ClawR");
        ClawL = hardwareMap.servo.get("ClawL");
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
        Lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
         /*
         Setting the Direction of each motor where needed
         */
        FrontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        FrontLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        BackLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        BackRight.setDirection(DcMotorSimple.Direction.REVERSE);
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
        FrontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        FrontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BackLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BackRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        Lift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        Joint.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        telemetry.update();

        waitForStart();
        Phone.setPosition(x);
        ClawL.setPosition(CL);
        ClawR.setPosition(CR);
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
        if (gamepad1.dpad_up){
            Phone.setPosition(x);
            x = Range.clip(x+0.005,0.001,1);
            telemetry.addData("Position",Phone.getPosition());
        }
        if (gamepad1.dpad_down){
            Phone.setPosition(x);
            x = Range.clip(x-0.005,0.001,1);
            telemetry.addData("Position",Phone.getPosition());
        }
        /*
        Similar to the movement of the phone's servo as it incrementally increases and decreases the
        position of the servo on the Claw Arm.
         */
        if (gamepad1.a) {
            ClawL.setPosition(CL);
            CL = Range.clip(CL+0.005,0.18,.6);
//            ClawR.setPosition(CR);
//            CR = Range.clip(CR-0.005,0.59,.9);
//            telemetry.addData("Position Claw R",ClawR.getPosition());
            /*
            Telemetry to get the position of the claw for troubleshooting.
             */
            telemetry.addData("Position Claw L", ClawL.getPosition());
        }
        if (gamepad1.b){
            ClawL.setPosition(CL);
            CL = Range.clip(CL-0.005,.18,.6);
//            ClawR.setPosition(CR);
//            CR = Range.clip(CR+0.005,0.59,.9);
//            telemetry.addData("Position Claw R",ClawR.getPosition());
            /*
            Telemetry to get the position of the claw for troubleshooting.
             */
            telemetry.addData("Position Claw L", ClawL.getPosition());
        }
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
        if (gamepad1.right_bumper){
            Joint.setPower(0.3);
        }else
        if (gamepad1.left_bumper){
            Joint.setPower(-0.3);
        }else
        {
            Joint.setPower(0.0);
        }
    }
}
