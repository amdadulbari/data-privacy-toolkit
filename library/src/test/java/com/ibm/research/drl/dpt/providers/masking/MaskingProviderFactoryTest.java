/*******************************************************************
 *                                                                 *
 * Copyright IBM Corp. 2015                                        *
 *                                                                 *
 *******************************************************************/
package com.ibm.research.drl.dpt.providers.masking;

import com.ibm.research.drl.dpt.configuration.ConfigurationManager;
import com.ibm.research.drl.dpt.configuration.DataMaskingTarget;
import com.ibm.research.drl.dpt.configuration.DefaultMaskingConfiguration;
import com.ibm.research.drl.dpt.configuration.MaskingConfiguration;
import com.ibm.research.drl.dpt.providers.ProviderType;
import com.ibm.research.drl.dpt.providers.masking.persistence.LocallyPersistentMaskingProvider;
import org.apache.commons.lang3.SerializationUtils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class FoobarMaskingProvider extends AbstractMaskingProvider {

    public FoobarMaskingProvider(SecureRandom random, MaskingConfiguration maskingConfiguration) {}

    @Override
    public String mask(String identifier) {
        return "foobar123";
    }
}

public class MaskingProviderFactoryTest {

    @Test
    @Disabled
    public void testPersistenceBetweenInstances() {
        fail("Not implemented yet");
    }

    @Test
    public void testRegisterOK() {
        Map<String, DataMaskingTarget> identifiedTypes = new HashMap<>();
        ProviderType foobarType = ProviderType.valueOf("FOOBAR");
        identifiedTypes.put("f1", new DataMaskingTarget(foobarType, "f1"));

        MaskingProviderFactory maskingProviderFactory = new MaskingProviderFactory(new ConfigurationManager(),
                identifiedTypes);

        maskingProviderFactory.registerMaskingProviderClass(FoobarMaskingProvider.class, foobarType);
        MaskingProvider maskingProvider = maskingProviderFactory.get("f1");
        assertTrue(maskingProvider instanceof FoobarMaskingProvider);
        assertEquals("foobar123", maskingProvider.mask(""));
    }

    @Test
    public void maskNamedFields() {

        Map<String, DataMaskingTarget> identifiedTypes = new HashMap<>();
        identifiedTypes.put("name", new DataMaskingTarget(ProviderType.NAME, "name"));

    /* Instantiate a MaskingProviderFactory with a ConfigurationManager and the map of identified types */
        MaskingProviderFactory maskingProviderFactory = new MaskingProviderFactory(new ConfigurationManager(),
                identifiedTypes);

    /* Get the masking provider and mask a value */
        MaskingProvider maskingProvider = maskingProviderFactory.get("name");
        String maskedValue = maskingProvider.mask("John Smith");
        System.out.println(maskedValue);
    }

    @Test
    public void testWithoutIdentified() {
        ConfigurationManager configurationManager = new ConfigurationManager();

        MaskingProviderFactory maskingProviderFactory = new MaskingProviderFactory(configurationManager, null);
        MaskingProvider maskingProvider = maskingProviderFactory.get(ProviderType.NAME, configurationManager.getDefaultConfiguration());

        String originalValue = "John";
        String maskedValue = maskingProvider.mask(originalValue);
        System.out.println(maskedValue);
    }

    @Test
    public void testPersistent() {
        MaskingProviderFactory mpf = new MaskingProviderFactory();

        MaskingProvider mp = mpf.get(ProviderType.NAME, new DefaultMaskingConfiguration());

        assertTrue(mp instanceof NameMaskingProvider);
    }

    @Test
    public void testPersistentLocalOnly() {
        MaskingProviderFactory mpf = new MaskingProviderFactory();
        MaskingConfiguration configuration = new DefaultMaskingConfiguration();

        MaskingProvider mp1 = mpf.get(ProviderType.NAME, configuration);

        assertTrue(mp1 instanceof NameMaskingProvider);

        MaskingProvider mp2 = mpf.get(ProviderType.NAME, configuration);

        assertTrue(mp2 instanceof NameMaskingProvider);

        assertNotSame(mp1, mp2);

        configuration.setValue("persistence.export", true);
        configuration.setValue("persistence.namespace", "AAAA");

        MaskingProvider mp3 = mpf.get(ProviderType.NAME, configuration);

        assertTrue(mp3 instanceof LocallyPersistentMaskingProvider);
        
        configuration.setValue("persistence.namespace", "BBBB");

        MaskingProvider mp4 = mpf.get(ProviderType.NAME, configuration);

        assertTrue(mp4 instanceof LocallyPersistentMaskingProvider);

        assertNotSame(mp3, mp4);
    }

    @Test
    public void testPersistentGlobalSameConfiguration() {
        MaskingProviderFactory mpf = new MaskingProviderFactory();
        MaskingConfiguration configuration = new DefaultMaskingConfiguration();
        configuration.setValue("persistence.export", true);
        configuration.setValue("persistence.namespace", "test");

        MaskingProvider mp1 = mpf.get(ProviderType.NAME, configuration);

        assertTrue(mp1 instanceof LocallyPersistentMaskingProvider);

        MaskingProvider mp2 = mpf.get(ProviderType.NAME, configuration);

        assertTrue(mp2 instanceof LocallyPersistentMaskingProvider);

        assertSame(mp1, mp2);
    }

    @Test
    @Disabled
    public void verifySerialization() throws Exception {
        MaskingProviderFactory mpf = new MaskingProviderFactory();
        MaskingConfiguration configuration = new DefaultMaskingConfiguration();

        for (ProviderType providerType : ProviderType.values()) {
            MaskingProvider maskingProvider = mpf.get(providerType, configuration);

            assertNotNull(maskingProvider);
            assertTrue(maskingProvider instanceof Serializable);
            assertNotSame(maskingProvider, SerializationUtils.clone(maskingProvider));
        }
    }
}