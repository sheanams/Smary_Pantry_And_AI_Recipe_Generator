package com.smartpantry.landing.dialogs;

import com.smartpantry.landing.utils.ColorPalette;
import com.smartpantry.landing.utils.FontManager;
import com.smartpantry.landing.utils.UIHelper;
import java.awt.*;
import javax.swing.*;

/**
 * Enterprise sales and consultation dialog.
 */
public class ContactDialog extends JDialog {

    public ContactDialog(Frame owner) {
        super(owner, "SmartPantry", true);
        setSize(520, 580);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(ColorPalette.BG_DARK);
        header.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel sub = new JLabel("Custom deployments, restaurant inventory APIs, and multi-tenant licenses.");
        sub.setFont(FontManager.small());
        sub.setForeground(ColorPalette.TEXT_MUTED);

        JPanel titleBox = new JPanel();
        titleBox.setLayout(new BoxLayout(titleBox, BoxLayout.Y_AXIS));
        titleBox.setOpaque(false);
        titleBox.add(Box.createRigidArea(new Dimension(0, 4)));
        titleBox.add(sub);

        header.add(titleBox, BorderLayout.WEST);

        JPanel body = new JPanel();
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setBackground(ColorPalette.BG_LIGHT);
        body.setBorder(BorderFactory.createEmptyBorder(25, 40, 25, 40));

        JTextField nameF = new JTextField();
        JTextField companyF = new JTextField();
        JTextField emailF = new JTextField();
        JComboBox<String> scaleC = new JComboBox<>(new String[]{"1 - 5 Kitchen Locations", "6 - 25 Locations", "25+ Enterprise Chains", "Smart Appliance OEM Partner"});
        JTextArea msgArea = new JTextArea(4, 20);
        msgArea.setLineWrap(true);
        msgArea.setWrapStyleWord(true);

        body.add(new JLabel("Full Name:"));
        body.add(Box.createRigidArea(new Dimension(0, 4)));
        body.add(nameF);
        body.add(Box.createRigidArea(new Dimension(0, 10)));
        body.add(new JLabel("Company / Organization:"));
        body.add(Box.createRigidArea(new Dimension(0, 4)));
        body.add(companyF);
        body.add(Box.createRigidArea(new Dimension(0, 10)));
        body.add(new JLabel("Work Email:"));
        body.add(Box.createRigidArea(new Dimension(0, 4)));
        body.add(emailF);
        body.add(Box.createRigidArea(new Dimension(0, 10)));
        body.add(new JLabel("Deployment Scale:"));
        body.add(Box.createRigidArea(new Dimension(0, 4)));
        body.add(scaleC);
        body.add(Box.createRigidArea(new Dimension(0, 10)));
        body.add(new JLabel("Project Requirements & Goals:"));
        body.add(Box.createRigidArea(new Dimension(0, 4)));
        body.add(new JScrollPane(msgArea));
        body.add(Box.createRigidArea(new Dimension(0, 20)));

        JButton submit = UIHelper.createRoundedButton("Submit Inquiry", ColorPalette.PRIMARY, ColorPalette.TEXT_WHITE, FontManager.buttonPrimary(), 18);
        submit.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        submit.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Thank you! An enterprise solution architect will contact you within 24 business hours.", "Inquiry Received", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        });

        body.add(submit);

        add(header, BorderLayout.NORTH);
        add(body, BorderLayout.CENTER);
    }
}
