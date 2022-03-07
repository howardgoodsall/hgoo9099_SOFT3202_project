package au.edu.sydney.soft3202.task1;
import org.junit.jupiter.api.Assertions.assertThrows;

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
    public void incorrectNumber1(){
        assertThrows(IllegalArgumentException.class, () -> this.sb.addItem("apple", 0));
    }

    @Test
    public void incorrectNumber2(){
        assertThrows(IllegalArgumentException.class, () -> this.sb.addItem("apple", -1));
    }

    //removeItem() tests
    @Test
    public void itemNotFound(){
        assertEquals(False, () -> this.sb.removeItem("apple", 1));
    }

    @Test
    public void incorrectNumber2(){
        this.sb.addItem("apple", 1);
        assertThrows(IllegalArgumentException.class, () -> this.sb.removeItem("apple", 1));
    }
}
