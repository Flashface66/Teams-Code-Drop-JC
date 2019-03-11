package org.firstinspires.ftc.teamcode.Autonomous;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.DogeCV;
import com.disnodeteam.dogecv.detectors.roverrukus.GoldAlignDetector;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "PanCake_Auto_Red_Crater")
public class PanCake_Auto_Red_Crater extends LinearOpMode {


    /* Declare OpMode members. */
    private DcMotor FrontLeft  = null;
    private DcMotor FrontRight = null;
    private DcMotor BackRight  = null;
    private DcMotor BackLeft   = null;
    private DcMotor Lift       = null;
    private Servo   Intake     = null;
    private ElapsedTime runtime = new ElapsedTime();

    private GoldAlignDetector detector;

    private double getruntime;



    private double goldPos = 0;


    @Override
    public void runOpMode(){

        //MOTORS
        FrontLeft  = hardwareMap.get(DcMotor.class,"Fleft");
        FrontRight = hardwareMap.get(DcMotor.class,"Fright");
        BackRight  = hardwareMap.get(DcMotor.class,"BackRight");
        BackLeft   = hardwareMap.get(DcMotor.class,"BackLeft");
        Lift       = hardwareMap.get(DcMotor.class,"Lift");
        Intake     = hardwareMap.get(Servo.class,  "Intake");

        FrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        FrontRight.setDirection(DcMotor.Direction.FORWARD);//Reverse
        FrontLeft.setDirection(DcMotor.Direction.FORWARD);//Forward
        BackLeft.setDirection(DcMotor.Direction.FORWARD);//Forward
        BackRight.setDirection(DcMotor.Direction.REVERSE);//Reverse

        FrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        FrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);



        //CV DETECTOR
        telemetry.addData("Status", "Camera Enabled");

        detector = new GoldAlignDetector();
        detector.init(hardwareMap.appContext, CameraViewDisplay.getInstance());
        detector.useDefaults();




        // Optional Tuning
        detector.alignSize = 200; // How wide (in pixels) is the range in which the gold object will be aligned. (Represented by green bars in the preview)
        detector.alignPosOffset = -6; // How far from center frame to offset this alignment zone.
        detector.downscale = 0.4; // How much to downscale the input frames

        detector.areaScoringMethod = DogeCV.AreaScoringMethod.MAX_AREA; // Can also be PERFECT_AREA
        //detector.perfectAreaScorer.perfectArea = 10000; // if using PERFECT_AREA scoring
        detector.maxAreaScorer.weight = 0.005;

        detector.ratioScorer.weight = 5;
        detector.ratioScorer.perfectRatio = 1.0;



        detector.enable();


        telemetry.addData("Status", "Initialized");
        telemetry.update();

        Intake.setPosition(0.5);

