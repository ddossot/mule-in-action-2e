Examples for Mule in Action, Second Edition
-------------------------------------------

Source code snippets and sample applications for the Second Edition of the book Mule in Action, authored by David Dossot, John D'Emic and Victor Romero, and published by Manning Publications.

Book home page: http://www.manning.com/dossot2/


Requirements:

- Oracle JDK 1.6 with unlimited JCE cryptography,
- Maven 3.

Tests in the examples will open ports while they run, like 8080, 5555 and 52525, so watch out for potential conflicts. 

Some examples are not meant to be run, as they try to bind to fictitious host names. These examples are not included in the POM hierarchy.

First, ensure that Maven's setting.xml file contains this plugin group:

    <pluginGroup>org.mule.tools</pluginGroup>

To build and run tests run:

    MAVEN_OPTS="-Xms128m -Xmx512m -XX:MaxPermSize=256m" mvn clean install

in the directory where is README file is located.


To open projects in Eclipse, run:

    mvn eclipse:eclipse

in the directory where is README file is located, then import the generated projects in Eclipse.

To open projects in Mule Studio, import them as "Maven-based Mule Projects from pom.xml".

#### Copyright 2012-2013 Manning Publications Co. - Licensed under the MIT License.
