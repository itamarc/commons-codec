/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.commons.codec.cli;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;


/**
 * Tests {@link Digest}.
 *
 * @since 1.17
 */
public class DigestTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    /**
     * Tests if empty arguments are handled correctly.
     *
     * @throws IllegalArgumentException for some failure scenarios.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testEmptyArguments() throws IOException {
        Digest.main(new String[0]);
    }
    /**
     * Tests if null arguments are handled correctly.
     *
     * @throws IllegalArgumentException for some failure scenarios.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testNullArguments() throws IOException {
        Digest.main(null);
    }

    /**
     * Tests if MD5 digest is generated correctly.
     */
    @Test
    public void testMD5() throws IOException {
        final String[] args = new String[] { "MD5", "sample message" };
        Digest.main(args);
        assertEquals("362962f26d8763682a1de8ec4a276698\r\n", outContent.toString());
    }

    /**
     * Tests if digests are generated correctly with parameter "ALL".
     */
    @Test
    public void testAllAlgorithms() throws IOException {
        final String[] args = new String[] { "ALL", "sample message" };
        Digest.main(args);
        String expected = "MD2 fecf1d3bd300e5018b89148cd77497a6\r\n" +
        "MD5 362962f26d8763682a1de8ec4a276698\r\n" +
        "SHA-1 7e5ee118ece79e5a2d22711a5bd0f30d617b4321\r\n" +
        "SHA-224 715ef9177f02bc834b85cadc806aae13f2239e3cf0f861505976272a\r\n" +
        "SHA-256 59162c6b059f619b0538f592de24e163061316572869ffc9a2648315dbe75997\r\n" +
        "SHA-384 c72206d84e4f211fb90fe4d9b58dd7339d6b321de4e52906d9f9af3b5808603f580ef45e774cd9dc3e84395ee005ebad\r\n" +
        "SHA-512 dff9a77b195fec431d0c54c11b8d492dfca47ed11a472c2e7452fb432b76988651cfd5d8d428df70552aa6d57c5d6c9ea39ed5bb528462bfb787d4c1618f6e2d\r\n" +
        "SHA-512/224 644e8daf1d38041b875729759b750a8b347af5c2590f67d141ba6a2f\r\n" +
        "SHA-512/256 bf92fa34836193d0c46500b2223caff74503b5f33314178f8437b08c4084210c\r\n";
        assertEquals(expected, outContent.toString());
    }

    /**
     * Tests if MD5 digest is generated correctly.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testNonExistentDigest() throws IOException {
        final String[] args = new String[] { "NonExistentDigest", "sample message" };
        Digest.main(args);
    }
}
