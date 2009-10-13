package misc;

import java.util.Random;
import java.util.UUID;

import org.springframework.security.providers.encoding.PasswordEncoder;
import org.springframework.security.providers.encoding.ShaPasswordEncoder;

import junit.framework.TestCase;

public class PasswordEncryptionTests extends TestCase {

    public void testShaPasswordEncoder() {
        PasswordEncoder encoder = new ShaPasswordEncoder();
        String password = encoder.encodePassword("mypass", "salty");
        System.out.println("Password:");
        System.out.println(password);
        System.out.println(password.length());

    }

    public void testShaPasswordEncoder512() {
        PasswordEncoder encoder = new ShaPasswordEncoder(512);
        String password = encoder.encodePassword("mypass", "salty");
        System.out.println("Password:");
        System.out.println(password);
        System.out.println(password.length());

    }    
    
    public void testRandomForSalt() {
        Random r = new Random();
        String token = Long.toString(Math.abs(r.nextLong()), 36)
                + Long.toString(Math.abs(r.nextLong()), 36);
        System.out.println("Token:");
        System.out.println(token);
        System.out.println(token.length());

    }

    public void testRandomForSalt2() {
        UUID uuid = UUID.randomUUID();
        String token = uuid.toString();
        System.out.println("Salt2:");
        System.out.println(token);
        System.out.println(token.length());
        String finalToken = token.replace("-", "").substring(0, 24);
        System.out.println(finalToken);
        System.out.println(finalToken.length());
        
    }
    
    public void testCreatePassword() {
        UUID uuid = UUID.randomUUID();
        String token = uuid.toString();
        String hash = token.replace("-", "").substring(0, 24);
        PasswordEncoder encoder = new ShaPasswordEncoder();
        String password = encoder.encodePassword("password", hash);
        System.out.println("Password: " + password + hash);
    }

}
