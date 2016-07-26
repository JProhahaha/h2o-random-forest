package ensemble.randomforest;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.beust.jcommander.ParameterException;

public class RFUtilsTest {
	public static String[] args;

	@Test
	public void testParseRFArgs() {
		args = new String[] { "-h" };
		RFArguments cliArgs = RFUtils.parseRFArguments(args);
		assertTrue(cliArgs.help);
	}

	@Test(expected = ParameterException.class)
	public void testParemeterException() {
		RFUtils.parseRFArguments(new String[] { "h" });
	}
}
