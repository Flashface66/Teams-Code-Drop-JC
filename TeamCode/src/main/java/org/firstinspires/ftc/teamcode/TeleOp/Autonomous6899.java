package org.firstinspires.ftc.teamcode.TeleOp;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.DogeCV;
import com.disnodeteam.dogecv.detectors.roverrukus.GoldAlignDetector;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import java.util.ArrayList;
import java.util.List;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.XYZ;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.YZX;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesReference.EXTRINSIC;
import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.BACK;
import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.FRONT;

@Autonomous(name = "Autonomous 6899")
public class Autonomous6899 extends LinearOpMode {

    private Hardware6899 Bot = new Hardware6899();

    private enum CompetitionLegs{
        Delatch,Vuforia, Mineral, Marker, Crater, Finish}
    CompetitionLegs Legs;

    private GoldAlignDetector detector;

    private ElapsedTime runtime = new ElapsedTime();

    private double getruntime;

    private int y = 2;

    private String Rucks;

    private static final String VUFORIA_KEY = "AU3/vpT/////AAABmSeNX5cWlUasitZlrRdsr6UXUxmuHVz0w0+A2hsEHEDmOXhnedTh/pyJSdeSzznNkYLUtxa5SEUqrwClVdYYAOZTo8zEUGDNUdfUSd8mKmjezFt64DRUyPb2EwE+LG172I4yK/jdryp1sHIfD1uVt3pqaz438j/dRQTnqED2yWQ28inuRMv0je2GcLDYWeeIrcKKwaMkSHYGikcu6knLTQSXcaqcdJkXa+d2teUqzGcH5pmg0PzzTffyjIRuj342Ivwm4+e8hAY1IwhsyTHHhvs+7w6G1qRX+yR4udJh4S6lUQU/ZYwn/7IQJqATkM7uOeBTBm4UNX1uz+8uRP0ZS2qJFWb+zagMlA34x+/9+hVh";

    private static final float mmPerInch        = 25.4f;
    private static final float mmFTCFieldWidth  = (12*6) * mmPerInch;       // the width of the FTC field (from the center point to the outer panels)
    private static final float mmTargetHeight   = (6) * mmPerInch;          // the height of the center of the target image above the floor

    // Select which camera you want use.  The FRONT camera is the one on the same side as the screen.
    // Valid choices are:  BACK or FRONT
    private static final VuforiaLocalizer.CameraDirection CAMERA_CHOICE = BACK;

    private OpenGLMatrix lastLocation = null;

    private VuforiaLocalizer vuforia;

