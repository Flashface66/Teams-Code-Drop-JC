package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpModeManager;
import com.qualcomm.robotcore.eventloop.opmode.OpModeRegistrar;

public class RegisterOpMode {
    @OpModeRegistrar
    public static void registerMyOpModes(OpModeManager manager){
        manager.register("Prodigy", Prodigy.class);
        manager.register("TeamMylesTest", TeamMylesTest.class);
        manager.register("Prodigy Testing", ProdigyTesting.class);


    }
}
