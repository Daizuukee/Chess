package pieces;

import chessGame.ChessMain;
import chessGame.ChessPiece;

public class Rook extends ChessPiece {

    public Rook(int x, int y, Colors c) {
        super(x, y, c);
    }
    
   
@Override
    public Boolean[][] possible(ChessMain m, Boolean[][] map) {
        //Horizontal Movement
        for(int ix = x+1; ix < map[0].length; ix++) {
            if(m.getPieceTypes()[y][ix] != -1) {
                if(m.getPieces().get(m.getIndexMap()[y][ix]).color != color)
                    map[y][ix] = true;
                break;
            }
            map[y][ix] = true;
        }

        for(int ix = x-1; ix >= 0; ix--) {
            if(m.getPieceTypes()[y][ix] != -1) {
                if(m.getPieces().get(m.getIndexMap()[y][ix]).color != color)
                    map[y][ix] = true;
                break;
            }
            map[y][ix] = true;
        }

        //Vertical Movement
        for(int iy = y+1; iy < map.length; iy++) {
            if(m.getPieceTypes()[iy][x] != -1) {
                if(m.getPieces().get(m.getIndexMap()[iy][x]).color != color)
                    map[iy][x] = true;
                break;
            }
            map[iy][x] = true;
        }

        for(int iy = y-1; iy >= 0; iy--) {
            if(m.getPieceTypes()[iy][x] != -1) {
                if(m.getPieces().get(m.getIndexMap()[iy][x]).color != color)
                    map[iy][x] = true;
                break;
            }
            map[iy][x] = true;
        }

        return map;
    }
}