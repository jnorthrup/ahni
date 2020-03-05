java org.apache.xalan.xslt.Process -in %1.xml -xsl format.xsl -out %12.xml
java org.apache.xalan.xslt.Process -in %12.xml -xsl draw.xsl -out %1.svg