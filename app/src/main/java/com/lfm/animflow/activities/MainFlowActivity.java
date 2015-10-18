package com.lfm.animflow.activities;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.lfm.animflow.R;
import com.lfm.animflow.fragments.FlowContent2Fragment;
import com.lfm.animflow.fragments.FlowContentFragment;
import com.lfm.animflow.fragments.FlowItemsFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lucas FOULON-MONGAÏ, github.com/LucasFoulonMongai  on 17/10/15.
 */
public class MainFlowActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG_FLOW_ITEMS = "animflow://items";
    private static final String TAG_FLOW_CONTENT_1 = "animflow://content_1";
    private static final String TAG_FLOW_CONTENT_2 = "animflow://content_2";


    private Map<String, Class<?>> mapFragment = new HashMap<>();
    private DrawerLayout drawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_flow);
        initFragmentMap();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        goContent(TAG_FLOW_ITEMS);
    }

    public void initFragmentMap() {
        mapFragment.put(TAG_FLOW_ITEMS, FlowItemsFragment.class);
        mapFragment.put(TAG_FLOW_CONTENT_1, FlowContentFragment.class);
        mapFragment.put(TAG_FLOW_CONTENT_2, FlowContent2Fragment.class);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main_flow, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_items) {
            goContent(TAG_FLOW_ITEMS);
        } else if (id == R.id.nav_content) {
            goContent(TAG_FLOW_CONTENT_1);
        } else if (id == R.id.nav_content_2) {
            goContent(TAG_FLOW_CONTENT_2);
        }
        return true;
    }

    public void goContent(String pushLink) {
        drawer.closeDrawer(GravityCompat.START);

        if (pushLink.equals(TAG_FLOW_ITEMS)) {
            switchFragment(pushLink);
            return;
        }

        if (pushLink.equals(TAG_FLOW_CONTENT_1)) {
            switchFragment(pushLink);
            return;
        }

        if (pushLink.equals(TAG_FLOW_CONTENT_2)) {
            switchFragment(pushLink);
            return;
        }

    }

    private void switchFragment(String tag) {
        Fragment mContent = Fragment.instantiate(MainFlowActivity.this, mapFragment.get(tag).getName());

        getSupportFragmentManager()
                .beginTransaction()
                        //.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .replace(R.id.content_frame, mContent)
                .commitAllowingStateLoss();
    }
}
