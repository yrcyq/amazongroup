import java.util.*;
/*
 * two optimization approach: 1. time fit, 2. space fit;
 * future work: 
 * use a occupied load factor 
 * if the occupied load factor is below threshold, which means their are many empty time, so use time fit
 * else, which means most time is occupied, then we need to find the most fit space for the content 
 */

public class Optimization {
	public static Map<Content, int[]> insert(Map<Integer, List<Content>> schedules, List<Content> toBeInsertedContents, int limit){
		Map<Integer, Map<Integer, Set<Content>>> availableTimes = new HashMap<>();
		Map<Content, int[]> result = new HashMap<>();
		for(int i : schedules.keySet()){
			Map<Integer, Set<Content>> availableTime = findAvailableTime(schedules.get(i), limit);
			availableTimes.put(i, availableTime);
		}
		Collections.sort(toBeInsertedContents, new MyComparator());
		for(Content c : toBeInsertedContents){
			int[] pair = insert(availableTimes, c, limit);
			result.put(c, pair);
		}
		return result;
	}
	
	// time fit, choose the earlist available time
	private static int[] insert(Map<Integer, Map<Integer, Set<Content>>> availableTimes, Content c, int limit) {
		// TODO Auto-generated method stub
		int areaNo = -1;
		int startTime = Integer.MAX_VALUE;
		for(int i = 0; i<availableTimes.size(); i++){
			Map<Integer, Set<Content>> availableTime = availableTimes.get(i);
			//availableTime is TreeMap, so the key is in ascending order
			for(int j = 0, count = 0; j <=availableTime.size(); j++){	
				if(j == availableTime.size()){
					if(startTime > j){
						startTime = j;
						areaNo = i;
					}
					break;
				}
				Set<Content> exist = availableTime.get(j);
				if(exist.size() >= limit || exist.contains(c)){
					count = 0;
					continue;
				}
				count++;
				if(count == c.getEnd() - c.getStart() + 1){
					int start = j - count + 1;
					if(start < startTime){
						startTime = start;
						areaNo = i;
					}
					break;
				}
			}
		}
		return new int[]{areaNo, startTime};
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
