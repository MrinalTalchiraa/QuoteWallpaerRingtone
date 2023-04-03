package videostatus.setcallertunewallpaper;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdListener;
import com.applovin.mediation.MaxAdViewAdListener;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.ads.MaxAdView;
import com.applovin.mediation.ads.MaxInterstitialAd;
import com.applovin.mediation.nativeAds.MaxNativeAdListener;
import com.applovin.mediation.nativeAds.MaxNativeAdLoader;
import com.applovin.mediation.nativeAds.MaxNativeAdView;
import com.applovin.sdk.AppLovinSdk;
import com.applovin.sdk.AppLovinSdkConfiguration;
import com.bumptech.glide.Glide;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdListener;
import com.facebook.ads.NativeAdView;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.VideoController;
import com.google.android.gms.ads.appopen.AppOpenAd;
import com.google.android.gms.ads.formats.MediaView;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.wang.avi.AVLoadingIndicatorView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.solovyev.android.checkout.ActivityCheckout;
import org.solovyev.android.checkout.Checkout;
import org.solovyev.android.checkout.EmptyRequestListener;
import org.solovyev.android.checkout.Inventory;
import org.solovyev.android.checkout.ProductTypes;
import org.solovyev.android.checkout.Purchase;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

public class SplashLaunchActivity extends AppCompatActivity {

    // this app
    public static boolean isFirst = false;

    private ActivityCheckout mCheckout;
    private Inventory mInventory;

    DatabaseReference reference;

    AppOpenAd.AppOpenAdLoadCallback loadCallback;

    static ProgressDialog dialog;

    public static InterstitialAd interstitialAd;
    public static InterstitialAdListener interstitialAdListener;

    public static com.google.android.gms.ads.InterstitialAd mInterstitialAd;

    public static MaxInterstitialAd maxInterstitialAd;
    public static int retry = 0;
    public static MaxAd nativeAd;

    //public static String AdMob_AppId = "ca-app-pub-3940256099942544~3347511713";  //  ads add manifests meta-data
    //public static String AdMob_Banner = "ca-app-pub-3940256099942544/6300978111";
    //public static String AdMob_Int = "ca-app-pub-3940256099942544/1033173712";
    //public static String AdMob_Reward = "ca-app-pub-3940256099942544/5224354917";  // test id
    //public static String AdMob_NativeAdvance = "ca-app-pub-3940256099942544/2247696110";
    //public static String AdMob_AppOpenAds = "ca-app-pub-3940256099942544/3419835294";

    public static String AdMob_AppId = "";  //  ads add manifests meta-data
    public static String AdMob_Banner = "";
    public static String AdMob_Int = "";
    public static String AdMob_Reward = "";  // test id
    public static String AdMob_NativeAdvance = "";
    public static String AdMob_AppOpenAds = "";

    public static int interClick;
    public static int ckickAds = 0;

    //IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID

    //MoPub Test Ads Ids
    //public static String FB_Banner = "b195f8dd8ded45fe847ad89ed1d016da";
    //public static String FB_Int = "24534e1901884e398f1253216226017e";
    //public static String FB_Native = "11a17b188668469fb0412708c3d16813";

    public static String FB_Banner = "";
    public static String FB_Int = "";
    public static String FB_Native = "";

    public static String MAX_Banner = "";
    public static String MAX_Int = "";
    public static String MAX_Native = "";

    public static UnifiedNativeAd nativeAd11;

    public static String MoreApps;

    public static String PrivacyPolicy;

    public static String QurekaAds;

    public static ArrayList<String> WebAppIconList;
    public static ArrayList<String> WebAppLinkList;

    public static String CheckAds = "";    //   fb  || admob  || web || startapp || max

    public static Boolean CheckFBAdMobNative = false;

    public static Boolean CheckFBAdMobInterstitial = false;

    public static Boolean QurekaInterstitialAd = false;

    public static String CheckWebinFailed;

    // this activity var
    private AVLoadingIndicatorView avi;
    RelativeLayout RlLoading, RlStart;
    ImageView Privacy;
    RelativeLayout Start;
    Animation bounce;

