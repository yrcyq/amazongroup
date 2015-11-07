import java.util.Comparator;

public class MyComparator implements Comparator<Content> {

	@Override
	public int compare(Content o1, Content o2) {
		// TODO Auto-generated method stub
		return o2.getContentWeight()/(o2.getEnd()-o2.getStart()+1)
				-o1.getContentWeight()/(o1.getEnd()+o1.getStart()+1);
	}

}
