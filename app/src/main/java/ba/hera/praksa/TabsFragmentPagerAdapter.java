package ba.hera.praksa;

/**
 * Created by Benjamin on 16.6.2015.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


public class TabsFragmentPagerAdapter extends FragmentPagerAdapter {

    public TabsFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {
        if(index == 0)
            return new Tab1();
        if(index == 1)
            return new Tab2();
        if(index == 2)
            return new Tab3();


        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}

