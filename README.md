adminiview
==========

Simple Document Gathering and HTML Report Generator for people tired of stacks of paper and want to digitize most documents for easy lookup. This tool is made for home use, and does the following:

- Gathers all file(s) in a directory which match a certain signature
- Runs reports, written in Apache Velocity
- Generates clickable HTML output with a date oveview

WARNING: As of writing this tool is very much work in progress.

The generated HTML report(s) are templatable, and stylable. 

The keyword convention is (extensions are optional).

- topic@{date}.???
- topic_desc1_desc2@{date}.???

The topic is a leading description, such as "banking", "mortgage", "bill", "gas". The descriptions are just guiding descriptions which can be generated as keywords.

The date conventions (for now) are:
- ddmmyyyy, e.g. 23012009 (23-Jan-2009)
- mmyyyy, e.g. 052002 (defaults to 01-May-2002)
- yyyy, e.g. 2012 (defaults to 01-01-2012)

Building
========
It is a maven project, make sure you have that installed. Then run: 
- mvn build

As said, this tool is intended for personal use. if there are any suggestions to improve it, it would be appreciated if it comes with a pull request :-)
