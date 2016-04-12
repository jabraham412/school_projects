# Author: John Abraham
# Data-Centric Security
# Exercise2: Port Scanner

# first you need to create a shodan account
# and obtain the shodan API key
# then install the python module.
# on linux: 'sudo easy_install shodan'
# it is important to keep the module up to date:
# 'sudo easy_install -U shodan'
# also do not name the script 'shodan.py' here I named it
# 'shodan_script.py' because shodan.py will conflict with the
# shodan.py python module

import shodan
from datetime import datetime

def main():
  # the first thing we need to do is to initialize the API object
  # with the API key issued when you sign up for a shodan account
  SHODAN_API_KEY = "SjGOMa1LUc5RYsU4bWAmhNkInLaxphfC"
  api = shodan.Shodan(SHODAN_API_KEY)
  # accept input from the user rather than hardcode subnet addresses into
  # the code
  remoteSubnet = raw_input("Input a subnet (example: 192.0.216.0/24): ")
  # show the date and time of the search query because we want to rerun this
  # query later and compare results
  print "-" * 50
  print 'Date & time of query: ', datetime.now().strftime("%D %I:%M:%S %p")
  print "-" * 50
  # now that we have our API object all good to go, we're ready to perform a search:
  # wrap the request in a try/excep block to catch errors
  try:
    # search shodan with the ip subnet provided by the user as the search term
    results = api.search(remoteSubnet)
    # show the results. it returns a dictionary of result information
    print 'Results found: %s\n' % results['total']
    for result in results['matches']:
      print 'IP: %s' % result['ip_str']
      print result['data']
  except shodan.APIError, e:
    print 'Error: %s' % e

if __name__ == '__main__':
  main()