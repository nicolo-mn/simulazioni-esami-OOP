package a03a.e2;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    
    private static final long serialVersionUID = -6218820567019985015L;
    private final Map<JButton,Pair<Integer,Integer>> cells = new HashMap<>();
    private final Logics logics;
    
    public GUI(int size) {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100*size, 100*size);
        this.logics = new LogicsImpl(size);
        
        JPanel grid = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(BorderLayout.CENTER,grid);
        
        ActionListener el = e -> {
        	var button = (JButton)e.getSource();
        	var position = this.cells.get(button);
        	this.logics.hit(position.getX(), position.getY());
            if (this.logics.isOver()) {
                System.exit(0);
            }
            this.updateCells();
        };
                
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                final JButton jb = new JButton(" ");
                this.cells.put(jb, new Pair<>(j,i));
                grid.add(jb);
                jb.addActionListener(el);
            }
        }
        this.updateCells();
        // var random = new Random();
    	// var randomPosition = new Pair<>(random.nextInt(size),random.nextInt(size));
    	// cells.forEach( (button,position) -> { if (position.equals(randomPosition)) {button.setText("*");}});
    	
        this.setVisible(true);
    }

    void updateCells() {
        for (var entry : this.cells.entrySet()) {
            final var pos = entry.getValue();
            final var jb = entry.getKey();
            if (this.logics.isRook(pos.getX(), pos.getY())) {
                jb.setText("R");
            }
            else if (this.logics.isPawn(pos.getX(), pos.getY())) {
                jb.setText("*");
            }
            else {
                jb.setText(" ");
            }
            jb.setEnabled(this.logics.isSelected(pos.getX(), pos.getY()) ? true : false);
        }
    }
    
}
