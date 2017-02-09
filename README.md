# EmailExtractor

## Description
Simple single jar application (script-like) to extract email addresses from documents.
Supported files extensions: .doc, .docx, .pdf, .txt, .html 
As output it generates output.csv file containing data structure: <file name>;<email address>

Parameters (non mandatory):
```java
 -d,--debug          enables debug output
 -i,--input <arg>    input folder [default - current directory]
 -l,--level <arg>    level of directories to visit (depth) [default - 3]
 -o,--output <arg>   output folder [default - current directory]
 -t,--time <arg>     specifies max. time in hours for how long it will
                     keep running the script [default - 1]
```