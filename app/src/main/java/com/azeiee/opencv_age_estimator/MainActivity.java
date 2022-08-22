package com.azeiee.opencv_age_estimator;

import android.graphics.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.azeiee.opencv_age_estimator.databinding.ActivityMainBinding;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraActivity;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;

import java.util.Collections;
import java.util.List;

public class MainActivity extends CameraActivity {
    private static final String TAG = "OpenCV_MainActivity";

    // Used to load the 'opencv_age_estimator' library on application startup.
    static {
        System.loadLibrary("opencv_age_estimator");
        if (OpenCVLoader.initDebug()) {
            Log.d(TAG, "static initializer: OpenCv loaded");
        }else{
            Log.d(TAG, "static initializer: OpenCv not loaded");
            System.loadLibrary("opencv_java4");
        }
    }

    private ActivityMainBinding binding;

    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status){
                case BaseLoaderCallback.SUCCESS:
                    binding.OpenCVCameraView.enableView();
                    break;
                default:
                    super.onManagerConnected(status);
                    break;
            }

        }
    };

    CameraBridgeViewBase.CvCameraViewListener2 mCVcameraListner = new CameraBridgeViewBase.CvCameraViewListener2() {
        @Override
        public void onCameraViewStarted(int width, int height) {

        }

        @Override
        public void onCameraViewStopped() {

        }

        @Override
        public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
            return inputFrame.rgba();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Example of a call to a native method
       /* TextView tv = binding.sampleText;
        tv.setText(stringFromJNI());*/
        binding.OpenCVCameraView.setVisibility(View.VISIBLE);
        binding.OpenCVCameraView.setCvCameraViewListener(mCVcameraListner);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(binding.OpenCVCameraView != null){
            binding.OpenCVCameraView.disableView();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(OpenCVLoader.initDebug()){
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }else{
            Log.d(TAG, "onResume: There is someerror in OpenCV");
            System.loadLibrary("opencv_java4");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(binding.OpenCVCameraView != null){
            binding.OpenCVCameraView.disableView();
        }
    }

    @Override
    protected List<? extends CameraBridgeViewBase> getCameraViewList() {
        return Collections.singletonList(binding.OpenCVCameraView);
    }

    /**
     * A native method that is implemented by the 'opencv_age_estimator' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}