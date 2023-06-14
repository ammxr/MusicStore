// Name: Ammar Hakim
// Student #: 501173795

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;


// Simulation of audio content in an online store
// The songs, podcasts, audiobooks listed here can be "downloaded" to your library

public class AudioContentStore
{
		HashMap<String, Integer> titleIndexMap = new HashMap<>();
		HashMap<String, ArrayList<Integer>> artistsIndexMap = new HashMap<>();
		HashMap<Song.Genre, ArrayList<Integer>> genreIndexMap = new HashMap<>();
		ArrayList<String> allInfoList = new ArrayList<>();
		private ArrayList<AudioContent> contents; 

		public AudioContentStore() throws FileNotFoundException {
			contents = new ArrayList<AudioContent>();
			try {
				File store = new File("store.txt");
				Scanner scanner = new Scanner(store);
				while (scanner.hasNextLine()) {
					String type = scanner.nextLine();

					// If Line defines object type Song get corresponding attributes based off the following lines
					if (type.equals("SONG")) {
						String id = scanner.nextLine();
						String title = scanner.nextLine();
						
						// Converting year and length to Int
						int year = Integer.parseInt(scanner.nextLine());
						int length = Integer.parseInt(scanner.nextLine());
						String author = scanner.nextLine();
						String composer = scanner.nextLine();
						String genreString = scanner.nextLine();
						Song.Genre genre = Song.Genre.valueOf(genreString);
						int numLines = Integer.parseInt(scanner.nextLine());
						String file = "";
						for (int i = 0; i < numLines; i++) {
							file += scanner.nextLine() + "\n";
						}
						// Adding Song object to contents
						contents.add(new Song(title, year, id, type, file, length, author, composer, genre, file));
						// List all data/info for Partial String Search
						allInfoList.add((title + " " + year + " " + id + " " + type + " " + file + " " + length + " " + author + " " + composer + " " + genre + " " + file));
						System.out.println("Loading SONG");
						
					// If Line defines object type AudioBook get corresponding attributes based off the following lines
					} else if (type.equals("AUDIOBOOK")) {
						String id = scanner.nextLine();
						String title = scanner.nextLine();

						// Converting year and length to Int
						int year = Integer.parseInt(scanner.nextLine());
						int length = Integer.parseInt(scanner.nextLine());
						String author = scanner.nextLine();
						String narrator = scanner.nextLine();
						int numChapters = Integer.parseInt(scanner.nextLine());
						ArrayList<String> chapterTitles = new ArrayList<String>();
						ArrayList<String> chapters = new ArrayList<String>();
						for (int i = 0; i < numChapters; i++) {
							chapterTitles.add(scanner.nextLine());
						}

						// Get Chapter Titles alongside their contents
						String chapterLines = "";
						for (int i = 0; i < numChapters; i++) {
							int numLines = Integer.parseInt(scanner.nextLine());
							for (int j = 0; j < numLines; j++) {
								chapterLines += scanner.nextLine() + "\n";
							}
							chapters.add(chapterLines);
						}

						// Adding Song object to contents
						contents.add(new AudioBook(title, year, id, type, "", length, author, narrator, chapterTitles, chapters));
						// List all data/info for Partial String Search
						allInfoList.add((title + " " + year + " " + id + " " + type + " " + length + " " + author + " " + narrator + " " + chapterTitles + " " + chapters));
						System.out.println("Loading AUDIOBOOK");
					}
				}
				scanner.close();
			// If file fails to open Exit Program
			} catch (FileNotFoundException e) {
				System.out.println(e.getMessage() + ". EXITING PROGRAM");
				System.exit(1);
			}
			
			// TITLE-INDEX HASHMAP
			int i = 1;
			for (AudioContent content : contents) {
				if (content.getType().equals(Song.TYPENAME)) {
					String title = ((Song) content).getTitle();
					// Assigning title (key) with corresponding store index (value)
					titleIndexMap.put(title, i);
				} else if (content.getType().equals(AudioBook.TYPENAME)) {
					String title = ((AudioBook) content).getTitle();
					// Assigning title (key) with corresponding store index (value)
					titleIndexMap.put(title, i);
				}
				i++;
			}
			
			// ARTIST-INDEX HASHMAP
			int j = 1;
			for (AudioContent content : contents){
				String name = "";
				// Get object Artist/Author/Host
				if (content.getType().equals(Song.TYPENAME)){
					name = ((Song) content).getArtist();
				}
				else if (content.getType().equals(AudioBook.TYPENAME)){
					name = ((AudioBook) content).getAuthor();
				}
				else if (content.getType().equals(Podcast.TYPENAME)){
					name = ((Podcast) content).getHost();
				}
				// Skip loop iteration if None of the Above Object Types
				else{
					continue;
				}

				// If artist not added to hashmap. Add artist and write corresponding index
				if (!artistsIndexMap.containsKey(name)){
					artistsIndexMap.put(name, new ArrayList<Integer>());
					artistsIndexMap.get(name).add(j);
				}
				// Else, add the index to the list of indices
				else{
					artistsIndexMap.get(name).add(j);
				}
				j++;
			}

			// GENRE-INDEX HASHMAP
			int k = 1;

			for (AudioContent content : contents) {
				if (content.getType().equals(Song.TYPENAME)) {
					// Get Genre of type ENUM Genre
					Song.Genre genre = ((Song) content).getGenre();
					// If genre not added to hashmap. Add genre and write corresponding index
					if (!genreIndexMap.containsKey(genre)) {
						genreIndexMap.put(genre, new ArrayList<Integer>());
						genreIndexMap.get(genre).add(k);
					}
					// Else, add the index to the list of indices
					else {
						genreIndexMap.get(genre).add(k);
					}
				}
				k++;
			}
		}
		
