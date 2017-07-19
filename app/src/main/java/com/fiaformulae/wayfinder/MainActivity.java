package com.fiaformulae.wayfinder;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.fiaformulae.wayfinder.sidebar.faq.FaqFragment;
import com.fiaformulae.wayfinder.sidebar.home.HomeFragment;
import com.fiaformulae.wayfinder.sidebar.map.MapFragment;
import com.fiaformulae.wayfinder.sidebar.raceday.RaceDayFragment;
import com.fiaformulae.wayfinder.sidebar.teams.TeamsFragment;
import com.fiaformulae.wayfinder.sidebar.weather.WeatherFragment;

public class MainActivity extends AppCompatActivity {
  @BindView(R.id.drawer_layout) DrawerLayout drawerLayout;
  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.navigation_view) NavigationView navigationView;
  private ActionBarDrawerToggle drawerToggle;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);

    setSupportActionBar(toolbar);
    setNavigationListener();

    drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open,
        R.string.drawer_close) {
      public void onDrawerClosed(View view) {
        super.onDrawerClosed(view);
      }

      public void onDrawerOpened(View drawerView) {
        super.onDrawerOpened(drawerView);
      }
    };

    drawerLayout.addDrawerListener(drawerToggle);
    drawerToggle.syncState();
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.main, menu);
    return super.onCreateOptionsMenu(menu);
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_search:
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  @Override public void setTitle(CharSequence title) {
    toolbar.setTitle(title);
  }

  private void setNavigationListener() {
    navigationView.setNavigationItemSelectedListener(item -> {
      item.setChecked(!item.isChecked());
      drawerLayout.closeDrawers();
      Fragment fragment = null;
      switch (item.getItemId()) {
        case R.id.wayfinder:
          fragment = new MapFragment();
          setTitle(getString(R.string.wayfinder));
        case R.id.raceday_schedule:
          fragment = new RaceDayFragment();
          setTitle(getString(R.string.raceday_schedule));
        case R.id.race_teams:
          fragment = new TeamsFragment();
          setTitle(getString(R.string.race_teams));
        case R.id.weather:
          fragment = new WeatherFragment();
          setTitle(getString(R.string.weather));
        case R.id.faq:
          fragment = new FaqFragment();
          setTitle(getString(R.string.faq));
        default:
          fragment = new HomeFragment();
          setTitle(getString(R.string.home));
      }
      FragmentManager fragmentManager = getSupportFragmentManager();
      fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
      return true;
    });
  }
}
