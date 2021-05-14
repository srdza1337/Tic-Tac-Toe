package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Game5x5 extends AppCompatActivity implements View.OnClickListener {

    private Button[][] buttons = new Button[5][5];

    private boolean player1Turn = true;

    private  int roundCount;

    private  int player1Points;
    private  int player2Points;

    private MediaPlayer clockVoice; //novo
    private CountDownTimer countDownTimer;//novo
    private int sec=10900;//novo
    public TextView timer;//novo

    public TextView textViewPlayer1;
    public TextView textViewPlayer2;
    String pl1,pl2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game5x5);

        timer= (TextView) findViewById(R.id.timer);//novo
        clockVoice= MediaPlayer.create(Game5x5.this,R.raw.music);//novo
        clockVoice.start();//novo

        Intent intent= this.getIntent();
        if (intent!=null) {
            pl1 = intent.getStringExtra("pl1");
            pl2 = intent.getStringExtra("pl2");
        }
        textViewPlayer1 = (TextView) findViewById(R.id.text_view_p1);
        textViewPlayer2 = (TextView) findViewById(R.id.text_view_p2);

        textViewPlayer1.setText(pl1+": 0");
        textViewPlayer2.setText(pl2+": 0");

        Time();//novo

        for(int  i = 0; i < 5; i++){
            for (int j = 0;j < 5; j++){
                String buttonID = "button_"  + i + j;
                int resID = getResources().getIdentifier(buttonID,"id",getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }
        Button buttonReset = findViewById(R.id.button_reset);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();

            }
        });
        Button buttonpa = (Button) findViewById(R.id.buttonpa);//novo
        buttonpa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetBoard();

                timer.setText("10");

            }
        });

        Button buttonbtm = (Button) findViewById(R.id.buttonbtm);//novo
        buttonbtm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Game5x5.this,First_Page.class));
            }
        });
    }

    @Override
    public void onClick(View v) {
        countDownTimer.start();//novo
        if (!((Button) v).getText().toString().equals("")){
            return;
        }
        if(player1Turn){
            textViewPlayer1.setTextColor(Color.parseColor("gray"));//novo
            textViewPlayer2.setTextColor(Color.parseColor("green"));//novo
            ((Button)v).setText("X");
            ((Button)v).setBackgroundColor(getResources().getColor(R.color.blue));
        } else {
            ((Button) v).setText("O");
            textViewPlayer1.setTextColor(Color.parseColor("blue"));//novo
            textViewPlayer2.setTextColor(Color.parseColor("gray"));//novo
            ((Button) v).setBackgroundColor(getResources().getColor(R.color.green));
        }
        roundCount++;

        if(checkForWin()){
            if (player1Turn){
                player1Wins();
            } else {
                player2Wins();
            }

        } else if (roundCount == 25) {
            draw();
        } else {
            player1Turn = !player1Turn;
        }
    }

    private boolean checkForWin(){
        String[][] field = new String[5][5];
        for(int i = 0 ; i<5 ;i++)
            for(int j = 0 ; j<5 ;j++){
                field[i][j] = buttons[i][j].getText().toString();
            }
        for(int i = 0 ; i<5 ;i++) {
            if      ((field[i][0].equals(field[i][1]) && field[i][0].equals(field[i][2])&& !field[i][0].equals(""))
                    ||
                    (field[i][1].equals(field[i][2]) && field[i][1].equals(field[i][3])&& !field[i][1].equals(""))
                    ||
                    (field[i][2].equals(field[i][3]) && field[i][2].equals(field[i][4])&& !field[i][2].equals("")))
            {
                return true;
            }
        }

        for(int i = 0 ; i<5 ;i++) {
            if      ((field[0][i].equals(field[1][i]) && field[0][i].equals(field[2][i]) && !field[0][i].equals(""))
                    ||
                    (field[1][i].equals(field[2][i]) && field[1][i].equals(field[3][i]) && !field[1][i].equals(""))
                    ||
                    (field[2][i].equals(field[3][i]) && field[2][i].equals(field[4][i]) && !field[2][i].equals("")))

            {
                return true;
            }
        }
        //velika dijagonala gore levo
        if ((field[0][0].equals(field[1][1]) && field[0][0].equals(field[2][2]) && !field[0][0].equals(""))
                || (field[1][1].equals(field[2][2]) && field[1][1].equals(field[3][3]) && !field[1][1].equals(""))
                || (field[2][2].equals(field[3][3]) && field[2][2].equals(field[4][4]) && !field[2][2].equals(""))){
            return true;
        }
        //velika dijagonala gore desno
        if ((field[0][3].equals(field[1][2]) && field[0][3].equals(field[2][1]) && !field[0][3].equals(""))
                || (field[1][2].equals(field[2][1]) && field[1][2].equals(field[3][0]) && !field[1][2].equals(""))) {
            return true;
        }
        //mala dijagonala iznad gore desno
        if (field[0][2].equals(field[1][1]) && field[0][2].equals(field[2][0]) && !field[0][2].equals("")) {
            return true;
        }
        //mala dijag ispod gore desno
        if ((field[0][4].equals(field[1][3]) && field[0][4].equals(field[2][2]) && !field[0][4].equals(""))
                || (field[1][3].equals(field[2][2]) && field[1][3].equals(field[3][1]) && !field[1][3].equals(""))
                || (field[2][2].equals(field[3][1]) && field[2][2].equals(field[4][0]) && !field[2][2].equals(""))) {
            return true;
        }
        //mala dijag ispod gore levo
        if (field[1][0].equals(field[2][1]) && field[1][0].equals(field[3][2]) && !field[1][0].equals("")
            ||(field[2][1].equals(field[3][2]) && field[2][1].equals(field[4][3]) && !field[2][1].equals(""))) {
            return true;
        }
        // mala dijag iznad gore levo
        if (field[0][1].equals(field[1][2]) && field[0][1].equals(field[2][3]) && !field[0][1].equals("")
            ||(field[1][2].equals(field[2][3]) && field[1][2].equals(field[3][4]) && !field[1][2].equals(""))) {
            return true;
        }
        if (field[4][1].equals(field[3][2]) && field[4][1].equals(field[2][3]) && !field[4][1].equals("")
                ||(field[3][2].equals(field[2][3]) && field[3][2].equals(field[1][4]) && !field[3][2].equals(""))) {
            return true;
        }
        if (field[2][0].equals(field[3][1]) && field[2][0].equals(field[4][2]) && !field[2][0].equals("")) {
            return true;
        }
        if (field[0][2].equals(field[1][3]) && field[0][2].equals(field[2][4]) && !field[0][2].equals("")) {
            return true;
        }
        if (field[2][4].equals(field[3][3]) && field[2][4].equals(field[4][2]) && !field[2][4].equals("")) {
            return true;
        }
        return false;
    }
    private void player1Wins() {
        player1Points++;
        Toast.makeText(this,pl1+" wins!",Toast.LENGTH_SHORT).show();
        updatePointsText();
        countDownTimer.cancel();//novo
    }
    private void player2Wins() {
        player2Points++;
        Toast.makeText(this,pl2+" wins!",Toast.LENGTH_SHORT).show();
        updatePointsText();
        countDownTimer.cancel();//novo
    }
    private void draw() {
        Toast.makeText(this, "Draw!",Toast.LENGTH_SHORT).show();
        countDownTimer.cancel();//novo
    }

    private void updatePointsText(){
        textViewPlayer1.setText(pl1+": "+player1Points);
        textViewPlayer2.setText(pl2+": "+player2Points);
        textViewPlayer1.setTextColor(Color.parseColor("blue"));//novo
        textViewPlayer2.setTextColor(Color.parseColor("gray"));//novo
    }
    private void resetBoard(){
        countDownTimer.start();//novo
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                buttons[i][j].setText("");
                buttons[i][j].setBackgroundColor(getResources().getColor(R.color.gray));//novo

            }
        }
        roundCount = 0;
        player1Turn = true;
        countDownTimer.cancel();//novo
    }
    private void resetGame(){
        player1Points = 0;
        player2Points = 0;
        updatePointsText();
        resetBoard();
    }
    private void Time() {                //novo
        countDownTimer = new CountDownTimer(sec, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                timer.setText(millisUntilFinished / 1000 + "");
            }

            @Override
            public void onFinish() {
                player1Turn = !player1Turn;
                if (player1Turn) {
                    Toast.makeText(Game5x5.this, pl1 + "'s turn.", Toast.LENGTH_SHORT).show();
                    textViewPlayer1.setTextColor(Color.parseColor("blue"));
                    textViewPlayer2.setTextColor(Color.parseColor("gray"));
                }
                else {
                    Toast.makeText(Game5x5.this, pl2 + "'s turn.", Toast.LENGTH_SHORT).show();
                    textViewPlayer1.setTextColor(Color.parseColor("gray"));
                    textViewPlayer2.setTextColor(Color.parseColor("green"));
                }
                countDownTimer.start();
                clockVoice.start();

            }
        };
    }


    @Override
    protected void onSaveInstanceState( Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("roundCount", roundCount);
        outState.putInt("player1Points", player1Points);
        outState.putInt("player2Points", player2Points);
        outState.putBoolean("player1Turn", player1Turn);
    }

    @Override
    protected void onRestoreInstanceState( Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        roundCount = savedInstanceState.getInt("roundCount");
        player1Points = savedInstanceState.getInt("player1Points");
        player2Points = savedInstanceState.getInt("player2Points");
        player1Turn = savedInstanceState.getBoolean("player1Turn");
    }
}