		// Iterate and Get all contents from contents array list
		public AudioContent getContent(int index)
		{
			if (index < 1 || index > contents.size())
			{
				return null;
			}
			return contents.get(index-1);
		}
		
		// Print all contents
		public void listAll()
		{
			for (int i = 0; i < contents.size(); i++)
			{
				int index = i + 1;
				System.out.print(index + ". ");
				contents.get(i).printInfo();
				System.out.println();
			}
		}


		
	public void search(String title)
	{
		// Get Set of all Keys 
		Set<String> keySet = titleIndexMap.keySet();
		// Iterate through keys (titles)
		for (String key : keySet) {
			// Get their index
			Integer index = titleIndexMap.get(key);
			// If matching title found printinfo and return
			if (key.equals(title)){
				System.out.print((index) + ". ");
				contents.get(index-1).printInfo();
				return;
			}
		}
		System.out.println("No matches for " + title);
	}

	public void searchA(String artist)
	{
		// Get Set of all Keys  
		Set<String> keySet = artistsIndexMap.keySet();
		// Iterate through keys (Artists)
		for (String key : keySet) {
			// Get their indices
			ArrayList<Integer> indices = artistsIndexMap.get(key);
			// If matching artist found printInfo and return
			if (key.equals(artist)){
				for (Integer index : indices){
					System.out.print(index + ". ");
					contents.get(index-1).printInfo();
					System.out.println();
				}
				return;
			}
		}
		System.out.println("No matches for " + artist);
	}

	public void searchG(String genre)
	{
		// Get Set of all Keys
		Set<Song.Genre> keySet = genreIndexMap.keySet();
		// Iterate through keys (genres)
		for (Song.Genre key : keySet) {
			// If matching artist found printInfo and return
			if (key.name().equals(genre)){
				ArrayList<Integer> indices = genreIndexMap.get(key);
				// Go through all indices and printInfo of each match
				for (Integer index : indices){
					System.out.print((index) + ". ");
					contents.get(index-1).printInfo();
					System.out.println();
				}
				return;
			}
		}
		System.out.println("No matches for " + genre);
	}
	
	public ArrayList<Integer> downloadA(String artist)
	{
		// Found variable to determine exception need
		boolean found = false;

		// Get Key Set
		Set<String> keySet = artistsIndexMap.keySet();
		ArrayList<Integer> indices = new ArrayList<>();
		// Iterate through key set and find matching artists
		for (String key : keySet) {
			// If found then update found to true so no exception is raised
			if (key.equals(artist)){
				indices = artistsIndexMap.get(key);
				found = true;		
			}
		}
		if (found == false){
			throw new NoMatchesException(artist);
		}
		// Return indices to be downloaded
		else{
			return indices;
		}
	}

	public ArrayList<Integer> downloadG(String genre)
	{
		// Found variable to determine exception need
		boolean found = false;

		// Get Key Set
		Set<Song.Genre> keySet = genreIndexMap.keySet();
		ArrayList<Integer> indices = new ArrayList<>();
		// Iterate through key set and find matching genres
		for (Song.Genre key : keySet) {
			// If found then update found to true so no exception is raised
			if (key.name().equals(genre)){
				indices = genreIndexMap.get(key);
				found = true;		
			}
		}
		if (found == false){
			throw new NoMatchesException(genre);
		}
		// Return indices to be downloaded
		return indices;
	}
	
    public void searchP(String query)
    {
		// Found variable to determine exception need 
		boolean found = false;

		// Iterate through list containing each objects searchable contents
        for (int i = 0; i < allInfoList.size(); i++){
			String allInfo = allInfoList.get(i);
			// If any content contains the search query printInfo and set found = true
			if (allInfo.contains(query)){
				System.out.print((i+1) + ". ");
				contents.get(i).printInfo();
				System.out.println();
				found = true;
			}
		}
		if (found == false){
			System.out.println("No matches for " + query);
		}
    }
}