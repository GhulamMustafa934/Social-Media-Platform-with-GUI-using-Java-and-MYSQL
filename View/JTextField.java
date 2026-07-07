package View;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.BorderFactory;

public class JTextField extends javax.swing.JTextField {

    private Shape shape;
    private String hint;

    public JTextField(String hint) {
        super();
        this.hint = hint;
        setFont(new Font("Segoe UI", Font.BOLD, 20));
        setOpaque(false);
        setText(hint);
        setForeground(GUIConstants.textFieldHint);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        addFocusListener(new FocusListener() {
            @Override
            public void focusLost(FocusEvent e) {
                if (getText().equals("")) {
                    setText(hint);
                    setForeground(GUIConstants.textFieldHint);
                }
            }

            @Override
            public void focusGained(FocusEvent e) {
                if (getText().equals(hint)) {
                    setText("");
                    setForeground(GUIConstants.black);
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(GUIConstants.white);
        g.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 45, 45);
        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        g.setColor(GUIConstants.white);
        g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 45, 45);
    }

    @Override
    public boolean contains(int x, int y) {
        if (shape == null || !shape.getBounds().equals(getBounds())) {
            shape = new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, 45, 45);
        }
        return shape.contains(x, y);
    }
}