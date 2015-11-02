import java.util.*;
public class Schedule {
	private Map<Integer,ScheduleList> plan=new HashMap<>();
	
	public void addContents(List<Content> availableContents){
		//Set<Integer> exsistIds=new HashSet<>(); 
		plan.put(0,new ScheduleList());
		int currentPlanNumber=plan.size();
		for(Content content:availableContents){
			PriorityQueue<ScheduleList> pq=new PriorityQueue<>(new Comparator<ScheduleList>(){

				@Override
				public int compare(ScheduleList sl1, ScheduleList sl2) {
					// TODO Auto-generated method stub
					return sl1.getGrossWeight()-sl2.getGrossWeight();
				}
				
			});
			int duplicateIdx=-1;
			for(int idx:plan.keySet()){
				ScheduleList sl=plan.get(idx);
				if(sl.isTimeConflict(content)){
					if(sl.isDuplicate(content)){
						duplicateIdx=idx;
						break;
					}
				}
				else
					pq.add(sl);
			}
			if(duplicateIdx!=-1){
				continue;
			}
			if(pq.size()==0){
				ScheduleList sl = new ScheduleList();
				sl.addContent(content);	
				plan.put(currentPlanNumber++,sl);
			}
			else{
				ScheduleList sl=pq.peek();
				sl.addContent(content);
			}
		}
	}
	public List<Content> getSchedule(){
		List<Content> result=new ArrayList<>();
		PriorityQueue<ScheduleList> pq=new PriorityQueue<>(new Comparator<ScheduleList>(){

			@Override
			public int compare(ScheduleList sl1, ScheduleList sl2) {
				// TODO Auto-generated method stub
				return sl1.getGrossWeight()-sl2.getGrossWeight();
			}
			
		});
		for(int idx:plan.keySet()){
			if(pq.size()<3){
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
