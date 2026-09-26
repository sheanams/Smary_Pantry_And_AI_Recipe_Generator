package com.smartpantry.landing.components;

import com.smartpantry.landing.utils.ColorPalette;
import com.smartpantry.landing.utils.FontManager;
import com.smartpantry.landing.utils.UIHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

/**
 * An animated statistic counter card for the metrics section.
 * Displays a large number with label and an animated count-up effect.
 */
public class StatCard extends JPanel {

    private final String value;
    private final String suffix;
    private final String label;
    private final Color accentColor;
    private float animProgress = 0f;

    public StatCard(String value, String suffix, String label, Color accentColor) {
        this.value = value;
        this.suffix = suffix;
        this.label = label;
        this.accentColor = accentColor;
        setOpaque(false);
        setPreferredSize(new Dimension(200, 130));
    }

    /** Start the count-up animation. */
    public void startAnimation() {
        Timer timer = new Timer(20, null);
        timer.addActionListener(e -> {
            animProgress = Math.min(1f, animProgress + 0.03f);
            repaint();
            if (animProgress >= 1f) {
                timer.stop();
            }
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        UIHelper.enableAntialiasing(g2);

        int w = getWidth();
        int h = getHeight();

        // ── Background ──────────────────────────────────────────────
        g2.setColor(ColorPalette.withAlpha(ColorPalette.BG_WHITE, 10));
        g2.fill(new RoundRectangle2D.Float(0, 0, w, h, 16, 16));

        // ── Accent dot ──────────────────────────────────────────────
        g2.setColor(accentColor);
        g2.fillOval(w / 2 - 4, 14, 8, 8);

        // ── Animated value ──────────────────────────────────────────
        String displayValue;
        try {
            int numericVal = Integer.parseInt(value);
            int animatedVal = (int)(numericVal * animProgress);
            displayValue = String.valueOf(animatedVal) + suffix;
        } catch (NumberFormatException e) {
            displayValue = value + suffix;
        }

        g2.setFont(FontManager.statNumber());
        g2.setColor(ColorPalette.TEXT_WHITE);
        FontMetrics fm = g2.getFontMetrics();
        int textX = (w - fm.stringWidth(displayValue)) / 2;
        g2.drawString(displayValue, textX, 70);

        // ── Label ───────────────────────────────────────────────────
        g2.setFont(FontManager.statLabel());
        g2.setColor(ColorPalette.TEXT_ON_DARK);
        fm = g2.getFontMetrics();
        textX = (w - fm.stringWidth(label)) / 2;
        g2.drawString(label, textX, 100);

        g2.dispose();
    }
}
