# Author: John Abraham
# Data-Centric Security
# Exercise2: Web Crawler
#
# avoiding detection during the crawl process is kept in
# mind and several measures are taken:
# 1. Speed: If you send too many requests to a website
#    quickly one after the other you can get a too many
#    requests error and the url will not be crawls or worse
#    you can get blocked from the website. To avoid this I
#    inserted a one second wait/sleep after each url fetch.
# 2. Identity: websites do not want to block genuine users
#    so we should try to look like one using user-agent spoofing.
#    To do that we can change the user agent on urllib2.urlopen()
#    that we are using to open a url. When opening a url and
#    requesting the html from that url I am pretending to be
#    Mozilla Firefox v 5.0 and not showing that I am infact a
#    web crawler bot.
# 3. Infinite loop traps: some websites create infinite loop
#    traps that spiders can get stuck in. To avoid this problem
#    I have an option to set a max link depth before beginning a
#    crawl, and during the crawl process visited/crawled links are
#    stored in a container and never visited/crawled again.
#    So whether a max link depth limit is set or not this crawler
#    avoids these kinds of traps.
# there are some other strategies to consider when avoiding
# crawler traps but these two were suffcient for most websites.
# Other possible strategies may include:
# 1. avoiding honeypot traps (urls not displayed to users
#    but still embedded in html).
# 2. IP spoofing on every http get request.
# 3. not following the same crawling pattern.
# 4. Nnot requesting pages that have contiguous urls on a page.
#    It is better to access urls in a random order as a user would
#    and not one after the other.

import re
import sys
import time
import Queue
import urllib2
import urlparse
from bs4 import BeautifulSoup

def main():
  print 'This crawler extracts useful information from organization\'s websites for penetration testing.'
  print 'The information it looks for is:'
  print '1. e-mail addresses'
  print '2. phone numbers'
  print '3. SSN\'s'
  print '4. MD5 hashes'
  print '5. IPv4 Addresses'
  print '6. physical addresses'
  print '7. URLs that point to any site with the name test, dev, stage in it\n'
  base_url = raw_input('Enter a base url (with http(s)://) to start the crawl: ')
  if base_url.endswith('/'):
    base_url = base_url[:-1]
  set_depth = raw_input('Set a crawl depth limit (y/n): ')
  # crawl the entire website with no page depth limit
  # will take a long time for large websites
  if set_depth == 'n':
    print "-" * 50
    print 'crawling in progress...'
    # website sitemaps have a generic tree-like structure when you remove all duplicate urls.
    # the base url is the root. The root will usually have several urls embedded in it
    # and each child url will have several urls embedded in it and so on...
    # in this case the best way to get all the urls in the website is to use a
    # breadth-first search (level-order traversal) algorithm since for every node
    # we know its children nodes but we don't know anything about any deeper nodes
    # from that level. If we use a depth-first search traversal algorithm the problem
    # is that when we go a level deeper we can't go backwards after visiting the deepest
    # leaf node as we would in a regular binary search tree where we can do a recursive call
    # and go back to the parent.
    discovered_urls = []
    queue = Queue.Queue()
    queue.put(base_url)
    while not queue.empty():
      current_url = queue.get()
      if current_url not in discovered_urls:
        discovered_urls.append(current_url)
        extract_data_from_html(current_url)
        for item in get_urls_in_page(current_url, base_url):
          queue.put(item)
      time.sleep(1)
    print "-" * 50
  # set a crawl web page depth limit
  # useful for large websites that take a long time to crawl
  elif set_depth == 'y':
    # depth 1 means only extract data only from the base url provided by the user
    # depth 2 means extract data from the base url and all urls found in the base
    # and so on...
    try:
      depth_limit = int(raw_input('Enter a crawl depth limit (>0) for the crawl: '))
    except:
      print 'Please enter an integer. Exiting...'
      sys.exit()
    while depth_limit <= 0:
      try:
        depth_limit = int(raw_input('Enter a crawl depth limit (>0) for the crawl: '))
      except:
        print 'Please enter an integer. Exiting...'
        sys.exit()        
    print "-" * 50
    print 'crawling in progress...'
    # apply the same breadth-first search (BFS) algorithm except in this case
    # we need to to keep track of the depth level and stop the crawl when the
    # deepest level is reached
    discovered_urls = []
    queue = Queue.Queue()
    queue.put(base_url)
    current_level = 1
    while not queue.empty():
      current_url = queue.get()
      if current_url not in discovered_urls:
        discovered_urls.append(current_url)
        extract_data_from_html(current_url)
        if current_level != depth_limit:
          current_level = current_level + 1
          for item in get_urls_in_page(current_url, base_url):
            queue.put(item)
      time.sleep(1)
    print "-" * 50
  else:
    print 'Please select a valid option. Exiting...'
    sys.exit()

