package com.srteam.expensetracker.ui.help;

import android.os.Bundle;
import android.widget.RelativeLayout;

import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.srteam.expensetracker.R;
import com.srteam.expensetracker.isConfig.isAdsConfig;
import com.srteam.expensetracker.ui.BaseActivity;

public class HelpActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setToolbar(toolbar);

        /*banner ad*/

        RelativeLayout mAdView = findViewById(R.id.adView);
        isAdsConfig.showBanner(this,mAdView,false);
    }

}
