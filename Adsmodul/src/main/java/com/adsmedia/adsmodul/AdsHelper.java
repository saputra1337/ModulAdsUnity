package com.adsmedia.adsmodul;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

import android.app.Activity;

import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

import com.adsmedia.mastermodul.MasterAdsHelper;
import com.unity3d.ads.IUnityAdsInitializationListener;
import com.unity3d.ads.IUnityAdsLoadListener;
import com.unity3d.ads.IUnityAdsShowListener;
import com.unity3d.ads.UnityAds;
import com.unity3d.ads.UnityAdsShowOptions;
import com.unity3d.services.banners.BannerErrorInfo;
import com.unity3d.services.banners.BannerView;
import com.unity3d.services.banners.UnityBannerSize;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class AdsHelper {
    public static boolean openads = false;
    public static boolean debugMode = false;
    public static boolean directData = false;
    public static void gdpr(Activity activity, Boolean childDirected, String keypos, String gameAppId) {
    }

    public static void initializeAds(Activity activity, int pos) {
    }
    public static void initializeAdsPrime(Activity activity, String pos, String gameId, boolean test) {
        IUnityAdsInitializationListener listener = new IUnityAdsInitializationListener() {
            @Override
            public void onInitializationComplete() {

            }

            @Override
            public void onInitializationFailed(UnityAds.UnityAdsInitializationError error, String message) {

            }
        };
        UnityAds.initialize(activity, gameId, test, listener);
        MasterAdsHelper.initializeAds(activity, pos);
    }

    public static void initializeAdsPrime(Activity activity, String pos, String gameAppId) {
        IUnityAdsInitializationListener listener = new IUnityAdsInitializationListener() {
            @Override
            public void onInitializationComplete() {

            }

            @Override
            public void onInitializationFailed(UnityAds.UnityAdsInitializationError error, String message) {

            }
        };
        UnityAds.initialize(activity, gameAppId, debugMode, listener);
        MasterAdsHelper.initializeAds(activity, pos);
    }
    public static void debugModePrime(Boolean debug) {
        debugMode = debug;
        MasterAdsHelper.debugMode(debug);
    }
    public static BannerView unityBanner;
    public static void showBannerPrime(Activity activity, RelativeLayout layout, String metaId) {
        unityBanner = new BannerView(activity, metaId, new UnityBannerSize(320, 50));
        unityBanner.load();
        directData = true;
        layout.addView(unityBanner);
        unityBanner.setListener(new BannerView.Listener() {
            @Override
            public void onBannerFailedToLoad(BannerView bannerAdView, BannerErrorInfo errorInfo) {
                super.onBannerFailedToLoad(bannerAdView, errorInfo);
                MasterAdsHelper.showBanner(activity, layout);
            }
        });
    }
    public static void loadInterstitialPrime(Activity activity, String admobId) {
        directData = true;
        IUnityAdsLoadListener listener = new IUnityAdsLoadListener() {
            @Override
            public void onUnityAdsAdLoaded(String placementId) {

            }

            @Override
            public void onUnityAdsFailedToLoad(String placementId, UnityAds.UnityAdsLoadError error, String message) {

            }
        };
        try {
            UnityAds.load(admobId, listener);
        } catch (Exception e) {
            e.printStackTrace();
        }
        MasterAdsHelper.loadInterstitial(activity);
    }

    public static int count = 0;

    public static void showInterstitialPrime(Activity activity, String admobId, int interval) {
        if (count >= interval) {
            IUnityAdsShowListener showListener = new IUnityAdsShowListener() {
                @Override
                public void onUnityAdsShowFailure(String placementId, UnityAds.UnityAdsShowError error, String message) {
                    MasterAdsHelper.showInterstitial(activity);
                }

                @Override
                public void onUnityAdsShowStart(String placementId) {

                }

                @Override
                public void onUnityAdsShowClick(String placementId) {

                }

                @Override
                public void onUnityAdsShowComplete(String placementId, UnityAds.UnityAdsShowCompletionState state) {

                }
            };
            UnityAds.show(activity, admobId, new UnityAdsShowOptions(), showListener);
            loadInterstitialPrime(activity, admobId);
            count = 0;
        } else {
            count++;
        }
    }

    public static final String md5(final String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest
                    .getInstance("MD5");
            digest.update(s.getBytes());
            byte[] messageDigest = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++) {
                String h = Integer.toHexString(0xFF & messageDigest[i]);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            //Logger.logStackTrace(TAG,e);
        }
        return "";
    }
}
