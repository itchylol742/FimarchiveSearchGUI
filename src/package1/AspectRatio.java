package package1;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Properties;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class AspectRatio {
    private static final double ASPECT_RATIO = 3.0 / 4.0;

    public static void main(String[] args) {
    	Properties props = System.getProperties();

        //We want to loop through the entrys using the Keyset
        Set<Object> propKeySet = props.keySet();

       for (Object singleKey : propKeySet) {
       System.out.println(singleKey += props.getProperty((String) singleKey));    
       }
    	System.out.println(System.getProperty("sun.java2d.uiScale" ));
    	System.setProperty("Dsun.java2d.uiScale", "3");
        final JPanel preview = new JPanel();
        preview.setBackground(Color.YELLOW);
        preview.setBorder(new LineBorder(Color.BLUE, 5));

        final JPanel container = new JPanel(new GridBagLayout());
        container.add(preview);
        container.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                resizePreview(preview, container);
            }
        });

        final JFrame frame = new JFrame("AspectRatio");
        frame.getContentPane().add(container);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static void resizePreview(JPanel preview, JPanel container) {
        int w = container.getWidth();
        int h = container.getHeight();
        int previewWidth = (int) Math.round(w / 3.0);
        int previewHeight = (int) Math.round(previewWidth * ASPECT_RATIO);
        // say the container is 120x20, then the preview should be 40x30, which
        // would not fit the height
        if (previewHeight > h)
            previewHeight = h;
        preview.setPreferredSize(new Dimension(previewWidth, previewHeight));
        container.revalidate();
    }
}