package com.smartpantry.landing.panels;

import com.smartpantry.landing.utils.ColorPalette;
import com.smartpantry.landing.utils.FontManager;
import com.smartpantry.landing.utils.UIHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;

/**
 * Top navigation bar with logo, nav links, and CTA buttons.
 * Supports callbacks for smooth scrolling and modal triggers.
 */
public class NavBarPanel extends JPanel {

    private static final int NAV_HEIGHT = 70;

    public NavBarPanel(Map<String, Runnable> navActions, Runnable onSignIn, Runnable onGetStarted) {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(0, NAV_HEIGHT));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, NAV_HEIGHT));
        setOpaque(false);

        // ── Content wrapper with padding ─────────────────────────────
        JPanel content = new JPanel(new BorderLayout());
        content.setOpaque(false);
        content.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 50));

        // ── Brand / Logo ─────────────────────────────────────────────
        JPanel brandPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        brandPanel.setOpaque(false);

        JLabel logoIcon = new JLabel("\uD83E\uDD6C"); // 🥬
        logoIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 28));

        JLabel brandName = new JLabel("SmartPantry");
        brandName.setFont(FontManager.navBrand());
        brandName.setForeground(ColorPalette.PRIMARY);

        JLabel tagBadge = new JLabel("  AI-Powered");
        tagBadge.setFont(FontManager.badge());
        tagBadge.setForeground(ColorPalette.ACCENT);

        brandPanel.add(logoIcon);
        brandPanel.add(brandName);
        brandPanel.add(tagBadge);

        // ── Center nav links ─────────────────────────────────────────
        JPanel navLinks = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 0));
        navLinks.setOpaque(false);

        String[] links = {"Features", "Modules", "How It Works"};
        for (String link : links) {
            Runnable action = (navActions != null) ? navActions.get(link) : null;
            JLabel navLabel = createNavLink(link, action);
            navLinks.add(navLabel);
        }

        // ── Right buttons ────────────────────────────────────────────
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        rightPanel.setOpaque(false);

        JButton loginBtn = UIHelper.createOutlinedButton(
                "Sign In", ColorPalette.PRIMARY, ColorPalette.TEXT_WHITE,
                FontManager.buttonSecondary(), 24
        );
        loginBtn.setPreferredSize(new Dimension(100, 38));
        if (onSignIn != null) {
            loginBtn.addActionListener(e -> onSignIn.run());
        }

        JButton signUpBtn = UIHelper.createRoundedButton(
                "Get Started", ColorPalette.PRIMARY, ColorPalette.TEXT_WHITE,
                FontManager.buttonPrimary(), 24
        );
        signUpBtn.setPreferredSize(new Dimension(130, 38));
        if (onGetStarted != null) {
            signUpBtn.addActionListener(e -> onGetStarted.run());
        }

        rightPanel.add(loginBtn);
        rightPanel.add(signUpBtn);

        content.add(brandPanel, BorderLayout.WEST);
        content.add(navLinks, BorderLayout.CENTER);
        content.add(rightPanel, BorderLayout.EAST);
        add(content, BorderLayout.CENTER);
    }

    private JLabel createNavLink(String text, Runnable onClick) {
        JLabel label = new JLabel(text);
        label.setFont(FontManager.navLink());
        label.setForeground(ColorPalette.TEXT_ON_DARK);
        label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                label.setForeground(ColorPalette.PRIMARY_LIGHT);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                label.setForeground(ColorPalette.TEXT_ON_DARK);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (onClick != null) {
                    onClick.run();
                }
            }
        });

        return label;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        UIHelper.enableAntialiasing(g2);

        // Semi-transparent dark background with bottom border
        g2.setColor(ColorPalette.withAlpha(ColorPalette.BG_DARK, 240));
        g2.fillRect(0, 0, getWidth(), getHeight());

        // Bottom accent line
        g2.setColor(ColorPalette.withAlpha(ColorPalette.PRIMARY, 40));
        g2.fillRect(0, getHeight() - 1, getWidth(), 1);

        g2.dispose();
        super.paintComponent(g);
    }
}
