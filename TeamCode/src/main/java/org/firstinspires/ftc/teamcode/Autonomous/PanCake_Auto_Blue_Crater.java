/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode.Autonomous;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.DogeCV;
import com.disnodeteam.dogecv.detectors.roverrukus.GoldAlignDetector;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="PanCake_Auto_Blue_Crater", group="JC")
@Disabled
public class PanCake_Auto_Blue_Crater extends LinearOpMode {

    /* Declare OpMode members. */
    private DcMotor FrontLeft = null;
    private DcMotor FrontRight = null;
    private DcMotor BackRight = null;
    private DcMotor BackLeft = null;
    private DcMotor Lift = null;
    private ElapsedTime runtime = new ElapsedTime();

    private GoldAlignDetector detector;



    double goldPos = 0;


    @Override
    public void runOpMode() {

        //MOTORS
        FrontLeft = hardwareMap.get(DcMotor.class,"Fleft");
        FrontRight = hardwareMap.get(DcMotor.class,"Fright");
        BackRight = hardwareMap.get(DcMotor.class,"BackRight");
        BackLeft = hardwareMap.get(DcMotor.class,"BackLeft");
        Lift = hardwareMap.get(DcMotor.class, "Lift");

        FrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        FrontRight.setDirection(DcMotorSimple.Direction.FORWARD);//Reverse
        FrontLeft.setDirection(DcMotorSimple.Direction.REVERSE);//Forward
        BackLeft.setDirection(DcMotorSimple.Direction.REVERSE);//Forward
        BackRight.setDirection(DcMotorSimple.Direction.FORWARD);//Reverse


        //CV DETECTOR
        telemetry.addData("Status", "Camera Enabled");

        detector = new GoldAlignDetector();
        detector.init(hardwareMap.appContext, CameraViewDisplay.getInstance());
        detector.useDefaults();




        // Optional Tuning
        detector.alignSize = 120; // How wide (in pixels) is the range in which the gold object will be aligned. (Represented by green bars in the preview)
        detector.alignPosOffset = 0; // How far from center frame to offset this alignment zone.
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



//        for(int i = 0; i < 100; i++){
//            goldPos = detector.getXPosition();
//            telemetry.addData("X Pos" , goldPos); // Gold X pos.
//
//            sleep(2);
//        }
//        goldPos = goldPos/99;
//        telemetry.addData("goldPos" , goldPos); // Is the bot aligned with the gold mineral
//        telemetry.update();


        goldPos = detector.getXPosition();

        dismount();
        sleep(500);

        delay();
        sleep(2000);

        Left();
        sleep(450);

        forward();
        sleep(300);

        Right();
        sleep(450);



        if(goldPos < 160){
            forward();
            sleep(250);
            
            Left();
            sleep(250);

            forward();
            sleep(3000);

            reverse();
            sleep(1300);

            Left();
            sleep(2300);

            rotateright();
            sleep(700);

            Left();
            sleep(3800);

            Right();
            sleep(5000);
            
        }else if (goldPos > 500) {
            forward();
            sleep(250);
            
            Right();
            sleep(250);

            forward();
            sleep(3000);

            reverse();
            sleep(1300);

            Left();
            sleep(2300);

            rotateright();
            sleep(700);

            Left();
            sleep(3800);

            Right();
            sleep(5000);
        }else{
            forward();
            sleep(3000);

            reverse();
            sleep(1300);

            Left();
            sleep(2300);

            rotateright();
            sleep(700);

            Left();
            sleep(3800);

            Right();
            sleep(5000);
        }



        


        
        detector.disable();



    }



    public void dismount(){
        telemetry.addData("status" , "dismount");
        telemetry.update();

        Lift.setPower(-0.6);

    }


    public void Left(){
        telemetry.addData("status" , "Left"); // Is the bot aligned with the gold mineral
        telemetry.update();

        FrontRight.setPower(-0.5);
        FrontLeft.setPower(-0.5);
        BackLeft.setPower(-0.5);
        BackRight.setPower(-0.5);

    }

    public void Right(){
        telemetry.addData("status" , "Right"); // Is the bot aligned with the gold mineral
        telemetry.update();


        FrontRight.setPower(0.5);
        FrontLeft.setPower(0.5);
        BackLeft.setPower(0.5);
        BackRight.setPower(0.5);

    }

    public void forward(){
        telemetry.addData("status" , "forward"); // Is the bot aligned with the gold mineral
        telemetry.update();

        FrontRight.setPower(0.5);
        FrontLeft.setPower(00.5);
        BackLeft.setPower(-0.5);
        BackRight.setPower(-0.5);


    }

    public void reverse(){
        telemetry.addData("status" , "reverse"); // Is the bot aligned with the gold mineral
        telemetry.update();

        FrontRight.setPower(-0.5);
        FrontLeft.setPower(-0.5);
        BackLeft.setPower(0.5);
        BackRight.setPower(0.5);

    }

    public void delay(){
        telemetry.addData("status" , "delay");
        FrontRight.setPower(0);
        FrontLeft.setPower(0);
        BackLeft.setPower(0);
        BackRight.setPower(0);



    }

    public void rotateright(){
        telemetry.addData("status" , "Rotate Right");
        FrontRight.setPower(-0.5);
        FrontLeft.setPower(0.5);
        BackLeft.setPower(0.5);
        BackRight.setPower(-0.5);


    }

    public void  rotateleft(){
        telemetry.addData("status" , "Rotate Left");
        FrontRight.setPower(0.5);
        FrontLeft.setPower(-0.5);
        BackLeft.setPower(-0.5);
        BackRight.setPower(0.5);

    }

}
