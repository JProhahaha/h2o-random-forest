package ensemble.randomforest;

import com.beust.jcommander.ParameterException;

import hex.tree.drf.DRFModel;
import hex.tree.drf.DRFModel.DRFParameters;
import water.H2O;
import water.H2OApp;
import water.fvec.Frame;

/**
 * Estimator class for Random Forest. This class instantiates an instance of
 * <code>h2o.ai</code> and builds a Random Forest model to save as a POJO.
 * 
 * @author JP
 */
public class RandomForestEstimator {

	public static void main(String[] args) {
		
		RFArguments cliArgs;
		try {
			cliArgs = RFUtils.parseRFArguments(args);
			// Exit program if help was requested
			if (cliArgs.help)
				System.exit(0);
			
			System.out.print("Starting h2o instance...");
			H2OApp.main(cliArgs.h2oParams.split(" "));
			H2O.waitForCloudSize(1, 10 * 1000 /* ms */);
			System.out.println("Done!");
			
			System.out.print("Loading data...");
			Frame dataFrame = RFUtils.loadDataFrame(cliArgs.inputFilePath);
			System.out.println("Done!");
			
			System.out.print("Creating DRF Parameters...");
			DRFParameters drfParams = RFUtils.createDRFParams(cliArgs, dataFrame);
			System.out.println("Done!");
			
			System.out.print("Training DRF Model...");
			DRFModel model = RFUtils.trainModel(drfParams, cliArgs.modelName);
			System.out.println("Done!");
			
			System.out.print("Saving model to output file...");
			RFUtils.saveModel(model, cliArgs.outputDir, cliArgs.modelName);
			System.out.println("Done!");
			
			System.out.print("Saving training metrics to output file...");
			RFUtils.saveTrainingMetrics(model, cliArgs.outputDir);
			System.out.println("Done!");
		} catch (ParameterException pe) {
			System.out.println(pe.getLocalizedMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			H2O.orderlyShutdown();
		}
		System.out.println("Complete!");
	}
}
