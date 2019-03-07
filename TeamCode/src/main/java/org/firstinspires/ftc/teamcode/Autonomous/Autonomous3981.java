package org.firstinspires.ftc.teamcode.Autonomous;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.DogeCV;
import com.disnodeteam.dogecv.detectors.roverrukus.GoldAlignDetector;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "Autonomous 3981")
public class Autonomous3981 extends LinearOpMode {


    /* Declare OpMode members. */
    private DcMotor FrontLeft  = null;
    private DcMotor FrontRight = null;
    private DcMotor BackRight  = null;
    private DcMotor BackLeft   = null;
    private DcMotor Lift       = null;
    private ElapsedTime runtime = new ElapsedTime();

    private GoldAlignDetector detector;



    private double goldPos = 0;


    @Override
    public void runOpMode(){

        //MOTORS
        FrontLeft  = hardwareMap.get(DcMotor.class,"Fleft");
        FrontRight = hardwareMap.get(DcMotor.class,"Fright");
        BackRight  = hardwareMap.get(DcMotor.class,"BackRight");
        BackLeft   = hardwareMap.get(DcMotor.class,"BackLeft");
        Lift       = hardwareMap.get(DcMotor.class, "Lift");

        FrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        FrontRight.setDirection(DcMotor.Direction.FORWARD);//Reverse
        FrontLeft.setDirection(DcMotor.Direction.REVERSE);//Forward
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
        detector.alignPosOffset = -2; // How far from center frame to offset this alignment zone.
        detector.downscale = 0.4; // How much to downscale the input frames

        detector.areaScoringMethod = DogeCV.AreaScoringMethod.MAX_AREA; // Can also be PERFECT_AREA
        //detector.perfectAreaScorer.perfectArea = 10000; // if using PERFECT_AREA scoring
        detector.maxAreaScorer.weight = 0.005;

        detector.ratioScorer.weight = 5;
        detector.ratioScorer.perfectRatio = 1.0;



        detector.enable();


        telemetry.addData("Status", "Initialized");
        telemetry.update();
        

        waitForStart();


        telemetry.addData("IsAligned" , detector.getAligned()); // Is the bot aligned with the gold mineral
        telemetry.update();

        goldPos = detector.getXPosition();


        //dismount();
        //sleep(500);

        delay(1000);

        //Left
        RunwithEncAll();
        RunToPosDrive(1,-0.5,300);

        //Forward
        Strafe(1,0.5,300);

        //Right
        RunToPosDrive(1,0.5,300);

        delay(1400);

        if(goldPos < 170) {
            telemetry.addLine("Going to Left side");
            telemetry.update();
            //Forward
            Strafe(1,0.5,300);

            //Left
            RunToPosDrive(1,0.5,400);

            //Forward
            Strafe(4,0.7,4000);

            //Reverse
            Strafe(2,-0.7,3000);

            //Left
            RunToPosDrive(2,-0.5,2600);

            //Rotate Left
            Rotate(1,0.6,1100);

            //Right
            RunToPosDrive(1,0.5,3800);

            //Left
            RunToPosDrive(5,-0.5,8000);

        }else if (goldPos > 500) {
            telemetry.addLine("Going to Right Side");
            telemetry.update();

            //Forward
            Strafe(1,0.5,300);

            //Right
            RunToPosDrive(1,-0.5,400);

            //Forward
            Strafe(4,0.7,4000);

            //Reverse
            Strafe(2,-0.7,3000);

            //Left
            RunToPosDrive(2,-0.5,2600);

            //Rotate Left
            Rotate(1,0.6,1100);

            //Right
            RunToPosDrive(1,0.5,3800);

            //Left
            RunToPosDrive(5,-0.5,8000);

        }else{
            telemetry.addLine("Going Straight");
            telemetry.update();

            Strafe(4,0.7,4000);

            //Reverse
            Strafe(2,-0.7,3000);

            //Left
            RunToPosDrive(2,-0.5,2600);

            //Rotate Left
            Rotate(1,0.6,1100);

            //Right
            RunToPosDrive(1,0.5,3800);

            //Left
            RunToPosDrive(5,-0.5,8000);

        }
    }

    public void dismount(){
        telemetry.addData("status" , "dismount");
        telemetry.update();

        Lift.setPower(-0.6);

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
