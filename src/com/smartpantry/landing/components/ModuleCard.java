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
 * A module step card used in the "How It Works" or Modules section.
 * Displays a step number, title, and feature list.
 */
public class ModuleCard extends JPanel {

    private final int stepNumber;
    private final String title;
    private final String[] features;
    private final Color accentColor;
    private boolean isHovered = false;

    public ModuleCard(int stepNumber, String title, String[] features, Color accentColor) {
        this.stepNumber = stepNumber;
        this.title = title;
        this.features = features;
        this.accentColor = accentColor;
        setOpaque(false);
        setPreferredSize(new Dimension(340, 240));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) { isHovered = true; repaint(); }
            @Override
            public void mouseExited(MouseEvent e) { isHovered = false; repaint(); }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        UIHelper.enableAntialiasing(g2);

        int w = getWidth();
        int h = getHeight();
        int arc = 20;

        // ── Shadow ──────────────────────────────────────────────────
        g2.setColor(new Color(0, 0, 0, isHovered ? 25 : 12));
        g2.fill(new RoundRectangle2D.Float(6, 6, w - 12, h - 12, arc, arc));

        // ── Card body ───────────────────────────────────────────────
        g2.setColor(ColorPalette.CARD_BG);
        g2.fill(new RoundRectangle2D.Float(4, 2, w - 8, h - 10, arc, arc));

        // ── Left accent stripe ──────────────────────────────────────
        g2.setClip(new RoundRectangle2D.Float(4, 2, w - 8, h - 10, arc, arc));
        GradientPaint gp = new GradientPaint(0, 0, accentColor, 0, h, accentColor.darker());
        g2.setPaint(gp);
        g2.fillRect(4, 2, 6, h);
        g2.setClip(null);

        // ── Hover border ────────────────────────────────────────────
        if (isHovered) {
            g2.setColor(ColorPalette.withAlpha(accentColor, 60));
            g2.setStroke(new BasicStroke(2));
            g2.draw(new RoundRectangle2D.Float(4, 2, w - 9, h - 11, arc, arc));
        }

        // ── Step badge ──────────────────────────────────────────────
        int badgeSize = 36;
        int badgeX = 28;
        int badgeY = 20;
        g2.setColor(accentColor);
        g2.fillOval(badgeX, badgeY, badgeSize, badgeSize);
        g2.setFont(FontManager.bodyBold());
        g2.setColor(Color.WHITE);
        FontMetrics bfm = g2.getFontMetrics();
        String stepStr = String.valueOf(stepNumber);
        g2.drawString(stepStr,
                badgeX + (badgeSize - bfm.stringWidth(stepStr)) / 2,
                badgeY + (badgeSize - bfm.getHeight()) / 2 + bfm.getAscent()
        );

        // ── Title ───────────────────────────────────────────────────
        g2.setFont(FontManager.cardTitle());
        g2.setColor(ColorPalette.TEXT_PRIMARY);
        g2.drawString(title, badgeX + badgeSize + 14, badgeY + 24);

        // ── Features list ───────────────────────────────────────────
        g2.setFont(FontManager.cardBody());
        g2.setColor(ColorPalette.TEXT_SECONDARY);
        int featureY = badgeY + badgeSize + 24;
        for (String feature : features) {
            // Bullet dot
            g2.setColor(accentColor);
            g2.fillOval(30, featureY - 5, 7, 7);
            // Feature text
            g2.setColor(ColorPalette.TEXT_SECONDARY);
            g2.drawString(feature, 46, featureY);
            featureY += 26;
        }

        g2.dispose();
    }
}
