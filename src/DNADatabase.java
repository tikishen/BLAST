import containers.DNASequenceInfo;
import containers.MatchedElement;
import containers.SeqLocation;

import java.util.*;

/**
 * This class contains following methods: add sequence for DNASquences;
 * add segments to hash map; extend matches; find DNA sequences matches
 *
 * @modifier Elena Shen with assistance from Eddie Dong
 * @version 11/10/2017
 */
public class DNADatabase {
    ArrayList<DNASequenceInfo> dnaSequences;
    HashMap<String, ArrayList<SeqLocation>> seqIndexes;
    int keyLength;

    // Constructor
    public DNADatabase(int keyLength) {
        dnaSequences = new ArrayList<DNASequenceInfo>();
        seqIndexes = new HashMap<String, ArrayList<SeqLocation>>();
        this.keyLength = keyLength;
    }

    /**
     * Add segments to the HashTable
     * @param seq
     * @param seqId
     */
    private void addSegmentsToHashTable(String seq, int seqId) {
        for (int startIndex = 0; startIndex <= seq.length() - keyLength; startIndex ++) {
            String key = seq.substring(startIndex, startIndex + keyLength);
            if (! seqIndexes.containsKey(key)) {  // create entry for the key
                seqIndexes.put(key, new ArrayList<SeqLocation>());
            }
            seqIndexes.get(key).add(new SeqLocation(seqId, startIndex));
        }
    }

    /**
     * Add DNA sequences
     * @param dnaSequences
     */

    public void add(List<DNASequenceInfo> dnaSequences) {
        for (int seqId = 0; seqId < dnaSequences.size(); seqId ++) {
            DNASequenceInfo info = dnaSequences.get(seqId);
            this.dnaSequences.add(info);
            addSegmentsToHashTable(info.getSeq(), seqId);
        }
    }

    /**
     * Modify the passed argument in-place.
     * @param match
     * @param query
     */
    private void extendMatch(MatchedElement match, String query) {
        int seqId = match.getLocOfMatchInDB().seqId;
        String seq = dnaSequences.get(seqId).getSeq();

        int seqStart = match.getLocOfMatchInDB().startIndex;
        int queryStart = match.getIndexOfMatchInQuery();

        for (int i = seqStart - 1, j = queryStart - 1;
             i >= 0 && j >= 0 && seq.charAt(i) == query.charAt(j); i --, j --)
            match.leftExtend();

        for (int i = seqStart + keyLength, j = queryStart + keyLength;
             i < seq.length() && j < query.length() && seq.charAt(i) == query.charAt(j);
             i ++, j ++)
            match.rightExtend();
    }

    /**
     * Add a new match into previous list of matches, do not add it if duplicated.
     * if falls within the range of a previous match, means duplicated and ignore this match;
     * else if the match encloses a previous match, expand (overwrite) that match with the previous one.
     * @param match
     * @param matches
     */
    private static void addWithoutDuplicates(MatchedElement match, List<MatchedElement> matches) {
        for (MatchedElement prevMatch : matches) {
            if (match.seqIdInDB() != prevMatch.seqIdInDB()) continue;

            if (match.startIndexInDB() >= prevMatch.startIndexInDB()
                    && match.endIndexInDB() <= prevMatch.endIndexInDB()) {
                return;
            } else if (match.startIndexInDB() <= prevMatch.startIndexInDB()
                    && match.endIndexInDB() >= prevMatch.endIndexInDB()) {
                prevMatch.expand(match.getMatchLength(), match.startIndexInDB());
                return;
            }
            // else, continue to look if there's a previous overlapping match
        }
        // checked all previous matches and did not find a duplicate
        matches.add(match);
    }

    /**
     * Find matches of a specific query from the DNA database.
     * @param query
     * @param threshold
     * @return length of matches; index of match in query; loc of match in DB
     */
    public List<MatchedElement> findMatches(String query, int threshold) {
        // create an empty list of matches
        List<MatchedElement> matches = new ArrayList<MatchedElement>();
        Set<String> usedQueryKeys = new HashSet<String>();

        for (int startIndex = 0; startIndex <= query.length() - keyLength; startIndex ++) {
            // uses all unique keys from the query, with no duplicates
            String queryKey = query.substring(startIndex, startIndex + keyLength);
            if (usedQueryKeys.contains(queryKey)) {
                continue;
            } else {
                usedQueryKeys.add(queryKey);
            }

            // if no such pattern is found in db
            if (! seqIndexes.containsKey(queryKey)) continue;

            for (SeqLocation loc : seqIndexes.get(queryKey)) {
                // find out how far matches can be extended
                MatchedElement match = new MatchedElement(keyLength, startIndex, loc);
                extendMatch(match, query);

                if (match.getMatchLength() >= threshold) {  // might already be extended
                    addWithoutDuplicates(match, matches);
                }
            }
        }
        return matches;
    }
}
