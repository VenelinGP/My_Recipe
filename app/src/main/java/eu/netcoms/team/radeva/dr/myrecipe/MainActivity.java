package eu.netcoms.team.radeva.dr.myrecipe;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import eu.netcoms.team.radeva.dr.myrecipe.fragments.AddNewRecipieFragment;
import eu.netcoms.team.radeva.dr.myrecipe.fragments.MyRecipesFragment;
import eu.netcoms.team.radeva.dr.myrecipe.fragments.HomePageFragment;

public class MainActivity extends AppCompatActivity{
    private ViewPager viewPager;
    private FragmentPagerAdapter tabPagesAdapter;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        android.support.v7.app.ActionBar mActionBar = getSupportActionBar();
        mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        tabPagesAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0: return new HomePageFragment();
                    case 1: return new MyRecipesFragment();
                    case 2: return new AddNewRecipieFragment();
                    default: return null;
                }
            }
        };

        viewPager = (ViewPager)findViewById(R.id.vp);
        viewPager.setAdapter(tabPagesAdapter);

        viewPager.setOnPageChangeListener(
                new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                        getSupportActionBar().setSelectedNavigationItem(position);
                    }
                });

        android.support.v7.app.ActionBar.TabListener listener = new android.support.v7.app.ActionBar.TabListener() {
            @Override
            public void onTabSelected(android.support.v7.app.ActionBar.Tab tab, FragmentTransaction ft) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(android.support.v7.app.ActionBar.Tab tab, FragmentTransaction ft) {
            }

            @Override
            public void onTabReselected(android.support.v7.app.ActionBar.Tab tab, FragmentTransaction ft) {
            }
        };

        // Home
        android.support.v7.app.ActionBar.Tab home = mActionBar.newTab();
        home.setText("Home");
        home.setTabListener(listener);
        mActionBar.addTab(home);

        // AllRecipie
        android.support.v7.app.ActionBar.Tab allRecipie = mActionBar.newTab();
        allRecipie.setText(R.string.myRecipes);
        allRecipie.setTabListener(listener);
        mActionBar.addTab(allRecipie);

        //NewRecipie
        android.support.v7.app.ActionBar.Tab newRecipie = mActionBar.newTab();
        newRecipie.setText("Add new recipe");
        newRecipie.setTabListener(listener);
        mActionBar.addTab(newRecipie);
    }
}
