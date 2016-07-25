package ensemble.randomforest;

import com.beust.jcommander.Parameter;

/**
 * JCommander command line arguments class for Random Forest Estimator.
 * 
 * @author JP
 */
public class RFArguments {
	@Parameter(names = { "-h", "--help" }, description = "Displays Help Usage", help = true)
	public boolean help;

	@Parameter(names = { "-i", "--input" }, description = "Path to date file for training", required = true)
	public String inputFilePath;

	@Parameter(names = { "-o", "--output" }, description = "Path to output directory. (Default root)")
	public String outputDir = ".";
	
	@Parameter(names = "--h2o-help", description = "Displays help usage for h2o specific parameters.", help = true)
	public boolean h2oHelp;
	
	@Parameter(names = "--h2o-params", description = "h2o specific parameters for instantiation in a \"quoted string\".")
	public String h2oParams = "";
	
	@Parameter(names = "--h2o-quiet", description = "Used to avoid printing h2o logging to the console. (Default false)")
	public boolean h2oQuiet = false;
	
	@Parameter(names = "--ntrees", description = "Number of trees for Random Forest.", required = true)
	public int nTrees;
}
