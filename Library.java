// Name: Ammar Hakim
// Student #: 501173795

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/*
 * This class manages, stores, and plays audio content such as songs, podcasts and audiobooks. 
 */
public class Library
{
	private ArrayList<Song> 			songs; 
	private ArrayList<Podcast> 		podcasts;
	private ArrayList<AudioBook> 	audiobooks;
	private ArrayList<Playlist> 	playlists; 
		
	// Public methods in this class set errorMsg string 
	// Error Messages can be retrieved from main in class MyAudioUI by calling  getErrorMessage()
	// In assignment 2 we will replace this with Java Exceptions
	String errorMsg = "";
	
	public String getErrorMessage()
	{
		return errorMsg;
	}

	public Library()
	{
		songs 			= new ArrayList<Song>(); 
		podcasts		= new ArrayList<Podcast>(); ;
		audiobooks 	= new ArrayList<AudioBook>(); ;
		playlists   = new ArrayList<Playlist>(); 
	}
	/*
	 * Download audio content from the store. Since we have decided (design decision) to keep 3 separate lists in our library
	 * to store our songs, podcasts and audiobooks (we could have used one list) then we need to look at the type of
	 * audio content to determine which list it belongs to above
	 * 
	 * Make sure you do not add song/podcast/audiobook to a list if it is already there. Hint: use the equals() method
	 * If it is already in a list, set the errorMsg string and return false. Otherwise add it to the list and return true
	 */
	public void download(AudioContent content) throws DuplicateContentFoundException 
	{
		if (content.getType().equals(Song.TYPENAME)){
			// If Song is already downloaded, throw exception
			if (songs.contains(content)){
				throw new DuplicateContentFoundException("Song " + content.getTitle() + " already downloaded");
			}
			else{
				System.out.println("SONG " + content.getTitle() + " Added to Library");
				songs.add((Song)content);
			}
		}
		else if (content.getType().equals(AudioBook.TYPENAME)){
			// If AudioBook is already downloaded, throw exception
			if (audiobooks.contains(content)){
				throw new DuplicateContentFoundException("AudioBook " + content.getTitle() + " already downloaded");
			}
			else{
				System.out.println("AUDIOBOOK " + content.getTitle() + " Added to Library");
				audiobooks.add((AudioBook)content);
			}
		}
		// If Podcast is already downloaded, throw exception
		if (content.getType().equals(Podcast.TYPENAME)){
			if (podcasts.contains(content)){
				throw new DuplicateContentFoundException("Podcast " + content.getTitle() + " already downloaded");
			}
			else{
				System.out.println("PODCAST " + content.getTitle() + " Added to Library");
				podcasts.add((Podcast)content);
			}
		}
	}
	
	// Print Information (printInfo()) about all songs in the array list
	public void listAllSongs()
	{
		// Iterate and printInfo of each downloaded song
		for (int i = 0; i < songs.size(); i++)
		{
			int index = i + 1;
			System.out.print(index + ". ");
			songs.get(i).printInfo();
			System.out.println();	
		}
	}
	
	// Print Information (printInfo()) about all audiobooks in the array list
	public void listAllAudioBooks()
	{
		// Iterate and printinfo of each downloaded audiobook
		for (int i = 0; i < audiobooks.size(); i++)
		{
			int index = i + 1;
			System.out.print(index + ". ");
			audiobooks.get(i).printInfo();
			System.out.println();	
		}
	}
	
  // Print Information (printInfo()) about all podcasts in the array list
	public void listAllPodcasts()
	{
		// Iterate and printinfo of each downloaded Podcast
		for (int i = 0; i < podcasts.size(); i++)
		{
			int index = i+1;
			System.out.print(index + ". ");
			podcasts.get(i).printInfo();
		}
	}
	
  // Print the name of all playlists in the playlists array list
	// First print the index number as in listAllSongs() above
	public void listAllPlaylists()
	{
		for (int i = 0; i < playlists.size(); i++)
		{
			int index = i+1;
			System.out.println(index + ". " +  playlists.get(i).getTitle());
		}
	}
	
  // Print the name of all artists. 
	// Go through the songs array list and add to an arraylist only if it is
	// not already there. Once the artist arraylist is complete, print the artists names
	public void listAllArtists()
	{
		ArrayList<String> artists = new ArrayList<String>();
		// Iterate through songs library and retrieve/print all artists
		for (Song song : songs)
		{
			if (!artists.contains(song.getArtist()))
				artists.add(song.getArtist());
		}
		for (int i = 0; i < artists.size(); i++)
		{
			int index = i+1;
			System.out.println(index + ". " + artists.get(i));
		}
	}

