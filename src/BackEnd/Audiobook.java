/**
 * Name:Jonathan Maderic
 * Section:1
 * Program:Final Project Phase 1
 * Date: 1/27/13
 * Description: This class is the Audiobook class that is inherits the Item class
 */

package BackEnd;

/**
 * This class is the Audiobook class that is used to create Audiobook for the store
 * @author Jonathan Maderic
 * @version 1.0 1/27/13
 */
public class Audiobook extends Item
{
  //variable for the class
  //used to hold the total of these sold (global)
  private static int totalNumberSold = 0;
  
  /**
  * dataLoaderInit is the init constructor for the dataLoader to use
  * 
  * @param lastUsedId is used to as a constant counter that assigns an id number to the item
  * @param id is used to hold the items id (is zero if the data loader is entering the value)
  * @param name is used to hold the name of the item
  * @param yearOfRelease is used to hold the year the item was released
  * @param duration is used to hold the the length of the item in seconds
  * @param genre is used to hold the genre of the item
  * @param preview is the string holding the file name of the preiveiw file
  * @param numSold is the variable to hold the number of this item has sold
  * @param price is the is the variable that holds the cost of the item
  * @param hidden is the varaible that tells is the user can see or buy the item
  * @param cumulativeRating is the user defined rating for the item
  * @param numRatings is the number of users that have rated this item
  * @param creator is the name of the author for this audio book
  * @param totalNumberSold is the number of the totalNumberSold for this audio book
  */
  public void dataLoaderInit(int id, String name, int yearOfRelease, int duration, String genre, String preview,
              int numSold, double price, boolean hidden, int cumulativeRating, int numRatings, String creator, 
                             int totalNumberSold, int lastUsedId) 
  {
    //PRE:lastUsed will be set to its correct value through the data loader and name, yearOfRelease, duration,
    //genre, preview, price, hidden, creator,totalNumberSold are assigned values
    //POST: lastUsedId will itereate, id will have a unique value, name, yearOfRelease,duration,genre,preview,numSold,
    //price,hidden,cumulativeRating, numRatings, creator, totalNumberSold  will have values set by the dataLoader
    
    //sets the author name
    this.creator =creator;
    //sets the totalNumberSold name
    Audiobook.totalNumberSold =totalNumberSold;
    //calls the user id to fill in some of the feilds
    userInit(id, name, yearOfRelease, duration, genre, preview, price, hidden, creator);
    //fills in the feilds the user doesn't have rights to fill in
    //sets the numSold
    this.numSold = numSold;
    //sets the cumulativeRating
    this.cumulativeRating = cumulativeRating;
    //sets the numRatings
    this.numRatings = numRatings;
    //sets teh lastused id
    setLastUsedId(lastUsedId);
  }
  
  /**
  * buy is the method used when buying this item
  * 
  * @param numSold is the number of this audio book sold
  * @param totalNumberSold is the number of all audio books sold
  */
  @Override
  public void buy()
  {
    //PRE:
    //POST: numSold and totalNumberSold are iterated
    
    //iterates num sold
    numSold++;
    //iterates totalNumberSold
    totalNumberSold++;
  }
  
  /**
  * getPopularity is the method used to return the popularity ( percent)
  * 
  * @param numSold is the number of this audio book sold
  * @param totalNumberSold is the number of all audio books sold
  */
  public int getPopularity()
  {
    //PRE:
    //POST: popularity in percent form is calulated and returned
    
    //returns the popularity
    return ((numSold / (totalNumberSold == 0 ? 1 : totalNumberSold) * 100));
  }
  public static int getTotalNumberSold() {
      return totalNumberSold;
  }
  
     /**
  * toString is the accessor method used to return the album as a string
  * 
  * @param id is used to hold the items id (is zero if the data loader is entering the value)
  * @param name is used to hold the name of the item
  * @param creator is the name of the artist for this album
  * @param yearOfRelease is used to hold the year the item was released
  * @param genre is used to hold the genre of the item
  * @param price is the is the variable that holds the cost of the item
  */
  @Override
  public String toString()
  {
    //PRE: data members hold values
    //POST: the audio book as a string is returned
    
    //returns the toString result
    return "Audiobook: " + name + " by " + creator + " (genre " + genre + ", year of release: " + yearOfRelease + ").\n" + 
	    "           Duration: " + getHour() + ":" + getMinute() + ":" + getSecond() + ", Popularity: " + getPopularity() + ", average rating: " + String.format("Range = %.2f", getRating());
  }
}