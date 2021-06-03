package com.example.softwareengineering_project;

import android.graphics.BitmapFactory;
import android.hardware.Camera;

import com.googlecode.tesseract.android.TessBaseAPI;

import org.opencv.core.*;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.os.AsyncTask;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

//Tesseract << 사진 -> 문자열로 바꿔주는 API
//opencv에 필요한 라이브러리

public class CV_Controller {
    TessBaseAPI tessBaseAPI = new TessBaseAPI();


    public void CV_start() {
        //inputmat은 캡쳐한 사진을 받으면 되는데 만약 캡쳐한 사진이 파일이라면 (사진이라면)
        //takepicture.jpg로 저장했다고가정했습니다
        Mat inputmat = Imgcodecs.imread("takepicture.jpg");
        Mat cont = Contour(inputmat);
        // 이 cleaner 이라는 파일형식이 opencv_contour_img.jpg 파일입니다.
        File cleaner = cleanimg(cont); // 이 파일을 Tesseract 에 넣어주기만 하면 됩니다.
    }

    private static File cleanimg(Mat input) {
        //Mat inputmat = Imgcodecs.imread("sample_input1.jpg");
        Mat blur = new Mat();
        Mat output = new Mat();
        Mat gray = new Mat();
        Mat clone_img = input.clone();
        Mat size_up = new Mat();
        //that is sharptext() just meaning
        //Imgproc.pyrUp(clone_img, size_up);
        Imgproc.resize(clone_img, size_up, new Size(), 15, 15, Imgproc.INTER_CUBIC);
        Imgproc.cvtColor(size_up, blur, Imgproc.COLOR_RGB2GRAY);
        Imgproc.GaussianBlur(size_up, blur, new Size(3, 3), 0);
        Imgproc.cvtColor(blur, gray, Imgproc.COLOR_RGB2GRAY);

        Core.addWeighted(gray, 1.6, gray, -0.15, 0.5, gray);
        //Imgcodecs.imwrite("testimg.jpg", gray);
        Imgproc.threshold(gray, output, 205, 255, Imgproc.THRESH_BINARY);

        //제가 Tesseratc 가 입력받는 형식을 몰라서 일단 파일 만들어서 파일형식으로 리턴해줬어요.
        Imgcodecs.imwrite("opencv_contour_img.jpg", output);
        File resultFile = new File("opencv_contour_img.jpg");

        return resultFile;
    }

    public static Mat Contour(Mat imageMat) {
        Mat rgb = new Mat();
        rgb = imageMat.clone();
        Mat grayImage = new Mat();
        Mat img_result = new Mat();
        Rect cutImg = new Rect();

        Imgproc.cvtColor(rgb, grayImage, Imgproc.COLOR_RGB2GRAY);

        Mat gradThresh = new Mat();
        Mat hierarchy = new Mat();
        List<MatOfPoint> contours = new ArrayList<MatOfPoint>();

        Imgproc.adaptiveThreshold(grayImage, gradThresh, 255, Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY_INV, 3, 12);
        Imgproc.morphologyEx(gradThresh, gradThresh, Imgproc.MORPH_CLOSE, hierarchy);
        removeVerticalLines(gradThresh, 100);
        Imgproc.findContours(gradThresh, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE, new Point(0, 0));

        if (contours.size() > 0) {
            for (int idx = 0; idx < contours.size(); idx++) {
                Rect rect = Imgproc.boundingRect(contours.get(idx));

                // 이미지 크기를 512 x 512 라고 가정
                if (rect.height > 50 && rect.width > 50 && !(rect.width >= 512 - 5 && rect.height >= 512 - 5)) {
                    Imgproc.rectangle(imageMat, new Point(rect.br().x - rect.width, rect.br().y - rect.height)
                            , rect.br()
                            , new Scalar(0, 255, 0), 5);
                    cutImg = new Rect(new Point(rect.br().x - rect.width, rect.br().y - rect.height), rect.br());
                    //Imgproc.rectangle(img_mask, new Point(rect.br().x - rect.width, rect.br().y - rect.height), rect.br(), new Scalar(255), -1);
                }
            }
        }
        return img_result;
    }

    public static void removeVerticalLines(Mat img, int limit) {
        Mat lines = new Mat();
        int threshold = 100;
        int minLength = 80;
        int lineGap = 5;
        int rho = 1;

        Imgproc.HoughLinesP(img, lines, rho, Math.PI / 180, threshold, minLength, lineGap);

        for (int i = 0; i < lines.total(); i++) {

            double[] vec = lines.get(i, 0);
            Point pt1, pt2;

            pt1 = new Point(vec[0], vec[1]);
            pt2 = new Point(vec[2], vec[3]);

            double gapY = Math.abs(vec[3] - vec[1]);
            double gapX = Math.abs(vec[2] - vec[0]);

            if (gapY > limit && limit > 0) {
                Imgproc.line(img, pt1, pt2, new Scalar(0, 0, 0), 10);
            }
        }
    }
}