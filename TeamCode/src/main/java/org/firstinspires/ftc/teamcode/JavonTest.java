package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;


// @author Colin 'Bart' Campbell & Javon 'Utility Pole' Peart
@TeleOp(name = "JavonTest")
public class JavonTest extends LinearOpMode
{

    private DcMotor FrontLeft, BackLeft, FrontRight, BackRight, ChainLift;
    private Servo trayDispL, trayDispR,IntakeR,IntakeL; // Servos to control tray dispenser

    @Override
    public void runOpMode() throws InterruptedException {

        FrontLeft =  hardwareMap.dcMotor.get("FrontLeft");
        FrontRight =  hardwareMap.dcMotor.get("FrontRight");
        BackLeft = hardwareMap.dcMotor.get("BackLeft");
        BackRight = hardwareMap.dcMotor.get("BackRight");

        ChainLift = hardwareMap.dcMotor.get("ChainLift");
        trayDispL = hardwareMap.servo.get("TrayDispL");
        trayDispR = hardwareMap.servo.get("TrayDispR");
        // TODO inverse either of the servos if anything goes wrong in testing

        IntakeL = hardwareMap.servo.get("IntakeL");
        IntakeR = hardwareMap.servo.get("IntakeR");
        //TODO : inverse either of the servos incase if anything goes wrong in testing

        FrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //ChainLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        waitForStart();

        while(opModeIsActive()){
            MovementSys();
            CollectionSys();
            trayControlSys();
            telemetry.update();
        }

    }

    private void MovementSys() {
        telemetry.addLine("************Status: Running***********");

        double Power1, Power2;
        Power1 = Range.clip(gamepad1.left_stick_y, -1, 1);
        Power2 = Range.clip(gamepad1.right_stick_y, -1, 1);

        FrontLeft.setPower(-Power2);
        BackLeft.setPower (-Power2);

        BackRight.setPower (Power1);
        FrontRight.setPower(Power1);

        telemetry.addLine("Robots as seen...");
        telemetry.addData("In their Natural Habitat", Power1);
    }

    private void CollectionSys(){
        if (gamepad1.left_bumper){
            IntakeL.setPosition(-1);
            IntakeR.setPosition(-1);
        }
        else if (gamepad1.right_bumper)
        {
            IntakeL.setPosition(1);
            IntakeR.setPosition(1);
        }
        else
        {
            IntakeL.setPosition(0);
            IntakeR.setPosition(0);
        }
    }

    private void trayControlSys()
    {

        // Events for chain lift
        if (gamepad1.dpad_up){
            ChainLift.setPower(1);
        }
        else if(gamepad1.dpad_down){
            ChainLift.setPower(-1);
        }
        else{
            ChainLift.setPower(0);
        }

        //Events for tray dispenser sevos
        if (gamepad1.dpad_left) {
            trayDispL.setPosition(1.0);
            trayDispR.setPosition(1.0);
        }
        else if (gamepad1.dpad_right) {
            trayDispL.setPosition(-1.0);
            trayDispR.setPosition(-1.0);
        } else
        {
            // set to .5 for no continuous rotation
            trayDispL.setPosition(0.5);
            trayDispR.setPosition(0.5);
        }
    }
}

