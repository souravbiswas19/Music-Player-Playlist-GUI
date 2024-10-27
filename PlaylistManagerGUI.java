
import java.awt.*;
import java.util.List;
import javax.swing.*;

public class PlaylistManagerGUI {

    private Playlist playlist;
    private JTextArea textArea;
    private JTextField titleField;
    private JTextField artistField;
    private JTextField durationField;
    private JTextField searchField;
    private JTextField saveField;
    private JTextField loadField;
    private JTextField moveOldField;
    private JTextField moveNewField;

    public PlaylistManagerGUI() {
        playlist = new Playlist();
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("Playlist Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);
        frame.setLayout(new BorderLayout());

        // Text area to display playlist
        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Panel for controls
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 2));

        // Input fields for song details
        panel.add(new JLabel("Title:"));
        titleField = new JTextField();
        panel.add(titleField);

        panel.add(new JLabel("Artist:"));
        artistField = new JTextField();
        panel.add(artistField);

        panel.add(new JLabel("Duration (minutes):"));
        durationField = new JTextField();
        panel.add(durationField);

        panel.add(new JLabel("Search:"));
        searchField = new JTextField();
        panel.add(searchField);

        panel.add(new JLabel("Save Filename:"));
        saveField = new JTextField();
        panel.add(saveField);

        panel.add(new JLabel("Load Filename:"));
        loadField = new JTextField();
        panel.add(loadField);

        panel.add(new JLabel("Move Old Position:"));
        moveOldField = new JTextField();
        panel.add(moveOldField);

        panel.add(new JLabel("Move New Position:"));
        moveNewField = new JTextField();
        panel.add(moveNewField);

        // Buttons for actions
        JButton addButton = new JButton("Add Song");
        JButton removeButton = new JButton("Remove Song");
        JButton displayButton = new JButton("Display Playlist");
        JButton shuffleButton = new JButton("Shuffle Playlist");
        JButton searchButton = new JButton("Search");
        JButton saveButton = new JButton("Save Playlist");
        JButton loadButton = new JButton("Load Playlist");
        JButton moveButton = new JButton("Move Song");
        JButton sortByTitleButton = new JButton("Sort by Title");
        JButton sortByArtistButton = new JButton("Sort by Artist");
        JButton reverseButton = new JButton("Reverse Playlist");
        JButton toggleRepeatButton = new JButton("Toggle Repeat");

        // Adding action listeners
        addButton.addActionListener(e -> addSong());
        removeButton.addActionListener(e -> removeSong());
        displayButton.addActionListener(e -> displayPlaylist());
        shuffleButton.addActionListener(e -> shufflePlaylist());
        searchButton.addActionListener(e -> searchSong());
        saveButton.addActionListener(e -> savePlaylist());
        loadButton.addActionListener(e -> loadPlaylist());
        moveButton.addActionListener(e -> moveSong());
        sortByTitleButton.addActionListener(e -> sortByTitle());
        sortByArtistButton.addActionListener(e -> sortByArtist());
        reverseButton.addActionListener(e -> reversePlaylist());
        toggleRepeatButton.addActionListener(e -> toggleRepeatMode());

        // Adding buttons to panel
        panel.add(addButton);
        panel.add(removeButton);
        panel.add(displayButton);
        panel.add(shuffleButton);
        panel.add(searchButton);
        panel.add(saveButton);
        panel.add(loadButton);
        panel.add(moveButton);
        panel.add(sortByTitleButton);
        panel.add(sortByArtistButton);
        panel.add(reverseButton);
        panel.add(toggleRepeatButton);

        frame.add(panel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    private void addSong() {
        String title = titleField.getText();
        String artist = artistField.getText();
        String durationStr = durationField.getText();

        if (title.isEmpty() || artist.isEmpty() || durationStr.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill all fields.");
            return;
        }

        try {
            double duration = Double.parseDouble(durationStr);
            playlist.addSong(new Song(title, artist, duration));
            JOptionPane.showMessageDialog(null, "Song added.");
            clearFields();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid duration. Please enter a number.");
        }
    }

    private void removeSong() {
        String title = titleField.getText();
        if (title.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter the title of the song to remove.");
            return;
        }
        playlist.removeSongByTitle(title);
        JOptionPane.showMessageDialog(null, "Song removed.");
        clearFields();
    }

    private void displayPlaylist() {
        List<String> displayList = playlist.displayPlaylist();
        textArea.setText(String.join("\n", displayList));
    }

    private void shufflePlaylist() {
        playlist.shufflePlaylist();
        JOptionPane.showMessageDialog(null, "Playlist shuffled.");
    }

    private void searchSong() {
        String query = searchField.getText();
        List<String> results = playlist.searchSong(query);
        textArea.setText(String.join("\n", results));
    }

    private void savePlaylist() {
        String filename = saveField.getText();
        if (filename.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter a filename.");
            return;
        }
        playlist.savePlaylist(filename);
    }

    private void loadPlaylist() {
        String filename = loadField.getText();
        if (filename.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter a filename.");
            return;
        }
        playlist.loadPlaylist(filename);
        JOptionPane.showMessageDialog(null, "Playlist loaded.");
    }

    private void moveSong() {
        String oldPosStr = moveOldField.getText();
        String newPosStr = moveNewField.getText();

        if (oldPosStr.isEmpty() || newPosStr.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill both positions.");
            return;
        }

        try {
            int oldPosition = Integer.parseInt(oldPosStr);
            int newPosition = Integer.parseInt(newPosStr);
            playlist.moveSong(oldPosition, newPosition);
            JOptionPane.showMessageDialog(null, "Song moved.");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid position. Please enter numbers.");
        }
    }

    private void sortByTitle() {
        playlist.sortByTitle();
        JOptionPane.showMessageDialog(null, "Playlist sorted by title.");
    }

    private void sortByArtist() {
        playlist.sortByArtist();
        JOptionPane.showMessageDialog(null, "Playlist sorted by artist.");
    }

    private void reversePlaylist() {
        playlist.reversePlaylist();
        JOptionPane.showMessageDialog(null, "Playlist reversed.");
    }

    private void toggleRepeatMode() {
        playlist.toggleRepeatMode();
        JOptionPane.showMessageDialog(null, "Repeat mode toggled.");
    }

    private void clearFields() {
        titleField.setText("");
        artistField.setText("");
        durationField.setText("");
        searchField.setText("");
        saveField.setText("");
        loadField.setText("");
        moveOldField.setText("");
        moveNewField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(PlaylistManagerGUI::new);
    }
}
