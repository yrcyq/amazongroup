import java.util.*;

public class Schedule1 {
	public static List<Content> schedule(List<Content> availableContents, int limit){
		if(availableContents == null || availableContents.isEmpty())
			return new ArrayList<Content>();
		Collections.sort(availableContents,new Comparator<Content>(){

			@Override
			public int compare(Content o1, Content o2) {
				// TODO Auto-generated method stub
				//assume the value is per time 
				return o1.getContentWeight()/(o1.getEnd()-o1.getStart()+1)-o2.getContentWeight()/(o2.getEnd()-o2.getStart()+1);
			}
			
		});
		List<Content> result = new ArrayList<>();
		Map<Integer, Set<Integer>> occupiedTime = new HashMap<>();
		for(Content c : availableContents){
			if(!isAvailabe(occupiedTime, c, limit))
				continue;
			updateOccupied(occupiedTime, c);
			result.add(c);
		}
		return result;
	}

	private static void updateOccupied(Map<Integer, Set<Integer>> occupiedTime, Content c) {
		// TODO Auto-generated method stub
		for(int i = c.getStart(); i <= c.getEnd(); i++){
			Set<Integer> exist = occupiedTime.containsKey(i) ? occupiedTime.get(i) : new HashSet<>();
			exist.add(c.getId());
			occupiedTime.put(i, exist);
		}
	}

	private static boolean isAvailabe(Map<Integer, Set<Integer>> occupiedTime, Content c, int limit) {
		// TODO Auto-generated method stub
		for(int i = c.getStart(); i <= c.getEnd(); i++){
			if(!occupiedTime.containsKey(i)) 
				continue;
			if(occupiedTime.get(i).contains(c.getId()) || occupiedTime.get(i).size() >= limit)
				return false;
		}
		return true;
	}
}
