package org.firstinspires.ftc.teamcode.WORLDS;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.DogeCV;
import com.disnodeteam.dogecv.detectors.roverrukus.GoldAlignDetector;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous
public class Worlds_Autonomous extends LinearOpMode {

    private static final double     CPMR    = 1440 ;    // TETRIX Motor Encoder
    private static final double     DGR   = 0.5 ;     // < 1.0 if geared UP
    private static final double     WDI   = 3.5 ;     // For figuring circumference
    private static final double     CPI         = (CPMR * DGR) / (WDI * 3.1415);

    private ElapsedTime runtime = new ElapsedTime();

    private HardwareWorlds RB = new HardwareWorlds();

    private int Pos = 0;


    @Override
    public void runOpMode() {

        GoldAlignDetector detector = new GoldAlignDetector();
        detector.init(hardwareMap.appContext, CameraViewDisplay.getInstance()); // Initialize it with the app context and camera
        detector.useDefaults(); // Set detector to use default settings
        //tuning
        detector.alignSize = 160; // How wide (in pixels) is the range in which the gold object will be aligned. (Represented by green bars in the preview)
        detector.alignPosOffset = 8; // How far from center frame to offset this alignment zone.
        detector.downscale = 0.2; // How much to downscale the input frames

        detector.areaScoringMethod = DogeCV.AreaScoringMethod.MAX_AREA; // Can also be PERFECT_AREA
        //detector.perfectAreaScorer.perfectArea = 10000; // if using PERFECT_AREA scoring
        detector.maxAreaScorer.weight = 0.005; //
        detector.ratioScorer.weight = 5; //
        detector.ratioScorer.perfectRatio = 1.0; // Ratio adjustment

        RB.init(hardwareMap);

        Stop();
        RB.BackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        RB.BackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        RB.FrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        RB.FrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        detector.enable();
        waitForStart();
        /*

        OUTDATEDDDDDDD!!!!
        Update to match the gear ratios

        Food for the thought.
        Forward movement is positive Inches for             ----------- FBMove
        Strafe right movement is positive Inches for        ----------- Strafe
        Rotate right movement is positive Inches for        ----------- Rotate
                12 - 90 degrees
                22 - 180 degrees

         */

        //Strafe towards the balls
        Strafe(-8.375,10);

        if (detector.getAligned()){
            Pos =2;
            detector.disable();
            telemetry.addData("Position", Pos);
            telemetry.update();

            //Rotate to face the Cube
            Rotate(-10,4);

            //Move forward Knock off cube
            FBMove(5,2);

            //Move back out of way of other minerals
            FBMove(-3.8,2);

            //Rotate with back facing the wall--to red crater
            Rotate(10,4);

            //Strafe forward enough to avoid collisions with other robots
            Strafe(-1.5,8);

            //Move back to the wall
            FBMove(-18,4);

            //Rotate facing the depot
            Rotate(-7.7,4);

            //Move forward to the depot to deposit
            /*
            Deposit Below FBMove
             */
            FBMove(22,6);

            // Move back to original Position
            FBMove(-22,6);

            //Rotate to face Crater
            Rotate(22,5);


        }else {
            //Rotate if the ball is not found in the center or Position 2.
            Rotate(4, 10);
            if (detector.getAligned() && Pos == 0) {
                Pos = 3;
                detector.disable();
                telemetry.addData("Position", Pos);
                telemetry.update();
                Rotate(-4, 10);

                Strafe(-3,4);

                FBMove(8,7);

                Strafe(-8,4);

                Strafe(5.5,4);

                FBMove(-8,4);

                //Move back to the wall
                FBMove(-17.8,4);

                //Rotate facing the depot
                Rotate(-6.9,4);

                //Move forward to the depot to deposit
            /*
            Deposit Below FBMove
             */
                FBMove(22,6);

                // Move back to original Position
                FBMove(-22,6);

                //Rotate to face Crater
                Rotate(22,5);


            }else {
                //Rotate to face the 1st mineral if other two aren't gold
                Rotate(-9.9, 11);
                if (detector.getAligned() && Pos == 0) {
                    Pos = 1;
                    detector.disable();
                    telemetry.addData("Position", Pos);
                    telemetry.update();

                    Rotate(5.9,4);

                    Strafe(-3,4);

                    FBMove(-8,7);

                    Strafe(-8,4);

                    Strafe(5.5,4);

                    //Move back to the wall
                    FBMove(-8.8,4);

                    //Rotate facing the depot
                    Rotate(-6.9,4);

                    //Move forward to the depot to deposit
            /*
            Deposit Below FBMove
             */
                    FBMove(22,6);

                    // Move back to original Position
                    FBMove(-22,6);

                    //Rotate to face Crater
                    Rotate(22,5);
                }
            }
        }

        detector.disable();
        telemetry.addData("Position", Pos);
        telemetry.update();


    }

