package major_project.model;

/**
 * Interface for output API
 */
public interface CurrencyOutput {
    /** create report and return as string */
    public String createReport(String curr1Name, String curr1Code,
        String curr2Name, String curr2Code, String exRate, String curr1Val,
        String curr2Val);

    /** call to api, or not for offline, to send report */
    public boolean sendReport(String report);
}
