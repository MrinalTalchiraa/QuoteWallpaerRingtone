package videostatus.setcallertunewallpaper;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import android.content.pm.Signature;
import android.os.StrictMode;
import android.text.TextUtils;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;


import org.solovyev.android.checkout.Billing;


public class MyOneApplication extends Application {

    private static AppOpenManager appOpenManager;

    public static SharedPreferences sharedPreferencesInApp;
    public static SharedPreferences.Editor editorInApp;

    public static final String TAG = MyOneApplication.class.getSimpleName();
    private static MyOneApplication instance;

    public void onCreate() {
        Signature[] signatureArr;
        super.onCreate();

        MobileAds.initialize(
                this,
                new OnInitializationCompleteListener() {
                    @Override
                    public void onInitializationComplete(InitializationStatus initializationStatus) {}
                });

        appOpenManager = new AppOpenManager(this);

        sharedPreferencesInApp = getSharedPreferences("my", MODE_PRIVATE);
        editorInApp = sharedPreferencesInApp.edit();

        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().build());
        instance = this;

    }

    public static void setuser_balance(Integer user_balance) {
        editorInApp.putInt("user_balance", user_balance).commit();
    }
    public static Integer getuser_balance() {
        return sharedPreferencesInApp.getInt("user_balance", 0);
    }

    public static void setuser_onetime(Integer user_onetime) {
        editorInApp.putInt("user_onetime", user_onetime).commit();
    }
    public static Integer getuser_onetime() {
        return sharedPreferencesInApp.getInt("user_onetime", 0);
    }

    private final Billing mBilling = new Billing(this, new Billing.DefaultConfiguration() {
        @Override
        public String getPublicKey() {

            // LICENSE_KEY
            final String LICENSE_KEY = "";
            return Encryption.decrypt(LICENSE_KEY, "se.solovyev@gmail.com");

        }
    });

    public static MyOneApplication get(Activity activity) {
        return (MyOneApplication) activity.getApplication();
    }

    public Billing getBilling() {
        return mBilling;
    }

    public static synchronized MyOneApplication getInstance() {
        MyOneApplication app;
        synchronized (MyOneApplication.class) {
            app = instance;
        }
        return app;
    }

}
