import java.util.*;


public class Optimization {
	public static Map<Content, int[]> insert(Map<Integer, List<Content>> schedules, List<Content> toBeInsertedContents, int limit){
		Map<Integer, Map<Integer, Set<Content>>> availableTimes = new HashMap<>();
		Map<Content, int[]> result = new HashMap<>();
		for(int i : schedules.keySet()){
			Map<Integer, Set<Content>> availableTime = findAvailableTime(schedules.get(i), limit);
			availableTimes.put(i, availableTime);
		}
		Collections.sort(toBeInsertedContents, new Comparator<Content>(){
			@Override
			public int compare(Content o1, Content o2){
				return -o1.getContentWeight()/(o1.getEnd()+o1.getStart()+1)-o2.getContentWeight()/(o2.getEnd()-o2.getStart()+1);
			}
		});
		for(Content c : toBeInsertedContents){
			int[] pair = insert(availableTimes, c, limit);
			result.put(c, pair);
		}
		return result;
	}
	
	
	private static int[] insert(Map<Integer, Map<Integer, Set<Content>>> availableTimes, Content c, int limit) {
		// TODO Auto-generated method stub
		return null;
	}


	private static Map<Integer, Set<Content>> findAvailableTime(List<Content> contents, int limit){
		Map<Integer, Set<Content>> unavailable = new HashMap<>();
		int lastEnd = 0;
		for(Content c : contents){
			lastEnd = Math.max(lastEnd, c.getEnd());
			for(int i = c.getStart(); i <= c.getEnd(); i++){
				Set<Content> exist = unavailable.containsKey(i) ? unavailable.get(i) : new HashSet<>();
				exist.add(c);
				unavailable.put(i, exist);
			}
		}
		Map<Integer, Set<Content>> available = new TreeMap<>();
		for(int i = 1; i <= lastEnd; i++){
			if(unavailable.containsKey(i)){
				Set<Content> exist = unavailable.get(i);
				if(exist.size() < limit)
					available.put(i, exist);
			}
			else
				available.put(i, new HashSet<>());
		}
		return available;
	}
}
