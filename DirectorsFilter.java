
/**
 * Write a description of MinutesFilter here.
 * 
 * @author Xiao Quan
 * @version Dec. 30, 2020
 */
public class DirectorsFilter implements Filter {
	private String [] directors;

	public DirectorsFilter( String directors ) {
		this.directors = directors.split(",");
	}

	@Override
	public boolean satisfies ( String id ) {
		for ( String dir : directors ) {
			if ( MovieDatabase.getDirector(id).contains( dir ) )
				return true;
		}
		return false;
	}
}
