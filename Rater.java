
/**
 * Write a description of Rater here.
 * 
 * @author Xiao Quan 
 * @version Dec 29, 2020
 */

import java.util.ArrayList;

public interface Rater {
	public void addRating(String item, double rating);

    public boolean hasRating(String item);

    public String getID();

    public int numRatings();

    public double getRating(String item);

    public ArrayList<String> getItemsRated();
}
