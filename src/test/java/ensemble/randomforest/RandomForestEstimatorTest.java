package ensemble.randomforest;

import org.junit.BeforeClass;
import org.junit.Test;

public class RandomForestEstimatorTest {
	private static String[] args;
	private static String inputFilePath = "src\\test\\resources\\iris.data";
	private static String outputDirPath = "test-output";
	
	@BeforeClass
	public static void setup(){
		args = new String[] { "--input", inputFilePath, "--output", outputDirPath, "--h2o-quiet", "--ntrees", "10"};
	}
	
	@Test
	public void testMain() {
		// All tests within scope of this Main method are tested within RFUtilsTest
		//RandomForestEstimator.main(args);
	}
}
