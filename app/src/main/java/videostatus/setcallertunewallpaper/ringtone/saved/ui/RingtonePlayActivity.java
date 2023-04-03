package videostatus.setcallertunewallpaper.ringtone.saved.ui;

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
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Timer;

import androidx.appcompat.app.AppCompatActivity;
import videostatus.setcallertunewallpaper.CheckInternet;
import videostatus.setcallertunewallpaper.R;
import videostatus.setcallertunewallpaper.SplashLaunchActivity;
import videostatus.setcallertunewallpaper.ringtone.saved.model.RingtoneData;

public class RingtonePlayActivity extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    public static ProgressDialog pDialog;

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
  //  ImageView ivFavouriteTune;
    boolean blFavorite = true;
    //FavouriteDBManager favouriteDBManager;

    TextView titleText;


    File outputFileMain;
    Boolean isDownload = false;

    ImageView Back;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player_saved);
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


        pDialog = new ProgressDialog(this);

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
        //titleText.setText(RingtoneSavedListActivity.Catename);

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
      //  ivFavouriteTune = (ImageView) findViewById(R.id.ivFavouriteTune);
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Settings.System.canWrite(this)) {
                openDialog(position);
            } else {
                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent.setData(Uri.parse("package:" + getPackageName()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }
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

        final File f = new File(ringModels.get(position).getUri());
        if (f.exists()) {
            f.delete();
        }

     /*   request.setDestinationUri(Uri.fromFile(f));
        downloadID=downloadmanager.enqueue(request);*/

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }
}
