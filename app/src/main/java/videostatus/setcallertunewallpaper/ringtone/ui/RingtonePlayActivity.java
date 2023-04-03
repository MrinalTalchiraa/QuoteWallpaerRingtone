package videostatus.setcallertunewallpaper.ringtone.ui;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import videostatus.setcallertunewallpaper.CheckInternet;
import videostatus.setcallertunewallpaper.R;
import videostatus.setcallertunewallpaper.SplashLaunchActivity;
import videostatus.setcallertunewallpaper.ringtone.model.RingtoneData;

public class RingtonePlayActivity extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    ProgressDialog pDialog1;

    //public static ProgressDialog pDialog;

    MediaPlayer mediaPlayer;
    TextView tv_CurrentDuration, tv_TotalDuration, tv_tuneName;
    ImageView iv_Previous, iv_PlayPuase, iv_Next;
    SeekBar seekBar;
    ArrayList<RingtoneData> ringModels;
    int position;
    boolean blOffline = false;
    //    HorizontalInfiniteCycleViewPager viewPager;
//    ViewpagerAdapter viewpagerAdapter;
    int ii = 0;
    Timer timer;
    //ImageView ivFavouriteTune;
    boolean blFavorite = true;
    //FavouriteDBManager favouriteDBManager;

    TextView titleText;
    ImageView DownloadImg;

    File outputFileMain;
    Boolean isDownload = false;

    ImageView Back;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);
        // favouriteDBManager=new FavouriteDBManager(this);
        // favouriteDBManager.open();
        getWindow().setFlags(1024, 1024);


        //fb ads call
        SplashLaunchActivity.FBInterstitialAdCall(this);


        //Mix Banner Ads Call
        RelativeLayout adContainer = (RelativeLayout) findViewById(R.id.btm10);
        RelativeLayout adContainer2 = (RelativeLayout) findViewById(R.id.ads2);
        ImageView OwnBannerAds = (ImageView) findViewById(R.id.bannerads);
        SplashLaunchActivity.MixBannerAdsCall(this, adContainer, adContainer2, OwnBannerAds);


        Back = (ImageView) findViewById(R.id.back);
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        pDialog1 = new ProgressDialog(this);
        pDialog1.setCancelable(false);


        DownloadImg = (ImageView) findViewById(R.id.downloadImg);
        DownloadImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isDownload == true) {
                    Toast.makeText(RingtonePlayActivity.this, "Already Downloaded", Toast.LENGTH_SHORT).show();
                } else {
                    new DownloadVideo().execute("home");
                }
            }
        });

        iniViewBound();

        try {
            Bundle bundle = getIntent().getExtras();
            ringModels = (ArrayList<RingtoneData>) bundle.getSerializable("arraylist");
            position = getIntent().getIntExtra("position", 0);
            blOffline = getIntent().getBooleanExtra("offline", false);
//            Log.d("skjgposition1", position + "");
        } catch (Exception e) {
            e.printStackTrace();
        }

        titleText = (TextView) findViewById(R.id.titletext);
        titleText.setText(RingtoneCallerCategoryListActivity.Catename);

       /* if(blOffline){
            ivFavouriteTune.setVisibility(View.GONE);
        }
        else{
            ivFavouriteTune.setVisibility(View.VISIBLE);
        }*/
        if (ringModels != null) {
            setMediaPlayer();

        } else {
            Toast.makeText(this, "Url Null.", Toast.LENGTH_SHORT).show();
        }

