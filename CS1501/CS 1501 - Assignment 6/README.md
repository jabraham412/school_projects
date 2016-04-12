#Assignment 6: RSA Encryption Algorithm#

The objective of this assignment is to implement the RSA encryption algorithm
using the BigInteger class in java

to run:

1. Generate keys by typing the following in to the terminal:

java RSA <nBits>

where <nBits> is the number bits the keys consist of (ex. 8 works: anything more than that takes too long to decrypt)
this generates the public and private keys in public.txt and private.txt

2. Encrypt a file by typing the following in to the terminal:

java RSA -encrypt file.txt

this generates an encrypted file "file.txt.enc"

3. Decrypt an encrypted file by typing the following in to the terminal: 

java RSA -decrypt file.txt.enc

this decrypted the encrypted content in "file.txt.enc" and stores the original text in
"file.txt.cop"
