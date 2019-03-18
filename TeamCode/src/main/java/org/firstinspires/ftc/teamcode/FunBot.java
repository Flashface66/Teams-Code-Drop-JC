package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

public class FunBot extends LinearOpMode {

    DcMotor Fleft,Fright,
            BackL,BackR,
            Lift;
    double MpH, Speed;;
    boolean Reverseinput;
//    public enum VehicleGears
//    {Neutral,One,Two,Three,four,reverse}
//    VehicleGears Gears;
    private ElapsedTime runtime = new ElapsedTime();

    public enum VehicleGears
    {Neutral,One,Two,Three,four,reverse{};
        public VehicleGears next(){
            return values()[ordinal()+1];
        }
        public VehicleGears previous(){
            return values()[ordinal()-1];
        }
    }
    VehicleGears Gears;

    public VehicleGears getGears() {
        /*
            Controls
            Gear Up - Right Bumper
            Gear Down - Left Bumper
            Accelerate - Right Trigger
            Brake - Left Trigger
            Reverse Gear - 1 value on Joystick + Gear down
             */
        if (gamepad1.right_bumper) {
            UpGears();
        }
        if (gamepad1.left_bumper) {
            DownGears();
        }
        if (gamepad1.left_bumper && gamepad1.right_stick_y == 1)
        {
            Reverseinput = true;
            DownGears();
        }
        return Gears;
    }
    public void UpGears() {
        if (Gears == VehicleGears.reverse && runtime.seconds() > .5){
            Gears = VehicleGears.Neutral;
        }else if (Gears == VehicleGears.Neutral&& runtime.seconds() > .5){
            Gears = VehicleGears.One;
        }else if (Gears == VehicleGears.One&& runtime.seconds() > .5){
            Gears = VehicleGears.Two;
        }else if (Gears == VehicleGears.Two&& runtime.seconds() > .5){
            Gears = VehicleGears.Three;
        }else if (Gears == VehicleGears.Three&& runtime.seconds() > .5){
            Gears = VehicleGears.four;
        }
        runtime.reset();
    }
    public void DownGears(){
        if (Gears == VehicleGears.four&& runtime.seconds() > .5){
            Gears = VehicleGears.Three;
        }else if (Gears == VehicleGears.Three&& runtime.seconds() > .5){
            Gears = VehicleGears.Two;
        }else if (Gears == VehicleGears.Two&& runtime.seconds() > .5){
            Gears = VehicleGears.One;
        }else if (Gears == VehicleGears.One&& runtime.seconds() > .5){
            Gears = VehicleGears.Neutral;
        }else if (Gears == VehicleGears.Neutral&& runtime.seconds() > .5){
            Gears = VehicleGears.reverse;
        }
        runtime.reset();
    }

    public double SpeedLegs(){
        if (Gears == VehicleGears.One){
            Speed = Range.clip(Speed+0.001,0,0.2);
        }if (Gears == VehicleGears.Two){
            Speed = Range.clip(Speed+0.001,0,0.4);
        }if (Gears == VehicleGears.Three){
            Speed = Range.clip(Speed+0.001,0,0.6);
        }if (Gears == VehicleGears.four){
            Speed = Range.clip(Speed+0.001,0,1);
        }if (Gears == VehicleGears.Neutral){
            Speed = Range.clip(Speed+0.001,0,0);
        }if (Gears == VehicleGears.reverse){
            Speed = Range.clip(Speed-0.001,-1,0);
        }
        return Speed;
    }
    @Override
    public void runOpMode() throws InterruptedException {

        //Hardware Mapping
        Fleft = hardwareMap.dcMotor.get("fleft");
        Fright = hardwareMap.dcMotor.get("fright");
        BackL = hardwareMap.dcMotor.get("backl");
        BackR = hardwareMap.dcMotor.get("backr");

        Fright.setDirection(DcMotorSimple.Direction.REVERSE);
        BackR.setDirection(DcMotorSimple.Direction.REVERSE);

        telemetry.addLine("Controls");
        telemetry.addLine("Gear Up      - Right Bumper");
        telemetry.addLine("Gear Down    - Left Bumper");
        telemetry.addLine("Accelerate   - Right Trigger");
        telemetry.addLine("Brake        - Left Trigger");
        telemetry.addLine("Reverse Gear - 1 value on Joystick + Gear down");
        telemetry.update();
        waitForStart();
        Gears = VehicleGears.One;
        /*
        Controls
        Gear Up - Right Bumper
        Gear Down - Left Bumper
        Accelerate - Right Trigger
        Brake - Left Trigger
        Reverse Gear - 1 value on Joystick + Gear down
         */
        while (opModeIsActive()){
            SpeedLegs();
            if (gamepad1.right_trigger == 1){
                MpH = SpeedLegs();
            }
            else if (gamepad1.left_trigger == 1){
                MpH = Range.clip(MpH - 0.001, 0,1);
            }else
            {MpH = 0;}
            getGears();
            Fright.setPower(MpH);
            Fleft.setPower(MpH);
            BackL.setPower(MpH);
            BackR.setPower(MpH);
            if (gamepad1.right_stick_y == 1 ){
                Fright.setPower(-MpH);
                Fleft.setPower(MpH);
                BackL.setPower(MpH);
                BackR.setPower(-MpH);
            }
            if (gamepad1.right_stick_y == -1){
                Fright.setPower(MpH);
                Fleft.setPower(-MpH);
                BackL.setPower(-MpH);
                BackR.setPower(MpH);
            }
            telemetry.addLine("Controls");
            telemetry.addLine("Gear Up      - Right Bumper");
            telemetry.addLine("Gear Down    - Left Bumper");
            telemetry.addLine("Accelerate   - Right Trigger");
            telemetry.addLine("Brake        - Left Trigger");
            telemetry.addLine("Reverse Gear - 1 value on Joystick + Gear down");
            telemetry.addLine("Gear");
            telemetry.addData("Current",Gears);
            telemetry.update();
        }
    }

}
