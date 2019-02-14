package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

// Ask me ( Colin ) if you don't understand what I did before you touch the code : )

@Autonomous(name = "ColinAutonomousTest")
public class ColinAutonomousTest extends LinearOpMode
{

    private DcMotor FrontLeft, BackLeft, FrontRight, BackRight, ChainLift, HookLift;
    private Servo trayDispL, trayDispR,IntakeR,IntakeL;
    private ElapsedTime elapsedTime;

    @Override
    public void runOpMode()
    {
        //Wheel Motors
        FrontLeft =  hardwareMap.dcMotor.get("FrontLeft");
        FrontRight =  hardwareMap.dcMotor.get("FrontRight");
        BackLeft = hardwareMap.dcMotor.get("BackLeft");
        BackRight = hardwareMap.dcMotor.get("BackRight");

        //Collection Devices
        HookLift = hardwareMap.dcMotor.get("HookLift");
        ChainLift = hardwareMap.dcMotor.get("ChainLift");
        trayDispL = hardwareMap.servo.get("TrayDispL");
        trayDispR = hardwareMap.servo.get("TrayDispR");
        trayDispL.setDirection(Servo.Direction.REVERSE);


        //Lowest Intake Servos
        IntakeL = hardwareMap.servo.get("IntakeL");
        IntakeR = hardwareMap.servo.get("IntakeR");

        FrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Robot Timing, for testing purposes
        elapsedTime = new ElapsedTime();

        waitForStart(); //wait for the driver to press play
        while(opModeIsActive())
        {
            // Code for autonomous here
            hookDown(elapsedTime);
            DriveForElapseTime(elapsedTime,11,6,.5);
            StopAll();
        }
    }

    private void Drive()
    {
        FrontLeft.setPower(.6);
        FrontRight.setPower(.6);
        BackLeft.setPower(-.6);
        BackRight.setPower(-.6);
    }

    private void Drive(double power)
    {
        FrontLeft.setPower(power);
        FrontRight.setPower(power);
        BackLeft.setPower(-power);
        BackRight.setPower(-power);
    }

    // set @param power to negative for reverse
    private void DriveForElapseTime(ElapsedTime elapsedTime, double maxR, double minR, double power)
    {
        if ( elapsedTime.seconds() < maxR && elapsedTime.seconds() > minR)
        {
            FrontLeft.setPower(power);
            FrontRight.setPower(power);
            BackLeft.setPower(-power);
            BackRight.setPower(-power);
        }
        else
            StopAll();
        /**
        {
            FrontLeft.setPower(0);
            FrontRight.setPower(0);
            BackLeft.setPower(0);
            BackRight.setPower(0);
        }**/
    }

    private void Reverse()
    {
        FrontLeft.setPower(-.6);
        FrontRight.setPower(-.6);
        BackLeft.setPower(.6);
        BackRight.setPower(.6);
    }

    private void Reverse(double power)
    {
        FrontLeft.setPower(-power);
        FrontRight.setPower(-power);
        BackLeft.setPower(power);
        BackRight.setPower(power);
    }

    private void StopAll()
    {
        FrontLeft.setPower(0);
        FrontRight.setPower(0);
        BackLeft.setPower(0);
        BackRight.setPower(0);
        // Add more motors when necessary
    }

    private void hookDown(ElapsedTime elapsedTime)
    {
        if ( elapsedTime.seconds() < 5 && elapsedTime.seconds() >= 0)
            HookLift.setPower(.3);
        else
            HookLift.setPower(0);
    }

}
