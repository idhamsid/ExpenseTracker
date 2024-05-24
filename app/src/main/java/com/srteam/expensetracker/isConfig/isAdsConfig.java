package com.srteam.expensetracker.isConfig;


import static com.srteam.expensetracker.isConfig.Config.BANNER;
import static com.srteam.expensetracker.isConfig.Config.COUNTER;
import static com.srteam.expensetracker.isConfig.Config.ENABLE_ADS;
import static com.srteam.expensetracker.isConfig.Config.INTER;
import static com.srteam.expensetracker.isConfig.Config.REWARD_VIDEO;

import android.app.Activity;
import android.app.Dialog;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.srteam.expensetracker.R;

public class isAdsConfig {
    public static void initAds(Activity context) {
        PrefManager prefManager = new PrefManager(context);
        if (!prefManager.isRemoveAd()) {
            if (ENABLE_ADS) {
                MobileAds.initialize(context, new OnInitializationCompleteListener() {
                    @Override
                    public void onInitializationComplete(InitializationStatus initializationStatus) {

                    }
                });
            }
        }
    }

    private static IsAdsListener isListener;

    public interface IsAdsListener {
        void onClose();

        void onShow();

        void onNotShow();

        void onDisable();
    }

    public static void setIsAdsListener(IsAdsListener isAdsListener) {
        isListener = isAdsListener;
    }


    public static InterstitialAd mInterstitialAd;

    public static void loadInters(Activity activity, Boolean show) {
        PrefManager prefManager = new PrefManager(activity);
        if (!prefManager.isRemoveAd()) {
            if (ENABLE_ADS) {
                AdRequest request = new AdRequest.Builder()
                        .build();
                InterstitialAd.load(activity, INTER, request,
                        new InterstitialAdLoadCallback() {
                            @Override
                            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                                mInterstitialAd = interstitialAd;
                            }

                            @Override
                            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                                mInterstitialAd = null;
                            }
                        });
            }
        }
    }

    public static void showInterst(Activity activity, int interval) {
        PrefManager prefManager = new PrefManager(activity);
        if (!prefManager.isRemoveAd()) {
            if (ENABLE_ADS) {
                if (COUNTER >= interval) {
                    if (mInterstitialAd != null) {
                        mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdDismissedFullScreenContent() {
                                super.onAdDismissedFullScreenContent();
                                isListener.onClose();
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                                super.onAdFailedToShowFullScreenContent(adError);
                                isListener.onNotShow();
//                                    Log.e("adslog", "onAdFailedToShowFullScreenContent: admob ");
                            }

                            @Override
                            public void onAdShowedFullScreenContent() {
                                super.onAdShowedFullScreenContent();
                                isListener.onShow();
                            }
                        });
                        mInterstitialAd.show(activity);
                    } else {
//                            Log.e("adslog", "showInterst: admob null");
                    }
                    loadInters(activity, false);
                    COUNTER = 0;
                } else {
                    COUNTER++;
                    isListener.onNotShow();
                }
            } else isListener.onDisable();
        } else  isListener.onDisable();
    }


    public static RewardedAd mRewardedAd;
    public static boolean unlockreward = false;

    private static RewardListener rewardListener;

    public static interface RewardListener {
        void onRewarded();

        void onRewardFailed();

        void onRewardDisable();
    }

    public static void setRewodListener(RewardListener listener) {
        rewardListener = listener;
    }

    public static void loadReward(Activity activity) {
        PrefManager prefManager = new PrefManager(activity);
        if (!prefManager.isRemoveAd()) {
            if (ENABLE_ADS) {
                AdRequest adRequest = new AdRequest.Builder()
                        .build();
                RewardedAd.load(activity, REWARD_VIDEO,
                        adRequest, new RewardedAdLoadCallback() {
                            @Override
                            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                                mRewardedAd = null;
//                                    Log.v("adslogh", "onAdFailedToLoad: loaderror " + loadAdError.getMessage());
                            }

                            @Override
                            public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                                mRewardedAd = rewardedAd;
                                rewardedAd = null;
//                                    Log.i("adslogh", "onAdFailedToLoad: onAdLoaded " + rewardedAd.getRewardItem());
                            }
                        });

            }
        }
    }


    public static void showDialog(Activity activity) {
        final Dialog dialog = new Dialog(activity, R.style.SheetDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_download);
        Button imgOpen = dialog.findViewById(R.id.tbWatch);
        imgOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAdsConfig.showReward(activity);
                dialog.dismiss();
            }
        });
        Button tbOpen = dialog.findViewById(R.id.tbCancel);
        tbOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public static void showReward(Activity activity) {
        PrefManager prefManager = new PrefManager(activity);
        if (!prefManager.isRemoveAd()) {
            if (ENABLE_ADS) {
//            Log.d("adslog*****", "showReward:  " + mRewardedAd);
                if (mRewardedAd != null) {
                    Activity activityContext = activity;
                    mRewardedAd.show(activityContext, new OnUserEarnedRewardListener() {
                        @Override
                        public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                            unlockreward = true;
                            loadReward(activity);
                        }
                    });
                    mRewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            super.onAdDismissedFullScreenContent();
                            if (unlockreward) rewardListener.onRewarded();
                        }
                    });
                } else {
//                            Log.v("adslog******", "showReward:  null");
                    loadReward(activity);
                    rewardListener.onRewardFailed();
                }
            } else rewardListener.onRewardDisable();
        } else  rewardListener.onRewardDisable();
    }


    public static AdView adViewAdmob;

    public static void showBanner(Activity activity, RelativeLayout layAds, Boolean medium) {
        PrefManager prefManager = new PrefManager(activity);
        if (!prefManager.isRemoveAd()) {
            if (ENABLE_ADS) {
                AdRequest request = new AdRequest.Builder().build();
                adViewAdmob = new AdView(activity);
                adViewAdmob.setAdUnitId(BANNER);
                layAds.addView(adViewAdmob);
                if (!medium) {
                    AdSize adSize = getAdSize(activity);
                    adViewAdmob.setAdSize(adSize);
                } else
                    adViewAdmob.setAdSize(AdSize.MEDIUM_RECTANGLE);
                adViewAdmob.loadAd(request);
                adViewAdmob.setAdListener(new AdListener() {
                    @Override
                    public void onAdLoaded() {
                        super.onAdLoaded();
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        super.onAdFailedToLoad(loadAdError);
                    }
                });
            }
        }
    }

    private static AdSize getAdSize(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        float widthPixels = outMetrics.widthPixels;
        float density = outMetrics.density;
        int adWidth = (int) (widthPixels / density);
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(activity, adWidth);
    }

}
