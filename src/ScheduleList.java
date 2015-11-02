import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ScheduleList {
	private Map<Integer,Content> schedule;
	private int grossWeight;
	public ScheduleList(){
		schedule = new HashMap<>();
		grossWeight = 0;
	}
	public boolean isTimeConflict(Content content){
		Content exist = schedule.get(content.getStart());
		return exist != null;
	}
	public boolean isDuplicate(Content content){
		Content exist = schedule.get(content.getStart());
		if(exist == null)
			return false;
		return exist.getId() == content.getId()&&exist.getValue() == content.getValue();
	}
	public int getGrossWeight(){
		return grossWeight;
	}
	public void addContent(Content content){
		if(isDuplicate(content))
			return ;
		for(int i = content.getStart();i<=content.getEnd();i++){
			schedule.put(i,content);
		}
		grossWeight+=(content.getEnd()-content.getStart()+1)*content.getValue();
	}
	public List<Content> getScheduleListContents(){
		return new ArrayList<Content>(schedule.values());
	}
}
