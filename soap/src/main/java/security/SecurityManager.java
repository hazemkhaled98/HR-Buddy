package security;

import com.hrbuddy.util.JwtUtil;

public class SecurityManager {

    public static void authorizeUser(String token) {
        if (!JwtUtil.validateToken(token)) {
            throw new RuntimeException("Unauthorized");
        }
    }

    public static void authorizeAdmin(String token) {
        if (!JwtUtil.isAdmin(token)) {
            throw new RuntimeException("Unauthorized");
        }
    }
}
