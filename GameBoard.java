import javax.swing.*;             // Import Swing components for GUI
import java.awt.*;                // Import AWT classes for layout and colors

public class GameBoard extends JFrame {

    // Chessboard 2D array to represent piece positions (8x8 board)
    private String[][] chessBoard = {
        {"wRook1", "wKnight1", "wBishop1", "wQueen", "wKing", "wBishop2", "wKnight2", "wRook2"},
        {"wPawn1", "wPawn2", "wPawn3", "wPawn4", "wPawn5", "wPawn6", "wPawn7", "wPawn8"},
        {"", "", "", "", "", "", "", ""},
        {"", "", "", "", "", "", "", ""},
        {"", "", "", "", "", "", "", ""},
        {"", "", "", "", "", "", "", ""},
        {"bPawn1", "bPawn2", "bPawn3", "bPawn4", "bPawn5", "bPawn6", "bPawn7", "bPawn8"},
        {"bRook1", "bKnight1", "bBishop1", "bQueen", "bKing", "bBishop2", "bKnight2", "bRook2"}
    };

    private JPanel boardPanel;         // Panel for displaying the chessboard
    
    // --------------------------------------------------------------------------------------------
    // create your 2d Array to store your image variables and assign positions
    // add your code here
    // this line of code initializes a new 2D Array of Strings the size of 1 row and 2 columns
    // your 2D array must be a minimum of 6 rows x 2 columns
    // you may add a row for every image if you'd like to have every square be a different color/image
    // --------------------------------------------------------------------------------------------
    private String[][] imageData = {
        {"wRook1.png", "7"},    // Row 0: image filename and unsorted sort key
        {"wKnight1.png", "5"},  // Row 1
        {"wBishop1.png", "3"},  // Row 2
        {"wQueen.png", "4"},    // Row 3
        {"wKing.png", "2"},     // Row 4
        {"wPawn1.png", "6"}     // Row 5
    };

    public GameBoard() {
        setTitle("Chess Game Board");                         // Set window title
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);       // Exit when window is closed
        setSize(800, 800);                                    // Set window size
        setLocationRelativeTo(null);                          // Center the window
        setLayout(new BorderLayout());                        // Use BorderLayout for overall frame

        // Create boardPanel to hold the chessboard (center of frame)
        boardPanel = new JPanel(new GridLayout(8, 8));        // 8x8 grid layout for chessboard
        initializeBoard();                                    // Build chessboard with checkered pattern and pieces
        add(boardPanel, BorderLayout.CENTER);                 // Add chessboard panel to the center

        // --------------------------------------------------------------------------------------------
        // print the contents of your 2D array
        // this is a requirement to show your 2D array is not sorted at the beginning of your program
        // --------------------------------------------------------------------------------------------
        System.out.println("Unsorted imageData:");
        printImageData(imageData);  // Print unsorted imageData to console

        // --------------------------------------------------------------------------------------------
        // this is where your sorting method will be called 
        // you will use the column 2 values to arrange your images to the board
        // be sure to sort them before you add them onto the board 
        // you will use a loop to add to your 2D Array, below is an example of how to add ONE image to ONE square
        // --------------------------------------------------------------------------------------------
        mergeSort(imageData, 0, imageData.length - 1);  // Call merge sort on the imageData array

        // Print the sorted imageData array to verify the sort order
        System.out.println("Sorted imageData:");
        printImageData(imageData);