//        viewpagerAdapter=new ViewpagerAdapter(this);
//        viewPager.setAdapter(viewpagerAdapter);
//        viewPager.startAutoScroll(true);


    }

    private void setMediaPlayer() {

        mediaPlayer = new MediaPlayer();
        tv_tuneName.setText(ringModels.get(position).getName());

        try {
            if (ringModels.get(position).getUri() != null) {
                mediaPlayer.setDataSource(ringModels.get(position).getUri());
            } else {
                Toast.makeText(this, "Url not Found.", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        new AsyncTask<Void, Void, Void>() {

            Dialog progressDialog;
            private int mDurationTime;
            String mRemainingTimeStr;

            @Override
            protected Void doInBackground(Void... voids) {

                try {

                    mediaPlayer.prepare();
                    mDurationTime = mediaPlayer.getDuration();
                    int remainingSecs = (int) (mDurationTime / 1000);
                    int remainingMins = remainingSecs / 60;
                    remainingSecs = remainingSecs % 60;
                    mRemainingTimeStr = String.format(Locale.getDefault(), "%02d", remainingMins) + ":" + String.format(Locale.getDefault(), "%02d", remainingSecs);

                } catch (Exception e) {
                    progressDialog.dismiss();
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPreExecute() {

                progressDialog = new Dialog(RingtonePlayActivity.this);
                View view = LayoutInflater.from(RingtonePlayActivity.this).inflate(R.layout.loading, null);
                progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                progressDialog.setContentView(view);
                progressDialog.setCancelable(false);
                progressDialog.show();

                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(Void aVoid) {

                tv_TotalDuration.setText(mRemainingTimeStr);
//                start= new Thread(MusicPlayerActivity.this);

                seekBar.setProgress(0);
                seekBar.setMax(mediaPlayer.getDuration());

                updateProgressBar();
                progressDialog.dismiss();
                super.onPostExecute(aVoid);

            }

        }.execute();

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                iv_PlayPuase.setImageDrawable(getResources().getDrawable(R.drawable.pyr_play));
            }
        });

    }

    private void iniViewBound() {
       // ivFavouriteTune = (ImageView) findViewById(R.id.ivFavouriteTune);
//        viewPager =(HorizontalInfiniteCycleViewPager) findViewById(R.id.viewPager);
        tv_CurrentDuration = (TextView) findViewById(R.id.tv_CurrentDuration);
        tv_TotalDuration = (TextView) findViewById(R.id.tv_TotalDuration);
        tv_tuneName = (TextView) findViewById(R.id.tv_tuneName);
        tv_tuneName.setSelected(true);
        iv_Previous = (ImageView) findViewById(R.id.iv_Previous);
        iv_PlayPuase = (ImageView) findViewById(R.id.iv_PlayPuase);
        iv_Next = (ImageView) findViewById(R.id.iv_Next);

        seekBar = (SeekBar) findViewById(R.id.sb_Duration);
        iv_Previous.setOnClickListener(this);
        iv_PlayPuase.setOnClickListener(this);
        iv_Next.setOnClickListener(this);

        seekBar.setOnSeekBarChangeListener(this);/*new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
//                if(b) mediaPlayer.seekTo(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mHandler.removeCallbacks(mUpdateTimeTask);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mHandler.removeCallbacks(mUpdateTimeTask);
                mediaPlayer.seekTo(seekBar.getProgress());
                updateProgressBar();
            }

        });*/

    }

    //    Thread start;
    public void updateProgressBar() {
        mHandler.postDelayed(mUpdateTimeTask, 100);
    }

    private Handler mHandler = new Handler();
    ;
    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            if (mediaPlayer != null) {
                int mDurationTime = mediaPlayer.getCurrentPosition();
                int remainingSecs = (int) (mDurationTime / 1000);
                int remainingMins = remainingSecs / 60;
                remainingSecs = remainingSecs % 60;
                String mRemainingTimeStr = String.format(Locale.getDefault(), "%02d", remainingMins) + ":" + String.format(Locale.getDefault(), "%02d", remainingSecs);

                tv_CurrentDuration.setText(mRemainingTimeStr);
                seekBar.setProgress(mediaPlayer.getCurrentPosition());
                mHandler.postDelayed(this, 100);
            }
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_Previous:
                if (position > 0) {

                    isDownload = false;
                    position = position - 1;
                    if (mediaPlayer != null) {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer = null;
                    }
                    setMediaPlayer();
                    iv_PlayPuase.setImageResource(R.drawable.pyr_play);
                } else {

                    Toast.makeText(this, "It's first ringtone", Toast.LENGTH_SHORT).show();

                }
                break;

            case R.id.iv_Next:
                if (position < ringModels.size() - 1) {

                    isDownload = false;
                    position++;
                    if (mediaPlayer != null) {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer = null;
                    }
                    setMediaPlayer();
                    iv_PlayPuase.setImageResource(R.drawable.pyr_play);
                } else {

                    Toast.makeText(this, "It's last ringtone", Toast.LENGTH_SHORT).show();

                }
                break;

            case R.id.iv_PlayPuase:

                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    iv_PlayPuase.setImageResource(R.drawable.pyr_play);
                } else {
                    try {
                        if (mediaPlayer == null) {
                            return;
                        }
//                        start.start();
                        mediaPlayer.start();
                        iv_PlayPuase.setImageResource(R.drawable.pyr_pause);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

/*
    @Override
    public void run() {

        int currentPosition= 0;
        int total = mediaPlayer.getDuration();
        while (mediaPlayer!=null && currentPosition<total) {
            try {
                Thread.sleep(1000);
                currentPosition= mediaPlayer.getCurrentPosition();
            } catch (InterruptedException e) {
                return;
            } catch (Exception e) {
                return;
            }
            seekBar.setProgress(currentPosition);
        }

    }*/

    @Override
    protected void onPause() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            iv_PlayPuase.setImageResource(R.drawable.pyr_play);
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {

        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }


        super.onDestroy();
    }

    public void onBack(View view) {
        onBackPressed();
    }

    public void option(View view) {
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Settings.System.canWrite(this)) {
                openDialog(position);
            } else {
                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent.setData(Uri.parse("package:" + getPackageName()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }*/
    }

    @SuppressLint("NewApi")
    private void openDialog(final int position) {

        LinearLayout ringtone, alarm, notification;

        final Dialog dialog = new Dialog(RingtonePlayActivity.this, R.style.MyDeleteDialog);
        View view = LayoutInflater.from(RingtonePlayActivity.this).inflate(R.layout.dialog_set_ringtone, null);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(view);

//        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        View view= LayoutInflater.from(this).inflate(R.layout.dialog_set_ringtone,null);
//        dialog.setContentView(view);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        dialog.create();

        ringtone = (LinearLayout) view.findViewById(R.id.set_ringtone);
        alarm = (LinearLayout) view.findViewById(R.id.set_Alarm);
        notification = (LinearLayout) view.findViewById(R.id.set_notification);
        ImageView close = (ImageView) view.findViewById(R.id.iv_Close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        ringtone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTune(position, 1);
                dialog.dismiss();
            }
        });

        alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTune(position, 4);
                dialog.dismiss();
            }
        });

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTune(position, 2);
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    private void setTune(final int position, final int code) {

        if (blOffline) {
            setOffLineTune(ringModels.get(position).getUri(), code);
            return;
        }

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

//        String  path=saveAs(ringModels.get(position).getRingPath(),position);
        File cacheDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + File.separator + "Quotes Status Ringtone/Ringtones" + File.separator);
        if (!cacheDir.exists())
            cacheDir.mkdirs();
        String filename = RingtoneCallerCategoryListActivity.Catename + newDateStr;
        final File f = new File(cacheDir, filename);
        if (f.exists()) {
            f.delete();
        }

     /*   request.setDestinationUri(Uri.fromFile(f));
        downloadID=downloadmanager.enqueue(request);*/

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                try {

                    URL u = new URL(ringModels.get(position).getUri());
                    InputStream is = u.openStream();

                    DataInputStream dis = new DataInputStream(is);

                    byte[] buffer = new byte[1024];
                    int length;

                    FileOutputStream fos = new FileOutputStream(f);
                    while ((length = dis.read(buffer)) > 0) {
                        fos.write(buffer, 0, length);
                    }

                } catch (MalformedURLException mue) {
                    Log.e("SYNC getUpdate", "malformed url error", mue);
                } catch (IOException ioe) {
                    Log.e("SYNC getUpdate", "io error", ioe);
                } catch (SecurityException se) {
                    Log.e("SYNC getUpdate", "security error", se);
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {

                File uri = new File(f.getAbsolutePath());
                File file3 = new File(uri.getAbsolutePath());
                sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.parse(file3.toString())));


                ContentValues contentValues = new ContentValues();
                contentValues.put("_data", file3.getAbsolutePath());
                contentValues.put("title", file3.getName());
                contentValues.put("_size", Integer.valueOf(215454));
                contentValues.put("mime_type", "audio/*");
                contentValues.put("duration", Integer.valueOf(230));
                contentValues.put("is_ringtone", Boolean.valueOf(true));
                contentValues.put("is_notification", Boolean.valueOf(true));
                contentValues.put("is_alarm", Boolean.valueOf(true));
                contentValues.put("is_music", Boolean.valueOf(true));

                Uri contentUriForPath = MediaStore.Audio.Media.getContentUriForPath(file3.getAbsolutePath());
                ContentResolver contentResolver = getContentResolver();
                StringBuilder sb6 = new StringBuilder();
                sb6.append("_data=\"");
                sb6.append(file3.getAbsolutePath());
                sb6.append("\"");
                contentResolver.delete(contentUriForPath, sb6.toString(), null);
                Uri insert = getContentResolver().insert(contentUriForPath, contentValues);
                RingtoneManager.setActualDefaultRingtoneUri(getApplicationContext(), code, insert);
          /*  if(code==4) {
                Settings.System.putString(getContentResolver(), Settings.System.ALARM_ALERT, path);
            }*/
                //RingtoneManager.setActualDefaultRingtoneUri(context, RingtoneManager.TYPE_RINGTONE, uri);
                Toast.makeText(RingtonePlayActivity.this, "Set Successfully.", Toast.LENGTH_LONG).show();


                super.onPostExecute(aVoid);
            }
        }.execute();


    }


    public void setOffLineTune(String path, int code) {
        File uri = new File(path);
        File file3 = new File(uri.getAbsolutePath());
        sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.parse(file3.toString())));

        ContentValues contentValues = new ContentValues();
        contentValues.put("_data", file3.getAbsolutePath());
        contentValues.put("title", file3.getName());
        contentValues.put("_size", Integer.valueOf(215454));
        contentValues.put("mime_type", "audio/*");
        contentValues.put("duration", Integer.valueOf(230));
        contentValues.put("is_ringtone", Boolean.valueOf(true));
        contentValues.put("is_notification", Boolean.valueOf(true));
        contentValues.put("is_alarm", Boolean.valueOf(true));
        contentValues.put("is_music", Boolean.valueOf(true));

        Uri contentUriForPath = MediaStore.Audio.Media.getContentUriForPath(file3.getAbsolutePath());
        ContentResolver contentResolver = getContentResolver();
        StringBuilder sb6 = new StringBuilder();
        sb6.append("_data=\"");
        sb6.append(file3.getAbsolutePath());
        sb6.append("\"");
        contentResolver.delete(contentUriForPath, sb6.toString(), null);
        Uri insert = getContentResolver().insert(contentUriForPath, contentValues);
        RingtoneManager.setActualDefaultRingtoneUri(getApplicationContext(), code, insert);
          /*  if(code==4) {
                Settings.System.putString(getContentResolver(), Settings.System.ALARM_ALERT, path);
            }*/
        //RingtoneManager.setActualDefaultRingtoneUri(context, RingtoneManager.TYPE_RINGTONE, uri);
        Toast.makeText(RingtonePlayActivity.this, "Set Successfully.", Toast.LENGTH_LONG).show();

    }

    public String saveAs(final String resSoundId, final int position) {

      /*  DownloadManager downloadmanager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(resSoundId);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setTitle("Ringtone Download.");
        request.setDescription("Downloading...");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);*/
        File cacheDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/JioRingtone/");
        if (!cacheDir.exists())
            cacheDir.mkdirs();
        String filename = RingtoneCallerCategoryListActivity.Catename + ".mp3";
        final File f = new File(cacheDir, filename);
        if (f.exists()) {
            f.delete();
        }
     /*   request.setDestinationUri(Uri.fromFile(f));
        downloadID=downloadmanager.enqueue(request);*/
        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    URL u = new URL(resSoundId);
                    InputStream is = u.openStream();

                    DataInputStream dis = new DataInputStream(is);

                    byte[] buffer = new byte[1024];
                    int length;

                    FileOutputStream fos = new FileOutputStream(f);
                    while ((length = dis.read(buffer)) > 0) {
                        fos.write(buffer, 0, length);
                    }

                } catch (MalformedURLException mue) {
                    Log.e("SYNC getUpdate", "malformed url error", mue);
                } catch (IOException ioe) {
                    Log.e("SYNC getUpdate", "io error", ioe);
                } catch (SecurityException se) {
                    Log.e("SYNC getUpdate", "security error", se);
                }

                return null;
            }
        }.execute();

        return cacheDir + filename;
    }

   /* private long downloadID;
    private BroadcastReceiver onDownloadComplete = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);

            if (downloadID == id) {

                Toast.makeText(MusicPlayerActivity.this, "Download Completed.", Toast.LENGTH_SHORT).show();
                Log.d("sdgwetjh",""+path);
                File uri=new File(path);
                File file3 = new File(uri.getAbsolutePath());
                sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.parse(file3.toString())));

                ContentValues contentValues = new ContentValues();
                contentValues.put("_data", file3.getAbsolutePath());
                contentValues.put("title", file3.getName());
                contentValues.put("_size", Integer.valueOf(215454));
                contentValues.put("mime_type", "audio/*");
                contentValues.put("duration", Integer.valueOf(230));
                contentValues.put("is_ringtone", Boolean.valueOf(true));
                contentValues.put("is_notification", Boolean.valueOf(true));
                contentValues.put("is_alarm", Boolean.valueOf(true));
                contentValues.put("is_music", Boolean.valueOf(true));

                Uri contentUriForPath = MediaStore.Audio.Media.getContentUriForPath(file3.getAbsolutePath());
                ContentResolver contentResolver = getContentResolver();
                StringBuilder sb6 = new StringBuilder();
                sb6.append("_data=\"");
                sb6.append(file3.getAbsolutePath());
                sb6.append("\"");
                contentResolver.delete(contentUriForPath, sb6.toString(), null);
                Uri insert = getContentResolver().insert(contentUriForPath, contentValues);
                RingtoneManager.setActualDefaultRingtoneUri(getApplicationContext(), code, insert);

          *//*  if(code==4) {
                Settings.System.putString(getContentResolver(), Settings.System.ALARM_ALERT, path);
            }*//*
                //RingtoneManager.setActualDefaultRingtoneUri(context, RingtoneManager.TYPE_RINGTONE, uri);
                Toast.makeText(MusicPlayerActivity.this,"Set Successfully."+code+insert,Toast.LENGTH_LONG).show();
            }
        }
    };
*/


    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        mHandler.removeCallbacks(mUpdateTimeTask);
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        mHandler.removeCallbacks(mUpdateTimeTask);
        mediaPlayer.seekTo(seekBar.getProgress());
        updateProgressBar();
    }

    private class DownloadVideo extends AsyncTask<String, Void, String> {

        StringBuilder sb2;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog1.setCancelable(false);
            pDialog1.show();
            pDialog1.setContentView(R.layout.download_dialog);


            //Mix Native Ads Call
            ShimmerFrameLayout shimmerFrameLayout = (ShimmerFrameLayout) pDialog1.findViewById(R.id.shimmer_300);
            FrameLayout frameLayout = pDialog1.findViewById(R.id.fl_adplaceholder);
            ImageView image = (ImageView) pDialog1.findViewById(R.id.banner_image);
            CardView qurekanative = (CardView) pDialog1.findViewById(R.id.qurekanative);
            RelativeLayout NativeAdContainer = (RelativeLayout) pDialog1.findViewById(R.id.nativeAds);
            RelativeLayout NativeAdsStartApp = (RelativeLayout) pDialog1.findViewById(R.id.sNativeAds);
            RecyclerView nativeMoPub = (RecyclerView) pDialog1.findViewById(R.id.nativemopub);
            FrameLayout maxNative = (FrameLayout) pDialog1.findViewById(R.id.max_native_ad_layout);
            SplashLaunchActivity.MixNativeAdsCall(RingtonePlayActivity.this, shimmerFrameLayout, frameLayout, image, qurekanative, NativeAdContainer, NativeAdsStartApp, nativeMoPub, maxNative);

        }

        @Override
        public String doInBackground(String... strings) {


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
            sb2.append(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + File.separator + "Quotes Status Ringtone/Ringtones");
            sb2.append(File.separator + RingtoneCallerCategoryListActivity.Catename + " " + ringModels.get(position).getName() + newDateStr + SystemClock.uptimeMillis());
            sb2.append(".mp3");

            try {
                URL u = new URL(ringModels.get(position).getUri());
                URLConnection conn = u.openConnection();
                int contentLength = conn.getContentLength();

                DataInputStream stream = new DataInputStream(u.openStream());

                byte[] buffer = new byte[contentLength];
                stream.readFully(buffer);
                stream.close();

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

        @SuppressLint("LongLogTag")
        public void onPostExecute(String result) {

            isDownload = true;
            pDialog1.dismiss();
            Toast.makeText(RingtonePlayActivity.this, "Ringtone Download Successfully", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }
}
