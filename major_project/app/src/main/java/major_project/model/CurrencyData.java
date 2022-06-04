package major_project.model;

/**
 * A POJO class for getting currency data from input API
 */
public class CurrencyData {
    private String currency_name;
    private String[] countries;
    private String decimal_units;
    private String currency_code;

    public CurrencyData(String currency_name, String[] countries,
        String decimal_units, String currency_code) {
            this.currency_name = currency_name;
            this.countries = countries;
            this.decimal_units = decimal_units;
            this.currency_code = currency_code;
    }

    /** return currency name */
    public String getCurrencyName() {
        return this.currency_name;
    }

    /** return countries for this currency*/
    public String[] getCountries() {
        return this.countries;
    }

    /** return decimal units (not used)*/
    public String getDecimalUnits() {
        return this.decimal_units;
    }

    /** return currency code */
    public String getCurrencyCode() {
        return this.currency_code;
    }
}
