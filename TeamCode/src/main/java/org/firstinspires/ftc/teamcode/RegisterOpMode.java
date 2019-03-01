package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpModeManager;
import com.qualcomm.robotcore.eventloop.opmode.OpModeRegistrar;

import org.firstinspires.ftc.teamcode.TeleOp6899.TeleOp6899;

public class RegisterOpMode {
    @OpModeRegistrar
    public static void registerMyOpModes(OpModeManager manager){
        manager.register("TeleOp6899", TeleOp6899.class);
    }
}
