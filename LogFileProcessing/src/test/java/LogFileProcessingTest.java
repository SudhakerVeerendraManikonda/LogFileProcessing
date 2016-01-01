
import jdk.jfr.events.FileReadEvent;
import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import static org.junit.Assert.*;

/**
 * Test Class for LogFileProcessingClass
 * Author: Sudhaker V Manikonda
 * Date: 12-31-2015
 */
public class LogFileProcessingTest {
    List<String> fileNames;
    List<String> fileContents;
    File directory, emptyDirectory;
    File file;
    LogFileProcessing lpf;

    /**
     * Creating an empty directory and a normal directory and
     * Creating files, writing content to those files and adding files to those directories.
     */
    @Before
    public void setUp() {
        lpf = new LogFileProcessing();

        fileNames = new ArrayList<String>();

        // add 4 different values to list
        fileNames.add("src/test/resources/Directory/log_2015_06_04.log");
        fileNames.add("src/test/resources/Directory/log_2015_06_05.log");
        fileNames.add("src/test/resources/Directory/log_2015_06_06.log");
        fileNames.add("src/test/resources/Directory/log_2015_06_07.log");

        fileContents = new ArrayList<String>();

        fileContents.add("qwerty");
        fileContents.add("zaqxsw");
        fileContents.add("rfvbgt");
        fileContents.add("tgbvfr");
        fileContents.add("ikmjun");

        emptyDirectory = new File("src/test/resources/EDirectory");
        emptyDirectory.mkdir();

        directory = new File("src/test/resources/Directory");
        directory.mkdir();

        for (String temp : fileNames) {
            file = new File(temp);
            try {
                file.createNewFile();
                FileWriter fileWriter = new FileWriter(file);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                for(String content: fileContents) {
                    bufferedWriter.append(content + "\n");
                }
                bufferedWriter.flush();
                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Deleting the files and directories after each test.
     */
    @After
    public void tearDown() {
        for (String temp : fileNames) {
            file = new File(temp);
            file.delete();
        }
        directory.delete();
        emptyDirectory.delete();
    }

    /**
     * Initial test to check the environment and setup is working fine.
     */
    @Test
    public void testCanary()
    {
        assertTrue(true);
    }

    /**
     * This test will check to see if all the filesnames in the folder are loaded
     * correctly into the treeset.
     */
    @Test
    public void testListAllFilesInAFolder()
    {
        TreeSet<String> listOfFileNames = new TreeSet<String>();
        listOfFileNames.add("src/test/resources/Directory/log_2015_06_04.log");
        listOfFileNames.add("src/test/resources/Directory/log_2015_06_05.log");
        listOfFileNames.add("src/test/resources/Directory/log_2015_06_06.log");
        listOfFileNames.add("src/test/resources/Directory/log_2015_06_07.log");
        assertThat(listOfFileNames,is(lpf.listAllFilesInAFolder(directory)));
    }

    /**
     * This test will check if nothing is loaded into the treeset for an empty directory.
     */
    @Test
    public void testListAllFilesInAnEmptyFolder()
    {
        TreeSet<String> listOfFileNamesEmpty = new TreeSet<String>();
        assertThat(listOfFileNamesEmpty,is(lpf.listAllFilesInAFolder(emptyDirectory)));
    }

    /**
     * This test will count the total number of lines in all the files.
     * This will ensure that the underlying private methods have been called as well.
     */
    @Test
    public void testNumberOfLinesAppendedInTotal()
    {
        TreeSet<String> listOfFileNames = new TreeSet<String>();
        listOfFileNames.add("src/test/resources/Directory/log_2015_06_04.log");
        listOfFileNames.add("src/test/resources/Directory/log_2015_06_05.log");
        listOfFileNames.add("src/test/resources/Directory/log_2015_06_06.log");
        listOfFileNames.add("src/test/resources/Directory/log_2015_06_07.log");
        assertEquals(20,lpf.appendLineNumbers(listOfFileNames));
    }

    /**
     * This test will count the total number of lines in all three files.
     * This will ensure that the underlying private methods have been called as well.
     */
    @Test
    public void testNumberOfLinesAppendedInTotalForThreeFiles()
    {
        TreeSet<String> listOfFileNames = new TreeSet<String>();
        listOfFileNames.add("src/test/resources/Directory/log_2015_06_04.log");
        listOfFileNames.add("src/test/resources/Directory/log_2015_06_05.log");
        listOfFileNames.add("src/test/resources/Directory/log_2015_06_06.log");
        assertEquals(15,lpf.appendLineNumbers(listOfFileNames));
    }
}
