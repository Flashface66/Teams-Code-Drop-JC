package org.firstinspires.ftc.teamcode.TeleOp6899;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.DogeCV;
import com.disnodeteam.dogecv.detectors.roverrukus.GoldAlignDetector;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "Autonomous 6899")
public class Autonomous6899 extends LinearOpMode {

    Hardware6899 Bot = new Hardware6899();

    private enum CompetitionLegs{
        Delatch,Vuforia, Mineral, Marker, Crater, Finish}
        CompetitionLegs Legs;

    private GoldAlignDetector detector;

    private ElapsedTime runtime = new ElapsedTime();

    private double getruntime;


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

        detector.enable();
        waitForStart();


        while (opModeIsActive()) {

            runtime.reset();
            if (detector.getAligned()){
                telemetry.addLine("Found Cube!");
                telemetry.update();
            }
            if (!detector.getAligned() && runtime.seconds() <= 3 && opModeIsActive()) {
                telemetry.addLine("Looking for Cube first side");
                telemetry.update();
                //Looking for Cube
                Rotate(1, .2, 2000);
                if (detector.getAligned()) {
                    telemetry.addLine("Found Cube! 1st side");
                    telemetry.update();
                    getruntime = runtime.seconds();
                }

                //Opposite direction
                telemetry.addLine("Looking for Cube second side");
                telemetry.update();
                Rotate(1, -.2, 2000);
                if (detector.getAligned()) {
                    telemetry.addLine("Found Cube! 2nd side");
                    telemetry.update();
                    getruntime = runtime.seconds();
                }
            }

            telemetry.addLine("Grabbing the Cube!");
            telemetry.update();
            //Goes to cube and grabs it
            RunToPosDrive(4, 0.5, 3000);
            Bot.IntakeL.setPosition(1);
            Bot.IntakeR.setPosition(0);

            telemetry.addLine("Moving back to Original Pos.");
            telemetry.update();
            //Moves back to original position
            RunToPosDrive(4, -0.5, 3000);
            Bot.IntakeL.setPosition(0.5);
            Bot.IntakeR.setPosition(0.5);

            //Rotates back into original position
            runtime.reset();

                Rotate(1, 0.2, 1000);

            //Just for testing - stops Opmode
        }
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
        ZeroDrive();
        RunwithEncAll();
    }

    private void RunToPos(DcMotor motor, int inches, double power){
        motor.setPower(power);
        motor.setTargetPosition(inches);
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
        ZeroDrive();
        RunwithEncAll();
    }

    private void RunwithEncAll(){
        Bot.FrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Bot.FrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Bot.BackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Bot.BackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
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
}

