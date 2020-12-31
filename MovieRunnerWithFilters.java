
/**
 * Write a description of MovieRunnerWithFilters here.
 * 
 * @author Xiao Quan
 * @version December 30, 2020
 */
import java.util.*;

public class MovieRunnerWithFilters {

	public void printAverageRatings () {
		// SecondRatings ratings = new SecondRatings("ratedmovies_short.csv", "ratings_short.csv");
		ThirdRatings ratings = new ThirdRatings("ratings.csv");
		System.out.format( "%d ratings loaded!\n", ratings.getRaterSize() );

		MovieDatabase.initialize( "ratedmoviesfull.csv" );
		System.out.format( "%d movies loaded!\n", MovieDatabase.size() );

		ArrayList<Rating> averageRatings = ratings.getAverageRatings( 35 );
		System.out.format( "Found %d movies!\n", averageRatings.size() );
		Collections.sort( averageRatings );

		for ( Rating rating : averageRatings ) {
			String title = MovieDatabase.getTitle( rating.getItem() );
			System.out.format( "%.2f %s\n", rating.getValue(), title );
		}
	}

	public ArrayList<Rating> getAverageRatingsByFilter ( int minimalRaters, Filter filterCriteria ) {
		ArrayList<String> filteredMovieID = MovieDatabase.filterBy( filterCriteria );
		ThirdRatings ratings = new ThirdRatings("ratings.csv");
		ArrayList<Rating> averageRatings = new ArrayList<Rating>();

		for ( String movieID : filteredMovieID ) {
			if ( ratings.getAverageByID( movieID, minimalRaters ) != 0d ) {
				averageRatings.add( new Rating( movieID, ratings.getAverageByID( movieID, minimalRaters )) );
			}
		}
		return averageRatings;
	}

	public void printAverageRatingsByYear() {
		ThirdRatings ratings = new ThirdRatings("ratings.csv");
		System.out.format( "%d ratings loaded!\n", ratings.getRaterSize() );

		MovieDatabase.initialize( "ratedmoviesfull.csv" );
		System.out.format( "%d movies loaded!\n", MovieDatabase.size() );

		Filter yearFilter = new YearAfterFilter( 2000 );

		ArrayList<Rating> averageRatings = getAverageRatingsByFilter ( 20, yearFilter );
		System.out.format( "Found %d movies!\n", averageRatings.size() );
		Collections.sort( averageRatings );

		for ( Rating rating : averageRatings ) {
			Double value = rating.getValue();
			String movieID = rating.getItem();
			String title = MovieDatabase.getTitle( movieID );
			int year = MovieDatabase.getYear( movieID );
			System.out.format( "%.2f %d %s\n", value, year, title );
		}
	}

	public void printAverageRatingsByGenre() {
		ThirdRatings ratings = new ThirdRatings("ratings.csv");
		System.out.format( "%d ratings loaded!\n", ratings.getRaterSize() );

		MovieDatabase.initialize( "ratedmoviesfull.csv" );
		System.out.format( "%d movies loaded!\n", MovieDatabase.size() );

		Filter genreFilter = new GenreFilter( "Comedy" );

		ArrayList<Rating> averageRatings = getAverageRatingsByFilter ( 20, genreFilter );
		System.out.format( "Found %d movies!\n", averageRatings.size() );
		Collections.sort( averageRatings );

		for ( Rating rating : averageRatings ) {
			Double value = rating.getValue();
			String movieID = rating.getItem();
			String title = MovieDatabase.getTitle( movieID );
			String genre = MovieDatabase.getGenres( movieID );		
			System.out.format( "%.2f %s\n\t %s\n", value, title, genre );
		}
	}

