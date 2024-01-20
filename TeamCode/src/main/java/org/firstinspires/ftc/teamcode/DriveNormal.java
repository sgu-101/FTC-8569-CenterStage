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
    private Servo claw;

    private Servo claw2;

    private CRServo arm;

    private CRServo arm2;
    double MIN_POSITION = 0, MAX_POSITION = 1;
    double contPower;

    @Override
    public void runOpMode() throws InterruptedException {
        BL = hardwareMap.get(DcMotorEx.class, "BL");
        BR = hardwareMap.get(DcMotorEx.class, "BR");
        FL = hardwareMap.get(DcMotorEx.class, "FL");
        FR = hardwareMap.get(DcMotorEx.class, "FR");
        claw = hardwareMap.get(Servo.class, "claw1");
        claw2 = hardwareMap.get(Servo.class, "claw2");
        arm = hardwareMap.get(CRServo.class, "arm");
        arm2 = hardwareMap.get(CRServo.class, "arm2");

        FR.setDirection(DcMotorEx.Direction.FORWARD);
        BR.setDirection(DcMotorEx.Direction.FORWARD);
        double speedDivide = 1;
        double clawPosition;
        BL.setDirection(DcMotorEx.Direction.REVERSE);//switched from BR TO BL
        FL.setDirection(DcMotorEx.Direction.REVERSE);//switched from FR TO FL

        waitForStart();

        clawPosition = .5;

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

            if (gamepad1.a && clawPosition > MIN_POSITION) clawPosition -= .01;
            if (gamepad1.b && clawPosition < MAX_POSITION) clawPosition += .01;
//            if(gamepad1.left_trigger>0.2){
//                hang.setPower(0.75);
//            } else if (gamepad1.right_trigger>0.2){
//                hang.setPower(-0.5);
//            } else{
//                hang.setPower(0);
//            }
            if (gamepad1.dpad_left)
                contPower = .20;
            else if (gamepad1.dpad_right)
                contPower = -.20;
            else
                contPower = 0.0;

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
            BL.setPower(BLP*1.2);
            BR.setPower(BRP);
            BL.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
            FL.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
            BR.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
            FR.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

            claw.setPosition(Range.clip(clawPosition, MIN_POSITION, MAX_POSITION));
            claw2.setPosition(Range.clip(1-clawPosition, MIN_POSITION, MAX_POSITION));
            arm.setPower(contPower);
            arm2.setPower(-contPower);

            //telemetry.addData("state",drone.getPower());
            //telemetry.addData("a",gamepad1.a);
        }

    }

}