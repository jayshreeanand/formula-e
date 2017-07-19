package com.fiaformulae.wayfinder;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
  @BindView(R.id.left_drawer) ViewGroup drawer;
  @BindView(R.id.drawer_list) ListView drawerList;
  private ActionBarDrawerToggle drawerToggle;
  private String[] sidebarTitles;
  private CharSequence title;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);

    title = getTitle();
    sidebarTitles = getResources().getStringArray(R.array.sidebar_array);

    drawerList.setAdapter(new ArrayAdapter<>(this, R.layout.item_drawer_list, sidebarTitles));
    drawerList.setOnItemClickListener(new DrawerItemClickListener());

    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setHomeButtonEnabled(true);

    drawerToggle =
        new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close) {
          public void onDrawerClosed(View view) {
            super.onDrawerClosed(view);

            getSupportActionBar().setTitle(title);
            invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
          }

          public void onDrawerOpened(View drawerView) {
            super.onDrawerOpened(drawerView);
            getSupportActionBar().setTitle("");
            invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
          }
        };

    drawerLayout.addDrawerListener(drawerToggle);
    if (savedInstanceState == null) {
      selectItem(0);
    }
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.main, menu);
    return super.onCreateOptionsMenu(menu);
  }

  @Override public boolean onPrepareOptionsMenu(Menu menu) {
    boolean drawerOpen = drawerLayout.isDrawerOpen(drawer);
    menu.findItem(R.id.action_search).setVisible(!drawerOpen);
    return super.onPrepareOptionsMenu(menu);
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    if (drawerToggle.onOptionsItemSelected(item)) {
      return true;
    }
    switch (item.getItemId()) {
      case R.id.action_search:
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  @Override protected void onPostCreate(Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);
    drawerToggle.syncState();
  }

  @Override public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
    drawerToggle.onConfigurationChanged(newConfig);
  }

  @Override public void setTitle(CharSequence title) {
    this.title = title;
    getSupportActionBar().setTitle(title);
  }

  private void selectItem(int position) {
    Fragment fragment = null;
    if (position == 0) {
      fragment = new HomeFragment();
    } else if (position == 1) {
      fragment = new MapFragment();
    } else if (position == 2) {
      fragment = new RaceDayFragment();
    } else if (position == 3) {
      fragment = new TeamsFragment();
    } else if (position == 4) {
      fragment = new WeatherFragment();
    } else if (position == 5) {
      fragment = new FaqFragment();
    }
    FragmentManager fragmentManager = getSupportFragmentManager();
    fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

    // Highlight the selected item, update the title, and close the drawer
    drawerList.setItemChecked(position, true);
    setTitle(sidebarTitles[position]);
    drawerLayout.closeDrawer(drawer);
  }

  private class DrawerItemClickListener implements ListView.OnItemClickListener {
    @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
      selectItem(position);
    }
  }
}
