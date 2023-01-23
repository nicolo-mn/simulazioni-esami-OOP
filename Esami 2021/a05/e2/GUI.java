package a05.e2;

import javax.swing.*;

import java.util.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    
    private static final long serialVersionUID = -6218820567019985015L;
    private final Map<JButton, Pair<Integer, Integer>> cells = new HashMap<>();
    private Logics logics;
    
    public GUI(int size) {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100*size, 100*size);
        
        this.logics = new LogicsImpl(size);
        JPanel grid = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(BorderLayout.CENTER,grid);
        
        ActionListener al = e -> {
        	var jb = (JButton)e.getSource();
            logics.hit(this.cells.get(jb).getX(), this.cells.get(jb).getY());
            if (logics.isOver()) {
                System.exit(0);
            }
        };
                
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                final Pair<Integer, Integer> pos = new Pair<>(j,i);
                final JButton jb = new JButton(this.logics.isCellBoolean(pos.getX(), pos.getY())
                                    ? this.logics.getCellAsBool(pos.getX(), pos.getY()) ? "True" : "False"
                                    : this.logics.getCellAsOperation(pos.getX(), pos.getY()).getName());
                this.cells.put(jb, pos);
                grid.add(jb);
                jb.addActionListener(al);
            }
        }
        this.setVisible(true);
    }
}