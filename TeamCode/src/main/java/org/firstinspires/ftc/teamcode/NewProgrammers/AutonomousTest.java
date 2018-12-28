package org.firstinspires.ftc.teamcode.NewProgrammers;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous (name = "FirstAuto")
public class AutonomousTest extends LinearOpMode {
    private DcMotor FrontR,FrontL,BackR,BackL, IntakeL,IntakeR;


    @Override
    public void runOpMode() throws InterruptedException {
        FrontL = hardwareMap.dcMotor.get("Fleft");
        FrontR = hardwareMap.dcMotor.get("Fright");
        BackR = hardwareMap.dcMotor.get("BackRight");
        BackL = hardwareMap.dcMotor.get("BackLeft");
        IntakeL = hardwareMap.dcMotor.get("IntakeL");
        IntakeR = hardwareMap.dcMotor.get("IntakeR");
        FrontL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FrontR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        IntakeL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        IntakeR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        waitForStart();



    }
}
