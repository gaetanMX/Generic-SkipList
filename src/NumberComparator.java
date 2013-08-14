import java.util.Comparator;

public class NumberComparator implements Comparator<Number>{

	@Override
	public int compare(Number o1, Number o2) {
		if(o1.intValue()==o2.intValue()){
			return 0;
		}else if(o1.intValue() < o2.intValue()){
			return -1;
		}
		return 1;
	}

}
