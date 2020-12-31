
/**
 * Write a description of FirstRatings here.
 * 
 * @author Xiao Quan 
 * @version December 28, 2020
 */

import edu.duke.*;
import java.util.*;
import java.io.*;
import org.apache.commons.csv.*;

public class FirstRatings {

    public ArrayList<Movie> loadMovies(String filename) {

    	ArrayList <Movie> movies = new ArrayList<Movie>();

    	String file = "data/" + filename;
    	FileResource fr = new FileResource(file);

    	CSVParser parser = fr.getCSVParser();

    	for ( CSVRecord parsed : parser ) {
    		Movie curMovie = new Movie( parsed.get("id"), 
    									parsed.get("title"),
    									parsed.get("year"),
    									parsed.get("genre"),
    									parsed.get("director"),
    									parsed.get("country"),
    									parsed.get("poster"),
    									Integer.parseInt(parsed.get("minutes")));
    		movies.add( curMovie );

    	}

    	return movies;
    }

    public void testLoadMovies () {
    	ArrayList<Movie> movies = loadMovies( "ratedmoviesfull.csv");
    	// ArrayList<Movie> movies = loadMovies( "ratedmovies_short.csv");

    	System.out.println( movies.size() + " movies loaded!" );
    	// System.out.println( movies );
    	int comedyCount = 0;
    	for ( Movie movie : movies ) {
    		if ( movie.getGenres().contains("Comedy") )
    			comedyCount++; 
    	}
    	System.out.format( "There are %d comedies here!", comedyCount );
    	int longMovieCount = 0;
    	for ( Movie movie : movies ) {
    		if ( movie.getMinutes() > 150 )
    			longMovieCount++; 
    	}
    	System.out.format( "There are %d movies longer than 150min!\n", longMovieCount );
    	HashMap <String, Integer> directorMovie = new HashMap <String, Integer>();
    	for ( Movie movie : movies ) {
    		String director = movie.getDirector();
            if ( director.contains(", ") ) {
                String [] directors = director.split(", ");
                for ( String dir : directors ) {
                    if ( directorMovie.containsKey( dir ) ) 
                        directorMovie.put( dir, directorMovie.get( dir ) + 1 );
                    else 
                        directorMovie.put( dir,  1 );   
                }         
            }
            else {
                if ( directorMovie.containsKey( director ) ) 
                    directorMovie.put( director, directorMovie.get( director ) + 1 );
                else  
                    directorMovie.put( director,  1 );  
            }

    	}

    	Integer prolific = Collections.max( directorMovie.values() );
        ArrayList<String> prolificDirectors = new ArrayList<String>();
        for ( String director : directorMovie.keySet() ) {
            if ( directorMovie.get( director ) == prolific ) {
                prolificDirectors.add( director );
            }
        }

    	System.out.format("The most prolific director directed %d movie(s).\n", 
    		prolific );
        System.out.format( "There are %d directors who directed that many movies.\n" ,prolificDirectors.size() );
        System.out.format( "They are: %s \n", prolificDirectors );
    }

    public ArrayList<Rater> loadRaters( String filename ) {
    	ArrayList<Rater> raters = new ArrayList<Rater>();
    	String file = "data/" + filename;
    	FileResource fr = new FileResource( file );
    	CSVParser parser = fr.getCSVParser();
        String prev_id = "1";
        Rater rater = new EfficientRater( "1" );
        try {
            List<CSVRecord> records = parser.getRecords();
            for ( CSVRecord parsed : records ) {
                String rater_id = parsed.get("rater_id");
                if ( rater_id.equals(prev_id) ) {
                    rater.addRating( parsed.get("movie_id"), Double.parseDouble(parsed.get("rating")));
                    if ( records.lastIndexOf( parsed ) == records.size() - 1 ) {
                        raters.add( rater );
                    }

                }
                else {
                    raters.add( rater );  
                    rater = new EfficientRater( rater_id );
                    rater.addRating( parsed.get("movie_id"), Double.parseDouble(parsed.get("rating")));
                    prev_id = rater_id;
                }
            }
        }   
        catch ( IOException e )  {
            e.printStackTrace();
    	} 

    	return raters;
    }

    public void testLoadRater() {
    	// ArrayList<Rater> raters = loadRaters( "ratings_short.csv" );
        ArrayList<Rater> raters = loadRaters( "ratings.csv" );
    	System.out.format( "There are a total of %d raters", raters.size() );
        int numRatings = 0;
        HashMap<String, Integer> raterNumRatings = new HashMap<String, Integer>();
        HashSet<String> uniqueTitles = new HashSet<>();
        for ( Rater rater : raters ) {
            raterNumRatings.put( rater.getID(), rater.numRatings() );

            if ( rater.getID().equals( "193" ) ) {
                System.out.format( "\nRater 193 has %d ratings.\n", rater.numRatings() );
            }
            
            if ( rater.hasRating( "1798709" ) ) {
                numRatings++;
            }

            for ( String movie_id : rater.getItemsRated() ) {
                uniqueTitles.add( movie_id );
            }

        }
        int mostRatings = Collections.max( raterNumRatings.values() );
        for ( String rater_id : raterNumRatings.keySet() ) {
            if ( raterNumRatings.get( rater_id ) == mostRatings ) {
                System.out.println( "rater " + rater_id + " has the most number of ratings: " +  mostRatings );
            }
        }
        System.out.format( "\nThe movie (1798709) has %d ratings", numRatings );
        System.out.format( "\nThere are %d unique titles rated.", uniqueTitles.size() );
    }
}
