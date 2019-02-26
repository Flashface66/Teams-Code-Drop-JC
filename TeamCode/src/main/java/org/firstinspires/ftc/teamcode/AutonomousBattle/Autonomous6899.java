package org.firstinspires.ftc.teamcode.AutonomousBattle;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.DogeCV;
import com.disnodeteam.dogecv.detectors.roverrukus.GoldAlignDetector;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.sun.tools.javac.jvm.Code;

@Autonomous(name = "6899 AutoTesting")
public class Autonomous6899 extends LinearOpMode {

    private enum CompetitionLegs{
                Delatch,Vuforia, Mineral, Marker, Crater, Finish} CompetitionLegs Legs;
    private GoldAlignDetector detector;
    private ElapsedTime runtime = new ElapsedTime();
    private int tester;
    private int x=1;
    private int y=1;
    private int amount;
    private int Center = 1;
    private enum FoundSide{Left,Right} FoundSide Side;

    Hardware6899 Bot = new Hardware6899();



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

        Bot.init(hardwareMap);

        Bot.FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Bot.FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Bot.BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Bot.BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        waitForStart();

        while(opModeIsActive()){

            if (Legs == CompetitionLegs.Delatch){
                telemetry.addLine("Delatch");
                telemetry.update();
                RunToPos(Bot.ChainLift, 20,.5);
                sleep(5000);
                Zero(Bot.ChainLift);
                Rotate(10,0.4);
                telemetry.addLine("Removing Latch...");
                telemetry.update();
                sleep(2000);
                RunToPosDrive(10,.5);
                telemetry.addLine("Moving away from Lander....");
                telemetry.update();
                sleep(2000);
                Rotate(-10,0.5);
                telemetry.addLine("Fixing position back to original....");
                sleep(2000);
                Legs=CompetitionLegs.Vuforia;
            }
            //TODO Make sure to do Vuforia and link all 'Ifs'
            if (Legs == CompetitionLegs.Vuforia){
                telemetry.addLine("Vuforia");
                telemetry.update();
                Legs = CompetitionLegs.Mineral;
            }
            if (Legs == CompetitionLegs.Mineral){
                detector.enable();
                int endLeft = 12;
                while (!detector.isFound() && x<= endLeft){
                    Rotate(x,0.3);
                    x++;
                    if (detector.isFound()){
                        telemetry.addLine("Left");
                        telemetry.update();
                        Side = FoundSide.Left;
                    }
                }
                int endRight = 24;
                while (!detector.isFound() && x<= endRight){
                    Rotate(-y,0.3);
                    y--;
                    if (detector.isFound()){
                        telemetry.addLine("Right");
                        telemetry.update();
                        Side = FoundSide.Right;
                    }
                }
                if (detector.isFound()){
                    RunToPosDrive(10,0.5);
                    sleep(2000);
                    RunToPosDrive(-7,0.5);
                    amount = Correction();
                    Rotate(amount,0.5);
                }
                Legs = CompetitionLegs.Marker;
            }
            if (Legs == CompetitionLegs.Marker){
                telemetry.addLine("Marker");
                telemetry.update();
                Rotate(10,0.5);
                RunToPosDrive(20,0.3);
                Rotate(5,0.5);
                RunToPosDrive(10,0.3);
                MarkerPlace();
                Legs = CompetitionLegs.Crater;
            }
            if (Legs == CompetitionLegs.Crater){
                telemetry.addLine("Crater");
                telemetry.update();
                RunToPosDrive(40,0.5);
                Legs = CompetitionLegs.Finish;
            }
            if (Legs == CompetitionLegs.Finish){
                RunToPos(Bot.ChainLift,5,0.5);
            }

        }

    }

    private void RunNoEnc(DcMotor motor){
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
    private void RunWithEnc(DcMotor motor){
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    private void RunwithEncAll(){
        Bot.FrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Bot.FrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Bot.BackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Bot.BackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    private void RunToPosDrive(int inches, double power){
        Bot.FrontRight.setPower(power);
        Bot.BackLeft.setPower(power);
        Bot.FrontLeft.setPower(power);
        Bot.BackRight.setPower(power);
        Bot.FrontLeft.setTargetPosition(inches);
        Bot.FrontRight.setTargetPosition(inches);
        Bot.BackRight.setTargetPosition(inches);
        Bot.BackLeft.setTargetPosition(inches);
    }
    private int Busy(DcMotor motor){
        int val;
        if (!motor.isBusy()) {
            motor.setPower(0);
            val = 1;
        }else{
            val = 2;
        }
        return val;
    }
    private void Rotate(int inches,double power){
        Bot.FrontRight.setPower(power);
        Bot.BackLeft.setPower(power);
        Bot.FrontLeft.setPower(power);
        Bot.BackRight.setPower(power);
        Bot.FrontLeft.setTargetPosition(inches);
        Bot.FrontRight.setTargetPosition(inches);
        Bot.BackRight.setTargetPosition(inches);
        Bot.BackLeft.setTargetPosition(inches);
    }
    private int BusyDrive() {
        int val;
        if (!Bot.FrontRight.isBusy() && !Bot.FrontLeft.isBusy() &&
                !Bot.BackLeft.isBusy() && !Bot.BackLeft.isBusy()) {
            Bot.FrontRight.setPower(0);
            Bot.BackLeft.setPower(0);
            Bot.FrontLeft.setPower(0);
            Bot.BackRight.setPower(0);
            runtime.reset();
            val = 1;
        }else {
            val = 2;
        }
        return val;
    }
    private void RunToPos(DcMotor motor, int inches,double power){
        motor.setPower(power);
        motor.setTargetPosition(inches);
    }
    private void Zero(DcMotor motor){
        motor.setPower(0);
    }
    private void ZeroDrive(){
        Bot.FrontLeft.setPower(0);
        Bot.FrontRight.setPower(0);
        Bot.BackRight.setPower(0);
        Bot.BackLeft.setPower(0);
    }
    private int Correction() {
        int amount = 0;
        if (Side == FoundSide.Right) amount = x;
        if (Side == FoundSide.Left) amount = -y;
        return amount;
    }
    private void MarkerPlace(){
    }
}
