
/**
 * Write a description of SecondRatings here.
 * 
 * @author Xiao Quan
 * @version Dec 30, 2020
 */

import java.util.*;

public class FourthRatings {

	public double dotProduct ( Rater me, Rater r ) {
		ArrayList<String> myRatings = me.getItemsRated();
		double dotProduct = 0;
		for ( String movieID : myRatings ) {
			if ( r.hasRating( movieID ) ) {
				dotProduct += convert( me.getRating( movieID ) ) * convert( r.getRating( movieID ) );
			}
		}
		return dotProduct;
	}

	private ArrayList<Rating> getSimilarities( String curRaterID ) {
		Rater curRater = RaterDatabase.getRater( curRaterID );
		ArrayList<Rater> allRaters = RaterDatabase.getRaters();
		ArrayList<Rating> similarRaters = new ArrayList<Rating>();
		for ( Rater rater : allRaters ) {
			if ( !rater.getID().equals( curRaterID )) {
				double dot = dotProduct( curRater , rater );
				if ( dot > 0 ) 
					similarRaters.add( new Rating( rater.getID(), dot ) );
			}
		}

		Collections.sort( similarRaters, Collections.reverseOrder() );

		return similarRaters;
	}

	public ArrayList<Rating> getSimilarRatings ( String raterID, int k, int minimalRaters ) {
		ArrayList<Rating> similarRatersAndWeights = getSimilarities( raterID );
		HashSet<String> similarMovieIDs = new HashSet<String>();
		ArrayList<Rater> similarRaters = new ArrayList<Rater>();
		HashMap<String, Double> raterWeights = new HashMap<String, Double>();
		

		// Get two lists of all the similar raters and their rated movies
		for ( int i = 0; i < k; i++ ) {
			Rating similarRaterRating = similarRatersAndWeights.get(i);
			String similarRaterID = similarRaterRating.getItem();
			double weight = similarRaterRating.getValue();
			raterWeights.put( similarRaterID, weight );
			Rater similarRater = RaterDatabase.getRater( similarRaterID );
			similarRaters.add( similarRater );
			for ( String movieID : similarRater.getItemsRated() ) {
				similarMovieIDs.add( movieID );	
			}
		}

		// Loop over the movies list and calculate the weighted averages
		ArrayList<Rating> weightedRatings = new ArrayList<Rating>();
		Rater me = RaterDatabase.getRater( raterID );
		ArrayList<String> ratedByMe = me.getItemsRated();

		for ( String mID : similarMovieIDs ) {
			int numRatings = 0;
			double weighted = 0;
			for ( Rater sRater : similarRaters ) {
				if ( sRater.getItemsRated().contains( mID ) && !ratedByMe.contains( mID ) ) {
					numRatings++;
					double weight = raterWeights.get( sRater.getID() );
					weighted += weight * sRater.getRating( mID );
				}
			}
			if ( numRatings >= minimalRaters ) {
				double weightedAverage = weighted / numRatings;
				weightedRatings.add( new Rating( mID, weightedAverage ));
			}
		}
		Collections.sort( weightedRatings, Collections.reverseOrder() );

		return weightedRatings;
	}

	public ArrayList<Rating> normalizeRatings( ArrayList<Rating> weightedRatings ) {
		ArrayList<Double> scores = new ArrayList<>();
		for ( Rating r : weightedRatings ) {
			scores.add( r.getValue() );
		}
		double highest = Collections.max( scores );
		for ( int i = 0; i < weightedRatings.size()-1; i++ ) {
			Rating r = weightedRatings.get( i );
			Rating normalizedRating = new Rating( r.getItem(), ( r.getValue()/highest ) * 10 );
			weightedRatings.set(i , normalizedRating );
		}
		return weightedRatings;
	}

	public ArrayList<Rating> getSimilarRatingsByFilter ( String raterID, int k, int minimalRaters, Filter filterCriteria ) {
		ArrayList<String> filteredMovieID = MovieDatabase.filterBy( filterCriteria );
		ArrayList<Rating> similarRatings = getSimilarRatings ( raterID, k, minimalRaters );
		ArrayList<Rating> filteredRatings = new ArrayList<Rating>();

		for ( Rating similarRating : similarRatings ) {
			String movieID = similarRating.getItem();
			if ( filteredMovieID.contains(movieID) ) {
				filteredRatings.add( similarRating );
			}
		}	
		return filteredRatings;
	}


	private double convert ( double rating ) {
		return rating - 5;
	}

	public double getAverageByID( String movieId , int minimalRaters ) {
		int numRaters = 0; 
		double ratingTotal = 0;
		for ( Rater rater : RaterDatabase.getRaters() ) {
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

	public ArrayList<Rating> getAverageRatingsByFilter( int minimalRaters, Filter filterCriteria ) {
		ArrayList<Rating> averageRatings = new ArrayList<Rating>();
		ArrayList<String> movies = MovieDatabase.filterBy( filterCriteria );
		for ( String movieID : movies ) {
			if ( getAverageByID( movieID, minimalRaters ) != 0d ) {
				averageRatings.add( new Rating( movieID, getAverageByID( movieID, minimalRaters )) );
			}
		}
		return averageRatings;
	}	

}
