package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "Auto Quick Test")

public class AutonomousT3st extends LinearOpMode {
    private DcMotor FrontLeft, FrontRight,
            BackRight, BackLeft, Lift,
            Joint,Joint2;
    private Servo IntakeL,IntakeR;
    private enum CompetitionLegs{
                                Delatch, Mineral, Marker, Crater} CompetitionLegs Legs;
    private double TimeOut = 5000;
    private ElapsedTime runtime = new ElapsedTime();

private void RunWithEnc(DcMotor motor){
    motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
}
private void RunNoEnc(DcMotor motor){
    motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
}
private void RunNoEncAll(){
    FrontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    FrontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    BackLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    BackRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    Lift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    Joint.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    Joint2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
}
private void RunWithEncAll(){
    FrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    FrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    BackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    BackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    Lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    Joint.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    Joint2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
}
private void StopResetEnc(DcMotor motor){
    motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
}
private void StopResetAllEnc(){
    FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    Lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    Joint.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    Joint2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
}
private void RunToPos(DcMotor motor, int inches,double power){
    motor.setPower(power);
    motor.setTargetPosition(inches);
}
private void RunToPosDrive(int inches, double power){
    FrontRight.setPower(power);
    BackLeft.setPower(power);
    FrontLeft.setPower(power);
    BackRight.setPower(power);
    FrontLeft.setTargetPosition(inches);
    FrontRight.setTargetPosition(inches);
    BackRight.setTargetPosition(inches);
    BackLeft.setTargetPosition(inches);
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
private int BusyDrive() {
    int val;
    if (!FrontRight.isBusy() && !FrontLeft.isBusy() &&
            !BackLeft.isBusy() && !BackLeft.isBusy()) {
        FrontRight.setPower(0);
        BackLeft.setPower(0);
        FrontLeft.setPower(0);
        BackRight.setPower(0);
        runtime.reset();
        val = 1;
    }else {
        val = 2;
    }
    return val;
}
private void Rotate(int inches,double power){
    FrontRight.setPower(power);
    BackLeft.setPower(power);
    FrontLeft.setPower(power);
    BackRight.setPower(power);
    FrontLeft.setTargetPosition(inches);
    FrontRight.setTargetPosition(inches);
    BackRight.setTargetPosition(inches);
    BackLeft.setTargetPosition(inches);
}


    @Override
    public void runOpMode() throws InterruptedException {
      /*
          Mapping each hardware device to the phone configuration file
         */
        FrontLeft = hardwareMap.dcMotor.get("Fleft");
        FrontRight = hardwareMap.dcMotor.get("Fright");
        BackRight = hardwareMap.dcMotor.get("BackRight");
        BackLeft = hardwareMap.dcMotor.get("BackLeft");
        Joint = hardwareMap.dcMotor.get("Joint");
        Joint2 = hardwareMap.dcMotor.get("Joint2");
        Lift = hardwareMap.dcMotor.get("Lift");
        IntakeL = hardwareMap.servo.get("IntakeL");
        IntakeR = hardwareMap.servo.get("IntakeR");
        /*
        Setting the stops for the Robot.
            This makes the motor's activity, once their value is zero, to act as a brake.
        */
        FrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Joint.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Joint2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        /*
         Setting the Direction of each motor where needed
         */
        FrontRight.setDirection(DcMotorSimple.Direction.FORWARD);//Reverse
        FrontLeft.setDirection(DcMotorSimple.Direction.REVERSE);//Forward
        BackLeft.setDirection(DcMotorSimple.Direction.REVERSE);//Forward
        BackRight.setDirection(DcMotorSimple.Direction.FORWARD);//Reverse

        StopResetAllEnc();
        RunWithEncAll();
        runtime.reset();
        telemetry.addLine("Waiting for start");
        telemetry.addData("Lift Current Position",Lift.getCurrentPosition());
        telemetry.update();
        waitForStart();
        telemetry.addData("Lift",Lift.getCurrentPosition());
        int tester = 0;
        while (opModeIsActive()){


            if (runtime.seconds() < TimeOut && opModeIsActive()&&Legs == CompetitionLegs.Delatch){
                tester = Busy(Lift);
                if (tester == 2) {
                    RunToPos(Lift, 20, .5);
                    telemetry.addData("Lift", Lift.getCurrentPosition());
                }if (!Lift.isBusy()){
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
                    Legs=CompetitionLegs.Mineral;
                }
            }


            if (runtime.seconds() < TimeOut && opModeIsActive() &&Legs == CompetitionLegs.Mineral){
               if (tester == 2)
                BusyDrive();
                RunToPosDrive(20,0.8);
                telemetry.addLine("~~~~~~~~~~~~~~~~~~~Mineral~~~~~~~~~~~");
                telemetry.addData("Front Left ",FrontLeft.getCurrentPosition());
                telemetry.addData("Back Left ",BackLeft.getCurrentPosition());
                telemetry.addData("Front Right ",FrontRight.getCurrentPosition());
                telemetry.addData("Back Right ",BackRight.getCurrentPosition());
            }

            telemetry.update();
        }
    }
}
