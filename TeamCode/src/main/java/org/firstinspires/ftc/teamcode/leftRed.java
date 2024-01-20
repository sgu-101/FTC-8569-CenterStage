package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.opencv.core.Mat;


import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;
import org.openftc.easyopencv.OpenCvPipeline;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Core;


@Autonomous
public class leftRed extends LinearOpMode {

    private DcMotor backLeft;
    private DcMotor backRight;
    private DcMotor frontLeft;
    private DcMotor frontRight;
    OpenCvCamera cam = null;
    boolean done = false;
    @Override
    public void runOpMode(){
        backLeft = hardwareMap.get(DcMotor.class, "BL");
        backRight = hardwareMap.get(DcMotor.class, "BR");
        frontLeft = hardwareMap.get(DcMotor.class, "FL");
        frontRight = hardwareMap.get(DcMotor.class, "FR");


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

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        cam = OpenCvCameraFactory.getInstance().createInternalCamera(OpenCvInternalCamera.CameraDirection.BACK, cameraMonitorViewId);
        emptyPipeline pipe = new emptyPipeline();
        cam.setPipeline(pipe);
        cam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened()
            {
                telemetry.addData("correct", 1);
                cam.startStreaming(320, 240, OpenCvCameraRotation.SIDEWAYS_LEFT);
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
            telemetry.update();
            int initialPropPos = pipe.getPropPosition();
            if (!done){
                if (initialPropPos==1){
                    frontLeft.setPower(0.2);
                    backLeft.setPower(-0.2);
                    frontRight.setPower(-0.2);
                    backRight.setPower(0.2);
                    sleep(300);
                    frontLeft.setPower(0.45);
                    backLeft.setPower(0.45);
                    frontRight.setPower(0.45);
                    backRight.setPower(0.45);
                    sleep(700);
                    frontLeft.setPower(0);
                    backLeft.setPower(0);
                    frontRight.setPower(0);
                    backRight.setPower(0);
                    sleep(200);
                    frontLeft.setPower(-0.3);
                    backLeft.setPower(-0.3);
                    frontRight.setPower(-0.3);
                    backRight.setPower(-0.3);
                    sleep(200);
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
                    sleep(700);
                    frontLeft.setPower(0);
                    backLeft.setPower(0);
                    frontRight.setPower(0);
                    backRight.setPower(0);
                    sleep(200);
                    frontLeft.setPower(-0.3);
                    backLeft.setPower(-0.3);
                    frontRight.setPower(-0.3);
                    backRight.setPower(-0.3);
                    sleep(800);
                    frontLeft.setPower(0);
                    backLeft.setPower(0);
                    frontRight.setPower(0);
                    backRight.setPower(0);
                    done = true;

                } else if (initialPropPos==3){
                    frontLeft.setPower(-0.2);
                    backLeft.setPower(0.2);
                    frontRight.setPower(0.2);
                    backRight.setPower(-0.2);
                    sleep(300);
                    frontLeft.setPower(0.45);
                    backLeft.setPower(0.45);
                    frontRight.setPower(0.45);
                    backRight.setPower(0.45);
                    sleep(700);
                    frontLeft.setPower(0);
                    backLeft.setPower(0);
                    frontRight.setPower(0);
                    backRight.setPower(0);
                    sleep(200);
                    frontLeft.setPower(-0.3);
                    backLeft.setPower(-0.3);
                    frontRight.setPower(-0.3);
                    backRight.setPower(-0.3);
                    sleep(200);
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


            Rect leftRect = new Rect(1,1,106,239);
            Rect midRect = new Rect(107,1,106,239);
            Rect rightRect = new Rect(214,1,105,239);

            input.copyTo(output);
            Imgproc.rectangle(output,leftRect,rectColor,2);
            Imgproc.rectangle(output,midRect,rectColor,2);
            Imgproc.rectangle(output,rightRect,rectColor,2);


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

            return input;

        }

        public int getPropPosition(){
            return propPosition;
        }
    }
}
