package com.example.testnotfinal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testnotfinal.ml.Modelv6EfficientnetLite;
import com.example.testnotfinal.ml.Modelv6Mobilenetv2;
import com.example.testnotfinal.ml.Modelv6Resnet50;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.label.Category;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button selectbtn, predictbtn, scanbtn, exitbtn, sendbtn;
    private TextView feedbackbtn, helpbtn, aboutbtn;
    private ImageView imageView1, imageView2;
    private EditText txtemail, txtdetails;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (OpenCVLoader.initDebug()) {
            Log.d("OPENCV", "OpenCV initialized");
        }

        //get permission
        getPermission();

        feedbackbtn = (TextView) findViewById(R.id.feedbackbtn);
        helpbtn = (TextView) findViewById(R.id.helpbtn);

        scanbtn = (Button) findViewById(R.id.scanbtn);
        exitbtn = (Button) findViewById(R.id.exitbtn);

        scanbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 11);
            }
        });

        exitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                System.exit(0);
            }
        });

        feedbackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                feedback();
            }
        });

        helpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                help();
            }
        });

//        predictbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (bitmap != null) {
//                    try {
//
//                        Modelv5EfficientnetLite model1 = Modelv5EfficientnetLite.newInstance(getApplicationContext());
//                        Modelv5Mobilenetv2 model2 = Modelv5Mobilenetv2.newInstance(getApplicationContext());
//                        Modelv5Resnet50 model3 = Modelv5Resnet50.newInstance(getApplicationContext());
//
//                        // Creates inputs for reference.
//                        TensorBuffer inputFeature = TensorBuffer.createFixedSize(new int[]{1, 224, 224, 3}, DataType.UINT8);
//
//                        bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
//                        bitmap = Bitmap.createScaledBitmap(bitmap, 224, 224, true);
//
//                        TensorImage tensorImage = new TensorImage(DataType.UINT8);
//                        tensorImage.load(bitmap);
//                        ByteBuffer byteBuffer = tensorImage.getBuffer();
//
//                        inputFeature.loadBuffer(byteBuffer);
//
//                        // Runs model inference and gets result.
//                        Modelv5EfficientnetLite.Outputs outputs1 = model1.process(inputFeature);
//                        Modelv5Mobilenetv2.Outputs outputs2 = model2.process(inputFeature);
//                        Modelv5Resnet50.Outputs outputs3 = model3.process(inputFeature);
//
//                        List<Category> probability1 = outputs1.getProbabilityAsCategoryList();
//                        List<Category> probability2 = outputs2.getProbabilityAsCategoryList();
//                        List<Category> probability3 = outputs3.getProbabilityAsCategoryList();
//
//                        probability1.sort(Comparator.comparing(Category::getScore, Comparator.reverseOrder()));
//                        probability2.sort(Comparator.comparing(Category::getScore, Comparator.reverseOrder()));
//                        probability3.sort(Comparator.comparing(Category::getScore, Comparator.reverseOrder()));
//
//                        String labelA1 = probability1.get(0).getLabel();
//                        String labelA2 = probability1.get(1).getLabel();
//
//                        String labelB1 = probability2.get(0).getLabel();
//                        String labelB2 = probability2.get(1).getLabel();
//
//                        String labelC1 = probability3.get(0).getLabel();
//                        String labelC2 = probability3.get(1).getLabel();
//
//                        float scoreA1 = probability1.get(0).getScore();
//                        float scoreA2 = probability1.get(1).getScore();
//
//                        float scoreB1 = probability2.get(0).getScore();
//                        float scoreB2 = probability2.get(1).getScore();
//
//                        float scoreC1 = probability3.get(0).getScore();
//                        float scoreC2 = probability3.get(1).getScore();
//
////                        result.setText(label + " " + score);
////                        test.setText(label2 + " " + score2);
//
//                        model1.close();
//                        model2.close();
//                        model3.close();
//
//                        LayoutInflater inflater = (LayoutInflater)
//                                getSystemService(LAYOUT_INFLATER_SERVICE);
//                        View popupView = inflater.inflate(R.layout.predict_window, null);
//                        View popupView2 = inflater.inflate(R.layout.details_window, null);
//
//                        TextView predictA1 = popupView.findViewById(R.id.predictA1);
//                        TextView predictA2 = popupView.findViewById(R.id.predictA2);
//                        TextView predictB1 = popupView.findViewById(R.id.predictB1);
//                        TextView predictB2 = popupView.findViewById(R.id.predictB2);
//                        TextView predictC1 = popupView.findViewById(R.id.predictC1);
//                        TextView predictC2 = popupView.findViewById(R.id.predictC2);
//
//                        TextView sign_name = popupView2.findViewById(R.id.title_details);
//                        TextView sign_desc = popupView2.findViewById(R.id.desc_details);
//
//                        ImageView sign_image = popupView2.findViewById(R.id.imageView_details);
//
//                        sign_desc.setMovementMethod(new ScrollingMovementMethod());
////                        sign_desc.setJustificationMode(LineBreaker.JUSTIFICATION_MODE_INTER_WORD);
//
//                        //note: result is based on resnet50 model
//                        String sign_result = labelC1;
//                        switch (sign_result) {
////                            case "Unknown":
////                                sign_name.setText(R.string.name_test);
////                                sign_desc.setText(R.string.desc_test);
////                                break;
//                            case "Men at Work Sign":
//                                sign_name.setText(R.string.name_men_at_work);
//                                sign_desc.setText(R.string.desc_men_at_work);
//                                break;
//                            case "No Entry Sign":
//                                sign_name.setText(R.string.name_no_entry);
//                                sign_desc.setText(R.string.desc_no_entry);
//                                break;
//                            case "No Overtaking Sign":
//                                sign_name.setText(R.string.name_no_overtaking);
//                                sign_desc.setText(R.string.desc_no_overtaking);
//                                break;
//                            case "No Turns Sign":
//                                sign_name.setText(R.string.name_no_turns);
//                                sign_desc.setText(R.string.desc_no_turns);
//                                break;
//                            case "Pedestrian Crossing Sign":
//                                sign_name.setText(R.string.name_pedestrian_crossing);
//                                sign_desc.setText(R.string.desc_pedestrian_crossing);
//                                break;
//                            case "School Zone Sign":
//                                sign_name.setText(R.string.name_school_zone);
//                                sign_desc.setText(R.string.desc_school_zone);
//                                break;
//                            case "Stop Sign":
//                                sign_name.setText(R.string.name_stop);
//                                sign_desc.setText(R.string.desc_stop);
//                                break;
//                            case "Traffic Lights Ahead Sign":
//                                sign_name.setText(R.string.name_traffic_lights_ahead);
//                                sign_desc.setText(R.string.desc_traffic_lights_ahead);
//                                break;
//                            case "Turn Left Ahead Sign":
//                                sign_name.setText(R.string.name_turn_left_ahead);
//                                sign_desc.setText(R.string.desc_turn_left_ahead);
//                                break;
//                            case "Turn Right Ahead Sign":
//                                sign_name.setText(R.string.name_turn_right_ahead);
//                                sign_desc.setText(R.string.desc_turn_right_ahead);
//                                break;
//                            default:
//                                sign_name.setText(R.string.name_test);
//                                sign_desc.setText(R.string.desc_test);
//                                break;
//
//                        }
//
////                        if (labelC1.equals("Unknown")) {
////
////                        }
//
//
//                        //todo: if to check percentage if too low change name to unknown
//
//                        predictA1.setText(labelA1 + " " + scoreA1);
//                        predictA2.setText(labelA2 + " " + scoreA2);
//                        predictB1.setText(labelB1 + " " + scoreB1);
//                        predictB2.setText(labelB2 + " " + scoreB2);
//                        predictC1.setText(labelC1 + " " + scoreC1);
//                        predictC2.setText(labelC2 + " " + scoreC2);
//
//                        // create the popup window
//                        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
//                        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
//
//                        // lets the taps outside the popup also dismiss it
//                        boolean focusable = true;
////                        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
//                        final PopupWindow popupWindow = new PopupWindow(popupView2, width, height, focusable);
//
//                        // show the popup window
//                        // which view you pass in doesn't matter, it is only used for the window tolken
//                        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
//                        popupWindow.setElevation(20);
////                        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
//                        popupWindow.showAtLocation(popupView2, Gravity.CENTER, 0, 0);
//
//
////                        int selectedId = radioGroup.getCheckedRadioButtonId();
////                        radioButton = (RadioButton) findViewById(selectedId);
////                        if (radioButton.getText().equals("EfficientNet-Lite")) {
//////                            test.setText("ssss");
////                            Modelv4EfficientnetLite model = Modelv4EfficientnetLite.newInstance(getApplicationContext());
////
////                            // Creates inputs for reference.
////                            TensorBuffer inputFeature = TensorBuffer.createFixedSize(new int[]{1, 224, 224, 3}, DataType.UINT8);
////
////                            bitmap = Bitmap.createScaledBitmap(bitmap, 224, 224, true);
////
////                            TensorImage tensorImage = new TensorImage(DataType.UINT8);
////                            tensorImage.load(bitmap);
////                            ByteBuffer byteBuffer = tensorImage.getBuffer();
////
////                            inputFeature.loadBuffer(byteBuffer);
////
////                            // Runs model inference and gets result.
////                            Modelv4EfficientnetLite.Outputs outputs = model.process(inputFeature);
////                            List<Category> probability = outputs.getProbabilityAsCategoryList();
////
////                            probability.sort(Comparator.comparing(Category::getScore, Comparator.reverseOrder()));
//////                        result.setText(probability.toString());
////                            String label = probability.get(0).getLabel();
////                            String label2 = probability.get(1).getLabel();
////                            float score = probability.get(0).getScore();
////                            float score2 = probability.get(1).getScore();
////                            result.setText(label + " " + score);
////                            test.setText(label2 + " " + score2);
////
////                            model.close();
////
////                        } else if (radioButton.getText().equals("MobileNetV2")) {
//////                            test.setText("aaaa");
////                            Modelv4Mobilenetv2 model = Modelv4Mobilenetv2.newInstance(getApplicationContext());
////
////                            // Creates inputs for reference.
////                            TensorBuffer inputFeature = TensorBuffer.createFixedSize(new int[]{1, 224, 224, 3}, DataType.UINT8);
////
////                            bitmap = Bitmap.createScaledBitmap(bitmap, 224, 224, true);
////
////                            TensorImage tensorImage = new TensorImage(DataType.UINT8);
////                            tensorImage.load(bitmap);
////                            ByteBuffer byteBuffer = tensorImage.getBuffer();
////
////                            inputFeature.loadBuffer(byteBuffer);
////
////                            // Runs model inference and gets result.
////                            Modelv4Mobilenetv2.Outputs outputs = model.process(inputFeature);
////                            List<Category> probability = outputs.getProbabilityAsCategoryList();
////
////                            probability.sort(Comparator.comparing(Category::getScore, Comparator.reverseOrder()));
//////                        result.setText(probability.toString());
////                            String label = probability.get(0).getLabel();
////                            String label2 = probability.get(1).getLabel();
////                            float score = probability.get(0).getScore();
////                            float score2 = probability.get(1).getScore();
////                            result.setText(label + " " + score);
////                            test.setText(label2 + " " + score2);
////
////                            model.close();
////
////                        } else if (radioButton.getText().equals("ResNet50")) {
//////                            test.setText("dddd");
////                            Modelv4Resnet50 model = Modelv4Resnet50.newInstance(getApplicationContext());
////
////                            // Creates inputs for reference.
////                            TensorBuffer inputFeature = TensorBuffer.createFixedSize(new int[]{1, 224, 224, 3}, DataType.UINT8);
////
////                            bitmap = Bitmap.createScaledBitmap(bitmap, 224, 224, true);
////
////                            TensorImage tensorImage = new TensorImage(DataType.UINT8);
////                            tensorImage.load(bitmap);
////                            ByteBuffer byteBuffer = tensorImage.getBuffer();
////
////                            inputFeature.loadBuffer(byteBuffer);
////
////                            // Runs model inference and gets result.
////                            Modelv4Resnet50.Outputs outputs = model.process(inputFeature);
////                            List<Category> probability = outputs.getProbabilityAsCategoryList();
////
////                            probability.sort(Comparator.comparing(Category::getScore, Comparator.reverseOrder()));
//////                        result.setText(probability.toString());
////                            String label = probability.get(0).getLabel();
////                            String label2 = probability.get(1).getLabel();
////                            float score = probability.get(0).getScore();
////                            float score2 = probability.get(1).getScore();
////                            result.setText(label + " " + score);
////                            test.setText(label2 + " " + score2);
////
////                            model.close();
////                        }
//
//                        // Creates inputs for reference.
////                        TensorBuffer inputFeature = TensorBuffer.createFixedSize(new int[]{1, 224, 224, 3}, DataType.UINT8);
////
////                        bitmap = Bitmap.createScaledBitmap(bitmap, 224, 224, true);
//
//                        // turn image to greyscale
////                        int width, height;
////                        height = bitmap.getHeight();
////                        width = bitmap.getWidth();
////
////                        Bitmap bmpGrayscale = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
////                        Canvas c = new Canvas(bmpGrayscale);
////                        Paint paint = new Paint();
////                        ColorMatrix cm = new ColorMatrix();
////                        cm.setSaturation(0);
////                        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
////                        paint.setColorFilter(f);
////                        c.drawBitmap(bitmap, 0, 0, paint);
////                        return bmpGrayscale;
//
////                        TensorImage tensorImage = new TensorImage(DataType.UINT8);
////                        tensorImage.load(bitmap);
////                        ByteBuffer byteBuffer = tensorImage.getBuffer();
////
////                        inputFeature.loadBuffer(byteBuffer);
//
////                        // Runs model inference and gets result.
////                        Modelv2EfficientnetLite.Outputs outputs = model.process(inputFeature);
////                        Modelv2Mobilenetv2.Outputs outputs = model.process(inputFeature);
////                        Modelv2Resnet50.Outputs outputs = model.process(inputFeature);
////                        List<Category> probability = outputs.getProbabilityAsCategoryList();
////
////                        probability.sort(Comparator.comparing(Category::getScore, Comparator.reverseOrder()));
//////                        result.setText(probability.toString());
////                        String label = probability.get(0).getLabel();
////                        String label2 = probability.get(1).getLabel();
////                        float score = probability.get(0).getScore();
////                        float score2 = probability.get(1).getScore();
////                        result.setText(label + " " + score);
////                        test.setText(label2 + " " + score2);
////
////                        model.close();
////                        if (result.getText().toString().equals("Stop Sign")) {
////                            test.setText(R.string.tf1);
////                        } else if (result.getText().toString().equals("Pedestrian Crossing Sign")) {
////                            test.setText(R.string.tf2);
////                        } else {
////                            test.setText(R.string.tf3);
////                        }
//
//                        // Releases model resources if no longer used.
//
//
//                    } catch (IOException e) {
//                        // TODO Handle the exception
//                    }
//
//                } else {
//                    Toast.makeText(getApplicationContext(), "No Image Detected", Toast.LENGTH_SHORT).show();
//                }
////                popup();
//            }
//        });
    }

    void getPermission() {
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, 9);
        }
    }

    public void feedback() {
        Intent Email = new Intent(Intent.ACTION_SEND);
        Email.setType("text/email");
        Email.putExtra(Intent.EXTRA_EMAIL, new String[] { "kerrmicko.lanante@evsu.edu.ph" });
        Email.putExtra(Intent.EXTRA_SUBJECT, "Feedback");
        Email.putExtra(Intent.EXTRA_TEXT, "To the developers ..., ");
        startActivity(Intent.createChooser(Email, "Send Feedback:"));
        // inflate the layout of the popup window
//        LayoutInflater inflater = (LayoutInflater)
//                getSystemService(LAYOUT_INFLATER_SERVICE);
//        View popupView = inflater.inflate(R.layout.feedback_window, null);
//
//        // create the popup window
//        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
//        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
//        boolean focusable = true; // lets taps outside the popup also dismiss it
//        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
//
//        Button sendbtn = popupView.findViewById(R.id.sendbtn);
////        EditText txtEmail = popupView.findViewById(R.id.editText_email);
//        EditText txtMessage = popupView.findViewById(R.id.editText_feedback);
//        sendbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                final String username = "mixy7777@gmail.com";
////                final String password = "JFrame.this";
////                String messageToSend = txtMessage.getText().toString();
////                Properties props = new Properties();
////                props.put("mail");
////                Intent Email = new Intent(Intent.ACTION_SEND);
////                Email.setType("text/email");
////                Email.putExtra(Intent.EXTRA_EMAIL, new String[] { "kerrmicko.lanante@evsu.edu.ph" });
////                Email.putExtra(Intent.EXTRA_SUBJECT, "Feedback");
////                Email.putExtra(Intent.EXTRA_TEXT, "Hello ..., " + txtMessage.getText().toString());
////                startActivity(Intent.createChooser(Email, "Send Feedback:"));
////                Toast.makeText(getApplicationContext(), "Test", Toast.LENGTH_SHORT).show();
//            }
//        });

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
//        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
//        popupWindow.setElevation(20);
//        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
    }

    public void help() {
        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.help_window, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        popupWindow.setElevation(20);
        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);

        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
    }

    public void predict() {
        if (bitmap != null) {
            try {

                Modelv6EfficientnetLite model1 = Modelv6EfficientnetLite.newInstance(getApplicationContext());
                Modelv6Mobilenetv2 model2 = Modelv6Mobilenetv2.newInstance(getApplicationContext());
                Modelv6Resnet50 model3 = Modelv6Resnet50.newInstance(getApplicationContext());

                // Creates inputs for reference.
                TensorBuffer inputFeature = TensorBuffer.createFixedSize(new int[]{1, 224, 224, 3}, DataType.UINT8);

                bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
                bitmap = Bitmap.createScaledBitmap(bitmap, 224, 224, true);

                TensorImage tensorImage = new TensorImage(DataType.UINT8);
                tensorImage.load(bitmap);
                ByteBuffer byteBuffer = tensorImage.getBuffer();

                inputFeature.loadBuffer(byteBuffer);

                // Runs model inference and gets result.
//                Modelv6EfficientnetLite.Outputs outputs1 = model1.process(inputFeature);
//                Modelv6Mobilenetv2.Outputs outputs2 = model2.process(inputFeature);
                Modelv6Resnet50.Outputs outputs3 = model3.process(inputFeature);

//                List<Category> probability1 = outputs1.getProbabilityAsCategoryList();
//                List<Category> probability2 = outputs2.getProbabilityAsCategoryList();
                List<Category> probability3 = outputs3.getProbabilityAsCategoryList();

//                probability1.sort(Comparator.comparing(Category::getScore, Comparator.reverseOrder()));
//                probability2.sort(Comparator.comparing(Category::getScore, Comparator.reverseOrder()));
                probability3.sort(Comparator.comparing(Category::getScore, Comparator.reverseOrder()));

//                String labelA1 = probability1.get(0).getLabel();
//                String labelA2 = probability1.get(1).getLabel();

//                String labelB1 = probability2.get(0).getLabel();
//                String labelB2 = probability2.get(1).getLabel();

                String labelC1 = probability3.get(0).getLabel();
                String labelC2 = probability3.get(1).getLabel();

//                float scoreA1 = probability1.get(0).getScore();
//                float scoreA2 = probability1.get(1).getScore();

//                float scoreB1 = probability2.get(0).getScore();
//                float scoreB2 = probability2.get(1).getScore();

                float scoreC1 = probability3.get(0).getScore();
                float scoreC2 = probability3.get(1).getScore();

                model1.close();
                model2.close();
                model3.close();

                LayoutInflater inflater = (LayoutInflater)
                        getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.predict_window, null);
                View popupView2 = inflater.inflate(R.layout.details_window, null);
                View loadingWindow = inflater.inflate(R.layout.loading, null);

                TextView predictA1 = popupView.findViewById(R.id.predictA1);
                TextView predictA2 = popupView.findViewById(R.id.predictA2);
                TextView predictB1 = popupView.findViewById(R.id.predictB1);
                TextView predictB2 = popupView.findViewById(R.id.predictB2);
                TextView predictC1 = popupView.findViewById(R.id.predictC1);
                TextView predictC2 = popupView.findViewById(R.id.predictC2);

                TextView sign_name = popupView2.findViewById(R.id.title_details);
                TextView sign_desc = popupView2.findViewById(R.id.desc_details);

                ImageView sign_image = popupView2.findViewById(R.id.imageView_details);

                sign_image.setImageBitmap(bitmap);

                sign_desc.setMovementMethod(new ScrollingMovementMethod());
//                        sign_desc.setJustificationMode(LineBreaker.JUSTIFICATION_MODE_INTER_WORD);

                if (scoreC1 <= .30) {
                    sign_name.setText(R.string.name_test);
                    sign_desc.setText(R.string.desc_test);
                } else {
                    //note: result is based on resnet50 model
//                    String sign_result = labelC1;
                    switch (labelC1) {
                        case "Men at Work Sign":
                            sign_name.setText(R.string.name_men_at_work);
                            sign_desc.setText(R.string.desc_men_at_work);
                            break;
                        case "No Entry Sign":
                            sign_name.setText(R.string.name_no_entry);
                            sign_desc.setText(R.string.desc_no_entry);
                            break;
                        case "No Overtaking Sign":
                            sign_name.setText(R.string.name_no_overtaking);
                            sign_desc.setText(R.string.desc_no_overtaking);
                            break;
                        case "No Turns Sign":
                            sign_name.setText(R.string.name_no_turns);
                            sign_desc.setText(R.string.desc_no_turns);
                            break;
                        case "Pedestrian Crossing Sign":
                            sign_name.setText(R.string.name_pedestrian_crossing);
                            sign_desc.setText(R.string.desc_pedestrian_crossing);
                            break;
                        case "School Zone Sign":
                            sign_name.setText(R.string.name_school_zone);
                            sign_desc.setText(R.string.desc_school_zone);
                            break;
                        case "Stop Sign":
                            sign_name.setText(R.string.name_stop);
                            sign_desc.setText(R.string.desc_stop);
                            break;
                        case "Traffic Lights Ahead Sign":
                            sign_name.setText(R.string.name_traffic_lights_ahead);
                            sign_desc.setText(R.string.desc_traffic_lights_ahead);
                            break;
                        case "Turn Left Ahead Sign":
                            sign_name.setText(R.string.name_turn_left_ahead);
                            sign_desc.setText(R.string.desc_turn_left_ahead);
                            break;
                        case "Turn Right Ahead Sign":
                            sign_name.setText(R.string.name_turn_right_ahead);
                            sign_desc.setText(R.string.desc_turn_right_ahead);
                            break;

                        case "No Parking Sign":
                            sign_name.setText(R.string.name_no_parking);
                            sign_desc.setText(R.string.desc_no_parking);
                            break;
                        case "No Left Turn Sign":
                            sign_name.setText(R.string.name_no_left_turn);
                            sign_desc.setText(R.string.desc_no_left_turn);
                            break;
                        case "No Right Turn Sign":
                            sign_name.setText(R.string.name_no_right_turn);
                            sign_desc.setText(R.string.desc_no_right_turn);
                            break;
                        case "Approach to Intersection Sign":
                            sign_name.setText(R.string.name_approach_to_intersection);
                            sign_desc.setText(R.string.desc_approach_to_intersection);
                            break;
                        case "Camping Area Sign":
                            sign_name.setText(R.string.name_camping_area);
                            sign_desc.setText(R.string.desc_camping_area);
                            break;
                        case "Double Sharp Turn Sign":
                            sign_name.setText(R.string.name_double_sharp_turn);
                            sign_desc.setText(R.string.desc_double_sharp_turn);
                            break;
                        case "Gasoline Station Sign":
                            sign_name.setText(R.string.name_gasoline_station);
                            sign_desc.setText(R.string.desc_gasoline_station);
                            break;
                        case "Give Way Sign":
                            sign_name.setText(R.string.name_give_way);
                            sign_desc.setText(R.string.desc_give_way);
                            break;
                        case "Hump Ahead Sign":
                            sign_name.setText(R.string.name_hump_ahead);
                            sign_desc.setText(R.string.desc_hump_ahead);
                            break;
                        case "Keep Left Sign":
                            sign_name.setText(R.string.name_keep_left);
                            sign_desc.setText(R.string.desc_keep_left);
                            break;
                        case "Keep Right Sign":
                            sign_name.setText(R.string.name_keep_right);
                            sign_desc.setText(R.string.desc_keep_right);
                            break;
                        case "Landslide Prone Area Sign":
                            sign_name.setText(R.string.name_landslide_prone_area);
                            sign_desc.setText(R.string.desc_landslide_prone_area);
                            break;
                        case "No Blowing of Horns Sign":
                            sign_name.setText(R.string.name_no_blowing_of_horns);
                            sign_desc.setText(R.string.desc_no_blowing_of_horns);
                            break;
                        case "No Cars Sign":
                            sign_name.setText(R.string.name_no_cars);
                            sign_desc.setText(R.string.desc_no_cars);
                            break;
                        case "No Pedestrian Crossing Sign":
                            sign_name.setText(R.string.name_no_pedestrian_crossing);
                            sign_desc.setText(R.string.desc_no_pedestrian_crossing);
                            break;
                        case "No U-Turn Sign":
                            sign_name.setText(R.string.name_no_u_turn);
                            sign_desc.setText(R.string.desc_no_u_turn);
                            break;
                        case "Opening Bridge Ahead Sign":
                            sign_name.setText(R.string.name_opening_bridge_ahead);
                            sign_desc.setText(R.string.desc_opening_bridge_ahead);
                            break;
                        case "Road Narrows Ahead Sign":
                            sign_name.setText(R.string.name_road_narrows_ahead);
                            sign_desc.setText(R.string.desc_road_narrows_ahead);
                            break;
                        case "Roundabout Sign":
                            sign_name.setText(R.string.name_roundabout);
                            sign_desc.setText(R.string.desc_roundabout);
                            break;
                        case "Sharp Turn Sign":
                            sign_name.setText(R.string.name_sharp_turn);
                            sign_desc.setText(R.string.desc_sharp_turn);
                            break;
                        case "Telephone Sign":
                            sign_name.setText(R.string.name_telephone);
                            sign_desc.setText(R.string.desc_telephone);
                            break;
                        case "Two Way Sign":
                            sign_name.setText(R.string.name_two_way);
                            sign_desc.setText(R.string.desc_two_way);
                            break;
                        case "Uneven Road Ahead Sign":
                            sign_name.setText(R.string.name_uneven_road_ahead);
                            sign_desc.setText(R.string.desc_uneven_road_ahead);
                            break;
                        case "Vehicle May Pass Either Side Sign":
                            sign_name.setText(R.string.name_vehicle_may_pass_either_side);
                            sign_desc.setText(R.string.desc_vehicle_may_pass_either_side);
                            break;
                        default:
                            sign_name.setText(R.string.name_test);
                            sign_desc.setText(R.string.desc_test);
                            break;
                    }
                }

                //todo: if to check percentage if too low change name to unknown

                final double threshold = .5;

//                if (scoreA1 <= threshold) {
//                    predictA1.setText("Unknown " + scoreA1);
//                } else {
//                    predictA1.setText(labelA1 + " " + scoreA1);
//                }

//                predictA1.setText(scoreA1 <= threshold ? "Unknown " + scoreA1 : labelA1 + " " + scoreA1);
//                predictA2.setText(scoreA2 <= threshold ? "Unknown " + scoreA2 : labelA2 + " " + scoreA2);
//                predictB1.setText(scoreB1 <= threshold ? "Unknown " + scoreB1 : labelB1 + " " + scoreB1);
//                predictB2.setText(scoreB2 <= threshold ? "Unknown " + scoreB2 : labelB2 + " " + scoreB2);
                predictC1.setText(scoreC1 <= threshold ? "Unknown " + scoreC1 : labelC1 + " " + scoreC1);
                predictC2.setText(scoreC2 <= threshold ? "Unknown " + scoreC2 : labelC2 + " " + scoreC2);

//                if (scoreA2 <= threshold) {
//                    predictA2.setText("Unknown " + scoreA2);
//                } else {
//                    predictA2.setText(labelA2 + " " + scoreA2);
//                }
//
//                if (scoreB1 <= threshold) {
//                    predictB1.setText("Unknown " + scoreA2);
//                } else {
//                    predictB1.setText(labelB1 + " " + scoreB1);
//                }
//
//                if (scoreB2 <= threshold) {
//                    predictB2.setText("Unknown " + scoreA2);
//                }  else {
//                    predictB2.setText(labelB2 + " " + scoreB2);
//                }
//
//                if (scoreC1 <= threshold) {
//                    predictC1.setText("Unknown " + scoreA2);
//                } else {
//                    predictC1.setText(labelC1 + " " + scoreC1);
//                }
//
//                if (scoreC2 <= threshold) {
//                    predictC2.setText("Unknown " + scoreA2);
//                } else {
//                    predictC2.setText(labelC2 + " " + scoreC2);
//                }

                // create the popup window
                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;

                // lets the tap outside the popup dismiss it
                boolean focusable = true;
//                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
                final PopupWindow popupWindow = new PopupWindow(popupView2, width, height, focusable);
                final PopupWindow loading = new PopupWindow(loadingWindow, width, height);

                loading.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                loading.setElevation(25);
                loading.showAtLocation(loadingWindow, Gravity.CENTER, 0, 0);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loading.dismiss();
                        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                        popupWindow.setElevation(20);
                        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
//                popupWindow.showAtLocation(popupView2, Gravity.CENTER, 0, 0);
                    }
                }, 2000);

                // show the popup window
                // which view you pass in doesn't matter, it is only used for the window tolken

            } catch (IOException e) {
                // TODO Handle the exception
            }

        } else {
            Toast.makeText(getApplicationContext(), "No Image Detected", Toast.LENGTH_SHORT).show();
        }
    }

