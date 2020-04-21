package package1;

import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.Button;

import javax.swing.BoxLayout;
import java.awt.GridBagLayout;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import net.miginfocom.swing.MigLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

public class PanelTest2 extends JFrame {

    private final JPanel P1;
    private final JPanel P2;
    private final JPanel main;
    private final JScrollPane scrol;
    private final JButton jButton;
    private final JButton jButton2;

    public int numberOfStuff;
    
    public PanelTest2() {
    	numberOfStuff = 0;
        P1 = new JPanel();
        P2 = new JPanel();
        main = new JPanel();
        jButton = new JButton("Add");
        jButton2 = new JButton("Remove");
        scrol = new JScrollPane(P2);
        P2.setLayout(new MigLayout("", "[]", "[]"));
        initialize();
        getContentPane().add(main);
        this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        this.setSize(1075, 678);
        this.setVisible(true);

    }

    public static void main(String[] args) {
        PanelTest2 jframeExample = new PanelTest2();

    }
    
    private void addPressed(ActionEvent evt) {
    	numberOfStuff++;
    	JPanel temp = new JPanel();
    	temp.setLayout(new MigLayout());
    	JTextArea textArea = new JTextArea(Integer.toString(numberOfStuff) + " Story info", 4, 60);
    	
    	System.out.println(textArea.getRows());
    	
    	Font font = textArea.getFont();
    	float size = font.getSize() + 8.0f;
    	textArea.setFont( font.deriveFont(size));
    	
    	
    	temp.setBackground(Color.GRAY);
        P2.add(temp, "wrap");
        temp.add(textArea, "wrap");
        temp.add(new Button("Open Story (not working)"));
        revalidate();
    }

    private void removePressed(ActionEvent evt) {
    	numberOfStuff = 0;
        P2.removeAll();
        P2.repaint();
    }

    private void initialize() {
        main.setLayout(new MigLayout("", "[1100px]", "[300px][700px]"));
        main.add(P1, "cell 0 0,grow");
        main.add(scrol, "cell 0 1,grow");
        jButton.addActionListener((ActionEvent evt) -> {
            addPressed(evt);
        });
        jButton2.addActionListener((ActionEvent evt) -> {
            removePressed(evt);
        });
        P1.add(jButton);
        P1.add(jButton2);
    }

}