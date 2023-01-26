package a01b.e2;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    
    private static final long serialVersionUID = -6218820567019985015L;
    private final Map<JButton, Pair<Integer, Integer>> cells = new HashMap<>();
    private final Logics logics = new LogicsImpl();
    
    public GUI(int size) {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(50*size, 50*size);
        
        JPanel panel = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(panel);
        
        ActionListener al = e -> {
        	var button = (JButton)e.getSource();
            var pos = this.cells.get(button);
            var res = this.logics.hit(pos.getX(), pos.getY());
            switch (res) {
                case FIRST: button.setText("1"); break;
                case SECOND: button.setText("2"); break;
                case THIRD: button.setText("3");
                    for (var entry : this.cells.entrySet()) {
                        if (this.logics.isSelected(entry.getValue().getX(), entry.getValue().getY())) {
                            entry.getKey().setText("*");
                        }
                        entry.getKey().setEnabled(false);
                    }
                case WRONG:
            }
        	
        };
                
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                final Pair<Integer,Integer> pos = new Pair<>(j,i);
                final JButton jb = new JButton(" ");
                this.cells.put(jb, pos);
                jb.addActionListener(al);
                panel.add(jb);
            }
        }
        this.setVisible(true);
    }
    
}