//    public void sendEmail() {
//        Intent Email = new Intent(Intent.ACTION_SEND);
//        Email.setType("text/email");
//        Email.putExtra(Intent.EXTRA_EMAIL, new String[] { "admin@hotmail.com" });
//        Email.putExtra(Intent.EXTRA_SUBJECT, "Feedback");
//        Email.putExtra(Intent.EXTRA_TEXT, "Dear ...," + "");
//        startActivity(Intent.createChooser(Email, "Send Feedback:"));
//    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //9 = permission
        if (requestCode == 9) {
            if (grantResults.length>0) {
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    this.getPermission();
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //10 = select image
        if (requestCode == 10) {
            if (data!=null) {
                Uri uri = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
//                    imageView.setImageBitmap(bitmap);

                    int width, height;
                    height = bitmap.getHeight();
                    width = bitmap.getWidth();

                    Mat tmp = new Mat(width, height, CvType.CV_8UC1);
                    Utils.bitmapToMat(bitmap, tmp);
                    //greyscale
                    Imgproc.cvtColor(tmp, tmp, Imgproc.COLOR_RGB2GRAY);
                    //ksize width and height must be positive and odd
//                    Imgproc.GaussianBlur(tmp, tmp, new Size(5, 5), 0, 0);
//                    Imgproc.Canny(tmp, tmp, 50, 200);
                    Utils.matToBitmap(tmp, bitmap);

                    imageView1.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            //11 = capture
        } else if (requestCode == 11) {
            if (data!=null) {
                try {
                    bitmap = (Bitmap) data.getExtras().get("data");
//                  imageView1.setImageBitmap(bitmap);

                    int width, height;
                    height = bitmap.getHeight();
                    width = bitmap.getWidth();

                    Mat tmp = new Mat(width, height, CvType.CV_8UC1);
                    Utils.bitmapToMat(bitmap, tmp);
                    //greyscale
                    Imgproc.cvtColor(tmp, tmp, Imgproc.COLOR_RGB2GRAY);
                    //ksize width and height must be positive and odd
//                  Imgproc.GaussianBlur(tmp, tmp, new Size(5, 5), 0, 0);
//                  Imgproc.Canny(tmp, tmp, 50, 200);
                    Utils.matToBitmap(tmp, bitmap);

                    predict();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"No image detected please try again.", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(getApplicationContext(),"No image detected please try again.", Toast.LENGTH_SHORT).show();
            }


//            imageView2.setImageBitmap(bitmap);

//          Predict
//            if (bitmap != null) {
//                try {
//                    ModelEfficientnetLite model = ModelEfficientnetLite.newInstance(getApplicationContext());
////                        ModelResnet50 model = ModelResnet50.newInstance(getApplicationContext());
////                        ModelMobilenetv2 model = ModelMobilenetv2.newInstance(getApplicationContext());
//
//                    // Creates inputs for reference.
//                    TensorBuffer inputFeature = TensorBuffer.createFixedSize(new int[]{1, 224, 224, 3}, DataType.UINT8);
//
//                    bitmap = Bitmap.createScaledBitmap(bitmap, 224, 224, true);
//
//                    TensorImage tensorImage = new TensorImage(DataType.UINT8);
//                    tensorImage.load(bitmap);
//                    ByteBuffer byteBuffer = tensorImage.getBuffer();
//
//                    inputFeature.loadBuffer(byteBuffer);
//
//                    // Runs model inference and gets result.
//                    ModelEfficientnetLite.Outputs outputs = model.process(inputFeature);
////                        ModelResnet50.Outputs outputs = model.process(inputFeature);
////                        ModelMobilenetv2.Outputs outputs = model.process(inputFeature);
//                    List<Category> probability = outputs.getProbabilityAsCategoryList();
//
//                    probability.sort(Comparator.comparing(Category::getScore, Comparator.reverseOrder()));
////                        result.setText(probability.get(0).getLabel());
//                    String label = probability.get(0).getLabel();
//                    float score = probability.get(0).getScore();
//                    result.setText(label + " " + score);
//
//                    if (result.getText().toString().equals("Stop Sign")) {
//                        test.setText(R.string.tf1);
//                    } else if (result.getText().toString().equals("Pedestrian Crossing Sign")) {
//                        test.setText(R.string.tf2);
//                    } else {
//                        test.setText(R.string.tf3);
//                    }
//
//                    // Releases model resources if no longer used.
//                    model.close();
//
//                } catch (IOException e) {
//                    // TODO Handle the exception
//                }
//            } else {
//                Toast.makeText(getApplicationContext(), "No Image Detected", Toast.LENGTH_SHORT).show();
//            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}