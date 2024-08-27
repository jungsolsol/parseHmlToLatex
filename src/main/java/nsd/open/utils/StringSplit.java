package nsd.open.utils;
public class StringSplit {
    private String extracted;
    private String remaining;

    public StringSplit(String extracted, String remaining) {
        this.extracted = extracted;
        this.remaining = remaining;
    }

    public String getExtracted() {
        return extracted;
    }

    public String getRemaining() {
        return remaining;
    }
}
