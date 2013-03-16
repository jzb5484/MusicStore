/**
 * Name:Jonathan Maderic
 * Section:1
 * Program:Final Project Phase 1
 * Date: 1/26/13
 * Description: This class is the item class that is inherited by the album,audiobook,and film classes
 */

package BackEnd;

import java.util.Timer;
import java.util.TimerTask;

/**
 * This class is the Item class that is used to create items for the store
 * @author Jonathan Maderic
 * @version 1.0 1/26/13
 */
public abstract class Item
{ 
  //constants for the class
  //hour divider
  private static final int HOUR_DIVIDER = 3600;
  //minute divider and subtracter
  private static final int MINUTE_DIVIDER = 60;
  //second mod divider
  private static final int SECOND_DIVIDER = 1;
  
   //time for a preveiw in milliseconds - currently set to 30 seconds
  private static final int PREVEIW_TIME_MILLS = 30000;

  
  //variable for the class
  //lastUsedId is the static counter that is init 0 but is loaded when the program boots
  protected static int lastUsedId = 0;
  //id holds the unique id of the item (there will not be an item id 0 so this is default to 0 zero for error checking)
  protected int id = 0;
  //name is the string that holds the item name
  protected String name;
  //yearOfRelease is the int the hold the year the item was released
  protected int yearOfRelease;
  //duration is the duration int that holds the length of the item in seconds
  protected int duration;
  //genre holds the string the holds the genre of the item
  protected String genre;
  //preview hold the string containing the file name to the file holding the preview
  protected String preview;
  //numSold is the item for how many of this item sold
  protected int numSold;
  //price is the variable that holds the cost of this item
  protected double price;
  //hidden is the bool variable that tells if the item can be veiw or purcased by a user(non-admin)
  //(false mean not hidden and is user accessable)
  protected boolean hidden;
  //cumulativeRating is the variable that holds the combination of all user given ratings ( 5 stars)
  protected int cumulativeRating;
  //numRatings is the variable that stores how many users have rated this item
  protected int numRatings;
  //creator is the variable that stores the artist, author, or director
  protected String creator;
  
  //creates a audio player to later be set up to play audio
  private AudioPlayer player;
  //creates the timer to deal with makeing sure the user can't hear the whole time unless purchased
  private Timer preveiwTimer;

  /**
  * Item is the default constructor 
  * 
  * @param lastUsedId is used to as a constant counter that assigns an id number to the item
  * @param id is used to hold the items id (can't be zero)
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
  */
  public Item() 
  {
    //PRE:lastUsed will be set to its correct value through the data loader
    //POST: lastUsedId will itereate, id will have a unique value, name, yearOfRelease,duration,genre,preview,numSold,
    //price,hidden,cumulativeRating, numRatings will have values
    
    //iterating the LastUsedId to generated the new id
    lastUsedId++;
    //sets the id to the new id
    id = lastUsedId;
    //assigns blanks to the other values
    name = "";
    yearOfRelease = 0;
    duration = 0;
    genre = "";
    preview = "";
    numSold = 0;
    price = 0;
    hidden = false;
    cumulativeRating = 0;
    numRatings = 0;
  }
  
   /**
  * userInit is the init constructor 
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
  * @param creator is the name of the artist for this album
  */
  public void userInit(int id, String name, int yearOfRelease, int duration, String genre, String preview, double price, boolean hidden, String creator) 
  {
    //PRE:lastUsed will be set to its correct value through the data loader and name, yearOfRelease, duration,
    //genre, preview, price, hidden, creator are assigned values
    //POST: lastUsedId will itereate, id will have a unique value, name, yearOfRelease,duration,genre,preview,
    //price,hidden, creator will have values set by the user
    
    //sets the artist name
    this.creator =creator;
    //sets the name
    this.name = name;
    //sets the year of the release
    this.yearOfRelease = yearOfRelease;
    //sets the duration in seconds
    this.duration = duration;
    //set the genre
    this.genre = genre;
    //set the preview
    this.preview = preview;
    //set the price
    this.price = price;
    //set the hidden
    this.hidden = hidden;
    if(id ==0)
    {
      //iterating the LastUsedId to generated the new id
      lastUsedId++;
      //sets the id to the new id
      id = lastUsedId;
    }
    //if the data loader is entering it
    else
    {
      //sets the id
      this.id = id;
    }
    //assigns blanks to the other values
    numSold = 0;
    cumulativeRating = 0;
    numRatings = 0;
  }
  
