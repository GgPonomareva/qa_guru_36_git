package li.c;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.InvalidArgumentException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class NewZipTests {

    private final ClassLoader cl = NewZipTests.class.getClassLoader();

    String archiveName = "PlainZip.zip";


    @BeforeEach
    void setUp() throws ZipException, URISyntaxException {
        extractThem(archiveName);
    }


    private ZipEntry getFileEntryFromArchive(String archiveName, String filename) throws IOException {
        ZipEntry entry;

        try (InputStream is = cl.getResourceAsStream(archiveName);

             ZipInputStream zis = new ZipInputStream(is)) {

            while ((entry = zis.getNextEntry()) != null) {

                if (entry.getName().endsWith(filename)) return entry;

            }

            throw new InvalidArgumentException("File " + filename + " not found in the archive" + archiveName);
        }
    }

    @Test
    void zipWithPdfTestSimple() throws IOException {

        ZipEntry pdfEntry = getFileEntryFromArchive(archiveName, "junit-user-guide-5.9.2.pdf");

        assert pdfEntry.getSize() > 0;

        System.out.println(pdfEntry.getSize());
    }

    @Test
    void zipWithXlsTestSimple() throws IOException {
        ZipEntry xlsEntry = getFileEntryFromArchive(archiveName, "teachers.xls");

        assert xlsEntry.getSize() > 0;

        System.out.println(xlsEntry.getSize());
    }

    @Test
    void zipWithCsvTestSimple() throws IOException {
        ZipEntry csvEntry = getFileEntryFromArchive(archiveName, "qaguru.csv");

        assert csvEntry.getSize() > 0;

        System.out.println(csvEntry.getSize());
    }


    void extractThem(String archiveName) throws ZipException, URISyntaxException {

        URL resource = cl.getResource(archiveName);
        File zipFIle = Paths.get(resource.toURI()).toFile();
        String extractionPath = zipFIle.getParent();
        ZipFile filesZip = new ZipFile(zipFIle);

        filesZip.extractAll(extractionPath);

    }

    @Test
    void pdfParseTest() throws Exception {

        URL pdfURL = cl.getResource("junit-user-guide-5.9.2.pdf");

        System.out.println(pdfURL.toString());

        PDF pdf = new PDF(pdfURL);

        Assertions.assertEquals(
                "Stefan Bechtold, Sam Brannen, Johannes Link, Matthias Merdes, Marc Philipp, Juliette de Rancourt, Christian Stein",
                pdf.author
        );
    }

    @Test
    void xlsParseTest() throws Exception {

        URL xlsURL = cl.getResource("teachers.xls");

        System.out.println(xlsURL.toString());

        XLS xls = new XLS(xlsURL);

        Assertions.assertTrue(
                xls.excel.getSheetAt(0).getRow(3).getCell(2).getStringCellValue()
                        .startsWith("1. Суммарное количество часов планируемое на штатную по всем разделам плана")
        );
    }

    @Test
    void csvParseTest() throws IOException, CsvException {

        List<String[]> content;

        try (InputStream is = cl.getResourceAsStream("qaguru.csv");
             InputStreamReader isr = new InputStreamReader(is)) {
            CSVReader csvReader = new CSVReader(isr);
            content = csvReader.readAll();
        }

        content.forEach(e -> System.out.println(Arrays.toString(e)));

    }


    private ZipInputStream getStreamFromArchive(String archiveName, String filename) throws IOException {


        ZipEntry entry;
        ZipInputStream zis;
        InputStream is = cl.getResourceAsStream(archiveName);
        zis = new ZipInputStream(is);

        while ((entry = zis.getNextEntry()) != null) {


            if (entry.getName().endsWith(filename)) return zis;

        }


        is.close();
        zis.close();
        throw new InvalidArgumentException("ERROR: File " + filename + " was not found in the archive" + archiveName + "\n");
    }


    @Test
    void zipWithPdfStreamTest() throws IOException {

        InputStream inputStream = getStreamFromArchive(archiveName, "junit-user-guide-5.9.2.pdf");

        PDF pdf = new PDF(inputStream);

        System.out.println(pdf.author);

        Assertions.assertEquals(
                "Stefan Bechtold, Sam Brannen, Johannes Link, Matthias Merdes, Marc Philipp, Juliette de Rancourt, Christian Stein",
                pdf.author
        );

        inputStream.close();
    }

}
