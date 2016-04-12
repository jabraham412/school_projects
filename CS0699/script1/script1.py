# Author: John Abraham
# Data-Centric Security
# Exercise1: Credit Card Search

import re

def main():
  output_f = open('output.txt', 'w')
  input_f = open('searchme.txt')
  total_num_matches = 0
  with input_f as fp:
    for line in fp:
      potential_matches = re.findall('[0-9]{12,16}', line)
      if potential_matches:
        # Using Luhn's algorithm/formula we can determine whether
        # each of the numbers is a valid CC number.
        for match in potential_matches:
          if is_valid(match, len(match)):
            output_f.write(match+'\n')
            total_num_matches += 1
  output_f.close()
  input_f.close()
  print 'total number of matches found:', total_num_matches
  print 'outputs are stored in the \'output.txt\' file'

def is_valid(card_number, card_len):
  # Luhn's algorithm
  # step1: reverse the number
  reversed_num = card_number[::-1]
  # step2: take the first, third, ... and every other odd digit (index)
  # in the reversed digits and sum them to form the partial sum s1
  s1 = 0
  for i in range(card_len):
    if (i%2 == 0):
      s1 += int(reversed_num[i])
  # step3: taking the second, fourth ... and every other even digit (index)
  # in the reversed digits:
  # a. multiply each digit by two and sum the digits if the answer is
  # greater than nine to form partial sums for the even digits
  # b. sum the partial sums of the even digits to form s2
  s2 = 0
  for i in range(card_len):
    if (i%2 != 0):
      doubled_digit = int(reversed_num[i])*2
      doubled_digit = str(doubled_digit)
      if len(doubled_digit) == 2:
        s2 += int(doubled_digit[0]) + int(doubled_digit[1])
      else:
        s2 += int(doubled_digit)
  # If s1 + s2 ends in zero then the original number is in the form of a valid
  # credit card number as verified by the Luhn test.
  sum = str(s1 + s2)
  if int(sum[len(sum)-1]) == 0:
    return True
  else:
    return False

if __name__ == '__main__':
  main()