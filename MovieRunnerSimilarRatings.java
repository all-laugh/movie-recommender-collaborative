
/**
 * Write a description of MovieRunnerWithFilters here.
 * 
 * @author Xiao Quan
 * @version December 30, 2020
 */
import java.util.*;

public class MovieRunnerSimilarRatings {

	public void printAverageRatings () {
		FourthRatings ratings = new FourthRatings();

		RaterDatabase.initialize( "ratings.csv" );
		System.out.format( "%d ratings loaded!\n", RaterDatabase.size() );

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

	public void printSimilarRatings() {
		FourthRatings ratings = new FourthRatings();
		MovieDatabase.initialize( "ratedmoviesfull.csv" );
		RaterDatabase.initialize( "ratings.csv" );
		ArrayList<Rating> similarRatings = ratings.getSimilarRatings( "01", 20, 5 );
		System.out.format( "Found %d similar movies!\n", similarRatings.size() );
		for ( Rating rating : similarRatings ) {
			String title = MovieDatabase.getTitle( rating.getItem() );
			double score = rating.getValue();
			System.out.format( "%.2f %s\n", score, title );
		}
	}	

	public void printSimilarRatingsByGenre() {
		FourthRatings ratings = new FourthRatings();
		MovieDatabase.initialize( "ratedmoviesfull.csv" );
		RaterDatabase.initialize( "ratings.csv" );

		Filter genreFilter = new GenreFilter("Mystery");
		ArrayList<Rating> similarRatings = ratings.getSimilarRatingsByFilter ( "964", 20, 5, genreFilter );
		System.out.format( "Found %d similar movies!\n", similarRatings.size() );
		for ( Rating rating : similarRatings ) {
			String movieID = rating.getItem();
			String title = MovieDatabase.getTitle( movieID );
			double score = rating.getValue();
			String genre = MovieDatabase.getGenres( movieID );
			System.out.format( "%.2f %s\n\t%s\n", score, title, genre );
		}
	}

	public void printSimilarRatingsByDirector() {
		FourthRatings ratings = new FourthRatings();
		MovieDatabase.initialize( "ratedmoviesfull.csv" );
		RaterDatabase.initialize( "ratings.csv" );

		Filter dirFilter = new DirectorsFilter("Clint Eastwood,J.J. Abrams,Alfred Hitchcock,Sydney Pollack,David Cronenberg,Oliver Stone,Mike Leigh");
		ArrayList<Rating> similarRatings = ratings.getSimilarRatingsByFilter ( "120", 10, 2, dirFilter );
		System.out.format( "Found %d similar movies!\n", similarRatings.size() );
		for ( Rating rating : similarRatings ) {
			String movieID = rating.getItem();
			String title = MovieDatabase.getTitle( movieID );
			double score = rating.getValue();
			String director = MovieDatabase.getDirector( movieID );
			System.out.format( "%.2f %s\n\t%s\n", score, title, director );
		}
	}

	public void printSimilarRatingsByGenreAndMinutes() {
		FourthRatings ratings = new FourthRatings();
		MovieDatabase.initialize( "ratedmoviesfull.csv" );
		RaterDatabase.initialize( "ratings.csv" );

		AllFilters allFilter = new AllFilters();
		allFilter.addFilter( new GenreFilter("Drama") );
		allFilter.addFilter( new MinutesFilter( 80, 160 ) );

		ArrayList<Rating> similarRatings = ratings.getSimilarRatingsByFilter ( "168", 10, 3, allFilter );
		System.out.format( "Found %d similar movies!\n", similarRatings.size() );
		for ( Rating rating : similarRatings ) {
			String movieID = rating.getItem();
			String title = MovieDatabase.getTitle( movieID );
			double score = rating.getValue();
			String director = MovieDatabase.getDirector( movieID );
			System.out.format( "%.2f %s\n\t%s\n", score, title, director );
		}
	}

	public void printSimilarRatingsByYearAfterAndMinutes() {
		FourthRatings ratings = new FourthRatings();
		MovieDatabase.initialize( "ratedmoviesfull.csv" );
		RaterDatabase.initialize( "ratings.csv" );

		AllFilters allFilter = new AllFilters();
		allFilter.addFilter( new YearAfterFilter(1975) );
		allFilter.addFilter( new MinutesFilter( 70, 200 ) );

		ArrayList<Rating> similarRatings = ratings.getSimilarRatingsByFilter ( "314", 10, 5, allFilter );
		System.out.format( "Found %d similar movies!\n", similarRatings.size() );
		for ( Rating rating : similarRatings ) {
			String movieID = rating.getItem();
			String title = MovieDatabase.getTitle( movieID );
			double score = rating.getValue();
			String director = MovieDatabase.getDirector( movieID );
			System.out.format( "%.2f %s\n\t%s\n", score, title, director );
		}
	}

	public ArrayList<Rating> getAverageRatingsByFilter ( int minimalRaters, Filter filterCriteria ) {
		ArrayList<String> filteredMovieID = MovieDatabase.filterBy( filterCriteria );
		FourthRatings ratings = new FourthRatings();
		RaterDatabase.initialize( "ratings.csv" );
		ArrayList<Rating> averageRatings = new ArrayList<Rating>();

		for ( String movieID : filteredMovieID ) {
			if ( ratings.getAverageByID( movieID, minimalRaters ) != 0d ) {
				averageRatings.add( new Rating( movieID, ratings.getAverageByID( movieID, minimalRaters )) );
			}
		}
		return averageRatings;
	}
	

	public void printAverageRatingsByYearAfterAndGenre() {
		AllFilters allFilter = new AllFilters();
		allFilter.addFilter( new GenreFilter("Drama") );
		allFilter.addFilter( new YearAfterFilter( 2010 ) );

		FourthRatings ratings = new FourthRatings();

		RaterDatabase.initialize( "ratings.csv" );
		System.out.format( "%d ratings loaded!\n", RaterDatabase.size() );

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

}
