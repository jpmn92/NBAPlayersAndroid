package com.nbastatsquiz.fragments.help;

import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import com.nbastatsquiz.R;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * Fragmento que contiene otros fragmentos anidados para representar las categorías
 * de peliculas
 */
public class FragmentoTabsAyuda extends Fragment {
    private AppBarLayout appBarLayout;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private static FragmentoTabsAyuda fragmentoTabsAyuda;

    public FragmentoTabsAyuda() {
    }

    public static FragmentoTabsAyuda newInstance(Bundle datos) {
        if (fragmentoTabsAyuda == null) {
            fragmentoTabsAyuda =
                    new FragmentoTabsAyuda();
        }

        if (datos != null) {
            fragmentoTabsAyuda.setArguments(datos);
        }
        return fragmentoTabsAyuda;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragmento_paginado_ayuda, container, false);

        initComponents(view);

        if (savedInstanceState == null) {
            insertarTabs(container);

            // Setear adaptador al viewpager.

            poblarViewPager(viewPager);

            tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#FFFFFF"));

            tabLayout.setupWithViewPager(viewPager);
        }

        return view;
    }

    private void initComponents(View view) {
        viewPager = (ViewPager) view.findViewById(R.id.pager);
    }

    private void insertarTabs(ViewGroup container) {
        View padre = (View) container.getParent();
        appBarLayout = (AppBarLayout) padre.findViewById(R.id.appbar);

        tabLayout = new TabLayout(getActivity());
        tabLayout.setTabTextColors(Color.parseColor("#FFFFFF"), Color.parseColor("#FFFFFF"));
        appBarLayout.addView(tabLayout);
    }

    private void poblarViewPager(ViewPager viewPager) {

        AdaptadorSecciones adapter = new AdaptadorSecciones(getChildFragmentManager());

        adapter.addFragment(FragmentoHelpReglas.newInstance(null), getString(R.string.tab_reglas));
        adapter.addFragment(FragmentoHelpGlosario.newInstance(null), getString(R.string.tab_glosario));
        adapter.addFragment(FragmentoHelpAboutUs.newInstance(null), getString(R.string.tab_aboutUs));


//        adapter.addFragment(FragmentoPeliculas.nuevaInstancia(2), "Aventuras");
//        adapter.addFragment(FragmentoPeliculas.nuevaInstancia(3), "Belico");
//        adapter.addFragment(FragmentoPeliculas.nuevaInstancia(4), "Comedia");
//        adapter.addFragment(FragmentoPeliculas.nuevaInstancia(5), "Drama");
//        adapter.addFragment(FragmentoPeliculas.nuevaInstancia(6), "Ficcion");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_categorias, menu);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        appBarLayout.removeView(tabLayout);
    }

    /**
     * Un {@link FragmentStatePagerAdapter} que gestiona las secciones, fragmentos y
     * títulos de las pestañas
     */
    public class AdaptadorSecciones extends FragmentPagerAdapter {
        private final List<Fragment> fragmentos = new ArrayList<>();
        private final List<String> titulosFragmentos = new ArrayList<>();

        public AdaptadorSecciones(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentos.get(position);
        }

        @Override
        public int getCount() {
            return fragmentos.size();
        }

        public void addFragment(Fragment fragment, String title) {
            fragmentos.add(fragment);
            titulosFragmentos.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titulosFragmentos.get(position);
        }
    }


}
