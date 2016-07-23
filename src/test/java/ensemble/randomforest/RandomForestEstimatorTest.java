package ensemble.randomforest;

import org.junit.BeforeClass;
import org.junit.Test;

public class RandomForestEstimatorTest {
	public static String[] args;

	@BeforeClass
	public static void setup(){
		args = new String[] { "-h" };
	}
	
	@Test
	public void testMain() {
		RandomForestEstimator.main(args);
	}
}
