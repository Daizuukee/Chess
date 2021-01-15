import chessGame.ChessMain;
import chessGame.ChessEventListener;
import chessGame.ChessPiece.Colors;
import pieces.*;
import chessGame.*;

public class ConsoleVisualizer extends ChessEventListener {

    public static void main(String[] args) {
        new ConsoleVisualizer().chess();
    }
    
    int[][] pieceIndexes = { 
        { 1, 2, 3, 5, 4, 3, 2, 1 }, 
        { 0, 0, 0, 0, 0, 0, 0, 0 },
        {-1,-1,-1,-1,-1,-1,-1,-1 }, 
        {-1,-1,-1,-1,-1,-1,-1,-1 },
        {-1,-1,-1,-1,-1,-1,-1,-1 }, 
        {-1,-1,-1,-1,-1,-1,-1,-1 }, 
        { 0, 0, 0, 0, 0, 0, 0, 0 },
        { 1, 2, 3, 4, 5, 3, 2, 1 } };

    CreateablePieces createablePieces = new CreateablePieces(Pawn.class, Rook.class, Bishop.class, Horse.class, King.class, Queen.class);

    int[][] pieceCols = { 
        { 1, 1, 1, 1, 1, 1, 1, 1 }, 
        { 1, 1, 1, 1, 1, 1, 1, 1 }, 
        { 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0 }, 
        { 0, 0, 0, 0, 0, 0, 0, 0 }, 
        { 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0 }, 
        { 0, 0, 0, 0, 0, 0, 0, 0 } };

    Boolean exited = false;

    ChessMain m;

    public void chess() {
        
        try {
            m = new ChessMain(pieceIndexes, createablePieces, pieceCols, this);
        } catch (Exception e) {
            return;
        }

        exited = false;
        while (!exited) {
            Boolean moved = false;
            while (!moved) {
                System.out.println(output(m, -1, -1));
                System.out.print("\033[31mEnter your Piece (eg. F3 A5 B7):  \033[0m");
                String s;
                while ((s = System.console().readLine().toUpperCase()).length() < 2) {
                    System.out.print("\033[31mEnter a 2D coordinate! Enter your Piece (eg. F3 A5 B7):  \033[0m");
                }
                System.out.println("\033[32m Available Spots for: \033[0m" + s);
                int x = s.charAt(0) - 65;
                int y = s.charAt(1) - 49;
                String board = output(m, x, y);
                if (board != null) {
                    System.out.println(board);
                    if (m.getPieceTypes()[y][x] != -1) {
                        System.out.print("\033[31mSelect where to move eg. F3 A5 B7):  \033[0m");

                        while ((s = System.console().readLine().toUpperCase()).length() < 2) {
                            System.out
                                .print("\033[31mEnter a 2D coordinate! Enter your Piece (eg. F3 A5 B7):  \033[0m");
                        }
                        System.out.println("\033[32m Moving to: \033[0m" + s);
                        int nx = s.charAt(0) - 65;
                        int ny = s.charAt(1) - 49;
                        try {
                            m.move(x, y, nx, ny);
                            moved = true;
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                } else {
                    System.out.println("\033[31mAn Error Occured!\033[0m");
                }
            }
        }
    }

    public static String output(ChessMain m, int x, int y) {
        StringBuilder out = new StringBuilder("  ");
        Boolean[][] s;
        
        for (int ix = 0; ix < m.getPieceTypes()[0].length; ix++) {
            out.append((char)(ix + 65));
        }
        out.append("\n");
        try {
            s = (x < 0 || y < 0) ? new Boolean[0][] : m.possible(x, y); // Check if linting is possible
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null; // A mistake occured when reading m.possible() the board cannot be displayed
        }
        for (int iy = 0; iy < m.getPieceTypes().length; iy++) {
            int[] pt = m.getPieceTypes()[iy];
            StringBuilder line = new StringBuilder(iy + 1 + " ");
            out.append(line.substring(Math.max(0,line.length()-3),Math.max(0,line.length()-3) + 2));
            for (int ix = 0; ix < pt.length; ix++) {
                Boolean darken = m.getPieceTypes()[iy][ix] != -1
                                 && m.getPieces().get(m.getIndexMap()[iy][ix]).color == Colors.BLACK; 
                                 // check if a piece is not white/the board because of
                                 // coloring the different pieces
                int p = pt[ix];

                out.append("\033["); // Color code beginning
                if (!(x < 0 || y < 0)) {
                    if (s[iy][ix].booleanValue()) //primitive value to suppress null
                        out.append("1;100;"); // Linting (Bold and Grey background)
                    else
                        out.append("2;"); // Linting unusable spots (50% opacity)
                }

                if (x == ix && y == iy)
                    out.append("103;"); // Linting currently used piece (103 is light orange background)

                if (darken)
                    out.append("30m"); // Linting black pieces
                else
                    out.append("37m"); // Linting white pieces

                switch (p) {
                    case 0:
                        out.append("o"); // Pawn
                        break;
                    case 1:
                        out.append("W"); // Rook
                        break;
                    case 2:
                        out.append("I"); // Bishop
                        break;
                    case 3:
                        out.append("P"); // Horse
                        break;
                    case 4:
                        out.append("K"); // King
                        break;
                    case 5:
                        out.append("Q"); // Queen
                        break;
                    default:
                        out.append(((ix + iy) % 2 == 0) ? "." : ","); // Empty tile [-1]
                        break;
                }
                out.append("\033[0m");
            }
            out.append("\n");
        }
        return out.toString();
    }


    @Override
	public void onPieceDestroyed(ChessPiece cp, ChessPiece origin, int x, int y) {
        if(cp.getClass() == King.class) {
            exited = true; //Ends Game
            System.out.println("\033[35;1m" + ((cp.color == Colors.WHITE)?"BLACK":"WHITE") +" WON THE GAME");
            
            try {
                m = new ChessMain(pieceIndexes, createablePieces, pieceCols, this);
            } catch (Exception e) {
                return;
            }
            output(m,-1,-1);
        }
    }
}