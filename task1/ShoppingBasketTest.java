Spackage task1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class ShoppingBasketTest{
    ShoppingBasket sb;

    @BeforeEach
    public void setUp(){
        this.sb = new ShoppingBasket();
    }

    //Constructor specific tests
    @Test
    public void initNotNull(){
        assertNotNull(this.sb);
    }

    //addItem() tests
    @Test
    public void incorrectName(){
        assertThrows(IllegalArgumentException.class, () -> this.sb.addItem("some other fruit", 1));
    }

    @Test
    public void incorrectNumberAdd1(){
        assertThrows(IllegalArgumentException.class, () -> this.sb.addItem("apple", 0));
    }

    @Test
    public void incorrectNumberAdd2(){
        assertThrows(IllegalArgumentException.class, () -> this.sb.addItem("apple", -1));
    }

    //removeItem() tests
    @Test
    public void correctRemove(){
        this.sb.addItem("apple", 1);
        assertTrue(this.sb.removeItem("apple", 1));
    }

    @Test
    public void itemNotInBasketRemove(){
        assertFalse(this.sb.removeItem("apple", 1));
    }

    @Test
    public void itemNotInBasketRemove(){
        assertFalse(this.sb.removeItem("some other fruit", 1));
    }

    @Test
    public void moreThanInBasketRemove(){
        this.sb.addItem("apple", 1);
        assertFalse(this.sb.removeItem("apple", 2));
    }

    @Test
    public void incorrectNumberRemove1(){
        this.sb.addItem("apple", 1);//Don't know if it will check name or number first, so have to add first
        assertThrows(IllegalArgumentException.class, () -> this.sb.removeItem("apple", 0));
    }

    @Test
    public void incorrectNumberRemove2(){
        this.sb.addItem("apple", 1);
        assertThrows(IllegalArgumentException.class, () -> this.sb.removeItem("apple", -1));
    }

    //getItems() tests
    @Test
    public void correctGetItems(){
        this.sb.addItem("apple", 1);
        List<javafx.util.Pair<String,Integer>> items = this.sb.getItems();
        assertEquals(items[0].getKey(), "apple");
        assertEquals(items[0].getValue(), 1);
    }
}
