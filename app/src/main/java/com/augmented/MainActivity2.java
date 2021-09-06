package com.augmented;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCameraView;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static android.hardware.Camera.CameraInfo.CAMERA_FACING_FRONT;

public class MainActivity2 extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener2 {

    JavaCameraView javaCameraView;
    File cascFile, cascFile2, cascFile3;
    CascadeClassifier faceDetector, lipDetector, eyedetector;
    private Mat mRGBA, mGREY;
    private Button buttonmakeup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        buttonmakeup = findViewById(R.id.button_makeup);
        buttonmakeup.setOnClickListener(v -> {
            buttonmakeup = findViewById(R.id.button_makeup);
            Intent in = new Intent(MainActivity2.this, MakeupActivity.class);
            startActivity(in);
        });
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PackageManager.PERMISSION_GRANTED);

        javaCameraView = findViewById(R.id.javaCamView);
        javaCameraView.setCameraIndex(CAMERA_FACING_FRONT
        );
        if (!OpenCVLoader.initDebug()) {
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_0_0, this, baseCallback);
        } else {
            try {
                baseCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        javaCameraView.setCvCameraViewListener(this);
    }

    //face detection

    @Override
    public void onCameraViewStarted(int width, int height) {
        mRGBA = new Mat();
        mGREY = new Mat();


    }

    @Override
    public void onCameraViewStopped() {
        mRGBA.release();
        mGREY.release();


    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        mRGBA = inputFrame.rgba();
        mGREY = inputFrame.gray();


        MatOfRect faceDetections = new MatOfRect();
        MatOfRect lipdetections = new MatOfRect();
        MatOfRect eyedetections = new MatOfRect();

        faceDetector.detectMultiScale(mRGBA, faceDetections, 1.1, 10);
        lipDetector.detectMultiScale(mRGBA, lipdetections);

        for (Rect rect : faceDetections.toArray()) {

            Imgproc.rectangle(mRGBA, new Point(rect.x, rect.y
            ), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(255, 0, 0));


        }


        //lip detection
        lipDetector.detectMultiScale(mRGBA, lipdetections, 1.1, 20);

        for (Rect rect : lipdetections.toArray()) {
            Imgproc.rectangle(mRGBA, new Point(rect.x, rect.y
            ), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 255, 0));
        }


        //eye detections

        eyedetector.detectMultiScale(mRGBA, eyedetections, 1.1, 14);

        for (Rect rect : eyedetections.toArray()) {
            Imgproc.rectangle(mRGBA, new Point(rect.x, rect.y
            ), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 0, 255));
        }


        return mRGBA;


    }


    private final BaseLoaderCallback baseCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) throws IOException {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS: {


                    InputStream is = getResources().openRawResource(R.raw.haarcascade_frontalface_alt2);
                    File cascadeDir = getDir("cascade", Context.MODE_PRIVATE);
                    cascFile = new File(cascadeDir, "haarcascade_frontalface_alt2.xml");


                    FileOutputStream foa = new FileOutputStream(cascFile);

                    byte[] buffer = new byte[4096];

                    int bytesRead;

                    while ((bytesRead = is.read(buffer)) != -1) {
                        foa.write(buffer, 0, bytesRead);
                    }
                    is.close();
                    foa.close();

                    faceDetector = new CascadeClassifier(cascFile.getAbsolutePath());

                    if (faceDetector.empty()) {
                        faceDetector = null;
                    } else

                        cascadeDir.delete();
                    javaCameraView.enableView();

                    //lips detection
                    InputStream is2 = getResources().openRawResource(R.raw.mouth);

                    File cascadeDir2 = getDir("cascade", Context.MODE_PRIVATE);
                    cascFile2 = new File(cascadeDir2, "mouth.xml");

                    FileOutputStream foa1 = new FileOutputStream(cascFile2);

                    byte[] buffer1 = new byte[4096];

                    int bytesRead1;

                    while ((bytesRead1 = is2.read(buffer1)) != -1) {
                        foa1.write(buffer1, 0, bytesRead1);
                    }

                    is2.close();
                    foa1.close();

                    lipDetector = new CascadeClassifier(cascFile2.getAbsolutePath());

                    if (lipDetector.empty()) {
                        lipDetector = null;
                    } else

                        cascadeDir2.delete();
                    javaCameraView.enableView();


//eye detector

                    InputStream is3 = getResources().openRawResource(R.raw.haarcascade_eye);
                    File cascadeDir3 = getDir("cascade", Context.MODE_PRIVATE);

                    cascFile3 = new File(cascadeDir3, "haarcascade_eye.xml");
                    FileOutputStream foa2 = new FileOutputStream(cascFile3);


                    byte[] buffer2 = new byte[4096];

                    int bytesRead2;

                    while ((bytesRead2 = is3.read(buffer2)) != -1) {
                        foa2.write(buffer2, 0, bytesRead2);
                    }

                    is3.close();
                    foa2.close();

                    eyedetector = new CascadeClassifier(cascFile3.getAbsolutePath());

                    if (eyedetector.empty()) {
                        eyedetector = null;
                    } else

                        cascadeDir3.delete();

                    javaCameraView.enableView();

                }

                break;


                default: {
                    super.onManagerConnected(status);
                }
                break;
            }

        }

    };
}