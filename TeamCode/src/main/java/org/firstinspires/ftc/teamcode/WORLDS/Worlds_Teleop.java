package org.firstinspires.ftc.teamcode.WORLDS;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "Worlds Robot", group = "JC")

public class Worlds_Teleop extends LinearOpMode {
    private DcMotor FrontLeft     = null;
    private DcMotor FrontRight    = null;
    private DcMotor BackRight     = null;
    private DcMotor BackLeft      = null;
    private DcMotor Lift          = null;
    private DcMotor Diposite      = null;
    private DcMotor EnterCrater   = null;
    private Servo   Spin1         = null;
    private Servo   Spin2         = null;
    private Servo   BoxLift1      = null;
    private Servo   BoxLift2      = null;
    private Servo   Intake1       = null;
    private Servo   Intake2       = null;


    private final boolean shouldMecanumDrive = true;

    @Override
    public void runOpMode() {
        FrontLeft    = hardwareMap.get(DcMotor.class, "Fleft");
        FrontRight   = hardwareMap.get(DcMotor.class, "Fright");
        BackRight    = hardwareMap.get(DcMotor.class, "BRight");
        BackLeft     = hardwareMap.get(DcMotor.class, "BLeft");
        Lift         = hardwareMap.get(DcMotor.class, "Lift");
        Diposite     = hardwareMap.get(DcMotor.class, "Diposite");
        EnterCrater  = hardwareMap.get(DcMotor.class, "ECrater");
        BoxLift1     = hardwareMap.get(Servo.class,   "BoxLift1");
        BoxLift2     = hardwareMap.get(Servo.class,   "BoxLift2");
        Spin1        = hardwareMap.get(Servo.class,   "Spin1");
        Spin2        = hardwareMap.get(Servo.class,   "Spin2");


        FrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Diposite.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        EnterCrater.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        waitForStart();
        while (opModeIsActive()) {
            Movement();
            LiftControl();
            DipositeControl();
            Intake();
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
            FrontLeft.setPower(wheels.frontLeft);
            FrontRight.setPower(wheels.frontRight);
            BackLeft.setPower(wheels.backLeft);
            BackRight.setPower(wheels.backRight);
        }

    }

    private void LiftControl() {
        if (gamepad1.right_trigger >0.5)
            Lift.setPower(1);
        else if (gamepad1.left_trigger >0.5)
            Lift.setPower(-1);
        else
            Lift.setPower(0.0);
    }

    private void Intake() {
        if (gamepad2.a) {
            Spin1.setPosition(-1);
            Spin2.setPosition(1);
        }else

            if (gamepad2.b) {
            Spin1.setPosition(1);
            Spin2.setPosition(-1);
        }
    else
    {
        Spin1.setPosition(0.5);
        Spin2.setPosition(0.5);

    }

 if(gamepad2.dpad_up) {
     Intake1.setPosition(0);
     Intake2.setPosition(1);

 }else

     if(gamepad2.dpad_down)
         Intake1.setPosition(0);
     Intake2.setPosition(1);


    }


}
