package major_project.model;

public class CurrencyOutputOffline implements CurrencyOutput {
    //Offline model for output
    public CurrencyOutputOffline(){
        return;
    }

    //Create String of short form report
    public String createReport(String curr1Name, String curr1Code,
        String curr2Name, String curr2Code, String exRate, String curr1Val,
        String curr2Val) {
        String shortReport = String.format("%s:%s    Rate:%s    %s %s:%s %s",
            curr1Name, curr2Name, exRate, curr1Code, curr1Val, curr2Code,
            curr2Val);
        return shortReport;
    }


    //Do nothing
    public boolean sendReport(String report) {
        System.out.println(report);
        return true;
    }
}
