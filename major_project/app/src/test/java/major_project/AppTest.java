package major_project;
import major_project.model.*;

import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.mockito.MockedStatic;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;

class AppTest {

    private APICaller apiCommMock;
    private CurrencyDataStore dbMock;

    @BeforeEach
    public void setUp(){
        this.apiCommMock = mock(APICaller.class);
        this.dbMock = mock(CurrencyDataStore.class);
    }

    //Offline input model tests
    @Test
    public void offInpSupportedCurr() {
         CurrencyModelOffline model = new CurrencyModelOffline();
         model.getSupportedCurrencies();
         ArrayList<String[]> suppCurrs = model.supportedCurrencies("country");
         String[] curr = {"$$$","country"};
         assertArrayEquals(suppCurrs.get(0), curr);
    }

    @Test
    public void offInpCurrConv() {
        CurrencyModelOffline model = new CurrencyModelOffline();
        assertEquals("1", model.currConversion("A","B","1"));
    }

    @Test
    public void offInpUpdateRate() {
        CurrencyModelOffline model = new CurrencyModelOffline();
        model.updateRate(1.0, "$$$","$$$");
        //Nothing to test
    }

    @Test
    public void offInpClearCache() {
        CurrencyModelOffline model = new CurrencyModelOffline();
        model.clearCache();
        //Nothing to test
    }

    @Test
    public void offInpgetExchangeRateCache() {
        CurrencyModelOffline model = new CurrencyModelOffline();
        assertNull(model.getExchangeRateCache("$$$", "$$$"));
    }

    @Test
    public void offInpcalcRate() {
        CurrencyModelOffline model = new CurrencyModelOffline();
        assertEquals(model.calcExchangeRate("1", "1"), "1.000");
    }

    @Test
    public void offInpExchRate() {
        CurrencyModelOffline model = new CurrencyModelOffline();
        assertEquals(model.getExchangeRate("$$$", "$$$"), "1.000");
    }

    @Test
    public void offInpSignUp() {
        CurrencyModelOffline model = new CurrencyModelOffline();
        assertTrue(model.signUp("u", "p"));
    }

    @Test
    public void offInpLogin() {
        CurrencyModelOffline model = new CurrencyModelOffline();
        assertEquals("white",model.login("u", "p"));
    }

    @Test
    public void offInpGetUserColour() {
        CurrencyModelOffline model = new CurrencyModelOffline();
        assertEquals("0x888888",model.getUserColour("u"));
    }

    @Test
    public void offInpUpdateColour() {
        CurrencyModelOffline model = new CurrencyModelOffline();
        model.updateColour("c","u");
    }

    @Test
    public void offInpUpdateTheme() {
        CurrencyModelOffline model = new CurrencyModelOffline();
        model.updateTheme("t", "u");
    }

    @Test
    public void offInpInsertViewCurrency() {
        CurrencyModelOffline model = new CurrencyModelOffline();
        model.insertViewCurrency("$$$", "$$$", "u");
    }

    @Test
    public void offInpRemoveViewCurrency() {
        CurrencyModelOffline model = new CurrencyModelOffline();
        model.removeViewCurrency("$$$", "$$$");
    }

    @Test
    public void offInpClearViewingTable() {
        CurrencyModelOffline model = new CurrencyModelOffline();
        model.clearViewingTable("u");
    }

    @Test
    public void offInpGetViewingCurrencies() {
        CurrencyModelOffline model = new CurrencyModelOffline();
        assertNull(model.getViewingCurrencies("u"));
    }

    //Online input model tests

    @Test
    public void onInpSupportedCurr() {
        String response = "{\"response\":{\"fiats\":{\"$$$\":{\"currency_name\":\"curr_name\",\"currency_code\":\"$$$\",\"decimal_units\":\"1\",\"countries\":[\"country\"]}}}}";
        when(this.apiCommMock.apiCommGET(anyString())).thenReturn(response);
        CurrencyModelOnline model = new CurrencyModelOnline("1234",this.apiCommMock, this.dbMock);
        String[] curr = {"$$$","curr_name"};
        assertArrayEquals(model.supportedCurrencies("country").get(0), curr);
    }

    @Test
    public void onInpCurrConv() {
        String response = "{\"response\":{\"timestamp\":1000000,\"date\":\"0\",\"from\":\"$$$\",\"to\":\"$$$\",\"amount\":1,\"value\":1}}}";
        when(this.apiCommMock.apiCommGET(anyString())).thenReturn(response);
        CurrencyModelOnline model = new CurrencyModelOnline("1234", this.apiCommMock, this.dbMock);
        assertEquals(model.currConversion("$$$", "$$$", "1"), "1.000");
    }

    @Test
    public void onInpUpdateRate() {
        CurrencyModelOnline model = new CurrencyModelOnline("1234", this.apiCommMock, this.dbMock);
        model.updateRate(1.0, "$$$","$$$");
        verify(dbMock).updateRate(1.0, "$$$","$$$");
    }

    @Test
    public void onInpCalcRate() {
        CurrencyModelOnline model = new CurrencyModelOnline("1234", this.apiCommMock, this.dbMock);
        assertEquals(model.calcExchangeRate("1", "1"), "1.000");
    }

    @Test
    public void onInpGetExchRate() {
        String response = "{\"response\":{\"date\":\"1\",\"base\":\"$$$\",\"rates\":{\"$$$\":1.000}}}";
        when(this.apiCommMock.apiCommGET(anyString())).thenReturn(response);
        CurrencyModelOnline model = new CurrencyModelOnline("1234", this.apiCommMock, this.dbMock);
        assertEquals(model.getExchangeRate("$$$", "$$$"), "1.000");
    }

