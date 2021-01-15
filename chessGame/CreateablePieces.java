package chessGame;

public final class CreateablePieces {
    private final Class<? extends ChessPiece>[] Pieces;
    
    @SuppressWarnings("unchecked")
    public CreateablePieces(Class<? extends ChessPiece>... classes) {
        Pieces = classes;
    }

    public Class<? extends ChessPiece> get(int index) {
        return Pieces[index];
    }
}
