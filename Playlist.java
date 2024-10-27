
import java.io.*;
import java.util.*;

public class Playlist {

    private LinkedList<Song> songs;
    private boolean repeatMode = false; // flag to toggle repeat mode

    // Constructor to initialize the playlist
    public Playlist() {
        songs = new LinkedList<>();
    }

    // Add a song to the playlist at the end or at a specific position
    public void addSong(Song song) {
        songs.add(song);
    }

    public void addSongAtPosition(Song song, int position) {
        if (position < 1 || position > songs.size() + 1) {
            System.out.println("Invalid position.");
        } else {
            songs.add(position - 1, song);
        }
    }

    // Remove a song by its title
    public void removeSongByTitle(String title) {
        Song songToRemove = null;
        for (Song song : songs) {
            if (song.getTitle().equalsIgnoreCase(title)) {
                songToRemove = song;
                break;
            }
        }
        if (songToRemove != null) {
            songs.remove(songToRemove);
            System.out.println("Song removed successfully.");
        } else {
            System.out.println("Song not found.");
        }
    }

    // Remove a song by its position
    public void removeSongByPosition(int position) {
        if (position < 1 || position > songs.size()) {
            System.out.println("Invalid position.");
        } else {
            songs.remove(position - 1);
            System.out.println("Song removed successfully.");
        }
    }

    // Display the entire playlist
    public List<String> displayPlaylist() {
        List<String> displayList = new ArrayList<>();
        if (songs.isEmpty()) {
            displayList.add("The playlist is empty.");
        } else {
            double totalDuration = 0;
            int count = 1;
            for (Song song : songs) {
                displayList.add(count++ + ". " + song);
                totalDuration += song.getDuration();
            }
            displayList.add("Total duration: " + totalDuration + " minutes");
        }
        return displayList;
    }

    public void moveSong(int oldPosition, int newPosition) {
        if (oldPosition < 1 || oldPosition > songs.size() || newPosition < 1 || newPosition > songs.size()) {
            System.out.println("Invalid position.");
        } else {
            Song song = songs.remove(oldPosition - 1);
            songs.add(newPosition - 1, song);
            System.out.println("Song moved successfully.");
        }
    }

    public void reversePlaylist() {
        Collections.reverse(songs);
        System.out.println("Playlist reversed.");
    }

    public List<String> searchSong(String query) {
        List<String> resultList = new ArrayList<>();
        boolean found = false;
        int count = 1;
        for (Song song : songs) {
            if (song.getTitle().equalsIgnoreCase(query) || song.getArtist().equalsIgnoreCase(query)) {
                resultList.add("Found: " + count + ". " + song);
                found = true;
            }
            count++;
        }
        if (!found) {
            resultList.add("Song not found.");
        }
        return resultList;
    }

    public void savePlaylist(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Song song : songs) {
                writer.write(song.getTitle() + "," + song.getArtist() + "," + song.getDuration() + "\n");
            }
            System.out.println("Playlist saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving playlist: " + e.getMessage());
        }
    }

    public void loadPlaylist(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            songs.clear();
            while ((line = reader.readLine()) != null) {
                String[] details = line.split(",");
                if (details.length == 3) {
                    String title = details[0];
                    String artist = details[1];
                    double duration = Double.parseDouble(details[2]);
                    addSong(new Song(title, artist, duration));
                }
            }
            System.out.println("Playlist loaded successfully.");
        } catch (IOException e) {
            System.out.println("Error loading playlist: " + e.getMessage());
        }
    }

    public void shufflePlaylist() {
        Collections.shuffle(songs);
        System.out.println("Playlist shuffled.");
    }

    public void toggleRepeatMode() {
        repeatMode = !repeatMode;
        if (repeatMode) {
            System.out.println("Repeat mode enabled.");
        } else {
            System.out.println("Repeat mode disabled.");
        }
    }

    public void sortByTitle() {
        songs.sort(Comparator.comparing(Song::getTitle));
        System.out.println("Playlist sorted by title.");
    }

    // Sort the playlist by artist
    public void sortByArtist() {
        songs.sort(Comparator.comparing(Song::getArtist));
        System.out.println("Playlist sorted by artist.");
    }
}
