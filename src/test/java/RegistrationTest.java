import org.junit.*;
import socnet.entities.User;
import socnet.services.AuthService;
import socnet.utils.Either;

import javax.ejb.EJB;
import javax.naming.Binding;
import javax.naming.InitialContext;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.validation.ConstraintViolation;
import java.util.Hashtable;
import java.util.Set;

public class RegistrationTest {
    private AuthService aBean;

    @Before
    public void init() throws NamingException {
        Hashtable<String, String> hashtable = new Hashtable();
        hashtable.put("java.naming.factory.initial", "org.apache.openejb.client.LocalInitialContextFactory");
        hashtable.put("log4j.category.OpenEJB.startup", "debug");
        hashtable.put("log4j.category.OpenEJB.options", "debug");
        hashtable.put("log4j.category.OpenEJB.startup.config", "debug");

        hashtable.put("openejb.deployments.classpath.exclude","");
        hashtable.put("openejb.deployments.classpath.include", ".*");


        hashtable.put("myDS","new://Resource?type=DataSource");
        hashtable.put("myDS.JdbcDriver","org.postgresql.Driver");
        hashtable.put("myDS.JdbcUrl","jdbc:postgresql://localhost:5432/testdb-test");
        hashtable.put("myDS.JtaManaged","true");
        hashtable.put("myDS.DefaultAutoCommit","true");
        hashtable.put("myDS.UserName", "admin");
        hashtable.put("myDS.Password","1234");

        InitialContext ctx = new InitialContext(hashtable);
        NamingEnumeration<Binding> list = ctx.listBindings("java:openejb/");
        while (list.hasMore()) {
            Binding item = list.next();
            System.out.println(item.getClassName() +" :: " + "java:openejb/" + item.getName());
        }
        //aBean = (AuthService) ctx.lookup("java:comp/env/AuthService");
    }

    @Test
    public void saveTest() {
        Either<User, Set<ConstraintViolation<User>>> either = aBean.register("qqqq", "wwwww");
        Assert.assertTrue(either.isLefty());
    }
}
