
import java.util.Scanner;

public class puissance4 {

    private static final int ROWS = 6; // Nombre de lignes du plateau
    private static final int COLS = 7; // Nombre de colonnes du plateau
    private static char[][] board = new char[ROWS][COLS]; // Le plateau de jeu
    private static Player currentPlayer; // Le joueur actuel
    private static Player playerRed = new Player("Rouge", 'R'); // Joueur Rouge
    private static Player playerYellow = new Player("Jaune", 'Y'); // Joueur Jaune

    public static void main(String[] args) {
        initializeBoard(); // Initialise le plateau
        currentPlayer = playerRed; // Le joueur Rouge commence

        Scanner scanner = new Scanner(System.in); // Objet Scanner pour lire l'entrée utilisateur

        while (true) { // Boucle principale du jeu
            printBoard(); // Affiche le plateau
            int col = getPlayerMove(scanner); // Obtient le mouvement du joueur
            dropDisc(col); // Place le jeton dans la colonne choisie

            if (checkWin()) { // Vérifie si le joueur actuel a gagné
                printBoard();
                System.out.println("Le joueur " + currentPlayer.getName() + " a gagné !");
                break;
            }

            if (isBoardFull()) { // Vérifie si le plateau est plein (match nul)
                printBoard();
                System.out.println("Match nul !");
                break;
            }

            switchPlayer(); // Passe au joueur suivant
        }

        scanner.close(); // Ferme l'objet Scanner
    }

    private static void initializeBoard() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                board[i][j] = ' '; // Initialise chaque case du plateau à vide
            }
        }
    }

    private static void printBoard() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                char token = board[i][j];
                if (token == playerRed.getSymbol()) {
                    System.out.print("\u001B[31m" + token + "\u001B[0m "); // Rouge pour 'R'
                } else if (token == playerYellow.getSymbol()) {
                    System.out.print("\u001B[33m" + token + "\u001B[0m "); // Jaune pour 'Y'
                } else {
                    System.out.print("  ");
                }
            }
            System.out.println();
        }
        System.out.println("1 2 3 4 5 6 7"); // Affiche les numéros de colonne
    }

    private static int getPlayerMove(Scanner scanner) {
        int col;
        do {
            System.out.print("Joueur " + currentPlayer.getName() + ", entrez le numéro de colonne (1-7) : ");
            col = scanner.nextInt() - 1; // Lit le mouvement de l'utilisateur
        } while (col < 0 || col >= COLS || board[0][col] != ' '); // Vérifie la validité du mouvement

        return col;
    }

    private static void dropDisc(int col) {
        for (int i = ROWS - 1; i >= 0; i--) {
            if (board[i][col] == ' ') {
                board[i][col] = currentPlayer.getSymbol(); // Place le jeton dans la colonne choisie
                break;
            }
        }
    }

    private static void switchPlayer() {
        currentPlayer = (currentPlayer == playerRed) ? playerYellow : playerRed; // Passe au joueur suivant
    }

   private static boolean checkWin() {
    // Parcourt tout le plateau
    for (int row = 0; row < ROWS; row++) {
        for (int col = 0; col < COLS; col++) {
            char token = board[row][col];
            if (token == ' ') {
                continue; // Ignore les cases vides
            }

            // Vérifie les 4 directions
            if (checkDirection(row, col, 0, 1, token) || // Horizontal
                checkDirection(row, col, 1, 0, token) || // Vertical
                checkDirection(row, col, 1, 1, token) || // Diagonale descendante
                checkDirection(row, col, 1, -1, token)) { // Diagonale ascendante
                return true;
            }
        }
    }
    return false; // Aucun alignement trouvé
}

    private static boolean checkDirection(int startRow, int startCol, int rowDir, int colDir, char token) {
        int count = 0;

        // Parcourt jusqu'à 4 cases dans la direction donnée
        for (int i = 0; i < 4; i++) {
            int row = startRow + i * rowDir;
            int col = startCol + i * colDir;

            // Vérifie si on est hors limites
            if (row < 0 || row >= ROWS || col < 0 || col >= COLS) {
                return false;
            }

            // Vérifie si le jeton correspond
            if (board[row][col] == token) {
                count++;
            } else {
                break; // Arrête si les jetons ne sont pas consécutifs
            }
        }
        return count == 4; // Retourne true si 4 jetons consécutifs sont trouvés
    }


    private static boolean isBoardFull() {
        for (int i = 0; i < COLS; i++) {
            if (board[0][i] == ' ') {
                return false; // Le plateau n'est pas plein
            }
        }
        return true; // Le plateau est plein
    }
}

// Définition de la classe Player, qui représente un joueur dans le jeu Puissance 4
class Player {
        // Attributs privés de la classe Player
    private String name; // Le nom du joueur
    private char symbol; // Le symbole associé au joueur (par exemple, 'R' pour Rouge, 'Y' pour Jaune)

    // Constructeur de la classe Player
    public Player(String name, char symbol) {
                // Initialisation des attributs du joueur avec les valeurs fournies en paramètres
        this.name = name;
        this.symbol = symbol;
    }
    // Méthode pour obtenir le nom du joueur
    public String getName() {
        return name;
    }
    // Méthode pour obtenir le symbole associé au joueur
    public char getSymbol() {
        return symbol;
    }
}