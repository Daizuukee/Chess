package chessGame;

public class ChessEventListener {

    /**
     * Will be called whenever a Piece is removed from the Board
     * @param cp The Piece Removed
     * @param initiater The Piece that Initiated the Removal
     * @param x The X-Position of the Initiator
     * @param y The Y-Position of the Initiator
     */
    public void onPieceDestroyed(ChessPiece cp, ChessPiece initiater, int x, int y) {
        /**/
    }
    /**
     * Will be called whenever a Piece is moved on the Board
     * @param cp The Piece Removed
     * @param x The before-X-Position of the Piece
     * @param y The before-Y-Position of the 
     * @param endX The before-X-Position of the Piece
     * @param endY The before-Y-Position of the 
     */
    public void onMove(ChessPiece cp, int x, int y, int endX, int endY) {
        /**/
    }
    /**
     * Will be called whenever there is a creation error caused, use it to Debug your code.
     * @param e The Exception that was created.
     * @param c The Class that caused the issue.
     */
    public void OnError(Exception e, Class<? extends ChessPiece> c) {
        /**/
    }
}