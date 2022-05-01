package major_project.model;

/**
 * Online model for output API
 */
public class CurrencyOutputOnline implements CurrencyOutput {
    private final String apiKey;
    private final String apiSID;
    private final String fromNum;
    private final String toNum;
    private APICaller apiComm;

    public CurrencyOutputOnline(String apiKey, String apiSID, String fromNum,
        String toNum, APICaller apiComm){
        this.apiKey = apiKey;
        this.apiSID = apiSID;
        this.fromNum = fromNum;
        this.toNum = toNum;
        this.apiComm = apiComm;
    }

    /**
     * Create short form report
     */
    public String createReport(String curr1Name, String curr1Code,
        String curr2Name, String curr2Code, String exRate, String curr1Val,
        String curr2Val) {
        String shortReport = String.format("%s:%s    Rate:%s    %s %s:%s %s",
            curr1Name, curr2Name, exRate, curr1Code, curr1Val, curr2Code,
            curr2Val);

        return shortReport;
    }

    /**
     * Send report
     */
    public boolean sendReport(String report) {
        String uri = String.format(
            "https://api.twilio.com/2010-04-01/Accounts/%s/Messages.json",
            apiSID);
        String postAuth = String.format("%s:%s", apiSID, apiKey);
        if(this.apiComm.apiCommPOST(uri, report, postAuth, toNum, fromNum) == null) {
            System.out.println(String.format(
            "Make sure environment variables are configured correctly, To: %s, From %s",
            toNum, fromNum));
            return false;
        }
        return true;
    }
}
