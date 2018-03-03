package com.github.jjschemaplugin;

import org.apache.maven.plugin.testing.AbstractMojoTestCase;
import org.junit.Test;

import java.io.File;

public class SchemagenMojoTest extends AbstractMojoTestCase {
    /** {@inheritDoc} */
    protected void setUp() throws Exception
    {
        // required
        super.setUp();
    }

    /** {@inheritDoc} */
    protected void tearDown() throws Exception
    {
        // required
        super.tearDown();
    }

    @Test
    public void testExecute() throws Exception {
        File pom = getTestFile( "src/test/resources/unit/json-dto-test/pom.xml" );
        assertNotNull(pom);
        assertTrue(pom.exists());

        SchemagenMojo schemagen = new SchemagenMojo();
        schemagen = (SchemagenMojo) configureMojo(schemagen, extractPluginConfiguration("jjschema-maven-plugin", pom));
        assertNotNull(schemagen);
        schemagen.execute();
    }
}