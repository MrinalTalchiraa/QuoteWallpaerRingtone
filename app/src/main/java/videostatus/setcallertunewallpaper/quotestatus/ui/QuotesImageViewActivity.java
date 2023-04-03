package videostatus.setcallertunewallpaper.quotestatus.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;

import com.facebook.shimmer.ShimmerFrameLayout;



import videostatus.setcallertunewallpaper.CheckInternet;
import videostatus.setcallertunewallpaper.R;
import videostatus.setcallertunewallpaper.SplashLaunchActivity;
import videostatus.setcallertunewallpaper.quotestatus.fragment.QuotesImagesFragment;
import videostatus.setcallertunewallpaper.quotestatus.model.QuotesImageData;
import videostatus.setcallertunewallpaper.wallpaper.ui.WallpaperViewActivity;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class QuotesImageViewActivity extends AppCompatActivity {

    ProgressDialog pDialog;

    int action = 0;

    private AsyncTask mMyTask;

    String DownloadLink;

    ProgressDialog mProgressDialog;

    Context mContext;

    ImageView Download, WhatsApp, Share;

    //set save file location example: .getAbsolutePath() + "/Pictures");
    boolean success = false;

    protected int curruntPosition;
    protected int hh;

    int position;
    String ImageLink;
    LinearLayout backarrow;
    String name;

    TextView titleText;

    LottieAnimationView loading;

    boolean isCheckDownload = false;
    String DownloadPath;

    ViewPager viewPager;
    ImagePagerAdapter adapter;

    List<QuotesImageData> mlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotes_images_view);


        //fb ads call
        SplashLaunchActivity.FBInterstitialAdCall(this);


        //Mix Banner Ads Call
        RelativeLayout adContainer = (RelativeLayout) findViewById(R.id.btm10);
        RelativeLayout adContainer2 = (RelativeLayout) findViewById(R.id.ads2);
        ImageView OwnBannerAds = (ImageView) findViewById(R.id.bannerads);
        SplashLaunchActivity.MixBannerAdsCall(this, adContainer, adContainer2, OwnBannerAds);


        titleText = (TextView) findViewById(R.id.toolbar_title);
        titleText.setText(QuotesImagesFragment.Catename);

        backarrow = (LinearLayout) findViewById(R.id.menu);

        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();

            }
        });

        mlist = new ArrayList<>();

        try {
            Bundle bundle = getIntent().getExtras();
            mlist = (ArrayList<QuotesImageData>) bundle.getSerializable("arraylist");
            ImageLink = getIntent().getStringExtra("imagelink");
            position = getIntent().getIntExtra("position", 0);

        } catch (Exception e) {
            e.printStackTrace();
        }

        name = QuotesImagesFragment.Catename;

        loading = (LottieAnimationView) findViewById(R.id.img_internet);
        loading.setVisibility(View.VISIBLE);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);


        Download = (ImageView) findViewById(R.id.llDownload);
        WhatsApp = (ImageView) findViewById(R.id.llWhatsApp);
        Share = (ImageView) findViewById(R.id.llShare);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        adapter = new ImagePagerAdapter();
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(position);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                // TODO Auto-generated method stub
                // Here you can set the wallpaper
                curruntPosition = arg0;
                if (curruntPosition == arg0) {
                    hh = 1;
                    isCheckDownload = false;
                }

            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub

            }
        });


        Download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int currentItem = viewPager.getCurrentItem();

                //String link11 = WallpaperListAdapter.mWallpaperList.get(currentItem);
                String link11 = mlist.get(currentItem).getUri();

                DownloadLink = link11;
                action = 0;

                if (isCheckDownload == false) {
                    new DownloadTask(QuotesImageViewActivity.this).execute(new String[]{link11});
                } else {
                    Toast.makeText(QuotesImageViewActivity.this, "Image Already Downloaded", Toast.LENGTH_SHORT).show();
                }


            }
        });

        WhatsApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                int currentItem = viewPager.getCurrentItem();

                //String link22 = WallpaperListAdapter.mWallpaperList.get(currentItem);
                String link22 = mlist.get(currentItem).getUri();

                action = 1;

                if (isCheckDownload == false) {
                    new DownloadTask(QuotesImageViewActivity.this).execute(new String[]{link22});
                } else {

                    DownloadedImageWhatsAppShare();
                }


                //new WhatsAppShare(WallpaperViewActivity.this).execute(new String[]{link22});

            }
        });

        Share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int currentItem = viewPager.getCurrentItem();

                //String link33 = WallpaperListAdapter.mWallpaperList.get(currentItem);
                String link33 = mlist.get(currentItem).getUri();

                action = 2;

                if (isCheckDownload == false) {
                    new DownloadTask(QuotesImageViewActivity.this).execute(new String[]{link33});
                } else {

                    DownloadedImageShare();
                }

            }
        });

    }

    public class ImagePagerAdapter extends PagerAdapter {
        // protected int[] mImages = MainActivity.ICONS;
        /// int[] icons = MainActivity.ICONS;

        @Override
        public int getCount() {

            return mlist.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((ImageView) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Context context = QuotesImageViewActivity.this;
            ImageView imageView = new ImageView(context);
            // int padding = context.getResources().getDimensionPixelSize(
            // R.dimen.padding_large);
            // imageView.setPadding(padding, padding, padding, padding);
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);

            Glide.with(QuotesImageViewActivity.this)
                    .load(mlist.get(position).getUri())
                    .into(imageView);
            ((ViewPager) container).addView(imageView, 0);

            //notifyDataSetChanged();

            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

            ((ViewPager) container).removeView((ImageView) object);
        }
    }

    public class DownloadTask extends AsyncTask<String, String, String> {
        private Context context33;
        StringBuilder sb2;

        public DownloadTask(Context context2) {
            this.context33 = context2;
        }

        public void onPreExecute() {
            super.onPreExecute();

            //loading.setVisibility(View.GONE);

            pDialog.setCancelable(false);
            pDialog.show();
            pDialog.setContentView(R.layout.download_dialog);


            //Mix Native Ads Call
            ShimmerFrameLayout shimmerFrameLayout = (ShimmerFrameLayout) pDialog.findViewById(R.id.shimmer_300);
            FrameLayout frameLayout = pDialog.findViewById(R.id.fl_adplaceholder);
            ImageView image = (ImageView) pDialog.findViewById(R.id.banner_image);
            CardView qurekanative = (CardView) pDialog.findViewById(R.id.qurekanative);
            RelativeLayout NativeAdContainer = (RelativeLayout) pDialog.findViewById(R.id.nativeAds);
            RelativeLayout NativeAdsStartApp = (RelativeLayout) pDialog.findViewById(R.id.sNativeAds);
            RecyclerView nativeMoPub = (RecyclerView) pDialog.findViewById(R.id.nativemopub);
            FrameLayout maxNative = (FrameLayout) pDialog.findViewById(R.id.max_native_ad_layout);
            SplashLaunchActivity.MixNativeAdsCall(QuotesImageViewActivity.this, shimmerFrameLayout, frameLayout, image, qurekanative, NativeAdContainer, NativeAdsStartApp, nativeMoPub, maxNative);

            //downloading.setVisibility(View.VISIBLE);

        }

        public String doInBackground(String... args) {

            String dateStr = "04/05/2010";

            SimpleDateFormat curFormater = new SimpleDateFormat("dd/MM/yyyy");
            Date dateObj = null;
            try {
                dateObj = curFormater.parse(dateStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            SimpleDateFormat postFormater = new SimpleDateFormat("MMddyyyy");

            String newDateStr = postFormater.format(dateObj);

            sb2 = new StringBuilder();
            sb2.append(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + File.separator + "Quotes Status Ringtone/Quotes Images");
            sb2.append(File.separator);
            sb2.append(name + " " + mlist.get(position).getName() + newDateStr + SystemClock.uptimeMillis());
            sb2.append(".jpg");

            try {
                URL u = new URL(args[0]);
                URLConnection conn = u.openConnection();
                int contentLength = conn.getContentLength();

                DataInputStream stream = new DataInputStream(u.openStream());

                byte[] buffer = new byte[contentLength];
                stream.readFully(buffer);
                stream.close();
                DownloadPath = String.valueOf(sb2);
                DataOutputStream fos = new DataOutputStream(new FileOutputStream(String.valueOf(sb2)));
                fos.write(buffer);
                fos.flush();
                fos.close();
            } catch (FileNotFoundException e) {
                return null; // swallow a 404
            } catch (IOException e) {
                return null; // swallow a 404
            }

            return null;
        }

        public void onPostExecute(String args) {

            isCheckDownload = true;

            if (action == 0) {

                //loading.setVisibility(View.GONE);
                pDialog.dismiss();

                CallAct1();

            } else if (action == 1) {

                pDialog.dismiss();

                File file123 = new File(DownloadPath);
                Uri imageConvertToUri = FileProvider.getUriForFile(QuotesImageViewActivity.this,
                        getPackageName() + ".provider",
                        file123);

                String appName = getResources().getString(R.string.app_name);
                final String appPackageName = getPackageName();
                Intent share = new Intent("android.intent.action.SEND");
                share.setType("image/jpeg");
                share.setPackage("com.whatsapp");
                share.putExtra(Intent.EXTRA_TEXT, appName + " \n : https://play.google.com/store/apps/details?id=" + appPackageName);
                share.putExtra("android.intent.extra.STREAM", imageConvertToUri);
                share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivity(share);


            } else if (action == 2) {

                pDialog.dismiss();

                File file123 = new File(DownloadPath);
                Uri imageConvertToUri = FileProvider.getUriForFile(QuotesImageViewActivity.this,
                        getPackageName() + ".provider",
                        file123);

                String appName = getResources().getString(R.string.app_name);
                final String appPackageName = getPackageName();
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_STREAM, imageConvertToUri);
                shareIntent.putExtra(Intent.EXTRA_TEXT, appName + " \n : https://play.google.com/store/apps/details?id=" + appPackageName);
                shareIntent.setType("image/*");
                startActivity(Intent.createChooser(shareIntent, "Share Image"));


            }

        }
    }

    private void DownloadedImageShare() {

        File file123 = new File(DownloadPath);
        Uri imageConvertToUri = FileProvider.getUriForFile(QuotesImageViewActivity.this,
                getPackageName() + ".provider",
                file123);

        String appName = getResources().getString(R.string.app_name);
        final String appPackageName = getPackageName();
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, imageConvertToUri);
        shareIntent.putExtra(Intent.EXTRA_TEXT, appName + " \n : https://play.google.com/store/apps/details?id=" + appPackageName);
        shareIntent.setType("image/*");
        startActivity(Intent.createChooser(shareIntent, "Share Image"));

    }

    private void DownloadedImageWhatsAppShare() {

        File file123 = new File(DownloadPath);
        Uri imageConvertToUri = FileProvider.getUriForFile(QuotesImageViewActivity.this,
                getPackageName() + ".provider",
                file123);

        String appName = getResources().getString(R.string.app_name);
        final String appPackageName = getPackageName();
        Intent share = new Intent("android.intent.action.SEND");
        share.setType("image/jpeg");
        share.setPackage("com.whatsapp");
        share.putExtra(Intent.EXTRA_TEXT, appName + " \n : https://play.google.com/store/apps/details?id=" + appPackageName);
        share.putExtra("android.intent.extra.STREAM", imageConvertToUri);
        share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(share);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }


    private void CallAct1() {
        Intent intent = null;
        SplashLaunchActivity.InterstitialAdsCall(this, intent);
        Toast.makeText(QuotesImageViewActivity.this, "Image Download Successfully", Toast.LENGTH_SHORT).show();
    }

}

