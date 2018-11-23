package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpModeManager;
import com.qualcomm.robotcore.eventloop.opmode.OpModeRegistrar;

public class RegisterOpMode {
    @OpModeRegistrar
    public static void registerMyOpModes(OpModeManager manager){
        manager.register("Prodigy", MarcNew.class);
        manager.register("TeamMylesTest", TeamMylesTest.class);
        manager.register("MecaTest", Mecanumn_Test.class);


    }
}