    @Test
    public void onInpClearCache() {
        CurrencyModelOnline model = new CurrencyModelOnline("1234", this.apiCommMock, this.dbMock);
        model.clearCache();
        verify(dbMock).dropRatesTable();
    }

    @Test
    public void onInpSignUpExists() {
        CurrencyModelOnline model = new CurrencyModelOnline("1234", this.apiCommMock, this.dbMock);
        when(this.dbMock.searchUsers(anyString())).thenReturn(true);
        assertFalse(model.signUp("u","p"));
    }

    @Test
    public void onInpLogin() {
        CurrencyModelOnline model = new CurrencyModelOnline("1234", this.apiCommMock, this.dbMock);
        when(this.dbMock.login(anyString(), anyString())).thenReturn("a");
        assertEquals(model.login("u", "p"), "a");
    }

    @Test
    public void onInpGetUserColour() {
        CurrencyModelOnline model = new CurrencyModelOnline("1234", this.apiCommMock, this.dbMock);
        when(this.dbMock.getUserColour(anyString())).thenReturn("a");
        assertEquals(model.getUserColour("u"), "a");
    }

    @Test
    public void onInpUpdateColour() {
        CurrencyModelOnline model = new CurrencyModelOnline("1234", this.apiCommMock, this.dbMock);
        model.updateColour("c", "u");
        verify(this.dbMock).updateColour("c", "u");
    }

    @Test
    public void onInpUpdateTheme() {
        CurrencyModelOnline model = new CurrencyModelOnline("1234", this.apiCommMock, this.dbMock);
        model.updateTheme("c", "u");
        verify(this.dbMock).updateTheme("c", "u");
    }

    @Test
    public void onInpInsertViewCurrencyNull() {
        CurrencyModelOnline model = new CurrencyModelOnline("1234", this.apiCommMock, this.dbMock);
        when(this.dbMock.getViewingCurrencies(anyString())).thenReturn(null);
        model.insertViewCurrency("$$$", "$$$", "u");
        verify(this.dbMock, never()).insertViewCurrency("$$$", "$$$", "u");
    }

    @Test
    public void onInpInsertViewCurrencyNotExists() {
        CurrencyModelOnline model = new CurrencyModelOnline("1234", this.apiCommMock, this.dbMock);
        ArrayList<String[]> result = new ArrayList<String[]>();
        when(this.dbMock.getViewingCurrencies(anyString())).thenReturn(result);
        model.insertViewCurrency("$$$", "$$$", "u");
        verify(this.dbMock).insertViewCurrency("$$$", "$$$", "u");
    }

    @Test
    public void onInpInsertViewCurrencyExists() {
        CurrencyModelOnline model = new CurrencyModelOnline("1234", this.apiCommMock, this.dbMock);
        ArrayList<String[]> result = new ArrayList<String[]>();
        String[] item = {"$$$", "$$$"};
        result.add(item);
        when(this.dbMock.getViewingCurrencies(anyString())).thenReturn(result);
        model.insertViewCurrency("$$$", "$$$", "u");
        verify(this.dbMock, never()).insertViewCurrency("$$$", "$$$", "u");
    }

    @Test
    public void onInpRemoveViewCurrency() {
        CurrencyModelOnline model = new CurrencyModelOnline("1234", this.apiCommMock, this.dbMock);
        model.removeViewCurrency("$$$","u");
        verify(this.dbMock).deleteViewCurrency("$$$","u");
    }

    @Test
    public void onInpClearViewingTable() {
        CurrencyModelOnline model = new CurrencyModelOnline("1234", this.apiCommMock, this.dbMock);
        model.clearViewingTable("u");
        verify(this.dbMock).clearViewingTable("u");
    }

    @Test
    public void onInpGetViewingCurrencies() {
        CurrencyModelOnline model = new CurrencyModelOnline("1234", this.apiCommMock, this.dbMock);
        ArrayList<String[]> result = new ArrayList<String[]>();
        String[] item = {"$$$", "$$$"};
        result.add(item);
        when(this.dbMock.getViewingCurrencies(anyString())).thenReturn(result);
        assertArrayEquals(item, model.getViewingCurrencies("u").get(0));
    }


    //Offline output model tests
    @Test
    public void offOutCreateReport() {
        CurrencyOutputOffline model = new CurrencyOutputOffline();
        String expected = "a:b    Rate:1    a 1:b 1";
        assertEquals(model.createReport("a", "a","b", "b", "1", "1","1"), expected);
    }

    @Test
    public void offOutSendReport() {
        CurrencyOutputOffline model = new CurrencyOutputOffline();
        assertTrue(model.sendReport(""));
    }

    //Online output model tests
    @Test
    public void onOutCreateReport() {

        CurrencyOutputOnline model = new CurrencyOutputOnline("", "", "1","1",
            this.apiCommMock);
        String expected = "a:b    Rate:1    a 1:b 1";
        assertEquals(model.createReport("a", "a","b", "b", "1", "1","1"), expected);
    }

    @Test
    public void onOutSendReport() {

        when(this.apiCommMock.apiCommPOST(anyString(), anyString(), anyString(),
            anyString(), anyString())).thenReturn("");
        CurrencyOutputOnline model = new CurrencyOutputOnline("", "", "1","1",
            this.apiCommMock);
        assertTrue(model.sendReport(""));
    }

    @Test
    public void onOutSendReportNull() {

        when(this.apiCommMock.apiCommPOST(anyString(), anyString(), anyString(),
            anyString(), anyString())).thenReturn(null);
        CurrencyOutputOnline model = new CurrencyOutputOnline("", "", "1","1",
            this.apiCommMock);
        assertFalse(model.sendReport(""));
    }
}
