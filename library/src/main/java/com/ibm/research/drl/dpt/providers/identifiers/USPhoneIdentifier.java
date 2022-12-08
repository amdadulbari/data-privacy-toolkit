/*******************************************************************
 *                                                                 *
 * Copyright IBM Corp. 2021                                        *
 *                                                                 *
 *******************************************************************/
package com.ibm.research.drl.dpt.providers.identifiers;

import com.ibm.research.drl.dpt.providers.ProviderType;
import com.ibm.research.drl.dpt.util.Tuple;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class USPhoneIdentifier extends AbstractIdentifier implements IdentifierWithOffset {
    @Override
    public ProviderType getType() {
        return ProviderType.PHONE;
    }

    @Override
    public boolean isOfThisType(String data) {
        return isOfThisTypeWithOffset(data).getFirst();
    }

    @Override
    public String getDescription() {
        return "US specific phone/fax/beeper identifier";
    }

    private final static String dashedPhonePattern = "(?:#\\s*)?((?:(?:\\(\\s*\\d{3}\\s*\\)|(?:1" +
            "-" +
            ")?\\d{3}" +
            "-" +
            ")\\s*)?\\d{3}" +
            "-" +
            "\\d{4})";

    private final static String spacedPhonePattern = "#?\\s*((?:(?:\\(\\s*\\d{3}\\s*\\)|(?:1" +
            " " +
            ")?\\d{3}" +
            " " +
            ")\\s*)?\\d{3}" +
            " " +
            "\\d{4})";

    private final static String n = "\\s*(?::|No\\.\\s*:?|H|M)?\\s*";

    private final static String prefixes = "(?:" +
            "Pgr" +
            "|Tel(?:ephone)?" +
            "|Ph(?:one)?" +
            "|Fax" +
            "|Home" +
            "|Mobile" +
            "|Work" +
            "|cell" +
            "|contact" +
            ")";

    private static final List<Pattern> patterns = Arrays.asList(
            Pattern.compile(dashedPhonePattern),
            Pattern.compile(spacedPhonePattern),
            Pattern.compile(prefixes + n + spacedPhonePattern, Pattern.CASE_INSENSITIVE),
            Pattern.compile(prefixes + n + dashedPhonePattern, Pattern.CASE_INSENSITIVE)
    );

    @Override
    public Tuple<Boolean, Tuple<Integer, Integer>> isOfThisTypeWithOffset(String data) {
        for(Pattern pattern: patterns) {
            Matcher matcher = pattern.matcher(data);
            if (matcher.matches()) {
                return new Tuple<>(true, new Tuple<>(matcher.start(1), matcher.end(1) - matcher.start(1)));
            }
        }
        
        return new Tuple<>(false, null);
        /*
        return patterns.parallelStream().map(
                pattern -> pattern.matcher(data)
        ).filter(
                Matcher::matches
        ).map(
                matcher -> new Tuple<>(true, new Tuple<>(matcher.start(1), matcher.end(1) - matcher.start(1)))
        ).reduce(
                (a, b) -> a
        ).orElse(new Tuple<>(false, null));
        */
    }
    
    @Override
    public int getMinimumCharacterRequirements() {
        return CharacterRequirements.DIGIT;
    }

    @Override
    public int getMinimumLength() {
        return 8;
    }

    @Override
    public int getMaximumLength() {
        return Integer.MAX_VALUE;
    }
}

