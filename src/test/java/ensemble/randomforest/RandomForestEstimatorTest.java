package ensemble.randomforest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.beust.jcommander.ParameterException;

import hex.tree.drf.DRF;
import hex.tree.drf.DRFModel;
import hex.tree.drf.DRFModel.DRFParameters;
import water.H2O;
import water.H2OApp;
import water.Key;
import water.fvec.Frame;
import water.fvec.NFSFileVec;
import water.parser.ParseDataset;

public class RandomForestEstimatorTest {
	public static String[] args;
	private static String inputFilePath = "src\\test\\resources\\iris.data";
	private static String outputDirPath = "test-output";
	private static NFSFileVec nfs;
	private static Frame frame;
	
	@BeforeClass
	public static void setupH2O(){
		String[] h2oArgs = {"-name", "DRF", "-ga_opt_out", "yes", "-log_dir", outputDirPath + File.separatorChar + "log", "-quiet"};
		H2OApp.main(h2oArgs);
		H2O.waitForCloudSize(1, 10 * 1000 /* ms */);
		
		nfs = NFSFileVec.make(new File(inputFilePath));
		frame = ParseDataset.parse(Key.make("dataset-key"), nfs._key);
	}
	
	@AfterClass
	public static void shutdown(){
		nfs.remove();
		frame.delete();
		H2O.orderlyShutdown();
	}

	@Test
	public void testParseRFArgs() throws Exception {
		args = new String[] { "-h" };
		RFArguments cliArgs = RandomForestEstimator.parseRFArguments(args);
		assertTrue(cliArgs.help);
		
		args = new String[] { "-i", inputFilePath, "-o", outputDirPath, "--ntrees", "50", "--max-depth", "50", "--model-name", "my-model", "--nthreads", "1" };
		cliArgs = RandomForestEstimator.parseRFArguments(args);
		assertEquals(inputFilePath, cliArgs.inputFilePath);
		assertEquals(outputDirPath, cliArgs.outputDir);
		assertEquals(50, cliArgs.nTrees);
		assertEquals(50, cliArgs.maxDepth);
		assertEquals("my-model", cliArgs.modelName);
		assertEquals(1, cliArgs.nThreads);
	}

	@Test(expected = Exception.class)
	public void test_InvalidTrees() throws Exception {
		args = new String[] { "-i", inputFilePath, "-o", outputDirPath, "--ntrees", "0", "--max-depth", "50", "--model-name", "my-model" };
		RandomForestEstimator.parseRFArguments(args);
	}

	@Test(expected = Exception.class)
	public void test_InvalidMaxDepth() throws Exception {
		args = new String[] { "-i", inputFilePath, "-o", outputDirPath, "--ntrees", "50", "--max-depth", "0", "--model-name", "my-model" };
		RandomForestEstimator.parseRFArguments(args);
	}
	
	@Test(expected = Exception.class)
	public void test_InvalidModelName() throws Exception {
		args = new String[] { "-i", inputFilePath, "-o", outputDirPath, "--ntrees", "50", "--max-depth", "50", "--model-name", "" };
		RandomForestEstimator.parseRFArguments(args);
	}

	@Test(expected = ParameterException.class)
	public void testParemeterException() throws Exception {
		RandomForestEstimator.parseRFArguments(new String[] { "h" });
	}
	
	@Test
	public void testLoadDataFrame() {
		Frame frame = RandomForestEstimator.loadDataFrame(inputFilePath);
		assertEquals("dataset-key", frame._key.toString());
		assertEquals(5, frame.numCols());
		assertEquals(150, frame.numRows());
		assertEquals("sepal_length", frame._names[0]);
		assertEquals("sepal_width", frame._names[1]);
		assertEquals("petal_length", frame._names[2]);
		assertEquals("petal_width", frame._names[3]);
		assertEquals("class", frame.lastVecName());
	}
	
	@Test
	public void testCreateDRFParams() {
		RFArguments args = new RFArguments();
		args.nTrees = 50;
		args.maxDepth = 10;
		
		DRFParameters params = RandomForestEstimator.createDRFParams(args, frame);
		assertEquals("dataset-key", params._train.toString());
		assertEquals("class", params._response_column);
		assertEquals(50, params._ntrees);
		assertEquals(10, params._max_depth);
	}
	
	@Test
	public void testTrainModel() {
		DRFParameters params = new DRFParameters();
		params._train = frame._key;
		params._response_column = frame.lastVecName(); 
		params._ntrees = 1;
		params._max_depth = 5;
		
		DRFModel model = RandomForestEstimator.trainModel(params, "my-model");
		assertEquals("my-model", model._key.toString());
		assertEquals(1, model._parms._ntrees);
		assertEquals(5, model._parms._max_depth);
		assertEquals("class", model._parms._response_column);
	}
	
	@Test
	public void testSaveModel() {
		DRFParameters params = new DRFParameters();
		params._train = frame._key;
		params._response_column = frame.lastVecName(); 
		params._ntrees = 1;
		params._max_depth = 5;
		
		DRFModel model = RandomForestEstimator.trainModel(params, "my-model");
		
		RandomForestEstimator.saveModel(model, outputDirPath, "my-model");
		
		File file = new File(outputDirPath + File.separatorChar + "my-model.model");
		assertTrue(file.isFile());
	}
	
	@Test
	public void testSaveTrainingMetrics() throws IOException {
		DRFParameters params = new DRFParameters();
		params._train = frame._key;
		params._response_column = frame.lastVecName(); 
		params._ntrees = 1;
		params._max_depth = 5;
		
		DRF job = new DRF(params, Key.<DRFModel>make("my-model"));
		DRFModel model = job.trainModel().get();
		
		RandomForestEstimator.saveTrainingMetrics(model, outputDirPath);
		File file = new File(outputDirPath + File.separatorChar + "training-metrics.txt");
		assertTrue(file.isFile());
	}
}
