package com.smartpantry.landing.utils;

import java.awt.Color;

/**
 * Enterprise color palette for SmartPantry.
 * Defines a consistent, professional color scheme across the entire application.
 */
public final class ColorPalette {

    private ColorPalette() {} // Prevent instantiation

    // ── Primary Brand Colors ──────────────────────────────────────────
    public static final Color PRIMARY        = new Color(16, 185, 129);   // Emerald-500
    public static final Color PRIMARY_DARK   = new Color(5, 150, 105);    // Emerald-600
    public static final Color PRIMARY_DARKER = new Color(4, 120, 87);     // Emerald-700
    public static final Color PRIMARY_LIGHT  = new Color(52, 211, 153);   // Emerald-400
    public static final Color PRIMARY_PALE   = new Color(209, 250, 229);  // Emerald-100

    // ── Accent Colors ─────────────────────────────────────────────────
    public static final Color ACCENT         = new Color(245, 158, 11);   // Amber-500
    public static final Color ACCENT_LIGHT   = new Color(252, 211, 77);   // Amber-300
    public static final Color ACCENT_DARK    = new Color(217, 119, 6);    // Amber-600

    // ── Neutral / Background Colors ───────────────────────────────────
    public static final Color BG_DARK        = new Color(15, 23, 42);     // Slate-900
    public static final Color BG_DARK_ALT    = new Color(30, 41, 59);     // Slate-800
    public static final Color BG_MEDIUM      = new Color(51, 65, 85);     // Slate-700
    public static final Color BG_LIGHT       = new Color(248, 250, 252);  // Slate-50
    public static final Color BG_WHITE       = new Color(255, 255, 255);

    // ── Text Colors ───────────────────────────────────────────────────
    public static final Color TEXT_PRIMARY    = new Color(15, 23, 42);     // Slate-900
    public static final Color TEXT_SECONDARY  = new Color(100, 116, 139);  // Slate-500
    public static final Color TEXT_MUTED      = new Color(148, 163, 184);  // Slate-400
    public static final Color TEXT_WHITE      = new Color(255, 255, 255);
    public static final Color TEXT_ON_DARK    = new Color(226, 232, 240);  // Slate-200

    // ── Status Colors ─────────────────────────────────────────────────
    public static final Color SUCCESS        = new Color(34, 197, 94);    // Green-500
    public static final Color WARNING        = new Color(234, 179, 8);    // Yellow-500
    public static final Color DANGER         = new Color(239, 68, 68);    // Red-500
    public static final Color INFO           = new Color(59, 130, 246);   // Blue-500

    // ── Card & Surface Colors ─────────────────────────────────────────
    public static final Color CARD_BG        = new Color(255, 255, 255);
    public static final Color CARD_BORDER    = new Color(226, 232, 240);  // Slate-200
    public static final Color CARD_SHADOW    = new Color(0, 0, 0, 15);
    public static final Color DIVIDER        = new Color(226, 232, 240);  // Slate-200

    // ── Gradient pairs ────────────────────────────────────────────────
    public static final Color HERO_GRAD_START = new Color(15, 23, 42);
    public static final Color HERO_GRAD_END   = new Color(30, 58, 52);

    public static final Color CTA_GRAD_START  = new Color(5, 150, 105);
    public static final Color CTA_GRAD_END    = new Color(16, 185, 129);

    /**
     * Returns a color with modified alpha transparency.
     */
    public static Color withAlpha(Color color, int alpha) {
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
    }
}
