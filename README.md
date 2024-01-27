This is one of my first software produced with Java which is worth sharing.

This console application is intended to merge the results of 2 different API calls and produce an aggregate of information in TXT format. The goal is to produce a text file containing all links and clicks statistics (unique clicks and total clicks) for all Email Marketing campaigns within a chosen period of time. The software handles 2 API calls itself.

These are the API's used:

1) Get Messages List (https://sites.google.com/expertsender.com/api-documentation-v2/methods/email-messages/get-messages-list)
2) Get Link Statistics (https://sites.google.com/expertsender.com/api-documentation-v2/methods/email-statistics/get-link-statistics)

The library JDOM (https://github.com/hunterhacker/jdom/wiki/JDOM2-A-Primer) was used to read and parse XML files produced on local disk for creating the TXT files.

An attempt to implement routines to generate Excel files is also present, partially created, currently commented out. In the future, if it pays off, I'll get back to finish the application with Excel routines, but the TXT format is working perfectly at the moment.

