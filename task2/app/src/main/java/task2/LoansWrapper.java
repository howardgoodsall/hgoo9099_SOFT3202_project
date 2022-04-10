package task2;

import java.util.List;

public class LoansWrapper {
    private List<Loans> loans;
    private int credits = 0;

    public LoansWrapper(List<Loans> loans) {
        this.loans = loans;
    }

    public LoansWrapper(int credits, List<Loans> loans) {
        this.credits = credits;
        this.loans = loans;
    }

    public int getCredits() {
        return this.credits;
    }

    public List<Loans> getLoans() {
        return this.loans;
    }
}
