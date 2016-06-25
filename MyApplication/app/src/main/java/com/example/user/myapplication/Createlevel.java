package com.example.user.myapplication;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.myapplication.R;

import java.io.File;
import java.io.FileOutputStream;

public class Createlevel extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createlevel);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        start();

    }

    public class type {
        public int a=0;
        //constructor
        public type(int a) {
            this.a=a;
        }
    }
    int width = 10;
    int height = 10;
    RelativeLayout r1;
    ImageButton temp;
    final int bombC=0;
    final int grassC=1;
    final int rockC=2;
    final int playerC =3;
    final int flagC =4;
    int Array[][];
    Bitmap bomb;
    Bitmap grass;
    Bitmap rock;
    Bitmap player0;
    Bitmap player1;
    Bitmap player2;
    Bitmap player3;
    Bitmap flag;
    ImageButton[][]images;


    public void start(){
        // Array
        images=new ImageButton[height][width];
        Array = new int[height][width];

        for (int x=0; x < height; x++){
            for (int y = 0; y < width; y++){


                Array [x][y] = grassC;
            };
        };
        Display d = getWindowManager().getDefaultDisplay();
        r1 = (RelativeLayout) findViewById(R.id.rl);
        Bitmap baground = BitmapFactory.decodeResource(this.getResources(),R.drawable.grass);
        baground =Bitmap.createScaledBitmap(baground,d.getHeight(),d.getWidth(),false);
        r1.setBackground( getResources().getDrawable(R.drawable.grass));



        int size = d.getWidth();

        bomb = BitmapFactory.decodeResource(this.getResources(),R.drawable.bomb);
        bomb = Bitmap.createScaledBitmap(bomb,size/height,size/width,false);

        grass = BitmapFactory.decodeResource(this.getResources(),R.drawable.grass);
        grass = Bitmap.createScaledBitmap(grass,size/height,size/width,false);

        rock = BitmapFactory.decodeResource(this.getResources(),R.drawable.rock);
        rock = Bitmap.createScaledBitmap(rock,size/height,size/width,false);


        player0 = BitmapFactory.decodeResource(this.getResources(),R.drawable.player0);
        player0 = Bitmap.createScaledBitmap(player0,size/height,size/width,false);

        player1 = BitmapFactory.decodeResource(this.getResources(),R.drawable.player1);
        player1 = Bitmap.createScaledBitmap(player1,size/height,size/width,false);

        player2 = BitmapFactory.decodeResource(this.getResources(),R.drawable.player2);
        player2 = Bitmap.createScaledBitmap(player2,size/height,size/width,false);

        player3 = BitmapFactory.decodeResource(this.getResources(),R.drawable.player3);
        player3 = Bitmap.createScaledBitmap(player3,size/height,size/width,false);

        flag = BitmapFactory.decodeResource(this.getResources(),R.drawable.flag);
        flag = Bitmap.createScaledBitmap(flag,size/height,size/width,false);





        for (int x=0; x < height; x++){
            for (int y = 0; y < width; y++){
                temp = new ImageButton(this);
                temp.setX(x*size/(float)height);
                temp.setY(y*size/(float)width);

                temp.setPadding(1,1,1,1);
                temp.setBackgroundColor(Color.BLACK);

                if(x!=0||y!=0){
                    temp.setTag(new type(grassC));
                    temp.setImageBitmap(grass);
                    temp.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            type t=(type)v.getTag();
                            ImageButton IB=(ImageButton)v;
                            switch (t.a){
                                case grassC:
                                    IB.setTag(new type(rockC));

                                    IB.setImageBitmap(rock);

                                    break;
                                case rockC:
                                    IB.setTag(new type(bombC));
                                    IB.setImageBitmap(bomb);

                                    break;
                                case bombC:
                                    IB.setTag(new type(flagC));
                                    IB.setImageBitmap(flag);

                                    break;
                                case flagC:
                                    IB.setTag(new type(grassC));
                                    IB.setImageBitmap(grass);

                                    break;
                            }
                        }
                    });
                }
                else{
                    temp.setImageBitmap(player0);
                    temp.setTag(new type(playerC));

                }
                images[x][y]=temp;
                r1.addView(temp);

            }
        }



    }

    public void SaveLevel(View v){
        EditText eT = (EditText) findViewById(R.id.editText);

        String filename = eT.getText().toString();
        if(filename.length()==0){
            Context context = getApplicationContext();
            CharSequence text = "Please enter a name!";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return;
        }
        FileOutputStream outputStream;

        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);

            for (int x=0; x < height; x++){
                for (int y = 0; y < width; y++){
                    type t=(type)images[x][y].getTag();

                    switch (t.a){
                        case grassC:
                            outputStream.write((grassC+" ").getBytes());
                            break;
                        case rockC:
                            outputStream.write((rockC+" ").getBytes());
                            break;
                        case bombC:
                            outputStream.write((bombC+" ").getBytes());
                            break;
                        case flagC:
                            outputStream.write((flagC+" ").getBytes());
                            break;
                        case playerC:
                            outputStream.write((playerC+" ").getBytes());
                            break;
                    }
                }
            }
            outputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Back(View v){
        finish();;
    }

}



