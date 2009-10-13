package misc;

import java.util.UUID;

import junit.framework.TestCase;

public class TokenCreationTests extends TestCase {

	public void testCreateToken() {
        UUID uuid = UUID.randomUUID();
        String tokenWithDashes = uuid.toString();
        // We only need a 24 byte hash (without dashes), for now
        String token = tokenWithDashes.replace("-", "");
        System.out.println(token);
        System.out.println(token.length());
		assertEquals(32, token.length());
		assertFalse(token.contains("-"));
	}
	
}
