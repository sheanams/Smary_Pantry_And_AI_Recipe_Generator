package com.smartpantry.landing.panels;

import com.smartpantry.landing.components.StatCard;
import com.smartpantry.landing.utils.ColorPalette;
import com.smartpantry.landing.utils.FontManager;
import com.smartpantry.landing.utils.UIHelper;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Statistics / metrics bar section with animated count-up numbers.
 * Shows key impact metrics on a dark gradient background.
 */
public class StatsPanel extends JPanel {

    private final List<StatCard> statCards = new ArrayList<>();

    public StatsPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(60, 60, 60, 60));

        // ── Section label ────────────────────────────────────────────
        JLabel label = new JLabel("IMPACT METRICS");
        label.setFont(FontManager.badge());
        label.setForeground(ColorPalette.PRIMARY_LIGHT);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel title = new JLabel("Real Results, Real Savings");
        title.setFont(FontManager.sectionTitle());
        title.setForeground(ColorPalette.TEXT_WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(label);
        add(UIHelper.verticalSpacer(10));
        add(title);
        add(UIHelper.verticalSpacer(40));

        // ── Stat cards row ───────────────────────────────────────────
        JPanel row = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 0));
        row.setOpaque(false);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));

        StatCard s1 = new StatCard("40", "%", "Food Waste Reduced", ColorPalette.PRIMARY);
        StatCard s2 = new StatCard("10000", "+", "Recipes Available", ColorPalette.ACCENT);
        StatCard s3 = new StatCard("500", "K+", "Active Users", ColorPalette.INFO);
        StatCard s4 = new StatCard("98", "%", "User Satisfaction", ColorPalette.SUCCESS);

        statCards.add(s1);
        statCards.add(s2);
        statCards.add(s3);
        statCards.add(s4);

        row.add(s1);
        row.add(s2);
        row.add(s3);
        row.add(s4);

        add(row);

        // ── Trigger animation after a short delay ────────────────────
        Timer delayTimer = new Timer(800, e -> {
            for (StatCard card : statCards) {
                card.startAnimation();
            }
            ((Timer) e.getSource()).stop();
        });
        delayTimer.setRepeats(false);
        delayTimer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        UIHelper.enableAntialiasing(g2);
        int w = getWidth();
        int h = getHeight();

        // ── Dark gradient background ─────────────────────────────────
        GradientPaint gp = new GradientPaint(
                0, 0, ColorPalette.BG_DARK,
                w, h, ColorPalette.BG_DARK_ALT
        );
        g2.setPaint(gp);
        g2.fillRect(0, 0, w, h);

        // ── Subtle dots pattern ──────────────────────────────────────
        g2.setColor(ColorPalette.withAlpha(ColorPalette.PRIMARY, 10));
        for (int x = 20; x < w; x += 40) {
            for (int y = 20; y < h; y += 40) {
                g2.fillOval(x, y, 3, 3);
            }
        }

        g2.dispose();
        super.paintComponent(g);
    }
}
