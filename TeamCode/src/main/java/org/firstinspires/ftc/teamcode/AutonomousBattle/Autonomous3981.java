package org.firstinspires.ftc.teamcode.AutonomousBattle;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.DogeCV;
import com.disnodeteam.dogecv.detectors.roverrukus.GoldAlignDetector;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name = "3981AutoTesting")
public class Autonomous3981 extends LinearOpMode {

    private GoldAlignDetector detector;
    private DcMotor FrontLeft, BackLeft, FrontRight, BackRight,
            ChainLift, HookLift;
    private Servo trayDispL, trayDispR,
            IntakeR,IntakeL;



    @Override
    public void runOpMode() throws InterruptedException {


        detector = new GoldAlignDetector(); // Create detector
        detector.init(hardwareMap.appContext, CameraViewDisplay.getInstance()); // Initialize it with the app context and camera
        detector.useDefaults(); // Set detector to use default settings

        //tuning
        detector.alignSize = 100; // How wide (in pixels) is the range in which the gold object will be aligned. (Represented by green bars in the preview)
        detector.alignPosOffset = 0; // How far from center frame to offset this alignment zone.
        detector.downscale = 0.4; // How much to downscale the input frames

        detector.areaScoringMethod = DogeCV.AreaScoringMethod.MAX_AREA; // Can also be PERFECT_AREA
        //detector.perfectAreaScorer.perfectArea = 10000; // if using PERFECT_AREA scoring
        detector.maxAreaScorer.weight = 0.005; //

        detector.ratioScorer.weight = 5; //
        detector.ratioScorer.perfectRatio = 1.0; // Ratio adjustment

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
        //ChainLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        waitForStart();

        while(opModeIsActive()){

        }

    }

    private void ForwardEnc(double power, int inches){

    }

}
