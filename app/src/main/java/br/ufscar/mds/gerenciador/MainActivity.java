package br.ufscar.mds.gerenciador;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;

import br.ufscar.mds.gerenciador.utils.SlidingTabLayout;

public class MainActivity extends AppCompatActivity implements SlidingTabLayout.TabColorizer {

    //TAG
    private static final String TAG = "Gerenciador MDS";
    // Objetos que manipulam as tabs
    FragmentsAdapter mAdapter;
    ViewPager mPager;
    SlidingTabLayout tabs;

    // Objetos que manipulam o menu lateral e a ActionBar
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Coloca o logo na ActionBar
        this.configurarActionBar();

        // Troca o icone de menu pela setinha
        this.criarEfeitoMenuDrawer();

        // Cria as tabs
        this.criarTabs();

        // Cria os botões flutuantes
        this.criarBotoesFlutuantes();

        // Cria os listeners para quando o usuário clicar nos botões laterais
        this.criarListenersBotoesMenuLateral();
    }

    public void criarListenersBotoesMenuLateral() {
        LinearLayout buttonGoogleAgenda = (LinearLayout) findViewById(R.id.buttonGoogleAgenda);
        buttonGoogleAgenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent googleLoginActivity = new Intent(MainActivity.this,CalendarConnectActivity.class);
                startActivity(googleLoginActivity);
            }
        });

        LinearLayout buttonSobre = (LinearLayout) findViewById(R.id.buttonSobre);
        buttonSobre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //About Dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(R.string.about_title)
                        .setMessage(R.string.about_message)
                        .setPositiveButton(R.string.ok,null)
                        .create()
                        .show();
            }
        });

        LinearLayout buttonAddSemester = (LinearLayout) findViewById(R.id.buttonAddSemester);
        buttonAddSemester.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addSemesterActivity = new Intent(MainActivity.this,AddSemesterActivity.class);
                startActivity(addSemesterActivity);
            }
        });

        LinearLayout buttonAddCourse = (LinearLayout) findViewById(R.id.buttonAddCourse);
        buttonAddCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addCourseActivity = new Intent(MainActivity.this,AddCourseActivity.class);
                startActivity(addCourseActivity);
            }
        });
    }

    public void criarBotoesFlutuantes() {
        FloatingActionButton fab_new = (FloatingActionButton) findViewById(R.id.fab_new);
        fab_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Nova atividade
                //TODO (1) Adicionar nova atividade
            }
        });

        FloatingActionButton fab_camera = (FloatingActionButton) findViewById(R.id.fab_camera);
        fab_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO (2) Abrir camera para tirar nova foto
            }
        });
    }

    public void configurarActionBar() {
        Drawable logo = new IconDrawable(getApplicationContext(), FontAwesomeIcons.fa_home)
                .colorRes(R.color.blue).sizeDp(24);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(logo);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("  " + "Gerenciador");
    }

    public void criarTabs() {
        mAdapter = new FragmentsAdapter(getSupportFragmentManager(), getApplicationContext());
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);

        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setCustomTabColorizer(this);
        tabs.setDistributeEvenly(true);
        tabs.setCustomTabView(R.layout.tab_title, 0);
        tabs.setViewPager(mPager);
    }

    public void criarEfeitoMenuDrawer() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                toolbar, R.string.openDrawer, R.string.closeDrawer) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        // End of toolbar functions

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    public int getIndicatorColor(int position) {
        return R.color.blue;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    public static class FragmentsAdapter extends FragmentPagerAdapter {

        private Context context;

        public FragmentsAdapter(FragmentManager fm, Context c) {
            super(fm);
            context = c;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new AtividadesFragment();
                case 1:
                    return new CursosFragment();
                default:
                    System.out.println("FragmentsAdapter position not found");
                    return null;
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Drawable image;

            switch (position) {
                case 0:
                    image = new IconDrawable(this.context, FontAwesomeIcons.fa_home)
                            .colorRes(R.color.blue).sizeDp(24);
                    break;
                case 1:
                    image = new IconDrawable(this.context, FontAwesomeIcons.fa_book)
                            .colorRes(R.color.blue).sizeDp(24);
                    break;
                default:
                    image = new IconDrawable(this.context, FontAwesomeIcons.fa_times)
                            .colorRes(R.color.blue).sizeDp(24);
                    break;
            }

            image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
            SpannableString sb = new SpannableString(" ");
            ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
            sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            return sb;
        }
    }
}
