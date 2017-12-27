package ph.gov.sarangani.sarbayforever.adapters;

import ph.gov.sarangani.sarbayforever.LayoutOne;
import ph.gov.sarangani.sarbayforever.LayoutTwo;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class ViewPager_Adapter extends FragmentStatePagerAdapter {

	public ViewPager_Adapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}

	public static int totalPage = 2;

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return totalPage;
	}

	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub

		switch (arg0) {
		case 0: {

			return new LayoutOne();
		}

		case 1: {
			return new LayoutTwo();
		}

		}
		return null;
	}

}
