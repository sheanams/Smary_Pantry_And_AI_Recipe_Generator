package com.smartpantry.landing.components;

import com.smartpantry.landing.utils.ColorPalette;
import com.smartpantry.landing.utils.FontManager;
import com.smartpantry.landing.utils.UIHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

/**
 * A modern card component with hover-lift effect, icon, title, and description.
 * Used for feature cards, module cards, etc.
 */
public class ModernCard extends JPanel {

    private final String icon;
    private final String title;
    private final String description;
    private final Color accentColor;
    private float hoverProgress = 0f;
    private Timer hoverTimer;
    private boolean isHovered = false;

    public ModernCard(String icon, String title, String description, Color accentColor) {
        this.icon = icon;
        this.title = title;
        this.description = description;
        this.accentColor = accentColor;

        setOpaque(false);
        setPreferredSize(new Dimension(320, 260));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                isHovered = true;
                startHoverAnimation(true);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                isHovered = false;
                startHoverAnimation(false);
            }
        });
    }

    private void startHoverAnimation(boolean entering) {
        if (hoverTimer != null && hoverTimer.isRunning()) {
            hoverTimer.stop();
        }
        hoverTimer = new Timer(16, e -> {
            if (entering) {
                hoverProgress = Math.min(1f, hoverProgress + 0.1f);
            } else {
                hoverProgress = Math.max(0f, hoverProgress - 0.1f);
            }
            repaint();
            if ((entering && hoverProgress >= 1f) || (!entering && hoverProgress <= 0f)) {
                ((Timer) e.getSource()).stop();
            }
        });
        hoverTimer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        UIHelper.enableAntialiasing(g2);

        int w = getWidth();
        int h = getHeight();
        int arc = 20;
        float yOffset = -6 * hoverProgress;

        // ── Shadow ───────────────────────────────────────────────────
        int shadowAlpha = 15 + (int)(20 * hoverProgress);
        int shadowSpread = 4 + (int)(6 * hoverProgress);
        g2.setColor(new Color(0, 0, 0, shadowAlpha));
        g2.fill(new RoundRectangle2D.Float(
                shadowSpread, shadowSpread + yOffset + 4,
                w - shadowSpread * 2, h - shadowSpread * 2,
                arc, arc
        ));

        // ── Card body ────────────────────────────────────────────────
        g2.translate(0, yOffset);
        g2.setColor(ColorPalette.CARD_BG);
        g2.fill(new RoundRectangle2D.Float(4, 4, w - 8, h - 12, arc, arc));

        // ── Top accent bar ───────────────────────────────────────────
        g2.setClip(new RoundRectangle2D.Float(4, 4, w - 8, h - 12, arc, arc));
        GradientPaint accentGrad = new GradientPaint(
                0, 0, accentColor, w, 0, accentColor.brighter()
        );
        g2.setPaint(accentGrad);
        g2.fillRect(4, 4, w - 8, 5);
        g2.setClip(null);

        // ── Border on hover ──────────────────────────────────────────
        if (hoverProgress > 0) {
            g2.setColor(ColorPalette.withAlpha(accentColor, (int)(80 * hoverProgress)));
            g2.setStroke(new BasicStroke(2f));
            g2.draw(new RoundRectangle2D.Float(4, 4, w - 9, h - 13, arc, arc));
        }

        // ── Icon circle ─────────────────────────────────────────────
        int iconSize = 54;
        int iconX = (w - iconSize) / 2;
        int iconY = 30;
        g2.setColor(ColorPalette.withAlpha(accentColor, 25));
        g2.fillOval(iconX, iconY, iconSize, iconSize);
        g2.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 26));
        FontMetrics ifm = g2.getFontMetrics();
        int emojiX = iconX + (iconSize - ifm.stringWidth(icon)) / 2;
        int emojiY = iconY + (iconSize - ifm.getHeight()) / 2 + ifm.getAscent();
        g2.setColor(accentColor);
        g2.drawString(icon, emojiX, emojiY);

        // ── Title ────────────────────────────────────────────────────
        g2.setFont(FontManager.cardTitle());
        g2.setColor(ColorPalette.TEXT_PRIMARY);
        FontMetrics tfm = g2.getFontMetrics();
        int titleX = (w - tfm.stringWidth(title)) / 2;
        g2.drawString(title, titleX, iconY + iconSize + 32);

        // ── Description (wrapped) ────────────────────────────────────
        g2.setFont(FontManager.cardBody());
        g2.setColor(ColorPalette.TEXT_SECONDARY);
        drawWrappedText(g2, description, 28, iconY + iconSize + 52, w - 56);

        g2.dispose();
    }

    private void drawWrappedText(Graphics2D g2, String text, int x, int y, int maxWidth) {
        FontMetrics fm = g2.getFontMetrics();
        String[] words = text.split(" ");
        StringBuilder line = new StringBuilder();
        int lineY = y;

        for (String word : words) {
            String testLine = line.length() == 0 ? word : line + " " + word;
            if (fm.stringWidth(testLine) > maxWidth) {
                String centeredLine = line.toString();
                int cx = x + (maxWidth - fm.stringWidth(centeredLine)) / 2;
                g2.drawString(centeredLine, cx, lineY);
                line = new StringBuilder(word);
                lineY += fm.getHeight() + 3;
            } else {
                line = new StringBuilder(testLine);
            }
        }
        if (line.length() > 0) {
            String centeredLine = line.toString();
            int cx = x + (maxWidth - fm.stringWidth(centeredLine)) / 2;
            g2.drawString(centeredLine, cx, lineY);
        }
    }
}
