<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" 
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output method="xml" indent="yes"
              doctype-system="svg-19991203.dtd"
              version="1.0"
              encoding="ISO-8859-1" />

  <xsl:template match="run">   
    <svg xmlns:svg="http://www.w3.org/2000/svg" width="575px" height="375px">

   <!--Write title and parameters-->

      <text style="font-size:10" x="10" y="25">
        <xsl:value-of select="@id" />
      </text>

   <!--Draw and label horizontal axis-->
    
    <path id="generation" stroke-width="1" stroke="black" fill="none" 
          d="M 40 340 L 540 340" />


    <text fill="blue" font-size="15" font-family="Verdana">
    <textPath dy="30" startOffset="40%" xlink:href="#generation">
    Generation</textPath>
    </text>



    <path stroke-width="1" stroke="black" fill="none" 
          d="M 140 345 L 140 335" />
    <path stroke-width="1" stroke="black" fill="none" 
          d="M 240 345 L 240 335" />
    <path stroke-width="1" stroke="black" fill="none" 
          d="M 340 345 L 340 335" />
    <path stroke-width="1" stroke="black" fill="none" 
          d="M 440 345 L 440 335" />
    <path stroke-width="1" stroke="black" fill="none" 
          d="M 540 345 L 540 335" />
         
    <xsl:variable name="generations">
		<xsl:value-of select="search-parameters/generations" />
    </xsl:variable>
    
    <text x="132" y="355" fill="black" font-size="11" font-family="Verdana">
        <xsl:value-of select="($generations * 0.2)" />
    </text>
    <text x="232" y="355" fill="black" font-size="11" font-family="Verdana">
        <xsl:value-of select="($generations * 0.4)" />
    </text>
    <text x="332" y="355" fill="black" font-size="11" font-family="Verdana">
        <xsl:value-of select="($generations * 0.6)" />
    </text>
    <text x="432" y="355" fill="black" font-size="11" font-family="Verdana">
        <xsl:value-of select="($generations * 0.8)" />
    </text>
    <text x="532" y="355" fill="black" font-size="11" font-family="Verdana">
        <xsl:value-of select="$generations" />
    </text>

   <!--Draw and label vertical axis-->
    
    <path id="complexity" stroke-width="1" stroke="black" fill="none" 
          d="M 40 340 L 40 40" />
     
    <text fill="blue" font-size="15" font-family="Verdana">
    <textPath dy="-25" startOffset="40%" xlink:href="#complexity">Number of Genes</textPath>
    </text>

       <xsl:variable name="complexity_max">
         <xsl:for-each select="generation/complexity/max">
           <xsl:sort data-type="number" order="descending"/>
           <xsl:if test="position()=1">
    	     <xsl:value-of select="."/>
    	   </xsl:if>
         </xsl:for-each>
       </xsl:variable>

       <xsl:variable name="complexity_min">
         <xsl:for-each select="generation/complexity/min">
           <xsl:sort data-type="number" order="ascending"/>
           <xsl:if test="position()=1">
    	  <xsl:value-of select="."/></xsl:if>
         </xsl:for-each>
       </xsl:variable> 

          <xsl:variable name="complexity_range">
    		<xsl:value-of select="($complexity_max - $complexity_min)" />
          </xsl:variable>

 
    <path id="complexity1" stroke-width="0.5" stroke="green" fill="none" 
          d="M 40 40 L 540 40" />

    <text fill="green" fill-opacity="1.0" font-size="9" font-family="Verdana">
    <textPath dy="8" startOffset="90%" xlink:href="#complexity1"> 
            <xsl:value-of select="($complexity_range + $complexity_min)" />
        </textPath>        
    </text>

    
    <path id="complexity2" stroke-width="0.5" stroke="green" fill="none" 
          d="M 40 100 L 540 100" />


    <text fill="green" fill-opacity="1.0" font-size="9" font-family="Verdana">
   <textPath dy="8" startOffset="70%" xlink:href="#complexity2"> 
            <xsl:value-of select="($complexity_range * 0.8) + $complexity_min" />
        </textPath>
    </text>

    
    <path id="complexity3" stroke-width="0.5" stroke="green" fill="none" 
          d="M 40 160 L 540 160" />


    <text fill="green" fill-opacity="1.0" font-size="9" font-family="Verdana">
   <textPath dy="8" startOffset="50%" xlink:href="#complexity3">
            <xsl:value-of select="($complexity_range * 0.6) + $complexity_min" />
        </textPath>
    </text>



    <path id="complexity4" stroke-width="0.5" stroke="green" fill="none" 
          d="M 40 220 L 540 220" />      


    <text fill="green" fill-opacity="1.0" font-size="9" font-family="Verdana">
    <textPath dy="8" startOffset="30%" xlink:href="#complexity4"> 
            <xsl:value-of select="($complexity_range * 0.4) + $complexity_min" />
        </textPath>
    </text>

    
    <path id="complexity5" stroke-width="0.5" stroke="green" fill="none" 
          d="M 40 280 L 540 280" />
    <text fill="green" fill-opacity="1.0" font-size="9" font-family="Verdana">
   <textPath dy="8" startOffset="10%" xlink:href="#complexity5"> 
            <xsl:value-of select="($complexity_range * 0.2) + $complexity_min" />
        </textPath>

    </text>
    

    <text fill="green" fill-opacity="1.0" font-size="9" font-family="Verdana">
     <textPath dy="8" startOffset="0%" xlink:href="#generation">
            <xsl:value-of select="$complexity_min" />
        </textPath>
    </text>


          
   <!--Plot max complexity per generation-->
   
    <xsl:for-each select="generation">
       
       <xsl:variable name="complexityMax">
         <xsl:value-of select="complexity/max" />
       </xsl:variable>
       
       <xsl:variable name="complexityMin">
         <xsl:value-of select="complexity/min" />
       </xsl:variable>

       <xsl:variable name="complexityAvg">
         <xsl:value-of select="complexity/avg" />
       </xsl:variable>
       
       <xsl:variable name="champComplexity">
         <xsl:value-of select="complexity/champ" />
       </xsl:variable>
       
       <xsl:variable name="xplot">
         <xsl:value-of select="((500 div $generations) * @id) + 40" />
       </xsl:variable>

       <xsl:variable name="yplot_max">
         <xsl:value-of select="340 - ((300 div $complexity_range) * ($complexityMax - $complexity_min))" />
       </xsl:variable>
       
       <xsl:variable name="yplot_min">
         <xsl:value-of select="340 - ((300 div $complexity_range) * ($complexityMin - $complexity_min))" />
       </xsl:variable>
       
       <xsl:variable name="yplot_avg">
         <xsl:value-of select="340 - ((300 div $complexity_range) * ($complexityAvg - $complexity_min))" />
       </xsl:variable>

       <xsl:variable name="yplot_champ">
         <xsl:value-of select="340 - ((300 div $complexity_range) * ($champComplexity - $complexity_min))" />
       </xsl:variable>

       <path d="M {$xplot - 2} {$yplot_champ - 2} L {$xplot} {$yplot_champ - 6} L {$xplot + 2} {$yplot_champ - 2} z" id="{@id}" fill="red" />

       <circle id="{@id}" r="1" cx="{$xplot}" cy="{$yplot_avg}" fill="black" />
       
       <path stroke-width="0.5" stroke="blue" fill="none" 
          d="M {$xplot} {$yplot_min} L {$xplot} {$yplot_max}" />
       
    </xsl:for-each>
    

    </svg>

  </xsl:template>
</xsl:stylesheet>
