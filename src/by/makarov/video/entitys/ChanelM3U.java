package by.makarov.video.entitys;   
public class ChanelM3U {
    private String canalName;
    private String tvgName;
    private String tvgShift;
    private String urlCanal;
    private String groupTitle;
    private int id;
    public ChanelM3U() {
    }

    public String getTvgName() {
        return tvgName;
    }

    public void setTvgName(String tvgName) {
        this.tvgName = tvgName;
    }

    public String getCanalName() {
        return canalName;
    }

    public void setCanalName(String canalName) {
        this.canalName = canalName;
    }

    public String getTvgShift() {
        return tvgShift;
    }

    public void setTvgShift(String tvgShift) {
        this.tvgShift = tvgShift;
    }

    public String getUrlCanal() {
        return urlCanal;
    }

    public void setUrlCanal(String urlCanal) {
        this.urlCanal = urlCanal;
    }

    public String getGroupTitle() {
        return groupTitle;
    }

    public void setGroupTitle(String groupTitle) {
        this.groupTitle = groupTitle;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}