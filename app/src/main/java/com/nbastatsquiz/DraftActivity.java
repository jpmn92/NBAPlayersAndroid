package com.nbastatsquiz;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.nbastatsquiz.beans.DraftPick;
import com.nbastatsquiz.lst_drafts.LstDraftsContract;
import com.nbastatsquiz.lst_drafts.LstDraftsPresenter;

import com.nbastatsquiz.tools.ColorApp;
import com.nbastatsquiz.tools.ConfigApp;
import com.nbastatsquiz.tools.FirebaseMethods;
import com.nbastatsquiz.tools.GenerateImageUrl;
import com.nbastatsquiz.tools.SessionManagement;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class DraftActivity extends Activity implements View.OnClickListener, LstDraftsContract.View {

    private int points, record, vidas, contadorAciertos, tiempo;
    private boolean recordConseguido;
    private InterstitialAd mInterstitialAd;
    private AdView adViewGameDraft;


    private MyCountDownTimer myCountDownTimer;
    private NavigationDrawerActivity navigationDrawerActivity;

    private TextView txtP1, txtP2, txtPoints, txtPregunta, txtNameP1, txtNameP2;
    private ImageView ivP1, ivP2, ivT1, ivT2, ivVidas, ivC1, ivC2;
    private LinearLayout linJ1, linJ2, linLoad;
    private RelativeLayout relCircle, relFront;
    private MediaPlayer mediaPlayer;

    private LstDraftsPresenter lstDraftsPresenter;
    private ArrayList<DraftPick> draftPicksGlobal;
    private DraftPick draftPick1, draftPick2;

    private Bundle params, paramsIniciales;
    private FirebaseMethods firebaseMethods;
    private ProgressBar progressBar;

    private Resources res;

    private int valueP1, valueP2;
    private boolean gameStarted, misc, miscSeason, sound, crono;
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
        setContentView(R.layout.activity_draft);

        inicializarPublicidad();
        inicializarPublicidadBanner((LinearLayout) findViewById(R.id.lndraftad));

        sessionManagement = new SessionManagement(this);

        tiempo = 10000;
        relFront = findViewById(R.id.relFrontDraft);
        points = 0;
        params = this.getIntent().getBundleExtra("miBundle");
        paramsIniciales = (Bundle) params.clone();
        username = sessionManagement.getSessionUserName();
        sound = sessionManagement.getSound();
        crono = sessionManagement.getCrono();

        misc = params.getString("Season").equalsIgnoreCase("MISC");


        res = getResources();

        if(misc){
            tiempo = 15000;
        }

        myCountDownTimer = new MyCountDownTimer(tiempo, 1000);

        gameStarted = false;

        initComponents();


        lstDraftsPresenter = new LstDraftsPresenter(this);
        generateImageUrl = new GenerateImageUrl();
        draftPicksGlobal = new ArrayList<>();

        if (misc) {
            mezclar();
        } else {
            lstDraftsPresenter.getDrafts(params);
        }

    }

    private void inicializarPublicidad() {
        MobileAds.initialize(this);
        mInterstitialAd = new InterstitialAd(this);

        if(ConfigApp.PUBLICIDAD_DEVELOPER){
            mInterstitialAd.setAdUnitId(getString(R.string.bloque_publicidad_intersticial_prueba)); //ESTE ES EL DE PRUEBA
        }
        else{
            mInterstitialAd.setAdUnitId(getString(R.string.bloque_publicidad_intersticial_draft)); // ESTE ES EL DE VERDAD
        }
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                //System.out.println("PUBLI CERRADA");
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
                if(crono){
                    myCountDownTimer.start();
                }
            }
        });
    }

    private void inicializarPublicidadBanner( View view) {
        MobileAds.initialize(this);
        adViewGameDraft = view.findViewById(R.id.adViewGameDraft);
        AdRequest adRequest = new AdRequest.Builder().build();
        System.out.println(adViewGameDraft.getAdUnitId());
        adViewGameDraft.loadAd(adRequest);
        adViewGameDraft.setAdListener(new AdListener(){
            @Override
            public void onAdClosed() {
                adViewGameDraft.loadAd(new AdRequest.Builder().build());
            }
        });
    }

    private void buscarRecord() {
        if (params.getBoolean("loged") && crono) {
            firebaseMethods.getRecord();
        }

    }

    private void mezclar() {


        Resources res = getResources();
        String[] categories = res.getStringArray(R.array.DraftYears);

        int random = (int) (Math.random() * (categories.length - 1) + 1);

        params.putString("Season", categories[random]);
        season = params.getString("Season");


        tiempo = 15000;
        progressBar.setMax(14);
        lstDraftsPresenter.getDrafts(params);
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

        txtPregunta = findViewById(R.id.txtPreguntaDraft);
        txtP1 = findViewById(R.id.txtP1Draft);
        txtP1.setTextColor(Color.RED);
        txtP2 = findViewById(R.id.txtP2Draft);
        txtP2.setTextColor(Color.RED);
        txtNameP1 = findViewById(R.id.txtNameP1Draft);
        txtNameP2 = findViewById(R.id.txtNameP2Draft);
        txtPoints = findViewById(R.id.txtPuntuacionDraft);
        ivP1 = findViewById(R.id.ivP1Draft);
        ivP2 = findViewById(R.id.ivP2Draft);
        ivT1 = findViewById(R.id.ivTeam1Draft);
        ivT2 = findViewById(R.id.ivTeam2Draft);
        ivC1 = findViewById(R.id.ivCollege1Draft);
        ivC2 = findViewById(R.id.ivCollege2Draft);

        ivVidas = findViewById(R.id.ivVidasDraft);
        linJ1 = findViewById(R.id.linJ1Draft);
        linJ1.setOnClickListener(this);
        linJ2 = findViewById(R.id.linJ2Draft);
        linJ2.setOnClickListener(this);
        relFront.setOnClickListener(this);
        linLoad = findViewById(R.id.linLoadDraft);
        linLoad.setOnClickListener(this);

        vidas = 3;
        contadorAciertos = 0;

        progressBar = findViewById(R.id.progressBarDraft);
        progressBar.setProgress(0);

        final ProgressDialog progressDialog = new ProgressDialog(DraftActivity.this, R.style.Theme_AppCompat_DayNight_Dialog);
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
        relCircle = findViewById(R.id.relCircleDraft);

//        txtPregunta.setText(statCategory + " " + season);

        firebaseMethods = new FirebaseMethods(DraftActivity.this, paramsIniciales);
        buscarRecord(); //TODO: AUN NO HAY RECORDS
    }


    private void selectDraftPicks() {


        if(misc){
            //si draftpick 1 es null que le asigne un valor y llame al segundo metodo
            if (draftPick1 == null) {
                int random = (int) (Math.random() * draftPicksGlobal.size());
                draftPick1 = draftPicksGlobal.get(random);
                mezclar();
//            selectDraftPick2();
            } else {
                //mezclamos y hacemos una segunda llamada si el 1 ya esta relleno
                mezclar();

            }
        }else{

            int random = (int) (Math.random() * draftPicksGlobal.size());
            draftPick1 = draftPicksGlobal.get(random);
            selectDraftPick2();
        }



    }

    private void selectDraftPick2() {
        int random = 0;
        do {


            random = (int) (Math.random() * draftPicksGlobal.size());
            draftPick2 = draftPicksGlobal.get(random);
            recogerDatos();

        } while (draftPick1.getPLAYER_NAME() == draftPick2.getPLAYER_NAME());


    }

    private void iluminar(String color) {
        txtP1.setText(String.valueOf(valueP1));
        txtP2.setText(String.valueOf(valueP2));
        relFront.setBackgroundColor(Color.parseColor(color));
        relFront.setVisibility(View.VISIBLE);
        relFront.postDelayed(new Runnable() {
            public void run() {
                relFront.setVisibility(View.GONE);
            }
        }, 1000);

    }


    private void finishGame() {

        if (checkInternetConnection()) {
            String message;
            if (params.getBoolean("loged") && crono) {
                FirebaseAuth mAuth;
                mAuth = FirebaseAuth.getInstance();
                FirebaseUser firebaseUser = mAuth.getCurrentUser();
                paramsIniciales.putString("userName", firebaseUser.getDisplayName());
                paramsIniciales.putInt("puntos", points);
                paramsIniciales.putString("image", String.valueOf(firebaseUser.getPhotoUrl()));
                paramsIniciales.putString("modoJuego", "Draft");

                if (points > 0) {
                    firebaseMethods.createFbPuntuacionFS(paramsIniciales);

                }
                if (points > record) {
                    record = points;
                    message = getString(R.string.new_record) + "\n" + getString(R.string.puntuacion) + points;
                } else {
                    message = getString(R.string.puntuacion) + points + "\n" + getString(R.string.record) + record;
                }
            } else {
                message = getString(R.string.puntuacion) + points;
            }


            myCountDownTimer.cancel();
            showFinishedDialog(this, message);
        } else {
            myCountDownTimer.cancel();

            showFinishedDialog(this, "Internet connection lost");
        }


    }

    private void continueGame() {

        selectDraftPicks();

    }

    private void recogerDatos() {

        String url_imageTeam1 = generateImageUrl.checkTeamImage(draftPick1.getTEAM_ABBREVIATION());
        String url_imageTeam2 = generateImageUrl.checkTeamImage(draftPick2.getTEAM_ABBREVIATION());

        String url_imageCollege1 = generateImageUrl.checkCollegeImage(draftPick1.getORGANIZATION());
        String url_imageCollege2 = generateImageUrl.checkCollegeImage(draftPick2.getORGANIZATION());


        String url_imagen1 = generateImageUrl.checkPlayerImage(draftPick1.getPERSON_ID());
        String url_imagen2 = generateImageUrl.checkPlayerImage(draftPick2.getPERSON_ID());


        //si es alguno de los que no tenemos url de la imagen, que la meta a capon
        switch (draftPick1.getPERSON_ID()) {
            case 1122:
//                Glide.with(this).load(R.drawable.img_1122).into(ivP1);

                Glide.with(this).load(R.drawable.img_1122).error(R.drawable.person).into(ivP1);
                break;
            case 304:
//                Glide.with(this).load(R.drawable.img_304).into(ivP1);

                Glide.with(this).load(R.drawable.img_304).error(R.drawable.person).into(ivP1);
                break;
            case 600015:
//                Glide.with(this).load(R.drawable.img_600015).into(ivP1);

                Glide.with(this).load(R.drawable.img_600015).error(R.drawable.person).into(ivP1);
                break;
            case 714:
//                Glide.with(this).load(R.drawable.img_714).into(ivP1);

                Glide.with(this).load(R.drawable.img_714).error(R.drawable.person).into(ivP1);
                break;
            case 1763:
//                Glide.with(this).load(R.drawable.img_1763).into(ivP1);

                Glide.with(this).load(R.drawable.img_1763).error(R.drawable.person).into(ivP1);
                break;
            case 764:
//                Glide.with(this).load(R.drawable.img_764).into(ivP1);

                Glide.with(this).load(R.drawable.img_764).error(R.drawable.person).into(ivP1);
                break;
            case 2221:
//                Glide.with(this).load(R.drawable.img_2221).into(ivP1);

                Glide.with(this).load(R.drawable.img_2221).error(R.drawable.person).into(ivP1);
                break;


            default:
                Glide.with(this).load(url_imagen1).error(R.drawable.person).transition(DrawableTransitionOptions.withCrossFade()).into(ivP1);
//                Glide.with(this).load(url_imagen1).error(R.drawable.person)
//                        //.memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
//                        //.networkPolicy(NetworkPolicy.NO_CACHE, NetworkPolicy.NO_STORE)
//                        .into(ivP1);


        }
        Glide.with(this).load(url_imageTeam1).transition(DrawableTransitionOptions.withCrossFade()).into(ivT1);

//        Glide.with(this).load(url_imageTeam1)
//                //.memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
//                //.networkPolicy(NetworkPolicy.NO_CACHE, NetworkPolicy.NO_STORE)
//                .into(ivT1);

//        Glide.with(this).load(url_imageCollege1)
//                .into(ivC1);
        Glide.with(this).load(url_imageCollege1).transition(DrawableTransitionOptions.withCrossFade()).into(ivC1);



        txtNameP1.setText(draftPick1.getPLAYER_NAME());
        txtNameP2.setText(draftPick2.getPLAYER_NAME());


        //si es alguno de los que no tenemos url de la imagen, que la meta a capon
        switch (draftPick2.getPERSON_ID()) {
            case 1122:
//                Glide.with(this).load(R.drawable.img_1122).into(ivP2);

                Glide.with(this).load(R.drawable.img_1122).error(R.drawable.person).into(ivP2);
                break;
            case 304:
//                Glide.with(this).load(R.drawable.img_304).into(ivP2);

                Glide.with(this).load(R.drawable.img_304).error(R.drawable.person).into(ivP2);
                break;
            case 600015:
//                Glide.with(this).load(R.drawable.img_600015).into(ivP2);

                Glide.with(this).load(R.drawable.img_600015).error(R.drawable.person).into(ivP2);
                break;
            case 714:
//                Glide.with(this).load(R.drawable.img_714).into(ivP2);

                Glide.with(this).load(R.drawable.img_714).error(R.drawable.person).into(ivP2);
                break;
            case 1763:
//                Glide.with(this).load(R.drawable.img_1763).into(ivP2);

                Glide.with(this).load(R.drawable.img_1763).error(R.drawable.person).into(ivP2);
                break;
            case 764:
//                Glide.with(this).load(R.drawable.img_764).into(ivP2);

                Glide.with(this).load(R.drawable.img_764).error(R.drawable.person).into(ivP2);
                break;
            case 2221:
//                Glide.with(this).load(R.drawable.img_2221).into(ivP2);

                Glide.with(this).load(R.drawable.img_2221).error(R.drawable.person).into(ivP2);
                break;


            default:
                Glide.with(this).load(url_imagen2).error(R.drawable.person).transition(DrawableTransitionOptions.withCrossFade()).into(ivP2);


//                Glide.with(this).load(url_imagen2).error(R.drawable.person)
//                        //.memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
//                        //.networkPolicy(NetworkPolicy.NO_CACHE, NetworkPolicy.NO_STORE)
//                        .into(ivP2);


        }
        Glide.with(this).load(url_imageTeam2).transition(DrawableTransitionOptions.withCrossFade()).into(ivT2);

//        Glide.with(this).load(url_imageTeam1)
//                //.memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
//                //.networkPolicy(NetworkPolicy.NO_CACHE, NetworkPolicy.NO_STORE)
//                .into(ivT1);

//        Glide.with(this).load(url_imageCollege1)
//                .into(ivC1);
        Glide.with(this).load(url_imageCollege2).transition(DrawableTransitionOptions.withCrossFade()).into(ivC2);
//        Glide.with(this).load(url_imageTeam2)
//                //.memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
//                //.networkPolicy(NetworkPolicy.NO_CACHE, NetworkPolicy.NO_STORE)
//                .into(ivT2);
//
//        Glide.with(this).load(url_imageCollege2)
//                .into(ivC2);

        txtPoints.setText(String.valueOf(points));


        calculateValues();

    }


    private void calculateValues() {

        //TODO: si es MISC dar mas segundos

        valueP1 = draftPick1.getOVERALL_PICK();
        valueP2 = draftPick2.getOVERALL_PICK();

        if (myCountDownTimer != null) {
            myCountDownTimer.cancel();
        }
        if(crono){
            myCountDownTimer.start();
        }
    }

    @Override
    public void onClick(View v) {

        //si clicamos cualquiera se cancela el contador
        myCountDownTimer.cancel();

        //if (checkInternetConnection() == true) {
        switch (v.getId()) {
            case R.id.linJ2Draft:
                if (valueP2 <= valueP1) {
                    acierto();
                } else {
                    fallo();
                }

                break;

            case R.id.linJ1Draft:
                if (valueP2 >= valueP1) {
                    acierto();
                } else {
                    fallo();
                }
                break;
        }
//        } else {
//            Toast.makeText(this, R.string.sin_conexion, Toast.LENGTH_SHORT).show();
//            finishGame();
//
//        }


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

        deletePlayers();

        if (misc) {
            mezclar();
        } else {
            continueGame();
        }
    }

    private void deletePlayers() {

        draftPick1 = null;
        draftPick2 = null;


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

        deletePlayers();

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
    public void successListDrafts(ArrayList<DraftPick> draftPicks) {

        draftPicksGlobal = draftPicks;

        if (draftPick1 == null) {
            selectDraftPicks();

        } else {
            selectDraftPick2();
        }

    }

    @Override
    public void failureListDrafts(String message) {

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
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                }
                vidas = 3;
                points = 0;
                contadorAciertos = 0;
                comprobarVidas();
                selectDraftPicks();
            }
        });
        builder.setNegativeButton(R.string.back_to_menu, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


//                Intent menu = new Intent(GameActivity.this, NavigationDrawerActivity.class);
//                GameActivity.this.startActivity(menu);
//                GameActivity.this.finish();
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                }
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