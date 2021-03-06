/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2017 iText Group NV
    Authors: iText Software.

    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License version 3
    as published by the Free Software Foundation with the addition of the
    following permission added to Section 15 as permitted in Section 7(a):
    FOR ANY PART OF THE COVERED WORK IN WHICH THE COPYRIGHT IS OWNED BY
    ITEXT GROUP. ITEXT GROUP DISCLAIMS THE WARRANTY OF NON INFRINGEMENT
    OF THIRD PARTY RIGHTS

    This program is distributed in the hope that it will be useful, but
    WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
    or FITNESS FOR A PARTICULAR PURPOSE.
    See the GNU Affero General Public License for more details.
    You should have received a copy of the GNU Affero General Public License
    along with this program; if not, see http://www.gnu.org/licenses or write to
    the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
    Boston, MA, 02110-1301 USA, or download the license from the following URL:
    http://itextpdf.com/terms-of-use/

    The interactive user interfaces in modified source and object code versions
    of this program must display Appropriate Legal Notices, as required under
    Section 5 of the GNU Affero General Public License.

    In accordance with Section 7(b) of the GNU Affero General Public License,
    a covered work must retain the producer line in every PDF that is created
    or manipulated using iText.

    You can be released from the requirements of the license by purchasing
    a commercial license. Buying such a license is mandatory as soon as you
    develop commercial activities involving the iText software without
    disclosing the source code of your own applications.
    These activities include: offering paid services to customers as an ASP,
    serving PDFs on the fly in a web application, shipping iText with a closed
    source product.

    For more information, please contact iText Software Corp. at this
    address: sales@itextpdf.com
 */
package com.itextpdf.kernel.pdf;

import com.itextpdf.test.ExtendedITextTest;
import com.itextpdf.test.annotations.type.IntegrationTest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(IntegrationTest.class)
public class FreeReferencesTest extends ExtendedITextTest {
    public static final String destinationFolder = "./target/test/com/itextpdf/kernel/pdf/FreeReferencesTest/";
    public static final String sourceFolder = "./src/test/resources/com/itextpdf/kernel/pdf/FreeReferencesTest/";

    @BeforeClass
    public static void beforeClass() {
        createOrClearDestinationFolder(destinationFolder);
    }

    @Test
    public void freeReferencesTest01() throws IOException {
        String src = "freeRefsGapsAndMaxGen.pdf";
        String out = "freeReferencesTest01.pdf";

        PdfDocument pdfDocument = new PdfDocument(new PdfReader(sourceFolder + src), new PdfWriter(destinationFolder + out));
        pdfDocument.close();

        String[] xrefString = extractXrefTableAsStrings(out);
        String[] expected = new String[] {
                        "xref\n" +
                        "0 15\n" +
                        "0000000004 65535 f \n" +
                        "0000000269 00000 n \n" +
                        "0000000561 00000 n \n" +
                        "0000000314 00000 n \n" +
                        "0000000005 65535 f \n" +
                        "0000000006 00000 f \n" +
                        "0000000007 00000 f \n" +
                        "0000000008 00000 f \n" +
                        "0000000009 00000 f \n" +
                        "0000000010 00000 f \n" +
                        "0000000011 00000 f \n" +
                        "0000000000 00001 f \n" +
                        "0000000133 00000 n \n" +
                        "0000000015 00000 n \n" +
                        "0000000613 00000 n \n" };
        Assert.assertArrayEquals(expected, xrefString);
    }

    @Test
    public void freeReferencesTest02() throws IOException {
        String src = "freeRefsGapsAndMaxGen.pdf";
        String out = "freeReferencesTest02.pdf";

        PdfDocument pdfDocument = new PdfDocument(new PdfReader(sourceFolder + src), new PdfWriter(destinationFolder + out),
                new StampingProperties().useAppendMode());
        pdfDocument.close();

        String[] xrefString = extractXrefTableAsStrings(out);
        String[] expected = new String[] {
                        "xref\n" +
                        "0 5\n" +
                        "0000000010 65535 f \n" +
                        "0000000269 00000 n \n" +
                        "0000000569 00000 n \n" +
                        "0000000314 00000 n \n" +
                        "0000000000 65535 f \n" +
                        "10 5\n" +
                        "0000000011 00000 f \n" + // Append mode, no possibility to fix subsections in first xref
                        "0000000000 00001 f \n" +
                        "0000000133 00000 n \n" +
                        "0000000015 00000 n \n" +
                        "0000000480 00000 n \n",

                        "xref\n" +
                        "0 1\n" +
                        "0000000004 65535 f \n" +
                        "3 9\n" +
                        "0000000995 00000 n \n" +
                        "0000000005 65535 f \n" +
                        "0000000006 00000 f \n" +
                        "0000000007 00000 f \n" +
                        "0000000008 00000 f \n" +
                        "0000000009 00000 f \n" +
                        "0000000010 00000 f \n" +
                        "0000000011 00000 f \n" +
                        "0000000000 00001 f \n"};
        Assert.assertArrayEquals(expected, xrefString);
    }

