import containers.MatchedElement;
import io.*;

import java.util.List;

/**
 * This class gets the DNA data and the query data from the data readers.
 * It has a run method that iterates through each query and displays any matches
 * with substrings of the DNA data that are longer than a given threshold
 *
 * @modifier Elena Shen with assistance from Eddie Dong
 * @version 11/11/2017
 */

public class BLASTApp {
    final static int defaultThreshold = 40;

    public static void run(int keyLength, String dataPath, int numLinesNucleoInfo,
                           String queriesPath) {
        DNADatabase db = new DNADatabase(keyLength);

        // read in DNASequence database
        DNADataReader dnaDataReader = new DNADataReader(dataPath, numLinesNucleoInfo);
        db.add(dnaDataReader.readData());

        // get threshold
        int threshold = ValidatedInputReader.getInteger(
                "Please input the threshold for shortest length of valid matched patterns:",
                defaultThreshold, "Pppppls!");

        // get queries
        QueryReader queryReader = new QueryReader(queriesPath);
        List<String> queries = queryReader.readData();

        // find matches & report
        for (String query : queries) {
            List<MatchedElement> matches = db.findMatches(query, threshold);
            MatchReporter.report(query, matches);
        }
    }
}
