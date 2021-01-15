package pieces;

import chessGame.ChessMain;
import chessGame.ChessPiece;

public class Horse extends ChessPiece {
    
    public Horse(int x, int y, Colors c) {
        super(x, y, c);
    }
    //Movement is complex so its stored as offsets in a list where the horse can go
    int[] ox = {-1,1,2,2,1,-1,-2,-2};
    int[] oy = {2,2,1,-1,-2,-2,-1,1};
    @Override
    public Boolean[][] possible(ChessMain m, Boolean[][] map) {
        for(int i = 0; i < 8; i++) {
                int ix = ox[i];
                int iy = oy[i];
            if(y+iy >= 0 && y+iy < map.length && 
               x+ix >= 0 && x+ix < map[0].length &&
               (m.getIndexMap()[y+iy][x+ix] == -1 || 
                m.getPieces().get(m.getIndexMap()[y+iy][x+ix]).color != color)
               )
                map[y+iy][x+ix] = true;
        }
        return map;
    }
}
