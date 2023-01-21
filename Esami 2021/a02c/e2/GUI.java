package a02c.e2;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    
    private static final long serialVersionUID = -6218820567019985015L;
    private final Map<JButton, Pair<Integer, Integer>> cells = new HashMap<>();
    private final Logics logics;
    
    public GUI(int size) {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(50*size, 50*size);
        this.logics = new LogicsImpl(size);

        JPanel panel = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(panel);
        
        ActionListener al = e -> {
            if (logics.isOver()) {
                System.exit(0);
            }    
            logics.next();
            this.updateCells();
        };
                
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
            	var pos = new Pair<>(j,i);
                final JButton jb = new JButton();
                this.cells.put(jb, pos);
                jb.addActionListener(al);
                panel.add(jb);
            }
        }
        this.updateCells();
        this.setVisible(true);
    }
    
    private void updateCells() {
        var pos = logics.getPos();
        for (var entry : cells.entrySet()) {
            entry.getKey().setText(logics.isObstacle(entry.getValue()) ? "o" : " ");
            if (entry.getValue().equals(pos)) {
                entry.getKey().setText("*");
            }
        }
    }
}