	// Delete a song from the library (i.e. the songs list) - 
	// also go through all playlists and remove it from any playlist as well
	public void deleteSong(int index)
	{
		// If Index is in range of the min and max of the songs library range delete song
		if (index>=0 && index <=songs.size()){
			Song song = songs.remove(index-1);
			// Search all playlists for song and delete from each
			for (int i = 0; i < playlists.size(); i++) {
				Playlist pl = playlists.get(i);
				if (pl.getContent().contains(song))
					pl.getContent().remove(song);
			}
		// Otherwise throw exception
		} else{
			throw new AudioContentNotFoundException("Invalid Index");
		}

	}
	
  //Sort songs in library by year
	public void sortSongsByYear()
	{
		Collections.sort(songs, new SongYearComparator());
	}

	private class SongYearComparator implements Comparator<Song>
	{
		public int compare(Song a, Song b)
		{
			if (a.getYear() > b.getYear()) return 1;
			if (a.getYear() < b.getYear()) return -1;	
			return 0;
		}
	}

	// Sort songs by length
	public void sortSongsByLength()
	{
		Collections.sort(songs, new SongLengthComparator());
	}

	private class SongLengthComparator implements Comparator<Song>
	{
		public int compare(Song a, Song b)
		{
			if (a.getLength() > b.getLength()) return 1;
			if (a.getLength() < b.getLength()) return -1;	
			return 0;
		}
	}

	// Sort songs by title (Comparable)
	public void sortSongsByName()
	{
		Collections.sort(songs);
	}

	
	
	/*
	 * Play Content
	 */
	
	// Play song from songs list
	public void playSong(int index)
	{
		if (index < 1 || index > songs.size())
		{
			throw new AudioContentNotFoundException("Content not found: (Index out of range)");
		}
		songs.get(index-1).play();
	}
	
	// Play podcast from list
	public boolean playPodcast(int index, int season, int episode)
	{
		if (index < 1 || index > podcasts.size())
		{
			errorMsg = "Podcast Not Found";
			return false;
		}
		Podcast podcast = podcasts.get(index-1);
		if (season < 1 || season > podcast.getSeasons().size())
		{
			errorMsg = "Season Not Found";
			return false;
		}
		
		if (index < 1 || index > podcast.getSeasons().get(season-1).episodeTitles.size())
		{
			errorMsg = "Episode Not Found";
			return false;
		}
		podcast.setSeason(season-1);
		podcast.setEpisode(episode-1);
		podcast.play();
		return true;
	}
	
	public boolean printPodcastEpisodes(int index, int season)
	{
		if (index < 1 || index > podcasts.size())
		{
			errorMsg = "Podcast Not Found";
			return false;
		}
		Podcast podcast = podcasts.get(index-1);
		podcast.printSeasonEpisodes(season);
		return true;
	}
	
	// Play audio book from list
	public void playAudioBook(int index, int chapter)
	{
		// Check if index in range else throw exception
		if (index < 1 || index > audiobooks.size())
		{
			throw new AudioContentNotFoundException("AudioBook Not Found");
		}
		// Get AudioBook object check if chapters in range
		AudioBook book = audiobooks.get(index-1);
		if (chapter < 1 || chapter > book.getNumberOfChapters())
		{
			throw new AudioContentNotFoundException("AudioBook Chapter Not Found");
		}
		// Play Book
		book.selectChapter(chapter);
		book.play();
	}
	
	public void printAudioBookTOC(int index)
	{
		// Check if index in range
		if (index < 1 || index > audiobooks.size())
		{
			throw new AudioContentNotFoundException("AudioBook Not Found");
		}
		AudioBook book = audiobooks.get(index-1);
		book.printTOC();
	}
	
  /*
   * Playlist Related Methods
   */
	
	// Make a new playlist 
	// Make sure a playlist with the same title doesn't already exist
	public void makePlaylist(String title)
	{
		Playlist pl = new Playlist(title);
		// Check if playlist exists, throw exception if yes
		if (playlists.contains(pl))
		{
				throw new DuplicateContentFoundException("Playlist " + title + " Already Exists");
		}
		// else add playlist
		playlists.add(pl);
	}
	
