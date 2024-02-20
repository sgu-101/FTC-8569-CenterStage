package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.opencv.core.Mat;
import com.qualcomm.robotcore.hardware.Servo;


import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;
import org.openftc.easyopencv.OpenCvPipeline;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Core;


@Autonomous
public class rightRed extends LinearOpMode {

    private DcMotor backLeft;
    private DcMotor backRight;
    private DcMotor frontLeft;
    private DcMotor frontRight;
    private Servo claw;
    OpenCvCamera cam = null;
    boolean done = false;

    private int width= 1280,height = 720;

    @Override
    public void runOpMode(){
        backLeft = hardwareMap.get(DcMotor.class, "BL");
        backRight = hardwareMap.get(DcMotor.class, "BR");
        frontLeft = hardwareMap.get(DcMotor.class, "FL");
        frontRight = hardwareMap.get(DcMotor.class, "FR");
        claw = hardwareMap.get(Servo.class, "claw1");



        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("a", "id", hardwareMap.appContext.getPackageName());
        cam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1") );
        emptyPipeline pipe = new emptyPipeline();
        cam.setPipeline(pipe);
        cam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened()
            {
                telemetry.addData("correct", 1);
                cam.startStreaming(width, height, OpenCvCameraRotation.UPRIGHT);
            }
            @Override
            public void onError(int errorCode)
            {
                telemetry.addData("incorrect", 0);
            }
        });

        telemetry.addLine("Waiting for start");
        telemetry.update();




        waitForStart();

        while (opModeIsActive())
        {
            claw.setPosition(0.9);
            telemetry.update();
            int initialPropPos = pipe.getPropPosition();

            if (!done){
                telemetry.update();
                if (initialPropPos==1){
                    frontLeft.setPower(0.45);
                    backLeft.setPower(0.45);
                    frontRight.setPower(0.45);
                    backRight.setPower(0.45);
                    sleep(900);
                    frontLeft.setPower(0);
                    backLeft.setPower(0);
                    frontRight.setPower(0);
                    backRight.setPower(0);
                    sleep(600);
                    frontLeft.setPower(-0.3);
                    backLeft.setPower(-0.3);
                    frontRight.setPower(0.3);
                    backRight.setPower(0.3);
                    sleep(1200);
                    frontLeft.setPower(0);
                    backLeft.setPower(0);
                    frontRight.setPower(0);
                    backRight.setPower(0);
                    sleep(300);
                    frontLeft.setPower(0.3);
                    backLeft.setPower(0.3);
                    frontRight.setPower(0.3);
                    backRight.setPower(0);
                    claw.setPosition(0.6);
                    sleep(600);
                    frontLeft.setPower(0);
                    backLeft.setPower(0);
                    frontRight.setPower(0);
                    backRight.setPower(0);
                    sleep(300);
                    frontLeft.setPower(-0.3);
                    backLeft.setPower(-0.3);
                    frontRight.setPower(-0.3);
                    backRight.setPower(-0.3);
                    sleep(600);
                    frontLeft.setPower(0);
                    backLeft.setPower(0);
                    frontRight.setPower(0);
                    backRight.setPower(0);
                    done = true;
                } else if (initialPropPos==2){
                    frontLeft.setPower(0.45);
                    backLeft.setPower(0.45);
                    frontRight.setPower(0.45);
                    backRight.setPower(0.45);
                    sleep(1200);
                    claw.setPosition(0.6);
                    frontLeft.setPower(0);
                    backLeft.setPower(0);
                    frontRight.setPower(0);
                    backRight.setPower(0);
                    sleep(600);
                    frontLeft.setPower(-0.3);
                    backLeft.setPower(-0.3);
                    frontRight.setPower(-0.3);
                    backRight.setPower(-0.3);
                    sleep(500);
                    frontLeft.setPower(0);
                    backLeft.setPower(0);
                    frontRight.setPower(0);
                    backRight.setPower(0);
                    done = true;

                } else if (initialPropPos==3){
                    frontLeft.setPower(0.6);
                    backLeft.setPower(-0.6);
                    frontRight.setPower(-0.6);
                    backRight.setPower(0.6);
                    sleep(700);
                    frontLeft.setPower(0);
                    backLeft.setPower(0);
                    frontRight.setPower(0);
                    backRight.setPower(0);
                    sleep(200);
                    frontLeft.setPower(0.65);
                    backLeft.setPower(0.65);
                    frontRight.setPower(0.65);
                    backRight.setPower(0.65);
                    sleep(600);
                    claw.setPosition(0.6);
                    frontLeft.setPower(0);
                    backLeft.setPower(0);
                    frontRight.setPower(0);
                    backRight.setPower(0);
                    sleep(600);
                    frontLeft.setPower(-0.3);
                    backLeft.setPower(-0.3);
                    frontRight.setPower(-0.3);
                    backRight.setPower(-0.3);
                    sleep(1000);
                    frontLeft.setPower(0);
                    backLeft.setPower(0);
                    frontRight.setPower(0);
                    backRight.setPower(0);
                    done = true;

                }
            }





            if(gamepad1.a)
            {
                cam.stopStreaming();

            }

            sleep(100);
        }
    }
    class emptyPipeline extends OpenCvPipeline
    {
        Mat YCbCr = new Mat();
        Mat leftCrop;
        Mat midCrop;
        Mat rightCrop;
        Mat output = new Mat();

        double leftavgred;
        double midavgred;
        double rightavgred;
        int propPosition = 0;
        Scalar rectColor = new Scalar(255.0,0.0,0.0);

        @Override
        public Mat processFrame(Mat input)
        {
            Imgproc.cvtColor(input,YCbCr, Imgproc.COLOR_RGB2YCrCb);

            Rect leftRect = new Rect(1,height/2,426,(height/2)-2);
            Rect midRect = new Rect(428,height/2,426,(height/2)-2);
            Rect rightRect = new Rect(855,height/2,425,(height/2)-2);

            input.copyTo(output);
            Imgproc.rectangle(output,leftRect,rectColor,5);
            Imgproc.rectangle(output,midRect,rectColor,5);
            Imgproc.rectangle(output,rightRect,new Scalar(0,255.0,0.0),5);


            leftCrop = YCbCr.submat(leftRect);
            midCrop = YCbCr.submat(midRect);
            rightCrop = YCbCr.submat(rightRect);

            Core.extractChannel(leftCrop, leftCrop, 2);
            Core.extractChannel(midCrop, midCrop, 2);
            Core.extractChannel(rightCrop, rightCrop, 2);

            Scalar leftavg = Core.mean(leftCrop);
            Scalar midavg = Core.mean(midCrop);
            Scalar rightavg = Core.mean(rightCrop);

            leftavgred = leftavg.val[0];
            midavgred = midavg.val[0];
            rightavgred = rightavg.val[0];

            if (leftavgred<midavgred && leftavgred<rightavgred){
                telemetry.addData("leftavg",leftavgred);
                telemetry.addData("midavg",midavgred);
                telemetry.addData("rightavg",rightavgred);
                telemetry.addLine("Left");
                propPosition = 1;

            }
            else if (midavgred<leftavgred && midavgred<rightavgred){
                telemetry.addData("leftavg",leftavgred);
                telemetry.addData("midavg",midavgred);
                telemetry.addData("rightavg",rightavgred);
                telemetry.addLine("Mid");
                propPosition = 2;
            }
            else if (rightavgred<leftavgred && rightavgred<midavgred){
                telemetry.addData("leftavg",leftavgred);
                telemetry.addData("midavg",midavgred);
                telemetry.addData("rightavg",rightavgred);
                telemetry.addLine("Right");
                propPosition = 3;
            }

            return output;

        }

        public int getPropPosition(){
            return propPosition;
        }
    }
}
