package a04.e2;

import javax.swing.*;
import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    
    private static final long serialVersionUID = -6218820567019985015L;
    private final Map<JButton, Pair<Integer, Integer>> cells = new HashMap<>();
    private final Logics logics;
       
    public GUI(int size) {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100*size, 100*size);
        this.logics = new LogicsImpl(size);
        JPanel panel = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(panel);
        
        ActionListener al = e -> {
        	var button = (JButton)e.getSource();
            this.logics.hit(this.cells.get(button).getX(), this.cells.get(button).getY());
            this.updateView();
            if (this.logics.isOver()) {
                System.exit(0);
            }
        };
                
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                //final JButton jb = new JButton(""+'\u2659'+'\u265A'+'\u265E');
                final JButton jb = new JButton();
                final Pair<Integer, Integer> pos = new Pair<>(j,i);
                this.cells.put(jb, pos);
                jb.addActionListener(al);
                panel.add(jb);
            }
        }
        this.updateView();
        this.setVisible(true);
    }

    private void updateView() {
        final Set<Pair<Integer,Integer>> pawns = this.logics.getPawns();
        this.cells.forEach((b,p) -> b.setText(this.logics.getMainPos().equals(p)
                                              ? this.logics.isKing() ? "\u265A" : "\u265E"
                                              : pawns.contains(p) ? "\u2659" : " "));
    }
    
}
