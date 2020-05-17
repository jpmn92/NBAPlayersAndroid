package com.nbaplayersandroid;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements View.OnClickListener{

    List<Player> players;
    int record;
    Player player1;
    Player player2;

    TextView txtP1;
    TextView txtP2;
    TextView txtRecord;
    TextView txtSalary;
    ImageButton buttonMas;
    ImageButton buttonMenos;
    ImageView ivP1;
    ImageView ivP2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        players = new ArrayList<>();
        txtP1 = findViewById(R.id.txtP1);
        txtP2 = findViewById(R.id.txtP2);
        txtSalary = findViewById(R.id.txtSalary);
        txtRecord = findViewById(R.id.txtRecord);
        buttonMas = findViewById(R.id.buttonMas);
        buttonMas.setOnClickListener(this);
        buttonMenos = findViewById(R.id.buttonMenos);
        buttonMenos.setOnClickListener(this);
        ivP1 = findViewById(R.id.ivP1);
        ivP2 = findViewById(R.id.ivP2);


        createPlayers();
        startGame();
    }

    private void startGame() {
        record = 0;
        int random = 0;
        random = (int) (Math.random()*players.size());
        player1 = players.get(random);
        selectPlayer2();
    }

    private void selectPlayer2() {
        int random = 0;
        do{
            random = (int) (Math.random()*players.size());
            player2 = players.get(random);
        } while (player1.getId()-1 == player2.getId()-1 || player1.getContract() == player2.getContract());

        txtP1.setText(player1.getFirstName() + " " + player1.getLastName());
        txtSalary.setText("$" + String.valueOf(player1.getContract()) + "M");
        ivP1.setImageResource(player1.getImage());
        txtP2.setText(player2.getFirstName() + " " + player2.getLastName());
        ivP2.setImageResource(player2.getImage());
        txtRecord.setText(String.valueOf(record));


//        view.getLabelFNameP1().setText(player1.getFirstName() + " " + player1.getLastName());
//        view.getLabelFNameP2().setText(player2.getFirstName() + " " + player2.getLastName());
//        view.getRecord().setText(String.valueOf(record));
//
//        view.getFrame().pack();
    }

    private void createPlayers() {
        Player player;
        player = new Player(1, "Doncic", "Luka", 7.3f, (R.drawable.doncic));
        players.add(player);
        player = new Player(2, "James", "LeBron", 37.43f, ( R.drawable.lebron));
        players.add(player);
        player = new Player(3, "Curry", "Stephen", 40.2f, ( R.drawable.curry));
        players.add(player);
        player = new Player(4, "Durant", "Kevin", 37.2f, ( R.drawable.durant));
        players.add(player);
        player = new Player(5, "Gasol", "Marc", 25.6f, ( R.drawable.marc));
        players.add(player);
        player = new Player(6, "Siakam", "Pascal", 2.35f, ( R.drawable.siakam));
        players.add(player);
        player = new Player(7, "Leonard", "Kawhi", 32.74f, ( R.drawable.leonard));
        players.add(player);
        player = new Player(8, "Simmons", "Ben", 8.11f, ( R.drawable.simmons));
        players.add(player);
        player = new Player(9, "Porzingis", "Kristaps", 27.28f, ( R.drawable.porzingis));
        players.add(player);
    }

    private void finishGame() {
        record = 0;
        txtRecord.setText(String.valueOf(record));
        System.out.println("Perdiste");
    }

    private void continueGame() {
        record++;
        player1 = player2;
        selectPlayer2();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonMas:
                //Toast.makeText(this, "PULSADO", Toast.LENGTH_SHORT).show();
                if(player2.getContract() > player1.getContract()){
                    continueGame();
                }
                else{
                    finishGame();
                }
                break;

            case R.id.buttonMenos:
                //Toast.makeText(this, "PULSADO", Toast.LENGTH_SHORT).show();
                if(player2.getContract() < player1.getContract()){
                    continueGame();
                }
                else{
                    finishGame();
                }
                break;
        }
    }
}
