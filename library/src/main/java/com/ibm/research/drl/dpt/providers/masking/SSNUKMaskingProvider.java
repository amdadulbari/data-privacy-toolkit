/*******************************************************************
 *                                                                 *
 * Copyright IBM Corp. 2015                                        *
 *                                                                 *
 *******************************************************************/
package com.ibm.research.drl.dpt.providers.masking;

import com.ibm.research.drl.dpt.configuration.DefaultMaskingConfiguration;
import com.ibm.research.drl.dpt.configuration.MaskingConfiguration;
import com.ibm.research.drl.dpt.providers.identifiers.SSNUKIdentifier;
import com.ibm.research.drl.dpt.util.RandomGenerators;

import java.security.SecureRandom;

public class SSNUKMaskingProvider extends AbstractMaskingProvider {
    /*
    The format of the number is two prefix letters, six digits, and one suffix letter.[5]
    The example used is typically AB123456C. Often, the number is printed with spaces to pair off
    the digits, like this: AB 12 34 56 C.
    Neither of the first two letters can be D, F, I, Q, U or V. The second letter also cannot be O.
    The prefixes BG, GB, NK, KN, TN, NT and ZZ are not allocated.[6]
    Validation lists of issued two-letter prefixes are published from time to time.[7][8]
    The suffix letter is either A, B, C, or D.[5] (although F, M, and P have been used for temporary numbers in the past)
     */
    private static final SSNUKIdentifier identifier = new SSNUKIdentifier();
    private static final char[] allowedFirstLetters = "ABCEGHJKLMNOPRSTWXYZ".toCharArray();
    private static final char[] allowedSecondLetters = "ABCEGHJKLMNPRSTWXYZ".toCharArray();
    private static final char[] allowedSuffixLetters = "ABCD".toCharArray();
    private final boolean preservePrefix;

    /**
     * Instantiates a new Ssnuk masking provider.
     */
    public SSNUKMaskingProvider() {
        this(new SecureRandom(), new DefaultMaskingConfiguration());
    }

    /**
     * Instantiates a new Ssnuk masking provider.
     *
     * @param configuration the configuration
     */
    public SSNUKMaskingProvider(MaskingConfiguration configuration) {
        this(new SecureRandom(), configuration);
    }

    /**
     * Instantiates a new Ssnuk masking provider.
     *
     * @param random the random
     */
    public SSNUKMaskingProvider(SecureRandom random) {
        this(random, new DefaultMaskingConfiguration());
    }

    /**
     * Instantiates a new Ssnuk masking provider.
     *
     * @param random        the random
     * @param configuration the configuration
     */
    public SSNUKMaskingProvider(SecureRandom random, MaskingConfiguration configuration) {
        this.random = random;
        this.preservePrefix = configuration.getBooleanValue("ssnuk.mask.preservePrefix");
    }

    @Override
    public String mask(String identifier) {
        String prefix;

        if (!SSNUKMaskingProvider.identifier.isOfThisType(identifier) || !this.preservePrefix) {
            prefix = "" + allowedFirstLetters[random.nextInt(allowedFirstLetters.length)];
            prefix += allowedSecondLetters[random.nextInt(allowedSecondLetters.length)];
        } else {
            prefix = identifier.substring(0, 2);
        }

        StringBuilder builder = new StringBuilder(prefix);
        for (int i = 0; i < 6; i++) {
            char randomDigit = RandomGenerators.randomDigit();
            builder.append(randomDigit);
        }

        char suffix = allowedSuffixLetters[random.nextInt(allowedSuffixLetters.length)];

        builder.append(suffix);

        return builder.toString();
    }
}

