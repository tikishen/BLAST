package containers;

/**
 * This classed is used to store a match location in database
 *
 * @modifier Elena Shen with assistance from Eddie Dong
 * @version 11/10/2017
 */

public class SeqLocation {
    public int seqId;
    public int startIndex;

    public SeqLocation(int seqId, int startIndex) {
        this.seqId = seqId;
        this.startIndex = startIndex;
    }
}
