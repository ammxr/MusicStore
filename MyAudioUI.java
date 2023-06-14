// Name: Ammar Hakim
// Student #: 501173795

import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;


// Simulation of a Simple Text-based Music App (like Apple Music)

public class MyAudioUI
{
	public static void main(String[] args) throws FileNotFoundException
	{
		// Simulation of audio content in an online store
		// The songs, podcasts, audiobooks in the store can be downloaded to your library
		AudioContentStore store = new AudioContentStore();
		
		// Create my music library
		Library library = new Library();

		Scanner scanner = new Scanner(System.in);
		System.out.print(">");

		// Process keyboard actions
		while (scanner.hasNextLine())
		{
			String action = scanner.nextLine();

			if (action == null || action.equals("")) 
			{
				System.out.print("\n>");
				continue;
			}
			else if (action.equalsIgnoreCase("Q") || action.equalsIgnoreCase("QUIT"))
				return;
			
			else if (action.equalsIgnoreCase("STORE"))	// List all songs
			{
				store.listAll(); 
			}
			else if (action.equalsIgnoreCase("SONGS"))	// List all songs
			{
				library.listAllSongs(); 
			}
			else if (action.equalsIgnoreCase("BOOKS"))	// List all songs
			{
				library.listAllAudioBooks(); 
			}
			else if (action.equalsIgnoreCase("PODCASTS"))	// List all songs
			{
				library.listAllPodcasts(); 
			}
			else if (action.equalsIgnoreCase("ARTISTS"))	// List all songs
			{
				library.listAllArtists(); 
			}
			else if (action.equalsIgnoreCase("PLAYLISTS"))	// List all play lists
			{
				library.listAllPlaylists(); 
			}
			else if (action.equalsIgnoreCase("DOWNLOAD")) 
			{
				int fromIndex = 0;
				int toIndex = 0;

				// Inputs
				System.out.print("From Store Content #: ");
				if (scanner.hasNextInt())
				{
					fromIndex = scanner.nextInt();
				}
				System.out.print("To Store Content #: ");
				if (scanner.hasNextInt())
				{
					toIndex = scanner.nextInt();
				}
				scanner.nextLine(); // Consume nl 
				
				// Downloading each index in range
				for (int i = fromIndex; i<=toIndex; i++){
					AudioContent content = store.getContent(i);
					try{
						library.download(content);
					}
					catch (DuplicateContentFoundException e){
						System.out.println(e.getMessage());
					}
					catch (Exception e){
						System.out.println("Invalid input (enter Int) in valid range");
					} 
				}		
			}
			
			else if (action.equalsIgnoreCase("PLAYSONG")) 
			{
				try {
					int index = 0;
					
					// Input
					System.out.print("Song Number: ");
					if (scanner.hasNextInt())
					{
						index = scanner.nextInt();
						// consume the nl character since nextInt() does not
						scanner.nextLine(); 
					}
					library.playSong(index);
				
				// If any error occurs catch the exception
				} catch (AudioContentNotFoundException e){
					System.out.println(e.getMessage());
				}
			}

			else if (action.equalsIgnoreCase("BOOKTOC")) 
			{
				int index = 0;

				System.out.print("Audio Book Number: ");
				try {
					if (scanner.hasNextInt())
					{
						index = scanner.nextInt();
						scanner.nextLine();
					}
					library.printAudioBookTOC(index);
				
				// If error occurs handle exception
				} catch (AudioContentNotFoundException e){
					System.out.println(e.getMessage());
				}
			}
			else if (action.equalsIgnoreCase("PLAYBOOK")) 
			{
				try {
					int index = 0;

					System.out.print("Audio Book Number: ");
					if (scanner.hasNextInt())
					{
						index = scanner.nextInt();
					}
					int chapter = 0;
					System.out.print("Chapter: ");
					if (scanner.hasNextInt())
					{
						chapter = scanner.nextInt();
						scanner.nextLine();
					}
					library.playAudioBook(index, chapter);	
				}

				// If error occurs handle exception
				catch (AudioContentNotFoundException e){
					System.out.println(e.getMessage());
				}
			}

			else if (action.equalsIgnoreCase("PODTOC")) 
			{
				try {
					int index = 0;
					int season = 0;
					
					System.out.print("Podcast Number: ");
					if (scanner.hasNextInt())
					{
						index = scanner.nextInt();
					}
					System.out.print("Season: ");
					if (scanner.hasNextInt())
					{
						season = scanner.nextInt();
						scanner.nextLine();
					}
					library.printPodcastEpisodes(index, season);
				}
				catch (AudioContentNotFoundException e){
					System.out.println("PODCASTS NOT SUPPORTED");
				}
			}
			else if (action.equalsIgnoreCase("PLAYPOD")) 
			{
				int index = 0;

				System.out.print("Podcast Number: ");
				if (scanner.hasNextInt())
				{
					index = scanner.nextInt();
					scanner.nextLine();
				}
				int season = 0;
				System.out.print("Season: ");
				if (scanner.hasNextInt())
				{
					season = scanner.nextInt();
					scanner.nextLine();
				}
				int episode = 0;
				System.out.print("Episode: ");
				if (scanner.hasNextInt())
				{
					episode = scanner.nextInt();
					scanner.nextLine();
				}
				library.playPodcast(index, season, episode);	
			}

			else if (action.equalsIgnoreCase("PLAYALLPL")) 
			{
				try{
					String title = "";
					// Inputs
					System.out.print("Playlist Title: ");
					if (scanner.hasNextLine())
					{
						title = scanner.nextLine();
					}
					library.playPlaylist(title);	
				// If Playlist not found handle in exception
				} catch (AudioContentNotFoundException e){
					System.out.println(e.getMessage());
				}
			}
			else if (action.equalsIgnoreCase("PLAYPL")) 
			{
				try {
					String title = "";
					int index = 0;
					// Inputs
					System.out.print("Playlist Title: ");
					if (scanner.hasNextLine())
					{
						title = scanner.nextLine();
					}
					System.out.print("Content Number: ");
					if (scanner.hasNextInt())
					{
						index = scanner.nextInt();
						scanner.nextLine();
					}
					library.playPlaylist(title, index);
				// If Playlist or Content number not found handle exception 
				} catch (AudioContentNotFoundException e){
					System.out.println(e.getMessage());
				}
			}
			// Delete a song from the library and any play lists it belongs to
			else if (action.equalsIgnoreCase("DELSONG")) 
			{
				int songNum = 0;

				// Inputs
				System.out.print("Library Song #: ");
				if (scanner.hasNextInt())
				{
					songNum = scanner.nextInt();
					scanner.nextLine();
				}
				try {
					library.deleteSong(songNum);	
				}
				// Index out of range Exceptions 
				catch (AudioContentNotFoundException e){
					System.out.println("Invalid Index. Must be integer");
				}
				catch (IndexOutOfBoundsException e){
					System.out.println("Index " + songNum + " out of range");
				}
			}


			else if (action.equalsIgnoreCase("MAKEPL")) 
			{
				String title = "";
				// Inputs
				System.out.print("Playlist Title: ");
				if (scanner.hasNextLine())
				{
					title = scanner.nextLine();
				}
				try{
					library.makePlaylist(title);

				// If playlist already exists catch exception
				} catch (DuplicateContentFoundException e){
					System.out.println(e.getMessage());
				}
			}


			else if (action.equalsIgnoreCase("PRINTPL"))	// print playlist content
			{
				String title = "";
				// Inputs
				System.out.print("Playlist Title: ");
				if (scanner.hasNextLine())
					title = scanner.nextLine();
				try{
					library.printPlaylist(title);
				}
				// If playlist not found handle exception
				catch (AudioContentNotFoundException e){
					System.out.println(e.getMessage());
				}
			}

			
			// Add content from library (via index) to a playlist
			else if (action.equalsIgnoreCase("ADDTOPL")) 
			{
				int contentIndex = 0;
				String contentType = "";
				String playlist = "";
				
				// Inputs
				System.out.print("Playlist Title: ");
				if (scanner.hasNextLine())
					playlist = scanner.nextLine();
        
				System.out.print("Content Type [SONG, PODCAST, AUDIOBOOK]: ");
				if (scanner.hasNextLine())
					contentType = scanner.nextLine();
				
				System.out.print("Library Content #: ");
				if (scanner.hasNextInt())
				{
					contentIndex = scanner.nextInt();
					scanner.nextLine(); // consume nl
				}
				try {
					library.addContentToPlaylist(contentType, contentIndex, playlist);
				}
				// Handling exceptions for duplicates, invalid types and not existing contents 
				catch (AudioContentNotFoundException e) {
					System.out.println(e.getMessage());
				}
				catch (DuplicateContentFoundException e) {
					System.out.println(e.getMessage());
				}
				catch (InputMismatchException e){
					System.out.println("Invalid Input");
				}	
			}  
			// Delete content from play list
			else if (action.equalsIgnoreCase("DELFROMPL")) 
			{
				int contentIndex = 0;
				String playlist = "";

				System.out.print("Playlist Title: ");
				if (scanner.hasNextLine())
					playlist = scanner.nextLine();
				
				System.out.print("Playlist Content #: ");
				if (scanner.hasNextInt())
				{
					contentIndex = scanner.nextInt();
					scanner.nextLine(); // consume nl
				}
				try {
					library.delContentFromPlaylist(contentIndex, playlist);	
				} catch (AudioContentNotFoundException e){
					System.out.println(e.getMessage());
				}
			}


			// Basic Title Search
			else if (action.equalsIgnoreCase("SEARCH")) 
			{
				System.out.print("Title: ");
				String title = scanner.nextLine();
				store.search(title);
			}
			
			// Searching Contents by Artist
			else if (action.equalsIgnoreCase("SEARCHA")) 
			{
				System.out.print("Artist: ");
				String artist = scanner.nextLine();
				store.searchA(artist);
			}

			// Searching Contents by Genre
			else if (action.equalsIgnoreCase("SEARCHG")) 
			{
				System.out.print("Genre [POP, ROCK, JAZZ, HIPHOP, RAP, CLASSICAL]: ");
				String genre = scanner.nextLine();
				store.searchG(genre);
			}

			// Downloading all contents by Artist
			else if (action.equalsIgnoreCase("DOWNLOADA")) 
			{
				System.out.print("Artist: ");
				String artist = scanner.nextLine();
				try {
					for (Integer i : store.downloadA(artist)){
						AudioContent content = store.getContent(i);
						try{
							library.download(content);
						} catch (DuplicateContentFoundException e) {
							System.out.println(e.getMessage());
						}
					}
				} catch (NoMatchesException e) {
					System.out.println(e.getMessage());
				}
			}

			// Downloading all contents by Genre
			else if (action.equalsIgnoreCase("DOWNLOADG")) 
			{
				System.out.print("Genre: ");
				String genre = scanner.nextLine();
				try {
					for (Integer i : store.downloadG(genre)){
						AudioContent content = store.getContent(i);
						try{
							library.download(content);
						} catch (DuplicateContentFoundException e) {
							System.out.println(e.getMessage());
						}
					}
				} catch (NoMatchesException e) {
					System.out.println(e.getMessage());
				}
			}

			// BONUS: Partial String Search. Searching all contents through all parameters/details including lyrics/audioFile
			else if (action.equalsIgnoreCase("SEARCHP")) 
			{
				System.out.print("Search Partial: ");
				String searchQuery = scanner.nextLine();
				store.searchP(searchQuery);
			}


			// Sorting
			else if (action.equalsIgnoreCase("SORTBYYEAR")) // sort songs by year
			{
				library.sortSongsByYear();
			}
			else if (action.equalsIgnoreCase("SORTBYNAME")) // sort songs by name (alphabetic)
			{
				library.sortSongsByName();
			}
			else if (action.equalsIgnoreCase("SORTBYLENGTH")) // sort songs by length
			{
				library.sortSongsByLength();
			}

			System.out.print("\n>");
		}
	}
}
