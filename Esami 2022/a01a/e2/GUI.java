package a01a.e2;

import javax.swing.*;
import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.*;

public class GUI extends JFrame {
    
    private final Map<JButton,Pair<Integer,Integer>> cells = new HashMap<>();
    private final Logics logics = new LogicsImpl();
    
    public GUI(int size) {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100*size, 100*size);
        
        JPanel panel = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(panel);
        
        ActionListener al = e -> {
        	var button = (JButton)e.getSource();
            this.logics.hit(this.cells.get(button).getX(), this.cells.get(button).getY());
            for (var entry : this.cells.entrySet()) {
                entry.getKey().setText(this.logics.isSelected(entry.getValue().getX(), entry.getValue().getY()) ? "*" : " "); 
            }
            if (this.logics.isOver()) {
                System.exit(0);
            }
        };
                
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                final JButton jb = new JButton(" ");
                final Pair<Integer, Integer> pos = new Pair<>(j,i);
                this.cells.put(jb,pos);
                jb.addActionListener(al);
                panel.add(jb);
            }
        }
        this.setVisible(true);
    }    
}
