
/**
 * Write a description of SecondRatings here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;

public class SecondRatings {
    private ArrayList<Movie> myMovies;
    private ArrayList<Rater> myRaters;

	private double getAverageByID( String movieId , int minimalRaters ) {
		int numRaters = 0; 
		double ratingTotal = 0;
		for ( Rater rater : myRaters ) {
			if ( rater.hasRating( movieId ) ) {
				numRaters++;
				ratingTotal += rater.getRating( movieId );
			}
		}

		if ( numRaters >= minimalRaters )
			return ratingTotal / numRaters;
		else return 0d;
	}

	public ArrayList<Rating> getAverageRatings( int minimalRaters ) {
		ArrayList<Rating> averageRatings = new ArrayList<Rating>();
		for ( Movie movie : myMovies ) {
			String movieID = movie.getID();
			if ( getAverageByID( movieID, minimalRaters ) != 0d ) {
				averageRatings.add( new Rating( movieID, getAverageByID( movieID, minimalRaters )) );
			}
		}
		return averageRatings;
	}

	public String getTitle( String movieID ) {
		String title = null;
		for ( Movie movie : myMovies ) {
			if ( movie.getID().equals( movieID ) )
				title = movie.getTitle();
		}

		return (title != null ) ? title : "Title not Found";
	}

	public String getID ( String title ) {
		String id = null;
		for ( Movie movie : myMovies ) {
			if ( movie.getTitle().equals( title ) )
				id = movie.getID();
		}
		return ( id != null ) ? id : "NO SUCH TITLE";
	}
    
    public SecondRatings() {
        // default constructor
        this("ratedmoviesfull.csv", "ratings.csv");
    }

    public SecondRatings( String movieFile, String ratingFile ) {
    	FirstRatings loader = new FirstRatings();
    	myMovies = loader.loadMovies( movieFile );
    	myRaters = loader.loadRaters( ratingFile );
    }

    public int getMovieSize() {
    	return myMovies.size();
    }

    public int getRaterSize() {
    	return myRaters.size();
    }
    
}
