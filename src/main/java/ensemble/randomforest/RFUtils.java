package ensemble.randomforest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;

import hex.tree.drf.DRF;
import hex.tree.drf.DRFModel;
import hex.tree.drf.DRFModel.DRFParameters;
import water.H2OApp;
import water.Key;
import water.fvec.Frame;
import water.fvec.NFSFileVec;
import water.parser.ParseDataset;

/**
 * A collection of static helper methods for <code>RandomForestEstimator</code>.
 * 
 * @see ensemble.randomforest.RandomForestEstimator RandomForestEstimator
 * @author JP
 */
public class RFUtils {

	/**
	 * Takes command line arguments and parses them to an instance of
	 * <code>RFArguments</code>.
	 * 
	 * @param args
	 *            <code>String</code> array of arguments from the command line
	 * @return instance of <code>RFArguments</code>
	 */
	public static RFArguments parseRFArguments(String[] args) {
		RFArguments cliArgs = new RFArguments();

		try {
			JCommander jc = new JCommander(cliArgs, args);
			if (cliArgs.help) {
				jc.usage();
				return cliArgs;
			} else if (cliArgs.h2oHelp) {
				// H2O will exit upon displaying usage
				H2OApp.main(new String[] { "-h" });
			}
			
			if(cliArgs.nTrees <= 0){
				// TODO: Error handling for number of trees
			}
			
			if(cliArgs.modelName.isEmpty()){
				// TODO: Error handling for empty string
			}
			
			// Create h2o instance parameters
			cliArgs.h2oParams = "-name DRF " // Default name for h2o instance
					+ "-ga_opt_out yes " // opts out of using Google Analytics embedded in H2O
					+ "-log_dir " + cliArgs.outputDir + File.separatorChar + "log " // log directory
					+ (cliArgs.h2oQuiet ? "-quiet " : "") // Quiet mode for h2o console printing
					+ cliArgs.h2oParams; // Rest of user submitted params
		} catch (ParameterException pe) {
			throw new ParameterException(pe);
		}
		return cliArgs;
	}

	/**
	 * Loads the data file into memory and parses into a <code>Frame</code>.
	 * 
	 * @param inputFilePath
	 *            path to data file
	 * @return <code>Frame</code> of parsed data
	 */
	public static Frame loadDataFrame(String inputFilePath) {
		NFSFileVec nfs = NFSFileVec.make(new File(inputFilePath));
		return ParseDataset.parse(Key.make("iris-data"), nfs._key);
	}

	/**
	 * Create all the parameters that will be used for the Random Forest.
	 * 
	 * @param cliArgs
	 *            command line arguments
	 * @param df
	 *            <code>Frame</code> of the data set
	 * @return <code>DRFParameters</code> params for Random Forest
	 */
	public static DRFParameters createDRFParams(RFArguments cliArgs, Frame df) {
		DRFParameters params = new DRFParameters();
		params._train = df._key;
		params._response_column = df.lastVecName();
		params._ntrees = cliArgs.nTrees;

		return params;
	}
	
	/**
	 * Loads parameters and trains Random Forest model. This method creates a
	 * block until the model is finished training.
	 * 
	 * @param params
	 *            <code>DRFParameter</code> for Random Forest
	 * @return <code>DRFModel</code> of trained Random Forest model
	 */
	public static DRFModel trainModel(DRFParameters params, String modelName) {
		DRF job = new DRF(params, Key.<DRFModel>make(modelName));
		return job.trainModel().get();
	}
	
	/**
	 * Saves Random Forest model to output file in the form of a POJO.
	 * 
	 * @param model
	 *            trained Random Forest model
	 * @param outputDir
	 *            path to output directory
	 * @param modelName
	 *            name for model output file
	 */
	public static void saveModel(DRFModel model, String outputDir, String modelName) {
		String modelFilePath = outputDir + File.separatorChar + modelName + ".model";
		try {
			FileOutputStream outputFile = new FileOutputStream(modelFilePath);
			model.toJava(outputFile, false, true);
			outputFile.close();
		} catch (IOException e) {
			System.err.println("Error writing model file: " + e.getMessage());
		}
	}
}