	// Print list of content (songs, audiobooks etc) in playlist named title from list of playlists
	public void printPlaylist(String title)
	{
		int index = playlists.indexOf(new Playlist(title));
		
		if (index == -1)
		{
			throw new AudioContentNotFoundException("Playlist Not Found");
		}
		playlists.get(index).printContents();
	}
	
	// Play all content in a playlist
	public void playPlaylist(String playlistTitle)
	{
		int index = playlists.indexOf(new Playlist(playlistTitle));

		if (index == -1)
		{
			throw new AudioContentNotFoundException("Playlist Not Found");
		}
		playlists.get(index).playAll();

	}
	
	// Play a specific song/audiobook in a playlist
	public void playPlaylist(String playlistTitle, int index)
	{
		int plIndex = playlists.indexOf(new Playlist(playlistTitle));
		
		// Throw exception if playlist title invalid
		if (plIndex == -1)
		{
			throw new AudioContentNotFoundException("Playlist Not Found");
		}
		Playlist pl = playlists.get(plIndex);
		
		// Check if index in range
		if (index < 1 || index > pl.getContent().size())
		{
			throw new AudioContentNotFoundException("Invalid Playlist AudioContent #");
		}
		System.out.println(pl.getTitle());
		
		// Play chapter 1 if this is an audio book. Could also set it to play all
		if (pl.getContent(index).getType().equals(AudioBook.TYPENAME))
		{
			AudioBook book = (AudioBook) pl.getContent(index);
			book.selectChapter(1);
		}

		pl.play(index);
	}

	// Getter method (as helper) for next Method
	public Playlist getPlaylist(String playlistTitle){
		for (int i = 0; i < playlists.size(); i++){
			if (playlists.get(i).getTitle().equals(playlistTitle)){
				return playlists.get(i);
			}
		}
		return null;
	}

	// Add a song/audiobook/podcast from library to a playlist
	public void addContentToPlaylist(String type, int index, String playlistTitle)
	{
		if (type.equalsIgnoreCase("SONG"))
		{
			// Check if index in range
			if (index < 1 || index > songs.size())
			{
				throw new AudioContentNotFoundException("Song not found");
			}
			Song ac = songs.get(index-1);
			this.getPlaylist(playlistTitle).addContent(ac);
		}
 
		else if (type.equalsIgnoreCase("AUDIOBOOK"))
		{
			// Check if index in range
			if (index < 1 || index > audiobooks.size())
			{
				throw new AudioContentNotFoundException("AudioBook not found");
			}
			AudioBook ac = audiobooks.get(index-1);
			this.getPlaylist(playlistTitle).addContent(ac);
		}
		else if (type.equalsIgnoreCase("PODCAST"))
		{
			// Check if index in range
			if (index < 1 || index > podcasts.size())
			{
				throw new AudioContentNotFoundException("Podcast not found");
			}
			Podcast ac = podcasts.get(index-1);
			this.getPlaylist(playlistTitle).addContent(ac);
			
		}
	}

  // Delete a song/audiobook/podcast from a playlist
	// Make sure the index is valid
	public void delContentFromPlaylist(int index, String playlistTitle)
	{
		int plIndex = playlists.indexOf(new Playlist(playlistTitle));
		// Check if playlist found otherwise throw exception
		if (plIndex == -1)
		{
			throw new AudioContentNotFoundException("Playlist not found");
		}
		Playlist pl = playlists.get(plIndex);
		
		// If Playlist found, delete content otherwise throw 
		if (!pl.contains(index))
		{
			throw new AudioContentNotFoundException("Content not found");
		}
		pl.deleteContent(index);
	}
}

// Exceptions
class AudioContentNotFoundException extends RuntimeException {
	public AudioContentNotFoundException(String message) {
		super(message); 
	}
}
class FileNotFoundException extends RuntimeException {
	public FileNotFoundException(String message) {
		super(message); 
	}
}
class DuplicateContentFoundException extends RuntimeException {
	public DuplicateContentFoundException(String message) {
		super(message);
	}
}
class InvalidIndexTypeException extends RuntimeException {
	public InvalidIndexTypeException(String message) {
		super(message);
	}
}
class NoMatchesException extends RuntimeException {
	public NoMatchesException(String message) {
		super("No matches for " + message);
	}
}