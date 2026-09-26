package com.smartpantry.landing.panels;

import com.smartpantry.landing.components.ModernCard;
import com.smartpantry.landing.utils.ColorPalette;
import com.smartpantry.landing.utils.FontManager;
import com.smartpantry.landing.utils.UIHelper;

import javax.swing.*;
import java.awt.*;

/**
 * Features section displaying the core objectives as elegant cards in a grid.
 */
public class FeaturesPanel extends JPanel {

    public FeaturesPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(80, 60, 80, 60));

        // ── Section header ───────────────────────────────────────────
        JLabel sectionTag = new JLabel("CORE CAPABILITIES");
        sectionTag.setFont(FontManager.badge());
        sectionTag.setForeground(ColorPalette.PRIMARY);
        sectionTag.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel title = new JLabel("Powerful Features for Smart Kitchens");
        title.setFont(FontManager.sectionTitle());
        title.setForeground(ColorPalette.TEXT_PRIMARY);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = UIHelper.createWrappedLabel(
                "Everything you need to manage your kitchen efficiently, reduce waste, " +
                        "and discover delicious recipes powered by artificial intelligence.",
                FontManager.sectionSub(),
                ColorPalette.TEXT_SECONDARY,
                550
        );
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(sectionTag);
        add(UIHelper.verticalSpacer(12));
        add(title);
        add(UIHelper.verticalSpacer(12));
        add(subtitle);
        add(UIHelper.verticalSpacer(50));

        // ── Feature cards grid ───────────────────────────────────────
        JPanel cardsRow1 = createCardRow(
                new ModernCard("\uD83D\uDCCA", "Track Inventory",
                        "Monitor food stock levels automatically with barcode scanning and smart categorization in real-time.",
                        ColorPalette.PRIMARY),
                new ModernCard("⏰", "Alert Expiration",
                        "Receive timely notifications before your food spoils to minimize waste and maximize freshness.",
                        ColorPalette.ACCENT),
                new ModernCard("\uD83E\uDDD1\u200D\uD83C\uDF73", "Generate Recipes",
                        "Create dynamic, personalized meals from available ingredients using our AI-powered recipe engine.",
                        ColorPalette.INFO)
        );

        JPanel cardsRow2 = createCardRow(
                new ModernCard("♻\uFE0F", "Reduce Waste",
                        "Minimize household food disposal rates by up to 40% with intelligent meal planning suggestions.",
                        ColorPalette.SUCCESS),
                new ModernCard("\uD83D\uDED2", "Smart Shopping",
                        "Generate automated grocery lists based on your pantry status, recipes, and household preferences.",
                        new Color(168, 85, 247)),
                new ModernCard("\uD83D\uDD12", "Secure Profiles",
                        "Register securely, manage dietary preferences, allergies, and household member profiles with ease.",
                        ColorPalette.DANGER)
        );

        add(cardsRow1);
        add(UIHelper.verticalSpacer(20));
        add(cardsRow2);
    }

    private JPanel createCardRow(ModernCard... cards) {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.CENTER, 24, 0));
        row.setOpaque(false);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 280));
        for (ModernCard card : cards) {
            row.add(card);
        }
        return row;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(ColorPalette.BG_LIGHT);
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.dispose();
        super.paintComponent(g);
    }
}
