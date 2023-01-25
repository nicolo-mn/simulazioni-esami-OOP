package a05.e2;

import javax.swing.*;
import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    
    private static final long serialVersionUID = -6218820567019985015L;
    private final Map<JButton, Pair<Integer,Integer>> cells = new HashMap<>();
    private final Logics logics;
    
    public GUI(int size) {
        this.logics = new LogicsImpl(size);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100*size, 100*size);
        
        JPanel panel = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(panel);
        
        ActionListener al = e -> {
        	var button = (JButton)e.getSource();
            this.logics.hit(this.cells.get(button).getX(), this.cells.get(button).getY());
            this.updateCells();
            if (this.logics.isOver()) {
                System.exit(0);
            }
        };
                
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                final Pair<Integer,Integer> pos = new Pair<>(j,i);
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
        for (var entry : this.cells.entrySet()) {
            entry.getKey().setText(this.logics.isNumber(entry.getValue().getX(), entry.getValue().getY())
                                   ? Integer.toString(this.logics.getNumber(entry.getValue().getX(), entry.getValue().getY()))
                                   : "X"); 
        }
    }
    
}
