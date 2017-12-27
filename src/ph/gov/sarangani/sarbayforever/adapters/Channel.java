package ph.gov.sarangani.sarbayforever.adapters;

public class Channel {

	String id;
	String channel;
	String peopleNo;
	String nName;
	String email;
	String bdate;
	String gender;
	String image;
	int icon;

	public Channel() {
		super();
	}

	public Channel(String channel) {

		this.channel = channel;
		this.nName = channel;

	}
	
	public Channel(String channel,int icon) {

		this.channel = channel;
		this.nName = channel;
		this.icon = icon;

	}

	public Channel(String id, String channel, String no) {
		this.id = id;
		this.channel = channel;
		this.peopleNo = no;

	}

	public Channel(String name, String email, String gender, String bdate,
			String image) {

		this.nName = name;
		this.email = email;
		this.gender = gender;
		this.bdate = bdate;
		this.image = image;
	}

	public String getId() {
		return this.id;
	}

	public String getChannel() {
		return this.channel;
	}

	public String getPeopleNo() {
		return this.peopleNo;
	}
	
	public String getNName() {
		return this.nName;
	}
	public int getIcon() {
		return this.icon;
	}

}
