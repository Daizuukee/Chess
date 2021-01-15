import chessGame.ChessMain;
import chessGame.ChessEventListener;
import chessGame.ChessPiece.Colors;
import pieces.*;

import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;

import chessGame.*;

public class JWindow extends ChessEventListener {

    public static void main(String[] args) {
        new JWindow().chess();
    }
    
    public class Frame extends JFrame implements ActionListener {

        public JWindow jw;
        public int actualWidth = 700;
        public int actualHeight = 700;
        public Frame(JWindow JW) {
            super("Chess");
            this.jw = JW;
            setSize(actualWidth+10,actualHeight+40);
            setResizable(false);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            jw.ButtonPressed(((JButton)e.getSource()).getName());
        }
    }

    int[][] pieceIndexes = { 
        { 1, 2, 3, 5, 4, 3, 2, 1 }, 
        { 0, 0, 0, 0, 0, 0, 0, 0 },
        {-1,-1,-1,-1,-1,-1,-1,-1 }, 
        {-1,-1,-1,-1,-1,-1,-1,-1 },
        {-1,-1,-1,-1,-1,-1,-1,-1 }, 
        {-1,-1,-1,-1,-1,-1,-1,-1 }, 
        { 0, 0, 0, 0, 0, 0, 0, 0 },
        { 1, 2, 3, 4, 5, 3, 2, 1 } };

    CreateablePieces createablePieces = new CreateablePieces(Pawn.class, Rook.class, Bishop.class, Horse.class, King.class, Queen.class);

    int[][] pieceCols = { 
        { 1, 1, 1, 1, 1, 1, 1, 1 }, 
        { 1, 1, 1, 1, 1, 1, 1, 1 }, 
        { 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0 }, 
        { 0, 0, 0, 0, 0, 0, 0, 0 }, 
        { 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0 }, 
        { 0, 0, 0, 0, 0, 0, 0, 0 } };

    Boolean exited = false;

    ChessMain m;

    JButton[][] buttons;
    Frame frame;

    public void chess() {
        frame = new Frame(this);
        frame.setVisible(true);
        frame.setBackground(Color.BLACK);
        
        try {
            m = new ChessMain(pieceIndexes, createablePieces, pieceCols, this);
        } catch (Exception e) {
            return;
        }

        buttons = new JButton[pieceIndexes.length][];
        for(int y = 0; y < pieceIndexes.length; y++) {
            buttons[y] = new JButton[pieceIndexes[0].length];
            for(int x = 0; x < pieceIndexes[0].length; x++) {
                JButton jb = new JButton();
                jb.setBounds(x * frame.actualWidth / pieceIndexes[0].length,
                             y * frame.actualHeight / pieceIndexes.length, 
                             frame.actualWidth / pieceIndexes[0].length, 
                             frame.actualHeight / pieceIndexes.length);
                jb.setName(x+";"+y);
                jb.addActionListener(frame);
                jb.update(frame.getGraphics());
                buttons[y][x] = jb;
                jb.updateUI();
                frame.add(jb);
            }
        }
        //FOR REASONS THAT ARE BEYOND ME THIS IS NEEDED IN ORDER FOR THE BUTTONS TO RENDER CORRECTLY
        JButton jb = new JButton();
        jb.setBounds(-999999999,-99999999,0,0);
        jb.setVisible(false);
        frame.add(jb);
        output(m,-1,-1);
    }

    public void output(ChessMain m, int x, int y) {
        Boolean[][] s;
        try {
            s = (x < 0 || y < 0) ? new Boolean[0][] : m.possible(x, y); // Check if linting is possible
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return; // A mistake occured when reading m.possible() the board cannot be displayed
        }
        for (int iy = 0; iy < m.getPieceTypes().length; iy++) {
            int[] pt = m.getPieceTypes()[iy];
            for (int ix = 0; ix < pt.length; ix++) {
                Boolean darken = m.getPieceTypes()[iy][ix] != -1
                                 && m.getPieces().get(m.getIndexMap()[iy][ix]).color == Colors.BLACK; 
                                 // check if a piece is not white/the board because of
                                 // coloring the different pieces
                int p = pt[ix];
                
                buttons[iy][ix].setBackground(((ix+iy)%2==0)?Color.gray:Color.darkGray); //standard color

                if (!(x < 0 || y < 0) && s[iy][ix].booleanValue()) //primitive value to suppress null
                    buttons[iy][ix].setBackground(Color.CYAN); //Selectable

                if (x == ix && y == iy)
                    buttons[iy][ix].setBackground(Color.orange); //Self

                if (darken)
                    buttons[iy][ix].setForeground(Color.black); //Black piece
                else
                    buttons[iy][ix].setForeground(Color.white); //White piece

                switch (p) {
                    case 0:
                        buttons[iy][ix].setText("Pawn"); //Pawn
                        break;
                    case 1:
                        buttons[iy][ix].setText("Rook"); // Rook
                        break;
                    case 2:
                        buttons[iy][ix].setText("Bishop"); // Bishop
                        break;
                    case 3:
                        buttons[iy][ix].setText("Horse"); // Horse
                        break;
                    case 4:
                        buttons[iy][ix].setText("King"); // King
                        break;
                    case 5:
                        buttons[iy][ix].setText("Queen"); // Queen
                        break;
                    default:
                        buttons[iy][ix].setText(""); // Empty tile [-1]
                        break;
                }
            }
        }
        frame.update(frame.getGraphics());
    }

    int selectX = -1;
    int selectY = -1;
    public void ButtonPressed(String name) {
        int x = Integer.parseInt(name.split(";")[0]);
        int y = Integer.parseInt(name.split(";")[1]);
        if(selectX == -1 && selectY == -1) {
        if(m.getPieceTypes()[y][x] != -1) {
            output(m,x,y);
            selectX = x;
            selectY = y;
        }
        }
        else {
            try {
                m.move(selectX,selectY,x,y);
            } catch(Exception e) {
                System.out.println(e.getMessage());
            }
            selectX = -1;
            selectY = -1;
            output(m,-1,-1);
        }
    }

    @Override
	public void onPieceDestroyed(ChessPiece cp, ChessPiece origin, int x, int y) {
        if(cp.getClass() == King.class) {
            try {
                m = new ChessMain(pieceIndexes, createablePieces, pieceCols, this);
            } catch (Exception e) {
                return;
            }
            output(m,-1,-1);
        }
    }
}