package containers;

/**
 * This class uses to get information of matched elements
 *
 * @modifier Elena Shen with assistance from Eddie Dong
 * @version 11/11/2017
 */

public class MatchedElement {
    int matchLength;
    int indexOfMatchInQuery;
    SeqLocation locOfMatchInDB;

    public MatchedElement(int matchLength, int indexOfMatchInQuery, SeqLocation locOfMatchInDB) {
        this.matchLength = matchLength;
        this.indexOfMatchInQuery = indexOfMatchInQuery;
        this.locOfMatchInDB = locOfMatchInDB;
    }

    /**
     * visit length of match
     * @return matchLength
     */
    public int getMatchLength() { return matchLength; }

    /**
     * visit index of match in query
     * @return indexOfMatchInQuery
     */
    public int getIndexOfMatchInQuery() { return indexOfMatchInQuery; }

    /**
     * visit location of match in DB
     * @return  locOfMatchInDB
     */
    public SeqLocation getLocOfMatchInDB() { return locOfMatchInDB; }

    /**
     * visit the start index of DB
     * @return location of the start of match index in DB
     */
    public int startIndexInDB() { return locOfMatchInDB.startIndex; }

    /**
     * visit the end index of DB
     * @return location of the END of match index in DB
     */
    public int endIndexInDB() { return locOfMatchInDB.startIndex + matchLength; }

    /**
     * visit the sequence ID of DB
     * @return location of the sequence ID of match index in DB
     */
    public int seqIdInDB() { return locOfMatchInDB.seqId; }

    /**
     * Extend the 11 letters in the left directions to make a longer sequence
     * that achieves a score higher than some prescribed cutoff score
     */
    public void leftExtend() {
        matchLength += 1;
        indexOfMatchInQuery -= 1;
        locOfMatchInDB.startIndex -= 1;
    }
    /**
     * Extend the right side
     */
    public void rightExtend() {
        matchLength += 1;
    }

    /**
     * Overwrite the very MatchedElement object with a wider range
     * when we find a broader match in db.
     * @param matchLength
     * @param startIndex
     */
    public void expand(int matchLength, int startIndex) {
        this.matchLength = matchLength;
        this.locOfMatchInDB.startIndex = startIndex;
    }
}
