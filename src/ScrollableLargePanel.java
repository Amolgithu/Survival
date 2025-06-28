import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ScrollableLargePanel {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ScrollableLargePanel().createUI());
    }

    private void createUI() {
        JFrame frame = new JFrame("Large Panel View");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        // Large panel with some drawing
        JPanel largePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Draw a grid
                for (int x = 0; x < getWidth(); x += 100) {
                    for (int y = 0; y < getHeight(); y += 100) {
                        g.drawRect(x, y, 100, 100);
                        g.drawString("(" + x + "," + y + ")", x + 20, y + 20);
                    }
                }
            }
        };
        largePanel.setPreferredSize(new Dimension(5000, 5000));

        // Put it inside a scroll pane
        JScrollPane scrollPane = new JScrollPane(largePanel,
                JScrollPane.VERTICAL_SCROLLBAR_NEVER,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        frame.add(scrollPane);

        // Add key bindings to scroll using arrow keys
        InputMap im = scrollPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = scrollPane.getActionMap();

        im.put(KeyStroke.getKeyStroke("UP"), "scrollUp");
        im.put(KeyStroke.getKeyStroke("DOWN"), "scrollDown");
        im.put(KeyStroke.getKeyStroke("LEFT"), "scrollLeft");
        im.put(KeyStroke.getKeyStroke("RIGHT"), "scrollRight");

        int scrollAmount = 50;

        am.put("scrollUp", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                JScrollBar bar = scrollPane.getVerticalScrollBar();
                bar.setValue(bar.getValue() - scrollAmount);
            }
        });

        am.put("scrollDown", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                JScrollBar bar = scrollPane.getVerticalScrollBar();
                bar.setValue(bar.getValue() + scrollAmount);
            }
        });

        am.put("scrollLeft", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                JScrollBar bar = scrollPane.getHorizontalScrollBar();
                bar.setValue(bar.getValue() - scrollAmount);
            }
        });

        am.put("scrollRight", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                JScrollBar bar = scrollPane.getHorizontalScrollBar();
                bar.setValue(bar.getValue() + scrollAmount);
            }
        });

        frame.setVisible(true);
    }
}