        waitForStart();
        while (opModeIsActive()) {

            telemetry.addData("IsAligned", detector.getAligned()); // Is the bot aligned with the gold mineral
            telemetry.update();

            goldPos = detector.getXPosition();


            dismount(1500);


            delay(1000);

            //Left
            RunwithEncAll();
            RunToPosDrive(1, -0.5, 350);

            //Forward
            Strafe(1, 0.5, 350);

            //Right
            RunToPosDrive(1, 0.5, 300);

            //Forward
            // Strafe(1, 0.5, 500);


            int x = 1;
            if (detector.isFound()) {
                if (detector.getAligned()) {
                    telemetry.addLine("Going Straight");
                    telemetry.update();

                    //Forward
                    Strafe(4, 0.7, 2000);

                    //Reverse
                    Strafe(2, -0.7, 1700);

                    //Left
                    RunToPosDrive(2, -0.5, 2300);

                    //Rotate Left
                    Rotate(1, 0.6, 800);

                    //Right
                    RunToPosDrive(1, -0.5, 3400);

                    Intake.setPosition(0);

                    //Left
                    RunToPosDrive(5, 0.5, 5000);
                    x = 0
                    ;
                }

            }
            if (!detector.getAligned() && !detector.isFound() && opModeIsActive() && x != 0 || detector.isFound() && opModeIsActive() && x != 0) {
                telemetry.addLine("Going to Right side");
                telemetry.update();
                x = 1;
                runtime.reset();
                RunToPosDrive(1, 0.3, 600);

                if (detector.getAligned()) {
                    getruntime = runtime.seconds();
                    x = 2;
                    //Forward
                    Strafe(1, 0.5, 300);

                    //Left
                    RunToPosDrive(1, -0.5, 400);

                    //Forward
                    Strafe(4, 0.7, 2000);

                    //Reverse
                    Strafe(2, -0.7, 1700);

                    //Left
                    RunToPosDrive(2, -0.5, 2300);

                    //Rotate Left
                    Rotate(1, 0.6, 800);

                    //Right
                    RunToPosDrive(1, -0.5, 3400);

                    Intake.setPosition(0);

                    //Left
                    RunToPosDrive(5, 0.5, 5000);

                }
            }
            if (x == 1) {
                RunToPosDrive(1, -0.3, 1200);
                telemetry.addLine("Going to Left side");
                telemetry.update();
                runtime.reset();
                if (detector.getAligned()) {
                    getruntime = runtime.seconds();
                    //Forward
                    Strafe(1, 0.5, 300);

                    //Right
                    RunToPosDrive(1, 0.5, 400);

                    //Forward
                    Strafe(4, 0.7, 2000);

                    //Reverse
                    Strafe(2, -0.7, 1700);

                    //Left
                    RunToPosDrive(2, -0.5, 2300);

                    //Rotate Left
                    Rotate(1, 0.6, 800);

                    //Right
                    RunToPosDrive(1, -0.5, 3400);

                    Intake.setPosition(0);

                    //Left
                    RunToPosDrive(5, 0.5, 5000);
                }
            }

        }

    }

    private void dismount(int sleep){
        telemetry.addData("status" , "dismount");
        telemetry.update();

        Lift.setPower(-0.6);
        sleep(sleep);
        Lift.setPower(0);

    }
    private void RunToPosDrive(int inches, double power, int sleeptimer){
        telemetry.addLine("Run to position");
        telemetry.update();
        FrontLeft.setTargetPosition(inches);
        FrontRight.setTargetPosition(inches);
        BackRight.setTargetPosition(inches);
        BackLeft.setTargetPosition(inches);
        FrontRight.setPower(power);
        BackLeft.setPower(power);
        FrontLeft.setPower(power);
        BackRight.setPower(power);
        sleep(sleeptimer);
        ZeroDrive();
        RunwithEncAll();
    }

    private void Rotate(int inches,double power, int sleeptimer){
        telemetry.addLine("Rotate");
        telemetry.update();

        FrontLeft.setTargetPosition(inches);
        FrontRight.setTargetPosition(inches);
        BackRight.setTargetPosition(inches);
        BackLeft.setTargetPosition(inches);
        FrontRight.setPower(-power);
        BackLeft.setPower(power);
        FrontLeft.setPower(power);
        BackRight.setPower(-power);
        sleep(sleeptimer);
        ZeroDrive();
        RunwithEncAll();


    }

    private void Strafe(int inches,double power, int sleeptimer){
        telemetry.addLine("Strafe");
        telemetry.update();
        FrontLeft.setTargetPosition(inches);
        FrontRight.setTargetPosition(inches);
        BackRight.setTargetPosition(inches);
        BackLeft.setTargetPosition(inches);
        FrontRight.setPower(power);
        BackLeft.setPower(power);
        FrontLeft.setPower(-power);
        BackRight.setPower(-power);
        sleep(sleeptimer);
        ZeroDrive();
        RunwithEncAll();
    }

    private void RunwithEncAll(){
        FrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        FrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    private void ZeroDrive(){
        FrontRight.setPower(0);
        BackLeft.setPower(0);
        FrontLeft.setPower(0);
        BackRight.setPower(0);
    }
    private void delay(int sleep){
        telemetry.addData("status" , "delay");
        FrontRight.setPower(0);
        FrontLeft.setPower(0);
        BackLeft.setPower(0);
        BackRight.setPower(0);
        sleep(sleep);



    }

}
