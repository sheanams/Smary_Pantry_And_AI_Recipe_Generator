# 🥬 SmartPantry — Enterprise Landing Page

**AI-Powered Kitchen Intelligence Platform**

A professional, enterprise-grade landing page built entirely with **Java Swing** featuring custom-painted components, animations, and a modern design system.

---

## 🚀 Quick Start

### Prerequisites
- **Java JDK 17+** (OpenJDK or Oracle JDK)

### Build & Run
```bash
# Option 1: Double-click
run.bat

# Option 2: Command line
javac -d out -sourcepath src src\com\smartpantry\landing\SmartPantryLanding.java
java -cp out com.smartpantry.landing.SmartPantryLanding
```

---

## 🏗️ Architecture

```
SmartPantry/
├── src/com/smartpantry/landing/
│   ├── SmartPantryLanding.java          # Main entry point
│   ├── components/
│   │   ├── ModernCard.java              # Feature card with hover-lift
│   │   ├── ModuleCard.java              # Module step card
│   │   └── StatCard.java               # Animated stat counter
│   ├── panels/
│   │   ├── NavBarPanel.java             # Top navigation bar
│   │   ├── HeroPanel.java              # Hero section with particles
│   │   ├── FeaturesPanel.java          # 6-card feature grid
│   │   ├── StatsPanel.java             # Animated metrics bar
│   │   ├── ModulesPanel.java           # 5 system modules
│   │   ├── HowItWorksPanel.java        # 4-step user journey
│   │   ├── PricingPanel.java           # 3-tier pricing cards
│   │   ├── CTAPanel.java               # Call-to-action section
│   │   └── FooterPanel.java            # Multi-column footer
│   └── utils/
│       ├── ColorPalette.java            # Enterprise color system
│       ├── FontManager.java             # Typography management
│       └── UIHelper.java               # UI component factory
└── run.bat                              # Build & run script
```

---

## 🎨 Design Features

| Feature | Description |
|---------|-------------|
| **Custom Painting** | All components use `paintComponent()` with `Graphics2D` for pixel-perfect rendering |
| **Hover Animations** | Smooth lift effects on cards with `Timer`-based animation |
| **Gradient Backgrounds** | Multi-stop gradients on hero, stats, and CTA sections |
| **Animated Counters** | Count-up animation on stat numbers |
| **Floating Particles** | Animated background particles in the hero section |
| **Custom Scrollbar** | Styled scrollbar matching the dark theme |
| **Modern Cards** | Rounded corners, shadows, accent bars, and hover borders |
| **Responsive Grid** | FlowLayout-based cards that adapt to window width |
| **Enterprise Typography** | System font detection with cascading fallbacks |
| **Color Design System** | 30+ named colors with semantic grouping |

---

## 📋 Landing Page Sections

1. **Navigation Bar** — Brand logo, nav links (Features, Modules, How It Works), Sign In / Get Started buttons
2. **Hero Section** — Animated gradient background with headline & CTA
3. **Features Grid** — 6 cards: Inventory, Expiry Alerts, Recipes, Waste Reduction, Shopping, Security
4. **Impact Metrics** — Animated counters: 40% waste reduced, 10K+ recipes, 500K+ users, 98% satisfaction
5. **System Modules** — 5 enterprise modules with feature lists
6. **How It Works** — 4-step visual flow: Scan → AI Analyzes → Get Recipes → Shop Smart
7. **Call to Action** — Final conversion section with gradient background
8. **Footer** — 4-column layout with links and social media

---

## 📝 License

Built for educational / demonstration purposes.
