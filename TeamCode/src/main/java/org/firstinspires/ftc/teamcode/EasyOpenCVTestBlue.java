package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;
import org.openftc.easyopencv.OpenCvPipeline;


@Autonomous
public class EasyOpenCVTestBlue extends LinearOpMode {
    OpenCvCamera cam = null;

    @Override
    public void runOpMode(){
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        cam = OpenCvCameraFactory.getInstance().createInternalCamera(OpenCvInternalCamera.CameraDirection.BACK, cameraMonitorViewId);
        cam.setPipeline(new emptyPipeline());
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

            Core.extractChannel(leftCrop, leftCrop, 1);
            Core.extractChannel(midCrop, midCrop, 1);
            Core.extractChannel(rightCrop, rightCrop, 1);

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

            }
            else if (midavgred<leftavgred && midavgred<rightavgred){
                telemetry.addData("leftavg",leftavgred);
                telemetry.addData("midavg",midavgred);
                telemetry.addData("rightavg",rightavgred);
                telemetry.addLine("Mid");
            }
            else if (rightavgred<leftavgred && rightavgred<midavgred){
                telemetry.addData("leftavg",leftavgred);
                telemetry.addData("midavg",midavgred);
                telemetry.addData("rightavg",rightavgred);
                telemetry.addLine("Right");
            }

            return input;

        }

        public int getPropPosition(){
            return propPosition;
        }
    }
}
