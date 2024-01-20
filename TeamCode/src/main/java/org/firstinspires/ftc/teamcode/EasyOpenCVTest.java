package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.opencv.core.Mat;


import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;
import org.openftc.easyopencv.OpenCvPipeline;


@Autonomous
public class EasyOpenCVTest extends LinearOpMode {
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
                cam.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);
            }
            @Override
            public void onError(int errorCode)
            {
                telemetry.addData("incorrect", 0);
            }
        });

        waitForStart();

        while (opModeIsActive())
        {

            telemetry.addData("Frame Count", cam.getFrameCount());
            telemetry.addData("FPS", String.format("%.2f", cam.getFps()));
            telemetry.addData("Total frame time ms", cam.getTotalFrameTimeMs());
            telemetry.addData("Pipeline time ms", cam.getPipelineTimeMs());
            telemetry.addData("Overhead time ms", cam.getOverheadTimeMs());
            telemetry.addData("Theoretical max FPS", cam.getCurrentPipelineMaxFps());
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
        @Override
        public Mat processFrame(Mat input)
        {
            return input;

        }
    }
}
