package com.smartpantry.landing.utils;

import java.awt.Font;
import java.awt.GraphicsEnvironment;

/**
 * Centralized font management for the SmartPantry application.
 * Provides consistent typography across all components.
 */
public final class FontManager {

    private static final String PRIMARY_FONT;
    private static final String MONO_FONT;

    static {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        String[] fontNames = ge.getAvailableFontFamilyNames();
        PRIMARY_FONT = findFont(fontNames, "Segoe UI", "SF Pro Display", "Helvetica Neue", "Arial");
        MONO_FONT = findFont(fontNames, "Cascadia Code", "JetBrains Mono", "Consolas", "Courier New");
    }

    private FontManager() {}

    private static String findFont(String[] available, String... preferred) {
        for (String pref : preferred) {
            for (String avail : available) {
                if (avail.equalsIgnoreCase(pref)) return avail;
            }
        }
        return "SansSerif";
    }

    // ── Display / Hero fonts ──────────────────────────────────────────
    public static Font heroTitle()     { return new Font(PRIMARY_FONT, Font.BOLD, 56); }
    public static Font heroSubtitle()  { return new Font(PRIMARY_FONT, Font.PLAIN, 20); }

    // ── Section fonts ─────────────────────────────────────────────────
    public static Font sectionTitle()  { return new Font(PRIMARY_FONT, Font.BOLD, 36); }
    public static Font sectionSub()    { return new Font(PRIMARY_FONT, Font.PLAIN, 16); }

    // ── Card / Component fonts ────────────────────────────────────────
    public static Font cardTitle()     { return new Font(PRIMARY_FONT, Font.BOLD, 20); }
    public static Font cardBody()      { return new Font(PRIMARY_FONT, Font.PLAIN, 14); }
    public static Font cardCaption()   { return new Font(PRIMARY_FONT, Font.PLAIN, 12); }

    // ── Navigation fonts ──────────────────────────────────────────────
    public static Font navLink()       { return new Font(PRIMARY_FONT, Font.PLAIN, 14); }
    public static Font navBrand()      { return new Font(PRIMARY_FONT, Font.BOLD, 22); }

    // ── Button fonts ──────────────────────────────────────────────────
    public static Font buttonPrimary() { return new Font(PRIMARY_FONT, Font.BOLD, 15); }
    public static Font buttonSecondary() { return new Font(PRIMARY_FONT, Font.PLAIN, 14); }

    // ── Stat / Metric fonts ───────────────────────────────────────────
    public static Font statNumber()    { return new Font(PRIMARY_FONT, Font.BOLD, 42); }
    public static Font statLabel()     { return new Font(PRIMARY_FONT, Font.PLAIN, 14); }

    // ── Utility ───────────────────────────────────────────────────────
    public static Font body()          { return new Font(PRIMARY_FONT, Font.PLAIN, 15); }
    public static Font bodyBold()      { return new Font(PRIMARY_FONT, Font.BOLD, 15); }
    public static Font small()         { return new Font(PRIMARY_FONT, Font.PLAIN, 12); }
    public static Font mono()          { return new Font(MONO_FONT, Font.PLAIN, 13); }
    public static Font badge()         { return new Font(PRIMARY_FONT, Font.BOLD, 11); }
    public static Font footerText()    { return new Font(PRIMARY_FONT, Font.PLAIN, 13); }
    public static Font footerHeading() { return new Font(PRIMARY_FONT, Font.BOLD, 16); }
}
