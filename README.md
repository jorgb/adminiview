adminiview
==========

Simple Document Gathering and HTML Report Generator for people tired of stacks of paper and want to digitize most documents for easy lookup. This tool is made for home use, and does the following:

- Gathers all file(s) in a directory which match a certain signature
- Runs reports, written in Apache Velocity
- Generates clickable HTML output with a date oveview
- Documents with multiple page(s) are grouped

WARNING: As of writing this tool is very much work in progress.

The generated HTML report(s) are templatable, and stylable. 

The keyword convention is (extensions are optional).

- topic@{date}.???
- topic_desc1_desc2@{date}.???
- topic_desc1@{date}_p{page}.???

The topic is a leading description, such as "banking", "mortgage", "bill", "gas". The descriptions are just guiding descriptions which can be generated as keywords.

The date conventions (for now) are:
- ddmmyyyy, e.g. 23012009 (23-Jan-2009)
- mmyyyy, e.g. 052002 (defaults to 01-May-2002)
- yyyy, e.g. 2012 (defaults to 01-01-2012)

Multiple pages are possible, if the base name is equal to another file in the same directory, a document will contain multiple file(s). For example:

- bills_mortgage@09012012_p1.jpg
- bills_mortgage@09012012_p2.jpg

Will be captured in a Document object with two DocumentFile objects. The basename that needs to be equal is "bills_mortgage@09012012" the extension of a file does not matter. A document without a _p?? postfix will default to page 1.

BUILDING
========
It is a maven project, make sure you have that installed. Then run: 
- mvn package

As said, this tool is intended for personal use. if there are any suggestions to improve it, it would be appreciated if it comes with a pull request :-)
