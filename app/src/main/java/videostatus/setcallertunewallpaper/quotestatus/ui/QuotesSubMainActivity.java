package videostatus.setcallertunewallpaper.quotestatus.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.kekstudio.dachshundtablayout.DachshundTabLayout;

import videostatus.setcallertunewallpaper.R;
import videostatus.setcallertunewallpaper.quotestatus.fragment.QuotesImagesFragment;
import videostatus.setcallertunewallpaper.quotestatus.fragment.QuotesTextFragment;

import java.util.ArrayList;
import java.util.List;

public class QuotesSubMainActivity extends AppCompatActivity {


    //FrameLayout simpleFrameLayout;
    TabLayout tabLayout;
    Fragment fragment = null;
    LinearLayout backarrow;

    private DachshundTabLayout mTabLayout;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotes_sub_main);


        this.mTabLayout = (DachshundTabLayout) findViewById(R.id.tabs);
        setRequestedOrientation(1);
        this.mViewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(this.mViewPager);

        //goToSecondFragment();

        //simpleFrameLayout = (FrameLayout) findViewById(R.id.simpleFrameLayout);
        backarrow = (LinearLayout) findViewById(R.id.menu);

        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();

            }
        });


    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {
        List<Fragment> mFragmentList = new ArrayList();
        List<String> mFragmentTitleList = new ArrayList();

        public void addFragment(Fragment fragment, String str) {
            this.mFragmentList.add(fragment);
            this.mFragmentTitleList.add(str);
        }

        public ViewPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        public Fragment getItem(int i) {
            return (Fragment) this.mFragmentList.get(i);
        }

        public int getCount() {
            return this.mFragmentList.size();
        }

        public CharSequence getPageTitle(int i) {
            return (CharSequence) this.mFragmentTitleList.get(i);
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new QuotesImagesFragment(), "Images");
        viewPagerAdapter.addFragment(new QuotesTextFragment(), "Text");
        viewPager.setAdapter(viewPagerAdapter);
        this.mTabLayout.setupWithViewPager(viewPager);
    }


   /* private void goToSecondFragment() {
        fragment = new QuotesImagesFragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.simpleFrameLayout, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();
    }*/

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }


}
