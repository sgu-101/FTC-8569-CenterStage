package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp
public class DriveNormal extends LinearOpMode {

    private DcMotorEx FR, FL, BL, BR, hang;
    private double FRP, FLP, BLP, BRP;
    private CRServo drone;


    @Override
    public void runOpMode() throws InterruptedException {
        BL = hardwareMap.get(DcMotorEx.class, "BL");
        BR = hardwareMap.get(DcMotorEx.class, "BR");
        FL = hardwareMap.get(DcMotorEx.class, "FL");
        FR = hardwareMap.get(DcMotorEx.class, "FR");
        hang = hardwareMap.get(DcMotorEx.class,"hang");
        drone = hardwareMap.get(CRServo.class, "drone");

        FR.setDirection(DcMotorEx.Direction.FORWARD);
        BR.setDirection(DcMotorEx.Direction.FORWARD);
        double speedDivide = 1;
        BL.setDirection(DcMotorEx.Direction.REVERSE);//switched from BR TO BL
        FL.setDirection(DcMotorEx.Direction.REVERSE);//switched from FR TO FL

        waitForStart();

        while(opModeIsActive()) {
            double y = 0.8*(Math.pow(-gamepad1.left_stick_y,2))*Math.signum(-gamepad1.left_stick_y); //y value is inverted
            double x = 0.8*(Math.pow(gamepad1.left_stick_x, 2))*Math.signum(gamepad1.left_stick_x);
            double rx = gamepad1.right_stick_x;
            if (gamepad1.left_bumper){
                speedDivide = 4;
            } else {
                speedDivide = 1.3;
                //speedDivide = 1;
            }
            if (gamepad1.x){
                drone.setPower(0.3);
                sleep(100);
                drone.setPower(0);
            }

            if(gamepad1.left_trigger>0.2){
                hang.setPower(0.75);
            } else if (gamepad1.right_trigger>0.2){
                hang.setPower(-0.5);
            } else{
                hang.setPower(0);
            }
            double frontLeftSpd=(y+x+rx)/speedDivide; //y+x+rx
            double frontRightSpd=(y-x-rx)/speedDivide;
            FLP = (y + x - rx)/speedDivide;
            FRP = (y - x + rx)/speedDivide;
            BLP = (y - x - rx)/speedDivide;
            BRP = (y + x + rx)/speedDivide;

            double denominator = Math.max(Math.abs(y)+Math.abs(x)+Math.abs(rx),1);
            FLP /= denominator;
            FRP /= denominator;
            BLP /= denominator;
            BRP /= denominator;

            FL.setPower(FLP);
            FR.setPower(FRP);
            BL.setPower(BLP);
            BR.setPower(BRP);
            BL.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
            FL.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
            BR.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
            FR.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

            //telemetry.addData("state",drone.getPower());
            //telemetry.addData("a",gamepad1.a);
        }

    }

}