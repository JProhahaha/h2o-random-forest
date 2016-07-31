# H2O.ai Distributed Random Forest in Java  
This project was created for providing an example of extending H2O's machine learning Java API to build a Distributed Random Forest application that can be executed on the command line.  
This is merely a use case on building Random Forest models via the command line using pure Java rather than using H2O's native client GUI, H2O Flow.  

## Requirements
  - Gradle 2.13+
  - Java 7+

## Input File Format  
Included data file for this example is the well known [iris.data](src/test/resources/iris.data) set. 

  - CSV with Column Header
  - Target class label (Gold Standard) needs to be the last column
      + Type of label can be nominal or numerical

## Build and Run
The following are instructions for building the dependencies that you need in order to run this application via the command line.  

  - Download or Git clone repository
  - Navigate command prompt to root folder containing `build.gradle`
  - Type `gradle jar`: This will build the jar in the `/build/libs` folder
  - Type `gradle copyLibs`: This will compile all the dependencies needed into `/build/libs/runtimeLibs` folder
  - Navigate to `build/libs` folder
  - Move jar file into runtimeLibs folder
  - Type `java -cp "runtimeLibs/*" ensemble.randomforest.RandomForestEstimator -h`
      + This will display the list of required and optional command line arguments  


## References  
  - [H2O.ai](http://www.h2o.ai/) - Main website
      + [Full Documentation](http://docs.h2o.ai/h2o/latest-stable/h2o-docs/index.html) - Main Documentation for entire framework
      + [DRF](http://docs.h2o.ai/h2o/latest-stable/h2o-docs/index.html#Data%20Science%20Algorithms-DRF) - Reference to main documentation on Distributed Random Forest
      + [GitHub](https://github.com/h2oai/h2o-3) - Source code
      + [h2o-core java api](https://h2o-release.s3.amazonaws.com/h2o/rel-turin/4/docs-website/h2o-core/javadoc/index.html) - H2O Core Java Developer Documenation (Javadoc)
      + [h2o-algos](https://h2o-release.s3.amazonaws.com/h2o/rel-turin/4/docs-website/h2o-algos/javadoc/index.html) - H2O Algos Java Developer Documenation (Javadoc)
      + [h2o-genmodel](https://h2o-release.s3.amazonaws.com/h2o/rel-turin/4/docs-website/h2o-genmodel/javadoc/index.html) - H2O Generated POJO Model API Developer Documentation (Javadoc)  
  - [JCommander](http://jcommander.org/) - Main website
      + [GitHub](https://github.com/cbeust/jcommander) - Source code
      + [Google Groups](https://groups.google.com/forum/#!forum/jcommander)
  - Data set for this example was from UC Irvine Machine Learning Repository
      + [Iris Data Set](https://archive.ics.uci.edu/ml/datasets/Iris)

### Credit  
This application uses Open Source components. You can find the source code of their open source projects along with license information below. I acknowledge and am grateful to these developers for their contributions to open source.  

**h2o.ai:** https://github.com/h2oai  
**License:** [Apache License 2.0](LICENSE)  

**JCommander:** https://github.com/cbeust/jcommander  
**License:** [Apache License 2.0](LICENSE)
