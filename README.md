Examples for Mule in Action, second edition
-------------------------------------------

Requirements:
- JDK 1.6
- Maven 3


Tests in the examples will open ports while they run, like 8080, so watch out for potential conflicts. 

Some examples are not meant to be run, as they try to bind to fictitious host names. These examples are not included in the POM hierarchy.


To build and run tests run:

    mvn clean install

in the directory where is README file is located.


To open projects in Eclipse, run:

    mvn eclipse:eclipse

in the directory where is README file is located, then import the generated projects in Eclipse.

### Copyright 2012 Manning Publications Co. - Licensed under the MIT License.
