package chessGame;

//the only external library used in the entire project ./.
import java.util.ArrayList;

import chessGame.ChessMain.ChessException.InvalidMoveException;
import chessGame.ChessMain.ChessException.NoSelectException;
import chessGame.ChessMain.ChessException.WrongColorException;
import chessGame.ChessPiece.Colors;

public final class ChessMain {

    public static class UniformArrayException extends Exception {
        UniformArrayException(String n1, String n2){  
            super(n1 + "'s length did not match " + n2 + "'s size or there was a discrepency in the size of its inner arrays, the 2D arrays need to be uniform");  
        }
        
        static final void testSize(String n1, String n2, Object[][] o1, Object[][] o2) throws UniformArrayException {
            Boolean works = o1.length == o2.length;
            int s = o1[0].length;
            for(int i = 0; i < o1.length && works;i++)
                works &= o1[i].length == s && o2[i].length == s;
            if(!works)
                throw new UniformArrayException(n1,n2);
        }
        //Wilkommen in der schrecklichen Welt namens Java, in der es int und Object gibt und aus irgendeinem Grund kein Object gibt das beides beinhaltet; Dies ist mit ABSTAND der schrecklichste Code den ich hier schreiben musste
        static final void testSize(String n1, String n2, int[][] o1, Object[][] o2) throws UniformArrayException {
            Boolean works = o1.length == o2.length;
            int s = o1[0].length;
            for(int i = 0; i < o1.length && works;i++)
                works &= o1[i].length == s && o2[i].length == s;
            if(!works)
                throw new UniformArrayException(n1,n2);
        }
    }  

    static class ChessException extends Exception {

        private ChessException(String exception) {
            super(exception);
        }

        static class InvalidMoveException extends ChessException {
            
            InvalidMoveException(ChessPiece cp, int x, int y) {
                super("The chesspiece: " + cp.getClass().getName() + " at: " + cp.x + ";" + cp.y + " cannot move to: " + x + ";" + y);
            }
        }
        
        static class NoSelectException extends ChessException {
            
            NoSelectException(int x, int y) {
                super("There is no chesspiece at: " + x + ";" + y);
            }
        }

        static class WrongColorException extends ChessException {
            
            WrongColorException(Colors c) {
                super("The selected piece of " + c.name() + " color, but it is not their turn");
            }
        }
    }
    

    public Colors turn;
    private final ChessEventListener cel;
    
    public ArrayList<ChessPiece> pieces;
    public ArrayList<ChessPiece> getPieces() { return pieces; }
    private int[][] indexMap;
    public int[][] getIndexMap() { return indexMap; }
    public int[][] pieceType;
    public int[][] getPieceTypes() { return pieceType; }
    /**
     * Creates a new Chess Game that you can read the Properties of most importantly for you will be `getPieces()`,`getPieceTypes` and `getIndexMap()`
     * @param indexes The indexes for the classes inside of `boardPieces` this will be the arrangement of your Chess Pieces
     * @param boardPieces The classes of your Chess Pieces, these will be all the Chess Pieces Available in the Chess Game
     * @param colors The colors of the Chess Pieces (here as indexes (normally 0 and 1)), use this to segment the Game into different Groups
     * @param CEL The ChessEventListener, use it to create Animations and more.
     * @throws UniformArrayException The 2D Arrays need to be of the same size and Uniform.
     */
    public ChessMain(int[][] indexes, CreateablePieces boardPieces, int[][] colors, ChessEventListener cel) throws UniformArrayException {
        this.cel = (cel != null)?cel:new ChessEventListener();
        Colors[][] cols = new Colors[colors.length][];
        for(int i = 0; i < cols.length; i++) {
            Colors[] col = new Colors[colors[i].length];
            for(int j = 0; j < col.length; j++)
                col[j] = Colors.values()[colors[i][j]];
            cols[i] = col;
        }

        create(indexes, boardPieces, cols);
    }

    /**
     * Creates a new Chess Game that you can read the Properties of most importantly for you will be `getPieces()`,`getPieceTypes` and `getIndexMap()`
     * @param indexes The indexes for the classes inside of `boardPieces` this will be the arrangement of your Chess Pieces
     * @param boardPieces The classes of your Chess Pieces, these will be all the Chess Pieces Available in the Chess Game
     * @param colors The colors of the Chess Pieces, use this to segment the Game into different Groups
     * @param CEL The ChessEventListener, use it to create Animations and more.
     * @throws UniformArrayException The 2D Arrays need to be of the same size and Uniform.
     */
    public ChessMain(int[][] indexes, CreateablePieces boardPieces, Colors[][] colors, ChessEventListener cel) throws UniformArrayException {
        this.cel = (cel != null)?cel:new ChessEventListener();
        create(indexes, boardPieces, colors);
    }

