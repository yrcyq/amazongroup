import java.util.*;
public class Schedule {
	static class MyComparator implements Comparator<ScheduleList>{

		@Override
		public int compare(ScheduleList sl1, ScheduleList sl2) {
			// TODO Auto-generated method stub
			return sl1.getGrossWeight()-sl2.getGrossWeight();
		}
		
	}
	
	public static List<Content> validSchedule(List<Content> availableContetns){
		if(availableContetns  ==  null)
			return new ArrayList<Content>();
		Map<Integer,ScheduleList> plan = addContents(availableContetns);
		return getSchedule(plan);
	}
	
	public static Map<Integer,ScheduleList> addContents(List<Content> availableContents){
		Map<Integer,ScheduleList> plan = new HashMap<>();
		int currentPlanNumber = 0;
		for(Content content : availableContents){
			PriorityQueue<ScheduleList> pq = new PriorityQueue<>(new MyComparator());
			int duplicateIdx = -1;
			for(int idx : plan.keySet()){
				ScheduleList sl = plan.get(idx);
				if(sl.isTimeConflict(content)){
					if(sl.isDuplicate(content)){
						duplicateIdx = idx;
						break;
					}
				}
				else
					pq.add(sl);
			}
			if(duplicateIdx != -1){
				continue;
			}
			if(pq.size() == 0){
				ScheduleList sl  =  new ScheduleList();
				sl.addContent(content);	
				plan.put(currentPlanNumber++, sl);
			}
			else{
				ScheduleList sl = pq.peek();
				sl.addContent(content);
			}
		}
		return plan;
	}
	
	public static List<Content> getSchedule(Map<Integer,ScheduleList> plan){
		List<Content> result = new ArrayList<>();
		PriorityQueue<ScheduleList> pq = new PriorityQueue<>(new MyComparator());
		for(int idx : plan.keySet()){
			if(pq.size() < 3){
				pq.add(plan.get(idx));
			}
			else{
				if(plan.get(idx).getGrossWeight()>pq.peek().getGrossWeight()){
					pq.poll();
					pq.add(plan.get(idx));
				}
			}
		}
		while(!pq.isEmpty()){
			result.addAll(pq.poll().getScheduleListContents());
		}
		return result;
	}
}
