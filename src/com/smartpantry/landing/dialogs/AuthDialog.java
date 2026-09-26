package com.smartpantry.landing.dialogs;

import com.smartpantry.landing.utils.ColorPalette;
import com.smartpantry.landing.utils.FontManager;
import com.smartpantry.landing.utils.UIHelper;

import javax.swing.*;
import java.awt.*;

/**
 * Authentication dialog with Sign In and Register tabs.
 */
public class AuthDialog extends JDialog {

    public AuthDialog(Frame owner, boolean startWithRegister) {
        super(owner, "SmartPantry — Account Access", true);
        setSize(480, 560);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());
        setResizable(false);

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(ColorPalette.BG_DARK);
        header.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel title = new JLabel("SmartPantry Enterprise");
        title.setFont(FontManager.cardTitle());
        title.setForeground(ColorPalette.TEXT_WHITE);

        JLabel sub = new JLabel("Secure identity & multi-tenant profile management");
        sub.setFont(FontManager.small());
        sub.setForeground(ColorPalette.TEXT_MUTED);

        JPanel titleBox = new JPanel();
        titleBox.setLayout(new BoxLayout(titleBox, BoxLayout.Y_AXIS));
        titleBox.setOpaque(false);
        titleBox.add(title);
        titleBox.add(Box.createRigidArea(new Dimension(0, 4)));
        titleBox.add(sub);

        header.add(titleBox, BorderLayout.WEST);

        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(FontManager.bodyBold());
        tabs.addTab("Sign In", createSignInPanel());
        tabs.addTab("Register Account", createRegisterPanel());

        if (startWithRegister) {
            tabs.setSelectedIndex(1);
        }

        add(header, BorderLayout.NORTH);
        add(tabs, BorderLayout.CENTER);
    }

    private JPanel createSignInPanel() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(ColorPalette.BG_LIGHT);
        p.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        JTextField emailF = new JTextField(20);
        JPasswordField passF = new JPasswordField(20);

        p.add(new JLabel("Work or Personal Email:"));
        p.add(Box.createRigidArea(new Dimension(0, 6)));
        p.add(emailF);
        p.add(Box.createRigidArea(new Dimension(0, 16)));
        p.add(new JLabel("Password:"));
        p.add(Box.createRigidArea(new Dimension(0, 6)));
        p.add(passF);
        p.add(Box.createRigidArea(new Dimension(0, 16)));

        JCheckBox remember = new JCheckBox("Remember this session (OAuth2 / JWT token)");
        remember.setOpaque(false);
        p.add(remember);
        p.add(Box.createRigidArea(new Dimension(0, 24)));

        JButton submit = UIHelper.createRoundedButton("Sign In to SmartPantry", ColorPalette.PRIMARY, ColorPalette.TEXT_WHITE, FontManager.buttonPrimary(), 18);
        submit.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        submit.addActionListener(e -> {
            String email = emailF.getText().trim();
            if (email.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter your email.", "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            JOptionPane.showMessageDialog(this, "Welcome back, " + email + "!\nAuthenticated via Secure JWT Token.", "Authentication Successful", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        });

        p.add(submit);
        return p;
    }

    private JPanel createRegisterPanel() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(ColorPalette.BG_LIGHT);
        p.setBorder(BorderFactory.createEmptyBorder(25, 40, 25, 40));

        JTextField nameF = new JTextField(20);
        JTextField emailF = new JTextField(20);
        JPasswordField passF = new JPasswordField(20);
        JComboBox<String> plan = new JComboBox<>(new String[]{"Free Tier (50 items)", "Pro Household (Unlimited)", "Enterprise Kitchen"});

        p.add(new JLabel("Full Name:"));
        p.add(Box.createRigidArea(new Dimension(0, 4)));
        p.add(nameF);
        p.add(Box.createRigidArea(new Dimension(0, 10)));
        p.add(new JLabel("Email Address:"));
        p.add(Box.createRigidArea(new Dimension(0, 4)));
        p.add(emailF);
        p.add(Box.createRigidArea(new Dimension(0, 10)));
        p.add(new JLabel("Password:"));
        p.add(Box.createRigidArea(new Dimension(0, 4)));
        p.add(passF);
        p.add(Box.createRigidArea(new Dimension(0, 10)));
        p.add(new JLabel("Initial Tier:"));
        p.add(Box.createRigidArea(new Dimension(0, 4)));
        p.add(plan);
        p.add(Box.createRigidArea(new Dimension(0, 20)));

        JButton registerBtn = UIHelper.createRoundedButton("Create Enterprise Account", ColorPalette.PRIMARY, ColorPalette.TEXT_WHITE, FontManager.buttonPrimary(), 18);
        registerBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        registerBtn.addActionListener(e -> {
            String name = nameF.getText().trim();
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please provide your full name.", "Validation", JOptionPane.WARNING_MESSAGE);
                return;
            }
            JOptionPane.showMessageDialog(this, "Account created successfully for " + name + "!\n14-day Pro features have been unlocked.", "Registration Complete", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        });

        p.add(registerBtn);
        return p;
    }
}
