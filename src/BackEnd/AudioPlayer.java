/**
 * Name:Jonathan Maderic Section:1 Program:Final Project Phase 1 Date: 2/13/13
 * Description: This class is the AudioPlayer class that playes the audio preveiws in .au
 * format only
 */
package BackEnd;

import java.io.*;
import javax.sound.sampled.*;

/**
 * This class is the AudioPlayer class that is used to playback previews
 *
 * @author Jonathan Maderic
 * @version 1.0 2/13/13
 */
public class AudioPlayer {
	//holds the audio format of the input

	private AudioFormat audioFormat;
	//defines the stream of the input and holds the input audio stream
	private AudioInputStream audioInput;
	//holds the output stream
	private SourceDataLine output;
	//holds the files name - file must be stored in the local directory and must be in *.au format
	private String fileName = "";
	//holds the boolean value in case the user wants to stop the playback
	private boolean stop = true;

	/**
	 * AudioPlayer is the init constructor that takes int eh file name
	 *
	 * @param fileName is the string that holds the file path within the local directory
	 * to the file trying to be played
	 * @param fileAudio is the string that holds the file path within the local directory
	 * to the file trying to be played
	 */
	public AudioPlayer(String fileAudio) {
		//PRE:fileAudio is assigned the local file path
		//POST: the file name is set
		//sets the file name to the one being preveiwed
		this.fileName = fileAudio;
	}

	/**
	 * stopPlayBack is the method that is used to stop the playBack of the the file
	 *
	 * @param stop is the boolean member that stops playback of audio if false
	 */
	protected void stopPlayBack() {
		//sets teh stream to stop playing
		stop = false;
	}

	/**
	 * playAudio is the method used to get the audio file and find out if it can be
	 * played as well as the format
	 *
	 * @param fileName is the string that holds the file path within the local directory
	 * to the file trying to be played
	 * @param file is the File data type holding the exact file path and is used to get
	 * the input stream
	 * @param output is the output stream running tot he speakers of the computer
	 * @param audioInput is the input stream pointing to the file location
	 * @param audioFormat holds the format of the current audio file
	 * @param dataLine holds the info of the audio format which include the file and this
	 * is //used to create the output stream
	 */
	protected void playAudio() {
		//PRE: The fileName has a value and the file is in the correct format and location. Also the computer has speakers
		//POST: After all the intermediate steps are done the output stream is ready and begins loading the thread to play
		//sets stop to true to allow the playback of the audio
		stop = true;
		try {
			//converts the string with the file name and local path to the file path
			File file = new File(fileName);
			//generates the input stream based on the file path
			audioInput = AudioSystem.getAudioInputStream(file);
			//finds the format of the audio wanting to be played
			audioFormat = audioInput.getFormat();
			//gets the information needed to set up the output stream
			DataLine.Info dataLine = new DataLine.Info(SourceDataLine.class, audioFormat);
			//sets up the output stream using the data aquired
			output = (SourceDataLine) AudioSystem.getLine(dataLine);
			//starts the playback stream to the speakers
			new PlayThread().start();
		} //cataches any errors in setting up the streams for file playback
		catch (Exception playBackError) {
			System.out.println("PlayBack setup error: " + playBackError);
		}
	}

	/**
	 * This class is the PlayThread class that is used to create the output thread for
	 * audio playback
	 *
	 * @author Jonathan Maderic
	 * @version 1.0 2/13/13
	 */
	class PlayThread extends Thread {
		//creates a new byte to hold the output buffer

		byte buffer[] = new byte[10000];

		/**
		 * run is the method used to send the audio data fromt eh buffer to the output
		 * stream (the speakers) is public becaus ethe system needs to access this to use
		 * the speakers as playback
		 *
		 * @param output is the output stream running tot he speakers of the computer
		 * @param audioInput is the input stream popinting to the file location
		 * @param audioFormat holds the format of the current audio file
		 * @param buffer is the byte that holds the temporary audio data as it is being
		 * sent out to the speakers
		 * @param streamSize is the integer that holds the remain number of audio bytes
		 * left to be played
		 * @param stop is the boolean that tells the thread to stop streaming to the
		 * speakers if the user //wants the playback to be stopped
		 */
		public void run() {
			//PRE: the member above are set
			//POST: the music will begin playing out the speakers unless the song ends or the user stops it or the program is
			//preveiwing
			try {
				//sets up an output stream to the speakers with the format type of the input
				output.open(audioFormat);
				//set the output stream to start streaming
				output.start();

				//sets the stream size to the input buffer Size ( declared here because this can cause an exception)
				int streamSize = audioInput.read(buffer, 0, buffer.length);

				//if the input still has data left to be played or the stop boolean is set to false continue outputing
				while (streamSize > 0 && stop != false) {
					//transfers the buffer tot the speaker output stream
					output.write(buffer, 0, streamSize);
					//set the stream size to the current size remaining in the buffer set to the remain part of the song
					streamSize = audioInput.read(buffer, 0, buffer.length);
				}
			} //cataches any errors in streamign the file to the speakers for playeback
			catch (Exception playBackError) {
				System.out.println("PlayBack error: " + playBackError);
			}
		}
	}
}
