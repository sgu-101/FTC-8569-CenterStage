package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp
public class ConfigurationTest extends LinearOpMode {
    private DcMotorEx FR, FL, BL, BR;
    private double FRP=0.5, FLP=0.5, BLP=0.5, BRP=0.5;

    @Override
    public void runOpMode() throws InterruptedException {
        BL = hardwareMap.get(DcMotorEx.class, "BL");
        BR = hardwareMap.get(DcMotorEx.class, "BR");
        FL = hardwareMap.get(DcMotorEx.class, "FL");
        FR = hardwareMap.get(DcMotorEx.class, "FR");

        BL.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        FL.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        BR.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        FR.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        FR.setDirection(DcMotorEx.Direction.FORWARD);
        BR.setDirection(DcMotorEx.Direction.FORWARD);
        BL.setDirection(DcMotorEx.Direction.FORWARD);//switched from BR TO BL
        FL.setDirection(DcMotorEx.Direction.FORWARD);//switched from FR TO FL

        waitForStart();
        while(opModeIsActive()) {
            if (gamepad1.left_trigger>0.5){FL.setPower(FLP);}
            else{FL.setPower(0);}
            if (gamepad1.left_bumper){BL.setPower(BLP);}
            else{BL.setPower(0);}
            if (gamepad1.right_trigger>0.5){FR.setPower(FRP);}
            else{FR.setPower(0);}
            if (gamepad1.right_bumper){BR.setPower(BRP);}
            else{BR.setPower(0);}

            telemetry.addData("FLP",FL.getPower());
            telemetry.addData("BLP",BL.getPower());
            telemetry.addData("FRP",FR.getPower());
            telemetry.addData("BRP",BR.getPower());

            telemetry.update();
        }

    }

}