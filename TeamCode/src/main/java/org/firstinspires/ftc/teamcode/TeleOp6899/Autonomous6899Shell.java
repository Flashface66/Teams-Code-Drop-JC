package org.firstinspires.ftc.teamcode.TeleOp6899;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.DogeCV;
import com.disnodeteam.dogecv.detectors.roverrukus.GoldAlignDetector;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;


public class Autonomous6899Shell extends LinearOpMode {

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


        waitForStart();
        Legs = CompetitionLegs.Mineral;


        while(opModeIsActive()){
//            telemetry.addLine("Rotate");
//            telemetry.update();
//            Rotate(10,1,2000);
//            ZeroDrive();
//            sleep(5000);
//            RunwithEncAll();
//            telemetry.addLine("Forward");
//            telemetry.update();
//            RunToPosDrive(10,.2,2000);
//            sleep(5000);
//            ZeroDrive();
//            requestOpModeStop();

            if (Legs == CompetitionLegs.Delatch){
                telemetry.addLine("Delatch");
                telemetry.update();
                RunToPos(Bot.ChainLift, 20,.5);
                sleep(5000);
                Zero(Bot.ChainLift);
                Rotate(10,0.4,2000);
                ZeroDrive();
                RunwithEncAll();
                telemetry.addLine("Removing Latch...");
                telemetry.update();
                sleep(2000);
                RunToPosDrive(10,.5,2000);
                RunwithEncAll();
                telemetry.addLine("Moving away from Lander....");
                telemetry.update();
                sleep(2000);
                Rotate(-10,0.5,2000);
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
                int endLeft = 2;
                while (!detector.isFound()){
                    if (x <endLeft) {
                        Rotate(1, 0.3, 500);
                        ZeroDrive();
                        RunwithEncAll();
                        sleep(1000);
                        telemetry.addData("x", "%d", x);
                        x++;
                        if (detector.isFound()) {
                            telemetry.addLine("Found!");
                            telemetry.addLine("Left");
                            telemetry.update();
                            Side = FoundSide.Left;
                            break;
                        }
                    }
                    if (x > endLeft){
                        break;
                    }
                }
                int endRight = 2;
                while (!detector.isFound()){
                    if (y<= endRight) {
                        Rotate(1, -0.3, 500);
                        ZeroDrive();
                        RunwithEncAll();
                        sleep(1000);
                        y++;
                        if (detector.isFound()) {
                            telemetry.addLine("Found!");
                            telemetry.addLine("Right");
                            telemetry.update();
                            Side = FoundSide.Right;
                            break;
                        }
                    }
                    if (y>endRight){
                        break;
                    }
                }
                if (detector.isFound()){
                    RunToPosDrive(3,0.5,2000);
                    ZeroDrive();
                    RunwithEncAll();
                    RunToPosDrive(3,-0.5,2000);
                    ZeroDrive();
                    RunwithEncAll();
                    amount = Correction();
                    Rotate(amount,0.5,500);
                    ZeroDrive();
                    RunwithEncAll();
                    Legs = CompetitionLegs.Marker;
                    requestOpModeStop();
                }
            }
            if (Legs == CompetitionLegs.Marker){
                telemetry.addLine("Marker");
                telemetry.update();
                Rotate(10,0.5,2000);
                RunToPosDrive(20,0.3,2000);
                Rotate(5,0.5,2000);
                RunToPosDrive(10,0.3,2000);
                MarkerPlace();
                Legs = CompetitionLegs.Crater;
            }
            if (Legs == CompetitionLegs.Crater){
                telemetry.addLine("Crater");
                telemetry.update();
                RunToPosDrive(40,0.5,2000);
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
    private void RunToPosDrive(int inches, double power, int sleeptimer){
        Bot.FrontLeft.setTargetPosition(inches);
        Bot.FrontRight.setTargetPosition(inches);
        Bot.BackRight.setTargetPosition(inches);
        Bot.BackLeft.setTargetPosition(inches);
        Bot.FrontRight.setPower(-power);
        Bot.BackLeft.setPower(-power);
        Bot.FrontLeft.setPower(-power);
        Bot.BackRight.setPower(-power);
        sleep(sleeptimer);
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
    private void Rotate(int inches,double power, int sleeptimer){
        Bot.FrontLeft.setTargetPosition(inches);
        Bot.FrontRight.setTargetPosition(inches);
        Bot.BackRight.setTargetPosition(inches);
        Bot.BackLeft.setTargetPosition(inches);
        Bot.FrontRight.setPower(-power);
        Bot.BackLeft.setPower(power);
        Bot.FrontLeft.setPower(power);
        Bot.BackRight.setPower(-power);
        sleep(sleeptimer);
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
