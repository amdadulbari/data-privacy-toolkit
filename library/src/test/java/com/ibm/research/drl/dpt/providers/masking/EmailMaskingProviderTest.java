/*******************************************************************
 *                                                                 *
 * Copyright IBM Corp. 2015                                        *
 *                                                                 *
 *******************************************************************/
package com.ibm.research.drl.dpt.providers.masking;

import com.ibm.research.drl.dpt.configuration.DefaultMaskingConfiguration;
import com.ibm.research.drl.dpt.configuration.FailMode;
import com.ibm.research.drl.dpt.configuration.MaskingConfiguration;
import com.ibm.research.drl.dpt.providers.identifiers.EmailIdentifier;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNot.not;
import static org.junit.jupiter.api.Assertions.*;

public class EmailMaskingProviderTest {

    @Test
    public void testMask() throws Exception {
        EmailMaskingProvider maskingProvider = new EmailMaskingProvider(new MaskingProviderFactory());

        assertThat(maskingProvider.mask("dummyEmail@ie.ibm.com"), not("dummyEmail@ie.ibm.com"));
        assertThat(maskingProvider.mask("adsfasdfafs12341@fdlkjfsal.com"), not("adsfasdfafs12341@fdlkjfsal.com"));
        assertThat(maskingProvider.mask("a12399@fdsaf.eu"), not("a12399@fdsaf.eu"));
        assertTrue(maskingProvider.mask("dummyname@test.com").endsWith(".com"));
        assertTrue(maskingProvider.mask("dummyname@test.co.uk").endsWith(".co.uk"));
    }

    @Test
    public void testMaskInvalidValue() throws Exception {
        MaskingConfiguration maskingConfiguration = new DefaultMaskingConfiguration();
        maskingConfiguration.setValue("fail.mode", FailMode.GENERATE_RANDOM);

        EmailMaskingProvider maskingProvider = new EmailMaskingProvider(maskingConfiguration, new MaskingProviderFactory());
        EmailIdentifier identifier = new EmailIdentifier();

        String invalidEmail = "foobar";
        String randomEmail = maskingProvider.mask(invalidEmail);

        assertFalse(randomEmail.equals(invalidEmail));
        assertTrue(identifier.isOfThisType(randomEmail));

        invalidEmail = "foobar@";
        randomEmail = maskingProvider.mask(invalidEmail);

        assertFalse(randomEmail.equals(invalidEmail));
        assertTrue(identifier.isOfThisType(randomEmail));

        invalidEmail = "foobar@foo";
        randomEmail = maskingProvider.mask(invalidEmail);

        assertFalse(randomEmail.equals(invalidEmail));
        assertTrue(identifier.isOfThisType(randomEmail));

    }

    @Test
    public void testMaskNameBasedUsername() {
        MaskingConfiguration maskingConfiguration = new DefaultMaskingConfiguration();
        maskingConfiguration.setValue("email.nameBasedUsername", true);

        MaskingProvider maskingProvider = new EmailMaskingProvider(maskingConfiguration, new MaskingProviderFactory());

        String originalUsername = "dummy";
        String original = originalUsername + "@ie.ibm.com";

        int randomOK = 0;

        for(int i = 0; i < 100; i++) {
            String maskedEmail = maskingProvider.mask(original);
            String maskedUsername = maskedEmail.substring(0, maskedEmail.indexOf('@'));

            if (!maskedUsername.toLowerCase().equals(originalUsername)) {
                randomOK++;
            }
        }

        assertTrue(randomOK > 0);
    }

    @Test
    public void testRandomPreserveDomainsNegative() {
        DefaultMaskingConfiguration configuration = new DefaultMaskingConfiguration();
        configuration.setValue("email.preserve.domains", -1);
        
        EmailMaskingProvider maskingProvider = new EmailMaskingProvider(configuration, new MaskingProviderFactory());
        
        String originalValue = "test@ie.ibm.com";
        String maskedValue = maskingProvider.mask(originalValue);
        
        assertTrue(maskedValue.endsWith("@ie.ibm.com"));
    }

