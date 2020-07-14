package com.nbastatsquiz;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.nbastatsquiz.fragments.auth.FragmentoLogin;
import com.nbastatsquiz.fragments.menu.FragmentoMenu;
import com.nbastatsquiz.fragments.menu.FragmentoMenuDraft;
import com.nbastatsquiz.fragments.auth.FragmentoRegister;
import com.nbastatsquiz.fragments.FragmentoAccount;
import com.nbastatsquiz.fragments.help.FragmentoTabsAyuda;
import com.nbastatsquiz.pruebas.Main4Activity_pruebaCH;
import com.nbastatsquiz.tools.SessionManagement;
import com.squareup.picasso.Picasso;

public class NavigationDrawerActivity extends AppCompatActivity {

    /**
     * Instancia del drawer
     */
    protected DrawerLayout mDrawer;

    /**
     * Titulo inicial del drawer
     */
    private String drawerTitle, urlImage;
    private NavigationView navigationView;
    private TextView emailHeader;
    private TextView nameHeader;
    private ImageView imageHeader;
    private SessionManagement sessionManagement;
    private String userName, email;
    private long backPressedTime;
    Toast backToast;
    private Boolean logueado;


    @Override
    public void onBackPressed() {

        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast.cancel();
//            getSupportFragmentManager().executePendingTransactions();
//            loadFragment(FragmentoMenu.newInstance(null));

            this.finish();

//            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
//                getSupportFragmentManager().popBackStackImmediate();
//            } else {
//                super.onBackPressed();
//
//            }

            return;

        } else {


            loadFragment(FragmentoMenu.newInstance(null));
            backToast = Toast.makeText(this, R.string.press_back, Toast.LENGTH_SHORT);
            backToast.show();

        }
        backPressedTime = System.currentTimeMillis();


    }

    @Override
    protected void onStart() {
        super.onStart();

        checkInternetConnection();
        checkSession();
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
            Toast.makeText(this, R.string.sin_conexion, Toast.LENGTH_SHORT).show();
        }

        return connected;

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        checkSession();
    }

    private void checkSession() {
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();

        if (firebaseUser != null) {
            logueado = true;
            userName = firebaseUser.getDisplayName();
            email = firebaseUser.getEmail();

            nameHeader.setText(userName);
            emailHeader.setText(email);
            Glide.with(this).load(firebaseUser.getPhotoUrl()).into(imageHeader);

            showLoggedMenu();

        } else {

            showAnonymusMenu();
            logueado = false;

            //No logueados


        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkSession();


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);


        setToolbar(); // Setear Toolbar como action bar

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        View headerView = navigationView.getHeaderView(0);

        emailHeader = (TextView) headerView.findViewById(R.id.emailHeader);
        nameHeader = (TextView) headerView.findViewById(R.id.usernameHeader);
        imageHeader = (ImageView) headerView.findViewById(R.id.circle_image);

//        OLD GENERAMOS AVATAR RANDOM
//        GenerateImageUrl generateImageUrl = new GenerateImageUrl();
//        Picasso.with(this).load(generateImageUrl.getRandomAvatar()).into(imageHeader);

        if (navigationView != null) {
            // Añadir caracteristicas
            setupDrawerContent(navigationView);

        }
        drawerTitle = getResources().getString(R.string.home_item);
        if (savedInstanceState == null) {
            // Seleccionar item
            selectItem(navigationView.getMenu().getItem(0));


        }


        //si esta logueado lo manda al perfil, sino al login

        View.OnClickListener drawerListener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (logueado == true) {

                    FragmentoAccount fragmentoAccount = FragmentoAccount.newInstance(null);
                    loadFragment(fragmentoAccount);

                } else {
                    FragmentoLogin fragmentoLogin = FragmentoLogin.newInstance(null);
                    loadFragment(fragmentoLogin);
                }
            }
        };

        emailHeader.setOnClickListener(drawerListener);
        nameHeader.setOnClickListener(drawerListener);
        imageHeader.setOnClickListener(drawerListener);


    }


    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // Marcar item presionado
                        menuItem.setChecked(true);
                        // Crear nuevo fragmento
                        String title = menuItem.getTitle().toString();
                        selectItem(menuItem);
                        return true;
                    }
                }
        );
    }

    private void selectItem(MenuItem itemDrawer) {
        Fragment fragmentoGenerico = null;
        FragmentManager fragmentManager = getSupportFragmentManager();

        switch (itemDrawer.getItemId()) {
            case R.id.nav_home:
                fragmentoGenerico = FragmentoMenu.newInstance(null);
                break;

            case R.id.nav_draft:


                fragmentoGenerico = FragmentoMenuDraft.newInstance(null);
//                Intent draft = new Intent(this, DraftActivity.class);
//                startActivity(draft);

                break;

            case R.id.nav_highs:

                Intent ch = new Intent(this, Main4Activity_pruebaCH.class);
                startActivity(ch);

                break;


            case R.id.nav_myAccount:

                fragmentoGenerico = FragmentoAccount.newInstance(null);
                break;

            case R.id.nav_help:

//                fragmentoGenerico = FragmentoHelp.newInstance(null);
                fragmentoGenerico = FragmentoTabsAyuda.newInstance(null);

                break;
            case R.id.nav_about_us:
                try {
                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "bowlofricedev@gmail.com", null));
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "NBA Stats Season - ");
                    startActivity(Intent.createChooser(emailIntent, getString(R.string.enviar_mail)));
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(this, getText(R.string.contact_mail), Toast.LENGTH_LONG);
                }

                fragmentoGenerico = FragmentoMenu.newInstance(null);
                break;

            case R.id.nav_login:

                fragmentoGenerico = FragmentoLogin.newInstance(null);
                break;

            case R.id.nav_register:

                fragmentoGenerico = FragmentoRegister.newInstance(null);
                break;

            case R.id.nav_logout:

                logout();
                break;


        }
        if (fragmentoGenerico != null) {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.main_content, fragmentoGenerico)
                    .commit();
        }

        // Setear título actual
        setTitle(itemDrawer.getTitle());

        mDrawer.closeDrawers(); // Cerrar drawer

    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            // Poner ícono del drawer toggle
            ab.setHomeAsUpIndicator(R.drawable.ic_menu);
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setTitle("NBA STATS QUIZ"); //TODO: ÑAPA PARA EL TITULO


        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        checkSession();

        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void loadFragment(Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragmentoGenerico = fragment;
        fragmentManager
                .beginTransaction()
                .replace(R.id.main_content, fragmentoGenerico)
                .addToBackStack(null)
                .commit();
    }

    public void logout() {

        SessionManagement sessionManagement = new SessionManagement(this);
        sessionManagement.removeSession();

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();

        Intent intent = new Intent(this, NavigationDrawerActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();

    }

    public void showAnonymusMenu() {

        //TODO: REVISAR ACCESOS, AHORA NO SE PUEDE TOCAR LA CONFIG SI NO ESTAS LOGUEADO
        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.nav_login).setVisible(true);
        nav_Menu.findItem(R.id.nav_register).setVisible(true);
        nav_Menu.findItem(R.id.nav_help).setVisible(true);
        nav_Menu.findItem(R.id.nav_about_us).setVisible(true);
        nav_Menu.findItem(R.id.nav_home).setVisible(true);
        nav_Menu.findItem(R.id.nav_myAccount).setVisible(false);
        nav_Menu.findItem(R.id.nav_logout).setVisible(false);
        nav_Menu.findItem(R.id.nav_highs).setVisible(false);



    }

    public void showLoggedMenu() {
        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.nav_login).setVisible(false);
        nav_Menu.findItem(R.id.nav_register).setVisible(false);
        nav_Menu.findItem(R.id.nav_help).setVisible(true);
        nav_Menu.findItem(R.id.nav_about_us).setVisible(true);
        nav_Menu.findItem(R.id.nav_home).setVisible(true);
        nav_Menu.findItem(R.id.nav_myAccount).setVisible(true);
        nav_Menu.findItem(R.id.nav_logout).setVisible(true);
        nav_Menu.findItem(R.id.nav_highs).setVisible(false);

    }


}
