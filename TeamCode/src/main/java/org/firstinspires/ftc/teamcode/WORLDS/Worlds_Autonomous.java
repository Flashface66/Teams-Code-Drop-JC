package org.firstinspires.ftc.teamcode.WORLDS;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous
public class Worlds_Autonomous extends LinearOpMode {

    private static final double     CPMR    = 1440 ;    // TETRIX Motor Encoder
    private static final double     DGR   = 2.0 ;     // < 1.0 if geared UP
    private static final double     WDI   = 4.0 ;     // For figuring circumference
    private static final double     CPI         = (CPMR * DGR) / (WDI * 3.1415);
    private HardwareWorlds RB = new HardwareWorlds();

    @Override
    public void runOpMode() throws InterruptedException {


        RB.init(hardwareMap);

        Stop();
        RB.BackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        RB.BackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        RB.FrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        RB.FrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        waitForStart();



    }

    private void FBMove(double inches, double timeout){
        int target;

        if (opModeIsActive()){

            target = RB.FrontRight.getCurrentPosition() + (int)(inches * CPI);
            RB.FrontLeft.setTargetPosition(target);
        }

    }

    private void Strafe(double inches, double timeout){

    }

    private void Rotate(double inches, double timeout){

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

    private void RunusingEnc(DcMotor motor, int inches, double timeout, double Power){
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor.setTargetPosition(inches);
        motor.setPower(Power);
    }

    private void RunwithoutEnc(){

    }

}

