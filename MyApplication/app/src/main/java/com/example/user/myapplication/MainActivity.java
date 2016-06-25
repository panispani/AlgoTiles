package com.example.user.myapplication;

import android.content.pm.ActivityInfo;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import bsh.Interpreter;

import java.lang.reflect.Array;
import java.security.Timestamp;
import java.util.Calendar;
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

    int dir=0;
    @Override
    protected void onStart(){
        super.onStart();
    }
    @Override
    protected void onResume(){
        super.onResume();;

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
        }
    }
    int width = 10;
    int height = 10;
    RelativeLayout r1;
    ImageView temp;
    int bombC=0;
    int grassC=1;
    int rockC=2;
    int playerC =3;
    int flagC =4;
    int Array[][];
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
        // Array
        images=new ImageView[height][width];
        Array = new int[height][width];
        Random r = new Random();
        for (int x=0; x < height; x++){
            for (int y = 0; y < width; y++){

                int i1 = (r.nextInt(3));
                Array [x][y] = i1;
            };
        };
        Array[0][0]=playerC;
        Array[height-1][width-1]=flagC;
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





        for (int x=0; x < height; x++){
            for (int y = 0; y < width; y++){
                temp = new ImageView(this);

                temp.setX(x*size/(float)height);
                temp.setY(y*size/(float)width);

                if (Array [x][y] == bombC){


                    temp.setImageBitmap(bomb);
                }
                if (Array [x][y] == rockC){


                    temp.setImageBitmap(rock);
                }

                if (Array [x][y] == playerC){

                    switch(dir){
                        case 0:
                            temp.setImageBitmap(player0);
                            break;
                        case 1:
                            temp.setImageBitmap(player1);
                            break;
                        case 2:
                            temp.setImageBitmap(player2);
                            break;
                        case 3:
                            temp.setImageBitmap(player3);
                            break;


                    }
                }

                if (Array [x][y] == flagC){


                    temp.setImageBitmap(flag);
                }

                if (Array [x][y] == grassC){
                    temp.setImageBitmap(grass);
                }

                images[x][y]=temp;
                images[x][y].setPadding(1,1,1,1);
                images[x][y].setBackgroundColor(Color.BLACK);

                r1.addView(temp);
            }
        }



    }


    int playerX = 0;
    int playerY = 0;

    public boolean isGrass(int height,int width){
        if(Array[height][width] == grassC){
            return true;
        }
        else
            return false;
    }
    public boolean isRock(int height,int width){
        if(Array[height][width] == rockC){
            return true;
        }
        else
            return false;
    }
    public boolean isBomb(int height,int width){
        return Array[height][width] == bombC;
    }


    int ForC = 0;
    int IfC =1;
    int EndC=2;
    int ElseC=3;
    int RightC =4;
    int LeftC =5;
    int MoveC=6;
    int NumC=7;
    void left(){
        dir--;
        if(dir<0){
            dir=3;
        }
    }
    void right(){
        dir++;
        if(dir>3){
            dir=0;
        }
    }
    int[] ar = {ForC,5+NumC,MoveC,EndC,RightC,ForC,5+NumC,MoveC,EndC};
    int lastIndex=8;

    int fun(int s){

        if(s>lastIndex){
            return -1;
        }
        if(ar[s]==EndC){
            return s;
        }

        else if(ar[s]==ForC){
            int k=ar[s+1]-NumC;
            int e=s;
            for(int i=0;i<k;i++){
                e = fun (s+2);
            }

            if(fun(e+1)==-1){
                return -1;
            }

        }
        else if(ar[s]==IfC){
            if(check(ar[s+1]-NumC)==0){
                int l=fun(s+2);
                return fun(l+1);
            }
            else{
                while(ar[s]!=EndC&&ar[s]!=ElseC){
                    s++;
                }
                if(ar[s]==EndC){
                    return s;
                }
                else{
                    return(s+1);
                }
            }
        }
        else if (ar[s]==MoveC){
            move();
        }
        else if(ar[s]==LeftC){
            left();
        }
        else if(ar[s]==RightC){
            right();
        }

        if(s<lastIndex&&s!=-1){
            return fun(s+1);
        }
        else {
            return -1;
        }
    }
    public int check(int num){
        switch (dir){
            case 0:
                return validMoveRight(1,num);

            case 1:
                return validMoveDown(1,num);

            case 2:
                return validMoveLeft(1,num);

            case 3:
                return validMoveUp(1,num);

        }
      return 0;
    }
    public void move(){
        if(check(1)!=0){
            return;
        }
        Array[playerY][playerX]=grassC;
        images[playerX][playerY].setImageBitmap(grass);
        switch (dir){
            case 0:
               playerX++;
                break;
            case 1:
               playerY++;
                break;
            case 2:
                playerX--;
                break;
            case 3:
             playerY--;
                break;
        }

        updateGame();

    }

    public void updateGame(){
        Array[playerY][playerX]=playerC;
        switch (dir){
            case 0:
                images[playerX][playerY].setImageBitmap(player0);
                break;
            case 1:
                images[playerX][playerY].setImageBitmap(player1);
                break;
            case 2:
                images[playerX][playerY].setImageBitmap(player2);
                break;
            case 3:
                images[playerX][playerY].setImageBitmap(player3);
                break;
        }

    }

    public int validMoveRight(int z, int move){
        if(playerX+z>=width){
            return 3;
        }
        if(isGrass(playerY, playerX + z)){
            if (z==move){return 0;}
            else
                z++;
            return validMoveRight(z, move);
        }
        if (isRock(playerY, playerX+z)){
            return 1;
        }
        if (isBomb(playerY, playerX+z)){
            return 2;
        }
        return -1;

    }
    public int validMoveLeft(int z, int move){
        if(playerX-z<0){
            return 3;
        }
        if(isGrass(playerY, playerX - z)){
            if (z==move){return 0;}
            else
                z--;
            return validMoveLeft(z, move);
        }
        if (isRock(playerY, playerX- z)){
           return 1;
        }
        if (isBomb(playerY, playerX- z)){
            return 2;
        }
        return -1;

    }
    public int validMoveUp(int z, int move){
        if(playerY-z<0){
            return 3;
        }
        if(isGrass(playerY - z, playerX)){
            if (z==move){return 0;}
            else
                z--;
            return validMoveUp(z, move);
        }
        if (isRock(playerY- z, playerX)){
            return 1;
        }
        if (isBomb(playerY- z, playerX)){
            return 2;
        }
        return -1;

    }
    public int validMoveDown(int z, int move){
        if(playerY+z>=height){
            return 3;
        }
        if(isGrass(playerY + z, playerX)){
            if (z==move){return 0;}
            else
                z++;
            return validMoveUp(z, move);
        }
        if (isRock(playerY+ z, playerX)){
            return 1;
        }
        if (isBomb(playerY+ z, playerX)){
            return 2;
        }
        return -1;

    }
}
