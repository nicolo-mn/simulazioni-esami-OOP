package a02b.e2;

import javax.swing.*;
import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    
    private static final long serialVersionUID = -6218820567019985015L;
    private final Map<JButton, Pair<Integer, Integer>> cells = new HashMap<>();
    private Logics logics;
    
    public GUI(int size) {
        logics = new LogicsImpl(size);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(50*size, 50*size);
        
        JPanel panel = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(panel);
        
        ActionListener al = e -> update();
                
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
            	var pos = new Pair<>(j,i);
                final JButton jb = new JButton();
                this.cells.put(jb, pos);
                jb.addActionListener(al);
                panel.add(jb);
            }
        }

        update();

        this.setVisible(true);
    }

    private void update() {
        var pos = logics.next();
        if (pos.isEmpty()) {
            System.exit(0);
        }
        for (var entry : this.cells.entrySet()) {
            if (entry.getValue().equals(pos.get())) {
                entry.getKey().setText("*");
            }
            else if (logics.isRight(entry.getValue())) {
                entry.getKey().setText("R");
            }
            else if (logics.isLeft(entry.getValue())) {
                entry.getKey().setText("L");
            }
            else {
                entry.getKey().setText(" ");
            }
        }
    }    
}
