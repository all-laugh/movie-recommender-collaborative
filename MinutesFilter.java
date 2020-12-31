
/**
 * Write a description of MinutesFilter here.
 * 
 * @author Xiao Quan
 * @version Dec. 30, 2020
 */
public class MinutesFilter implements Filter {
	private int min;
	private int max;

	public MinutesFilter( int min, int max ) {
		this.min = min;
		this.max = max;
	}

	@Override
	public boolean satisfies ( String id ) {
		int minutes = MovieDatabase.getMinutes( id );
		return minutes <= max &&
			   minutes >= min;
	}
}
