package com.bitbus.indexcards.util;

import java.util.regex.Pattern;

public final class StringMatcher {

    private static Pattern ALPHA_NUM_UNDERSCORE_HYPHEN = Pattern.compile("[A-Za-z0-9_-]+");

    private StringMatcher() {
        // Not intended for instantiation
    }

    public static boolean isAlphaNumericUnderscoreHyphen(String input) {
        return ALPHA_NUM_UNDERSCORE_HYPHEN.matcher(input).matches();
    }


}