    @Test
    public void freeReferencesTest03() throws IOException {
        String src = "freeRefsDeletedObj.pdf";
        String out = "freeReferencesTest03.pdf";

        PdfDocument pdfDocument = new PdfDocument(new PdfReader(sourceFolder + src), new PdfWriter(destinationFolder + out),
                new StampingProperties().useAppendMode());
        pdfDocument.addNewPage();
        pdfDocument.close();

        String[] xrefString = extractXrefTableAsStrings(out);
        String[] expected = new String[] {
                        "xref\n" +
                        "0 7\n" +
                        "0000000000 65535 f \n" +
                        "0000000265 00000 n \n" +
                        "0000000564 00000 n \n" +
                        "0000000310 00000 n \n" +
                        "0000000132 00000 n \n" +
                        "0000000015 00001 n \n" +
                        "0000000476 00000 n \n",

                        "xref\n" +
                        "0 1\n" +
                        "0000000005 65535 n \n" +
                        "3 3\n" +
                        "0000000923 00000 n \n" +
                        "0000001170 00000 n \n" +
                        "0000000000 00002 f \n" +
                        "7 1\n" +
                        "0000001303 00000 n \n",

                        "xref\n" +
                        "0 4\n" +
                        "0000000005 65535 f \n" +
                        "0000001706 00000 n \n" +
                        "0000001998 00000 n \n" +
                        "0000001751 00000 n \n" +
                        "5 1\n" +
                        "0000000000 00002 f \n" +
                        "8 2\n" +
                        "0000002055 00000 n \n" +
                        "0000002156 00000 n \n"};
        Assert.assertArrayEquals(expected, xrefString);
    }

    @Test
    public void freeReferencesTest04() throws IOException {
        String src = "simpleDoc.pdf";
        String out = "freeReferencesTest04.pdf";

        PdfDocument pdfDocument = new PdfDocument(new PdfReader(sourceFolder + src), new PdfWriter(destinationFolder + out));
        PdfObject contentsObj = pdfDocument.getPage(1).getPdfObject().remove(PdfName.Contents);
        Assert.assertTrue(contentsObj instanceof PdfIndirectReference);

        PdfIndirectReference contentsRef = (PdfIndirectReference) contentsObj;
        contentsRef.setFree();
        PdfObject freedContentsRefRefersTo = contentsRef.getRefersTo();
        Assert.assertNull(freedContentsRefRefersTo);
        pdfDocument.close();

        String[] xrefString = extractXrefTableAsStrings(out);
        String[] expected = new String[] {
                "xref\n" +
                "0 7\n" +
                "0000000005 65535 f \n" +
                "0000000133 00000 n \n" +
                "0000000425 00000 n \n" +
                "0000000178 00000 n \n" +
                "0000000015 00000 n \n" +
                "0000000000 00001 f \n" +
                "0000000476 00000 n \n"
        };
        Assert.assertArrayEquals(expected, xrefString);
    }

    @Test
    public void freeReferencesTest05() throws IOException {
        String src = "simpleDocWithSubsections.pdf";
        String out = "freeReferencesTest05.pdf";
        PdfDocument pdfDocument = new PdfDocument(new PdfReader(sourceFolder + src), new PdfWriter(destinationFolder + out));
        pdfDocument.close();

        String[] xrefString = extractXrefTableAsStrings(out);
        String[] expected = new String[] {
                "xref\n" +
                "0 14\n" +
                "0000000004 65535 f \n" +
                "0000000269 00000 n \n" +
                "0000000561 00000 n \n" +
                "0000000314 00000 n \n" +
                "0000000005 00000 f \n" +
                "0000000006 00000 f \n" +
                "0000000007 00000 f \n" +
                "0000000008 00000 f \n" +
                "0000000009 00000 f \n" +
                "0000000010 00000 f \n" +
                "0000000000 00000 f \n" +
                "0000000133 00000 n \n" +
                "0000000015 00000 n \n" +
                "0000000613 00000 n \n"
        };
        Assert.assertArrayEquals(expected, xrefString);

    }

