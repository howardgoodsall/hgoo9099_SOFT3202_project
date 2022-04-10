package task2;

public class Loans {
    private int amount;
    private boolean collateralRequired;
    private int rate;
    private int termInDays;
    private String type;
    private String due;
    private String id;
    private int repaymentAmount;
    private String status;

    public Loans(int amount, boolean collateralRequired, int rate, int termInDays, String type) {
        this.amount = amount;
        this.collateralRequired = collateralRequired;
        this.rate = rate;
        this.termInDays = termInDays;
        this.type = type;
    }

    public Loans(String due, String id, int repaymentAmount, String status, String type) {
        this.due = due;
        this.id = id;
        this.repaymentAmount = repaymentAmount;
        this.status = status;
        this.type = type;
    }

    public int getAmount() {
        return this.amount;
    }

    public boolean getCollateralRequired() {
        return this.collateralRequired;
    }

    public int getRate() {
        return this.rate;
    }

    public int getTermInDays() {
        return this.termInDays;
    }

    public String getType() {
        return this.type;
    }

    public String getDue() {
        return this.due;
    }

    public String getId() {
        return this.id;
    }

    public int getRepaymentAmount() {
        return this.repaymentAmount;
    }

    public String getStatus() {
        return this.status;
    }
}
