
/**
 * Write a description of MovieRunnerAverage here.
 * 
 * @author Xiao Quan
 * @version Dec. 29, 2020
 */
import java.util.*;

public class MovieRunnerAverage {

	public void printAverageRatings () {
		// SecondRatings ratings = new SecondRatings("ratedmovies_short.csv", "ratings_short.csv");
		SecondRatings ratings = new SecondRatings("ratedmoviesfull.csv", "ratings.csv");

		int movieSize = ratings.getMovieSize();
		int ratingSize = ratings.getRaterSize();

		System.out.format( "%d movies and %d ratings loaded!\n", movieSize, ratingSize );
		ArrayList<Rating> averageRatings = ratings.getAverageRatings( 12 );
		Collections.sort( averageRatings );

		for ( Rating rating : averageRatings ) {
			String title = ratings.getTitle( rating.getItem() );
			System.out.format( "%.2f %s\n", rating.getValue(), title );
		}
	}

	public void  getAverageRatingOneMovie () {
		SecondRatings ratings = new SecondRatings("ratedmoviesfull.csv", "ratings.csv");
		ArrayList<Rating> averageRatings = ratings.getAverageRatings( 3 );
		String title = "Vacation";

		for ( Rating rating : averageRatings ) {
			if ( ratings.getTitle( rating.getItem() ).equals(title) ) {
				System.out.format( "The average rating for '%s' is %.4f. \n", title, rating.getValue() );
				return;
			}
		}
		System.out.println( "Title " + title + " NOT FOUND" );
	}

}
