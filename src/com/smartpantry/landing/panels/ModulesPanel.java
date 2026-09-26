package com.smartpantry.landing.panels;

import com.smartpantry.landing.components.ModuleCard;
import com.smartpantry.landing.utils.ColorPalette;
import com.smartpantry.landing.utils.FontManager;
import com.smartpantry.landing.utils.UIHelper;

import javax.swing.*;
import java.awt.*;

/**
 * Modules section showcasing the 5 system modules with step cards.
 */
public class ModulesPanel extends JPanel {

    public ModulesPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(80, 60, 80, 60));

        // ── Section header ───────────────────────────────────────────
        JLabel tag = new JLabel("SYSTEM ARCHITECTURE");
        tag.setFont(FontManager.badge());
        tag.setForeground(ColorPalette.PRIMARY);
        tag.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel title = new JLabel("Enterprise Modules");
        title.setFont(FontManager.sectionTitle());
        title.setForeground(ColorPalette.TEXT_PRIMARY);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = UIHelper.createWrappedLabel(
                "A modular, scalable architecture designed for enterprise-grade " +
                        "kitchen management and AI-driven recipe intelligence.",
                FontManager.sectionSub(),
                ColorPalette.TEXT_SECONDARY,
                520
        );
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(tag);
        add(UIHelper.verticalSpacer(12));
        add(title);
        add(UIHelper.verticalSpacer(12));
        add(subtitle);
        add(UIHelper.verticalSpacer(50));

        // ── Module cards — Row 1 (3 cards) ───────────────────────────
        JPanel row1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        row1.setOpaque(false);
        row1.setMaximumSize(new Dimension(Integer.MAX_VALUE, 260));

        row1.add(new ModuleCard(1, "User Authentication",
                new String[]{
                        "Secure user registration & login",
                        "Manage dietary preferences",
                        "Allergy profile management",
                        "Multi-member households"
                },
                ColorPalette.PRIMARY));

        row1.add(new ModuleCard(2, "Smart Pantry Mgmt",
                new String[]{
                        "Log ingredients with expiry dates",
                        "Auto-update stock after cooking",
                        "Categorize by food groups",
                        "Barcode scanning support"
                },
                ColorPalette.INFO));

        row1.add(new ModuleCard(3, "AI Recipe Engine",
                new String[]{
                        "Analyze current pantry stock",
                        "Generate step-by-step recipes",
                        "Filter by dietary restrictions",
                        "LLM-powered suggestions"
                },
                ColorPalette.ACCENT));

        add(row1);
        add(UIHelper.verticalSpacer(24));

        // ── Module cards — Row 2 (2 cards, centered) ─────────────────
        JPanel row2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        row2.setOpaque(false);
        row2.setMaximumSize(new Dimension(Integer.MAX_VALUE, 260));

        row2.add(new ModuleCard(4, "Notification System",
                new String[]{
                        "Expiring item alerts",
                        "Low-stock notifications",
                        "Weekly meal plan reminders",
                        "Push & email delivery"
                },
                ColorPalette.DANGER));

        row2.add(new ModuleCard(5, "Smart Shopping List",
                new String[]{
                        "Auto-generate from missing items",
                        "Manual additions supported",
                        "Sync across household",
                        "Store aisle optimization"
                },
                new Color(168, 85, 247)));

        add(row2);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(ColorPalette.BG_WHITE);
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.dispose();
        super.paintComponent(g);
    }
}
