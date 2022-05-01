package major_project.model;

/**
 * Interface for output API
 */
public interface CurrencyOutput {
    public String createReport(String curr1Name, String curr1Code,
        String curr2Name, String curr2Code, String exRate, String curr1Val,
        String curr2Val);
    public boolean sendReport(String report);
}
