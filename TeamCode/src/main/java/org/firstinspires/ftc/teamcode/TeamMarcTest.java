package org.firstinspires.ftc.teamcode;

import com.disnodeteam.dogecv.detectors.roverrukus.GoldAlignDetector;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name = "TeamMarcTest",group = "A")
public class TeamMarcTest extends LinearOpMode {
    private double FrontPowerLeft;
    private double FrontPowerRight;
    private double BackPower;
    private ElapsedTime runtime = new ElapsedTime();
    private ElapsedTime ConIdle = new ElapsedTime();
    private DcMotor FrontLeft, FrontRight,
                BackRight, BackLeft, RLift,
                Back, Lift, Intake, Conveyor;
    private Servo Collect;
    private Boolean Turning,Forward, Failsafe, SafeFail;
    private GoldAlignDetector Gdectect = new GoldAlignDetector();

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
        Lift = hardwareMap.dcMotor.get("Lift");
        RLift = hardwareMap.dcMotor.get("RLift");
        Intake = hardwareMap.dcMotor.get("Intake");
        Conveyor = hardwareMap.dcMotor.get("Conveyor");
        Collect = hardwareMap.servo.get("Collect");
        /*
        Setting the stops for the Robot.
            This makes the motor's activity, once their value is zero, to act as a brake.
        */
            FrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            FrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            BackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            BackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            Lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            RLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            Intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            Conveyor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
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
            Lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            RLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            RLift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            Lift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            Conveyor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            Conveyor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        /*
         Setting the Outputs
         */
        telemetry.addData("First: ", "Starting @%2d",
                            Lift.getCurrentPosition());/*Getting the current position of the motor*/
        telemetry.addLine("..................");
        telemetry.addData("Robot: ", "Hardware map CHECK ");
        telemetry.update();
        waitForStart();
        Turning = false;
        Forward = false;
        Failsafe = false;

        while (opModeIsActive()) {
            MovementSys3();
            Conveyors();
            IntakeMockup();
            Servos();
        }
    }

    private void MovementSys3(){
        telemetry.addLine("************Status: Running***********");
        double Power1, Power2;
        Power1 = Range.clip(gamepad1.left_stick_y, -1,1);
        Power2 = Range.clip(gamepad1.right_stick_y, -1,1);
        FrontRight.setPower(Power2);
        FrontLeft.setPower(Power1);
        BackRight.setPower(Power2);
        BackLeft.setPower(Power1);
    }

    private void Conveyors(){
        if (gamepad2.right_trigger >=0.8){
            Conveyor.setPower(-0.8);
        }
        else if (gamepad2.left_trigger >=0.8){
            Conveyor.setPower(0.8);
        }
        if(gamepad2.right_trigger <0.8 && gamepad2.left_trigger <0.8){
            Conveyor.setPower(0);
        }
        if(gamepad2.right_bumper) {
            Lift.setPower(-0.8);
            RLift.setPower(0.3);
        }
            else if (gamepad2.left_bumper){
            Lift.setPower(0.8);
            RLift.setPower(-0.8);
                }else{
                    Lift.setPower(0);
                    RLift.setPower(0);
                }
        telemetry.addData("Lift","Getting Position: %2d, %2d",
                Lift.getCurrentPosition(), RLift.getCurrentPosition());
        telemetry.update();
    }

    private void IntakeMockup(){
        if (gamepad1.a){
            Intake.setPower(0.320);
        }
        if (gamepad1.b){
            Intake.setPower(-0.220);
        }
        if (!gamepad1.a && !gamepad1.b){
            Intake.setPower(0);
        }
    }

    private void Servos(){
        if (gamepad1.dpad_left){
            Collect.setPosition(1);
        }
        if (gamepad1.dpad_right){
            Collect.setPosition(0);
        }
        if (gamepad1.dpad_down){
            Collect.setPosition(0.5);
        }

    }

}
