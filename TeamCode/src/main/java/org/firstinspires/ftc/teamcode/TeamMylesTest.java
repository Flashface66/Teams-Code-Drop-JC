package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

@TeleOp
public class TeamMylesTest extends LinearOpMode
{

    private DcMotor FrontR,FrontL,BackR,BackL, IntakeL,IntakeR, trayLift;
    private Servo trayDispenserL, trayDispenserR;

    @Override
    public void runOpMode() throws InterruptedException {

        // Drive train and intake system
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
        BackL.setDirection(DcMotor.Direction.REVERSE);

        // Tray control
        trayDispenserL = hardwareMap.servo.get("TrayDispLeft");
        trayDispenserR = hardwareMap.servo.get("TrayDispRight");
        trayDispenserL.setDirection(Servo.Direction.REVERSE); // Both servos are facing each other
        trayLift = hardwareMap.dcMotor.get("TrayLift");

        waitForStart();

        while (opModeIsActive()){
            MovementSys();
            CollectionSys();
            trayControlSys();
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
        BackL.setPower(- gamepad1.left_stick_y);
        telemetry.addLine("How fast are you?");
        telemetry.addData("FAST AS FUCK BOI", Power1);
    }

    private void CollectionSys(){
        if (gamepad1.left_bumper){
            IntakeL.setPower(-1);
            IntakeR.setPower(-1);
        }else if (gamepad1.right_bumper){
            IntakeL.setPower(-1);
            IntakeR.setPower(-1);
        }else
        {
            IntakeL.setPower(0);
            IntakeR.setPower(0);
        }
    }

    private void trayControlSys()
    {
        // control the tray dispenser
        if (gamepad1.dpad_up)
        {
            trayDispenserL.setPosition(1.0);
            trayDispenserR.setPosition(1.0);
        } else if (gamepad1.dpad_down)
        {
            trayDispenserL.setPosition(0);
            trayDispenserR.setPosition(0);
        }


        // control the tray lift
        if (gamepad1.dpad_right)
        {
            trayLift.setPower(1.0);
        } else if (gamepad1.dpad_left)
        {
            trayLift.setPower(-1.0);
        }
    }
}

