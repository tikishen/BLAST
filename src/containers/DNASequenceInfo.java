package containers;

/**
 * This class is used to store DNA sequences information
 *
 * @modifier Elena Shen with assistance from Eddie Dong
 * @version 11/11/2017
 */

public class DNASequenceInfo {
    String gi;
    String desc;
    String seq;

    public DNASequenceInfo(String gi, String desc, String seq) {
        this.gi = gi;
        this.desc = desc;
        this.seq = seq;
    }

    /**
     * get DNA sequence name
     * @return gi
     */
    public String getGi() { return gi; }

    /**
     * get DNA sequence description
     * @return desc
     */
    public String getDesc() { return desc; }

    /**
     * get DNA sequence string
     * @return seq
     */
    public String getSeq() { return seq; }
}
