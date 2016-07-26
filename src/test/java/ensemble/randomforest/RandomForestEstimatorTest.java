package ensemble.randomforest;

import org.junit.BeforeClass;
import org.junit.Test;

public class RandomForestEstimatorTest {
	private static String[] args;
	private static String inputFilePath = "src\\test\\resources\\iris.data";
	private static String outputDirPath = "testing-output";
	
	@BeforeClass
	public static void setup(){
		args = new String[] { "--input", inputFilePath, "--output", outputDirPath, "--h2o-quiet", "--ntrees", "10"};
	}
	
	@Test
	public void testMain() {
		RandomForestEstimator.main(args);
	}
}