    private final void create(int[][] indexes, CreateablePieces boardPieces, Colors[][] colors) throws UniformArrayException {
        turn = Colors.WHITE;
        pieces = new ArrayList<>();
        indexMap = new int[indexes.length][];
        for(int w=0;w<indexMap.length;w++)
            indexMap[w] = new int[indexes[0].length];
        pieceType = new int[indexes.length][];
        for(int w=0;w<pieceType.length;w++)
            pieceType[w] = new int[indexes[0].length];
        for(int y = 0; y < indexes.length; y++) {
            int[] l = indexes[y];
            for(int x = 0; x < l.length; x++) {
                if(l[x] >= 0) {
                    try {
                        pieces.add((ChessPiece)(boardPieces.get(l[x])
                              .getDeclaredConstructor(int.class,int.class,Colors.class)
                              .newInstance(x,y,colors[y][x])));
                        indexMap[y][x] = pieces.size()-1;
                        pieceType[y][x] = l[x];
                    } catch(Exception e) {
                        cel.OnError(e,boardPieces.get(l[x]));
                    }
                }
                else {
                    indexMap[y][x] = -1;
                    pieceType[y][x] = -1;
                }
            }
        }
        UniformArrayException.testSize("indexes", "colors", indexes, colors);
    }

    public final void movePiece(int startX, int startY, int endX, int endY) {
        cel.onMove(pieces.get(indexMap[startY][startX]), startX, startY, endX, endY);
        if(indexMap[endY][endX] != -1) {
            cel.onPieceDestroyed(pieces.get(indexMap[endY][endX]), pieces.get(indexMap[endY][endX]), startY, startY);
            removePiece(indexMap[endY][endX]);
        }
        indexMap[endY][endX] = indexMap[startY][startX];
        pieceType[endY][endX] = pieceType[startY][startX];
        indexMap[startY][startX] = -1;
        pieceType[startY][startX] = -1;
        turn = (turn == Colors.WHITE)?Colors.BLACK:Colors.WHITE;
    }

    private final void removePiece(int index) {
        ChessPiece p = pieces.get(index);
        indexMap[p.y][p.x] = -1;
        pieces.remove(index);
        for(int y = 0; y < indexMap.length;++y) {
            int[] l = indexMap[y];
            for(int x = 0; x < l.length;++x) {
                l[x] -= (l[x] > index)?1:0;
            }
            indexMap[y] = l;
        }
    }

    /**
     * Moves a piece from start to end and removes the Object that was standing there beforehand.
     * @param startX X-Position of the piece that should be moved.
     * @param startY Y-Position of the piece that should be moved.
     * @param endX X-Position to which the piece should be moved.
     * @param endY Y-Position to which the piece should be moved.
     * @throws NoSelectException The start position did not lead to a Chess Piece.
     * @throws InvalidMoveException The piece cannot move the the end Position from this start Position.
     */
    public void move(int startX, int startY, int endX, int endY) throws NoSelectException, InvalidMoveException, WrongColorException {
        if(indexMap[startY][startX] == -1)
            throw new ChessException.NoSelectException(startX,startY);
        if(pieces.get(indexMap[startY][startX]).color != turn)
            throw new ChessException.WrongColorException(pieces.get(indexMap[startY][startX]).color);
        pieces.get(indexMap[startY][startX]).move(this, endX, endY);
    }
    /**
     * Gives you all possible Positions that a selected Chess Piece can access.
     * @param x X-Position of the piece that should be checked.
     * @param y Y-Position of the piece that should be checked.
     * @return a uniform 2D Boolean array where true means that it is accessible while false means that it is not.
     * @throws NoSelectException The start position did not lead to a Chess Piece.
     */
    public Boolean[][] possible(int x, int y) throws NoSelectException {
        if(indexMap[y][x] == -1)
            throw new NoSelectException(x, y);
        
        Boolean[][] map = new Boolean[indexMap.length][];
        int s = indexMap.length;
        for(int w=0;w<s;w++) {
            map[w] = new Boolean[s];
            for(int q=0;q<indexMap[w].length;q++)
                map[w][q] = false;
        }
            
        return pieces.get(indexMap[y][x]).possible(this, map);
    }

}
