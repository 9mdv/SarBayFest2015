package ph.gov.sarangani.sarbayforever.adapters;

import java.util.ArrayList;

import ph.gov.sarangani.sarbayforever.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;



public class IpcamList_Adapter extends ArrayAdapter<Channel> {

	ArrayList<Channel> origList = new ArrayList<Channel>();
	ArrayList<Channel> channelList = new ArrayList<Channel>();

	public IpcamList_Adapter(Context context, ArrayList<Channel> list) {
		super(context, 0, list);
		// TODO Auto-generated constructor stub

		this.origList.addAll(list);
		this.channelList.addAll(list);
	}

	public class ViewHolder {

		TextView channel;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		ViewHolder holder = null;

		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(
					R.layout.list_video, parent, false);

			holder = new ViewHolder();

			holder.channel = (TextView) convertView
					.findViewById(R.id.tvList_FestInfo);

			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		Channel adapter = channelList.get(position);

		
		holder.channel.setText(adapter.getChannel());
		

		return convertView;
	}


	

}
