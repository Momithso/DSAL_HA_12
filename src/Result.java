package src;

class Result {

    public final long maxLength;
    public final String maxCharSeq;

    public Result(long maxLength, String maxCharSeq) {
        this.maxLength = maxLength;
        this.maxCharSeq = maxCharSeq;
    }

    @Override
    public String toString() {
        return maxLength + " | " + maxCharSeq;
    }
}