package com.example.user.myapplication;

import android.content.pm.ActivityInfo;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import java.util.Random;

public class MainActivity extends Activity {
    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.imageView = (ImageView) this.findViewById(R.id.imageView1);
        Button photoButton = (Button) this.findViewById(R.id.button1);
        Button start = (Button) this.findViewById(R.id.start);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        start();

        photoButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fun(0);
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
    }
    @Override
    protected void onResume(){
        super.onResume();

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
            int[] commands = ImageProcessing.run(photo);
            Simulate.simulate(grid, commands);
        }
    }
    int width = 10;
    int height = 10;
    RelativeLayout r1;
    ImageView temp;
    int grid[][];
    Bitmap bomb;
    Bitmap grass;
    Bitmap rock;
    Bitmap player0;
    Bitmap player1;
    Bitmap player2;
    Bitmap player3;
    Bitmap flag;
    ImageView[][] images;

    public void start(){
        // grid
        grid = new int[height][width];
        Random r = new Random();
        for (int x=0; x < height; x++){
            for (int y = 0; y < width; y++){

                int i1 = (r.nextInt(3));
                grid [x][y] = i1;
            };
        };
        grid[0][0] = PLAYERUP;
        grid[height-1][width-1] = FLAG;
        r1 = (RelativeLayout) findViewById(R.id.rl);
        r1.setBackground( getResources().getDrawable(R.drawable.grass));
        Display d = getWindowManager().getDefaultDisplay();

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

        for (int x = 0; x < height; x++){
            for (int y = 0; y < width; y++){
                temp = new ImageView(this);

                temp.setX(x*size/(float)height);
                temp.setY(y*size/(float)width);

                if (grid [x][y] == BOMB){
                    temp.setImageBitmap(bomb);
                }
                if (grid [x][y] == ROCK){
                    temp.setImageBitmap(rock);
                }
                // default player is looking up
                if (grid [x][y] == PLAYER){
                    temp.setImageBitmap(player0);
                }

                if (grid [x][y] == FLAG){
                    temp.setImageBitmap(flag);
                }

                if (grid [x][y] == GRASS){
                    temp.setImageBitmap(grass);
                }

                images[x][y]=temp;
                images[x][y].setPadding(1,1,1,1);
                images[x][y].setBackgroundColor(Color.BLACK);

                r1.addView(temp);
            }
        }
    }
}
