package com.nbaplayersandroid;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nbaplayersandroid.beans.LeagueLeader;

import com.nbaplayersandroid.lst_league_leaders.LstLeagueLeaderContract;
import com.nbaplayersandroid.lst_league_leaders.LstLeagueLeaderPresenter;
import com.nbaplayersandroid.tools.ColorApp;
import com.nbaplayersandroid.tools.FirebaseMethods;
import com.nbaplayersandroid.tools.SessionManagement;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainActivity extends Activity implements View.OnClickListener, LstLeagueLeaderContract.View {

    int points, record, vidas;
    boolean recordConseguido;

    TextView txtP1, txtP2, txtPoints, txtPregunta, txtNameP1, txtNameP2;
    ImageView ivP1, ivP2, ivT1, ivT2, ivVidas;
    LinearLayout linJ1, linJ2, linFront, linLoad;
    RelativeLayout relCircle;
    MediaPlayer mediaPlayer;

    LstLeagueLeaderPresenter lstLeagueLeaderPresenter;
    ArrayList<LeagueLeader> leagueLeadersGlobal;
    LeagueLeader leagueLeader1, leagueLeader2;

    Bundle params, paramsIniciales;
    FirebaseMethods firebaseMethods;

    Resources res;

    float valueP1, valueP2;
    boolean gameStarted, misc, miscStats, miscSeason;
    String season, seasonType, statCategory, perMode, activeFlag, seasonText, statCategoryText, username;
    SessionManagement sessionManagement;

    @Override
    protected void onStart() {
        super.onStart();
        // checkSession();
    }

    private void checkSession() {

        sessionManagement = new SessionManagement(this);
        int userID = sessionManagement.getSession();

        if (userID != -1) {
            //Logueado
        } else {

            //No logueados

            //le pedimos username y despues guardamos la sesion

            //METODO PARA DIALOGO
//            new MaterialAlertDialogBuilder(this, R.style.CustomMaterialDialog)
//                    .setTitle("Introduce nombre usuario")
//                    .setMessage("Lorem ipsum dolor ....")
//                    .setPositiveButton("Ok", /* listener = */ null)
//                    .setNegativeButton("Cancel", /* listener = */ null)
//                    .show();

            username = "jp"; //o el quue se meta por el dialog

            sessionManagement.saveSession(username);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        vidas = 3;
        linFront = findViewById(R.id.linFront);
        points = 0;
        params = this.getIntent().getExtras();
        paramsIniciales = (Bundle) params.clone();
        username = params.getString("userName");
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

        gameStarted = false;

        initComponents();


        lstLeagueLeaderPresenter = new LstLeagueLeaderPresenter(this);
        firebaseMethods = new FirebaseMethods(paramsIniciales);

        buscarRecord();

        leagueLeadersGlobal = new ArrayList<>();

        if (misc) {
            mezclar();
        } else {
            lstLeagueLeaderPresenter.getLeagueLeaders(params);
        }
        // startGame();

    }

    private void buscarRecord() {
        record =  firebaseMethods.getRecord(username);

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
        //txtP1.setTextSize(50);
        //txtP1.setVisibility(View.GONE);
        txtP2 = findViewById(R.id.txtP2);
        //txtP2.setTextSize(50);
        txtP2.setTextColor(Color.RED);
        txtNameP1 = findViewById(R.id.txtNameP1);
        txtNameP2 = findViewById(R.id.txtNameP2);
        //txtP2.setVisibility(View.GONE);
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
        linFront.setOnClickListener(this);
        linLoad = findViewById(R.id.linLoad);
        linLoad.setOnClickListener(this);

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

        linFront.setVisibility(View.GONE);
        relCircle = findViewById(R.id.relCircle);

        txtPregunta.setText(statCategory + " " + season);


    }


    private void selectPlayers() {
        // record = 0;
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

//        random = (int) (Math.random() * leagueLeadersGlobal.size());
//        leagueLeader2 = leagueLeadersGlobal.get(random);
    }

    private void iluminar(String color) {
        txtP1.setText(String.valueOf(valueP1));
        txtP2.setText(String.valueOf(valueP2));
        linFront.setBackgroundColor(Color.parseColor(color));
        linFront.setVisibility(View.VISIBLE);
        linFront.postDelayed(new Runnable() {
            public void run() {
                linFront.setVisibility(View.GONE);
            }
        }, 1000);

    }

    private void fallo() {
        mediaPlayer = MediaPlayer.create(this, R.raw.error);
        mediaPlayer.start();
        iluminar(ColorApp.RED);
        vidas--;
        switch (vidas){
            case 1:
                // ivVidas.setIma
                // poner en el ivVidas la imagen vidas1
                break;
            case 2:

                break;
            case 3:

                break;
        }
        System.out.println("");
        if(vidas <= 0){
            finishGame();
        }



        //continueGame();


    }

    private void finishGame() {
        //si es mayor de 4 guarda en la bbdd
        if (points > record) {
            paramsIniciales.putInt("puntos", points);
            paramsIniciales.putString("username", username);
            firebaseMethods.createFbPuntuacion(paramsIniciales);
            Toast.makeText(this, "Nuevo record alcanzado", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "Perdiste" + username, Toast.LENGTH_SHORT).show();
        }
        vidas = 3;
        points = 0;
        //txtPoints.setText(String.valueOf(points));

    }

    private void continueGame() {

        selectPlayers();

    }

    private void recogerDatos() {

        String url_imageTeam1 = checkTeamImage(leagueLeader1.getTEAM());
        String url_imageTeam2 = checkTeamImage(leagueLeader2.getTEAM());


        String url_imagen1 = checkPlayerImage(leagueLeader1.getPLAYER_ID().intValue());
        String url_imagen2 = checkPlayerImage(leagueLeader2.getPLAYER_ID().intValue());


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
                //Picasso.with(this).load(url_imagen1).transform(new BlurTransformation(this)).error(R.drawable.person).into(ivP1); para difuminar imagen


        }

        Picasso.with(this).load(url_imageTeam1).into(ivT1);

        txtNameP1.setText(leagueLeader1.getPLAYER());
        txtNameP2.setText(leagueLeader2.getPLAYER());


        //Para cambiar el fondo, se podrá mejorar
//        Picasso.with(this).load(fbTeamList.get(team1).getUrlBackground()).into(new Target() {
//            @Override
//            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
//                linJ1.setBackground(new BitmapDrawable(bitmap));
//            }
//
//            @Override
//            public void onBitmapFailed(Drawable errorDrawable) {
//
//            }
//
//            @Override
//            public void onPrepareLoad(Drawable placeHolderDrawable) {
//
//            }
//        });
//        Picasso.with(this).load(fbTeamList.get(team2).getUrlBackground()).into(new Target() {
//            @Override
//            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
//                linJ2.setBackground(new BitmapDrawable(bitmap));
//            }
//
//            @Override
//            public void onBitmapFailed(Drawable errorDrawable) {
//
//            }
//
//            @Override
//            public void onPrepareLoad(Drawable placeHolderDrawable) {
//
//            }
//        });

        //si es alguno de los que no tenemos url de la imagen, que la meta a capon
        switch (leagueLeader2.getPLAYER_ID().intValue()) {
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
                Picasso.with(this).load(url_imagen2).error(R.drawable.person).into(ivP2);


        }
        Picasso.with(this).load(url_imageTeam2).into(ivT2);

        txtPoints.setText(String.valueOf(points));
        calculateValues();
    }

    private String checkPlayerImage(int idJugador) {


        String urlImage = "";

        switch (idJugador) {


            //steve nash
            case 959:
                urlImage = "https://i.dlpng.com/static/png/219514_preview.png";
                break;

            //derek fisher
            case 965:
                urlImage = "https://a.espncdn.com/combiner/i?img=/i/headshots/nba/players/full/246.png";
                break;

            //shane battier
            case 2203:
                urlImage = "https://a.espncdn.com/combiner/i?img=/i/headshots/nba/players/full/976.png";
                break;

            //michael redd
            case 2072:
                urlImage = "https://a.espncdn.com/i/headshots/nba/players/full/692.png";
                break;

            //jason kidd
            case 467:
                urlImage = "https://a.espncdn.com/i/headshots/nba/players/full/429.png";
                break;

            //Ray allen
            case 951:
                urlImage = "https://a.espncdn.com/i/headshots/nba/players/full/9.png";
                break;

            //jamison
            case 1712:
                urlImage = "https://a.espncdn.com/combiner/i?img=/i/headshots/nba/players/full/385.png";
                break;

            //jason stackhouse
            case 711:
                urlImage = "https://a.espncdn.com/combiner/i?img=/i/headshots/nba/players/full/802.png";
                break;

            //jermaine
            case 979:
                urlImage = "https://a.espncdn.com/combiner/i?img=/i/headshots/nba/players/full/615.png";
                break;

            //rashard lewis
            case 1740:
                urlImage = "https://a.espncdn.com/combiner/i?img=/i/headshots/nba/players/full/469.png";
                break;
            //juwan howard
            case 436:
                urlImage = "https://a.espncdn.com/combiner/i?img=/i/headshots/nba/players/full/351.png";
                break;

            //jason richardson
            case 2202:
                urlImage = "https://a.espncdn.com/combiner/i?img=/i/headshots/nba/players/full/1018.png";
                break;

            //tony parker
            case 2225:
                urlImage = "https://nba-players.herokuapp.com/players/Parker/Tony";
                break;

            //chauncey billups
            case 1497:
                urlImage = "https://a.espncdn.com/combiner/i?img=/i/headshots/nba/players/full/63.png";
                break;

            //boozer
            case 2430:
                urlImage = "https://a.espncdn.com/combiner/i?img=/i/headshots/nba/players/full/1703.png";
                break;

            //al harrington
            case 1733:
                urlImage = "https://a.espncdn.com/combiner/i?img=/i/headshots/nba/players/full/308.png";
                break;

            //maggete
            case 1894:
                urlImage = "https://a.espncdn.com/i/headshots/nba/players/full/497.png";
                break;
            //odom
            case 1885:
                urlImage = "https://a.espncdn.com/combiner/i?img=/i/headshots/nba/players/full/617.png";
                break;

            //gerald wallace
            case 2222:
                urlImage = "https://a.espncdn.com/combiner/i?img=/i/headshots/nba/players/full/1026.png";
                break;

            //brandon roy
            case 200750:
                urlImage = "https://a.espncdn.com/combiner/i?img=/i/headshots/nba/players/full/3027.png";
                break;

            //nate robinson
            case 101126:
                urlImage = "https://a.espncdn.com/combiner/i?img=/i/headshots/nba/players/full/2782.png";
                break;
            //chris duhon
            case 2768:
                urlImage = "https://a.espncdn.com/combiner/i?img=/i/headshots/nba/players/full/2377.png";
                break;

            //rip hamilton
            case 1888:
                urlImage = "https://a.espncdn.com/combiner/i?img=/i/headshots/nba/players/full/294.png";
                break;

            //shawn marion
            case 1890:
                urlImage = "https://a.espncdn.com/combiner/i?img=/i/headshots/nba/players/full/510.png";
                break;

            //kirilenko
            case 1905:
                urlImage = "https://a.espncdn.com/combiner/i?img=/i/headshots/nba/players/full/434.png";
                break;

            //andrew bynum
            case 101115:
                urlImage = "https://a.espncdn.com/combiner/i?img=/i/headshots/nba/players/full/2748.png";
                break;

            //stephen jackson
            case 1536:
                urlImage = "https://a.espncdn.com/i/headshots/nba/players/full/378.png";
                break;

            //francisco garcia
            case 101128:
                urlImage = "https://3.bp.blogspot.com/-bopDVYYRH2A/Uz9ibGtm3xI/AAAAAAAAXs0/ZkXJA4oq0mE/s1600/i.png";
                break;

            //ridnour
            case 2557:
                urlImage = "https://a.espncdn.com/i/headshots/nba/players/full/1985.png";
                break;


            default:
                urlImage = "https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/latest/260x190/" + idJugador + ".png";

        }


        return urlImage;
    }

    private String checkTeamImage(String equipo) {

        String urlImage = "";

        switch (equipo) {

            case "NBA":
                urlImage = "https://www.goodvinilos.com/6444/pegatina-logo-nba.jpg";
                break;

            case "NOH":
                urlImage = "https://i.pinimg.com/originals/77/6a/3a/776a3a8005d7c176db429f7ce54367f9.png";
                break;
            case "NOK":
                urlImage = "https://i.pinimg.com/originals/77/6a/3a/776a3a8005d7c176db429f7ce54367f9.png";
                break;

            case "SEA":
                urlImage = "https://upload.wikimedia.org/wikipedia/en/thumb/a/a4/Seattle_SuperSonics_logo.svg/1200px-Seattle_SuperSonics_logo.svg.png";
                break;

            case "VAN":
                urlImage = "https://upload.wikimedia.org/wikipedia/en/thumb/1/1e/Vancouver_Grizzlies_logo.svg/1200px-Vancouver_Grizzlies_logo.svg.png";
                break;

            case "CIN":
                urlImage = "https://worldsportlogos.com/wp-content/uploads/2019/07/Cincinnati-Royals-emblem-1972.png";
                break;

            case "NJN":
                urlImage = "https://vignette.wikia.nocookie.net/baloncesto/images/8/88/New_Jersey_Nets_logo.png";
                break;

            default:
                urlImage = "https://a.espncdn.com/i/teamlogos/nba/500/" + equipo + ".png";

        }


        return urlImage;
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
                valueP1 = leagueLeader1.getFG3_PCT().floatValue();
                valueP2 = leagueLeader2.getFG3_PCT().floatValue();
                break;

            case "FT_PCT":
                valueP1 = leagueLeader1.getFT_PCT().floatValue();
                valueP2 = leagueLeader2.getFT_PCT().floatValue();
                break;
        }
    }

    @Override
    public void onClick(View v) {

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
    }

    private void acierto() {
        mediaPlayer = MediaPlayer.create(this, R.raw.acierto);
        mediaPlayer.start();
        iluminar(ColorApp.GREEN);
        points++;
        if (misc) {
            mezclar();
        } else {

            continueGame();
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


    }
}
