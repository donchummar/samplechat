package com.chatapp.nod.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

import com.chatapp.nod.R;
import com.chatapp.nod.fragment.UserListFragment;


public class UserListing extends BaseActivity {

    private static final String TAG = UserListing.class.getName();

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private FrameLayout mContentFrame;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        setUpToolbar();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.nav_drawer);
        setUpNavDrawer();
        mContentFrame = (FrameLayout) findViewById(R.id.nav_contentframe);
        if (savedInstanceState == null)
            initUserListFragment();
    }

    private void initUserListFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        UserListFragment userListFragment = new UserListFragment();
        fragmentTransaction.add(R.id.nav_contentframe, userListFragment);
        fragmentTransaction.commit();

    }


    private void setUpNavDrawer() {
        if (mToolbar != null) {
            getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
            mToolbar.setNavigationIcon(R.drawable.ic_drawer);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDrawerLayout.openDrawer(GravityCompat.START);
                }
            });
        }
    }

    private void setUpToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
        }
    }

}
