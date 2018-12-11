package com.example.abbos.myapplicationm;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class ItemClicked extends AppCompatActivity {


    int[] IMAGES ={R.drawable.tp_1,R.drawable.tp_2
            ,R.drawable.tp_3_,R.drawable.tp_4,R.drawable.tp_5
            ,R.drawable.tp_6,R.drawable.tp_7,R.drawable.tp_8
            ,R.drawable.tp_9,R.drawable.tp_10,R.drawable.hg_1
            ,R.drawable.hg_2,R.drawable.hg_3,R.drawable.hg_4
            ,R.drawable.hg_5,R.drawable.hg_6,R.drawable.hg_7,R.drawable.hg_8
            ,R.drawable.hg_9,R.drawable.hg_10};
    String[] NameOfMovie ={"The Shawshank Redemption ","The Godfather"
            ,"The Godfather: Part II","The Dark Knight","12 Angry Men"
            ,"Schindler's List","The Lord of the Rings: The Return of the King",
            "Pulp Fiction","Il buono, il brutto, il cattivo","Fight Club ",
            "Avatar","Titanic","Star Wars: The Force Awakens"
            ,"Avengers: Infinity War","Jurassic World","The Avengers",
            "Furious 7","Avengers: Age of Ultron","Black Panther"
            ,"Harry Potter and the Deathly Hallows – Part 2"};
    String As="Marketed as James Cameron's Avatar, is a 2009 American epic science fiction film directed," +
            " written, produced, and co-edited by James Cameron, and stars Sam Worthington, Zoe Saldana, " +
            "Stephen Lang, Michelle Rodriguez, and Sigourney Weaver. The film is set in the mid-22nd century," +
            " when humans are colonizing Pandora, a lush habitable moon of a gas giant in the Alpha Centauri " +
            "star system, in order to mine the mineral unobtanium, a room-temperature superconductor. " +
            "The expansion of the mining colony threatens the continued existence of a " +
            "local tribe of Na'vi – a humanoid species indigenous to Pandora. The film's title refers to a genetically " +
            "engineered Na'vi body with the mind of a remotely located human that is used to interact with the natives of Pandora." +
            "" +
            "Development of Avatar began in 1994, when Cameron wrote an 80-page treatment for the film." +
            " Filming was supposed to take place after the completion of Cameron's 1997 film Titanic, for a planned release in 1999," +
            " but, according to Cameron, the necessary technology was not yet available to achieve his vision of the film. Work on the" +
            " language of the film's extraterrestrial beings began in 2005, and Cameron began developing the screenplay and " +
            "fictional universe in early 2006. Avatar was officially budgeted at $237 million." +
            " Other estimates put the cost between $280 million and $310 million for production and at $150 million for promotion." +
            " The film made extensive use of new motion capture filming techniques, and was released for traditional viewing, 3D viewing (using the RealD" +
            " 3D, Dolby 3D, XpanD 3D, and IMAX 3D formats), and for \"4D\" experiences in select South Korean theaters. The stereoscopic filmmaking was touted as a breakthrough in cinematic technology";

    int newString;
    private Bitmap[] bitmap =new Bitmap[15];
    private String[] Title =new String[15];
    private String[] Description =new String[15];
    private int CountAdded;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_clicked);
        obnovit();
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                newString = -1;
            } else {
                newString= extras.getInt("key");
            }
        } else {
            newString= (int) savedInstanceState.getSerializable("key");
        }
        ImageView imageView =findViewById(R.id.imageView);
        TextView textView =findViewById(R.id.textView);
        TextView textView2 =findViewById(R.id.textView2);

        if(CountAdded>newString){
            try {
                imageView.setImageBitmap(bitmap[newString]);
                textView.setText(Title[newString]);
                textView2.setText(Description[newString]);
            } catch (Exception e){
                e.printStackTrace();
            }
        }else {
            newString=newString-CountAdded;
            imageView.setImageResource(IMAGES[newString]);
            textView.setText(NameOfMovie[newString]);
            textView2.setText(As);
        }
        //Log.i("Nmdr","nmadr0="+newString);



    }

    public void OpenBrowser(View view){
        String escapedQuery = null;
        try {
            escapedQuery = URLEncoder.encode(NameOfMovie[newString], "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Uri uri = Uri.parse("http://www.google.com/#q=" + escapedQuery);
        Intent browserIntent = new Intent(Intent.ACTION_VIEW,uri);
        startActivity(browserIntent);
    }

    void obnovit(){
        CountAdded=0;
        for(int i=0;i<15;i++){
            File file = new File(Environment.getExternalStorageDirectory() + "/DirName", "image"+i);
            if(file.exists()){
                bitmap[CountAdded] = BitmapFactory.decodeFile(file.getAbsolutePath());
                try{
                    FileOutputStream out = new FileOutputStream(file);

                    bitmap[CountAdded].compress(Bitmap.CompressFormat.JPEG, 20, out);

                    out.flush();
                    out.close();

                    StringBuilder text = new StringBuilder();
                    File file2 = new File(Environment.getExternalStorageDirectory() + "/DirName","text"+i);

                    BufferedReader br = new BufferedReader(new FileReader(file2));

                    String line;
                    while ((line = br.readLine()) != null) {
                        text.append(line);
                        //  text.append('\n');
                    }

                    br.close() ;
                    for(int o=0;o<20;o++){
                        if(text.toString().substring(o, o + 2).equals("%%")){
                            Title[CountAdded]=text.toString().substring(0, o);
                            Description[CountAdded]=text.toString().substring(o+2,text.toString().length());
                            break;
                        }
                    }

                }catch(IOException e) {
                    e.printStackTrace();
                }
                CountAdded++;
            }
        }
    }
}
