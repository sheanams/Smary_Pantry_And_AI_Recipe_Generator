package com.smartpantry.landing.panels;

import com.smartpantry.landing.utils.ColorPalette;
import com.smartpantry.landing.utils.FontManager;
import com.smartpantry.landing.utils.UIHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Enterprise footer with multi-column links, social media, and legal text.
 */
public class FooterPanel extends JPanel {

    public FooterPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(50, 80, 30, 80));

        // ── Main footer columns ──────────────────────────────────────
        JPanel columnsPanel = new JPanel(new GridLayout(1, 4, 40, 0));
        columnsPanel.setOpaque(false);
        columnsPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));

        // Column 1: Brand
        JPanel brandCol = createColumn("SmartPantry",
                new String[]{"AI-powered kitchen management", "platform for modern households.", "",
                        "© 2026 SmartPantry Inc.", "All rights reserved."});

        // Column 2: Product
        JPanel productCol = createLinkColumn("Product",
                new String[]{"Features", "Modules", "AI Engine", "Mobile App", "API Docs"});

        // Column 3: Company
        JPanel companyCol = createLinkColumn("Company",
                new String[]{"About Us", "Careers", "Blog", "Press Kit", "Contact"});

        // Column 4: Support
        JPanel supportCol = createLinkColumn("Support",
                new String[]{"Help Center", "Community", "Status", "Privacy Policy", "Terms of Service"});

        columnsPanel.add(brandCol);
        columnsPanel.add(productCol);
        columnsPanel.add(companyCol);
        columnsPanel.add(supportCol);

        add(columnsPanel);
        add(UIHelper.verticalSpacer(30));

        // ── Divider ──────────────────────────────────────────────────
        JSeparator sep = new JSeparator();
        sep.setForeground(ColorPalette.withAlpha(ColorPalette.TEXT_ON_DARK, 30));
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        add(sep);
        add(UIHelper.verticalSpacer(20));

        // ── Bottom bar ───────────────────────────────────────────────
        JPanel bottomBar = new JPanel(new BorderLayout());
        bottomBar.setOpaque(false);
        bottomBar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        JLabel madeWith = new JLabel("Built with ❤ using Java Swing  •  Designed for Enterprise");
        madeWith.setFont(FontManager.small());
        madeWith.setForeground(ColorPalette.TEXT_MUTED);

        JPanel socialRow = new JPanel(new FlowLayout(FlowLayout.RIGHT, 16, 0));
        socialRow.setOpaque(false);
        String[] socials = {"GitHub", "Twitter", "LinkedIn", "Discord"};
        for (String s : socials) {
            JLabel social = new JLabel(s);
            social.setFont(FontManager.small());
            social.setForeground(ColorPalette.TEXT_MUTED);
            social.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            social.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) { social.setForeground(ColorPalette.PRIMARY_LIGHT); }
                @Override
                public void mouseExited(MouseEvent e) { social.setForeground(ColorPalette.TEXT_MUTED); }
            });
            socialRow.add(social);
        }

        bottomBar.add(madeWith, BorderLayout.WEST);
        bottomBar.add(socialRow, BorderLayout.EAST);

        add(bottomBar);
    }

    private JPanel createColumn(String heading, String[] items) {
        JPanel col = new JPanel();
        col.setLayout(new BoxLayout(col, BoxLayout.Y_AXIS));
        col.setOpaque(false);

        JLabel h = new JLabel(heading);
        h.setFont(FontManager.footerHeading());
        h.setForeground(ColorPalette.PRIMARY);
        h.setAlignmentX(Component.LEFT_ALIGNMENT);
        col.add(h);
        col.add(UIHelper.verticalSpacer(12));

        for (String item : items) {
            JLabel l = new JLabel(item.isEmpty() ? " " : item);
            l.setFont(FontManager.footerText());
            l.setForeground(ColorPalette.TEXT_MUTED);
            l.setAlignmentX(Component.LEFT_ALIGNMENT);
            col.add(l);
            col.add(UIHelper.verticalSpacer(4));
        }
        return col;
    }

    private JPanel createLinkColumn(String heading, String[] links) {
        JPanel col = new JPanel();
        col.setLayout(new BoxLayout(col, BoxLayout.Y_AXIS));
        col.setOpaque(false);

        JLabel h = new JLabel(heading);
        h.setFont(FontManager.footerHeading());
        h.setForeground(ColorPalette.TEXT_WHITE);
        h.setAlignmentX(Component.LEFT_ALIGNMENT);
        col.add(h);
        col.add(UIHelper.verticalSpacer(12));

        for (String link : links) {
            JLabel l = new JLabel(link);
            l.setFont(FontManager.footerText());
            l.setForeground(ColorPalette.TEXT_MUTED);
            l.setAlignmentX(Component.LEFT_ALIGNMENT);
            l.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            l.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) { l.setForeground(ColorPalette.PRIMARY_LIGHT); }
                @Override
                public void mouseExited(MouseEvent e) { l.setForeground(ColorPalette.TEXT_MUTED); }
            });

            col.add(l);
            col.add(UIHelper.verticalSpacer(6));
        }
        return col;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(ColorPalette.BG_DARK);
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.dispose();
        super.paintComponent(g);
    }
}
