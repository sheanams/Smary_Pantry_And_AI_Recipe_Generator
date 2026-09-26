package com.smartpantry.landing.utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

/**
 * UI helper utilities for creating modern, enterprise-grade Swing components.
 */
public final class UIHelper {

    private UIHelper() {}

    /**
     * Enables high-quality rendering hints on a Graphics2D context.
     */
    public static void enableAntialiasing(Graphics2D g2) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
    }

    /**
     * Creates a modern rounded button with hover effects.
     */
    public static JButton createRoundedButton(String text, Color bg, Color fg, Font font, int arcSize) {
        JButton btn = new JButton(text) {
            private boolean hovered = false;
            private boolean pressed = false;

            {
                addMouseListener(new MouseAdapter() {
                    @Override public void mouseEntered(MouseEvent e) { hovered = true; repaint(); }
                    @Override public void mouseExited(MouseEvent e)  { hovered = false; repaint(); }
                    @Override public void mousePressed(MouseEvent e) { pressed = true; repaint(); }
                    @Override public void mouseReleased(MouseEvent e){ pressed = false; repaint(); }
                });
            }

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                enableAntialiasing(g2);

                Color btnBg = bg;
                if (pressed) {
                    btnBg = btnBg.darker();
                } else if (hovered) {
                    btnBg = btnBg.brighter();
                }

                // Shadow
                if (!pressed) {
                    g2.setColor(new Color(0, 0, 0, 30));
                    g2.fill(new RoundRectangle2D.Float(2, 3, getWidth() - 4, getHeight() - 4, arcSize, arcSize));
                }

                // Button body
                g2.setColor(btnBg);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 3, arcSize, arcSize));

                // Text
                g2.setColor(fg);
                g2.setFont(font);
                FontMetrics fm = g2.getFontMetrics();
                int textX = (getWidth() - fm.stringWidth(getText())) / 2;
                int textY = (getHeight() - fm.getHeight()) / 2 + fm.getAscent() - 1;
                g2.drawString(getText(), textX, textY);

                g2.dispose();
            }
        };
        btn.setFont(font);
        btn.setForeground(fg);
        btn.setPreferredSize(new Dimension(
                btn.getFontMetrics(font).stringWidth(text) + 60,
                46
        ));
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    /**
     * Creates an outlined (ghost) button with rounded corners.
     */
    public static JButton createOutlinedButton(String text, Color borderColor, Color fg, Font font, int arcSize) {
        JButton btn = new JButton(text) {
            private boolean hovered = false;

            {
                addMouseListener(new MouseAdapter() {
                    @Override public void mouseEntered(MouseEvent e) { hovered = true; repaint(); }
                    @Override public void mouseExited(MouseEvent e)  { hovered = false; repaint(); }
                });
            }

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                enableAntialiasing(g2);

                if (hovered) {
                    g2.setColor(ColorPalette.withAlpha(borderColor, 25));
                    g2.fill(new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, arcSize, arcSize));
                }

                g2.setColor(borderColor);
                g2.setStroke(new BasicStroke(2f));
                g2.draw(new RoundRectangle2D.Float(1, 1, getWidth() - 3, getHeight() - 3, arcSize, arcSize));

                g2.setColor(hovered ? borderColor.brighter() : fg);
                g2.setFont(font);
                FontMetrics fm = g2.getFontMetrics();
                int textX = (getWidth() - fm.stringWidth(getText())) / 2;
                int textY = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();
                g2.drawString(getText(), textX, textY);

                g2.dispose();
            }
        };
        btn.setFont(font);
        btn.setPreferredSize(new Dimension(
                btn.getFontMetrics(font).stringWidth(text) + 60,
                46
        ));
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    /**
     * Creates HTML-formatted multi-line label.
     */
    public static JLabel createWrappedLabel(String text, Font font, Color color, int maxWidth) {
        String html = "<html><div style='width:" + maxWidth + "px; text-align:center;'>" + text + "</div></html>";
        JLabel label = new JLabel(html);
        label.setFont(font);
        label.setForeground(color);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        return label;
    }

    /**
     * Creates a simple spacer panel.
     */
    public static Component verticalSpacer(int height) {
        return Box.createRigidArea(new Dimension(0, height));
    }

    public static Component horizontalSpacer(int width) {
        return Box.createRigidArea(new Dimension(width, 0));
    }
}
