package com.example.softwareengineering_project;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.os.AsyncTask;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.googlecode.tesseract.android.TessBaseAPI;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import jy.demo.tesseract.android.CameraSurfaceView;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;

public class cv_Activity extends AppCompatActivity{
    TessBaseAPI tessBaseAPI;
    private final static String TAG = MainActivity.class.getClass().getSimpleName();
    private boolean isOpenCvLoaded = false;
    Button button, home_button;
    ImageView imageView;
    CameraSurfaceView surfaceView;
    TextView textView;
    private MyAPI mMyAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cv);
        initMyAPI("https://maxcha98.pythonanywhere.com/");

        surfaceView = findViewById(R.id.surfaceView);
        textView = findViewById(R.id.textView_cv);
        home_button = findViewById(R.id.cv_Home_BTN);
        Intent cv_home_intent = new Intent(this, Home.class);

        button = findViewById(R.id.button_cv);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                capture();
            }
        });
        home_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(cv_home_intent);
                finish();
            }
        });

        tessBaseAPI = new TessBaseAPI();
        String dir = getFilesDir() + "/tesseract";
        if(checkLanguageFile(dir+"/tessdata"))
            tessBaseAPI.init(dir, "eng");

        showSample();

        /*Call<String> postCall = mMyAPI.post_papa(null);
        postCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
                    Log.d(TAG,"등록 완료");
                    System.out.println(response.body());
                    Toast.makeText(getApplicationContext(),response.body(),Toast.LENGTH_SHORT).show();
                }else {
                    Log.d(TAG,"Status Code : " + response.code());
                    Log.d(TAG,response.errorBody().toString());
                    Log.d(TAG,call.request().body().toString());
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d(TAG,"Fail msg : " + t.getMessage());
            }
        });*/
    }
    public void showSample(){
        Bitmap samplebitmap = BitmapFactory.decodeResource(getResources(), R.drawable.sample);
        //String OCRresult = null;
        tessBaseAPI.setImage(samplebitmap);
        String OCRresult = tessBaseAPI.getUTF8Text();
        Toast.makeText(getApplicationContext(),OCRresult,Toast.LENGTH_SHORT).show();
        textView.setText(OCRresult);
    }
    private void initMyAPI(String baseUrl){
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();

        Log.d(TAG,"initMyAPI : " + baseUrl);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        mMyAPI = retrofit.create(MyAPI.class);
    }

    boolean checkLanguageFile(String dir)
    {
        File file = new File(dir);
        if(!file.exists() && file.mkdirs())
            createFiles(dir);
        else if(file.exists()){
            String filePath = dir + "/eng.traineddata";
            File langDataFile = new File(filePath);
            if(!langDataFile.exists())
                createFiles(dir);
        }
        return true;
    }

    private void createFiles(String dir)
    {
        AssetManager assetMgr = this.getAssets();

        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {
            inputStream = assetMgr.open("eng.traineddata");

            String destFile = dir + "/eng.traineddata";

            outputStream = new FileOutputStream(destFile);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, read);
            }
            inputStream.close();
            outputStream.flush();
            outputStream.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void capture()
    {
        surfaceView.capture(new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] bytes, Camera camera) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 8;

                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                bitmap = GetRotatedBitmap(bitmap, 90);

                button.setEnabled(false);
                button.setText("텍스트 인식중...");
                //new AsyncTess().execute(inputmap);
                //String result = findcontour(bitmap);
                //Bitmap samplebitmap = BitmapFactory.decodeResource(getResources(), R.drawable.sample);
                String OCRresult = null;
                tessBaseAPI.setImage(bitmap);
                OCRresult = tessBaseAPI.getUTF8Text();
                Toast.makeText(getApplicationContext(),OCRresult,Toast.LENGTH_SHORT).show();
                textView.setText(OCRresult);


                camera.startPreview();
            }
        });
    }

    public synchronized static Bitmap GetRotatedBitmap(Bitmap bitmap, int degrees) {
        if (degrees != 0 && bitmap != null) {
            Matrix m = new Matrix();
            m.setRotate(degrees, (float) bitmap.getWidth() / 2, (float) bitmap.getHeight() / 2);
            try {
                Bitmap b2 = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);
                if (bitmap != b2) {
                    bitmap = b2;
                }
            } catch (OutOfMemoryError ex) {
                ex.printStackTrace();
            }
        }
        return bitmap;
    }
