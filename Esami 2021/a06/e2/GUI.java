package a06.e2;

import javax.swing.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    
    private static final long serialVersionUID = -6218820567019985015L;
    private final Map<JButton, Pair<Integer,Integer>> cells = new HashMap<>();
    private final JButton advance = new JButton("ADVANCE");
    private final Logics logics;
        
    public GUI(int size) {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100*size, 100*size);
        this.logics = new LogicsImpl(size);
        
        JPanel grid = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(BorderLayout.CENTER,grid);
        this.getContentPane().add(BorderLayout.SOUTH,advance);
        
        advance.addActionListener(e -> {
        	final var res = this.logics.hitAdvance();
            switch (res) {
                case CLOSE: System.exit(0); break;
                case FIRSTCORRECT: for (var entry : this.cells.entrySet()) {
                    entry.getKey().setEnabled(false);
                }
                case CORRECT: for (var entry : this.cells.entrySet()) {
                    if (this.logics.isAsterisk(entry.getValue().getX(), entry.getValue().getY())) {
                        entry.getKey().setText("*");
                    } 
                } break;
                case WRONG:
            }
       	});
        
        ActionListener al = e -> {
        	final var jb = (JButton)e.getSource();
        	this.logics.hitCell(this.cells.get(jb).getX(), this.cells.get(jb).getY());
            for (var entry : this.cells.entrySet()) {
                if (this.logics.isPoint(entry.getValue().getX(), entry.getValue().getY())) {
                    entry.getKey().setText("O");
                }
            }
        };
                
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                final Pair<Integer, Integer> pos = new Pair<>(j,i);
                final JButton jb = new JButton("  ");
                this.cells.put(jb,pos);
                grid.add(jb);
                jb.addActionListener(al);
            }
        }
        this.setVisible(true);
    }
}