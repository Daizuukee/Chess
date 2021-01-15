package pieces;

import chessGame.ChessMain;
import chessGame.ChessPiece;

public class Queen extends ChessPiece {
    
    public Queen(int x, int y, Colors c) {
        super(x, y, c);
    }

    
@Override
    public Boolean[][] possible(ChessMain m, Boolean[][] map) {
        map = new Rook(x,y,color).possible(m,map);
        map = new Bishop(x,y,color).possible(m,map);
        return map;
    }
}
