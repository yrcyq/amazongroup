import java.util.*;
public class Selection {
	public static List<Content> select(int time, Map<Integer, List<Content>> schedules, Map<Integer, Double> areaWeight){
		List<Content> result = new ArrayList<>();
		findQualifiedContent(time, schedules);
		bruteForce(schedules, areaWeight, new HashSet<Integer>(), new ArrayList<Content>(), result, new Double(0), 0, 0);
		return result;
	}
	
	private static void findQualifiedContent(int time, Map<Integer, List<Content>> schedules) {
		// TODO Auto-generated method stub
		if(time < 1 || schedules.isEmpty())
			return ;
		for(int i : schedules.keySet()){
			List<Content> schedule = schedules.get(i);
			for(Content c : schedule){
				if(time < c.getStart() || time > c.getEnd())
					schedule.remove(c);
			}
		}
	}

	private static void bruteForce(Map<Integer, List<Content>> schedules, Map<Integer, Double> areaWeight, 
			Set<Integer> visited, List<Content> currentSelection, List<Content> result, Double globalSum, 
			int idx, double currentSum){
		if(idx == schedules.size()){
			if(currentSum > globalSum.doubleValue()){
				globalSum  = new Double(currentSum);
				result = new ArrayList<>(currentSelection);
			}
			return;
		}
		List<Content> schedule = schedules.get(idx);
		for(int i = -1; i < schedule.size(); i++){
			if(i == -1){
				currentSelection.add(null);
				bruteForce(schedules, areaWeight, visited, currentSelection, result, globalSum, idx+1, currentSum);
			}
			else{
				Content c = schedule.get(i);
				if(visited.contains(c.getId()))
					continue;
				visited.add(c.getId());
				currentSelection.add(c);
				bruteForce(schedules, areaWeight, visited, currentSelection, result, globalSum, idx+1, currentSum+c.getContentWeight()*areaWeight.get(idx));
				visited.remove(c.getId());
			}
			currentSelection.remove(currentSelection.size()-1);
		}
	}
}
