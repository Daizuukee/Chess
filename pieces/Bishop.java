package pieces;

import chessGame.ChessMain;
import chessGame.ChessPiece;

public class Bishop extends ChessPiece {
    
    public Bishop(int x, int y, Colors c) {
        super(x, y, c);
    }

    @Override
    public Boolean[][] possible(ChessMain m, Boolean[][] map) {
        for(int i = 1; x + i < map[0].length && y + i < map.length;i++) {
            if(m.getPieceTypes()[y+i][x+i] != -1) {
                if(m.getPieces().get(m.getIndexMap()[y+i][x+i]).color != color)
                    map[y+i][x+i] = true;
                break;
            }
            map[y+i][x+i] = true;
        }

        for(int i = 1; x - i >= 0 && y + i < map.length;i++) {
            if(m.getPieceTypes()[y+i][x-i] != -1) {
                if(m.getPieces().get(m.getIndexMap()[y+i][x-i]).color != color)
                    map[y+i][x-i] = true;
                break;
            }
            map[y+i][x-i] = true;
        }
        
        for(int i = 1; x + i < map[0].length && y - i >= 0;i++) {
            if(m.getPieceTypes()[y-i][x+i] != -1) {
                if(m.getPieces().get(m.getIndexMap()[y-i][x+i]).color != color)
                    map[y-i][x+i] = true;
                break;
            }
            map[y-i][x+i] = true;
        }
        
        for(int i = 1; x - i >= 0 && y - i >= 0;i++) {
            if(m.getPieceTypes()[y-i][x-i] != -1) {
                if(m.getPieces().get(m.getIndexMap()[y-i][x-i]).color != color)
                    map[y-i][x-i] = true;
                break;
            }
            map[y-i][x-i] = true;
        }
        return map;
    }
}
