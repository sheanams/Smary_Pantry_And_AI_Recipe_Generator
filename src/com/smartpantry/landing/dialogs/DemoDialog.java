package com.smartpantry.landing.dialogs;

import com.smartpantry.landing.utils.ColorPalette;
import com.smartpantry.landing.utils.FontManager;
import com.smartpantry.landing.utils.UIHelper;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.Vector;

/**
 * Interactive Live Demo Dialog demonstrating all 5 SmartPantry modules:
 * 1. User Profile & Dietary Restrictions
 * 2. Smart Pantry Management (Tracking & Expiry)
 * 3. AI Recipe Engine (Real-time generation)
 * 4. Alert & Notification System
 * 5. Smart Shopping List
 */
public class DemoDialog extends JDialog {

    private DefaultTableModel pantryModel;
    private DefaultTableModel shoppingModel;
    private JTextArea recipeOutputArea;
    private JComboBox<String> dietFilterCombo;
    private JLabel notificationBanner;

    public DemoDialog(Frame owner) {
        super(owner, "SmartPantry — Interactive System Demo", true);
        setSize(980, 680);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());

        JPanel header = createHeader();
        JTabbedPane tabs = createTabs();

        add(header, BorderLayout.NORTH);
        add(tabs, BorderLayout.CENTER);
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                UIHelper.enableAntialiasing(g2);
                GradientPaint gp = new GradientPaint(0, 0, ColorPalette.BG_DARK, getWidth(), 0, ColorPalette.BG_DARK_ALT);
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.dispose();
            }
        };
        header.setBorder(BorderFactory.createEmptyBorder(18, 25, 18, 25));

        JLabel title = new JLabel("SmartPantry Interactive Experience");
        title.setFont(FontManager.cardTitle());
        title.setForeground(ColorPalette.TEXT_WHITE);

        JLabel sub = new JLabel("Explore real-time inventory tracking, AI recipe synthesis, and automated grocery planning");
        sub.setFont(FontManager.small());
        sub.setForeground(ColorPalette.TEXT_MUTED);

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setOpaque(false);
        titlePanel.add(title);
        titlePanel.add(Box.createRigidArea(new Dimension(0, 4)));
        titlePanel.add(sub);

        header.add(titlePanel, BorderLayout.WEST);

        JButton closeBtn = UIHelper.createRoundedButton("Close Demo", ColorPalette.withAlpha(ColorPalette.BG_WHITE, 40), ColorPalette.TEXT_WHITE, FontManager.small(), 16);
        closeBtn.setPreferredSize(new Dimension(110, 32));
        closeBtn.addActionListener(e -> dispose());
        header.add(closeBtn, BorderLayout.EAST);

        return header;
    }

    private JTabbedPane createTabs() {
        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(FontManager.bodyBold());
        tabs.setBackground(ColorPalette.BG_LIGHT);

        tabs.addTab("📦 1. Pantry & Expiry Tracker", createPantryPanel());
        tabs.addTab("✨ 2. AI Recipe Generator", createRecipePanel());
        tabs.addTab("🛒 3. Smart Shopping List", createShoppingPanel());
        tabs.addTab("👤 4. Profile & Dietary Prefs", createProfilePanel());

        return tabs;
    }

    // ─────────────────────────────────────────────────────────────────
    // Tab 1: Smart Pantry & Expiration Tracker
    // ─────────────────────────────────────────────────────────────────
    private JPanel createPantryPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(ColorPalette.BG_LIGHT);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Alert Banner
        notificationBanner = new JLabel(" ⚠️ Notification: Whole Milk expires in 1 day! Spinach expires in 2 days. Consider cooking soon!");
        notificationBanner.setFont(FontManager.cardCaption());
        notificationBanner.setForeground(ColorPalette.DANGER.darker());
        notificationBanner.setOpaque(true);
        notificationBanner.setBackground(new Color(254, 242, 242));
        notificationBanner.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(252, 165, 165)),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));

        // Table
        String[] cols = {"Item Name", "Category", "Quantity", "Expiry Date", "Status"};
        pantryModel = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };
        pantryModel.addRow(new Object[]{"Whole Milk", "Dairy", "1 Gallon", "Tomorrow", "CRITICAL - Expiring Soon"});
        pantryModel.addRow(new Object[]{"Baby Spinach", "Produce", "300g", "In 2 days", "WARNING - Use Soon"});
        pantryModel.addRow(new Object[]{"Chicken Breast", "Meat", "500g", "In 4 days", "Good"});
        pantryModel.addRow(new Object[]{"Cheddar Cheese", "Dairy", "250g", "In 8 days", "Good"});
        pantryModel.addRow(new Object[]{"Garlic & Herbs", "Spices", "1 Bottle", "In 25 days", "Fresh"});
        pantryModel.addRow(new Object[]{"Brown Rice", "Grains", "1 kg", "In 60 days", "Fresh"});

        JTable table = new JTable(pantryModel);
        table.setRowHeight(32);
        table.setFont(FontManager.body());
        table.getTableHeader().setFont(FontManager.bodyBold());
        table.getTableHeader().setBackground(ColorPalette.BG_WHITE);

        // Custom status cell renderer
        table.getColumnModel().getColumn(4).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object val, boolean isSel, boolean hasFoc, int row, int col) {
                Component c = super.getTableCellRendererComponent(t, val, isSel, hasFoc, row, col);
                String status = String.valueOf(val);
                if (status.contains("CRITICAL")) {
                    setForeground(ColorPalette.DANGER);
                    setFont(getFont().deriveFont(Font.BOLD));
                } else if (status.contains("WARNING")) {
                    setForeground(ColorPalette.ACCENT_DARK);
                    setFont(getFont().deriveFont(Font.BOLD));
                } else {
                    setForeground(ColorPalette.SUCCESS.darker());
                    setFont(getFont().deriveFont(Font.PLAIN));
                }
                return c;
            }
        });

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createLineBorder(ColorPalette.CARD_BORDER));

        // Action Toolbar
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        actionPanel.setOpaque(false);

        JTextField nameField = new JTextField(10);
        nameField.setFont(FontManager.body());
        JComboBox<String> catCombo = new JComboBox<>(new String[]{"Dairy", "Produce", "Meat", "Grains", "Spices"});
        JTextField qtyField = new JTextField("1 unit", 6);
        JTextField expField = new JTextField("In 7 days", 8);

        JButton addBtn = UIHelper.createRoundedButton("Add Ingredient", ColorPalette.PRIMARY, ColorPalette.TEXT_WHITE, FontManager.small(), 12);
        addBtn.setPreferredSize(new Dimension(130, 34));
        addBtn.addActionListener(e -> {
            String name = nameField.getText().trim();
            if (!name.isEmpty()) {
                pantryModel.addRow(new Object[]{name, catCombo.getSelectedItem(), qtyField.getText(), expField.getText(), "Fresh"});
                nameField.setText("");
                JOptionPane.showMessageDialog(this, "Logged '" + name + "' into your real-time pantry inventory!", "Item Added", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        JButton barcodeBtn = UIHelper.createRoundedButton("📷 Scan Barcode", ColorPalette.INFO, ColorPalette.TEXT_WHITE, FontManager.small(), 12);
        barcodeBtn.setPreferredSize(new Dimension(130, 34));
        barcodeBtn.addActionListener(e -> {
            pantryModel.addRow(new Object[]{"Greek Yogurt (Scanned)", "Dairy", "500g", "In 6 days", "Fresh"});
            JOptionPane.showMessageDialog(this, "Barcode 078349281 scanned!\nIdentified: Greek Yogurt (500g)\nAdded to pantry.", "Barcode Scanner Simulation", JOptionPane.INFORMATION_MESSAGE);
        });

        JButton deleteBtn = UIHelper.createOutlinedButton("Remove Selected", ColorPalette.DANGER, ColorPalette.DANGER, FontManager.small(), 12);
        deleteBtn.setPreferredSize(new Dimension(130, 34));
        deleteBtn.addActionListener(e -> {
            int selected = table.getSelectedRow();
            if (selected >= 0) {
                pantryModel.removeRow(selected);
            } else {
                JOptionPane.showMessageDialog(this, "Please select an ingredient from the table to remove.", "Selection Required", JOptionPane.WARNING_MESSAGE);
            }
        });

        actionPanel.add(new JLabel("Name:"));
        actionPanel.add(nameField);
        actionPanel.add(new JLabel("Category:"));
        actionPanel.add(catCombo);
        actionPanel.add(addBtn);
        actionPanel.add(barcodeBtn);
        actionPanel.add(deleteBtn);

        JPanel top = new JPanel(new BorderLayout(5, 5));
        top.setOpaque(false);
        top.add(notificationBanner, BorderLayout.NORTH);
        top.add(actionPanel, BorderLayout.SOUTH);

        panel.add(top, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);

        return panel;
    }

    // ─────────────────────────────────────────────────────────────────
    // Tab 2: AI Recipe Engine
    // ─────────────────────────────────────────────────────────────────
    private JPanel createRecipePanel() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBackground(ColorPalette.BG_LIGHT);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel controlBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        controlBar.setOpaque(false);

        controlBar.add(new JLabel("Dietary Filter:"));
        dietFilterCombo = new JComboBox<>(new String[]{"High Protein / Balanced", "Keto / Low-Carb", "Vegetarian", "Quick 15-Minute Meals"});
        controlBar.add(dietFilterCombo);

        JButton generateBtn = UIHelper.createRoundedButton("✨ Generate AI Recipes From Pantry", ColorPalette.PRIMARY, ColorPalette.TEXT_WHITE, FontManager.buttonSecondary(), 16);
        generateBtn.setPreferredSize(new Dimension(280, 38));

        controlBar.add(generateBtn);

        recipeOutputArea = new JTextArea();
        recipeOutputArea.setFont(FontManager.mono());
        recipeOutputArea.setEditable(false);
        recipeOutputArea.setLineWrap(true);
        recipeOutputArea.setWrapStyleWord(true);
        recipeOutputArea.setText(
                "=========================================================================\n" +
                "               SMARTPANTRY AI RECIPE GENERATION ENGINE                  \n" +
                "=========================================================================\n" +
                "Click 'Generate AI Recipes From Pantry' above to synthesize dynamic meals\n" +
                "tailored to the ingredients expiring soonest in your inventory.\n"
        );

        generateBtn.addActionListener(e -> {
            recipeOutputArea.setText("🔄 Analyzing 6 pantry ingredients with priority on expiring items...\n");
            Timer timer = new Timer(600, evt -> {
                String filter = (String) dietFilterCombo.getSelectedItem();
                recipeOutputArea.setText(
                        "=========================================================================\n" +
                        "  ✨ SMARTPANTRY AI SUGGESTED RECIPE (Match Score: 94% Pantry Coverage)  \n" +
                        "=========================================================================\n\n" +
                        "  RECIPE NAME: Pan-Seared Garlic Spinach Chicken Alfredo\n" +
                        "  PREP TIME: 20 mins | SERVINGS: 2 | ESTIMATED CALORIES: 450 kcal\n" +
                        "  PROFILE COMPLIANCE: " + filter + " Checked & Approved ✓\n\n" +
                        "  [INGREDIENTS FROM YOUR PANTRY (Zero Waste Priority)]:\n" +
                        "    ✓ Whole Milk (Expiring Tomorrow! - Rescued 250ml)\n" +
                        "    ✓ Baby Spinach (Expiring in 2 days - Rescued 150g)\n" +
                        "    ✓ Chicken Breast (300g diced)\n" +
                        "    ✓ Cheddar Cheese (100g grated)\n" +
                        "    ✓ Garlic & Herbs seasoning\n\n" +
                        "  [MISSING INGREDIENTS (Auto-sent to Shopping List)]:\n" +
                        "    ⚠️ Heavy Cream (Optional alternative to milk)\n" +
                        "    ⚠️ Olive Oil (1 tbsp)\n\n" +
                        "  [STEP-BY-STEP COOKING INSTRUCTIONS]:\n" +
                        "  1. Heat a skillet over medium-high heat. Season diced chicken breast with garlic & herbs.\n" +
                        "  2. Sear chicken for 6-8 minutes until golden brown and cooked through (165°F).\n" +
                        "  3. Reduce heat to medium. Add fresh spinach and gently wilt for 1 minute.\n" +
                        "  4. Pour in milk and stir in grated cheddar cheese, simmering until a velvety sauce forms.\n" +
                        "  5. Toss chicken in sauce and serve immediately over steamed brown rice or greens.\n\n" +
                        "  💡 FOOD WASTE IMPACT: 2 high-risk ingredients salvaged! Saved ~ $4.50 in groceries.\n"
                );
                ((Timer) evt.getSource()).stop();
            });
            timer.setRepeats(false);
            timer.start();
        });

        JScrollPane scroll = new JScrollPane(recipeOutputArea);
        scroll.setBorder(BorderFactory.createLineBorder(ColorPalette.CARD_BORDER));

        panel.add(controlBar, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);

        return panel;
    }

    // ─────────────────────────────────────────────────────────────────
    // Tab 3: Smart Shopping List
    // ─────────────────────────────────────────────────────────────────
    private JPanel createShoppingPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(ColorPalette.BG_LIGHT);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        String[] cols = {"Item Required", "Aisle / Category", "Source", "Status"};
        shoppingModel = new DefaultTableModel(cols, 0);
        shoppingModel.addRow(new Object[]{"Extra Virgin Olive Oil", "Oils & Condiments", "AI Recipe Gap", "Pending"});
        shoppingModel.addRow(new Object[]{"Heavy Cream", "Dairy & Refrigerated", "AI Recipe Gap", "Pending"});
        shoppingModel.addRow(new Object[]{"Avocados", "Fresh Produce", "Manual Addition", "Pending"});
        shoppingModel.addRow(new Object[]{"Whole Wheat Tortillas", "Bakery & Breads", "Manual Addition", "Pending"});

        JTable table = new JTable(shoppingModel);
        table.setRowHeight(32);
        table.setFont(FontManager.body());
        table.getTableHeader().setFont(FontManager.bodyBold());

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createLineBorder(ColorPalette.CARD_BORDER));

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        top.setOpaque(false);

        JTextField itemField = new JTextField(15);
        JComboBox<String> aisleCombo = new JComboBox<>(new String[]{"Produce", "Dairy", "Bakery", "Spices", "Pantry Essentials"});

        JButton addBtn = UIHelper.createRoundedButton("Add to List", ColorPalette.PRIMARY, ColorPalette.TEXT_WHITE, FontManager.small(), 12);
        addBtn.setPreferredSize(new Dimension(110, 34));
        addBtn.addActionListener(e -> {
            String val = itemField.getText().trim();
            if (!val.isEmpty()) {
                shoppingModel.addRow(new Object[]{val, aisleCombo.getSelectedItem(), "Manual Addition", "Pending"});
                itemField.setText("");
            }
        });

        JButton syncBtn = UIHelper.createRoundedButton("📱 Sync to Mobile App", ColorPalette.ACCENT_DARK, ColorPalette.TEXT_WHITE, FontManager.small(), 12);
        syncBtn.setPreferredSize(new Dimension(170, 34));
        syncBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Grocery list synchronized to mobile devices associated with your family account!", "Mobile Sync Complete", JOptionPane.INFORMATION_MESSAGE);
        });

        top.add(new JLabel("Quick Add:"));
        top.add(itemField);
        top.add(aisleCombo);
        top.add(addBtn);
        top.add(syncBtn);

        panel.add(top, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);

        return panel;
    }

    // ─────────────────────────────────────────────────────────────────
    // Tab 4: User Profile & Dietary Restrictions
    // ─────────────────────────────────────────────────────────────────
    private JPanel createProfilePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(ColorPalette.BG_LIGHT);
        panel.setBorder(BorderFactory.createEmptyBorder(25, 40, 25, 40));

        JLabel title = new JLabel("User Profile & Dietary Preferences");
        title.setFont(FontManager.cardTitle());
        title.setForeground(ColorPalette.TEXT_PRIMARY);

        JLabel sub = new JLabel("SmartPantry aligns recipe generation and inventory safety with your health and dietary constraints.");
        sub.setFont(FontManager.body());
        sub.setForeground(ColorPalette.TEXT_SECONDARY);

        panel.add(title);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(sub);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Profile fields
        JPanel fields = new JPanel(new GridLayout(4, 2, 15, 12));
        fields.setOpaque(false);
        fields.setMaximumSize(new Dimension(650, 160));

        JTextField nameF = new JTextField("Enterprise Kitchen Admin");
        JTextField emailF = new JTextField("admin@smartpantry.internal");
        JComboBox<String> dietC = new JComboBox<>(new String[]{"Omnivore", "Vegetarian", "Vegan", "Keto", "Paleo", "Halal", "Kosher"});
        JComboBox<String> sizeC = new JComboBox<>(new String[]{"1 Person", "2 People", "3-4 Family Members", "5+ Household"});

        fields.add(new JLabel("Full Name:"));
        fields.add(nameF);
        fields.add(new JLabel("Email Address:"));
        fields.add(emailF);
        fields.add(new JLabel("Primary Diet:"));
        fields.add(dietC);
        fields.add(new JLabel("Household Size:"));
        fields.add(sizeC);

        panel.add(fields);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Allergies
        JLabel allergyTitle = new JLabel("Allergies & Sensitivities (AI Recipe Blacklist):");
        allergyTitle.setFont(FontManager.bodyBold());
        panel.add(allergyTitle);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        JPanel allergies = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        allergies.setOpaque(false);
        JCheckBox c1 = new JCheckBox("Peanuts / Tree Nuts");
        JCheckBox c2 = new JCheckBox("Gluten / Wheat");
        JCheckBox c3 = new JCheckBox("Dairy / Lactose");
        JCheckBox c4 = new JCheckBox("Shellfish");
        JCheckBox c5 = new JCheckBox("Soy");

        allergies.add(c1);
        allergies.add(c2);
        allergies.add(c3);
        allergies.add(c4);
        allergies.add(c5);
        panel.add(allergies);

        panel.add(Box.createRigidArea(new Dimension(0, 25)));

        JButton saveBtn = UIHelper.createRoundedButton("Save Preferences", ColorPalette.PRIMARY, ColorPalette.TEXT_WHITE, FontManager.buttonPrimary(), 18);
        saveBtn.setPreferredSize(new Dimension(170, 40));
        saveBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Profile preferences saved successfully!\nAI Recipe Engine updated with latest dietary rules.", "Saved", JOptionPane.INFORMATION_MESSAGE);
        });
        panel.add(saveBtn);

        return panel;
    }
}