    String currentVersion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_launch);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }

        UILoad();

    }

    private void UILoad() {

        if (CheckInternet.isNetworkAvailable(this)) {

            try {
                currentVersion = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }


            mCheckout = Checkout.forActivity(this, MyOneApplication.get(SplashLaunchActivity.this).getBilling());

            mCheckout.start();

            mCheckout.createPurchaseFlow(new PurchaseListener());

            mInventory = mCheckout.makeInventory();
            mInventory.load(Inventory.Request.create()
                    .loadAllPurchases()
                    .loadSkus(ProductTypes.IN_APP, getResources().getString(R.string.subscribe_KEY1)), new InventoryCallback());

            getPermission(this);

            FirebaseWebAppCall();

            FirebaseAdsCall();

            new GetVersionCode().execute();


            RlLoading = (RelativeLayout) findViewById(R.id.rlloading);
            RlStart = (RelativeLayout) findViewById(R.id.rlstart);
            RlLoading.setVisibility(View.VISIBLE);
            RlStart.setVisibility(View.GONE);

            Start = (RelativeLayout) findViewById(R.id.start);
            Start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //Mix Interstitial Ads Call
                    Intent intent = new Intent(SplashLaunchActivity.this, AppHomePageMainActivity.class);
                    startActivity(intent);

                }
            });

            Privacy = (ImageView) findViewById(R.id.privacy);
            Privacy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(PrivacyPolicy));
                    intent.setPackage("com.android.chrome");
                    startActivity(intent);

                }
            });

            //Bounce Animation
            bounce = AnimationUtils.loadAnimation(SplashLaunchActivity.this, R.anim.btn_press);
            MyBounceInterpolator interpolator = new MyBounceInterpolator(
                    0.5, 15);
            bounce.setInterpolator(interpolator);
            Start.startAnimation(bounce);

            // 3 loading dots
            String indicator = "LineScalePulseOutRapidIndicator";
            avi = (AVLoadingIndicatorView) findViewById(R.id.avi);
            avi.setIndicator(indicator);
            avi.setIndicatorColor(getResources().getColor(R.color.black1));
            avi.show();

            //if ads id offline mode
            //LoadAd();

        } else {

            final Dialog dialog = new Dialog(SplashLaunchActivity.this);
            dialog.setContentView(R.layout.no_internet);
            dialog.setCancelable(false);
            ImageView Refresh = (ImageView) dialog.findViewById(R.id.refresh);
            ImageView Exit = (ImageView) dialog.findViewById(R.id.exit);
            final ImageView NoInternetIcon = (ImageView) dialog.findViewById(R.id.img);
            LinearLayout MainNointernet = (LinearLayout) dialog.findViewById(R.id.nointernet);


            final Animation shake;
            shake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);

            NoInternetIcon.startAnimation(shake);

            MainNointernet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    NoInternetIcon.startAnimation(shake);

                }
            });

            Refresh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    dialog.dismiss();
                    UILoad();

                }
            });

            Exit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    dialog.dismiss();
                    finish();

                }
            });

            dialog.show();


        }

    }

    public static void QurekaOpenAds(Activity activity) {

        isFirst = true;

        final Dialog dialog = new Dialog(activity, R.style.DialogTheme);
        dialog.setContentView(R.layout.qureka_app_open_ad);
        dialog.setCancelable(false);

        RelativeLayout SkipAd = (RelativeLayout) dialog.findViewById(R.id.btnSkip);
        LinearLayout Qureka = (LinearLayout) dialog.findViewById(R.id.btnQureka);

        SkipAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        Qureka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://778.win.predchamp.com/"));
                intent1.setPackage("com.android.chrome");
                activity.startActivity(intent1);

                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void FirebaseAdsCall() {

        String app_package = getPackageName();
        String new_package = app_package.replace(".","_");

        reference = FirebaseDatabase.getInstance().getReference();

        reference.child(new_package).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.child("0").child("ads").getChildren()) {


                    MoreApps = snapshot.child("app_more").getValue().toString();
                    PrivacyPolicy = snapshot.child("app_privacy").getValue().toString();
                    QurekaAds = snapshot.child("app_qureka").getValue().toString();
                    CheckWebinFailed = snapshot.child("check_webapp").getValue().toString();

                    CheckAds = snapshot.child("check_ads").getValue().toString();

                    if (CheckAds.equals("admob")) {

                        AdMob_AppId = snapshot.child("admob_appid").getValue().toString();
                        AdMob_AppOpenAds = snapshot.child("admob_openads").getValue().toString();
                        AdMob_Banner = snapshot.child("admob_banner").getValue().toString();
                        AdMob_Int = snapshot.child("admob_inter").getValue().toString();
                        AdMob_NativeAdvance = snapshot.child("admob_native").getValue().toString();
                        AdMob_Reward = snapshot.child("admob_reward").getValue().toString();

                        interClick = Integer.parseInt(snapshot.child("inter_click").getValue().toString());

                    } else if (CheckAds.equals("fb")) {

                        AdMob_AppOpenAds = snapshot.child("admob_openads").getValue().toString();

                        //FB Ads
                        FB_Banner = snapshot.child("fb_banner").getValue().toString();
                        FB_Int = snapshot.child("fb_inter").getValue().toString();
                        FB_Native = snapshot.child("fb_native").getValue().toString();

                        if (TextUtils.isEmpty(FB_Int)) {
                            CheckFBAdMobInterstitial = true;
                            AdMob_Int = snapshot.child("admob_inter").getValue().toString();
                            interClick = Integer.parseInt(snapshot.child("inter_click").getValue().toString());
                        }

                        if (TextUtils.isEmpty(FB_Native)) {
                            CheckFBAdMobNative = true;
                            AdMob_NativeAdvance = snapshot.child("admob_native").getValue().toString();
                        }

                    } else if (CheckAds.equals("web")) {

                        AdMob_AppOpenAds = snapshot.child("admob_openads").getValue().toString();

                        //FB Ads
                        FB_Banner = snapshot.child("fb_banner").getValue().toString();
                        FB_Int = snapshot.child("fb_inter").getValue().toString();
                        FB_Native = snapshot.child("fb_native").getValue().toString();

                        if (TextUtils.isEmpty(FB_Int)) {
                            CheckFBAdMobInterstitial = true;
                            AdMob_Int = snapshot.child("admob_inter").getValue().toString();
                            interClick = Integer.parseInt(snapshot.child("inter_click").getValue().toString());
                        }

                        if (TextUtils.isEmpty(FB_Native)) {
                            CheckFBAdMobNative = true;
                            AdMob_NativeAdvance = snapshot.child("admob_native").getValue().toString();
                        }

                    } else if (CheckAds.equals("max")){

                        AdMob_AppOpenAds = snapshot.child("admob_openads").getValue().toString();

                        MAX_Banner = snapshot.child("max_banner").getValue().toString();
                        MAX_Int = snapshot.child("max_inter").getValue().toString();
                        MAX_Native = snapshot.child("max_native").getValue().toString();

                        if (TextUtils.isEmpty(FB_Int)) {
                            CheckFBAdMobInterstitial = true;
                            AdMob_Int = snapshot.child("admob_inter").getValue().toString();
                            interClick = Integer.parseInt(snapshot.child("inter_click").getValue().toString());
                        }

                        if (TextUtils.isEmpty(FB_Native)) {
                            CheckFBAdMobNative = true;
                            AdMob_NativeAdvance = snapshot.child("admob_native").getValue().toString();
                        }

                    }

                    if(TextUtils.isEmpty(AdMob_AppOpenAds)){
                        QurekaOpenAds(SplashLaunchActivity.this);
                    }

                }

                LoadAd();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                LoadAd();

                Toast.makeText(SplashLaunchActivity.this, "Server Issue", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void FirebaseWebAppCall() {

        reference = FirebaseDatabase.getInstance().getReference();

        reference.child("web_link").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                WebAppIconList = new ArrayList<>();
                WebAppLinkList = new ArrayList<>();

                for (DataSnapshot snapshot : dataSnapshot.child("0").child("ads").getChildren()) {

                    String icon = snapshot.child("icon").getValue().toString();
                    String link = snapshot.child("link").getValue().toString();

                    WebAppIconList.add(icon);
                    WebAppLinkList.add(link);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(SplashLaunchActivity.this, "Server Issue", Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void QurekaAds(View view) {

        Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse(SplashLaunchActivity.QurekaAds));
        intent1.setPackage("com.android.chrome");
        startActivity(intent1);

    }


    class MyBounceInterpolator implements android.view.animation.Interpolator {
        private double mAmplitude = 1;
        private double mFrequency = 10;

        MyBounceInterpolator(double amplitude, double frequency) {
            mAmplitude = amplitude;
            mFrequency = frequency;
        }

        @Override
        public float getInterpolation(float time) {
            return (float) (-1 * Math.pow(Math.E, -time / mAmplitude)
                    * Math.cos(mFrequency * time) + 1);
        }
    }

    public void LoadAd() {

        if (MyOneApplication.getuser_balance() == 0) {
            if (!TextUtils.isEmpty(SplashLaunchActivity.AdMob_AppOpenAds)) {

                try {
                    loadCallback = new AppOpenAd.AppOpenAdLoadCallback() {
                        public void onAppOpenAdLoaded(AppOpenAd appOpenAd) {
                            appOpenAd.show(SplashLaunchActivity.this, new FullScreenContentCallback() {
                                public void onAdShowedFullScreenContent() {
                                }
                                public void onAdDismissedFullScreenContent() {
                                    //startActivity(new Intent(Act_Launcher.this, Act_Main.class));
                                    //finish();
                                }
                                public void onAdFailedToShowFullScreenContent(AdError adError) {
                                    //Act_Launcher.this.startActivity(new Intent(Act_Launcher.this, Act_Main.class));
                                    //finish();
                                }
                            });
                        }

                        public void onAppOpenAdFailedToLoad(LoadAdError loadAdError) {
                            Log.e("AdmobBeta", "Error- " + loadAdError.toString());
                            // Act_Launcher.this.startActivity(new Intent(Act_Launcher.this, Act_Main.class));
                            //finish();
                        }
                    };
                    AppOpenAd.load((Context) SplashLaunchActivity.this, SplashLaunchActivity.AdMob_AppOpenAds, new AdRequest.Builder().build(), 1, loadCallback);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        if(CheckAds.equals("max")){
            // Make sure to set the mediation provider value to "max" to ensure proper functionality
            AppLovinSdk.getInstance( this ).setMediationProvider( "max" );
            AppLovinSdk.initializeSdk( this, new AppLovinSdk.SdkInitializationListener() {
                @Override
                public void onSdkInitialized(final AppLovinSdkConfiguration configuration)
                {
                    // AppLovin SDK is initialized, start loading ads
                }
            } );

            MAXInitialization(SplashLaunchActivity.this);

        }


        AudienceNetworkAds.initialize(getApplicationContext());

       // MobileAds.initialize(this, AdMob_AppId);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mInterstitialAd = new com.google.android.gms.ads.InterstitialAd(this);
        mInterstitialAd.setAdUnitId(AdMob_Int);
        mInterstitialAd.loadAd(new AdRequest.Builder().build());


        mInterstitialAd = new com.google.android.gms.ads.InterstitialAd(this);
        mInterstitialAd.setAdUnitId(AdMob_Int);
        mInterstitialAd.loadAd(new AdRequest.Builder().build());


        //fb interstitial Ads code

        FBAdsInitialization(getApplicationContext());


        avi.hide();
        RlLoading.setVisibility(View.GONE);
        RlStart.setVisibility(View.VISIBLE);

    }

    public static void FBAdsInitialization(Context context) {


        interstitialAd = new InterstitialAd(context, FB_Int);

        interstitialAdListener = new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {

            }

            @Override
            public void onInterstitialDismissed(Ad ad) {

                interstitialAd.loadAd(interstitialAd.buildLoadAdConfig()
                        .withAdListener(interstitialAdListener)
                        .build());

            }

            @Override
            public void onError(Ad ad, AdError adError) {

            }

            @Override
            public void onAdLoaded(Ad ad) {

            }

            @Override
            public void onAdClicked(Ad ad) {

            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }
        };

        interstitialAd.loadAd(interstitialAd.buildLoadAdConfig()
                .withAdListener(interstitialAdListener)
                .build());

    }

    private static void MAXInitialization(Activity context) {

        maxInterstitialAd = new MaxInterstitialAd(SplashLaunchActivity.MAX_Int, context);
        MaxAdListener adListener = new MaxAdListener() {
            @Override
            public void onAdLoaded(MaxAd ad) {

            }

            @Override
            public void onAdDisplayed(MaxAd ad) {

            }

            @Override
            public void onAdHidden(MaxAd ad) {

            }

            @Override
            public void onAdClicked(MaxAd ad) {

            }

            @Override
            public void onAdLoadFailed(String adUnitId, MaxError error) {
                retry++;
                long delay = TimeUnit.SECONDS.toMillis((long) Math.pow(2, Math.min(6, retry)));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        maxInterstitialAd.loadAd();
                    }
                }, delay);

            }

            @Override
            public void onAdDisplayFailed(MaxAd ad, MaxError error) {

            }
        };
        maxInterstitialAd.setListener(adListener);
        maxInterstitialAd.loadAd();

    }

    // AdMob Banner
    public static void AdMobBanner(final Context context, final RelativeLayout adMobbanner) {

        AdView mAdView = new AdView(context);
        mAdView.setAdSize(AdSize.BANNER);
        mAdView.setAdUnitId(AdMob_Banner);
        ((RelativeLayout) adMobbanner).addView(mAdView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);

                try {

                    if(CheckWebinFailed.equals("1")){

                        final float scale = context.getResources().getDisplayMetrics().density;
                        final int pixels = (int) (70 * scale + 0.5f);

                        final int min = 0;
                        final int max = 4;
                        final int random = new Random().nextInt((max - min) + 1) + min;

                        adMobbanner.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(WebAppLinkList.get(random)));
                                intent.setPackage("com.android.chrome");
                                context.startActivity(intent);
                            }
                        });

                        Picasso.get().load(WebAppIconList.get(random)).into(new Target() {
                            @Override
                            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

                                adMobbanner.setMinimumHeight(pixels);

                                Drawable dr = new BitmapDrawable(bitmap);

                                adMobbanner.setBackground(dr);

                            }

                            @Override
                            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                            }

                            @Override
                            public void onPrepareLoad(Drawable placeHolderDrawable) {

                            }
                        });

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }


            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        });

    }

    private static void MAXBanner(Context context, RelativeLayout adContainer, RelativeLayout adContainer2, ImageView OwnBannerAds) {

        MaxAdView adView = new MaxAdView(SplashLaunchActivity.MAX_Banner, context);
        adView.setListener(new MaxAdViewAdListener() {
            @Override
            public void onAdExpanded(MaxAd ad) {

            }

            @Override
            public void onAdCollapsed(MaxAd ad) {

            }

            @Override
            public void onAdLoaded(MaxAd ad) {

            }

            @Override
            public void onAdDisplayed(MaxAd ad) {

            }

            @Override
            public void onAdHidden(MaxAd ad) {

            }

            @Override
            public void onAdClicked(MaxAd ad) {

            }

            @Override
            public void onAdLoadFailed(String adUnitId, MaxError error) {

                try {

                    if (CheckWebinFailed.equals("1")) {

                        final int min = 0;
                        final int max = 4;
                        final int random = new Random().nextInt((max - min) + 1) + min;

                        if (WebAppIconList.get(random) != null && !WebAppIconList.get(random).isEmpty() && !WebAppIconList.get(random).equals("null")) {

                            OwnBannerAds.setVisibility(View.VISIBLE);

                            Glide.with(context)
                                    .load(WebAppIconList.get(random))
                                    .into(OwnBannerAds);

                            OwnBannerAds.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(WebAppLinkList.get(random)));
                                    intent.setPackage("com.android.chrome");
                                    context.startActivity(intent);
                                }
                            });
                        }

                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onAdDisplayFailed(MaxAd ad, MaxError error) {

            }
        });

        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        int height = context.getResources().getDimensionPixelSize(R.dimen.banner_height);
        adView.setLayoutParams(new FrameLayout.LayoutParams(width, height, Gravity.BOTTOM));

        adContainer.addView(adView);
        adView.loadAd();

    }

    // FB Banner Only
    public static void FbBanner(final Context context, final RelativeLayout adContainer, final RelativeLayout adStartApp, final ImageView OwnBannerAds) {

        if (FB_Banner != null && !FB_Banner.isEmpty() && !FB_Banner.equals("null")) {

            final RelativeLayout AdLayout = adContainer;
            AdLayout.setVisibility(View.GONE);
            com.facebook.ads.AdView adView = new com.facebook.ads.AdView(context, FB_Banner, com.facebook.ads.AdSize.BANNER_HEIGHT_50);
            AdLayout.addView(adView);

            com.facebook.ads.AdListener adListener = new com.facebook.ads.AdListener() {
                @Override
                public void onError(Ad ad, AdError adError) {

                    try {

                        if (CheckWebinFailed.equals("1")) {

                            final int min = 0;
                            final int max = 4;
                            final int random = new Random().nextInt((max - min) + 1) + min;

                            if (WebAppIconList.get(random) != null && !WebAppIconList.get(random).isEmpty() && !WebAppIconList.get(random).equals("null")) {

                                OwnBannerAds.setVisibility(View.VISIBLE);

                                Glide.with(context)
                                        .load(WebAppIconList.get(random))
                                        .into(OwnBannerAds);

                                OwnBannerAds.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(WebAppLinkList.get(random)));
                                        intent.setPackage("com.android.chrome");
                                        context.startActivity(intent);
                                    }
                                });
                            }

                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onAdLoaded(Ad ad) {

                    AdLayout.setVisibility(View.VISIBLE);

                }

                @Override
                public void onAdClicked(Ad ad) {

                }

                @Override
                public void onLoggingImpression(Ad ad) {

                }
            };

            //Request an ad
            adView.loadAd(adView.buildLoadAdConfig().withAdListener(adListener).build());


        }else {

            try {

                if (CheckWebinFailed.equals("1")) {

                    final int min = 0;
                    final int max = 4;
                    final int random = new Random().nextInt((max - min) + 1) + min;

                    if (WebAppIconList.get(random) != null && !WebAppIconList.get(random).isEmpty() && !WebAppIconList.get(random).equals("null")) {

                        OwnBannerAds.setVisibility(View.VISIBLE);

                        Glide.with(context)
                                .load(WebAppIconList.get(random).toString())
                                .into(OwnBannerAds);

                        OwnBannerAds.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(WebAppLinkList.get(random)));
                                intent.setPackage("com.android.chrome");
                                context.startActivity(intent);
                            }
                        });

                    }

                }


            } catch (Exception e) {
                e.printStackTrace();
            }

        }


    }

    //Web Server Ads
    private static void WebBanner(Context context, ImageView ownBannerAds) {

        try {
            final int min = 0;
            final int max = 4;
            final int random = new Random().nextInt((max - min) + 1) + min;

            if(WebAppIconList.get(random) != null && !WebAppIconList.get(random).isEmpty() && !WebAppIconList.get(random).equals("null")) {

                ownBannerAds.setVisibility(View.VISIBLE);

                Glide.with(context)
                        .load(WebAppIconList.get(random))
                        .into(ownBannerAds);

                ownBannerAds.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(WebAppLinkList.get(random)));
                        intent.setPackage("com.android.chrome");
                        context.startActivity(intent);
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    // BannerAds StartApp
    private static void StartAppBanner(Activity context, RelativeLayout adContainer, RelativeLayout adContainer2, ImageView ownBannerAds) {

        /*adContainer.setVisibility(View.GONE);
        adContainer2.setVisibility(View.VISIBLE);

        Banner startAppBanner = new Banner(context);
        RelativeLayout.LayoutParams bannerParameters =
                new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        bannerParameters.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

        adContainer2.addView(startAppBanner, bannerParameters);*/

    }

    private static void NativeAdsMAX(Context context, RelativeLayout nativeAdContainer, RelativeLayout nativeAdsStartApp, ShimmerFrameLayout shimmerFrameLayout, ImageView image, CardView qurekanative, FrameLayout nativeMax) {

        nativeMax.setVisibility(View.VISIBLE);

        MaxNativeAdLoader nativeAdLoader = new MaxNativeAdLoader( SplashLaunchActivity.MAX_Native, context );
        nativeAdLoader.setNativeAdListener( new MaxNativeAdListener()
        {
            @Override
            public void onNativeAdLoaded(final MaxNativeAdView nativeAdView, final MaxAd ad)
            {
                // Clean up any pre-existing native ad to prevent memory leaks.
                if ( nativeAd != null )
                {
                    nativeAdLoader.destroy( nativeAd );
                }

                // Save ad for cleanup.
                nativeAd = ad;

                // Add ad view to view.
                nativeMax.removeAllViews();
                nativeMax.addView( nativeAdView );
            }

            @Override
            public void onNativeAdLoadFailed(final String adUnitId, final MaxError error)
            {
                try {

                    nativeMax.setVisibility(View.GONE);

                    if (CheckWebinFailed.equals("1")) {


                        qurekanative.setVisibility(View.VISIBLE);

                        final int min = 5;
                        final int max = 9;
                        final int random = new Random().nextInt((max - min) + 1) + min;

                        Glide.with(context)
                                .load(WebAppIconList.get(random))
                                .into(image);

                        nativeAdContainer.setVisibility(View.GONE);
                        //START_native_layout.setVisibility(View.VISIBLE);
                        shimmerFrameLayout.stopShimmerAnimation();
                        shimmerFrameLayout.setVisibility(View.GONE);
                        image.setVisibility(View.VISIBLE);

                        qurekanative.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(WebAppLinkList.get(random)));
                                intent.setPackage("com.android.chrome");
                                context.startActivity(intent);
                            }
                        });

                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNativeAdClicked(final MaxAd ad)
            {
                // Optional click callback
            }
        } );

        nativeAdLoader.loadAd();

    }

    //  Facebook Native Ads Only
    public static void NativeAdsFB(final Context context, RelativeLayout FBnativeAdLayout, RelativeLayout StartAppNativeLayout, final ShimmerFrameLayout shimmerFrameLayout, ImageView image, CardView qurekanative) {

        final RelativeLayout FB_native_layout = FBnativeAdLayout;
        FB_native_layout.setVisibility(View.VISIBLE);
        final RelativeLayout START_native_layout = StartAppNativeLayout;

        final ImageView img = image;
        final ShimmerFrameLayout loading = shimmerFrameLayout;
        loading.setVisibility(View.VISIBLE);

        loading.startShimmerAnimation();

        if (CheckInternet.isNetworkAvailable(context)) {

            final NativeAd nativeAd = new NativeAd(context, FB_Native);
            NativeAdListener nativeAdListener = new NativeAdListener() {
                @Override
                public void onMediaDownloaded(Ad ad) {

                }

                @Override
                public void onError(Ad ad, AdError adError) {

                    NativeStart();

                }

                private void NativeStart() {


                    try {

                        if (CheckWebinFailed.equals("1")) {

                            qurekanative.setVisibility(View.VISIBLE);

                            final int min = 5;
                            final int max = 9;
                            final int random = new Random().nextInt((max - min) + 1) + min;

                            Glide.with(context)
                                    .load(WebAppIconList.get(random))
                                    .into(img);

                            FB_native_layout.setVisibility(View.GONE);
                            //START_native_layout.setVisibility(View.VISIBLE);
                            shimmerFrameLayout.stopShimmerAnimation();
                            shimmerFrameLayout.setVisibility(View.GONE);
                            img.setVisibility(View.VISIBLE);

                            qurekanative.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(WebAppLinkList.get(random)));
                                    intent.setPackage("com.android.chrome");
                                    context.startActivity(intent);
                                }
                            });

                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }

                @Override
                public void onAdLoaded(Ad ad) {

                    img.setVisibility(View.GONE);
                    shimmerFrameLayout.stopShimmerAnimation();
                    shimmerFrameLayout.setVisibility(View.GONE);
                    View adView = NativeAdView.render(context, nativeAd, NativeAdView.Type.HEIGHT_300);
                    FB_native_layout.addView(adView);

                }

                @Override
                public void onAdClicked(Ad ad) {

                }

                @Override
                public void onLoggingImpression(Ad ad) {

                }
            };

            // Request an ad
            nativeAd.loadAd(
                    nativeAd.buildLoadAdConfig()
                            .withAdListener(nativeAdListener)
                            .build());


        } else {

            shimmerFrameLayout.stopShimmerAnimation();
            shimmerFrameLayout.setVisibility(View.GONE);
            FB_native_layout.setVisibility(View.GONE);
            img.setVisibility(View.VISIBLE);

        }

    }

    private static void NativeAdsWeb(Context context, RelativeLayout FBnativeAdLayout, RelativeLayout StartAppNativeLayout, final ShimmerFrameLayout shimmerFrameLayout, ImageView image, CardView qurekanative) {

        qurekanative.setVisibility(View.VISIBLE);

        final RelativeLayout FB_native_layout = FBnativeAdLayout;
        FB_native_layout.setVisibility(View.VISIBLE);
        final RelativeLayout START_native_layout = StartAppNativeLayout;

        final ImageView img = image;

        try {
            final int min = 5;
            final int max = 9;
            final int random = new Random().nextInt((max - min) + 1) + min;

            Glide.with(context)
                    .load(WebAppIconList.get(random))
                    .into(img);

            FB_native_layout.setVisibility(View.GONE);
            img.setVisibility(View.VISIBLE);

            qurekanative.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse( WebAppLinkList.get(random)));
                    intent.setPackage("com.android.chrome");
                    context.startActivity(intent);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    //Native StartApp
    private static void NativeStartApp(Activity activity, ImageView image, RelativeLayout nativeAdsStartApp) {

        image.setVisibility(View.GONE);
        nativeAdsStartApp.setVisibility(View.VISIBLE);

    }

    // AdMob Native Ads
    public static void NativeAdsAdMob(final Activity activity, final FrameLayout frameLayout1, ShimmerFrameLayout shimmerFrameLayout, ImageView image, CardView qurekanative) {

        final ImageView img = image;
        //final ShimmerFrameLayout loading = shimmerFrameLayout;

        img.setVisibility(View.GONE);
        //loading.startShimmerAnimation();

        if (CheckInternet.isNetworkAvailable(activity)) {

            AdLoader.Builder builder = new AdLoader.Builder(activity, AdMob_NativeAdvance);

            builder.forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
                // OnUnifiedNativeAdLoadedListener implementation.
                @Override
                public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                    // You must call destroy on old ads when you are done with them,
                    // otherwise you will have a memory leak.
                    if (nativeAd11 != null) {
                        nativeAd11.destroy();
                    }
                    nativeAd11 = unifiedNativeAd;
                    FrameLayout frameLayout = frameLayout1;
                    frameLayout.setVisibility(View.VISIBLE);
                    UnifiedNativeAdView adView = (UnifiedNativeAdView) activity.getLayoutInflater()
                            .inflate(R.layout.ad_unified, null);
                    populateUnifiedNativeAdView(unifiedNativeAd, adView);
                    frameLayout.removeAllViews();
                    frameLayout.addView(adView);
                }

            });

            AdLoader adLoader = builder.withAdListener(new AdListener() {
                @Override
                public void onAdFailedToLoad(int errorCode) {

                    try {

                        if (CheckWebinFailed.equals("1")) {

                            qurekanative.setVisibility(View.VISIBLE);

                            final int min = 5;
                            final int max = 9;
                            final int random = new Random().nextInt((max - min) + 1) + min;

                            Glide.with(activity)
                                    .load(WebAppIconList.get(random))
                                    .into(img);

                            img.setVisibility(View.VISIBLE);
                            //loading.stopShimmerAnimation();
                            //loading.setVisibility(View.GONE);
                            frameLayout1.setVisibility(View.GONE);

                            qurekanative.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(WebAppLinkList.get(random)));
                                    intent.setPackage("com.android.chrome");
                                    activity.startActivity(intent);
                                }
                            });

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();
                    img.setVisibility(View.GONE);
                    //loading.stopShimmerAnimation();
                    //loading.setVisibility(View.GONE);
                }

                @Override
                public void onAdOpened() {
                    super.onAdOpened();

                    img.setVisibility(View.GONE);
                    //loading.stopShimmerAnimation();
                    //loading.setVisibility(View.GONE);
                }
            }).build();

            adLoader.loadAd(new AdRequest.Builder().build());

        } else {

            //loading.stopShimmerAnimation();
            //loading.setVisibility(View.GONE);
            frameLayout1.setVisibility(View.GONE);
            img.setVisibility(View.VISIBLE);
        }

    }

    // AdMob Native Ads
    public static void NativeAdsAdMobBlack(final Activity activity, final FrameLayout frameLayout1, ShimmerFrameLayout shimmerFrameLayout, ImageView image, CardView qurekanative) {

        final ImageView img = image;
        //final ShimmerFrameLayout loading = shimmerFrameLayout;

        img.setVisibility(View.GONE);
        //loading.startShimmerAnimation();

        if (CheckInternet.isNetworkAvailable(activity)) {

            AdLoader.Builder builder = new AdLoader.Builder(activity, AdMob_NativeAdvance);

            builder.forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
                // OnUnifiedNativeAdLoadedListener implementation.
                @Override
                public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                    // You must call destroy on old ads when you are done with them,
                    // otherwise you will have a memory leak.
                    if (nativeAd11 != null) {
                        nativeAd11.destroy();
                    }
                    nativeAd11 = unifiedNativeAd;
                    FrameLayout frameLayout = frameLayout1;
                    frameLayout.setVisibility(View.VISIBLE);
                    UnifiedNativeAdView adView = (UnifiedNativeAdView) activity.getLayoutInflater()
                            .inflate(R.layout.ad_unified_black, null);
                    populateUnifiedNativeAdView(unifiedNativeAd, adView);
                    frameLayout.removeAllViews();
                    frameLayout.addView(adView);
                }

            });

            AdLoader adLoader = builder.withAdListener(new AdListener() {
                @Override
                public void onAdFailedToLoad(int errorCode) {


                    try {

                        if (CheckWebinFailed.equals("1")) {

                            qurekanative.setVisibility(View.VISIBLE);

                            final int min = 5;
                            final int max = 9;
                            final int random = new Random().nextInt((max - min) + 1) + min;

                            Glide.with(activity)
                                    .load(WebAppIconList.get(random))
                                    .into(img);

                            img.setVisibility(View.VISIBLE);
                            //loading.stopShimmerAnimation();
                            //loading.setVisibility(View.GONE);
                            frameLayout1.setVisibility(View.GONE);

                            qurekanative.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(WebAppLinkList.get(random)));
                                    intent.setPackage("com.android.chrome");
                                    activity.startActivity(intent);
                                }
                            });

                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }

                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();
                    img.setVisibility(View.GONE);
                    //loading.stopShimmerAnimation();
                    //loading.setVisibility(View.GONE);


                }

                @Override
                public void onAdOpened() {
                    super.onAdOpened();

                    img.setVisibility(View.GONE);
                    //loading.stopShimmerAnimation();
                    //loading.setVisibility(View.GONE);


                }
            }).build();

            adLoader.loadAd(new AdRequest.Builder().build());

        } else {

            //loading.stopShimmerAnimation();
            //loading.setVisibility(View.GONE);
            frameLayout1.setVisibility(View.GONE);
            img.setVisibility(View.VISIBLE);
        }

    }

    //AdMob Native Ads
    private static void populateUnifiedNativeAdView(UnifiedNativeAd nativeAd, UnifiedNativeAdView adView) {

        MediaView mediaView = adView.findViewById(R.id.ad_media);
        adView.setMediaView(mediaView);

        // Set other ad assets.
        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
        adView.setBodyView(adView.findViewById(R.id.ad_body));
        adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
        adView.setIconView(adView.findViewById(R.id.ad_app_icon));
        adView.setPriceView(adView.findViewById(R.id.ad_price));
        adView.setStarRatingView(adView.findViewById(R.id.ad_stars));
        adView.setStoreView(adView.findViewById(R.id.ad_store));
        adView.setAdvertiserView(adView.findViewById(R.id.ad_advertiser));

        ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());

        if (nativeAd.getBody() == null) {
            adView.getBodyView().setVisibility(View.INVISIBLE);
        } else {
            adView.getBodyView().setVisibility(View.VISIBLE);
            ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
        }

        if (nativeAd.getCallToAction() == null) {
            adView.getCallToActionView().setVisibility(View.INVISIBLE);
        } else {
            adView.getCallToActionView().setVisibility(View.VISIBLE);
            ((Button) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
        }

        if (nativeAd.getIcon() == null) {
            adView.getIconView().setVisibility(View.GONE);
        } else {
            ((ImageView) adView.getIconView()).setImageDrawable(
                    nativeAd.getIcon().getDrawable());
            adView.getIconView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getPrice() == null) {
            adView.getPriceView().setVisibility(View.INVISIBLE);
        } else {
            adView.getPriceView().setVisibility(View.VISIBLE);
            ((TextView) adView.getPriceView()).setText(nativeAd.getPrice());
        }

        if (nativeAd.getStore() == null) {
            adView.getStoreView().setVisibility(View.INVISIBLE);
        } else {
            adView.getStoreView().setVisibility(View.VISIBLE);
            ((TextView) adView.getStoreView()).setText(nativeAd.getStore());
        }

        if (nativeAd.getStarRating() == null) {
            adView.getStarRatingView().setVisibility(View.INVISIBLE);
        } else {
            ((RatingBar) adView.getStarRatingView())
                    .setRating(nativeAd.getStarRating().floatValue());
            adView.getStarRatingView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getAdvertiser() == null) {
            adView.getAdvertiserView().setVisibility(View.INVISIBLE);
        } else {
            ((TextView) adView.getAdvertiserView()).setText(nativeAd.getAdvertiser());
            adView.getAdvertiserView().setVisibility(View.VISIBLE);
        }

        adView.setNativeAd(nativeAd);
        VideoController vc = nativeAd.getVideoController();

        if (vc.hasVideoContent()) {
            vc.setVideoLifecycleCallbacks(new VideoController.VideoLifecycleCallbacks() {
                @Override
                public void onVideoEnd() {
                    super.onVideoEnd();
                }
            });
        } else {
        }
    }

    public static void ADSDialog(Context context) {
        dialog = new ProgressDialog(context);
        dialog.setMessage("Ads Load");
        dialog.setCancelable(false);
        dialog.show();
        ADMOBTimerDialog();
    }

    public static void ADMOBTimerDialog() {
        new CountDownTimer(500, 1000) {
            @Override
            public void onTick(long j) {
            }

            @Override
            public void onFinish() {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
                try {
                    mInterstitialAd.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public static void FBADSDialog(Context activity) {
        dialog = new ProgressDialog(activity);
        dialog.setMessage("Ads Load");
        dialog.setCancelable(false);
        dialog.show();
        FBTimerDialog();
    }

    public static void FBTimerDialog() {
        new CountDownTimer(00, 1000) {
            @Override
            public void onTick(long j) {
            }

            @Override
            public void onFinish() {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
                try {
                    interstitialAd.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    class GetVersionCode extends AsyncTask<Void, String, String> {

        @Override
        protected String doInBackground(Void... voids) {

            String newVersion = null;

            try {
                Document document = Jsoup.connect("https://play.google.com/store/apps/details?id=" + getPackageName()  + "&hl=en")
                        .timeout(30000)
                        .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                        .referrer("http://www.google.com")
                        .get();
                if (document != null) {
                    Elements element = document.getElementsContainingOwnText("Current Version");
                    for (Element ele : element) {
                        if (ele.siblingElements() != null) {
                            Elements sibElemets = ele.siblingElements();
                            for (Element sibElemet : sibElemets) {
                                newVersion = sibElemet.text();
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return newVersion;

        }


        @Override
        protected void onPostExecute(String onlineVersion) {

            super.onPostExecute(onlineVersion);

            if (onlineVersion != null && !onlineVersion.isEmpty()) {

                if (Float.valueOf(currentVersion) < Float.valueOf(onlineVersion)) {

                    UpdateDialog(currentVersion, onlineVersion);

                }

            }

            Log.d("update", "Current version " + currentVersion + "playstore version " + onlineVersion);

        }
    }

    private void UpdateDialog(String currentVersion, String onlineVersion) {

        try {
            final Dialog dialogUpdate = new Dialog(SplashLaunchActivity.this);
            dialogUpdate.setContentView(R.layout.popup_update_dailog);
            dialogUpdate.setCancelable(false);

            String appName = getResources().getString(R.string.app_name);

            TextView UpdateTextView = (TextView) dialogUpdate.findViewById(R.id.updatetext);
            ImageView Cancel = (ImageView) dialogUpdate.findViewById(R.id.ucancel);
            ImageView Update = (ImageView) dialogUpdate.findViewById(R.id.update);

            UpdateTextView.setText("Update " + onlineVersion + " is available to download. Downloading the latest update you will get the latest features, improvements and bugs fixes for " + appName + "." );

            Cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    dialogUpdate.dismiss();

                }
            });

            Update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    dialogUpdate.dismiss();

                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));

                }
            });

            dialogUpdate.show();
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }

    }

    // Mix Interstitial Ads Call
    public static void InterstitialAdsCall(Activity context, Intent intent) {

        try {

            if(MyOneApplication.getuser_balance() == 0){

                if(CheckAds.equals("fb")){
                    if(SplashLaunchActivity.CheckFBAdMobInterstitial == true){
                        AdMobInterstitialAdCall(context, intent);
                    }else {
                        //Fb Ads in next activity
                        if(intent != null) {

                            QurekaInterstitialAd = true;

                            context.startActivity(intent);
                        }else {
                            FBInterstitialAdCall(context);
                        }
                    }
                } else if(CheckAds.equals("admob")){
                    AdMobInterstitialAdCall(context, intent);
                } else if(CheckAds.equals("web")){

                    if(!TextUtils.isEmpty(AdMob_Int)){
                        AdMobInterstitialAdCall(context, intent);
                    } else if(!TextUtils.isEmpty(FB_Int)){
                        if(intent != null) {

                            QurekaInterstitialAd = false;

                            context.startActivity(intent);
                        }else {
                            FBInterstitialAdCall(context);
                        }
                    } else {
                        if(intent != null) {

                            QurekaInterstitialAd = false;

                            context.startActivity(intent);
                            //StartAppAd.showAd(context);
                        }else {
                            QurekaInterstitialAd = true;
                            SplashLaunchActivity.QurekaInterstitialAdAdCall(context);
                        }
                    }

                } else if(CheckAds.equals("max")){

                    if(!TextUtils.isEmpty(MAX_Int)){
                        if(intent != null) {
                            context.startActivity(intent);
                        }
                    }else {
                        if(!TextUtils.isEmpty(AdMob_Int)){
                            AdMobInterstitialAdCall(context, intent);
                        }
                    }

                } else {
                    if(intent != null) {
                        context.startActivity(intent);
                    }
                }

            } else {
                if(intent != null) {
                    context.startActivity(intent);
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // AdMob Interstitial Ads Call
    public static void AdMobInterstitialAdCall(Context context, Intent intent) {

        try {

            ckickAds++;

            if(ckickAds == interClick){

                ckickAds = 0;

                // AdMob InterstitialAd
                if (mInterstitialAd.isLoaded()) {
                    ADSDialog(context);
                    mInterstitialAd.setAdListener(new AdListener() {
                        public void onAdClosed() {
                            super.onAdClosed();
                            mInterstitialAd.loadAd(new AdRequest.Builder().build());

                            QurekaInterstitialAd = false;

                            if(intent != null) {
                                context.startActivity(intent);
                            }else {
                                SplashLaunchActivity.QurekaInterstitialAdAdCall(context);
                            }
                        }
                    });
                } else {

                    if (CheckWebinFailed.equals("1")) {
                        QurekaInterstitialAd = true;
                    }else {
                        QurekaInterstitialAd = false;
                    }

                    if(intent != null) {
                        context.startActivity(intent);
                    }else {
                        SplashLaunchActivity.QurekaInterstitialAdAdCall(context);
                    }
                    //StartAppAd.showAd(context);
                }

            }else {

                if (CheckWebinFailed.equals("1")) {
                    QurekaInterstitialAd = true;
                }else {
                    QurekaInterstitialAd = false;
                }

                if(intent != null) {
                    context.startActivity(intent);
                }else {
                    SplashLaunchActivity.QurekaInterstitialAdAdCall(context);
                }

            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // FB Interstitial Ads Code in Next Activity Start
    public static void FBInterstitialAdCall(Activity context) {

        try {

            if(MyOneApplication.getuser_balance() == 0){

                if(CheckAds.equals("fb")) {
                    if (CheckFBAdMobInterstitial == false) {
                        if (interstitialAd.isAdLoaded()) {

                            QurekaInterstitialAd = false;

                            FBADSDialog(context);
                        }else {

                            QurekaInterstitialAd = true;

                            //StartAppAd.showAd(context);
                        }
                        FBAdsInitialization(context);
                    }
                }
                else if(CheckAds.equals("web")){
                    if(!TextUtils.isEmpty(FB_Int)){
                        if (interstitialAd.isAdLoaded()) {

                            QurekaInterstitialAd = false;

                            FBADSDialog(context);

                        }else {

                            QurekaInterstitialAd = true;

                            //StartAppAd.showAd(context);
                        }
                        FBAdsInitialization(context);
                    } else {
                        QurekaInterstitialAd = true;
                    }
                }
                else if(CheckAds.equals("max")){

                    if(!TextUtils.isEmpty(MAX_Int)) {
                        if (maxInterstitialAd.isReady()) {
                            maxInterstitialAd.showAd();
                        } else {
                            QurekaInterstitialAd = true;
                        }
                    }
                    MAXInitialization(context);

                }

                SplashLaunchActivity.QurekaInterstitialAdAdCall(context);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void QurekaInterstitialAdAdCall(Context context) {

        if(MyOneApplication.getuser_balance() == 0){

            if(SplashLaunchActivity.QurekaInterstitialAd == true){

                if(WebAppLinkList.size() >= 1){

                    SplashLaunchActivity.QurekaInterstitialAd = false;

                    final int minR = 1;
                    final int maxR = 2;
                    final int randomR = new Random().nextInt((maxR - minR) + 1) + minR;

                    if(randomR == 2) {

                        final Dialog dialog = new Dialog(context, R.style.DialogTheme);
                        dialog.setContentView(R.layout.qureka_interstial_ads);
                        dialog.setCancelable(false);

                        ImageView QurekaImgBlur = (ImageView) dialog.findViewById(R.id.imgblur);
                        ImageView QurekaImgMain = (ImageView) dialog.findViewById(R.id.imgqint);
                        RelativeLayout QurekaClose = (RelativeLayout) dialog.findViewById(R.id.qintclose);
                        LinearLayout QMainClick = (LinearLayout) dialog.findViewById(R.id.qmainclick);

                        RelativeLayout QTop = (RelativeLayout) dialog.findViewById(R.id.qtop);
                        RelativeLayout QBottom = (RelativeLayout) dialog.findViewById(R.id.qbottom);

                        Animation animation = AnimationUtils.loadAnimation(context,
                                R.anim.slide_down);
                        QTop.setAnimation(animation);

                        Animation animation1 = AnimationUtils.loadAnimation(context,
                                R.anim.slide_up);
                        QBottom.setAnimation(animation1);

                        QurekaClose.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(QurekaAds));
                                intent.setPackage("com.android.chrome");
                                context.startActivity(intent);

                                dialog.dismiss();
                            }
                        });

                        try {
                            final int min = 10;
                            final int max = 14;
                            final int random = new Random().nextInt((max - min) + 1) + min;

                            //Log.e("TAG", "QurekaInterstitialAdAdCall123: " + WebAppIconList.get(random) );

                            Glide.with(context)
                                    .load(WebAppIconList.get(random))
                                    .into(QurekaImgBlur);

                            Glide.with(context)
                                    .load(WebAppIconList.get(random))
                                    .into(QurekaImgMain);

                            QMainClick.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(WebAppLinkList.get(random)));
                                    intent.setPackage("com.android.chrome");
                                    context.startActivity(intent);

                                    dialog.dismiss();
                                }
                            });

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        dialog.show();

                    } else {

                        final Dialog dialog = new Dialog(context, R.style.DialogTheme);
                        dialog.setContentView(R.layout.qureka_interstial_ads2);
                        dialog.setCancelable(false);


                        ImageView QurekaImgMain = (ImageView) dialog.findViewById(R.id.imgqint);
                        RelativeLayout QurekaClose = (RelativeLayout) dialog.findViewById(R.id.qintclose);
                        LinearLayout QMainClick = (LinearLayout) dialog.findViewById(R.id.qmainclick);

                        RelativeLayout QTop = (RelativeLayout) dialog.findViewById(R.id.qtop);
                        RelativeLayout QBottom = (RelativeLayout) dialog.findViewById(R.id.qbottom);

                        Animation animation1 = AnimationUtils.loadAnimation(context,
                                R.anim.slide_up);
                        QBottom.setAnimation(animation1);

                        QurekaClose.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View view) {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(QurekaAds));
                                intent.setPackage("com.android.chrome");
                                context.startActivity(intent);

                                dialog.dismiss();
                            }
                        });

                        try{
                            final int min = 10;
                            final int max = 14;
                            final int random = new Random().nextInt((max - min) + 1) + min;

                            //Log.e("TAG", "QurekaInterstitialAdAdCall123: " + WebAppIconList.get(random) );

                            Glide.with(context)
                                    .load(WebAppIconList.get(random))
                                    .into(QurekaImgMain);

                            QMainClick.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(WebAppLinkList.get(random)));
                                    intent.setPackage("com.android.chrome");
                                    context.startActivity(intent);

                                    dialog.dismiss();
                                }
                            });

                        }catch (Exception e) {
                            e.printStackTrace();
                        }

                        dialog.show();

                    }

                }

            }

        }

    }

    //Mix Native Ads Code
    public static void MixNativeAdsCall(Activity activity, ShimmerFrameLayout shimmerFrameLayout, FrameLayout frameLayout, ImageView image, CardView qurekanative, RelativeLayout nativeAdContainer, RelativeLayout nativeAdsStartApp, RecyclerView nativeMoPub, FrameLayout nativeMax) {

        qurekanative.setVisibility(View.GONE);

        try {
            if(MyOneApplication.getuser_balance() == 0){

                if(CheckAds.equals("fb")) {
                    if (CheckFBAdMobNative == true) {
                        NativeAdsAdMob(activity, frameLayout, shimmerFrameLayout, image, qurekanative);
                    }else {
                        NativeAdsFB(activity, nativeAdContainer, nativeAdsStartApp, shimmerFrameLayout, image, qurekanative);
                    }
                }else if(CheckAds.equals("admob")){
                    NativeAdsAdMob(activity, frameLayout, shimmerFrameLayout, image, qurekanative);
                }else if(CheckAds.equals("web")){
                    if(!TextUtils.isEmpty(AdMob_NativeAdvance)) {
                        NativeAdsAdMob(activity, frameLayout, shimmerFrameLayout, image, qurekanative);
                    }else if(!TextUtils.isEmpty(FB_Native)){
                        NativeAdsFB(activity, nativeAdContainer, nativeAdsStartApp, shimmerFrameLayout, image, qurekanative);
                    }else {
                        NativeAdsWeb(activity, nativeAdContainer, nativeAdsStartApp, shimmerFrameLayout, image, qurekanative);
                    }
                }else if(CheckAds.equals("startapp")){
                    NativeStartApp(activity, image, nativeAdsStartApp);
                }else if(CheckAds.equals("max")){
                    if(!TextUtils.isEmpty(AdMob_NativeAdvance)) {
                        NativeAdsAdMob(activity, frameLayout, shimmerFrameLayout, image, qurekanative);
                    }else if(!TextUtils.isEmpty(FB_Native)){
                        NativeAdsFB(activity, nativeAdContainer, nativeAdsStartApp, shimmerFrameLayout, image, qurekanative);
                    }else {
                        NativeAdsMAX(activity, nativeAdContainer, nativeAdsStartApp, shimmerFrameLayout, image, qurekanative, nativeMax);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void MixNativeAdsCallBlack(Activity activity, ShimmerFrameLayout shimmerFrameLayout, FrameLayout frameLayout, ImageView image, CardView qurekanative, RelativeLayout nativeAdContainer, RelativeLayout nativeAdsStartApp, RecyclerView nativeMoPub, FrameLayout nativeMax) {

        qurekanative.setVisibility(View.GONE);

        try {
            if(MyOneApplication.getuser_balance() == 0){

                if(CheckAds.equals("fb")) {
                    if (CheckFBAdMobNative == true) {
                        NativeAdsAdMobBlack(activity, frameLayout, shimmerFrameLayout, image, qurekanative);
                    }else {
                        NativeAdsFB(activity, nativeAdContainer, nativeAdsStartApp, shimmerFrameLayout, image, qurekanative);
                    }
                }else if(CheckAds.equals("admob")){
                    NativeAdsAdMobBlack(activity, frameLayout, shimmerFrameLayout, image, qurekanative);
                }else if(CheckAds.equals("web")){
                    if(!TextUtils.isEmpty(AdMob_NativeAdvance)) {
                        NativeAdsAdMobBlack(activity, frameLayout, shimmerFrameLayout, image, qurekanative);
                    }else if(!TextUtils.isEmpty(FB_Native)){
                        NativeAdsFB(activity, nativeAdContainer, nativeAdsStartApp, shimmerFrameLayout, image, qurekanative);
                    }else {
                        NativeAdsWeb(activity, nativeAdContainer, nativeAdsStartApp, shimmerFrameLayout, image, qurekanative);
                    }
                }else if(CheckAds.equals("startapp")){
                    NativeStartApp(activity, image, nativeAdsStartApp);
                }else if(CheckAds.equals("max")){
                    if(!TextUtils.isEmpty(AdMob_NativeAdvance)) {
                        NativeAdsAdMob(activity, frameLayout, shimmerFrameLayout, image, qurekanative);
                    }else if(!TextUtils.isEmpty(FB_Native)){
                        NativeAdsFB(activity, nativeAdContainer, nativeAdsStartApp, shimmerFrameLayout, image, qurekanative);
                    }else {
                        NativeAdsMAX(activity, nativeAdContainer, nativeAdsStartApp, shimmerFrameLayout, image, qurekanative, nativeMax);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //Mix Banner Ads Code
    public static void MixBannerAdsCall(Activity context, RelativeLayout adContainer, RelativeLayout adContainer2, ImageView ownBannerAds) {

        try {
            if(MyOneApplication.getuser_balance() == 0){

                if (CheckAds.equals("fb")) {
                    FbBanner(context, adContainer, adContainer2, ownBannerAds);
                } else if (CheckAds.equals("admob")) {
                    AdMobBanner(context, adContainer);
                } else if (CheckAds.equals("web")) {
                    WebBanner(context, ownBannerAds);
                } else if(CheckAds.equals("startapp")){
                    StartAppBanner(context, adContainer, adContainer2, ownBannerAds);
                } else if(CheckAds.equals("max")){
                    MAXBanner(context, adContainer, adContainer2, ownBannerAds);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private class PurchaseListener extends EmptyRequestListener<Purchase> {
        @Override
        public void onSuccess(Purchase purchase) {
            MyOneApplication.setuser_balance(1);
        }

        @Override
        public void onError(int response, Exception e) {
            MyOneApplication.setuser_balance(0);
        }
    }

    private class InventoryCallback implements Inventory.Callback {
        @Override
        public void onLoaded(Inventory.Products products) {
            // your code here
        }
    }

    @Override
    protected void onDestroy() {
        mCheckout.stop();
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCheckout.onActivityResult(requestCode, resultCode, data);
    }

    public static void getPermission(Activity context) {

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                context.requestPermissions(new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                }, 0);
            }
        }

    }

    public static void createFolder() {
        File file  = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + File.separator + "Quotes Status Ringtone");
        File file0 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + File.separator + "Quotes Status Ringtone/Quotes Images");
        File file1 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + File.separator + "Quotes Status Ringtone/Wallpaper");
        File file2 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + File.separator + "Quotes Status Ringtone/Video Status");
        File file3 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + File.separator + "Quotes Status Ringtone/Ringtones");
        if (file.mkdir()) {
            //System.out.println("Directory created");
        }
        if (file0.mkdir()) {
            //System.out.println("Directory created");
        }
        if (file1.mkdir()) {
            //System.out.println("Directory created");
        }
        if (file2.mkdir()) {
            //System.out.println("Directory created");
        }
        if (file3.mkdir()) {
            //System.out.println("Directory created");
        }
    }

}

