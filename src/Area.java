import java.util.*;
public class Area {
	private int areaId;
	private int areaWeight;
	private List<Content> validSchedule;
	private Content currenShown;
	public int getAreaId() {
		return areaId;
	}
	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}
	public int getAreaWeight() {
		return areaWeight;
	}
	public void setAreaWeight(int areaWeight) {
		this.areaWeight = areaWeight;
	}
	public List<Content> getValidSchedule() {
		return validSchedule;
	}
	public void setValidSchedule(List<Content> schedule) {
		this.validSchedule = Schedule.validSchedule(schedule);
	}
	public Content getCurrenShown() {
		return currenShown;
	}
	public void setCurrenShown(Content currenShown) {
		this.currenShown = currenShown;
	}
	public void insertContent(Content content){
		
	}
}
