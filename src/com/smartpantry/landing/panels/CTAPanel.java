package com.smartpantry.landing.panels;

import com.smartpantry.landing.utils.ColorPalette;
import com.smartpantry.landing.utils.FontManager;
import com.smartpantry.landing.utils.UIHelper;
import java.awt.*;
import javax.swing.*;

/**
 * Call-to-action section with a bold gradient background and signup prompt.
 */
public class CTAPanel extends JPanel {

    public CTAPanel(Runnable onStartTrial, Runnable onContactSales) {
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(0, 340));
        setOpaque(false);

        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setOpaque(false);

        // ── Headline ─────────────────────────────────────────────────
        JLabel headline = new JLabel("Ready to Transform Your Kitchen?");
        headline.setFont(new Font(headline.getFont().getFamily(), Font.BOLD, 34));
        headline.setForeground(ColorPalette.TEXT_WHITE);
        headline.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel sub = UIHelper.createWrappedLabel(
                "Join over to save food, money, and time with SmartPantry. " +
                        "Start today",
                FontManager.heroSubtitle(),
                ColorPalette.withAlpha(ColorPalette.TEXT_WHITE, 210),
                500
        );
        sub.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ── Buttons ──────────────────────────────────────────────────
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 16, 0));
        btnPanel.setOpaque(false);

        JButton ctaBtn = UIHelper.createRoundedButton(
                "Start Free Trial",
                ColorPalette.BG_WHITE, ColorPalette.PRIMARY_DARK,
                FontManager.buttonPrimary(), 30
        );
        ctaBtn.setPreferredSize(new Dimension(200, 52));
        if (onStartTrial != null) {
            ctaBtn.addActionListener(e -> onStartTrial.run());
        }

        JButton learnBtn = UIHelper.createOutlinedButton(
                "Contact Sales",
                ColorPalette.TEXT_WHITE, ColorPalette.TEXT_WHITE,
                FontManager.buttonSecondary(), 30
        );
        learnBtn.setPreferredSize(new Dimension(180, 52));
        if (onContactSales != null) {
            learnBtn.addActionListener(e -> onContactSales.run());
        }

        btnPanel.add(ctaBtn);
        btnPanel.add(learnBtn);

        center.add(headline);
        center.add(UIHelper.verticalSpacer(16));
        center.add(sub);
        center.add(UIHelper.verticalSpacer(32));
        center.add(btnPanel);

        add(center);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        UIHelper.enableAntialiasing(g2);
        int w = getWidth();
        int h = getHeight();

        // ── Green gradient ───────────────────────────────────────────
        GradientPaint gp = new GradientPaint(
                0, 0, ColorPalette.CTA_GRAD_START,
                w, h, ColorPalette.CTA_GRAD_END
        );
        g2.setPaint(gp);
        g2.fillRect(0, 0, w, h);

        // ── Decorative circles ───────────────────────────────────────
        g2.setColor(ColorPalette.withAlpha(ColorPalette.BG_WHITE, 15));
        g2.fillOval(-80, -80, 300, 300);
        g2.fillOval(w - 200, h - 200, 350, 350);

        g2.setColor(ColorPalette.withAlpha(ColorPalette.BG_WHITE, 8));
        g2.fillOval(w / 2 - 100, -60, 200, 200);

        g2.dispose();
        super.paintComponent(g);
    }
}