    @Override
    public void runOpMode() throws InterruptedException {

        detector = new GoldAlignDetector(); // Create detector
        detector.init(hardwareMap.appContext, CameraViewDisplay.getInstance()); // Initialize it with the app context and camera
        detector.useDefaults(); // Set detector to use default settings
        //tuning
        detector.alignSize = 250; // How wide (in pixels) is the range in which the gold object will be aligned. (Represented by green bars in the preview)
        detector.alignPosOffset = -2; // How far from center frame to offset this alignment zone.
        detector.downscale = 0.4; // How much to downscale the input frames

        detector.areaScoringMethod = DogeCV.AreaScoringMethod.MAX_AREA; // Can also be PERFECT_AREA
        //detector.perfectAreaScorer.perfectArea = 10000; // if using PERFECT_AREA scoring
        detector.maxAreaScorer.weight = 0.005; //
        detector.ratioScorer.weight = 5; //
        detector.ratioScorer.perfectRatio = 1.0; // Ratio adjustment



        Bot.init(hardwareMap);
        int x = 1;
        detector.enable();
        boolean targetVisible = false;
        waitForStart();

        while (opModeIsActive()) {

            if (detector.getAligned()) {
                telemetry.addLine("Found Cube!");
                telemetry.update();

                telemetry.addLine("Grabbing the Cube!");
                telemetry.update();
                //Goes to cube and grabs it
                Bot.IntakeL.setPosition(0);
                Bot.IntakeR.setPosition(1);
                RunToPosDrive(1, 0.25, 3000);

                telemetry.addLine("Moving back to Original Pos.");
                telemetry.update();
                //Moves back to original position
                Bot.IntakeL.setPosition(0.5);
                Bot.IntakeR.setPosition(0.5);
                RunToPosDrive(1, -0.25, 3000);
                detector.disable();
                x = 0;
            }

            if (!detector.getAligned() && !detector.isFound() && opModeIsActive() && x != 0 || !detector.isFound() && opModeIsActive() && x != 0) {
                telemetry.addLine("Looking for Cube first side");
                telemetry.update();
                x = 1;
                //Looking for Cube
                runtime.reset();
                RotateAli(2, .1, 1800);
                if (detector.getAligned()) {
                    telemetry.addLine("Found Cube! 1st side");
                    telemetry.update();
                    getruntime = runtime.seconds();
                    detector.disable();
                    y=4;
                    x = 2;
                    telemetry.addLine("Grabbing the Cube!");
                    telemetry.update();
                    //Goes to cube and grabs it
                    Bot.IntakeL.setPosition(0);
                    Bot.IntakeR.setPosition(1);
                    RunToPosDrive(1, 0.25, 3000);

                    telemetry.addLine("Moving back to Original Pos.");
                    telemetry.update();
                    //Moves back to original position
                    Bot.IntakeL.setPosition(0.5);
                    Bot.IntakeR.setPosition(0.5);
                    RunToPosDrive(1, -0.25, 3000);
                    if (y == 3) {
                        runtime.reset();
                        RotateBack(1, -0.25, 1000);
                    } else {
                        runtime.reset();
                        RotateBack(1, 0.25, 1000);
                    }
                }

                //Opposite direction
                if (x == 1) {

                    telemetry.addLine("Looking for Cube second side");
                    telemetry.update();
                    runtime.reset();
                    RotateAli(2, -.1, 2500);
                    if (detector.getAligned()) {
                        y = 3;
                        telemetry.addLine("Found Cube!2nd side");
                        telemetry.update();
                        getruntime = runtime.seconds();
                        detector.disable();

                        x = 2;
                        telemetry.addLine("Grabbing the Cube!");
                        telemetry.update();
                        //Goes to cube and grabs it
                        Bot.IntakeL.setPosition(0);
                        Bot.IntakeR.setPosition(1);
                        RunToPosDrive(1, 0.25, 3000);

                        telemetry.addLine("Moving back to Original Pos.");
                        telemetry.update();
                        //Moves back to original position
                        Bot.IntakeL.setPosition(0.5);
                        Bot.IntakeR.setPosition(0.5);
                        RunToPosDrive(1, -0.25, 3000);
                        if (y == 3) {
                            runtime.reset();
                            RotateBack(1, -0.25, 1000);
                        } else {
                            runtime.reset();
                            RotateBack(1, 0.25, 1000);
                        }
                    }
                }
            }
            telemetry.addLine("Init Vuforia");
            telemetry.update();
            //Vuforia
            int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId",
                    "id", hardwareMap.appContext.getPackageName());
            //VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

            //TODO Use parameterless if you do not need to see the camera commented out below
            VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

            parameters.vuforiaLicenseKey = VUFORIA_KEY ;
            parameters.cameraDirection   = CAMERA_CHOICE;

            vuforia = ClassFactory.getInstance().createVuforia(parameters);

            //TODO Use the data here to get the different locations on the field.
            VuforiaTrackables targetsRoverRuckus = this.vuforia.loadTrackablesFromAsset("RoverRuckus");
            VuforiaTrackable blueRover = targetsRoverRuckus.get(0);
            blueRover.setName("Blue-Rover");
            VuforiaTrackable redFootprint = targetsRoverRuckus.get(1);
            redFootprint.setName("Red-Footprint");
            VuforiaTrackable frontCraters = targetsRoverRuckus.get(2);
            frontCraters.setName("Front-Craters");
            VuforiaTrackable backSpace = targetsRoverRuckus.get(3);
            backSpace.setName("Back-Space");

            List<VuforiaTrackable> allTrackables = new ArrayList<VuforiaTrackable>();
            allTrackables.addAll(targetsRoverRuckus);

            //Translation
            //Blue Rover
            OpenGLMatrix blueRoverLocationOnField = OpenGLMatrix
                    .translation(0, mmFTCFieldWidth, mmTargetHeight)
                    .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 0));
            blueRover.setLocation(blueRoverLocationOnField);

            //Red Footprint
            OpenGLMatrix redFootprintLocationOnField = OpenGLMatrix
                    .translation(0, -mmFTCFieldWidth, mmTargetHeight)
                    .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 180));
            redFootprint.setLocation(redFootprintLocationOnField);

            //Front Craters
            OpenGLMatrix frontCratersLocationOnField = OpenGLMatrix
                    .translation(-mmFTCFieldWidth, 0, mmTargetHeight)
                    .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0 , 90));
            frontCraters.setLocation(frontCratersLocationOnField);

            //Back Space
            OpenGLMatrix backSpaceLocationOnField = OpenGLMatrix
                    .translation(mmFTCFieldWidth, 0, mmTargetHeight)
                    .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, -90));
            backSpace.setLocation(backSpaceLocationOnField);

            final int CAMERA_FORWARD_DISPLACEMENT  = 110;   // eg: Camera is 110 mm in front of robot center
            final int CAMERA_VERTICAL_DISPLACEMENT = 200;   // eg: Camera is 200 mm above ground
            final int CAMERA_LEFT_DISPLACEMENT     = 0;     // eg: Camera is ON the robot's center line

            OpenGLMatrix phoneLocationOnRobot = OpenGLMatrix
                    .translation(CAMERA_FORWARD_DISPLACEMENT, CAMERA_LEFT_DISPLACEMENT, CAMERA_VERTICAL_DISPLACEMENT)
                    .multiplied(Orientation.getRotationMatrix(EXTRINSIC, YZX, DEGREES,
                            CAMERA_CHOICE == FRONT ? 90 : -90, 0, 0));

            //Let all the trackable listeners know where the phone is
            for (VuforiaTrackable trackable : allTrackables)
            {
                ((VuforiaTrackableDefaultListener)trackable.getListener()).setPhoneInformation(phoneLocationOnRobot, parameters.cameraDirection);
            }
            targetsRoverRuckus.activate();
            telemetry.addLine("Rotate");
            telemetry.update();
            Rotate(2, .2, 1600);
            RunToPosDrive(1, 0.5, 1000);

            //Vuforia time!
            while(opModeIsActive() && !targetVisible){
                telemetry.addLine("Vuforia While loop");
                telemetry.update();
                for (VuforiaTrackable trackable : allTrackables) {
                    if (((VuforiaTrackableDefaultListener) trackable.getListener()).isVisible()) {
                        telemetry.addData("Visible Target", trackable.getName());
                        telemetry.update();
                        Rucks = trackable.getName();
                        targetVisible = true;

                        // getUpdatedRobotLocation() will return null if no new information is available since
                        // the last time that call was made, or if the trackable is not currently visible.
                        OpenGLMatrix robotLocationTransform = ((VuforiaTrackableDefaultListener) trackable.getListener()).getUpdatedRobotLocation();
                        if (robotLocationTransform != null) {
                            lastLocation = robotLocationTransform;
                        }
                        break;
                    }
                }
                //Original Pos
                RunToPosDrive(1, -0.5, 900);
                Rotate(1,-0.5,150);
                RunToPosDrive(1, 0.5, 1450);
                switch (Rucks){
                    case "Blue-Rover":
                        BlueRover();
                        break;
                    case "Red-Footprint":
                        RedFootprint();
                        break;
                    case "Front-Craters":
                        FrontCraters();
                        break;
                    case "Back-Space":
                        BackSpace();
                        break;
                    default:
                        break;
                }
            }

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

    private void RotateAli(int inches,double power, int sleeptimer){
        while (!detector.getAligned() && opModeIsActive() && runtime.seconds() < 1.2 && y ==2) {
            Bot.FrontLeft.setTargetPosition(inches);
            Bot.FrontRight.setTargetPosition(inches);
            Bot.BackRight.setTargetPosition(inches);
            Bot.BackLeft.setTargetPosition(inches);
            Bot.FrontRight.setPower(-power);
            Bot.BackLeft.setPower(power);
            Bot.FrontLeft.setPower(power);
            Bot.BackRight.setPower(-power);
            if (detector.getAligned()) {
                telemetry.addLine("Found Cube!");
                telemetry.update();
                getruntime = runtime.seconds();
                ZeroDrive();
            }
            if (detector.getAligned()){
                y=3;
            }
        }
        while (!detector.getAligned() && opModeIsActive() && runtime.seconds() < 3.2 && y ==3) {
            Bot.FrontLeft.setTargetPosition(inches);
            Bot.FrontRight.setTargetPosition(inches);
            Bot.BackRight.setTargetPosition(inches);
            Bot.BackLeft.setTargetPosition(inches);
            Bot.FrontRight.setPower(-power);
            Bot.BackLeft.setPower(power);
            Bot.FrontLeft.setPower(power);
            Bot.BackRight.setPower(-power);
            if (detector.getAligned()) {
                telemetry.addLine("Found Cube!");
                telemetry.update();
                getruntime = runtime.seconds();
                ZeroDrive();
            }
        }
        sleep(sleeptimer);
        ZeroDrive();
        RunwithEncAll();
    }

    private void RotateBack(int inches,double power,int sleeptimer){
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

    private void DepositMarker(){
        /*TODO Add to config file and hardware code:
TODO                     The Servo
         */
        sleep(1000);
    }

    private void Step2(){
        RunToPosDrive(3,-0.5,2200);
        DepositMarker();
    }

    private void Step3(){
        RunToPosDrive(3,1,1700);
        requestOpModeStop();
    }

    //Continuation of Autonomous

    //Facing flagpoles
    private void BlueRover(){

        //Step Three Rotate facing crater
        telemetry.addLine("Step One");
        telemetry.update();
        Rotate(1,-0.5,1150);

        //Step Four Reverse and deposit team marker
        telemetry.addLine("Step Two");
        telemetry.update();
        Step2();

        //Step Five Move forward to crater
        telemetry.addLine("Step Three");
        telemetry.update();
        Step3();
    }

    //Facing wall nearest to Office
    private void RedFootprint(){

        //Step Three Rotate facing crater
        telemetry.addLine("Step One");
        telemetry.update();
        Rotate(1,-0.5,1150);

        //Step Four Reverse and deposit team marker
        telemetry.addLine("Step Two");
        telemetry.update();
        Step2();

        //Step Five Move forward to crater
        telemetry.addLine("Step Three");
        telemetry.update();
        Step3();

    }

    //Facing away from Robotics door
    private void FrontCraters(){

        //Step Three Rotate facing crater
        telemetry.addLine("Step One");
        telemetry.update();
        Rotate(1,0.5,750);

        //Step Four Reverse and deposit team marker
        telemetry.addLine("Step Two");
        telemetry.update();
        Step2();

        //Step Five Move forward to crater
        telemetry.addLine("Step Three");
        telemetry.update();
        Step3();
    }

    //Facing Robotics door
    private void BackSpace(){
        //Step Three Rotate facing crater
        telemetry.addLine("Step One");
        telemetry.update();
        Rotate(1,0.5,750);

        //Step Four Reverse and deposit team marker
        telemetry.addLine("Step Two");
        telemetry.update();
        Step2();

        //Step Five Move forward to crater
        telemetry.addLine("Step Three");
        telemetry.update();
        Step3();

    }

}
