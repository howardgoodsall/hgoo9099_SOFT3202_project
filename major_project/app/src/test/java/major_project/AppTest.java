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

    @BeforeEach
    public void setUp(){
        this.apiCommMock = mock(APICaller.class);
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
    public void offInpBasicCurrConv() {
        CurrencyModelOffline model = new CurrencyModelOffline();
        assertEquals("1", model.currConversion("A","B","1"));
    }

    @Test
    public void offInpBasicConvRate() {
        CurrencyModelOffline model = new CurrencyModelOffline();
        assertEquals("1", model.calcConversionRate("1", "1"));
    }

    //Online input model tests

    @Test
    public void onInpSupportedCurr() {
        String response = "{\"response\":{\"fiats\":{\"$$$\":{\"currency_name\":\"curr_name\",\"currency_code\":\"$$$\",\"decimal_units\":\"1\",\"countries\":[\"country\"]}}}}";
        APICaller apiCallMock = mock(APICaller.class);
        when(apiCallMock.apiCommGET(anyString())).thenReturn(response);
        CurrencyModelOnline model = new CurrencyModelOnline("1234",apiCallMock);
        String[] curr = {"$$$","curr_name"};
        assertArrayEquals(model.supportedCurrencies("country").get(0), curr);
    }

    @Test
    public void onInpCurrConv() {
        String response = "{\"response\":{\"timestamp\":1000000,\"date\":\"0\",\"from\":\"$$$\",\"to\":\"$$$\",\"amount\":1,\"value\":1}}}";
        APICaller apiCallMock = mock(APICaller.class);
        when(apiCallMock.apiCommGET(anyString())).thenReturn(response);
        CurrencyModelOnline model = new CurrencyModelOnline("1234", apiCallMock);
        assertEquals(model.currConversion("$$$", "$$$", "1"), "1.000");
    }

    @Test
    public void onInpConvRate() {
        APICaller apiCallMock = mock(APICaller.class);
        CurrencyModelOnline model = new CurrencyModelOnline("1234", apiCallMock);
        assertEquals(model.calcConversionRate("1", "1"), "1.000");
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
        APICaller apiCallMock = mock(APICaller.class);
        CurrencyOutputOnline model = new CurrencyOutputOnline("", "", "1","1",
            apiCallMock);
        String expected = "a:b    Rate:1    a 1:b 1";
        assertEquals(model.createReport("a", "a","b", "b", "1", "1","1"), expected);
    }

    @Test
    public void onOutSendReport() {
        APICaller apiCallMock = mock(APICaller.class);
        when(apiCallMock.apiCommPOST(anyString(), anyString(), anyString(),
            anyString(), anyString())).thenReturn("");
        CurrencyOutputOnline model = new CurrencyOutputOnline("", "", "1","1",
            apiCallMock);
        assertTrue(model.sendReport(""));
    }

    @Test
    public void onOutSendReportNull() {
        APICaller apiCallMock = mock(APICaller.class);
        when(apiCallMock.apiCommPOST(anyString(), anyString(), anyString(),
            anyString(), anyString())).thenReturn(null);
        CurrencyOutputOnline model = new CurrencyOutputOnline("", "", "1","1",
            apiCallMock);
        assertFalse(model.sendReport(""));
    }
}
