<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" 
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output method="xml" indent="yes"
              doctype-system="svg-19991203.dtd"
              version="1.0"
              encoding="ISO-8859-1" />

  <xsl:template match="run">
  
    <xsl:variable name="popSize">
		<xsl:value-of select="search-parameters/population-size" />
    </xsl:variable>     
    <svg xmlns:svg="http://www.w3.org/2000/svg" width="{($popSize * 2) + 400}px" height="460px">

   <!--Write title and parameters-->
   
      <text style="font-size:12" x="10" y="10">
        ID = <xsl:value-of select="@id" />
        Time/Date = <xsl:value-of select="@timedatestamp" />
      </text>
      <text style="font-size:10" x="10" y="25">
        PopSize=<xsl:value-of select="search-parameters/population-size" />; 
        NumGenerations=<xsl:value-of select="search-parameters/generations" />; 
      </text>

   <!--Draw and label horizontal axis-->
    
    <path id="generation" stroke-width="1" stroke="black" fill="none" 
          d="M 40 340 L 540 340" />
    <text fill="blue" font-size="12" font-family="Verdana">
    <textPath dy="30" startOffset="25%" xlink:href="#generation">Generation</textPath>
    </text>

    <path stroke-width="1" stroke="black" fill="none" 
          d="M 140 345 L 140 340" />
    <path stroke-width="1" stroke="black" fill="none" 
          d="M 240 345 L 240 340" />
    <path stroke-width="1" stroke="black" fill="none" 
          d="M 340 345 L 340 340" />
    <path stroke-width="1" stroke="black" fill="none" 
          d="M 440 345 L 440 340" />
    <path stroke-width="1" stroke="black" fill="none" 
          d="M 540 345 L 540 340" />

                   
    <xsl:variable name="generations">
		<xsl:value-of select="search-parameters/generations" />
    </xsl:variable>
    
    <text x="132" y="355" fill="black" font-size="11" font-family="Verdana">
        <xsl:value-of select="$generations div 5" />
    </text>
    <text x="232" y="355" fill="black" font-size="11" font-family="Verdana">
        <xsl:value-of select="$generations div 5 * 2" />
    </text>
    <text x="332" y="355" fill="black" font-size="11" font-family="Verdana">
        <xsl:value-of select="$generations div 5 * 3" />
    </text>
    <text x="432" y="355" fill="black" font-size="11" font-family="Verdana">
        <xsl:value-of select="$generations div 5 * 4" />
    </text>
    <text x="532" y="355" fill="black" font-size="11" font-family="Verdana">
        <xsl:value-of select="$generations div 5 * 5" />
    </text>

    
   <!--Draw vertical labels-->

    <text x="5" y="75" 
          style="writing-mode: tb; glyph-orientation-vertical: 0; fill: blue;
          font-size: 10; font-family: Verdana;"># of Individuals</text>

    <text x="15" y="285" fill="black" font-size="11" font-family="Verdana">
        <xsl:value-of select="($popSize * 0.2)" />
    </text>
    <text x="15" y="225" fill="black" font-size="11" font-family="Verdana">
        <xsl:value-of select="($popSize * 0.4)" />
    </text>
    <text x="15" y="165" fill="black" font-size="11" font-family="Verdana">
        <xsl:value-of select="($popSize * 0.6)" />
    </text>
    <text x="15" y="105" fill="black" font-size="11" font-family="Verdana">
        <xsl:value-of select="($popSize * 0.8)" />
    </text>
    <text x="15" y="45" fill="black" font-size="11" font-family="Verdana">
        <xsl:value-of select="($popSize)" />
    </text>

    
   <!--Draw species per generation-->

   <xsl:variable name="popIncrement">
	     <xsl:value-of select="(300 div $popSize)"/>
   </xsl:variable>
    
    <xsl:for-each select="generation">

       <xsl:variable name="xplot">
    	     <xsl:value-of select="(((500 div $generations) * @id) + 42)"/>
       </xsl:variable>
             
       <xsl:for-each select="specie">
           <xsl:variable name="specieColor">
             <xsl:choose>
               <xsl:when test="((position() mod 3 = 0) and (@id = preceding::specie/@id))">
                 <xsl:text>CornflowerBlue</xsl:text>
               </xsl:when>
               <xsl:when test="(position() mod 3) = 1 and (@id = preceding::specie/@id)">
                 <xsl:text>Yellow</xsl:text>
               </xsl:when>
               <xsl:when test="(position() mod 3) = 2 and (@id = preceding::specie/@id)">
                 <xsl:text>Plum</xsl:text>
               </xsl:when>
               <xsl:otherwise>
                <xsl:text>Chartreuse</xsl:text>
               </xsl:otherwise>
             </xsl:choose>
           </xsl:variable>
       
           <xsl:variable name="specieCount">
    	      <xsl:value-of select="@count" />
           </xsl:variable>
           <xsl:variable name="yplotFrom" 
                     select="(340 - (sum(preceding-sibling::specie/@count) * $popIncrement))" />
           <xsl:variable name="yplotTo" 
                     select="(($yplotFrom - ($specieCount * $popIncrement)) + 0.5)" />       
           <path stroke-width="3" stroke="{$specieColor}" fill="none" 
                 d="M {$xplot} {$yplotFrom} L {$xplot} {$yplotTo}" />
       </xsl:for-each>

    </xsl:for-each>
       
   
    </svg>
  </xsl:template>
</xsl:stylesheet>
