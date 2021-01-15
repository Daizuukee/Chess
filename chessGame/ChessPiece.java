package chessGame;

import chessGame.ChessMain.ChessException;
import chessGame.ChessMain.ChessException.InvalidMoveException;

public class ChessPiece {

    public enum Colors {
        WHITE, BLACK
    }

    public int x = 0,y = 0;

    public Colors color = Colors.WHITE;
    
    public ChessPiece(int x, int y, Colors c) {
        this.x = x;
        this.y = y;
        color = c;
    }

    /**
    * @param {m} This is the chessboard as a 2D array and all chesspieces.
    * @return Returns wether or not a tile is acessible. Set to possible(m)[y][x] as a standard.
    */
    public Boolean possible(ChessMain m,int x, int y) {
        Boolean[][] map = new Boolean[m.getIndexMap().length][];
        int s = m.getIndexMap()[0].length;
        for(int w=0;w<s;w++) {
            map[w] = new Boolean[s];
            for(int q=0;q<m.getIndexMap()[w].length;q++)
                map[w][q] = false;
        }

        Boolean[][] p = possible(m, map);
        return p[y][x];
    }

    /**
    * Get an array of the playing field, marking all Places you can move to with this Piece.
    * @param {m} This is the chessboard as a 2D array and all chesspieces.
    * @param {map} A simple precreated Boolean array (prefilled with false).
    * @return These should be all Tiles that can be accessed. Remember that the output will be possible[y][x]
    */
    @SuppressWarnings("unread")
    public Boolean[][] possible(ChessMain m, Boolean[][] map) {
        
        return map;
    } 

    public final void move(ChessMain m, int x, int y) throws InvalidMoveException {
        
        if(possible(m,x,y)) {
            movePiece(m,x,y);
        }
        else {
            throw new ChessException.InvalidMoveException(this,x,y);
        }
    }

    public void movePiece(ChessMain m, int x, int y) {
        m.movePiece(this.x,this.y,x,y);
        this.x = x;
        this.y = y;
    }
}
