# RangeTree
Java Implementation of 2D Range Tree based on this [slide](https://github.com/DanialDMQ/RangeTree/blob/main/slides5b.pdf).

This is not the most efficient one but can be extended to higher dimensions easily.

PS: The code is not clean, thanks to the proper time of the deadline. (yes, this is the final project of Data Structures course)

PS: The construction of y-tree is [lazy](https://en.wikipedia.org/wiki/Lazy_evaluation). (the original RangeTree is not like this. I did it as a trick to pass all of the test cases =)) ) 

# How to use

Run Main.java

Input:

Number of points

x of the points

y of the points

Number of queries

Each query in a separate line in the form: lower_x lower_y upper_x upper_y



Output:

For each query there is an output in the form: If there is no point in that region, it prints "None".

If there is, like input: 

x of points

y of points

where y ordered ascending



Example: (points: (1, 1), (2, 2), (3, 3))

Input:

3

1 2 3

1 2 3

1

0 0 4 4

output:

1 2 3

1 2 3
