package com.example.user.myapplication;

import android.content.pm.ActivityInfo;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;

import android.os.Handler;

import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import android.widget.TextView;

import java.sql.SQLOutput;
import java.util.ArrayList;

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

                TextView t = (TextView) findViewById(R.id.textView);
                t.setText("");
                moves.add(new MyPoint(0,0,dir));
                fun(0);

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable(){
                    public void run(){
                        MyPoint point0 = moves.get(0);
                        MyPoint point = moves.get(1);

                        images[point0.x][point0.y].setImageBitmap(grass);
                        switch (dir){
                            case 0:
                                images[point.x][point.y].setImageBitmap(player0);
                                break;
                            case 1:
                                images[point.x][point.y].setImageBitmap(player1);
                                break;
                            case 2:
                                images[point.x][point.y].setImageBitmap(player2);
                                break;
                            case 3:
                                images[point.x][point.y].setImageBitmap(player3);
                                break;
                        }

                        moves.remove(0);
                        if( moves.size()>1 )
                            handler.postDelayed(this,500);
                    }
                },500);
            }
        });
    }

    int dir=0;
    int[] ar;
    int lastIndex=8;

    @Override
    protected void onStart(){
        super.onStart();
    }
    @Override
    protected void onResume(){
        super.onResume();
    }

    private void setLastIndex() {
        int index = ImageProcessing.TILES_ROW * ImageProcessing.TILES_COL - 1;
        while(isNumber(ar[index - 1])) {
            index--;
        }
        lastIndex = index;
    }

    private boolean isNumber(int id) {
        return id >= NUMBER;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");

            ar = ImageProcessing.run(photo);
            setLastIndex();
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
    int playerX = 0;
    int playerY = 0;

    public void start(){
        // Array

        images=new ImageView[height][width];
        Array = new int[height][width];
        int level[][] ={
                {1,1,1,2,2,1,1,0,1,1},
        {0,0,1,2,4,1,1,1,1,1},
        {2,2,1,2,1,2,2,1,0,1},
        {2,2,1,1,1,0,1,2,2,1},
        {1,0,2,0,1,2,1,2,2,1},
        {1,0,0,2,1,1,1,0,2,1},
        {2,2,2,0,0,0,2,2,2,1},
        {1,2,2,2,2,2,2,2,2,1},
        {1,1,1,0,0,1,1,1,1,1},
        {1,0,1,1,1,1,2,0,0,0}};
        Random r = new Random();
        for (int x=0; x < height; x++){
            for (int y = 0; y < width; y++){


                Array [x][y] = level[x][y];
                //Array [x][y]= bombC;
            }
        }
         Array[playerY][playerX]=playerC;
        //Array[height-1][width-1]=flagC;
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

                temp.setX(y*size/(float)height);
                temp.setY(x*size/(float)width);

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

    public boolean isGrass(int y,int x){
        if(Array[y][x] == grassC){
            return true;
        }
        else
            return false;
    }
    public boolean isRock(int height,int width){
        if(Array[width][height] == rockC){
            return true;
        }
        else
            return false;

    }
    public boolean isBomb(int height,int width){
        return Array[height][width] == bombC;
    }

    public boolean isFlag(int y,int x){
        return Array[y][x] == flagC;
    }


    public static final int MOVE = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;
    public static final int IF = 4;
    public static final int FOR = 5;
    public static final int ELSE = 6;
    public static final int END = 7;
    public static final int NUMBER = 8;


    void left(){
        dir--;
        if(dir < 0){
            dir = 3;
        }
    }
    void right(){
        dir++;
        if(dir > 3){
            dir = 0;
        }
    }

    //int[] ar = {MoveC,MoveC,RightC,ForC,3+NumC,MoveC,EndC,LeftC,MoveC,MoveC,LeftC,MoveC,MoveC};
    //int lastIndex=12;


    int fun(int s){

        if(s==-1){
            return -1;
        }
        if(s>lastIndex){

            return -1;
        }
        if(ar[s] == END){
            return s;
        }

        else if(ar[s]==FOR){
            int k=ar[s+1]-NUMBER;
            int e=s;
            for(int i=0;i<k;i++){
                e = fun(s+2);
                if(e==-1)
                    return -1;

            }

            if(fun(e + 1) == -1){
                return -1;
            }

        }

        else if(ar[s]==IF){
            if(check(ar[s+1]-NUMBER)==0){
                int l=fun(s+2);
                if (l==-1)
                    return -1;
                return fun(l+1);

            }
            else{
                while(ar[s] != END && ar[s] != ELSE){
                    s++;
                }
                if(ar[s] == END){
                    return s;
                }
                else{
                    return(s + 1);
                }
            }
        }

        else if (ar[s]==MOVE){
            int t = move();
            if(t==bombC){
                loser();
                return -1;
            }

            if(t==rockC){
                return -1;
            }

            if(t==flagC){
                winner();
                return -1;
            }

        }
        else if(ar[s] == LEFT){
            left();
        }
        else if(ar[s] == RIGHT){
            right();
        }

        if(s < lastIndex && s != -1){
            return fun(s + 1);
        }
        else {
            return -1;
        }
    }


    private class MyPoint{
        int x,y,dir;
        public MyPoint(int x,int y,int dir){
            this.x = x;
            this.y = y;
            this.dir = dir;
        }
    }
    ArrayList<MyPoint> moves = new ArrayList<MyPoint>();


    public int check(int num){
        switch (dir){
            case 0:
                return validMoveRight(1,num);
            case 1:
                return validMoveDown(1,num);
            case 2:
                return validMoveLeft(1, num);
            case 3:
                return validMoveUp(1, num);
        }
        return 0;
    }
    public int move(){
        int t = check(1);
        if(t==flagC){
            updateCrush();
            return flagC;
        }
        if(t==bombC){

            updateCrush();
            return bombC;
        }
        if(t==rockC){
            return rockC;
        }
        if(t==flagC){
            return flagC;
        }

        Array[playerY][playerX]=grassC;
        //images[playerY][playerX].setImageBitmap(grass);
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
        return grassC;
    }

    public void updateGame(){
        moves.add(new MyPoint(playerY,playerX,dir));
        Array[playerY][playerX]=playerC;
        /*
        switch (dir){
            case 0:
                images[playerY][playerX].setImageBitmap(player0);
                break;
            case 1:
                images[playerY][playerX].setImageBitmap(player1);
                break;
            case 2:
                images[playerY][playerX].setImageBitmap(player2);
                break;
            case 3:
                images[playerY][playerX].setImageBitmap(player2);
                break;
        }
        */

    }

    public int validMoveRight(int z, int move){
        if(playerX+z>=width){
            return 3;
        }
        if(isGrass(playerY, playerX + z)){
            if (z==move){return grassC;}
            else
                z++;
            return validMoveRight(z, move);
        }
        if (isRock(playerY, playerX+z)){
            return rockC;
        }
        if (isBomb(playerY, playerX+z)){
            return bombC;
        }
        if (isFlag(playerY, playerX+z)){
            return flagC;
        }
        return -1;

    }
    public int validMoveLeft(int z, int move){
        if(playerX-z<0){
            return 3;
        }
        if(isGrass(playerY, playerX - z)){
            if (z==move){return grassC;}
            else
                z++;
            return validMoveLeft(z, move);
        }
        if (isRock(playerY, playerX - z)){
           return rockC;
        }
        if (isBomb(playerY, playerX - z)){
            return bombC;
        }
        if (isFlag(playerY, playerX - z)){
            return flagC;
        }
        return -1;

    }
    public int validMoveUp(int z, int move){
        if(playerY-z<0){
            return 3;
        }
        if(isGrass(playerY -z,playerX )){
            if (z==move){return grassC;}
            else
                z++;
            return validMoveUp(z, move);
        }
        if (isRock(playerY- z, playerX)){
            return rockC;
        }
        if (isBomb(playerY-z, playerX)){
            return bombC;
        }

        if (isFlag(playerY-z, playerX)){
            return flagC;
        }
        return -1;

    }
    public int validMoveDown(int z, int move){
        if(playerY+z>=height){
            return 3;
        }
        if(isGrass(playerY + z, playerX)){
            if (z==move){return grassC;}
            else
                z++;
            return validMoveDown(z, move);
        }
        if (isRock(playerY+ z, playerX)){
            return rockC;
        }
        if (isBomb(playerY+ z, playerX)){
            return bombC;
        }
        if (isFlag(playerY+ z, playerX)){
            return flagC;
        }
        return -1;

    }
    public void loser(){
        TextView t = (TextView) findViewById(R.id.textView);
        t.setText("Loser");
    }
    public void winner(){
        TextView t = (TextView) findViewById(R.id.textView);
        t.setText("Winner");
    }

    public void updateCrush(){
        Array[playerY][playerX]=grassC;
        //images[playerY][playerX].setImageBitmap(grass);
        switch (dir){
            case 0:
                playerX++;
                //images[playerY][playerX].setImageBitmap(player0);
                break;
            case 1:
                playerY++;
                //images[playerY][playerX].setImageBitmap(player1);
                break;
            case 2:
                playerX--;
                //images[playerY][playerX].setImageBitmap(player2);
                break;
            case 3:
                playerY--;
                //images[playerY][playerX].setImageBitmap(player2);
                break;
        }
        moves.add(new MyPoint(playerY,playerX,dir));
    }


}
