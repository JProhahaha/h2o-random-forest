package ensemble.randomforest;

import hex.tree.drf.DRFModel;
import hex.tree.drf.DRFModel.DRFParameters;
import water.H2O;
import water.H2OApp;
import water.fvec.Frame;

/**
 * Estimator class for Random Forest. This class instantiates an instance of
 * <code>h2o.ai</code> and builds a Random Forest model to save as a Java POJO.
 * 
 * @author JP
 */
public class RandomForestEstimator {

	public static void main(String[] args) {
		
		RFArguments cliArgs = RFUtils.parseRFArguments(args);
		// Exit program if help was used
		if (cliArgs.help)
			return;

		System.out.print("Starting h2o instance...");
		H2OApp.main(cliArgs.h2oParams.split(" "));
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
		
		dataFrame.delete();
		System.out.print("Shutting down H2O...");
		H2O.orderlyShutdown();
		System.out.println("Done!");
	}
}
