package com.nbastatsquiz;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.nbastatsquiz.R;
import com.nbastatsquiz.beans.LeagueLeader;

import com.nbastatsquiz.lst_league_leaders.LstLeagueLeaderContract;
import com.nbastatsquiz.lst_league_leaders.LstLeagueLeaderPresenter;
import com.nbastatsquiz.tools.ColorApp;
import com.nbastatsquiz.tools.FirebaseMethods;
import com.nbastatsquiz.tools.GenerateImageUrl;
import com.nbastatsquiz.tools.SessionManagement;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GameActivity extends Activity implements View.OnClickListener, LstLeagueLeaderContract.View {

    private int points, record, vidas, contadorAciertos, tiempo;
    private boolean recordConseguido;

    private MyCountDownTimer myCountDownTimer;
    private NavigationDrawerActivity navigationDrawerActivity;

    private TextView txtP1, txtP2, txtPoints, txtPregunta, txtNameP1, txtNameP2;
    private ImageView ivP1, ivP2, ivT1, ivT2, ivVidas;
    private LinearLayout linJ1, linJ2, linLoad;
    private RelativeLayout relCircle, relFront;
    private MediaPlayer mediaPlayer;

    private LstLeagueLeaderPresenter lstLeagueLeaderPresenter;
    private ArrayList<LeagueLeader> leagueLeadersGlobal;
    private LeagueLeader leagueLeader1, leagueLeader2;

    private Bundle params, paramsIniciales;
    private FirebaseMethods firebaseMethods;
    private ProgressBar progressBar;

    private Resources res;

    private DecimalFormat df;

    private float valueP1, valueP2;
    private boolean gameStarted, misc, miscStats, miscSeason, sound;
    private String season, seasonType, statCategory, perMode, activeFlag, username;
    private SessionManagement sessionManagement;

    private GenerateImageUrl generateImageUrl;

    @Override
    protected void onStart() {
        super.onStart();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sessionManagement = new SessionManagement(this);

        df = new DecimalFormat("0.00");
        tiempo = 10000;
        relFront = findViewById(R.id.relFront);
        points = 0;
        params = this.getIntent().getExtras();
        paramsIniciales = (Bundle) params.clone();
        username = sessionManagement.getSessionUserName();
        sound = sessionManagement.getSound();
        paramsIniciales.putString("userName", username);
        // Si queremos que se guarde el id del telefono para no registrar usuarios de momento
        // paramsIniciales.putString("userName", username + Settings.Secure.getString(getBaseContext().getContentResolver(), Settings.Secure.ANDROID_ID));
        season = params.getString("Season");
        seasonType = params.getString("SeasonType");
        statCategory = params.getString("StatCategory");
        perMode = params.getString("PerMode");
        activeFlag = params.getString("ActiveFlag");

        res = getResources();

        if (statCategory.equalsIgnoreCase("FG3_PCT") || statCategory.equalsIgnoreCase("FT_PCT") || statCategory.equalsIgnoreCase("FTM")) {
            params.putString("PerMode", "Totals");
        }

        miscStats = statCategory.equalsIgnoreCase("MISC");
        miscSeason = season.equalsIgnoreCase("MISC");

        misc = (miscStats || miscSeason);

        if (misc) {
            tiempo = 15000;
        }

        myCountDownTimer = new MyCountDownTimer(tiempo, 1000);

        gameStarted = false;

        initComponents();


        lstLeagueLeaderPresenter = new LstLeagueLeaderPresenter(this);
        generateImageUrl = new GenerateImageUrl();
        leagueLeadersGlobal = new ArrayList<>();

        if (misc) {
            mezclar();
        } else {
            lstLeagueLeaderPresenter.getLeagueLeaders(params);
        }

    }

    private void buscarRecord() {
        firebaseMethods.getRecord();

    }

    private void mezclar() {
        if (miscStats) {
            Resources res = getResources();
            String[] categories = res.getStringArray(R.array.TipoCategoria);

            int random = (int) (Math.random() * (categories.length - 1) + 1);

            String stat = getParam(R.array.TipoCategoria, random);

            params.putString("StatCategory", stat);
            statCategory = params.getString("StatCategory");
        }

        if (statCategory.equalsIgnoreCase("FG3_PCT") || statCategory.equalsIgnoreCase("FT_PCT") || statCategory.equalsIgnoreCase("FTM")) {
            params.putString("PerMode", "Totals");
        } else {
            params.putString("PerMode", perMode);
        }

        if (miscSeason) {
            Resources res = getResources();
            String[] categories = res.getStringArray(R.array.Temporadas);

            int random = (int) (Math.random() * (categories.length - 1) + 1);

            params.putString("Season", categories[random]);
            season = params.getString("Season");
        }

        tiempo = 15000;
        progressBar.setMax(14);
        lstLeagueLeaderPresenter.getLeagueLeaders(params);
    }

    public String getParam(int arrayId, int pos) {
        TypedArray resourceIDS = res.obtainTypedArray(arrayId);
        int[] resIds = new int[resourceIDS.length()];
        for (int i = 0; i < resourceIDS.length(); i++) {
            resIds[i] = resourceIDS.getResourceId(i, -1);
        }
        resourceIDS.recycle();
        String getParam = res.getResourceEntryName(resIds[pos]);

        return getParam;
    }


    private void initComponents() {

        txtPregunta = findViewById(R.id.txtPregunta);
        txtP1 = findViewById(R.id.txtP1);
        txtP1.setTextColor(Color.RED);
        txtP2 = findViewById(R.id.txtP2);
        txtP2.setTextColor(Color.RED);
        txtNameP1 = findViewById(R.id.txtNameP1);
        txtNameP2 = findViewById(R.id.txtNameP2);
        txtPoints = findViewById(R.id.txtPuntuacion);
        ivP1 = findViewById(R.id.ivP1);
        ivP2 = findViewById(R.id.ivP2);
        ivT1 = findViewById(R.id.ivTeam1);
        ivT2 = findViewById(R.id.ivTeam2);
        ivVidas = findViewById(R.id.ivVidas);
        linJ1 = findViewById(R.id.linJ1);
        linJ1.setOnClickListener(this);
        linJ2 = findViewById(R.id.linJ2);
        linJ2.setOnClickListener(this);
        relFront.setOnClickListener(this);
        linLoad = findViewById(R.id.linLoad);
        linLoad.setOnClickListener(this);

        vidas = 3;
        contadorAciertos = 0;

        progressBar = findViewById(R.id.progressBar);
        progressBar.setProgress(0);

        final ProgressDialog progressDialog = new ProgressDialog(this, R.style.Theme_AppCompat_DayNight_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Cargando...");
        progressDialog.show();

        linLoad.postDelayed(new Runnable() {
            public void run() {
                linLoad.setVisibility(View.GONE);
                progressDialog.dismiss();

            }
        }, 1500);

        relFront.setVisibility(View.GONE);
        relCircle = findViewById(R.id.relCircle);

        txtPregunta.setText(statCategory + " " + season);

        firebaseMethods = new FirebaseMethods(this, paramsIniciales);
        buscarRecord();
    }


    private void selectPlayers() {

        int random = (int) (Math.random() * leagueLeadersGlobal.size());
        leagueLeader1 = leagueLeadersGlobal.get(random);
        selectPlayer2();


    }

    private void selectPlayer2() {
        int random = 0;
        do {

            random = (int) (Math.random() * leagueLeadersGlobal.size());
            leagueLeader2 = leagueLeadersGlobal.get(random);
            recogerDatos();

        } while (leagueLeader1.getPLAYER_ID() == leagueLeader2.getPLAYER_ID());




    }

    private void iluminar(String color) {
        txtP1.setText(String.valueOf(df.format(valueP1)));
        txtP2.setText(String.valueOf(df.format(valueP2)));
        relFront.setBackgroundColor(Color.parseColor(color));
        relFront.setVisibility(View.VISIBLE);
        relFront.postDelayed(new Runnable() {
            public void run() {
                relFront.setVisibility(View.GONE);
            }
        }, 1000);

    }


    private void finishGame() {

        if (checkInternetConnection() == true) {
            String message;
            if (points > record) {
                record = points;
                paramsIniciales.putInt("puntos", points);
                paramsIniciales.putString("image", sessionManagement.getSesionImage());
                firebaseMethods.createFbPuntuacion(paramsIniciales);

                message = getString(R.string.new_record) + "\n" + getString(R.string.puntuacion) + points;
            } else {
                message = getString(R.string.puntuacion) + points + "\n" + getString(R.string.record) + record;
            }

            myCountDownTimer.cancel();
            showFinishedDialog(this, message);
        } else {
            myCountDownTimer.cancel();

            showFinishedDialog(this, "Internet connection lost");
        }


    }

    private void continueGame() {

        selectPlayers();

    }

    private void recogerDatos() {

        String url_imageTeam1 = generateImageUrl.checkTeamImage(leagueLeader1.getTEAM());
        String url_imageTeam2 = generateImageUrl.checkTeamImage(leagueLeader2.getTEAM());


        String url_imagen1 = generateImageUrl.checkPlayerImage(leagueLeader1.getPLAYER_ID().intValue());
        String url_imagen2 = generateImageUrl.checkPlayerImage(leagueLeader2.getPLAYER_ID().intValue());


        //si es alguno de los que no tenemos url de la imagen, que la meta a capon
        switch (leagueLeader1.getPLAYER_ID().intValue()) {
            case 1122:
                Picasso.with(this).load(R.drawable.img_1122).error(R.drawable.person).into(ivP1);
                break;
            case 304:
                Picasso.with(this).load(R.drawable.img_304).error(R.drawable.person).into(ivP1);
                break;
            case 600015:
                Picasso.with(this).load(R.drawable.img_600015).error(R.drawable.person).into(ivP1);
                break;
            case 714:
                Picasso.with(this).load(R.drawable.img_714).error(R.drawable.person).into(ivP1);
                break;

            default:
                Picasso.with(this).load(url_imagen1).error(R.drawable.person).into(ivP1);


        }

        Picasso.with(this).load(url_imageTeam1).into(ivT1);

        txtNameP1.setText(leagueLeader1.getPLAYER());
        txtNameP2.setText(leagueLeader2.getPLAYER());


        //si es alguno de los que no tenemos url de la imagen, que la meta a capon
        switch (leagueLeader2.getPLAYER_ID().intValue()) {
            case 1122:
                Picasso.with(this).load(R.drawable.img_1122).error(R.drawable.person).into(ivP2);
                break;
            case 304:
                Picasso.with(this).load(R.drawable.img_304).error(R.drawable.person).into(ivP2);
                break;
            case 600015:
                Picasso.with(this).load(R.drawable.img_600015).error(R.drawable.person).into(ivP2);
                break;
            case 714:
                Picasso.with(this).load(R.drawable.img_714).error(R.drawable.person).into(ivP2);
                break;

            default:
                Picasso.with(this).load(url_imagen2).error(R.drawable.person).into(ivP2);


        }
        Picasso.with(this).load(url_imageTeam2).into(ivT2);

        txtPoints.setText(String.valueOf(points));


        calculateValues();

    }


    private void calculateValues() {
        switch (statCategory) {
            case "PTS":
                valueP1 = leagueLeader1.getPTS().floatValue();
                valueP2 = leagueLeader2.getPTS().floatValue();
                break;

            case "AST":
                valueP1 = leagueLeader1.getAST().floatValue();
                valueP2 = leagueLeader2.getAST().floatValue();
                break;

            case "REB":
                valueP1 = leagueLeader1.getREB().floatValue();
                valueP2 = leagueLeader2.getREB().floatValue();
                break;

            case "OREB":
                valueP1 = leagueLeader1.getOREB().floatValue();
                valueP2 = leagueLeader2.getOREB().floatValue();
                break;

            case "DREB":
                valueP1 = leagueLeader1.getDREB().floatValue();
                valueP2 = leagueLeader2.getDREB().floatValue();
                break;

            case "STL":
                valueP1 = leagueLeader1.getSTL().floatValue();
                valueP2 = leagueLeader2.getSTL().floatValue();
                break;

            case "BLK":
                valueP1 = leagueLeader1.getBLK().floatValue();
                valueP2 = leagueLeader2.getBLK().floatValue();
                break;

            case "TOV":
                valueP1 = leagueLeader1.getTOV().floatValue();
                valueP2 = leagueLeader2.getTOV().floatValue();
                break;

            case "MIN":
                valueP1 = leagueLeader1.getMIN().floatValue();
                valueP2 = leagueLeader2.getMIN().floatValue();
                break;

            case "FG3M":
                valueP1 = leagueLeader1.getFG3M().floatValue();
                valueP2 = leagueLeader2.getFG3M().floatValue();
                break;

            case "FTM":
                valueP1 = leagueLeader1.getFTM().floatValue();
                valueP2 = leagueLeader2.getFTM().floatValue();
                break;

            case "FG3_PCT":
                valueP1 = leagueLeader1.getFG3_PCT().floatValue() * 100;
                valueP2 = leagueLeader2.getFG3_PCT().floatValue() * 100;
                break;

            case "FT_PCT":
                valueP1 = leagueLeader1.getFT_PCT().floatValue() * 100;
                valueP2 = leagueLeader2.getFT_PCT().floatValue() * 100;
                break;
        }


        //TODO: si es MISC dar mas segundos
        if (myCountDownTimer != null) {
            myCountDownTimer.cancel();
        }
        myCountDownTimer.start();
    }

    @Override
    public void onClick(View v) {

        //si clicamos cualquiera se cancela el contador
        myCountDownTimer.cancel();

        if (checkInternetConnection() == true) {
            switch (v.getId()) {
                case R.id.linJ2:
                    if (valueP2 >= valueP1) {
                        acierto();
                    } else {
                        fallo();
                    }

                    break;

                case R.id.linJ1:
                    if (valueP2 <= valueP1) {
                        acierto();
                    } else {
                        fallo();
                    }
                    break;
            }
        } else {
            Toast.makeText(this, R.string.sin_conexion, Toast.LENGTH_SHORT).show();
            finishGame();

        }


    }

    private void comprobarVidas() {


        switch (vidas) {
            case 0:
                ivVidas.setImageResource(R.drawable.vidas0);
                break;
            case 1:
                ivVidas.setImageResource(R.drawable.vidas1);
                break;
            case 2:
                ivVidas.setImageResource(R.drawable.vidas2);
                break;
            case 3:
                ivVidas.setImageResource(R.drawable.vidas3);
                break;
        }
    }

    private void acierto() {
        if (sound == true) {
            mediaPlayer = MediaPlayer.create(this, R.raw.acierto);
            mediaPlayer.start();

        }
        iluminar(ColorApp.GREEN);
        contadorAciertos++;
        if (contadorAciertos >= 10 && vidas < 3) {
            vidas++;
            comprobarVidas();
            contadorAciertos = 0;
        }
        comprobarVidas();
        points++;
        if (misc) {
            mezclar();
        } else {
            continueGame();
        }
    }

    private void fallo() {
        if (sound) {
            mediaPlayer = MediaPlayer.create(this, R.raw.error);
            mediaPlayer.start();

        }
        contadorAciertos = 0;
        iluminar(ColorApp.RED);
        vidas--;
        comprobarVidas();
        if (vidas <= 0) {
            finishGame();
        } else {
            if (misc) {
                mezclar();
            } else {
                continueGame();
            }
//            selectPlayers();
        }

        //continueGame();


    }
    //CLASE DE CUENTA ATRÁS

    public class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {

            int progress = (int) (millisUntilFinished / 1000);

            progressBar.setProgress(progressBar.getMax() - progress);
        }

        @Override
        public void onFinish() {
//            finish();
            myCountDownTimer.cancel();
            fallo();
            //selectPlayers();
        }
    }

    @Override
    public void successListLeagueLeaders(ArrayList<LeagueLeader> leagueLeaders) {

        //pasamos a arraylist global el arraygenerado
        leagueLeadersGlobal = leagueLeaders;
        selectPlayers();
        txtPregunta.setText(statCategory + " " + season);


    }

    @Override
    public void failureListLeagueLeaders(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle(R.string.no_data_found);
        builder.setPositiveButton(R.string.back_to_menu, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                onBackPressed();
//                Intent menu = new Intent(GameActivity.this, Menu.class);
//                menu.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                GameActivity.this.startActivity(menu);
            }
        });
        builder.show();
    }

    public int getRecord() {
        return record;
    }

    public void setRecord(int record) {
        this.record = record;
    }

    @Override
    protected void onPause() {
        super.onPause();
        myCountDownTimer.cancel();
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        myCountDownTimer.cancel();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myCountDownTimer.cancel();
        finish();
    }


    //dialog fin de partida
    public void showFinishedDialog(Activity activity, CharSequence message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setCancelable(false);
        builder.setTitle(R.string.game_finished);

        builder.setMessage(message);
        builder.setPositiveButton(R.string.play_again, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                vidas = 3;
                points = 0;
                contadorAciertos = 0;
                comprobarVidas();
                selectPlayers();
            }
        });
        builder.setNegativeButton(R.string.back_to_menu, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


//                Intent menu = new Intent(GameActivity.this, NavigationDrawerActivity.class);
//                GameActivity.this.startActivity(menu);
//                GameActivity.this.finish();
                onBackPressed();

            }
        });
        builder.show();
    }

    private Boolean checkInternetConnection() {
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;


        } else {
            connected = false;
        }

        return connected;

    }


}