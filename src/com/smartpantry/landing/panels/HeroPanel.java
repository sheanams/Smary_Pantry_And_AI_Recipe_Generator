package com.smartpantry.landing.panels;

import com.smartpantry.landing.utils.ColorPalette;
import com.smartpantry.landing.utils.FontManager;
import com.smartpantry.landing.utils.UIHelper;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import javax.swing.*;

/**
 * Hero section with gradient background, animated floating particles,
 * headline, subtext, and dual CTA buttons.
 */
public class HeroPanel extends JPanel {

    private float animPhase = 0f;

    public HeroPanel(Runnable onStartTrial, Runnable onWatchDemo) {
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(0, 620));
        setOpaque(false);

        // ── Start background animation ───────────────────────────────
        Timer animTimer = new Timer(40, e -> {
            animPhase += 0.02f;
            repaint();
        });
        animTimer.start();

        buildContent(onStartTrial, onWatchDemo);
    }

    private void buildContent(Runnable onStartTrial, Runnable onWatchDemo) {
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);
        centerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ── Badge ────────────────────────────────────────────────────
        JLabel badge = new JLabel("  \uD83C\uDF3F  AI-Powered Kitchen Intelligence  ");
        badge.setFont(FontManager.badge());
        badge.setForeground(ColorPalette.PRIMARY_LIGHT);
        badge.setAlignmentX(Component.CENTER_ALIGNMENT);
        badge.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ColorPalette.withAlpha(ColorPalette.PRIMARY, 80), 1, true),
                BorderFactory.createEmptyBorder(6, 16, 6, 16)
        ));

        // ── Title line 1 ─────────────────────────────────────────────
        JLabel title1 = new JLabel("The SmartPantry");
        title1.setFont(FontManager.heroTitle());
        title1.setForeground(ColorPalette.TEXT_WHITE);
        title1.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ── Title line 2 (accented) ──────────────────────────────────
        JLabel title2 = new JLabel("AI Recipe Generator");
        title2.setFont(new Font(title1.getFont().getFamily(), Font.BOLD, 48));
        title2.setForeground(ColorPalette.PRIMARY);
        title2.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ── Subtitle ─────────────────────────────────────────────────
        JLabel subtitle = UIHelper.createWrappedLabel(
                "Transform your kitchen with intelligent inventory tracking, " +
                        "AI-driven recipe suggestions, and automated meal planning. " +
                        "Reduce food waste by up to 40% while discovering delicious new recipes.",
                FontManager.heroSubtitle(),
                ColorPalette.TEXT_ON_DARK,
                600
        );
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ── Buttons ──────────────────────────────────────────────────
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 16, 0));
        btnPanel.setOpaque(false);

        JButton primaryBtn = UIHelper.createRoundedButton(
                "Start Now",
                ColorPalette.PRIMARY, ColorPalette.TEXT_WHITE,
                FontManager.buttonPrimary(), 30
        );
        primaryBtn.setPreferredSize(new Dimension(200, 52));
        if (onStartTrial != null) {
            primaryBtn.addActionListener(e -> onStartTrial.run());
        }

        JButton secondaryBtn = UIHelper.createOutlinedButton(
                "Explore Live Demo",
                ColorPalette.TEXT_ON_DARK, ColorPalette.TEXT_ON_DARK,
                FontManager.buttonSecondary(), 30
        );
        secondaryBtn.setPreferredSize(new Dimension(200, 52));
        if (onWatchDemo != null) {
            secondaryBtn.addActionListener(e -> onWatchDemo.run());
        }

        btnPanel.add(primaryBtn);
        btnPanel.add(secondaryBtn);

        // ── Assemble ─────────────────────────────────────────────────
        centerPanel.add(badge);
        centerPanel.add(UIHelper.verticalSpacer(28));
        centerPanel.add(title1);
        centerPanel.add(UIHelper.verticalSpacer(4));
        centerPanel.add(title2);
        centerPanel.add(UIHelper.verticalSpacer(24));
        centerPanel.add(subtitle);
        centerPanel.add(UIHelper.verticalSpacer(36));
        centerPanel.add(btnPanel);

        add(centerPanel);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        UIHelper.enableAntialiasing(g2);
        int w = getWidth();
        int h = getHeight();

        // ── Gradient background ──────────────────────────────────────
        GradientPaint gp = new GradientPaint(
                0, 0, ColorPalette.HERO_GRAD_START,
                w, h, ColorPalette.HERO_GRAD_END
        );
        g2.setPaint(gp);
        g2.fillRect(0, 0, w, h);

        // ── Animated floating circles (particles) ────────────────────
        drawFloatingParticles(g2, w, h);

        // ── Grid pattern overlay ─────────────────────────────────────
        g2.setColor(ColorPalette.withAlpha(ColorPalette.PRIMARY, 6));
        for (int x = 0; x < w; x += 60) {
            g2.drawLine(x, 0, x, h);
        }
        for (int y = 0; y < h; y += 60) {
            g2.drawLine(0, y, w, y);
        }

        g2.dispose();
        super.paintComponent(g);
    }

    private void drawFloatingParticles(Graphics2D g2, int w, int h) {
        float[][] particles = {
                {0.1f, 0.2f, 80, 8},
                {0.85f, 0.15f, 60, 10},
                {0.7f, 0.75f, 100, 6},
                {0.2f, 0.8f, 50, 12},
                {0.5f, 0.4f, 70, 5},
                {0.9f, 0.55f, 40, 9},
                {0.35f, 0.55f, 90, 7},
                {0.15f, 0.5f, 55, 11},
        };

        for (float[] p : particles) {
            float px = p[0] * w + (float) Math.sin(animPhase * p[3] * 0.3) * 20;
            float py = p[1] * h + (float) Math.cos(animPhase * p[3] * 0.2) * 15;
            float size = p[2];
            int alpha = (int) p[3];

            g2.setColor(ColorPalette.withAlpha(ColorPalette.PRIMARY, alpha));
            g2.fill(new Ellipse2D.Float(px - size / 2, py - size / 2, size, size));
        }
    }
}
