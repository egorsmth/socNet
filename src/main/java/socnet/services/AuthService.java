package socnet.services;

import socnet.entities.User;
import socnet.utils.Either;
import socnet.utils.Roles;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.validation.*;
import javax.validation.metadata.ConstraintDescriptor;
import java.util.*;
import java.util.logging.Logger;

@Stateless
@LocalBean
public class AuthService {
    @EJB
    UserService us;

    private ConstraintViolation<User> getUniqNameConstrainViolation(User user) {
        class UniqConst implements ConstraintViolation<User> {

            @Override
            public String getMessage() {
                return "Name should be unique";
            }

            @Override
            public String getMessageTemplate() {
                return this.getMessage();
            }

            @Override
            public User getRootBean() {
                return user;
            }

            @Override
            public Class getRootBeanClass() {
                return user.getClass();
            }

            @Override
            public Object getLeafBean() {
                return null;
            }

            @Override
            public Object[] getExecutableParameters() {
                return new Object[0];
            }

            @Override
            public Object getExecutableReturnValue() {
                return null;
            }

            @Override
            public Path getPropertyPath() {
                return null;
            }

            @Override
            public Object getInvalidValue() {
                return user.getName();
            }

            @Override
            public ConstraintDescriptor<?> getConstraintDescriptor() {
                return null;
            }

            @Override
            public Object unwrap(Class type) {
                return null;
            }
        }
        return new UniqConst();
    }

    public Optional<User> auth(String name, String pass) {
        User u = us.findOne(name, pass);

        return Optional.ofNullable(u);
    }

    public Either<User, Set<ConstraintViolation<User>>> register(String name, String pass) {
        // TODO: 8/12/2020 encrypt password
        User user = new User();
        user.setName(name);
        user.setPassword(pass);
        user.setRoles(new Roles[]{Roles.AUTH});

        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);
        if (!constraintViolations.isEmpty()) {
            return new Either<>(null, constraintViolations);
        }
        User uu = us.findOne(name, pass);
        if (uu != null) {
            constraintViolations.add(getUniqNameConstrainViolation(user));
            return new Either<>(null, constraintViolations);
        }
        User u = us.createUser(user);
        return new Either<>(u, null);
    }
}
