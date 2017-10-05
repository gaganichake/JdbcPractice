@echo off
path=%JAVA_HOME%\bin;
cd=XSD
xjc -d ../src -p gagan.practice.jaxb.beans.generated CandidateSearchCriteria.xsd
pause