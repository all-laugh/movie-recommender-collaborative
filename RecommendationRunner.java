import java.util.ArrayList;
import java.util.Collections;

public class RecommendationRunner implements Recommender {
    
	@Override
    public ArrayList<String> getItemsToRate (){
    	MovieDatabase.initialize( "ratedmoviesfull.csv" );
    	RaterDatabase.initialize( "ratings.csv" );

    	AllFilters allFilter = new AllFilters();
    	allFilter.addFilter( new YearAfterFilter(1980) );

    	FourthRatings ratings = new FourthRatings();
    	ArrayList<Rating> mostRatedRatings = ratings.getAverageRatingsByFilter( 3, allFilter  );

    	allFilter.addFilter( new GenreFilter("Drama") );
    	ArrayList<Rating> mostRatedDramas = ratings.getAverageRatingsByFilter( 2, allFilter );
    	
    	Collections.shuffle( mostRatedRatings );
    	Collections.shuffle( mostRatedDramas );       	

    	ArrayList<String> tenMovies = new ArrayList<String>();

    	for ( int i = 0; i < 6; i++ ) {
    		// int index =  1 + ( int ) ( Math.random() * ( mostRatedRatings.size()*.8 ) );
    		String movieID = mostRatedRatings.get(i).getItem();
    		tenMovies.add(movieID);
    	}

    	for ( int i = 0; i < 4; i++ ) {
    		// int index =  1 + ( int ) ( Math.random() * ( mostRatedRatings.size()*.8 ) );
    		String movieID = mostRatedDramas.get(i).getItem();
    		tenMovies.add(movieID);
    	}

    	return tenMovies;

    }

    @Override
    public void printRecommendationsFor (String webRaterID){

		FourthRatings ratings = new FourthRatings();
		MovieDatabase.initialize( "ratedmoviesfull.csv" );
		RaterDatabase.initialize( "ratings.csv" );
		try {
			ArrayList<Rating> similarRatings = ratings.getSimilarRatings( webRaterID, 20, 1 );
			System.out.println("<h3>Here are your recommendations!</h3>");
			System.out.println( "<html><div class='lessons'><table>" );

			for ( int i = 0; i < 10; i++ ) {
				if ( i % 5 == 0 ) { System.out.println("<tr>"); }
				Rating rating = similarRatings.get(i);
				String movieID = rating.getItem();
				String title = MovieDatabase.getTitle( movieID );
				String poster = MovieDatabase.getPoster( movieID );
				System.out.format( "<td><a href='%s'><img src='%s' width='90%%' align='center'><p>%s</p></td>", poster, poster, title );
				if ( i % 5 == 4 ) { System.out.println("</tr>"); }
			}
			System.out.println("</table></div></html>");

		} catch ( NullPointerException e ) {
			System.out.format("<html><table><tr><th>You have very peculiar taste...I can't make a good recommendation for you</th></tr></table></html>");
		} catch ( IndexOutOfBoundsException e ) {
			System.out.format("<html><table><tr><th>You have very particular taste... I don't really have much for you!</th></tr></table></html>");
		}

    }
}