# method to extract all the urls embedded in a web page
# given its url. the base url is also provided as a 
# parameter to determine whether the given url is internal
# to the base url and not an external url to another website.
# we are only interested in internal urls. if we follow external
# urls our crawl will never end; however, if we discover external urls
# with either of the words: 'test', 'dev', or 'stage' in them we will
# print that information to the screen.
def get_urls_in_page(parent_url, base_url):
  child_urls = []
  try:
    opener = urllib2.build_opener()
    opener.addheaders = [('User-agent', 'Mozilla/5.0')]
    response = opener.open(parent_url)
    html = response.read()
    soup = BeautifulSoup(html, 'lxml')
    # href fields in anchor tags
    anchor_tag_links = soup('a')
    for link in anchor_tag_links:
      if link.has_attr('href'):
      	# convert url from a relative path to absolute
        temp_url = urlparse.urljoin(parent_url, link['href'])
        # we're only interested in crawling links in the same domain
        # not external links like facebook, twitter, etc.
        if base_url in temp_url:
          child_urls.append(temp_url)
        else:
          external_url_check(temp_url)

    # action fields in form tags
    ls = soup.findAll('form')
    for l in ls:
      if str(l.get('action')) != 'None':
        # convert url from a relative path to absolute
        temp_url = urlparse.urljoin(parent_url, str(l.get('action')))
        # we're only interested in crawling links in the same domain
        # not external links like facebook, twitter, etc.
        if base_url in temp_url:
          child_urls.append(temp_url)
        else:
          external_url_check(temp_url)
  except urllib2.HTTPError, e:
    # ignore https errors like 404 - page not found and continue crawling
    pass
  # return a set version of the list to get rid of duplicate urls
  return set(child_urls)

# for urls that are external (not in our base domain) we will not
# crawl them but we will print them to the screen if they have
# one of these words in them: test, dev, or stage because them
# may be of some interest.
def external_url_check(external_url):
  if ('test' in external_url):
    print '-----> External URL contains the word \'test\':', external_url
  elif ('dev' in external_url):
    print '-----> External URL contains the word \'dev\':', external_url
  elif ('stage' in external_url):
    print '-----> External URL contains the word \'stage\':', external_url

# this method extracts data from every url that comes up in our BFS crawl
# algorithm. Using regular expressions we can extract several types of
# data using certain patterns.
def extract_data_from_html(url):
  print url
  try:
    opener = urllib2.build_opener()
    opener.addheaders = [('User-agent', 'Mozilla/5.0')]
    response = opener.open(url)
    html = response.read()
    # get email addresses
    findall(r'([\w\-\.]+@(\w[\w\-]+\.)+[\w\-]+)', html, 'Email Address:')
    # get phone numbers
    findall(r'\(?\d{3}\)?-? *\d{3}-? *-?\d{4}', html, 'Possible Phone Number:')
    # get possible SSN's
    findall(r'\d\d\d-\d\d-\d\d\d\d', html, 'Possible SSN:')
    # get possible MD5 hashes
    findall(r'([a-fA-F\d]{32})', html, 'Possible MD5 Hash:')
    # get IPv4 Addresses
    findall(r'(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(?:25[0-5]|2[0-4][0-9]|'
             '[01]?[0-9][0-9]?)\.(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(?:25[0-5]|'
             '2[0-4][0-9]|[01]?[0-9][0-9]?)', html, 'IPv4 Address:')
    # get street addresses
    findall(r'\d{1,4} [\w\s]{1,20} (?:street|st|avenue|ave|road|rd|highway|hwy|square|sq|'
             'trail|trl|drive|dr|court|ct|parkway|pkwy|circle|cir|boulevard|blvd|place|pl)'
             '\W?(?=\s|$)', html, 'Street Address:')
  except urllib2.HTTPError, e:
    # ignore https errors like 404 - page not found and continue crawling
    pass

# this method will print the data found to the screen if there is a match.
def findall(regex, html, type):
  r = re.compile(regex, re.IGNORECASE)
  item_matches = r.findall(html)
  # on every page for each piece of information found we only want to display
  # it once. To do that we keep track of items found using a list.
  discovered_items = []
  for item in item_matches:
    if item not in discovered_items:
      discovered_items.append(item)
      if type == 'Email Address:':
        print '----->', type, item[0]
      else:
        print '----->', type, item

if __name__ == '__main__':
  main()