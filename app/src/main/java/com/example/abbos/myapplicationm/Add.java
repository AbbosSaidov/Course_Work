package com.example.abbos.myapplicationm;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class Add extends AppCompatActivity {
    ImageView imageView;
    EditText title;
    EditText Description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        imageView =findViewById(R.id.imageView4);
        title=findViewById(R.id.editText);
        Description=findViewById(R.id.editText2);
    }

    public void pickImage (View view){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 5);
    }
    private int count=0;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == 5){
            try {
                InputStream inputStream = this.getContentResolver().openInputStream(Objects.requireNonNull(data.getData()));
                inputStream = new BufferedInputStream(inputStream);

                Bitmap bmp = BitmapFactory.decodeStream(inputStream);
                File file = null;
                for(int i=1;i<15;i++){
                     file= new File(Environment.getExternalStorageDirectory() + "/DirName", "image"+i);
                    if(!file.exists()){
                        count=i;
                        break;
                    }
                }

                try {
                    FileOutputStream out = new FileOutputStream(file);
                    bmp.compress(Bitmap.CompressFormat.JPEG, 100, out);
                    imageView.setImageBitmap(bmp);
                    out.flush();
                    out.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }catch(FileNotFoundException e){
                e.printStackTrace();
            }
        }
    }

    public void addmovie(View view){
        File gpxfile = new File(Environment.getExternalStorageDirectory() + "/DirName", "text"+count);
        try {
            FileWriter writer = new FileWriter(gpxfile);
            writer.append(title.getText()+"%%"+Description.getText());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
