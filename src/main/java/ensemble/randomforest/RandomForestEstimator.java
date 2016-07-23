package ensemble.randomforest;

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

		// TODO Instantiate H2O instance
	}
}
