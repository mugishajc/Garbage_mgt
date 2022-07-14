package com.example.garbagemanagement.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TabHost;

import com.example.garbagemanagement.Adapters.ViewPagerAdapter;
import com.example.garbagemanagement.Fragments.MainFragment;
import com.example.garbagemanagement.Fragments.PersonalFragment;
import com.example.garbagemanagement.Fragments.SettingsFragment;
import com.example.garbagemanagement.R;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.List;
import java.util.Vector;

public class UserActivity extends AppCompatActivity implements TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener {

    private TabHost tabHost;
    private ViewPager viewPager;
    private ViewPagerAdapter myViewPagerAdapter;
    private int i = 0;
    private Bundle bundle;
    private FirebaseAnalytics mFirebaseAnalytics;

    // fake content for tabhost
    class FakeContent implements TabHost.TabContentFactory {
        private final Context mContext;

        public FakeContent(Context context) {
            mContext = context;
        }

        @Override
        public View createTabContent(String tag) {
            View v = new View(mContext);
            v.setMinimumHeight(0);
            v.setMinimumWidth(0);
            return v;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        i++;

        // init tabhost
        this.initializeTabHost(savedInstanceState);

        // init ViewPager
        this.initializeViewPager();

        bundle = getIntent().getExtras();
        if(bundle != null) {
            int tab = bundle.getInt("ReturnTab");
            tabHost.setCurrentTab(tab);
        }
    }

    private void initializeViewPager() {
        List<Fragment> fragments = new Vector<Fragment>();

        bundle = getIntent().getExtras();
        String uid = null;
        if (bundle != null) {
            uid = bundle.getString("UID");
        }

        Bundle info = new Bundle();
        info.putString("UID",uid);
        PersonalFragment personalFragment = new PersonalFragment();
        personalFragment.setArguments(info);

        MainFragment mainFragment = new MainFragment();
        mainFragment.setArguments(info);

//        ComplainFragment complainFragment = new ComplainFragment();
//        complainFragment.setArguments(info);

        SettingsFragment settingsFrgament = new SettingsFragment();
        settingsFrgament.setArguments(info);

        fragments.add(personalFragment);
        fragments.add(mainFragment);
//        fragments.add(complainFragment);
        fragments.add(settingsFrgament);

        this.myViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragments);
        this.viewPager = (ViewPager) super.findViewById(R.id.viewPager);
        this.viewPager.setAdapter(this.myViewPagerAdapter);
        this.viewPager.setOnPageChangeListener(this);
        onRestart();

    }

    private void initializeTabHost(Bundle args) {

        tabHost = (TabHost) findViewById(android.R.id.tabhost);
        tabHost.setup();


        TabHost.TabSpec tabPersonal = tabHost.newTabSpec("I");
        tabPersonal.setIndicator("",getResources().getDrawable(R.drawable.icon_personal_selector));
        tabPersonal.setContent(new FakeContent(UserActivity.this));
        tabHost.addTab(tabPersonal);

        TabHost.TabSpec mainPersonal = tabHost.newTabSpec("J");
        mainPersonal.setIndicator("",getResources().getDrawable(R.drawable.icon_complain_24));
        mainPersonal.setContent(new FakeContent(UserActivity.this));
        tabHost.addTab(mainPersonal);

//        TabHost.TabSpec complainPersonal = tabHost.newTabSpec("C");
//        complainPersonal.setIndicator("",getResources().getDrawable(R.drawable.icon_complain_24));
//        complainPersonal.setContent(new FakeContent(UserActivity.this));
//        tabHost.addTab(complainPersonal);


        TabHost.TabSpec settingsPersonal = tabHost.newTabSpec("S");
        settingsPersonal.setIndicator("",getResources().getDrawable(R.drawable.icon_settings_selector));
        settingsPersonal.setContent(new FakeContent(UserActivity.this));
        tabHost.addTab(settingsPersonal);

        tabHost.setOnTabChangedListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        this.tabHost.setCurrentTab(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onTabChanged(String tabId) {
        int pos = this.tabHost.getCurrentTab();
        this.viewPager.setCurrentItem(pos);
    }
}