  /**
   * setName is the method used to modify the name
   * @param name is used to hold the name of the item
   * 
   */
  public void setName(String name)
  {
    //PRE: name has be assigned a value
    //POST: the new name is set
    
    //sets the new name
    this.name = name;
  }
  
  /**
   * setYearOfRelease is the method used to modify the yearOfRelease
   * @param yearOfRelease is used to hold the yearOfRelease of the item
   * 
   */
  public void setYearOfRelease(int yearOfRelease)
  {
    //PRE: yearOfRelease has be assigned a value
    //POST: the new yearOfRelease is set
    
    //sets the new yearOfRelease
    this.yearOfRelease = yearOfRelease;
  }
  
  /**
   * setDuration is the method used to modify the duration
   * @param duration is used to hold the duration of the item in seconds
   * 
   */
  public void setDuration(int duration)
  {
    //PRE: duration has be assigned a value that is positive
    //POST: the new duration is set
    
    //sets the new duration
    this.duration = duration;
  }
  
  /**
   * setGenre is the method used to modify the genre
   * @param genre is used to hold the genre of the item
   * 
   */
  public void setGenre(String genre)
  {
    //PRE: genre has be assigned a value
    //POST: the new genre is set
    
    //sets the new genre
    this.genre = genre;
  }
  
  /**
   * setPrice is the method used to modify the name
   * @param price is used to hold the name of the item
   * 
   */
  public void setPrice(double price)
  {
    //PRE: price has be assigned a value that is positive
    //POST: the new price is set
    //sets the new price
    this.price = price;
  }
  
  /**
   * setHidden is the method used to modify the hidden
   * @param hidden is used to hold the hidden of the item
   * 
   */
  public void setHidden(boolean hidden)
  {
    //PRE: hidden has be assigned a value
    //POST: the new hidden is set
    
    //sets the new hidden
    this.hidden = hidden;
  }
  
  /**
   * setLastUsedId is the method used to globally modify the lastUsedId
   * @param lastUsedId is used to hold the lastUsedId of the item
   * 
   */
  protected void setLastUsedId(int lastUsedId)
  {
    //PRE: lastUsedId has be assigned a value
    //POST: the new lastUsedId is set
    
    //sets the new lastUsedId
    this.lastUsedId = lastUsedId;
  }
  
  /**
   * setPreveiw is the method used to globally modify the preview
   * @param preview is used to hold the preview of the item
   * 
   */
  public void setPreview(String preview)
  {
    //PRE: preview has be assigned a value
    //POST: the new preview is set
    
    //sets the new preview
    this.preview = preview;
  }
  
  /**
   * getName is the accessor method used return the name of the item
   * @param name is used to hold the name of the item
   * 
   */
  public String getName()
  {
    //PRE:
    //POST: the name is returned
    
    //returns the name of the item
    return name;
  }
  
  /**
   * getId is the accessor method used return the id of the item
   * @param id is used to hold the id of the item
   * 
   */
  public int getId()
  {
    //PRE:
    //POST: the id is returned
    
    //returns the id of the item
    return id;
  }
  
  /**
   * getCumulativeRating is the accessor method used return the cumulativeRating of the item
   * @param cumulativeRating is used to hold the cumulativeRating of the item
   * 
   */
  public int getCumulativeRating()
  {
    //PRE:
    //POST: the cumulativeRating is returned
    
    //returns the cumulativeRating of the item
    return cumulativeRating;
  }
  