    @Test
    public void testRandomPreserveDomainsUnknownCapitalizedTLD() {
        DefaultMaskingConfiguration configuration = new DefaultMaskingConfiguration();
        configuration.setValue("email.preserve.domains", 3);

        EmailMaskingProvider maskingProvider = new EmailMaskingProvider(configuration, new MaskingProviderFactory());

        String originalValue = "test@gamers.prO";
        String maskedValue = maskingProvider.mask(originalValue);
        assertTrue(maskedValue.endsWith("@gamers.prO"));

        originalValue = "test@gamers.pra";
        maskedValue = maskingProvider.mask(originalValue);
        assertTrue(maskedValue.endsWith("@gamers.pra"));
    }

    @Test
    public void testMaskVirtualFieldUsername() {
        MaskingConfiguration maskingConfiguration = new DefaultMaskingConfiguration();
        maskingConfiguration.setValue("default.masking.provider", "HASH");
        maskingConfiguration.setValue("email.usernameVirtualField", "__username__");

        final MaskingProviderFactory factory = new MaskingProviderFactory();

        EmailMaskingProvider emailMaskingProvider = new EmailMaskingProvider(maskingConfiguration, factory);
        HashMaskingProvider hashMaskingProvider = new HashMaskingProvider();

        String originalValue = "user1@ibm.com";

        String maskedValue = emailMaskingProvider.mask(originalValue);

        String[] tokens = maskedValue.split("@");
        assertEquals(2, tokens.length);
        assertEquals(hashMaskingProvider.mask("user1"), tokens[0]);
    }

    @Test
    public void testMaskVirtualFieldDomain() {
        MaskingConfiguration maskingConfiguration = new DefaultMaskingConfiguration();
        maskingConfiguration.setValue("default.masking.provider", "HASH");
        maskingConfiguration.setValue("email.preserve.domains", 0);
        maskingConfiguration.setValue("email.domainVirtualField", "__domain__");

        final MaskingProviderFactory factory = new MaskingProviderFactory();

        EmailMaskingProvider emailMaskingProvider = new EmailMaskingProvider(maskingConfiguration, factory);
        HashMaskingProvider hashMaskingProvider = new HashMaskingProvider();

        String originalValue = "user1@ibm.com";

        String maskedValue = emailMaskingProvider.mask(originalValue);

        String[] tokens = maskedValue.split("@");
        assertEquals(2, tokens.length);
        assertEquals(hashMaskingProvider.mask("ibm.com"), tokens[1]);
    }

    @Test
    public void testMaskVirtualFieldDomainPreserveDomains() {
        MaskingConfiguration maskingConfiguration = new DefaultMaskingConfiguration();
        maskingConfiguration.setValue("default.masking.provider", "HASH");
        maskingConfiguration.setValue("email.preserve.domains", 1);
        maskingConfiguration.setValue("email.domainVirtualField", "__domain__");

        final MaskingProviderFactory factory = new MaskingProviderFactory();

        EmailMaskingProvider emailMaskingProvider = new EmailMaskingProvider(maskingConfiguration, factory);
        HashMaskingProvider hashMaskingProvider = new HashMaskingProvider();

        String originalValue = "user1@ibm.com";

        String maskedValue = emailMaskingProvider.mask(originalValue);

        String[] tokens = maskedValue.split("@");
        assertEquals(2, tokens.length);
        assertEquals(hashMaskingProvider.mask("ibm") + ".com", tokens[1]);
    }

    @Test
    @Disabled
    public void testPerformance() {
        int N = 1000000;
        DefaultMaskingConfiguration defaultMaskingConfiguration = new DefaultMaskingConfiguration("default");
        defaultMaskingConfiguration.setValue("email.preserve.domains", 0);
        DefaultMaskingConfiguration preserveConfiguration = new DefaultMaskingConfiguration("preserve");
        preserveConfiguration.setValue("email.preserve.domains", 2);

        DefaultMaskingConfiguration[] configurations = new DefaultMaskingConfiguration[]{
                defaultMaskingConfiguration, preserveConfiguration
        };

        String originalValue = "dummyEmail@ie.ibm.com";

        for (DefaultMaskingConfiguration configuration : configurations) {
            EmailMaskingProvider maskingProvider = new EmailMaskingProvider(configuration, new MaskingProviderFactory());

            long startMillis = System.currentTimeMillis();

            for (int i = 0; i < N; i++) {
                String maskedValue = maskingProvider.mask(originalValue);
            }

            long diff = System.currentTimeMillis() - startMillis;
            System.out.println(String.format("%s: %d operations took %d milliseconds (%f per op)",
                    configuration.getName(), N, diff, (double) diff / N));
        }
    }
}
