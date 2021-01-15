package pieces;

import chessGame.ChessMain;
import chessGame.ChessPiece;

public class Pawn extends ChessPiece {

    public Pawn(int x, int y, Colors c) {
        super(x, y, c);
    }


@Override
    public void movePiece(ChessMain m, int x, int y) {
        super.movePiece(m, x, y);
        if(y == 0 || y == m.getIndexMap().length-1) {
            m.pieces.set(m.getIndexMap()[y][x],new Queen(x,y,color));
            //Hard coded, but since this is an exception it is smarter to hardcode it than putting it into ChessMain.java
            m.pieceType[y][x] = 5; //ID of QUEEN chess piece
        }
    }
    
@Override
    public Boolean[][] possible(ChessMain m, Boolean[][] map) {
        if(color == Colors.BLACK) {
            if(y < map.length-1) {
                if(m.getIndexMap()[y+1][x] == -1)
                    map[y+1][x] = true;
                if(y == 1 && y < map.length-2 && m.getIndexMap()[y+2][x] == -1)
                    map[y+2][x] = true;
                if(x < map[0].length-1 && m.getIndexMap()[y+1][x+1] != -1 && m.getPieces().get(m.getIndexMap()[y+1][x+1]).color != color)
                    map[y+1][x+1] = true;
                if(x > 0 && m.getIndexMap()[y+1][x-1] != -1 && m.getPieces().get(m.getIndexMap()[y+1][x-1]).color != color)
                    map[y+1][x-1] = true;
            }
        }
        else {
            if(y < map.length-1) {
                if(m.getIndexMap()[y-1][x] == -1)
                    map[y-1][x] = true;
                if(y == map.length -2 && y > 2 && m.getIndexMap()[y-2][x] == -1)
                    map[y-2][x] = true;
                if(x < map[0].length-1 && m.getIndexMap()[y-1][x+1] != -1)
                    map[y-1][x+1] = true;
                if(x > 0 && m.getIndexMap()[y-1][x-1] != -1)
                    map[y-1][x-1] = true;
            }
        }
        return map;
    }
}
