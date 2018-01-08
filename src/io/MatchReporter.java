package io;

import containers.MatchedElement;
import containers.SeqLocation;

import java.util.List;

/**
 * Created by Adward on 17/11/11.
 */
public class MatchReporter {
    public static void report(String query, List<MatchedElement> matches) {
        System.out.format("There are %d Match(es) for %s\n", matches.size(), query);
        for (MatchedElement match : matches) {
            SeqLocation loc = match.getLocOfMatchInDB();
            System.out.format("match of length %d at index %d in query, (%d, %d) in database\n",
                    match.getMatchLength(), match.getIndexOfMatchInQuery(),
                    loc.seqId, loc.startIndex);
        }
    }
}