  public double getRating() {
      return ((double) cumulativeRating / (numRatings == 0 ? 1 : numRatings));
  }
  
  /**
   * getYearOfRelease is the accessor method used return the yearOfRelease of the item
   * @param yearOfRelease is used to hold the yearOfRelease of the item
   * 
   */
  public int getYearOfRelease()
  {
    //PRE:
    //POST: the yearOfRelease is returned
    
    //returns the yearOfRelease of the item
    return yearOfRelease;
  }
  
  /**
   * getGenre is the accessor method used return the genre of the item
   * @param genre is used to hold the genre of the item
   * 
   */
  public String getGenre()
  {
    //PRE:
    //POST: the genre is returned
    
    //returns the genre of the item
    return genre;
  }
  
  /**
   * getNumSold is the accessor method used return the numSold of the item
   * @param numSold is used to hold the numSold of the item
   * 
   */
  public int getNumSold()
  {
    //PRE:
    //POST: the numSold is returned
    
    //returns the numSold of the item
    return numSold;
  }
  
  /**
   * getNumRatings is the accessor method used return the numRatings of the item
   * @param numRatings is used to hold the numRatings of the item
   * 
   */
  public int getNumRatings()
  {
    //PRE:
    //POST: the numRatings is returned
    
    //returns the numRatings of the item
    return numRatings;
  }
  
  /**
   * getPrice is the accessor method used return the price of the item
   * @param price is used to hold the price of the item
   * 
   */
  public double getPrice()
  {
    //PRE:
    //POST: the price is returned
    
    //returns the price of the item
    return price;
  }
  
  /**
   * isVisible is the accessor method used return the hidden of the item
   * @param hidden is used to hold the hidden of the item
   * 
   */
  public boolean isVisible()
  {
    //PRE:
    //POST: the hidden is returned
    
    //returns the hidden of the item
    return hidden;
  }
  
  /**
   * getPreview is the accessor method used return the preview of the item
   * 
   */
  public String getPreview()
  {
    //PRE:
    //POST: the preview is returned
    
    //returns the preview of the item
    return preview;
  }
  
  /**
   * getHour is the accessor method used return the hour of the item
   * 
   */
  public int getHour()
  {
    //PRE:
    //POST: the hour is returned
    
    //returns the hour of the item
    return (duration / HOUR_DIVIDER);
  }
  
  /**
   * getMinute is the accessor method used return the minute of the item
   * 
   */
  public int getMinute()
  {
    //PRE:
    //POST: the minute is returned
    
    //returns the minute of the item
    return ((duration % HOUR_DIVIDER) / MINUTE_DIVIDER);
  }
  
  /**
   * getSecond is the accessor method used return the second of the item
   * 
   */
  public int getSecond()
  {
    //PRE:
    //POST: the second is returned
    
    //returns the second of the item
    return ((duration % MINUTE_DIVIDER) / SECOND_DIVIDER);
  }
  
  /**
   * getTotalDuration is the accessor method used return the totalDuration of the item
   * @param duration is used to hold the duration of the item
   * 
   */
  public int getTotalDuration()
  {
    //PRE:
    //POST: the totalDuration is returned
    
    //returns the totalDuration of the item
    return duration;
  }
  
   /**
   * getLastUsedId is the accessor method used return the lastUsedId of the item (global)
   * @param lastUsedId is used to hold the lastUsedId of the item
   * 
   */
  public static int getLastUsedId()
  {
    //PRE:
    //POST: the lastUsedId is returned
    
    //returns the lastUsedId of the item
    return lastUsedId;
  }
  
