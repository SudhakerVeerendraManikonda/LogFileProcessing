import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

/**
 * Class to implement processing of Log Files
 * Author: Sudhaker V Manikonda
 * Date: 12-31-2015
 */
public class LogFileProcessing {

    /**
     * Function listAllFilesInAFolder will accept a folder and return a treeset of file names
     * in the folder. We are returning a treeset because it will sort the file names
     * @param folder
     * @return fileNames
     */
    public TreeSet<String> listAllFilesInAFolder(final File folder) {

        TreeSet <String>fileNames = new TreeSet<String>();
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listAllFilesInAFolder(fileEntry);
            }
            else if(fileEntry.getName().charAt(0)==('.'))
                continue;
            else
            {
                fileNames.add(folder + "/" + fileEntry.getName());
            }
        }
        return fileNames;
    }

    /**
     * Function appendLineNumbers will accept a Treeset of filenames and return the count of total number
     * of lines in all the files.
     * Initially this function had a return type of void but for testing purposes its return type was changed to int.
     * @param fileNames
     * @return numberOfLinesAppendedTotal-1 - total number of lines in all files
     */
    public int appendLineNumbers(TreeSet <String>fileNames) {
        int numberOfLinesAppendedTotal = 1;
        Iterator<String> iterator;
        iterator = fileNames.iterator();
        List<String> fileContent = new ArrayList<String>();

        while (iterator.hasNext()) {
            String name = iterator.next();
            fileContent = appendLineNumbersToEachFileContent(name, numberOfLinesAppendedTotal);
            numberOfLinesAppendedTotal += fileContent.size();
            writeToFile(name,fileContent);
        }
        return numberOfLinesAppendedTotal-1;
    }

    /**
     * Function appendLineNumbersToEachFileContent will accept a file name as String and the count for line number and
     * return a ArrayList of Strings with the content of the file with line numbers before each line.
     * This method is private as it is being called from another method there is no reason to expose this method.
     * @param name
     * @param i
     * @return fileContent - Content of the file with line numbers
     */
    private List<String> appendLineNumbersToEachFileContent(String name, int i) {
        List<String> fileContent = new ArrayList<String>();
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(name);

            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String strLine;
            while ((strLine = bufferedReader.readLine()) != null) {
                fileContent.add(Integer.toString(i) + strLine);
                i++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileContent;
    }

    /**
     * This function will write the content that is achieved in function appendLineNumbersToEachFileContent to the file.
     * This method is private as it is being called from another method there is no reason to expose this method.
     * @param name
     * @param fileContent
     */
    private void writeToFile(String name, List<String> fileContent)
    {
        try {

            FileWriter fileWriter = new FileWriter(name);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (String temp : fileContent) {
                bufferedWriter.append(temp + "\n");
            }
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Main Method to run the code without test cases.
     * However this is being commented out as it requires a path to the folder
     * where files reside.
     * @param args
     *//*
    public static void main(String [] args)
    {
      LogFileProcessing lfp = new LogFileProcessing();
      final File folder = new File("/Users/veeru/LogFiles");
      lfp.appendLineNumbers(lfp.listAllFilesInAFolder(folder));
    }*/
}
