package org.firstinspires.ftc.teamcode.WORLDS;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "Judguing")
public class Judging extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();

    private HardwareWorlds RB = new HardwareWorlds();

    @Override
    public void runOpMode() {

        waitForStart();

        Extend(700);

        Deposit(500);

    }


private void Extend(int sleeptimer){

  RB.Extend.setPower(-0.5);
    sleep(sleeptimer);
}


private void Deposit(int sleeptimer){
        RB.Deposit.setPower(0.5);
    sleep(sleeptimer);
}

}
