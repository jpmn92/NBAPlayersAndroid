package com.nbaplayersandroid;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.nbaplayersandroid.fragments.FragmentoAboutUs;
import com.nbaplayersandroid.fragments.FragmentoHelp;
import com.nbaplayersandroid.fragments.FragmentoMenu;
import com.nbaplayersandroid.fragments.FragmentoSettings;
import com.nbaplayersandroid.menu.Menu;
import com.nbaplayersandroid.tools.GenerateImageUrl;
import com.nbaplayersandroid.tools.SessionManagement;
import com.squareup.picasso.Picasso;

public class NavigationDrawerActivity extends AppCompatActivity {

    /**
     * Instancia del drawer
     */
    protected DrawerLayout mDrawer;

    /**
     * Titulo inicial del drawer
     */
    private String drawerTitle;
    private NavigationView navigationView;
    private TextView emailHeader;
    private TextView nameHeader;
    private ImageView imageHeader;
    private SessionManagement sessionManagement;
    private String userName;


    @Override
    protected void onStart() {
        super.onStart();
        checkSession();
    }

    private void checkSession() {

        sessionManagement = new SessionManagement(this);
        int userID = sessionManagement.getSession();

        if (userID != -1) {
            userName = sessionManagement.getSessionUserName();
            nameHeader.setText(userName);
            emailHeader.setText(userName);

        } else {

            //No logueados


        }
    }

    @Override
    protected void onResume() {
        super.onResume();


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


        GenerateImageUrl generateImageUrl = new GenerateImageUrl();
        Picasso.with(this).load(generateImageUrl.getRandomAvatar()).into(imageHeader);

        if (navigationView != null) {
            // Añadir caracteristicas
            setupDrawerContent(navigationView);

        }
        drawerTitle = getResources().getString(R.string.home_item);
        if (savedInstanceState == null) {
            // Seleccionar item
            selectItem(navigationView.getMenu().getItem(0));


        }



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

            case R.id.nav_settings:

                fragmentoGenerico = FragmentoSettings.newInstance(null);
                break;

            case R.id.nav_help:

                fragmentoGenerico = FragmentoHelp.newInstance(null);
                break;
            case R.id.nav_about_us:

                fragmentoGenerico = FragmentoAboutUs.newInstance(null);
                break;





            //fragmentoGenerico = FragmentoEntradas.newInstance(null);
//                Intent intent = new Intent(this, PaypalPayment.class);
////                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//
//                startActivity(intent);
//                fragmentoGenerico = new DonationFragment();


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
//            ab.setTitle("NBA STATS QUIZ");


        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
                .commit();
    }
}
