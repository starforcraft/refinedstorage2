package com.refinedmods.refinedstorage2.core.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class Quantities {
    private static final DecimalFormat FORMATTER_WITH_UNITS = new DecimalFormat("####0.#", DecimalFormatSymbols.getInstance(Locale.US));
    private static final DecimalFormat FORMATTER = new DecimalFormat("#,###", DecimalFormatSymbols.getInstance(Locale.US));

    private Quantities() {
    }

    public static String formatWithUnits(long qty) {
        if (qty >= 1_000_000_000) {
            return FORMATTER_WITH_UNITS.format(qty / 1_000_000_000F) + "B";
        } else if (qty >= 1_000_000) {
            if (qty >= 100_000_000) {
                return FORMATTER_WITH_UNITS.format(Math.floor(qty / 1_000_000F)) + "M";
            }
            return FORMATTER_WITH_UNITS.format(qty / 1_000_000F) + "M";
        } else if (qty >= 1000) {
            if (qty >= 100_000) {
                return FORMATTER_WITH_UNITS.format(Math.floor(qty / 1000F)) + "K";
            }
            return FORMATTER_WITH_UNITS.format(qty / 1000F) + "K";
        }
        return String.valueOf(qty);
    }

    public static String format(long qty) {
        return FORMATTER.format(qty);
    }
}
