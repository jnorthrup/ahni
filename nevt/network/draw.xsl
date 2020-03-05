<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" 
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output method="xml" indent="yes"
              doctype-system="svg-19991203.dtd"
              version="1.0"
              encoding="ISO-8859-1" />

  <!--================================================================-->
  <!-- <network> template                                             -->
  <!--                                                                -->
  <!-- This template creates the entire SVG document.  It draws       -->
  <!-- the various parts of the network.                              -->
  <!--================================================================-->

  <xsl:template match="network">   
    <svg xmlns:svg="http://www.w3.org/2000/svg" width="{100*(count(layer))}" height="{75*(count(layer/neuron)+10)}" viewBox="0 0 {75*(count(layer))} {75*(count(layer/neuron)+10)}">

  <!--================================================================-->
  <!-- Write out the <title> element.  This is written in 12-point    -->
  <!-- type.                                                          -->
  <!--================================================================-->

      <text style="font-size:12" x="10" y="10">
        <xsl:value-of select="title"/>
      </text>

  <!--================================================================-->
  <!-- Define connection heads (triangles).                             -->
  <!--================================================================-->
        
        <defs>
             <marker id="triangle_black"
              viewBox="0 0 20 20" refX="0" refY="10" 
              markerUnits="strokeWidth"
              markerWidth="8" markerHeight="6"
              orient="auto">
              <path d="M 0 0 L 20 10 L 0 20 z" fill="black" fill-opacity="0.8" />
             </marker>

             <marker id="triangle_red"
              viewBox="0 0 20 20" refX="0" refY="10" 
              markerUnits="strokeWidth"
              markerWidth="8" markerHeight="6"
              orient="auto">
              <path d="M 0 0 L 20 10 L 0 20 z" fill="red" fill-opacity="0.8" />
             </marker>
        </defs>

  <!--================================================================-->
  <!-- Draw neurons for odd-numbered layers.                          -->                                              -->
  <!--================================================================-->


 
       <xsl:for-each select="layer[@id]">
             <xsl:variable name="layer_number">
		        <xsl:value-of select="@id" />
             </xsl:variable>

       
   <xsl:choose>
       <xsl:when test="position() mod 2 = 1"> 
           <xsl:for-each select="neuron">
                 <xsl:variable name="neuron_position">
    		        <xsl:value-of select="position()" />
                 </xsl:variable>
                <xsl:variable name="node_color">
                    <xsl:choose>
                        <xsl:when test="@type = 'input'">paleturquoise</xsl:when>
    		            <xsl:when test="@type = 'hidden'">palegreen</xsl:when>
    		            <xsl:when test="@type = 'output'">thistle</xsl:when>   
                    </xsl:choose>
                 </xsl:variable>  
                 <circle cx="{75*$layer_number}" cy="{50*$neuron_position}" r="10" fill="{$node_color}" stroke="black" stroke-width="1" />
                 <text x="{(75*$layer_number) - 8}" y="{50*$neuron_position + 3}" style="font-size:5pt"><xsl:value-of select="@id"/></text> 
    
              <!--================================================================-->
              <!-- Define variables for connections.                              -->
              <!--================================================================-->
    
    
            <xsl:for-each select="connection">
                     <xsl:variable name="from_x">
                        <xsl:choose>   
                           <xsl:when test="@from_layer &lt; $layer_number">
                           <xsl:value-of select="(75 * @from_layer) + 10" />
                           </xsl:when>
                           <xsl:otherwise>
                           <xsl:value-of select="(75 * @from_layer) - 10" />
                           </xsl:otherwise>
                        </xsl:choose>
                     </xsl:variable>
                     <xsl:variable name="from_y">
                        <xsl:choose>
                        
                            <xsl:when test="@from_layer mod 2 = 0">
                                <xsl:choose>
                                   <xsl:when test="@from_layer = $layer_number and $neuron_position &lt; @from_position">
                                        <xsl:value-of select="(50 * @from_position) + 23" />
                                   </xsl:when>
                                   <xsl:when test="@from_layer &lt; $layer_number">
                                        <xsl:value-of select="(50 * @from_position) + 27" />
                                   </xsl:when>
                                   <xsl:when test="@from_layer &gt; $layer_number">
                                        <xsl:value-of select="(50 * @from_position) + 27" />
                                   </xsl:when>
                                   <xsl:otherwise>
                                        <xsl:value-of select="(50 * @from_position) + 27" />
                                   </xsl:otherwise>                                   
                                </xsl:choose>
                             </xsl:when>
                            <xsl:when test="@from_layer mod 2 = 1">
                                <xsl:choose>
                                   <xsl:when test="@from_layer = $layer_number and $neuron_position &lt; @from_position">
                                        <xsl:value-of select="(50 * @from_position) - 2" />
                                   </xsl:when>
                                   <xsl:when test="@from_layer &lt; $layer_number">
                                        <xsl:value-of select="(50 * @from_position) + 2" />
                                   </xsl:when>
                                   <xsl:when test="@from_layer &gt; $layer_number">
                                        <xsl:value-of select="(50 * @from_position) + 2" />
                                   </xsl:when>
                                   <xsl:otherwise>
                                        <xsl:value-of select="(50 * @from_position) + 2" />
                                   </xsl:otherwise> 
                                </xsl:choose>
                             </xsl:when>
                               
                               
                        </xsl:choose>
                     </xsl:variable>
                     <xsl:variable name="to_x">
                        <xsl:choose>   
                           <xsl:when test="@from_layer &lt; $layer_number">
                           <xsl:value-of select="(75 * $layer_number) - 12" />
                           </xsl:when>
                           <xsl:when test="@from_layer = $layer_number and $neuron_position &gt; @from_position">
                           <xsl:value-of select="(75 * $layer_number)" />
                           </xsl:when>
                           <xsl:when test="@from_layer = $layer_number and $neuron_position &lt; @from_position">
                           <xsl:value-of select="(75 * $layer_number)" />
                           </xsl:when>
                           <xsl:otherwise>
                           <xsl:value-of select="(75 * $layer_number) + 12" />
                           </xsl:otherwise>
                        </xsl:choose>
                     </xsl:variable>
                     <xsl:variable name="to_y">                       
                        <xsl:choose>   
                           <xsl:when test="@from_layer &lt; $layer_number">
                                <xsl:value-of select="(50 * $neuron_position)" />
                           </xsl:when>
                           <xsl:when test="@from_layer = $layer_number and $neuron_position &gt; @from_position">
                                <xsl:value-of select="(50 * $neuron_position) - 12" />
                           </xsl:when>
                           <xsl:when test="@from_layer = $layer_number and $neuron_position &lt; @from_position">
                                <xsl:value-of select="(50 * $neuron_position) + 12" />
                           </xsl:when>
                           <xsl:otherwise>
                                <xsl:value-of select="(50 * $neuron_position) + 4" />
                           </xsl:otherwise>
                         </xsl:choose>                                                
                     </xsl:variable>
                     <xsl:variable name="connection" select="@id" />
        
                  <!--================================================================-->
                  <!-- Change colors for inhibitory/excitatory connections.                       -->
                  <!--================================================================-->
        
                     <xsl:variable name="line_color">
                        <xsl:choose>   
                           <xsl:when test="@weight >= 0">black</xsl:when>
                           <xsl:otherwise>red</xsl:otherwise>
                        </xsl:choose>
                     </xsl:variable>
        
                     <xsl:variable name="triangle_color">
                        <xsl:choose>   
                           <xsl:when test="@weight >= 0">url(#triangle_black)
                           </xsl:when>
                           <xsl:otherwise>url(#triangle_red)
                           </xsl:otherwise>
                        </xsl:choose>
                     </xsl:variable>

              <!--================================================================-->
              <!-- Assign opacity based on connection strength.                   -->
              <!--================================================================-->
                                   
                     <xsl:variable name="opacity">
                         <xsl:choose>
                               <xsl:when test="@weight &lt; 1.0 and @weight &gt;= 0.5">0.8</xsl:when>
                               <xsl:when test="@weight &gt; -1.0 and @weight &lt;= -0.5">0.8</xsl:when>   
                               <xsl:when test="@weight &gt;= 1.0">1.0</xsl:when>
                               <xsl:when test="@weight &lt;= -1.0">1.0</xsl:when>
                               <xsl:otherwise>0.5</xsl:otherwise>
                         </xsl:choose>   
                     </xsl:variable>

              <!--================================================================-->
              <!-- Assign stroke width based on connection strength.                   -->
              <!--================================================================-->
                                   
                     <xsl:variable name="strokewidth">
                         <xsl:choose>   
                               <xsl:when test="@weight &gt;= 1.0">1.0</xsl:when>
                               <xsl:when test="@weight &lt;= -1.0">1.0</xsl:when>
                               <xsl:otherwise>0.3</xsl:otherwise>
                         </xsl:choose>   
                     </xsl:variable>
        
              <!--================================================================-->
              <!-- Draw forward and recurrent connections.                        -->
              <!--================================================================-->
        
        
              <xsl:if test="$layer_number != @from_layer or $neuron_position != @from_position">
                     <path id="{$connection}"
        	           d="M {$from_x} {$from_y} L {$to_x} {$to_y}" fill="none" stroke="{$line_color}" stroke-width="{$strokewidth}" stroke-opacity="{$opacity}" marker-end="{$triangle_color}" />
              </xsl:if>
                    
              <!--================================================================-->
              <!-- Draw self connections.                                         -->
              <!--================================================================-->
        
              <xsl:if test="$layer_number = @from_layer and $neuron_position = @from_position">
                 <path id="{$connection}"
                       d="M {$from_x + 1},{$from_y - 7} a7,7 0 1,1 7,-7" 
                       fill="none" stroke="{$line_color}" stroke-width="{$strokewidth}" stroke-opacity="{$opacity}" marker-end="{$triangle_color}"/>
              </xsl:if>
        
              <!--================================================================-->
              <!-- Label connections with weights.                                -->
              <!--================================================================-->
    
    
            <!--
                 <xsl:variable name="weight_color">
                    <xsl:choose>   
                       <xsl:when test="@weight &lt; 0">blue
                       </xsl:when>
                       <xsl:otherwise>black
                       </xsl:otherwise>
                    </xsl:choose>
                 </xsl:variable>
                 <text font-family="Verdana" font-size="3" fill="{$weight_color}">
                 <textPath xlink:href="#{$connection}" startOffset="25%" dy="-0.5">
    	        <xsl:value-of select="format-number(@weight, '#.###')" />
                 </textPath>
                 </text>
             -->
             
                    
            </xsl:for-each>
          </xsl:for-each>
    </xsl:when>

  <!--================================================================-->
  <!-- Draw neurons for even-numbered layers.                         -->
  <!--================================================================-->

   
   <xsl:when test="position() mod 2 = 0"> 
           <xsl:for-each select="neuron">
                 <xsl:variable name="neuron_position">
    		        <xsl:value-of select="position()" />
                 </xsl:variable> 
                 <xsl:variable name="node_color">
                    <xsl:choose>
                        <xsl:when test="@type = 'input'">paleturquoise</xsl:when>
    		            <xsl:when test="@type = 'hidden'">palegreen</xsl:when>
    		            <xsl:when test="@type = 'output'">thistle</xsl:when>  
                    </xsl:choose>
                 </xsl:variable> 
                 <circle cx="{75*$layer_number}" cy="{(50*$neuron_position) + 25}" r="10" fill="{$node_color}" stroke="black" stroke-width="1" />
                 <text x="{(75*$layer_number) - 8}" y="{50*$neuron_position + 28}" style="font-size:5pt"><xsl:value-of select="@id"/></text> 
    
              <!--================================================================-->
              <!-- Define variables for connections.                              -->
              <!--================================================================-->
    
    
            <xsl:for-each select="connection">
                     <xsl:variable name="from_x">
                        <xsl:choose>   
                           <xsl:when test="@from_layer &lt; $layer_number">
                           <xsl:value-of select="(75 * @from_layer) + 10" />
                           </xsl:when>
                           <xsl:otherwise>
                           <xsl:value-of select="(75 * @from_layer) - 10" />
                           </xsl:otherwise>
                        </xsl:choose>
                     </xsl:variable>
                     <xsl:variable name="from_y">
                     
                        <xsl:choose>
                      
                             <xsl:when test="@from_layer mod 2 = 0">
                                <xsl:choose>
                                   <xsl:when test="@from_layer &lt; $layer_number">
                                        <xsl:value-of select="(50 * @from_position) + 25" />
                                   </xsl:when>
                                   <xsl:when test="@from_layer = $layer_number and $neuron_position &lt; @from_position">
                                        <xsl:value-of select="(50 * @from_position) + 23" />
                                   </xsl:when>
                                   <xsl:when test="@from_layer &gt; $layer_number">
                                        <xsl:value-of select="(50 * @from_position) + 27" />
                                   </xsl:when>
                                   <xsl:otherwise>
                                        <xsl:value-of select="(50 * @from_position) + 27" />
                                   </xsl:otherwise>
                                 </xsl:choose>
                              </xsl:when>
                              <xsl:when test="@from_layer mod 2 = 1">
                                <xsl:choose>
                                   <xsl:when test="@from_layer &lt; $layer_number">
                                   <xsl:value-of select="(50 * @from_position)" />
                                   </xsl:when>
                                   <xsl:when test="@from_layer = $layer_number and $neuron_position &lt; @from_position">
                                   <xsl:value-of select="(50 * @from_position) - 2" />
                                   </xsl:when>
                                   <xsl:when test="@from_layer &gt; $layer_number">
                                   <xsl:value-of select="(50 * @from_position)" />
                                   </xsl:when>
                                   <xsl:otherwise>
                                   <xsl:value-of select="(50 * @from_position) + 2" />
                                   </xsl:otherwise>
                                 </xsl:choose>
                              </xsl:when>
                           
                        </xsl:choose>
                        
                     </xsl:variable>
                     <xsl:variable name="to_x">
                        <xsl:choose>   
                           <xsl:when test="@from_layer &lt; $layer_number">
                           <xsl:value-of select="(75 * $layer_number) - 12" />
                           </xsl:when>
                           <xsl:when test="@from_layer = $layer_number and $neuron_position &gt; @from_position">
                           <xsl:value-of select="(75 * $layer_number)" />
                           </xsl:when>
                           <xsl:when test="@from_layer = $layer_number and $neuron_position &lt; @from_position">
                           <xsl:value-of select="(75 * $layer_number)" />
                           </xsl:when>
                           <xsl:otherwise>
                           <xsl:value-of select="(75 * $layer_number) + 12" />
                           </xsl:otherwise>
                        </xsl:choose>
                     </xsl:variable>
                     <xsl:variable name="to_y">
                        

                        <xsl:choose>
                        
                            <xsl:when test="@from_layer mod 2 = 0">
                                <xsl:choose>                           
                                   <xsl:when test="@from_layer &lt; $layer_number">
                                        <xsl:value-of select="(50 * $neuron_position) + 25" />
                                   </xsl:when>
                                   <xsl:when test="@from_layer = $layer_number and $neuron_position &gt; @from_position">
                                        <xsl:value-of select="(50 * $neuron_position) + 13" />
                                   </xsl:when>
                                   <xsl:when test="@from_layer = $layer_number and $neuron_position &lt; @from_position">
                                        <xsl:value-of select="(50 * $neuron_position) + 37" />
                                   </xsl:when>
                                   <xsl:when test="@from_layer &gt; $layer_number">
                                        <xsl:value-of select="(50 * $neuron_position) + 27" />
                                   </xsl:when>                                                               
                                </xsl:choose>
                             </xsl:when>
                          
                             <xsl:when test="@from_layer mod 2 = 1">
                                <xsl:choose>                           
                                   <xsl:when test="@from_layer &lt; $layer_number">
                                        <xsl:value-of select="(50 * $neuron_position) + 25" />
                                   </xsl:when>
                                   <xsl:when test="@from_layer = $layer_number and $neuron_position &gt; @from_position">
                                        <xsl:value-of select="(50 * $neuron_position) + 13" />
                                   </xsl:when>
                                   <xsl:when test="@from_layer = $layer_number and $neuron_position &lt; @from_position">
                                        <xsl:value-of select="(50 * $neuron_position) + 37" />
                                   </xsl:when>                                   
                                   <xsl:when test="@from_layer &gt; $layer_number">
                                        <xsl:value-of select="(50 * $neuron_position) + 27" />
                                   </xsl:when>                                    
                                </xsl:choose>
                             </xsl:when>
                         
                        </xsl:choose>

                        
                     </xsl:variable>
                     <xsl:variable name="connection" select="@id" />
        
                  <!--================================================================-->
                  <!-- Change colors for inhibitory/excitatory connections.                       -->
                  <!--================================================================-->
        
                     <xsl:variable name="line_color">
                        <xsl:choose>   
                           <xsl:when test="@weight >= 0">black</xsl:when>
                           <xsl:otherwise>red</xsl:otherwise>
                        </xsl:choose>
                     </xsl:variable>
        
                     <xsl:variable name="triangle_color">
                        <xsl:choose>   
                           <xsl:when test="@weight >= 0">url(#triangle_black)
                           </xsl:when>
                           <xsl:otherwise>url(#triangle_red)
                           </xsl:otherwise>
                        </xsl:choose>
                     </xsl:variable>

              <!--================================================================-->
              <!-- Assign opacity based on connection strength.                   -->
              <!--================================================================-->
                                   
                     <xsl:variable name="opacity">
                         <xsl:choose>
                               <xsl:when test="@weight &lt; 1.0 and @weight &gt;= 0.5">0.8</xsl:when>
                               <xsl:when test="@weight &gt; -1.0 and @weight &lt;= -0.5">0.8</xsl:when>   
                               <xsl:when test="@weight &gt;= 1.0">1.0</xsl:when>
                               <xsl:when test="@weight &lt;= -1.0">1.0</xsl:when>
                               <xsl:otherwise>0.5</xsl:otherwise>
                         </xsl:choose>   
                     </xsl:variable>

              <!--================================================================-->
              <!-- Assign stroke width based on connection strength.              -->
              <!--================================================================-->
                                   
                     <xsl:variable name="strokewidth">
                         <xsl:choose>   
                               <xsl:when test="@weight &gt;= 1.0">1.0</xsl:when>
                               <xsl:when test="@weight &lt;= -1.0">1.0</xsl:when>
                               <xsl:otherwise>0.3</xsl:otherwise>
                         </xsl:choose>   
                     </xsl:variable>
       
              <!--================================================================-->
              <!-- Draw forward and recurrent connections.                        -->
              <!--================================================================-->
        
        
              <xsl:if test="$layer_number != @from_layer or $neuron_position != @from_position">
                     <path id="{$connection}"
        	           d="M {$from_x} {$from_y} L {$to_x} {$to_y}" fill="none" stroke="{$line_color}" stroke-width="{$strokewidth}" stroke-opacity="{$opacity}" marker-end="{$triangle_color}" />
              </xsl:if>
                    
              <!--================================================================-->
              <!-- Draw self connections.                                         -->
              <!--================================================================-->
        
              <xsl:if test="$layer_number = @from_layer and $neuron_position = @from_position and $layer_number mod 2 = 0">
                 <path id="{$connection}"
                       d="M {$from_x + 1},{$from_y - 7} a7,7 0 1,1 7,-7" 
                       fill="none" stroke="{$line_color}" stroke-width="{$strokewidth}" stroke-opacity="{$opacity}" marker-end="{$triangle_color}"/>
              </xsl:if>
              
              <xsl:if test="$layer_number = @from_layer and $neuron_position = @from_position and $layer_number mod 2 = 1">
                 <path id="{$connection}"
                       d="M {$from_x + 1},{$from_y + 18} a7,7 0 1,1 7,-7" 
                       fill="none" stroke="{$line_color}" stroke-width="{$strokewidth}" stroke-opacity="{$opacity}" marker-end="{$triangle_color}"/>
              </xsl:if>
        
              <!--================================================================-->
              <!-- Label connections with weights.                                -->
              <!--================================================================-->
    
        <!--    
    
                 <xsl:variable name="weight_color">
                    <xsl:choose>   
                       <xsl:when test="@weight &lt; 0">blue
                       </xsl:when>
                       <xsl:otherwise>black
                       </xsl:otherwise>
                    </xsl:choose>
                 </xsl:variable>
                 <text font-family="Verdana" font-size="3" fill="{$weight_color}">
                 <textPath xlink:href="#{$connection}" startOffset="25%" dy="-0.5">
    	        <xsl:value-of select="format-number(@weight, '#.###')" />
                 </textPath>
                 </text>
          -->
                    
            </xsl:for-each>
          </xsl:for-each>
    </xsl:when>
    
  </xsl:choose>      
      
      
      
      
    </xsl:for-each>

  </svg>
  </xsl:template>
</xsl:stylesheet>
