package au.edu.sydney.soft3202.task1;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class ShoppingBasketTest{
    ShoppingBasket sb;

    @BeforeEach
    public void setUp() {
        this.sb = new ShoppingBasket();
    }

    //Constructor specific tests
    @Test
    public void initNotNull() {
        assertNotNull(this.sb);
    }

    //addItem() tests
    @Test
    public void incorrectName() {
        assertThrows(IllegalArgumentException.class, () -> this.sb.addItem("some other fruit", 1));
    }

    @Test
    public void incorrectNumberAdd1() {
        assertThrows(IllegalArgumentException.class, () -> this.sb.addItem("apple", 0));
    }

    @Test
    public void incorrectNumberAdd2() {
        assertThrows(IllegalArgumentException.class, () -> this.sb.addItem("apple", -1));
    }

    //removeItem() tests
    @Test
    public void correctRemove() {
        this.sb.addItem("apple", 1);
        assertTrue(this.sb.removeItem("apple", 1));
    }

    @Test
    public void itemNotInBasketRemove1() {
        assertFalse(this.sb.removeItem("apple", 1));
    }

    @Test
    public void itemNotInBasketRemove2() {
        assertFalse(this.sb.removeItem("some other fruit", 1));
    }

    @Test
    public void moreThanInBasketRemove() {
        this.sb.addItem("apple", 1);
        assertFalse(this.sb.removeItem("apple", 2));
    }

    @Test
    public void incorrectNumberRemove1() {
        this.sb.addItem("apple", 1);//Don't know if it will check name or number first, so have to add first
        assertThrows(IllegalArgumentException.class, () -> this.sb.removeItem("apple", 0));
    }

    @Test
    public void incorrectNumberRemove2() {
        this.sb.addItem("apple", 1);
        assertThrows(IllegalArgumentException.class, () -> this.sb.removeItem("apple", -1));
    }

    //getItems() tests
    @Test
    public void correctGetItems() {
        this.sb.addItem("apple", 1);
        List<javafx.util.Pair<String,Integer>> items = this.sb.getItems();
        assertEquals(items.get(0).getKey(), "apple");
        assertEquals(items.get(0).getValue(), 1);
    }

    @Test
    public void correctGetItemsMulti() {
        this.sb.addItem("apple", 1);
        this.sb.addItem("apple", 1);
        List<javafx.util.Pair<String,Integer>> items = this.sb.getItems();
        assertEquals(items.get(0).getKey(), "apple");
        assertEquals(items.get(0).getValue(), 2);
    }

    @Test
    public void correctGetItemsAddRem() {
        this.sb.addItem("apple", 2);
        this.sb.removeItem("apple", 1);
        List<javafx.util.Pair<String,Integer>> items = this.sb.getItems();
        assertEquals(items.get(0).getKey(), "apple");
        assertEquals(items.get(0).getValue(), 1);
    }

    //getValue() tests
    @Test
    public void correctGetValue1() {
        this.sb.addItem("apple", 1);
        assertEquals(2.5, this.sb.getValue());
    }

    @Test
    public void correctGetValue2() {
        this.sb.addItem("orange", 1);
        assertEquals(1.25, this.sb.getValue());
    }

    @Test
    public void correctGetValue3() {
        this.sb.addItem("pear", 1);
        assertEquals(3.0, this.sb.getValue());
    }

    @Test
    public void correctGetValue4() {
        this.sb.addItem("banana", 1);
        assertEquals(4.95, this.sb.getValue());
    }

    @Test
    public void nullGetValue1() {
        assertNull(this.sb.getValue());
    }
    
    @Test
    public void getValueAddTwice() {
        this.sb.addItem("apple", 1);
        this.sb.addItem("apple", 1);
        assertEquals(5.0, this.sb.getValue());
    }
    
    @Test
    public void getValueAddRem() {
        this.sb.addItem("apple", 1);
        this.sb.removeItem("apple", 1);
        assertEquals(5.0, this.sb.getValue());
    }

    //clear() tests
    @Test
    public void correctClear() {
        this.sb.addItem("apple", 1);
        this.sb.clear();
        assertNull(this.sb.getValue());
    }
}
