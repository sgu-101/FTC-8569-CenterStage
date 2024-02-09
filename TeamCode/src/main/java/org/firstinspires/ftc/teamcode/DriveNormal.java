package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

@TeleOp
public class DriveNormal extends LinearOpMode {

    private DcMotorEx FR, FL, BL, BR;
    private double FRP, FLP, BLP, BRP;
    private Servo claw, claw2, drone, Larm, Rarm;

    private CRServo wrist;
    double MIN_POSITION = 0, MAX_POSITION = 1;
    double contPower;
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
//        arm = hardwareMap.get(CRServo.class, "arm");
        //arm2 = hardwareMap.get(CRServo.class, "arm2");
        drone = hardwareMap.get(Servo.class,"drone");
        wrist = hardwareMap.get(CRServo.class, "wrist");

        double dronePos =0.65;
        FR.setDirection(DcMotorEx.Direction.FORWARD);
        BR.setDirection(DcMotorEx.Direction.FORWARD);
        double speedDivide = 1;
        double clawPosition;
        BL.setDirection(DcMotorEx.Direction.REVERSE);//switched from BR TO BL
        FL.setDirection(DcMotorEx.Direction.REVERSE);//switched from FR TO FL
        Rarm.setDirection(Servo.Direction.REVERSE);

        waitForStart();

        clawPosition = .5;

        while(opModeIsActive()) {
            double y = 0.8*(Math.pow(-gamepad1.left_stick_y,2))*Math.signum(-gamepad1.left_stick_y); //y value is inverted
            double x = 0.8*(Math.pow(gamepad1.left_stick_x, 2))*Math.signum(gamepad1.left_stick_x);
            double rx = -gamepad1.right_stick_x;
            if (gamepad1.a){
                speedDivide = 4;
            } else {
                speedDivide = 1.3;
                //speedDivide = 1;
            }


            //claw
            if ((gamepad1.right_trigger > 0) && clawPosition > MIN_POSITION) clawPosition -= .01;
            if ((gamepad1.left_trigger > 0) && clawPosition < MAX_POSITION) clawPosition += .01;


            //wrist

            if (gamepad1.dpad_left){
                wrist.setPower(0.2);
            } else if (gamepad1.dpad_right){
                wrist.setPower(-0.2);
            } else{
                wrist.setPower(0.0);
            }

            //drone
            if (gamepad1.x) {
                dronePos -= 0.01;
            }

            //slides
            if (gamepad1.right_bumper){
                Larm.setPosition(0.7);
                Rarm.setPosition(0.7);
            } else if (gamepad1.left_bumper) {
                Rarm.setPosition(0);
                Larm.setPosition(0);
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

            claw.setPosition(Range.clip(clawPosition, MIN_POSITION, MAX_POSITION));
            claw2.setPosition(Range.clip(1-clawPosition, MIN_POSITION, MAX_POSITION));
            drone.setPosition(Range.clip(dronePos, MIN_POSITION,MAX_POSITION));
            //arm2.setPower(-contPower);

            telemetry.addData("state",drone.getPosition());
            telemetry.addData("right trig",gamepad1.right_trigger);
            telemetry.update();
        }

    }

}