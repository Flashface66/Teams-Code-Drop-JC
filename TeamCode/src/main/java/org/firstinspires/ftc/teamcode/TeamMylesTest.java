package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name = "TeamMylesTest", group = "TeamB")
public class TeamMylesTest extends LinearOpMode {
    private DcMotor FrontR,FrontL,BackR,BackL, IntakeL,IntakeR;

    @Override
    public void runOpMode() throws InterruptedException {

        FrontL = hardwareMap.dcMotor.get("Fleft");
        FrontR = hardwareMap.dcMotor.get("Fright");
        BackR = hardwareMap.dcMotor.get("BackRight");
        BackL = hardwareMap.dcMotor.get("BackLeft");
        IntakeL = hardwareMap.dcMotor.get("IntakeL");
        IntakeR = hardwareMap.dcMotor.get("IntakeR");
        FrontL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FrontR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        IntakeL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        IntakeR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        waitForStart();

        while (opModeIsActive()){
            MovementSys();
            CollectionSys();
            telemetry.update();
        }
    }
    private void MovementSys() {
        telemetry.addLine("************Status: Running***********");
        double Power1, Power2;
        Power1 = Range.clip(gamepad1.left_stick_y, -1, 1);
        Power2 = Range.clip(gamepad1.right_stick_y, -1, 1);
        FrontR.setPower(-Power2);
        FrontL.setPower(Power1);
        BackR.setPower(-Power2);
        BackL.setPower(-Power1);
        telemetry.addLine("How fast are you?");
        telemetry.addData("Very fast man", Power1);
    }

    private void CollectionSys(){
        if (gamepad1.left_bumper){
            IntakeL.setPower(1);
            IntakeR.setPower(1);
        }else if (gamepad1.right_bumper){
            IntakeL.setPower(-1);
            IntakeR.setPower(-1);
        }else
        {
            IntakeL.setPower(0);
            IntakeR.setPower(0);
        }
    }
}

