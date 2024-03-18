package com.bandrango.store.main;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class ApplicationTest {

	@Test
	public final void testMain() {
		try {
			Application.main(new String[] {" "});
		} catch (Exception e) {
			fail("Error in testMain: " + e.getMessage());
		}
		
	}
	
}