/*
    private class AsyncTess extends AsyncTask<Bitmap, Integer, String> {
        @Override
        protected String doInBackground(Bitmap... mRelativeParams) {
            tessBaseAPI.setImage(mRelativeParams[0]);
            return tessBaseAPI.getUTF8Text();
        }
        protected void onPostExecute(String result) {
            textView.setText(result);
            Toast.makeText(cv_Activity.this, ""+result, Toast.LENGTH_LONG).show();

            button.setEnabled(true);
            button.setText("텍스트 인식");
        }
    }
 */
    public String findcontour(Bitmap bitmap){
        OpenCVLoader.initDebug();
        //Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.input);
        Mat mat = new Mat(bitmap.getWidth(), bitmap.getHeight(), CvType.CV_8UC3);
        Utils.bitmapToMat(bitmap, mat);

        Mat rgbMat = new Mat();
        Imgproc.cvtColor(mat, rgbMat, Imgproc.COLOR_RGBA2BGR);

        Mat dilatedMat = new Mat();
        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(7, 7));
        Imgproc.morphologyEx(rgbMat, dilatedMat, Imgproc.MORPH_OPEN, kernel);

        //red
        Mat redMat = new Mat();
        Core.inRange(rgbMat, new Scalar(0, 0, 120), new Scalar(100, 100, 255), redMat);

        //find contour
        Mat hierarchy = new Mat();
        List<MatOfPoint> contours = new ArrayList<>();

        Imgproc.findContours(redMat, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
        double largest_area =0;
        int largest_contour_index = 0;

        for (int contourIdx = 0; contourIdx < contours.size(); contourIdx++) {
            double contourArea = Imgproc.contourArea(contours.get(contourIdx));
            if (contourArea > largest_area) {
                largest_area = contourArea;
                largest_contour_index = contourIdx;
            }
        }

        Imgproc.drawContours(mat, contours, largest_contour_index, new Scalar(0, 255, 0, 255), 3);
        Mat result = cleanimg(mat);
        Bitmap outputImage= Bitmap.createBitmap(result.cols(), result.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(result, outputImage);

        String OCRresult = null;
        tessBaseAPI.setImage(outputImage);
        OCRresult = tessBaseAPI.getUTF8Text();

        return OCRresult;
    }

    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                    Log.i(TAG, "OpenCV loaded successfully");
                    break;
                default:
                    super.onManagerConnected(status);
                    break;
            }
        }
    };

    @Override
    public void onResume()
    {
        super.onResume();

        // OpenCV load
        if (!OpenCVLoader.initDebug()) {
            Log.d(TAG, "Internal OpenCV library not found. Using OpenCV Manager for initialization");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION, this, mLoaderCallback);
        } else {
            Log.d(TAG, "OpenCV library found inside package. Using it!");
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
            isOpenCvLoaded = true;
        }
    }

    private static Mat cleanimg(Mat input) {
        //Mat inputmat = Imgcodecs.imread("sample_input1.jpg");
        Mat blur = new Mat();
        Mat output = new Mat();
        Mat gray = new Mat();
        Mat clone_img = input.clone();
        Mat size_up = new Mat();
        //that is sharptext() just meaning
        //Imgproc.pyrUp(clone_img, size_up);
        Imgproc.resize(clone_img, size_up, new Size(), 3, 3, Imgproc.INTER_CUBIC);
        Imgproc.cvtColor(size_up, blur, Imgproc.COLOR_RGB2GRAY);
        Imgproc.GaussianBlur(size_up, blur, new Size(3, 3), 0);
        Imgproc.cvtColor(blur, gray, Imgproc.COLOR_RGB2GRAY);

        Core.addWeighted(gray, 1.6, gray, -0.15, 0.5, gray);
        //Imgcodecs.imwrite("testimg.jpg", gray);
        Imgproc.threshold(gray, output, 205, 255, Imgproc.THRESH_BINARY);

        return output;
    }
}