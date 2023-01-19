package a01b.e2;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    
    private static final long serialVersionUID = -6218820567019985015L;
    private final Map<JButton, Pair<Integer, Integer>> cells = new HashMap<>();
    Logics logics = new LogicsImpl();
    
    public GUI(int size) {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(50*size, 50*size);
        
        JPanel panel = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(panel);
        
        ActionListener al = e -> {
        	var button = (JButton)e.getSource();
            var position = cells.get(button);
        	logics.hit(position.getX(), position.getY());
            for (var entry : this.cells.entrySet()) {
                if (logics.isSelected(entry.getValue().getX(), entry.getValue().getY())) {
                    if (logics.isFirst(entry.getValue().getX(), entry.getValue().getY())) {
                        entry.getKey().setText("1");
                    }
                    else if (logics.isSecond(entry.getValue().getX(), entry.getValue().getY())) {
                        entry.getKey().setText("2");
                    }
                    else if (logics.isThird(entry.getValue().getX(), entry.getValue().getY())) {
                        entry.getKey().setText("3");
                    }
                    else {
                        entry.getKey().setText("*");
                    }
                }
                if (logics.isOver()) {
                    entry.getKey().setEnabled(false);
                }
            }
        };
                
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                final JButton jb = new JButton(" ");
                this.cells.put(jb, new Pair<>(i,j));
                jb.addActionListener(al);
                panel.add(jb);
            }
        }
        this.setVisible(true);
    }
    
}
