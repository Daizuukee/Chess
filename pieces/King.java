package pieces;

import chessGame.ChessMain;
import chessGame.ChessPiece;

public class King extends ChessPiece {
    
    public King(int x, int y, Colors c) {
        super(x, y, c);
    }

    @Override
    public Boolean[][] possible(ChessMain m, Boolean[][] map) {
        for(int iy = -1; iy < 2; iy++)
            if(y+iy >= 0 && y+iy < map.length)
                for(int ix = -1; ix < 2; ix++)
                    if(x+ix >= 0 && x+ix < map[0].length &&
                       (m.getIndexMap()[y+iy][x+ix] == -1 || 
                       m.getPieces().get(m.getIndexMap()[y+iy][x+ix]).color != color)
                       )
                        map[y+iy][x+ix] = true;
        //King cannot move to itself as it would lead to an infinite game
        map[y][x] = false;
        return map;
    }
}
