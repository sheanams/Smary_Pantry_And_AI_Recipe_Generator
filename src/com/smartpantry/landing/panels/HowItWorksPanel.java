package com.smartpantry.landing.panels;

import com.smartpantry.landing.utils.ColorPalette;
import com.smartpantry.landing.utils.FontManager;
import com.smartpantry.landing.utils.UIHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

/**
 * "How It Works" section with a 4-step horizontal flow showing the user journey.
 */
public class HowItWorksPanel extends JPanel {

    private static final String[][] STEPS = {
            {"1", "\uD83D\uDCF1", "Scan & Add", "Scan barcodes or manually add items to your digital pantry with expiry dates."},
            {"2", "\uD83E\uDDE0", "AI Analyzes", "Our AI engine processes your inventory and dietary preferences in real time."},
            {"3", "\uD83C\uDF72", "Get Recipes", "Receive personalized recipe suggestions using only your available ingredients."},
            {"4", "\uD83D\uDED2", "Shop Smart", "Auto-generated shopping lists keep your pantry optimized and waste-free."}
    };

    public HowItWorksPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(80, 60, 80, 60));

        // ── Section header ───────────────────────────────────────────
        JLabel tag = new JLabel("USER JOURNEY");
        tag.setFont(FontManager.badge());
        tag.setForeground(ColorPalette.PRIMARY_LIGHT);
        tag.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel title = new JLabel("How SmartPantry Works");
        title.setFont(FontManager.sectionTitle());
        title.setForeground(ColorPalette.TEXT_WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = UIHelper.createWrappedLabel(
                "From scanning your first ingredient to generating AI recipes — it's simple, intuitive, and powerful.",
                FontManager.sectionSub(),
                ColorPalette.TEXT_ON_DARK,
                480
        );
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(tag);
        add(UIHelper.verticalSpacer(12));
        add(title);
        add(UIHelper.verticalSpacer(12));
        add(subtitle);
        add(UIHelper.verticalSpacer(50));

        // ── Steps flow ───────────────────────────────────────────────
        JPanel stepsPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                UIHelper.enableAntialiasing(g2);
                drawSteps(g2, getWidth(), getHeight());
                g2.dispose();
            }
        };
        stepsPanel.setOpaque(false);
        stepsPanel.setPreferredSize(new Dimension(0, 220));
        stepsPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 220));

        add(stepsPanel);
    }

    private void drawSteps(Graphics2D g2, int w, int h) {
        int stepCount = STEPS.length;
        int cardW = 220;
        int totalW = stepCount * cardW + (stepCount - 1) * 40;
        int startX = (w - totalW) / 2;

        for (int i = 0; i < stepCount; i++) {
            int cx = startX + i * (cardW + 40);
            int cy = 10;

            // ── Connector line ───────────────────────────────────────
            if (i < stepCount - 1) {
                g2.setColor(ColorPalette.withAlpha(ColorPalette.PRIMARY, 60));
                g2.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL,
                        0, new float[]{8, 8}, 0));
                g2.drawLine(cx + cardW + 4, cy + 35, cx + cardW + 36, cy + 35);

                // Arrow head
                int ax = cx + cardW + 36;
                int ay = cy + 35;
                g2.setStroke(new BasicStroke(2));
                g2.drawLine(ax - 8, ay - 5, ax, ay);
                g2.drawLine(ax - 8, ay + 5, ax, ay);
            }

            // ── Card background ──────────────────────────────────────
            g2.setColor(ColorPalette.withAlpha(ColorPalette.BG_WHITE, 12));
            g2.fill(new RoundRectangle2D.Float(cx, cy, cardW, h - 30, 16, 16));

            g2.setColor(ColorPalette.withAlpha(ColorPalette.PRIMARY, 30));
            g2.setStroke(new BasicStroke(1));
            g2.draw(new RoundRectangle2D.Float(cx, cy, cardW, h - 30, 16, 16));

            // ── Step number circle ───────────────────────────────────
            int circleSize = 44;
            int circleX = cx + (cardW - circleSize) / 2;
            int circleY = cy + 18;
            g2.setColor(ColorPalette.PRIMARY);
            g2.fillOval(circleX, circleY, circleSize, circleSize);

            g2.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 22));
            FontMetrics efm = g2.getFontMetrics();
            String emoji = STEPS[i][1];
            g2.setColor(Color.WHITE);
            g2.drawString(emoji,
                    circleX + (circleSize - efm.stringWidth(emoji)) / 2,
                    circleY + (circleSize - efm.getHeight()) / 2 + efm.getAscent());

            // ── Step title ───────────────────────────────────────────
            g2.setFont(FontManager.cardTitle());
            g2.setColor(ColorPalette.TEXT_WHITE);
            FontMetrics tfm = g2.getFontMetrics();
            g2.drawString(STEPS[i][2],
                    cx + (cardW - tfm.stringWidth(STEPS[i][2])) / 2,
                    circleY + circleSize + 28);

            // ── Step description ─────────────────────────────────────
            g2.setFont(FontManager.cardCaption());
            g2.setColor(ColorPalette.TEXT_MUTED);
            drawWrapped(g2, STEPS[i][3], cx + 20, circleY + circleSize + 48, cardW - 40);
        }
    }

    private void drawWrapped(Graphics2D g2, String text, int x, int y, int maxW) {
        FontMetrics fm = g2.getFontMetrics();
        String[] words = text.split(" ");
        StringBuilder line = new StringBuilder();
        int ly = y;

        for (String word : words) {
            String test = line.length() == 0 ? word : line + " " + word;
            if (fm.stringWidth(test) > maxW) {
                String l = line.toString();
                g2.drawString(l, x + (maxW - fm.stringWidth(l)) / 2, ly);
                line = new StringBuilder(word);
                ly += fm.getHeight() + 2;
            } else {
                line = new StringBuilder(test);
            }
        }
        if (line.length() > 0) {
            String l = line.toString();
            g2.drawString(l, x + (maxW - fm.stringWidth(l)) / 2, ly);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        UIHelper.enableAntialiasing(g2);
        int w = getWidth();
        int h = getHeight();

        GradientPaint gp = new GradientPaint(
                0, 0, ColorPalette.BG_DARK_ALT,
                w, h, new Color(20, 35, 45)
        );
        g2.setPaint(gp);
        g2.fillRect(0, 0, w, h);

        g2.dispose();
        super.paintComponent(g);
    }
}
