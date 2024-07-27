package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import java.io.*;
import java.lang.Thread;


@TeleOp
public class DriveNormal extends LinearOpMode {

    private DcMotorEx FR, FL, BL, BR, LSlides,RSlides;
    private double FRP, FLP, BLP, BRP;
    private Servo claw, claw2, drone, Larm, Rarm, wrist;
    //private CRServo axax;


    double MIN_POSITION = 0, MAX_POSITION = 1;
    double contPower;
    ElapsedTime eTime = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
    private boolean idk =false;
    public class Arm extends Thread {
        public void run() {
            double wristPosition = 0;
            double armPos = 0.04;
            while (!isInterrupted()) {
                if (gamepad2.right_bumper) {
                    armPos += 0.005;
                    wristPosition -= 0.005;
                    if (wristPosition < 0.45) {
                        wristPosition = 0.45;
                    }
                    if (armPos > 0.8) {
                        armPos = 0.8;
                    }
                    Larm.setPosition(armPos);
                    Rarm.setPosition(armPos);
                    wrist.setPosition(wristPosition);
                    claw.setPosition(0);
                    claw2.setPosition(1);
                }
                if (gamepad2.left_bumper) {
                    armPos = 0.04;
                    wristPosition = 0.71;
                    Larm.setPosition(armPos);
                    Rarm.setPosition(armPos);
                    wrist.setPosition(wristPosition);
                    claw.setPosition(0);
                    claw2.setPosition(1);
                }
                try {
                    Thread.sleep(5); // Add a small delay to prevent the thread from consuming too much CPU
                } catch (InterruptedException e) {
                    break; // Exit the loop if the thread is interrupted
                }
            }
        }
    }
        @Override
    public void runOpMode() throws InterruptedException {
        BL = hardwareMap.get(DcMotorEx.class, "BL");
        BR = hardwareMap.get(DcMotorEx.class, "BR");
        FL = hardwareMap.get(DcMotorEx.class, "FL");
        FR = hardwareMap.get(DcMotorEx.class, "FR");
        Larm = hardwareMap.get(Servo.class, "Larm");
        Rarm = hardwareMap.get(Servo.class, "Rarm");
        claw = hardwareMap.get(Servo.class, "claw1");
        claw2 = hardwareMap.get(Servo.class, "claw2");
        LSlides = hardwareMap.get(DcMotorEx.class,"LSlides");
        RSlides = hardwareMap.get(DcMotorEx.class,"RSlides");
        drone = hardwareMap.get(Servo.class,"drone");
        wrist = hardwareMap.get(Servo.class, "wrist");
        //axax = hardwareMap.get(CRServo.class,"x");

        double dronePos =0.65;
        FR.setDirection(DcMotorEx.Direction.FORWARD);
        BR.setDirection(DcMotorEx.Direction.FORWARD);
        double speedDivide = 1;
        double clawPosition, claw2Position, clawvar, claw2var;
        BL.setDirection(DcMotorEx.Direction.REVERSE);//switched from BR TO BL
        FL.setDirection(DcMotorEx.Direction.REVERSE);//switched from FR TO FL
        Rarm.setDirection(Servo.Direction.REVERSE);

        BL.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        FL.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        BR.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        FR.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        LSlides.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        RSlides.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        waitForStart();
        Arm armThread= new Arm();
        armThread.start();

        clawPosition = 0;
        claw2Position = 0;
        clawvar = 0;
        claw2var = 0;

        while(opModeIsActive()) {
            double y = 0.8*(Math.pow(-gamepad1.left_stick_y,2))*Math.signum(-gamepad1.left_stick_y); //y value is inverted
            double x = 0.8*(Math.pow(gamepad1.left_stick_x, 2))*Math.signum(gamepad1.left_stick_x);
            double rx = -gamepad1.right_stick_x;


            //claw
            if (gamepad2.right_trigger > 0.2) {
                clawPosition = 0.17;
                claw2Position = 0.2;}
            if (gamepad2.left_trigger > 0.2) {
                clawPosition = 0;
                claw2Position = 0;}


            //slides
            if (gamepad2.dpad_up){

                LSlides.setPower(0.8);
                RSlides.setPower(-0.8);
                claw.setPosition(0);
                claw2.setPosition(1);
            } else if (gamepad2.dpad_down){
                LSlides.setPower(-0.8);
                RSlides.setPower(0.8);
                claw.setPosition(0);
                claw2.setPosition(1);
            } else {
                LSlides.setPower(0);
                RSlides.setPower(0);
            }

            //drone
            if (gamepad1.x) {
                drone.setPosition(1);
            }


            //y+x+rx
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


            claw.setPosition(Range.clip(clawPosition, MIN_POSITION, MAX_POSITION));
            claw2.setPosition(Range.clip(1-claw2Position, MIN_POSITION, MAX_POSITION));
            //arm2.setPower(-contPower);

            telemetry.addData("wrist position", wrist.getPosition());
            telemetry.addData("clawPosition",clawPosition);
            telemetry.addData("arm",Larm.getPosition());
            telemetry.addData("etime",eTime.time());
            telemetry.addData("Rslides",RSlides.getCurrentPosition());
            telemetry.addData("LSlides",LSlides.getCurrentPosition());
            telemetry.addData("clawvar",clawvar);
            telemetry.addData("claw2var",claw2var);

            telemetry.update();
        }

    }

}
