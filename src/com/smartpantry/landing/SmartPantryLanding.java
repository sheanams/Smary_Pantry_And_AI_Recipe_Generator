/*hello*/
package com.smartpantry.landing;

import com.smartpantry.landing.dialogs.AuthDialog;
import com.smartpantry.landing.dialogs.ContactDialog;
import com.smartpantry.landing.dialogs.DemoDialog;
import com.smartpantry.landing.panels.*;
import com.smartpantry.landing.utils.ColorPalette;
import com.smartpantry.landing.utils.UIHelper;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;

/**
 * ╔══════════════════════════════════════════════════════════════════════╗
 * ║                      THE SMARTPANTRY                               ║
 * ║          AI-Powered Kitchen Intelligence Platform                   ║
 * ║                                                                    ║
 * ║  Enterprise Landing Page — Built Entirely with Java Swing          ║
 * ║  Custom-painted modern UI with animations, smooth scrolling & demo ║
 * ╚══════════════════════════════════════════════════════════════════════╝
 *
 * Main application entry point.
 * Assembles all landing page sections into a scrollable, interactive window.
 */
public class SmartPantryLanding {

    private static final String APP_TITLE = "SmartPantry — AI-Powered Kitchen Intelligence";
    private static final int WINDOW_WIDTH = 1340;
    private static final int WINDOW_HEIGHT = 860;

    public static void main(String[] args) {
        // ── Set system properties for high-DPI rendering ──────────────
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");

        SwingUtilities.invokeLater(SmartPantryLanding::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        // ── Look and feel ────────────────────────────────────────────
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        // ── Main Frame ───────────────────────────────────────────────
        JFrame frame = new JFrame(APP_TITLE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(1100, 720));

        // ── Main scrollable content container ────────────────────────
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(ColorPalette.BG_DARK);

        // ── Instantiating sections ───────────────────────────────────
        FeaturesPanel featuresPanel = new FeaturesPanel();
        StatsPanel statsPanel = new StatsPanel();
        ModulesPanel modulesPanel = new ModulesPanel();
        HowItWorksPanel howItWorksPanel = new HowItWorksPanel();

        // ── Actions & Modals ─────────────────────────────────────────
        Runnable openDemo = () -> new DemoDialog(frame).setVisible(true);
        Runnable openSignIn = () -> new AuthDialog(frame, false).setVisible(true);
        Runnable openRegister = () -> new AuthDialog(frame, true).setVisible(true);
        Runnable openContact = () -> new ContactDialog(frame).setVisible(true);

        HeroPanel heroPanel = new HeroPanel(openRegister, openDemo);
        CTAPanel ctaPanel = new CTAPanel(openRegister, openContact);
        FooterPanel footerPanel = new FooterPanel();

        // ── Scroll pane container ────────────────────────────────────
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(22);
        scrollPane.getVerticalScrollBar().setBackground(ColorPalette.BG_DARK);

        // Custom styled scrollbar
        scrollPane.getVerticalScrollBar().setUI(new javax.swing.plaf.basic.BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = ColorPalette.withAlpha(ColorPalette.PRIMARY, 90);
                this.trackColor = ColorPalette.BG_DARK;
            }

            @Override
            protected JButton createDecreaseButton(int orientation) { return createZeroButton(); }

            @Override
            protected JButton createIncreaseButton(int orientation) { return createZeroButton(); }

            private JButton createZeroButton() {
                JButton btn = new JButton();
                btn.setPreferredSize(new Dimension(0, 0));
                return btn;
            }

            @Override
            protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
                Graphics2D g2 = (Graphics2D) g.create();
                UIHelper.enableAntialiasing(g2);
                g2.setColor(thumbColor);
                g2.fillRoundRect(thumbBounds.x + 2, thumbBounds.y, thumbBounds.width - 4, thumbBounds.height, 10, 10);
                g2.dispose();
            }

            @Override
            protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(trackColor);
                g2.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
                g2.dispose();
            }
        });

        // ── Nav Actions with Smooth Scrolling ────────────────────────
        Map<String, Runnable> navActions = new HashMap<>();
        navActions.put("Features", () -> smoothScrollTo(scrollPane, featuresPanel));
        navActions.put("Modules", () -> smoothScrollTo(scrollPane, modulesPanel));
        navActions.put("How It Works", () -> smoothScrollTo(scrollPane, howItWorksPanel));

        NavBarPanel navBarPanel = new NavBarPanel(navActions, openSignIn, openRegister);

        // ── Assemble all sections into content panel ─────────────────
        contentPanel.add(navBarPanel);
        contentPanel.add(heroPanel);
        contentPanel.add(featuresPanel);
        contentPanel.add(statsPanel);
        contentPanel.add(modulesPanel);
        contentPanel.add(howItWorksPanel);
        contentPanel.add(ctaPanel);
        contentPanel.add(footerPanel);

        frame.setContentPane(scrollPane);
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * Smoothly animates the vertical scrollbar of the scroll pane to the target component.
     */
    public static void smoothScrollTo(JScrollPane scrollPane, Component targetComponent) {
        if (targetComponent == null || scrollPane == null) return;

        Point p = SwingUtilities.convertPoint(targetComponent, 0, 0, scrollPane.getViewport().getView());
        int targetY = Math.max(0, p.y - 70); // 70px offset for fixed nav visibility
        JScrollBar vbar = scrollPane.getVerticalScrollBar();
        int startY = vbar.getValue();
        int diff = targetY - startY;
        if (diff == 0) return;

        int steps = 24;
        Timer timer = new Timer(15, null);
        final int[] step = {0};
        timer.addActionListener(e -> {
            step[0]++;
            float progress = (float) step[0] / steps;
            // Cosine ease-in-out calculation
            float ease = (float) (0.5 * (1.0 - Math.cos(progress * Math.PI)));
            vbar.setValue((int) (startY + diff * ease));
            if (step[0] >= steps) {
                timer.stop();
                vbar.setValue(targetY);
            }
        });
        timer.start();
    }
}
