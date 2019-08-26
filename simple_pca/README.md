# PCA explained

This is a simple project built on Scala that explains how Principle Component Analysis works. 

**Disclaimer** This is not a medium article about PCA. 

## What is PCA?
It is one of many techniques to reduce number of dimensions in your machine learning dataset.

What PCA does is it learns, "compress", and "reorganise" in a way that the first few dimensions describe most of the data, thus leaving you the choice to discard the rest (because they're useless now).

## How PCA works?

Basically, PCA can be broken down to 3 steps:

**Step 1**: Build a covariance matrix. A covariance matrix *explains the relationship between different variables (features)*. Imagine a dataset of 2 columns e.g. X and Y. You can easily explain their relationship via scatterplot.

Obviously you cannot do that with 10,000 dimensions, so a covariance matrix is our mathematical reprensetation of these relationships.

**Step 2**: Ingest the covariance matrix and calculate an array of eigenvectors and eigenvalues. Back to the X / Y scatterplot example earlier, imagine drawing 2 lines (which will be your vectors) that covers as much data as possible (measuring the variance of the data projected onto the axes).

Remember, the vectors are to "describe" how the plot looks like, not predict Y over X like regression. Example: vector a<sup>1</sup> to a<sup>2</sup> and vector b<sup>1</sup> to b<sup>2</sup> covers all the data points on the scatterplot.

What eigenvectors and eigenvalues does is "summarise" those vectors into magical numbers, and eigenvalues are our magic numbers for the next step.

**Step 3**: Reorganise and prioritise eigenvalues that holds the most variance of the data. This step basically sorts the eigenvalues in descending order. You can project these eigenvalues on a cumulative distribution and explain *if I use the first 50 dimensions, it would hold roughly 90% of the original data.* Having to process so little data with decent amount of variance (90% of the data) is a huge win.

**Step 4**: Once we decided how many vectors to discard, we create a *projection matrix* with the vectors we kept, and process the original 10,000 features with the projection matrix to get 50 compression dimensiones.

Ta-da! Dimension reduced!!


## What this code does?
It has two files: `tools.scala` and `Main.scala`. The former has all the functions to process the calculations from Step 1 to Step 4 mentioned above, and a function `pca` to call those functions and returns a processed PCA dataset. `Main.scala` is just to execute the `pca` function.

The `pca` function also accepts `threshold` argument that indicates the total percentage of variance to retain from the raw data. Right now, we set it at `0.97` as default (retain 97% variance).


## More Reading

This project refers to some good articles about PCA.

PCA In-Depth: https://jakevdp.github.io/PythonDataScienceHandbook/05.09-principal-component-analysis.html
Python Tutorial: https://towardsdatascience.com/principal-component-analysis-your-tutorial-and-code-9719d3d3f376