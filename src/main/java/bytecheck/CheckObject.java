package bytecheck;

import org.omg.CORBA.PRIVATE_MEMBER;

public class CheckObject {
    public static void main(String[] args) {
        UserService userService = new UserService();
        User user = userService.getUser();
        String name = user.getName();
        System.out.println(name);
    }

    public static class UserService {
        public UserDao userDao = new UserDao();

        public User getUser() {
            return userDao.getUser();
        }

        private static class UserDao {
            public User getUser() {
                return new User();
            }
        }
    }

    public static class User {
        public String getName() {
            return "Jungle";
        }
    }
}
