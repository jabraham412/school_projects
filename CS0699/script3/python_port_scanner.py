# Author: John Abraham
# Data-Centric Security
# Exercise2: Port Scanner

import socket
import sys
from datetime import datetime

def main():
  # take in user input as a host url or ip address
  remoteServerUserInput = raw_input("Enter a remote host to scan: ")
  scan(remoteServerUserInput)

def scan(remoteServer):
  print "-" * 50
  print 'Target website:', remoteServer
  print "Remote host IP address:", socket.gethostbyname(remoteServer)
  print 'Scanning ports 1 - 1024...'
  print "-" * 50
  print 'PORT \t STATE \t SERVICE'
  # log computer time before scan starts
  t0 = datetime.now()
  # scanning ports 1 - 2^16 takes a very long time
  # so we will only be scanning ports 1 - 1024.
  # these are the most well-known ports.
  for port in range(1,1024):
    try:
      # create a new socket on every iteration of the loop for every port.
      temp_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
      # set a time out time limit because if an open port is discovered the
      # program will freeze on that port. all we want to know is whether it
      # is open or closed and move on.
      temp_socket.settimeout(0.05)
      # connect_ex is like connect(address), but returns an error indicator instead
      # of raising an exception for errors returned by the C-level connect() call.
      # if return value is 0 then the port is open, and we only care about open/listening
      # ports so we ignore all other return values such as 11, 111, ... etc.
      result = temp_socket.connect_ex((remoteServer, port))
      if result == 0:
        print port,'\t Open \t ',socket.getservbyport(port)
      temp_socket.close()
    except KeyboardInterrupt:
      # usually with Ctrl+C
      print "Terminated by user"
      sys.exit()
    except socket.gaierror:
      # unknown host exception
      print 'Hostname could not be resolved. Exiting'
      sys.exit()
    except socket.error:
      # this exception is caused by socket.getservbyport(port)
      # when the port name of a port number is unknown.
      # we will only print 'unknown' under the service information
      # and carry on with the scan instead of crash the program.
      print 'unknown'
      pass
  # log computer time after scan ends
  t1 = datetime.now()
  # compute the amount of time the scan took
  total =  t1 - t0
  print "-" * 50
  # print information about when the scan took place because the open
  # ports on a server can change from time to time and this might be
  #useful information
  print 'Date & time of scan start: ', t0.strftime("%D %I:%M:%S %p")
  print 'Scan completed in: ', total
  print "-" * 50

if __name__ == '__main__':
  main()