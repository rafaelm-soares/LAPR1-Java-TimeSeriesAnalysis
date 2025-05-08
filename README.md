# Time Series Analysis Application

This Java-based application allows for the analysis and forecasting of time series data using fundamental statistical techniques. Designed with modular architecture, it supports both interactive and non-interactive modes of execution, enabling flexibility in data exploration and automation.

## Features

- **Time Resolution Aggregation:** Analyze time series by time of day (morning, afternoon, night, late night), daily, monthly, or yearly intervals.
- **Sorting Algorithms Comparison:** Includes Insertion Sort, Bubble Sort, and Merge Sort with performance evaluation to determine the most efficient algorithm.
- **Filtering and Smoothing:** Apply Simple Moving Average (SMA) and Exponentially Weighted Moving Average (EWMA) to remove noise and highlight trends.
- **Forecasting:** Predict future values using SMA or EWMA-based models with configurable parameters.
- **Error Evaluation:** Compute Mean Absolute Error (MAE) to assess prediction accuracy.
- **CSV File Support:** Load time series from CSV files and export results as CSV and PNG.
- **Graphical Visualization:** Generate charts using Gnuplot via the JavaPlot library.
- **Unit Testing:** All core methods are covered by automated unit tests to ensure correctness and maintainability.

## Usage

The application can be run in two modes:

### Interactive Mode

```bash
java -jar time-series.jar -nome_ts your_series.csv
