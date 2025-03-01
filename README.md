
<h1 align="center">JAVA SDK For Tesra  </h1>
<h4 align="center">Version 1.0.0 </h4>

## Overview

This is a comprehensive Java library for the Tesra blockchain. Currently, it supports local wallet management, digital identity management, digital asset management,  deployment and invoke for Smart Contract , and communication with Tesra Blockchain. The future will also support more rich functions and applications .

## Getting started

* 进入 [中文版](docs/cn/README.md) .
* Enter [English Version](docs/en/README.md) .

## Installation Environment 

Please configure JDK 8 and above.

> **Note:** As the length of key used in SDK is greater than 128, due to the restriction of JAVA security policy files, it is necessary to download local_policy.jar and US_export_policy.jar from the official website , to replace the two jar of ${java_home}/jre/lib/security in JRE directory.

Download URL：

>http://www.oracle.com/technetwork/java/javase/downloads/jce8-download-2133166.html


## Build

```
mvn clean install
```

## Preparations

* Make sure Tesra Blockchain has deployed well,  RPC port has been opened, and SDK will connect the RPC server to initialize.


# Contributing

Can I contribute patches to Tesra project?

Yes! Please open a pull request with signed-off commits. We appreciate your help!

You can also send your patches as emails to the developer mailing list.
Please join the Tesra mailing list or forum and talk to us about it.

Either way, if you don't sign off your patches, we will not accept them.
This means adding a line that says "Signed-off-by: Name <email>" at the
end of each commit, indicating that you wrote the code and have the right
to pass it on as an open source patch.

Also, please write good git commit messages.  A good commit message
looks like this:

  Header line: explain the commit in one line (use the imperative)

  Body of commit message is a few lines of text, explaining things
  in more detail, possibly giving some background about the issue
  being fixed, etc etc.

  The body of the commit message can be several paragraphs, and
  please do proper word-wrap and keep columns shorter than about
  74 characters or so. That way "git log" will show things
  nicely even when it's indented.

  Make sure you explain your solution and why you're doing what you're
  doing, as opposed to describing what you're doing. Reviewers and your
  future self can read the patch, but might not understand why a
  particular solution was implemented.

  Reported-by: whoever-reported-it
  Signed-off-by: Your Name <youremail@yourhost.com>

## Community

## Site

* https://www.tesra.me/

## License

The TesraSupernet library (i.e. all code outside of the cmd directory) is licensed under the GNU Lesser General Public License v3.0, also included in our repository in the License file.