  /**
   * addRating is the method used to add or modify the rating the user gives
   * @param oldRating is used to hold the old rating the user gave (is 0 if no rating)
   * @param newRating is used to hold the new rating the user gave
   * 
   */
  public void addRating(int oldRating, int newRating)
  {
    //PRE: oldRating is assigned a value between 0 and 5 or 0 for nothing, and newRating is assigned a value
    //between 0 and 5
    //POST:the cumulativeRating and numRatings is modified
    
    //if it is a 0 in oldRating then iterate the numRatings
    if(oldRating == 0)
    {
      //sets the cumulativeRating to include the users rating
      cumulativeRating = cumulativeRating + newRating;
      
      //sets that another rating was added
      if (newRating != 0) {numRatings++;}
    }
    else
    {
      //sets the cumulativeRating to a new value
      cumulativeRating = cumulativeRating + newRating - oldRating;
      
      if (newRating==0) {numRatings--;}
    }
  }
  
  /**
   * removeRating is the method used to remove the rating of the user
   * @param oldRating is used to hold the old rating the user gave (is 0 if no rating)
   * 
   */
  public void removeRating(int oldRating)
  {
    // call addRating with a new rating of 0.
    addRating(oldRating, 0);
  }
  
  /**
  * getCreator is the accessor method used to return the director, author, or artist name
  * 
  * @param creator is the name of the director, author, or artist
  */
  public String getCreator()
  {
    //PRE:
    //POST: director, author, or artist is returned
    
    //returns the name of the director, author, or artist
    return creator;
  }
  
  /**
  * setCreator is the method used to set the director, author, or artist
  * 
  * @param creator is the name of the director, author, or artist
  */
  public void setCreator(String creator)
  {
    //PRE:director, author, or artist is assigned a value
    //POST: director, author, or artist is set
    
    //sets the director, author, or artist
    this.creator = creator;
  }
  
  public abstract void buy();

    /**
  * playAudioPreveiw is the method used play a preveiw of the audio
  * 
  * @param player is an instance of the player
  * @param preveiwTimer is an instance of the timer
  * @param PREVEIW_TIME_MILLS is the constant for how long to play for
  * @param StopPreveiwPlay is an class the is called when the preveiw should stop playing
  * 
  */
   protected void playAudioPreveiw()
  {
     //creates an instance of the audio player
    player = new AudioPlayer(preview);
    //begins the audio playback
    player.playAudio();
    //creates a new instance of the timer
    preveiwTimer = new Timer();
    //sets the schedule for the timer to stop the playback
    preveiwTimer.schedule(new StopPreveiwPlay(player), PREVEIW_TIME_MILLS); 
  }
   
   /**
  * playAudioFull is the method used play a full clip of the audio
  * 
  * @param player is an instance of the player
  * 
  */
   protected void playAudioFull()
  {
     //creates an instance of the audio player
    player = new AudioPlayer(preview);
    //begins the audio playback
    player.playAudio();
  }
   
    /**
  * stopAudio is the method used stop a preveiw of the audio
  * 
  * @param player is an instance of the player
  * 
  */
   protected void stopAudio()
   {
     //stops the playback
     player.stopPlayBack();
   }
   
   /**
 * This class is the StopPreveiwPlay class that is used to stop the preveiw playback when the set time is up
 * @author Jonathan Maderic
 * @version 1.0 2/13/13
 */
   class StopPreveiwPlay extends TimerTask
   {
     //creates an instance of the play back to hold the playback begin stopped
     AudioPlayer player;
     
     /**
      * StopPreveiwPlay is the init constructor that atkes int he player curretnyl playing
      * 
      * @param player is an instance of the player
      * 
      */
     public StopPreveiwPlay(AudioPlayer player)
     {
       this.player = player;
     }
     
     /**
      * run is the method called whent he timer is done ticking and is public because it has to be accessed by the
      * system in TimerTask
      * 
      * @param player is an instance of the player
      * @param preveiwTimer is an instance of the timer
      * 
      */
     public void run()
     {
       //stosp the playback at the set amount of preivew time
       player.stopPlayBack();
       //stops the timer from ticking
       preveiwTimer.cancel();
     }
   }
  
}