    @Test
    public void freeReferencesTest06() throws IOException {
        String src = "simpleDocWithSubsections.pdf";
        String out = "freeReferencesTest06.pdf";
        PdfDocument pdfDocument = new PdfDocument(new PdfReader(sourceFolder + src), new PdfWriter(destinationFolder + out),
                new StampingProperties().useAppendMode());
        pdfDocument.close();

        String[] xrefString = extractXrefTableAsStrings(out);
        String[] expected = new String[] {
                        "xref\n" +
                        "0 4\n" +
                        "0000000000 65535 f \n" +
                        "0000000269 00000 n \n" +
                        "0000000569 00000 n \n" +
                        "0000000314 00000 n \n" +
                        "11 3\n" +
                        "0000000133 00000 n \n" + // Append mode, no possibility to fix subsections in first xref
                        "0000000015 00000 n \n" +
                        "0000000480 00000 n \n",

                        "xref\n" +
                        "0 1\n" +
                        "0000000004 65535 f \n" +
                        "3 8\n" +
                        "0000000935 00000 n \n" +
                        "0000000005 00000 f \n" +
                        "0000000006 00000 f \n" +
                        "0000000007 00000 f \n" +
                        "0000000008 00000 f \n" +
                        "0000000009 00000 f \n" +
                        "0000000010 00000 f \n" +
                        "0000000000 00000 f \n"
                        };
        Assert.assertArrayEquals(expected, xrefString);
    }

    @Test
    public void freeReferencesTest07() throws IOException {
        String out = "freeReferencesTest07.pdf";
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(destinationFolder + out));

        pdfDocument.createNextIndirectReference();

        pdfDocument.addNewPage();
        pdfDocument.close();

        String[] xrefString = extractXrefTableAsStrings(out);
        String[] expected = new String[] {
                        "xref\n" +
                        "0 7\n" +
                        "0000000004 65535 f \n" +
                        "0000000203 00000 n \n" +
                        "0000000414 00000 n \n" +
                        "0000000248 00000 n \n" +
                        "0000000000 00001 f \n" +
                        "0000000088 00000 n \n" +
                        "0000000015 00000 n \n"
                        };
        Assert.assertArrayEquals(expected, xrefString);
    }

    @Test
    public void freeReferencesTest08() throws IOException {
        String src = "simpleDoc.pdf";
        String out = "freeReferencesTest08.pdf";

        PdfDocument pdfDocument = new PdfDocument(new PdfReader(sourceFolder + src), new PdfWriter(destinationFolder + out),
                new StampingProperties().useAppendMode());
        PdfObject contentsObj = pdfDocument.getPage(1).getPdfObject().remove(PdfName.Contents);
        pdfDocument.getPage(1).setModified();
        Assert.assertTrue(contentsObj instanceof PdfIndirectReference);

        PdfIndirectReference contentsRef = (PdfIndirectReference) contentsObj;
        contentsRef.setFree();
        PdfObject freedContentsRefRefersTo = contentsRef.getRefersTo();
        Assert.assertNull(freedContentsRefRefersTo);
        pdfDocument.close();

        String[] xrefString = extractXrefTableAsStrings(out);
        String[] expected = new String[] {
                "xref\n" +
                "0 7\n" +
                "0000000000 65535 f \n" +
                "0000000265 00000 n \n" +
                "0000000564 00000 n \n" +
                "0000000310 00000 n \n" +
                "0000000132 00000 n \n" +
                "0000000015 00000 n \n" +
                "0000000476 00000 n \n",

                "xref\n" +
                "0 1\n" +
                "0000000005 65535 f \n" +
                "3 3\n" +
                "0000000923 00000 n \n" +
                "0000001170 00000 n \n" +
                "0000000000 00001 f \n"
        };
        Assert.assertArrayEquals(expected, xrefString);
    }

    private String[] extractXrefTableAsStrings(String out) throws IOException {
        byte[] outPdfBytes = readFile(destinationFolder + out);
        String outPdfContent = new String(outPdfBytes, StandardCharsets.US_ASCII);
        String xrefStr = "\nxref";
        String trailerStr = "trailer";
        int xrefInd = outPdfContent.indexOf(xrefStr);
        int trailerInd = outPdfContent.indexOf(trailerStr);
        int lastXrefInd = outPdfContent.lastIndexOf(xrefStr);
        List<String> xrefs = new ArrayList<>();
        while (true) {
            xrefs.add(outPdfContent.substring(xrefInd + 1, trailerInd));
            if (xrefInd == lastXrefInd) {
                break;
            }
            xrefInd = outPdfContent.indexOf(xrefStr, xrefInd + 1);
            trailerInd = outPdfContent.indexOf(trailerStr, trailerInd + 1);
        }
        return xrefs.toArray(new String[xrefs.size()]);
    }
}
