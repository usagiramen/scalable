# PCA explained

This is a simple project that explains how Principle Component Analysis works.

## What is PCA?
It is one of many techniques to reduce number of dimensions in your machine learning dataset.

*Example:*

**You**: I have a dataset of 10,000 dimensions that I'm gonna train you how to categorise pizza vs asparagus.

**Model**: That's cool, but it'll take me hours to ingest all those features and learn...

**PCA**: What if I told you, you can predict pizza vs asparagus decently with only.. *50* dimensions?

**Model**: :O

That's the gist of it. What PCA does is learn, "compress", and "reorganise" in a way that the first few dimensions describe most of the data, thus leaving you the choice to discard the rest (because they're useless now).

## How PCA works?

Basically, PCA can be broken down to 3 steps:

**Step 1**: Build a covariance matrix. A covariance matrix *explains the relationship between different variables (features)*. Imagine a dataset of 2 columns e.g. X and Y. You can easily explain their relationship via scatterplot.

Obviously you cannot do that with 10,000 dimensions, so a covariance matrix is our mathematical reprensetation of these relationships.

**Step 2**: Calculate eigenvectors and eigenvalues. Back to the X / Y scatterplot example earlier, imagine drawing 2 lines (which will be your vectors) that covers as much data as possible (measuring the variance of the data project onto the axes).

Remember, the vectors are to "describe" how the plot looks like, not predict Y over X like regression. Example: vector a<sup>1</sup> to a<sup>2</sup> and vector b<sup>1</sup> to b<sup>2</sup>.

What eigenvectors and eigenvalues does is "summarise" those vectors into a magical number, and eigenvalues is our magic number for the next step.

**Step 3**:


It accepts `threshold` argument that indicates the total percentage of variance to retain from the raw data.

Formula Reference: https://towardsdatascience.com/principal-component-analysis-your-tutorial-and-code-9719d3d3f376