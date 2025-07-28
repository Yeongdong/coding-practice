package christmas.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MenuTest {

    @DisplayName("메뉴에 메뉴이름이 있으면 테스트를 통과한다.")
    @Test
    public void testFindByName() {
        Menu menu = Menu.findByName("초코케이크");
        assertNotNull(menu);
        assertEquals("초코케이크", menu.getName());
        assertEquals(15_000, menu.getPrice());
    }

    @DisplayName("메뉴에 메뉴이름이 없으면 테스트를 통과한다.")
    @Test
    public void testFindByNameWithInvalidName() {
        Menu menu = Menu.findByName("피자");
        assertNull(menu);
    }
}

