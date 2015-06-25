package ba.hera.praksa;

import android.os.Bundle;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.app.ActionBar;
import android.view.Window;
import android.widget.Toast;

public class GrafActivity extends FragmentActivity implements ActionBar.TabListener {

    private ViewPager viewPager;
    private ActionBar actionBar;
    private TabsFragmentPagerAdapter tabsAdapter;
    private String[] days = new String[]{"Risk statistics","Heatmaps by aspects","Risks by Aspects"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            //getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
            requestWindowFeature(Window.FEATURE_ACTION_BAR);
            setContentView(R.layout.activity_graf);
            viewPager = (ViewPager) findViewById(R.id.viewPager);
            tabsAdapter = new TabsFragmentPagerAdapter(getSupportFragmentManager());
            viewPager.setAdapter(tabsAdapter);
            actionBar = getActionBar();
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
            for(int i=0; i<3; i++)
            {
                actionBar.addTab(actionBar.newTab().setText(days[i]).setTabListener(this));
            }
        }
        catch (Exception ex)
        {
            Toast.makeText(this,ex.toString(), Toast.LENGTH_LONG).show();
        }

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg) {
                actionBar.setSelectedNavigationItem(arg);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_graf, menu);
        return true;
    }

    @Override
    public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onTabSelected(Tab tab, FragmentTransaction arg1) {
        // TODO Auto-generated method stub
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
        // TODO Auto-generated method stub

    }

}
