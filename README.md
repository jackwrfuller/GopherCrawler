# Gopher Crawler

- Jack Fuller
- u6100349

## Installation

You can use the zip file submitted. Otherwise, you can clone it:

```shell
git clone -b release https://github.com/jackwrfuller/GopherCrawler.git
cd GopherCrawler 
```

## Usage

Once inside the project repo, the crawler can be run using:

```shell
java Crawler.java [host] [port] [selector]
```

The arguments are optional and set by default to the COMP3310 gopher server with selector "".

For tutors marking this project (and assuming the final server's details are the same), you can simply run

```shell
java Crawler.java
```

## Technical Notes

## Definitions

*External Server*: a server that has a different port or a different hostname (or both) than COMP3310 official gopher server.
In this project, this classified as a `FOREIGN` server.

*Maximum File Size*: 100,000 bytes.

*Timeout Duration*: 5 seconds.

*Invalid Reference/Server*: Any reference/server that causes an error to be thrown when attempting to crawl it is considered 'invalid'.
This could be because:
- The Gopher server returned a error code itself (i.e. a '3' code)
- The client did not respond within the timeout duration (i.e. the server timed out)
- The server's response exceeds the maximum file size.

## Wireshark Summary

![gopher-wireshark-summary](https://github.com/jackwrfuller/GopherCrawler/assets/78133717/2315e470-86c9-4c1b-bd74-c732999f23c3)