    private void FBMove(double inches, double timeout){
        int targetfr;
        int targetbr;
        int targetfl;
        int targetbl;

        if (opModeIsActive()){

            targetfl = RB.FrontLeft.getCurrentPosition() + (int)(inches * CPI);
            targetfr = RB.FrontRight.getCurrentPosition() + (int)(inches * CPI);
            targetbr = (RB.BackRight.getCurrentPosition() + (int)(inches * CPI));
            targetbl = RB.BackLeft.getCurrentPosition() + (int)(inches * CPI);
            RB.FrontLeft.setTargetPosition(targetfl);
            RB.FrontRight.setTargetPosition(targetfr);
            RB.BackRight.setTargetPosition(targetbr);
            RB.BackLeft.setTargetPosition(targetbl);

            RB.FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            RB.FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            RB.BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            RB.BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            runtime.reset();
            Start();

            while (opModeIsActive() && (runtime.seconds() <timeout) &&
                    (RB.BackLeft.isBusy() && RB.FrontRight.isBusy() && RB.BackRight.isBusy()
                        && RB.FrontLeft.isBusy())){
                telemetry.addLine("Robot busy");
                telemetry.update();
            }
            Stop();
            EncStart();
        }

    }

    private void Strafe(double inches, double timeout){
        int targetfr;
        int targetbr;
        int targetfl;
        int targetbl;

        if (opModeIsActive()){

            targetfl = RB.FrontLeft.getCurrentPosition() + (int)(inches * CPI);
            targetfr = RB.FrontRight.getCurrentPosition() + (int)(-inches * CPI);
            targetbr = (RB.BackRight.getCurrentPosition() + (int)(inches * CPI));
            targetbl = RB.BackLeft.getCurrentPosition() + (int)(-inches * CPI);
            RB.FrontLeft.setTargetPosition(targetfl);
            RB.FrontRight.setTargetPosition(targetfr);
            RB.BackRight.setTargetPosition(targetbr);
            RB.BackLeft.setTargetPosition(targetbl);

            RB.FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            RB.FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            RB.BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            RB.BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            runtime.reset();
            Start();

            while (opModeIsActive() && (runtime.seconds() <timeout) &&
                    (RB.BackLeft.isBusy() && RB.FrontRight.isBusy() && RB.BackRight.isBusy()
                            && RB.FrontLeft.isBusy())){
                telemetry.addLine("Robot busy");
                telemetry.update();
            }
            Stop();
            EncStart();
        }

    }

    private void Rotate(double inches, double timeout){
        int targetfr;
        int targetbr;
        int targetfl;
        int targetbl;

        if (opModeIsActive()){

            targetfl = RB.FrontLeft.getCurrentPosition() + (int)(inches * CPI);
            targetfr = RB.FrontRight.getCurrentPosition() + (int)(-inches * CPI);
            targetbr = (RB.BackRight.getCurrentPosition() + (int)(-inches * CPI));
            targetbl = RB.BackLeft.getCurrentPosition() + (int)(inches * CPI);
            RB.FrontLeft.setTargetPosition(targetfl);
            RB.FrontRight.setTargetPosition(targetfr);
            RB.BackRight.setTargetPosition(targetbr);
            RB.BackLeft.setTargetPosition(targetbl);

            RB.FrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            RB.FrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            RB.BackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            RB.BackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            runtime.reset();
            Start();

            while (opModeIsActive() && (runtime.seconds() <timeout) &&
                    (RB.BackLeft.isBusy() && RB.FrontRight.isBusy() && RB.BackRight.isBusy()
                            && RB.FrontLeft.isBusy())){
                telemetry.addLine("Robot busy");
                telemetry.update();
            }
            Stop();
            EncStart();
        }
    }

    private void Delatch(){
        RB.Lift.setPower(1);
    }

    private void EncStart(){
        RB.BackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        RB.BackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        RB.FrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        RB.FrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    private void Start(){
        RB.FrontRight.setPower(1);
        RB.BackRight.setPower(1);
        RB.FrontLeft.setPower(1);
        RB.BackLeft.setPower(1);
    }

    private void Stop(){
        RB.FrontRight.setPower(0);
        RB.BackRight.setPower(0);
        RB.FrontLeft.setPower(0);
        RB.BackLeft.setPower(0);
        RB.BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RB.BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RB.FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RB.FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        /*-----------------------------------------------------------*/
    }


}