        /*
         * --------------------------------------------------------------------------------------------
         *  Removing the extra sortedImagesPanel so it no longer displays a second row of pieces
         * --------------------------------------------------------------------------------------------
         
        JPanel sortedImagesPanel = new JPanel(new GridLayout(1, imageData.length));
        add(sortedImagesPanel, BorderLayout.SOUTH);

        for (int i = 0; i < imageData.length; i++) {
            String fileName = imageData[i][0];  // Get the image filename from the array
            // Load the image icon from the file name using loadImage() method (from "chess pieces/" folder)
            ImageIcon icon = loadImage(fileName);
            // Scale the image to fit the panel cell
            Image img = icon.getImage();
            Image scaledImg = img.getScaledInstance(80, 80, Image.SCALE_SMOOTH);
            icon = new ImageIcon(scaledImg);
            // Create a label to hold the image and center it
            JLabel imageLabel = new JLabel(icon);
            imageLabel.setHorizontalAlignment(JLabel.CENTER);
            imageLabel.setVerticalAlignment(JLabel.CENTER);
            sortedImagesPanel.add(imageLabel);
        }
        */
    }

    // Helper method to print the 2D imageData array to the console
    private void printImageData(String[][] data) {
        for (int i = 0; i < data.length; i++) {             // Loop through each row of the array
            System.out.print("[ ");                          // Print starting bracket for the row
            for (int j = 0; j < data[i].length; j++) {       // Loop through each column in the row
                System.out.print(data[i][j] + " ");          // Print each element followed by a space
            }
            System.out.println("]");                        // Close the row with a bracket and newline
        }
    }

    // Method to initialize the chessboard with a checkered pattern and chess pieces
    private void initializeBoard() {
        // Loop through rows 0 to 7 for the chessboard
        for (int row = 0; row < 8; row++) {
            // Loop through columns 0 to 7 for the chessboard
            for (int col = 0; col < 8; col++) {
                JPanel cell = new JPanel(new BorderLayout());  // Create a cell with BorderLayout

                // --------------------------------------------------------------------------------------------
                // creates the checkered pattern with the two colors
                // you can add more colors or take away any you'd like
                // --------------------------------------------------------------------------------------------
                if ((row + col) % 2 == 0) {                      // Even sum of row+col
                    cell.setBackground(Color.WHITE);             // Set cell background to white
                } else {                                         // Odd sum of row+col
                    cell.setBackground(Color.LIGHT_GRAY);        // Set cell background to light gray
                }

                // If there is a chess piece (non-empty string), add its image to the cell
                if (!chessBoard[row][col].isEmpty()) {
                    String pieceName = chessBoard[row][col];     // Get the piece name from the chessBoard array
                    // Load the image icon for the chess piece using loadImage() method (from "chess pieces/" folder)
                    ImageIcon icon = loadImage(pieceName);
                    // Scale the image to fit into the cell
                    Image img = icon.getImage();                 // Retrieve the Image object
                    Image scaledImg = img.getScaledInstance(60, 60, Image.SCALE_SMOOTH); // Scale image to 60x60
                    icon = new ImageIcon(scaledImg);             // Create a new ImageIcon with the scaled image

                    JLabel pieceLabel = new JLabel(icon);        // Create a JLabel with the chess piece icon
                    pieceLabel.setHorizontalAlignment(JLabel.CENTER);
                    pieceLabel.setVerticalAlignment(JLabel.CENTER);
                    cell.add(pieceLabel, BorderLayout.CENTER);   // Add the label to the center of the cell

                    // Optionally add a label for the piece name below the image
                    JLabel nameLabel = new JLabel(pieceName);
                    nameLabel.setFont(new Font("Arial", Font.PLAIN, 10));
                    nameLabel.setHorizontalAlignment(JLabel.CENTER);
                    cell.add(nameLabel, BorderLayout.SOUTH);     // Add the name label at the bottom of the cell
                }
                boardPanel.add(cell);                            // Add the cell to the boardPanel
            }
        }
    }

    // --------------------------------------------------------------------------------------------
    // add your merge sort method here
    // add a comment to every line of code that describes what the line is accomplishing
    // your mergeSort method does not have to return any value
    // Implement merge sort to sort the imageData 2D array based on the second column (index 1)
    // --------------------------------------------------------------------------------------------
    private void mergeSort(String[][] arr, int left, int right) {
        if (left < right) {                                    // If the left index is less than the right index
            int mid = (left + right) / 2;                      // Calculate the middle index of the array segment
            mergeSort(arr, left, mid);                         // Recursively sort the left half of the array
            mergeSort(arr, mid + 1, right);                    // Recursively sort the right half of the array
            merge(arr, left, mid, right);                      // Merge the two sorted halves into one
        }
    }

    // Merge function to combine two sorted sub-arrays into a single sorted sub-array
    private void merge(String[][] arr, int left, int mid, int right) {
        int n1 = mid - left + 1;                               // Calculate the size of the left sub-array
        int n2 = right - mid;                                  // Calculate the size of the right sub-array

        String[][] leftArr = new String[n1][2];                // Create a temporary array for the left sub-array
        String[][] rightArr = new String[n2][2];               // Create a temporary array for the right sub-array

        for (int i = 0; i < n1; i++) {                         // Copy elements into the left temporary array
            leftArr[i][0] = arr[left + i][0];                  // Copy image filename from arr to leftArr
            leftArr[i][1] = arr[left + i][1];                  // Copy sort key from arr to leftArr
        }
        for (int j = 0; j < n2; j++) {                         // Copy elements into the right temporary array
            rightArr[j][0] = arr[mid + 1 + j][0];              // Copy image filename from arr to rightArr
            rightArr[j][1] = arr[mid + 1 + j][1];              // Copy sort key from arr to rightArr
        }

        int i = 0;                                            // Initialize index i for leftArr
        int j = 0;                                            // Initialize index j for rightArr
        int k = left;                                         // Initialize index k for the merged array

        while (i < n1 && j < n2) {                            // Loop while both leftArr and rightArr have elements
            // Compare the sort keys (as integers) from leftArr and rightArr
            if (Integer.parseInt(leftArr[i][1]) <= Integer.parseInt(rightArr[j][1])) {
                arr[k][0] = leftArr[i][0];                    // Place leftArr element's filename into arr at index k
                arr[k][1] = leftArr[i][1];                    // Place leftArr element's sort key into arr at index k
                i++;                                          // Move to the next element in leftArr
            } else {
                arr[k][0] = rightArr[j][0];                   // Place rightArr element's filename into arr at index k
                arr[k][1] = rightArr[j][1];                   // Place rightArr element's sort key into arr at index k
                j++;                                          // Move to the next element in rightArr
            }
            k++;                                              // Increment the merged array index k
        }

        while (i < n1) {                                      // Copy any remaining elements from leftArr
            arr[k][0] = leftArr[i][0];                        // Copy image filename from leftArr to arr
            arr[k][1] = leftArr[i][1];                        // Copy sort key from leftArr to arr
            i++;
            k++;
        }

        while (j < n2) {                                      // Copy any remaining elements from rightArr
            arr[k][0] = rightArr[j][0];                       // Copy image filename from rightArr to arr
            arr[k][1] = rightArr[j][1];                       // Copy sort key from rightArr to arr
            j++;
            k++;
        }
    }

    // --------------------------------------------------------------------------------------------
    // loadImage() method: Loads an image from the "chess pieces/" folder given a piece name
    // --------------------------------------------------------------------------------------------
    private ImageIcon loadImage(String pieceName) {
        // If pieceName already ends with .png, use it; otherwise, append .png
        String fileName = pieceName.endsWith(".png") ? pieceName : pieceName + ".png";
        // Build the relative path using the "chess pieces" folder and the fileName
        String path = "chess pieces/" + fileName;

        // Try loading directly from the relative path
        ImageIcon icon = new ImageIcon(path);
        if (icon.getIconWidth() <= 0) {
            // If the width is 0 or negative, the image was not loaded properly
            System.err.println("ERROR: Could not load image " + path);
            return new ImageIcon();  // Return an empty icon
        }
        return icon;
    }

    public static void main(String[] args) {
        // Launch the game board on the Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GameBoard().setVisible(true);          // Create and display the GameBoard
            }
        });
    }
}
