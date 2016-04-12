#Assignment 4: Improving the Huffman Compression Algorithm#

The objective of this assignment is to implement the Burrows Wheeler and
Move-to-Front transform algorithms to rearrange characters in text
to make their compression using Huffman compression more efficient

program is run using pipelining. "-" is used for encoding, and "+" is used for decoding. Typing the following into the terminal would encode the contents of abra.txt and output the encoded text to the terminal:

java BurrowsWheeler - < abra.txt | java MoveToFront - | java Huffman -

and typing the following into the terminal would encode the contents of abra.txt then decodes the encoded text and output the original contents of abra.txt

java BurrowsWheeler - < abra.txt | java MoveToFront - | java Huffman - | java Huffman + | java MoveToFront + | java BurrowsWheeler +
