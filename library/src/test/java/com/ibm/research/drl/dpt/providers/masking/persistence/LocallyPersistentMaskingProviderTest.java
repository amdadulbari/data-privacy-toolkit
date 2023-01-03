/*******************************************************************
 *                                                                 *
 * Copyright IBM Corp. 2015                                        *
 *                                                                 *
 *******************************************************************/
package com.ibm.research.drl.dpt.providers.masking.persistence;

import com.ibm.research.drl.dpt.configuration.ConfigurationManager;
import com.ibm.research.drl.dpt.configuration.DefaultMaskingConfiguration;
import com.ibm.research.drl.dpt.providers.masking.EmailMaskingProvider;
import com.ibm.research.drl.dpt.providers.masking.MaskingProvider;
import com.ibm.research.drl.dpt.providers.masking.MaskingProviderFactory;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class LocallyPersistentMaskingProviderTest {

    @Test
    public void testPersistence() {

        String email1 = "joedoe1@foo.com";
        String email2 = "joedoe2@foo.com";

        DefaultMaskingConfiguration configuration = new DefaultMaskingConfiguration();
        MaskingProvider emailMaskingProvider = new EmailMaskingProvider(new MaskingProviderFactory(new ConfigurationManager(configuration), Collections.emptyMap()));
        LocallyPersistentMaskingProvider locallyPersistentMaskingProvider = new LocallyPersistentMaskingProvider(emailMaskingProvider, configuration);

        String maskedEmail1_once = locallyPersistentMaskingProvider.mask(email1);
        String maskedEmail1_twice = locallyPersistentMaskingProvider.mask(email1);
        assertEquals(maskedEmail1_once, maskedEmail1_twice);

        String maskedEmail2_once = locallyPersistentMaskingProvider.mask(email2);
        assertNotEquals(maskedEmail2_once, maskedEmail1_once);
    }

    @Test
    public void maskingIsPerformedOnlyOnce() {
        MaskingProvider maskingProvider = mock(MaskingProvider.class);
        String email1 = "VALUE";

        when(maskingProvider.mask(anyString(), anyString())).then( invocation -> Integer.toString(invocation.getArguments()[0].hashCode()));

        DefaultMaskingConfiguration configuration = new DefaultMaskingConfiguration();
        LocallyPersistentMaskingProvider locallyPersistentMaskingProvider = new LocallyPersistentMaskingProvider(maskingProvider, configuration);

        String maskedEmail1_once = locallyPersistentMaskingProvider.mask(email1);
        String maskedEmail1_twice = locallyPersistentMaskingProvider.mask(email1);
        assertEquals(maskedEmail1_once, maskedEmail1_twice);

        verify(maskingProvider, times(1)).mask(anyString(), anyString());
    }

    @Test
    public void maskingIsPerformedTwiceWithCase() {
        MaskingProvider maskingProvider = mock(MaskingProvider.class);
        String value1 = "VALUE";
        String value2 = "value";

        when(maskingProvider.mask(anyString(), anyString())).then( invocation -> Integer.toString(invocation.getArguments()[0].hashCode()));

        DefaultMaskingConfiguration configuration = new DefaultMaskingConfiguration();
        configuration.setValue("persistence.normalize.toLower", false);

        LocallyPersistentMaskingProvider locallyPersistentMaskingProvider = new LocallyPersistentMaskingProvider(maskingProvider, configuration);

        String maskedValue1 = locallyPersistentMaskingProvider.mask(value1);
        String maskedValue2 = locallyPersistentMaskingProvider.mask(value2);

        assertNotEquals(maskedValue1, maskedValue2);

        verify(maskingProvider, times(2)).mask(anyString(), anyString());
    }

    @Test
    public void maskingIsPerformedOnlyOnceWithCase() {
        MaskingProvider maskingProvider = mock(MaskingProvider.class);
        String value1 = "VALUE";
        String value2 = "value";

        when(maskingProvider.mask(anyString(), anyString())).then( invocation -> Integer.toString(invocation.getArguments()[0].hashCode()));

        DefaultMaskingConfiguration configuration = new DefaultMaskingConfiguration();
        configuration.setValue("persistence.normalize.toLower", true);

        LocallyPersistentMaskingProvider locallyPersistentMaskingProvider = new LocallyPersistentMaskingProvider(maskingProvider, configuration);

        String maskedValue = locallyPersistentMaskingProvider.mask(value1);
        String maskedValueAgain = locallyPersistentMaskingProvider.mask(value1);
        assertEquals(maskedValue, maskedValueAgain);

        String maskedValueDifferentCase = locallyPersistentMaskingProvider.mask(value2);
        assertEquals(maskedValue, maskedValueDifferentCase);

        verify(maskingProvider, times(1)).mask(anyString(), anyString());
    }
}
