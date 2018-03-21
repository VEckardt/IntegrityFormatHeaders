# IntegrityFormatHeaders
This utility allows you to clear any formatting from Headings and put in instead the correct h1, h2, h3 ... format


## Purpose
With this custom form you can format an Integrity document in that way, that each heading in the document will be set to the correct heading level.

![FormatHeaders](doc/FormatHeaders.PNG)

## Use Cases
- Set the correct Heading Level for nodes of category "Heading"

## Install
In IntegrityClient folder
- Put the "dist/IntegrityFormatHeaders.jar" directly into your IntegrityClient folder
- Copy also the files "lib/*.jar" into your IntegrityClient/lib folder
- Add a custom menu entry with:
```
name: Custom Gateway
program:  ../jre/bin/javaw.exe
parameter: -jar ../IntegrityFormatHeaders.jar
```

## How to test
- Open any document or just stay on one in the query result
- click Custom > Document Format Headers
- The custom form should open
- Start the formatter Gateway with a click at the [Format] button
- Then review the outcome

## Hints
- A preview is available, you should run it always at first!

## How to debug
- check the log file in your %temp% folder: IntegrityFormatHeaders_2017-09-27.log

##  Development environment
- PTC Integrity LM 10.9 (also 11.0 should be fine)
- Netbeans 7.4 (or 8)
- Java 1.7 (or 1.8)

## Known Limitations
- none
