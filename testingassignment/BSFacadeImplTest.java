package au.edu.sydney.soft3202.reynholm.erp.billingsystem;

import au.edu.sydney.soft3202.reynholm.erp.cheatmodule.*;
import au.edu.sydney.soft3202.reynholm.erp.client.*;
import au.edu.sydney.soft3202.reynholm.erp.compliance.*;
import au.edu.sydney.soft3202.reynholm.erp.permissions.*;
import au.edu.sydney.soft3202.reynholm.erp.project.*;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import org.mockito.MockedStatic;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;

public class BSFacadeImplTest{
    BSFacadeImpl bsfi;
    ERPCheatFactory cheatFact;
    AuthenticationModule authenMod;
    AuthorisationModule authorMod;
    Project projectMock;

    @BeforeEach
    public void setUp(){
        this.bsfi  = new BSFacadeImpl();
        this.cheatFact = new ERPCheatFactory();
        this.authenMod = this.cheatFact.getAuthenticationModule();
        this.authorMod = this.cheatFact.getAuthorisationModule();
        this.projectMock = mock(Project.class);

    }

    //injectAuth() tests
    @Test
    public void injectAuthNullAuthen(){
        assertThrows(IllegalArgumentException.class,() -> this.bsfi.injectAuth(null, this.authorMod));
    }

    @Test
    public void injectAuthNullAuthor(){
        assertThrows(IllegalArgumentException.class,() -> this.bsfi.injectAuth(this.authenMod, null));
    }

    //login() tests
    @Test
    public void loginBasic(){
        this.bsfi.injectAuth(this.authenMod, this.authorMod);
        assertTrue(this.bsfi.login("user", "password"));
    }

    @Test
    public void loginBasicFalseName(){//Bugged implementation as of 18/3
        this.bsfi.injectAuth(this.authenMod, this.authorMod);
        assertFalse(this.bsfi.login("not-user", "password"));
    }

    @Test
    public void loginBasicFalsePwd(){//Bugged implementation as of 18/3
        this.bsfi.injectAuth(this.authenMod, this.authorMod);
        assertFalse(this.bsfi.login("user", "not-password"));
    }

    @Test
    public void loginNullName(){
        this.bsfi.injectAuth(this.authenMod, this.authorMod);
        assertThrows(IllegalArgumentException.class,() -> this.bsfi.login(null, "password"));
    }

    @Test
    public void loginNullPwd(){
        this.bsfi.injectAuth(this.authenMod, this.authorMod);
        assertThrows(IllegalArgumentException.class,() -> this.bsfi.login("user", null));
    }

    @Test
    public void loginNullBoth(){
        this.bsfi.injectAuth(this.authenMod, this.authorMod);
        assertThrows(IllegalArgumentException.class,() -> this.bsfi.login(null, null));
    }

    @Test
    public void loginNoInject(){
        assertThrows(IllegalStateException.class,() -> this.bsfi.login("user", "password"));
    }

    //logout() tests
    @Test
    public void logoutIllState1(){
        assertThrows(IllegalStateException.class,() -> this.bsfi.logout());
    }

    @Test
    public void logoutIllState2(){
        this.bsfi.injectAuth(this.authenMod, this.authorMod);
        assertThrows(IllegalStateException.class,() -> this.bsfi.logout());
    }

    @Test
    public void logoutIllState3(){
        this.bsfi.injectAuth(this.authenMod, this.authorMod);
        this.bsfi.login("user", "password");
        this.bsfi.logout();
        assertThrows(IllegalStateException.class,() -> this.bsfi.logout());
    }

    //addProject Tests
    @Test
    public void addProjectNullName(){
        this.bsfi.injectAuth(this.authenMod, this.authorMod);
        this.bsfi.login("user", "password");
        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), anyString(), anyDouble(), anyDouble()))
                    .thenReturn(this.projectMock);
            assertThrows(IllegalArgumentException.class,() -> this.bsfi.addProject(null, "client", 1.0, 2.0));
        }
    }

    @Test
    public void addProjectNullClient(){
        this.bsfi.injectAuth(this.authenMod, this.authorMod);
        this.bsfi.login("user", "password");
        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), anyString(), anyDouble(), anyDouble()))
                    .thenReturn(this.projectMock);
            assertThrows(IllegalArgumentException.class,() -> this.bsfi.addProject("name", null, 1.0, 2.0));
        }
    }

    @Test
    public void addProjectEmptyName(){
        this.bsfi.injectAuth(this.authenMod, this.authorMod);
        this.bsfi.login("user", "password");
        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), anyString(), anyDouble(), anyDouble()))
                    .thenReturn(this.projectMock);
            assertThrows(IllegalArgumentException.class,() -> this.bsfi.addProject("", "client", 1.0, 2.0));
        }
    }

    @Test
    public void addProjectEmptyClient(){
        this.bsfi.injectAuth(this.authenMod, this.authorMod);
        this.bsfi.login("user", "password");
        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), anyString(), anyDouble(), anyDouble()))
                    .thenReturn(this.projectMock);
            assertThrows(IllegalArgumentException.class,() -> this.bsfi.addProject("name", "", 1.0, 2.0));
        }
    }

    @Test
    public void addProjectLowStdRt(){
        this.bsfi.injectAuth(this.authenMod, this.authorMod);
        this.bsfi.login("user", "password");
        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), anyString(), anyDouble(), anyDouble()))
                    .thenReturn(this.projectMock);
            assertThrows(IllegalArgumentException.class,() -> this.bsfi.addProject("name", "user", 0.1, 2.0));
        }
    }

    @Test
    public void addProjectLowOvrRt(){
        this.bsfi.injectAuth(this.authenMod, this.authorMod);
        this.bsfi.login("user", "password");
        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), anyString(), anyDouble(), anyDouble()))
                    .thenReturn(this.projectMock);
            assertThrows(IllegalArgumentException.class,() -> this.bsfi.addProject("name", "user", 1.0, 0.1));
        }
    }

    @Test
    public void addProjectOvrRtUnderTen(){//Bugged implementation as of 18/3
        this.bsfi.injectAuth(this.authenMod, this.authorMod);
        this.bsfi.login("user", "password");
        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), anyString(), anyDouble(), anyDouble()))
                    .thenReturn(this.projectMock);
            assertThrows(IllegalStateException.class,() -> this.bsfi.addProject("name", "user", 10.0, 11.0));
        }
    }

    /*@Test
    public void addProjectCorrectProject(){//Sample mock test
        this.bsfi.injectAuth(this.authenMod, this.authorMod);
        this.bsfi.login("user", "password");
        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), anyString(), anyDouble(), anyDouble()))
                    .thenReturn(this.projectMock);
            Project p = this.bsfi.addProject("name", "client", 1, 50);
            assertThat(p, equalTo(projectMock));
        }
    }*/
}