	public void printAverageRatingsByMinutes() {
		ThirdRatings ratings = new ThirdRatings("ratings.csv");
		System.out.format( "%d ratings loaded!\n", ratings.getRaterSize() );

		MovieDatabase.initialize( "ratedmoviesfull.csv" );
		System.out.format( "%d movies loaded!\n", MovieDatabase.size() );

		Filter minutesFilter = new MinutesFilter( 105, 135 );

		ArrayList<Rating> averageRatings = getAverageRatingsByFilter ( 5, minutesFilter );
		System.out.format( "Found %d movies!\n", averageRatings.size() );
		Collections.sort( averageRatings );

		// for ( Rating rating : averageRatings ) {
		// 	Double value = rating.getValue();
		// 	String movieID = rating.getItem();
		// 	String title = MovieDatabase.getTitle( movieID );
		// 	int minutes = MovieDatabase.getMinutes( movieID );		
		// 	System.out.format( "%.2f time: %d %s\n", value, minutes, title );
		// }
	}

	public void printAverageRatingsByDirectors() {
		ThirdRatings ratings = new ThirdRatings("ratings.csv");
		System.out.format( "%d ratings loaded!\n", ratings.getRaterSize() );

		MovieDatabase.initialize( "ratedmoviesfull.csv" );
		System.out.format( "%d movies loaded!\n", MovieDatabase.size() );

		Filter directorsFilter = new DirectorsFilter( "Clint Eastwood,Joel Coen,Martin Scorsese,Roman Polanski,Nora Ephron,Ridley Scott,Sydney Pollack" );

		ArrayList<Rating> averageRatings = getAverageRatingsByFilter ( 4, directorsFilter );
		System.out.format( "Found %d movies!\n", averageRatings.size() );
		Collections.sort( averageRatings );

		for ( Rating rating : averageRatings ) {
			Double value = rating.getValue();
			String movieID = rating.getItem();
			String title = MovieDatabase.getTitle( movieID );
			String directors = MovieDatabase.getDirector( movieID );		
			System.out.format( "%.2f %s\n\t %s\n", value, title, directors );
		}
	}

	public void printAverageRatingsByYearAfterAndGenre() {
		AllFilters allFilter = new AllFilters();
		allFilter.addFilter( new GenreFilter("Drama") );
		allFilter.addFilter( new YearAfterFilter( 2010 ) );

		ThirdRatings ratings = new ThirdRatings("ratings.csv");
		System.out.format( "%d ratings loaded!\n", ratings.getRaterSize() );

		MovieDatabase.initialize( "ratedmoviesfull.csv" );
		System.out.format( "%d movies loaded!\n", MovieDatabase.size() );

		ArrayList<Rating> averageRatings = getAverageRatingsByFilter ( 30, allFilter );
		System.out.format( "%d movie matched!\n", averageRatings.size() );
		Collections.sort( averageRatings );

		for ( Rating rating : averageRatings ) {
			String movieID = rating.getItem();
			Double value = rating.getValue();
			int year = MovieDatabase.getYear( movieID );
			String title = MovieDatabase.getTitle( movieID );
			String genre = MovieDatabase.getGenres( movieID );
			System.out.format( "%.2f %d %s\n\t%s\n", value, year, title, genre );
		}
	}

	public void printAverageRatingsByDirectorsAndMinutes() {
		AllFilters allFilter = new AllFilters();
		allFilter.addFilter( new DirectorsFilter("Clint Eastwood") );
		allFilter.addFilter( new MinutesFilter( 90, 180 ) );

		ThirdRatings ratings = new ThirdRatings("ratings.csv");
		System.out.format( "%d ratings loaded!\n", ratings.getRaterSize() );

		MovieDatabase.initialize( "ratedmoviesfull.csv" );
		System.out.format( "%d movies loaded!\n", MovieDatabase.size() );

		ArrayList<Rating> averageRatings = getAverageRatingsByFilter ( 30, allFilter );
		System.out.format( "%d movie matched!\n", averageRatings.size() );
		Collections.sort( averageRatings );

		for ( Rating rating : averageRatings ) {
			String movieID = rating.getItem();
			Double value = rating.getValue();
			int year = MovieDatabase.getYear( movieID );
			String title = MovieDatabase.getTitle( movieID );
			int minutes = MovieDatabase.getMinutes( movieID );	
			String directors = MovieDatabase.getDirector( movieID );	

			System.out.format( "%.2f %d %s\n\t %s\n", value, minutes, title, directors );
		}

	}

}
