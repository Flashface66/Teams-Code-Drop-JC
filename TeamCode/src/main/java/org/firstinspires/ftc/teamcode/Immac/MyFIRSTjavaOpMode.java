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

package org.firstinspires.ftc.teamcode.Immac;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;



@TeleOp(name="Basic: Linear OpMode", group="Linear Opmode")
@Disabled
public class MyFIRSTjavaOpMode extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftDrive = null;
    private DcMotor rightDrive = null;
    private DcMotor lift = null;
    private Servo intake = null;
    private Servo basket = null;
    private Servo arm1 = null;
    private Servo arm2 = null;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        leftDrive = hardwareMap.get(DcMotor.class, "Trackleft");
        rightDrive = hardwareMap.get(DcMotor.class, "TrackRight");
        lift = hardwareMap.get(DcMotor.class, "lift");
        intake = hardwareMap.get(Servo.class, "intake");
        basket = hardwareMap.get(Servo.class, "basket");
        arm1 = hardwareMap.get(Servo.class, "arm1");
        arm2 = hardwareMap.get(Servo.class, "arm2");


        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery
        leftDrive.setDirection(DcMotor.Direction.FORWARD);
        rightDrive.setDirection(DcMotor.Direction.REVERSE);
        lift.setDirection(DcMotor.Direction.FORWARD);
        intake.setDirection(Servo.Direction.FORWARD);
        basket.setDirection(Servo.Direction.FORWARD);
        arm1.setDirection(Servo.Direction.FORWARD);
        arm2.setDirection(Servo.Direction.REVERSE);


        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            Servos();
            Lift();
            driving();
        }
    }


    private void Lift(){
        double liftPower;
//Moving the lift
        if (gamepad2.left_bumper) {
            liftPower = 1;

        } else if (gamepad2.right_bumper) {
            liftPower = -1;
        } else {
            liftPower = 0;
        }

        lift.setPower(liftPower);
    }

    private void Servos() {
        //arm1 arm2 intake basket
        double arm1pos=1, arm2pos=1, basketpos=1;
//    telling the range of the arms and moving the arms
        if (gamepad2.x){
            arm1pos=Range.clip(arm1pos+0.001, 0, 0.75);
            arm2pos=Range.clip(arm2pos+0.001, 0, 0.75);
        }else if (gamepad2.y) {
            arm1pos=Range.clip(arm1pos-0.001, 0, 0.75);
            arm2pos=Range.clip(arm2pos-0.001, 0, 0.75);
        }
        telemetry.addData("Position arm1", arm1.getPosition());
        telemetry.addData("Position arm2", arm2.getPosition());
        arm1.setPosition(arm1pos);
        arm2.setPosition(arm2pos);
//      lifting the basket
        if (gamepad2.b) {
            basketpos=Range.clip(basketpos+0.001, 0, 0.75);
        }else if (gamepad2.a){
            basketpos=Range.clip(basketpos-0.001, 0, 0.75);
        }
        telemetry.addData("Position Basket", basket.getPosition());
        basket.setPosition(basketpos);

        if (gamepad2.dpad_up) {
            intake.setPosition(1);
    }else if (gamepad2.dpad_down) {
            intake.setPosition(0);
        }else {
            intake.setPosition(0.5);

        }
    }


    private void driving() {
        // Setup a variable for each drive wheel to save power level for telemetry
        double leftPower;
        double rightPower;


        // Choose to drive using either Tank Mode, or POV Mode
        // Comment out the method that's not used.  The default below is POV.

        // POV Mode uses left stick to go forward, and right stick to turn.
        // - This uses basic math to combine motions and is easier to drive straight.
        double drive = -gamepad1.left_stick_y;
        double turn = gamepad1.right_stick_x;
        leftPower = Range.clip(drive + turn, -.75, 0.75);
        rightPower = Range.clip(drive - turn, -0.75, 0.75);

        // Tank Mode uses one stick to control each wheel.
        // - This requires no math, but it is hard to drive forward slowly and keep straight.
//            leftPower  = -gamepad1.left_stick_y *0.5 ;
//            rightPower = -gamepad1.right_stick_y*0.5 ;
        // leftPower  = -gamepad1.left_stick_y *0.5;
        //  rightPower = -gamepad1.right_stick_y * 0.5;
        //  telemetry.addData("drive speed : ", "50%");
        // Send calculated power to wheels
        leftDrive.setPower(leftPower);
        rightDrive.setPower(rightPower);
        //right_Drive2.setPower(rightPower);
        // Show the elapsed game time and wheel power.
        telemetry.addData("Status", "Run Time: " + runtime.toString());
        telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftPower, rightPower);
        telemetry.update();
     /*
        //assign the values to the motor power variables
        double leftPower = 0;
        double rightPower = 0;
        run_power=gamepad1.dpad_down;
        run_power2=gamepad1.dpad_up;
        run_power3=gamepad1.dpad_left;
        run_power4=gamepad1.dpad_right;

        if(run_power2){
            speedmax=3;
        }
        if(run_power3){
            speedmax = 1;
        }

        if(run_power){
            speedmax=2;
        }
        if(run_power4){
            speedmax=4;
        }
        switch (speedmax){
            case 1:
                leftPower  = gamepad1.left_stick_y *0.5;
                rightPower = gamepad1.right_stick_y * 0.5;
                telemetry.addData("drive speed : ", "50%");
                break;
            case 2:
                leftPower  = gamepad1.left_stick_y *0.25;
                rightPower = gamepad1.right_stick_y * 0.25;
                telemetry.addData("drive speed : ", "25%");
                break;
            case 3:
                leftPower  = gamepad1.left_stick_y ;
                rightPower = gamepad1.right_stick_y ;
                telemetry.addData("drive speed : ", "billz");
                break;
            case 4:
                leftPower  = gamepad1.left_stick_y *0.75;
                rightPower = gamepad1.right_stick_y * 0.75;
                telemetry.addData("drive speed : ", "75%");
                break;


        }
        leftDrive.setPower(leftPower);
        rightDrive.setPower(rightPower);
        leftdrive2.setPower(leftPower);
        rightdrive2.setPower(rightPower);
        telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftPower, rightPower);
*/
    }
}
