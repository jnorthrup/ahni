#!/bin/bash
xalan -in %1.xml -xsl format.xsl -out %12.xml
xalan -in %12.xml -xsl draw.xsl -out %12.svg
