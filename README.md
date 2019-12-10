# SIX DEGREES OF KEVIN BACON BY TAWNY ANDREWS

This program will read data about actors and find connections between based on which movies they've acted in. This program will utilize graphs and breadth first search algorithm.

# HOW TO USE:

To use this program, be sure all files are in the same directory and to download the CSV file from the following link to the same folder as well. (Link: https://www.kaggle.com/tmdb/tmdb-movie-metadata#)

You must provide the location of the file for this to run. So, for example, you must run the program similar to the following depending on the directory it is in:
                                      java CS245A2 /tmp/data_files/tmdb_5000_credits.csv

# RUNTIME ANALYSIS:

This program implements a breadth first search (BFS). The time complexity for BFS is O(|V| + |E|), where V is the number of vertices and E is the number of edges.

