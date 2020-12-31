
/**
 * Write a description of SecondRatings here.
 * 
 * @author Xiao Quan
 * @version Dec 30, 2020
 */

import java.util.*;

public class ThirdRatings {
    private ArrayList<Rater> myRaters;

	public double getAverageByID( String movieId , int minimalRaters ) {
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
		ArrayList<String> movies = MovieDatabase.filterBy(new TrueFilter());
		for ( String movieID : movies ) {
			if ( getAverageByID( movieID, minimalRaters ) != 0d ) {
				averageRatings.add( new Rating( movieID, getAverageByID( movieID, minimalRaters )) );
			}
		}
		return averageRatings;
	}

    public ThirdRatings() {
        // default constructor
        this("ratings.csv");
    }

    public ThirdRatings( String ratingFile ) {
    	FirstRatings loader = new FirstRatings();
    	myRaters = loader.loadRaters( ratingFile );
    }

    public int getRaterSize() {
    	return myRaters.size();
    }
    
}
