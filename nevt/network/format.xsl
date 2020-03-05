<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" 
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

  <xsl:template match="network">   

    <network>
        <title> <xsl:value-of select="title" /> </title>
            
            <layer id="1">
                <xsl:for-each select="neuron">
                    <xsl:variable name="neuron_id">
    		            <xsl:value-of select="@id" />
                    </xsl:variable>
                    <xsl:variable name="neuron_type">
    		            <xsl:value-of select="@type" />
                    </xsl:variable> 
                    <xsl:if test="@hint-layer = 1">
                        <neuron id="{$neuron_id}" type="{$neuron_type}">
                            <xsl:for-each select="../connection">
                                <xsl:variable name="src_id">
    		                        <xsl:value-of select="@src-id" />
                                </xsl:variable>
                                <xsl:variable name="connection_id">
    		                        <xsl:value-of select="@id" />
                                </xsl:variable>
                                <xsl:variable name="recurrency">
    		                        <xsl:value-of select="@recurrent" />
                                </xsl:variable>
                                <xsl:variable name="weight">
    		                        <xsl:value-of select="@weight" />
                                </xsl:variable> 
                                <xsl:if test="$neuron_id = @tgt-id">
                                    <xsl:for-each select="../neuron">
                                        <xsl:if test="@id = $src_id">
                                            <xsl:variable name="src_layer">
                		                        <xsl:value-of select="@hint-layer" />
                                            </xsl:variable> 
                                            <xsl:variable name="src_position">
                		                        <xsl:value-of select="@hint-position" />
                                            </xsl:variable>
                                            <connection id="{$connection_id}" 
                                                from_layer="{$src_layer}"
                                                from_position="{$src_position}"
                                                recurrent="{$recurrency}"
                                                weight="{$weight}" />                                             
                                        </xsl:if>
                                    </xsl:for-each>

                                </xsl:if>
                            </xsl:for-each>   
                        </neuron>
                    </xsl:if>
                </xsl:for-each>
            </layer> 
            
            <layer id="2">
                <xsl:for-each select="neuron">
                    <xsl:variable name="neuron_id">
    		            <xsl:value-of select="@id" />
                    </xsl:variable>
                    <xsl:variable name="neuron_type">
    		            <xsl:value-of select="@type" />
                    </xsl:variable>                      
                    <xsl:if test="@hint-layer = 2">
                        <neuron id="{$neuron_id}" type="{$neuron_type}">
                            <xsl:for-each select="../connection">
                                <xsl:variable name="src_id">
    		                        <xsl:value-of select="@src-id" />
                                </xsl:variable>
                                <xsl:variable name="connection_id">
    		                        <xsl:value-of select="@id" />
                                </xsl:variable>
                                <xsl:variable name="recurrency">
    		                        <xsl:value-of select="@recurrent" />
                                </xsl:variable>
                                <xsl:variable name="weight">
    		                        <xsl:value-of select="@weight" />
                                </xsl:variable>  
                                <xsl:if test="$neuron_id = @tgt-id">
                                    <xsl:for-each select="../neuron">
                                        <xsl:if test="@id = $src_id">
                                            <xsl:variable name="src_layer">
                		                        <xsl:value-of select="@hint-layer" />
                                            </xsl:variable> 
                                            <xsl:variable name="src_position">
                		                        <xsl:value-of select="@hint-position" />
                                            </xsl:variable>
                                            <connection id="{$connection_id}" 
                                                from_layer="{$src_layer}"
                                                from_position="{$src_position}"
                                                recurrent="{$recurrency}"
                                                weight="{$weight}" />                                             
                                        </xsl:if>
                                    </xsl:for-each>

                                </xsl:if>
                            </xsl:for-each>   
                        </neuron>
                    </xsl:if>
                </xsl:for-each>
            </layer> 


            <layer id="3">
                <xsl:for-each select="neuron">
                    <xsl:variable name="neuron_id">
    		            <xsl:value-of select="@id" />
                    </xsl:variable>
                    <xsl:variable name="neuron_type">
    		            <xsl:value-of select="@type" />
                    </xsl:variable>  
                    <xsl:if test="@hint-layer = 3">
                        <neuron id="{$neuron_id}" type="{$neuron_type}">
                            <xsl:for-each select="../connection">
                                <xsl:variable name="src_id">
    		                        <xsl:value-of select="@src-id" />
                                </xsl:variable>
                                <xsl:variable name="connection_id">
    		                        <xsl:value-of select="@id" />
                                </xsl:variable>
                                <xsl:variable name="recurrency">
    		                        <xsl:value-of select="@recurrent" />
                                </xsl:variable>
                                <xsl:variable name="weight">
    		                        <xsl:value-of select="@weight" />
                                </xsl:variable>  
                                <xsl:if test="$neuron_id = @tgt-id">
                                    <xsl:for-each select="../neuron">
                                        <xsl:if test="@id = $src_id">
                                            <xsl:variable name="src_layer">
                		                        <xsl:value-of select="@hint-layer" />
                                            </xsl:variable> 
                                            <xsl:variable name="src_position">
                		                        <xsl:value-of select="@hint-position" />
                                            </xsl:variable>
                                            <connection id="{$connection_id}" 
                                                from_layer="{$src_layer}"
                                                from_position="{$src_position}"
                                                recurrent="{$recurrency}"
                                                weight="{$weight}" />                                             
                                        </xsl:if>
                                    </xsl:for-each>

                                </xsl:if>
                            </xsl:for-each>   
                        </neuron>
                    </xsl:if>
                </xsl:for-each>
            </layer> 
            
            
           <layer id="4">
                <xsl:for-each select="neuron">
                    <xsl:variable name="neuron_id">
    		            <xsl:value-of select="@id" />
                    </xsl:variable>
                    <xsl:variable name="neuron_type">
    		            <xsl:value-of select="@type" />
                    </xsl:variable>  
                    <xsl:if test="@hint-layer = 4">
                        <neuron id="{$neuron_id}" type="{$neuron_type}">
                            <xsl:for-each select="../connection">
                                <xsl:variable name="src_id">
    		                        <xsl:value-of select="@src-id" />
                                </xsl:variable>
                                <xsl:variable name="connection_id">
    		                        <xsl:value-of select="@id" />
                                </xsl:variable>
                                <xsl:variable name="recurrency">
    		                        <xsl:value-of select="@recurrent" />
                                </xsl:variable>
                                <xsl:variable name="weight">
    		                        <xsl:value-of select="@weight" />
                                </xsl:variable>  
                                <xsl:if test="$neuron_id = @tgt-id">
                                    <xsl:for-each select="../neuron">
                                        <xsl:if test="@id = $src_id">
                                            <xsl:variable name="src_layer">
                		                        <xsl:value-of select="@hint-layer" />
                                            </xsl:variable> 
                                            <xsl:variable name="src_position">
                		                        <xsl:value-of select="@hint-position" />
                                            </xsl:variable>
                                            <connection id="{$connection_id}" 
                                                from_layer="{$src_layer}"
                                                from_position="{$src_position}"
                                                recurrent="{$recurrency}"
                                                weight="{$weight}" />                                             
                                        </xsl:if>
                                    </xsl:for-each>

                                </xsl:if>
                            </xsl:for-each>   
                        </neuron>
                    </xsl:if>
                </xsl:for-each>
            </layer> 

           <layer id="5">
                <xsl:for-each select="neuron">
                    <xsl:variable name="neuron_id">
    		            <xsl:value-of select="@id" />
                    </xsl:variable>
                    <xsl:variable name="neuron_type">
    		            <xsl:value-of select="@type" />
                    </xsl:variable> 
                    <xsl:if test="@hint-layer = 5">
                        <neuron id="{$neuron_id}" type="{$neuron_type}">
                            <xsl:for-each select="../connection">
                                <xsl:variable name="src_id">
    		                        <xsl:value-of select="@src-id" />
                                </xsl:variable>
                                <xsl:variable name="connection_id">
    		                        <xsl:value-of select="@id" />
                                </xsl:variable>
                                <xsl:variable name="recurrency">
    		                        <xsl:value-of select="@recurrent" />
                                </xsl:variable>
                                <xsl:variable name="weight">
    		                        <xsl:value-of select="@weight" />
                                </xsl:variable>  
                                <xsl:if test="$neuron_id = @tgt-id">
                                    <xsl:for-each select="../neuron">
                                        <xsl:if test="@id = $src_id">
                                            <xsl:variable name="src_layer">
                		                        <xsl:value-of select="@hint-layer" />
                                            </xsl:variable> 
                                            <xsl:variable name="src_position">
                		                        <xsl:value-of select="@hint-position" />
                                            </xsl:variable>
                                            <connection id="{$connection_id}" 
                                                from_layer="{$src_layer}"
                                                from_position="{$src_position}"
                                                recurrent="{$recurrency}"
                                                weight="{$weight}" />                                             
                                        </xsl:if>
                                    </xsl:for-each>

                                </xsl:if>
                            </xsl:for-each>   
                        </neuron>
                    </xsl:if>
                </xsl:for-each>
            </layer> 
            
           <layer id="6">
                <xsl:for-each select="neuron">
                    <xsl:variable name="neuron_id">
    		            <xsl:value-of select="@id" />
                    </xsl:variable>
                    <xsl:variable name="neuron_type">
    		            <xsl:value-of select="@type" />
                    </xsl:variable>  
                    <xsl:if test="@hint-layer = 6">
                        <neuron id="{$neuron_id}" type="{$neuron_type}">
                            <xsl:for-each select="../connection">
                                <xsl:variable name="src_id">
    		                        <xsl:value-of select="@src-id" />
                                </xsl:variable>
                                <xsl:variable name="connection_id">
    		                        <xsl:value-of select="@id" />
                                </xsl:variable>
                                <xsl:variable name="recurrency">
    		                        <xsl:value-of select="@recurrent" />
                                </xsl:variable>
                                <xsl:variable name="weight">
    		                        <xsl:value-of select="@weight" />
                                </xsl:variable>  
                                <xsl:if test="$neuron_id = @tgt-id">
                                    <xsl:for-each select="../neuron">
                                        <xsl:if test="@id = $src_id">
                                            <xsl:variable name="src_layer">
                		                        <xsl:value-of select="@hint-layer" />
                                            </xsl:variable> 
                                            <xsl:variable name="src_position">
                		                        <xsl:value-of select="@hint-position" />
                                            </xsl:variable>
                                            <connection id="{$connection_id}" 
                                                from_layer="{$src_layer}"
                                                from_position="{$src_position}"
                                                recurrent="{$recurrency}"
                                                weight="{$weight}" />                                             
                                        </xsl:if>
                                    </xsl:for-each>

                                </xsl:if>
                            </xsl:for-each>   
                        </neuron>
                    </xsl:if>
                </xsl:for-each>
            </layer>                         


           <layer id="7">
                <xsl:for-each select="neuron">
                    <xsl:variable name="neuron_id">
    		            <xsl:value-of select="@id" />
                    </xsl:variable> 
                    <xsl:variable name="neuron_type">
    		            <xsl:value-of select="@type" />
                    </xsl:variable> 
                    <xsl:if test="@hint-layer = 7">
                        <neuron id="{$neuron_id}" type="{$neuron_type}">
                            <xsl:for-each select="../connection">
                                <xsl:variable name="src_id">
    		                        <xsl:value-of select="@src-id" />
                                </xsl:variable>
                                <xsl:variable name="connection_id">
    		                        <xsl:value-of select="@id" />
                                </xsl:variable>
                                <xsl:variable name="recurrency">
    		                        <xsl:value-of select="@recurrent" />
                                </xsl:variable>
                                <xsl:variable name="weight">
    		                        <xsl:value-of select="@weight" />
                                </xsl:variable>  
                                <xsl:if test="$neuron_id = @tgt-id">
                                    <xsl:for-each select="../neuron">
                                        <xsl:if test="@id = $src_id">
                                            <xsl:variable name="src_layer">
                		                        <xsl:value-of select="@hint-layer" />
                                            </xsl:variable> 
                                            <xsl:variable name="src_position">
                		                        <xsl:value-of select="@hint-position" />
                                            </xsl:variable>
                                            <connection id="{$connection_id}" 
                                                from_layer="{$src_layer}"
                                                from_position="{$src_position}"
                                                recurrent="{$recurrency}"
                                                weight="{$weight}" />                                             
                                        </xsl:if>
                                    </xsl:for-each>

                                </xsl:if>
                            </xsl:for-each>   
                        </neuron>
                    </xsl:if>
                </xsl:for-each>
            </layer>             


           <layer id="8">
                <xsl:for-each select="neuron">
                    <xsl:variable name="neuron_id">
    		            <xsl:value-of select="@id" />
                    </xsl:variable> 
                    <xsl:variable name="neuron_type">
    		            <xsl:value-of select="@type" />
                    </xsl:variable> 
                    <xsl:if test="@hint-layer = 8">
                        <neuron id="{$neuron_id}" type="{$neuron_type}">
                            <xsl:for-each select="../connection">
                                <xsl:variable name="src_id">
    		                        <xsl:value-of select="@src-id" />
                                </xsl:variable>
                                <xsl:variable name="connection_id">
    		                        <xsl:value-of select="@id" />
                                </xsl:variable>
                                <xsl:variable name="recurrency">
    		                        <xsl:value-of select="@recurrent" />
                                </xsl:variable>
                                <xsl:variable name="weight">
    		                        <xsl:value-of select="@weight" />
                                </xsl:variable>  
                                <xsl:if test="$neuron_id = @tgt-id">
                                    <xsl:for-each select="../neuron">
                                        <xsl:if test="@id = $src_id">
                                            <xsl:variable name="src_layer">
                		                        <xsl:value-of select="@hint-layer" />
                                            </xsl:variable> 
                                            <xsl:variable name="src_position">
                		                        <xsl:value-of select="@hint-position" />
                                            </xsl:variable>
                                            <connection id="{$connection_id}" 
                                                from_layer="{$src_layer}"
                                                from_position="{$src_position}"
                                                recurrent="{$recurrency}"
                                                weight="{$weight}" />                                             
                                        </xsl:if>
                                    </xsl:for-each>

                                </xsl:if>
                            </xsl:for-each>   
                        </neuron>
                    </xsl:if>
                </xsl:for-each>
            </layer> 
            
            
           <layer id="9">
                <xsl:for-each select="neuron">
                    <xsl:variable name="neuron_id">
    		            <xsl:value-of select="@id" />
                    </xsl:variable> 
                    <xsl:variable name="neuron_type">
    		            <xsl:value-of select="@type" />
                    </xsl:variable> 
                    <xsl:if test="@hint-layer = 9">
                        <neuron id="{$neuron_id}" type="{$neuron_type}">
                            <xsl:for-each select="../connection">
                                <xsl:variable name="src_id">
    		                        <xsl:value-of select="@src-id" />
                                </xsl:variable>
                                <xsl:variable name="connection_id">
    		                        <xsl:value-of select="@id" />
                                </xsl:variable>
                                <xsl:variable name="recurrency">
    		                        <xsl:value-of select="@recurrent" />
                                </xsl:variable>
                                <xsl:variable name="weight">
    		                        <xsl:value-of select="@weight" />
                                </xsl:variable>  
                                <xsl:if test="$neuron_id = @tgt-id">
                                    <xsl:for-each select="../neuron">
                                        <xsl:if test="@id = $src_id">
                                            <xsl:variable name="src_layer">
                		                        <xsl:value-of select="@hint-layer" />
                                            </xsl:variable> 
                                            <xsl:variable name="src_position">
                		                        <xsl:value-of select="@hint-position" />
                                            </xsl:variable>
                                            <connection id="{$connection_id}" 
                                                from_layer="{$src_layer}"
                                                from_position="{$src_position}"
                                                recurrent="{$recurrency}"
                                                weight="{$weight}" />                                             
                                        </xsl:if>
                                    </xsl:for-each>

                                </xsl:if>
                            </xsl:for-each>   
                        </neuron>
                    </xsl:if>
                </xsl:for-each>
            </layer> 

           <layer id="10">
                <xsl:for-each select="neuron">
                    <xsl:variable name="neuron_id">
    		            <xsl:value-of select="@id" />
                    </xsl:variable>
                    <xsl:variable name="neuron_type">
    		            <xsl:value-of select="@type" />
                    </xsl:variable>  
                    <xsl:if test="@hint-layer = 10">
                        <neuron id="{$neuron_id}" type="{$neuron_type}">
                            <xsl:for-each select="../connection">
                                <xsl:variable name="src_id">
    		                        <xsl:value-of select="@src-id" />
                                </xsl:variable>
                                <xsl:variable name="connection_id">
    		                        <xsl:value-of select="@id" />
                                </xsl:variable>
                                <xsl:variable name="recurrency">
    		                        <xsl:value-of select="@recurrent" />
                                </xsl:variable>
                                <xsl:variable name="weight">
    		                        <xsl:value-of select="@weight" />
                                </xsl:variable>  
                                <xsl:if test="$neuron_id = @tgt-id">
                                    <xsl:for-each select="../neuron">
                                        <xsl:if test="@id = $src_id">
                                            <xsl:variable name="src_layer">
                		                        <xsl:value-of select="@hint-layer" />
                                            </xsl:variable> 
                                            <xsl:variable name="src_position">
                		                        <xsl:value-of select="@hint-position" />
                                            </xsl:variable>
                                            <connection id="{$connection_id}" 
                                                from_layer="{$src_layer}"
                                                from_position="{$src_position}"
                                                recurrent="{$recurrency}"
                                                weight="{$weight}" />                                             
                                        </xsl:if>
                                    </xsl:for-each>

                                </xsl:if>
                            </xsl:for-each>   
                        </neuron>
                    </xsl:if>
                </xsl:for-each>
            </layer> 

           <layer id="11">
                <xsl:for-each select="neuron">
                    <xsl:variable name="neuron_id">
    		            <xsl:value-of select="@id" />
                    </xsl:variable>
                    <xsl:variable name="neuron_type">
    		            <xsl:value-of select="@type" />
                    </xsl:variable>  
                    <xsl:if test="@hint-layer = 11">
                        <neuron id="{$neuron_id}" type="{$neuron_type}">
                            <xsl:for-each select="../connection">
                                <xsl:variable name="src_id">
    		                        <xsl:value-of select="@src-id" />
                                </xsl:variable>
                                <xsl:variable name="connection_id">
    		                        <xsl:value-of select="@id" />
                                </xsl:variable>
                                <xsl:variable name="recurrency">
    		                        <xsl:value-of select="@recurrent" />
                                </xsl:variable>
                                <xsl:variable name="weight">
    		                        <xsl:value-of select="@weight" />
                                </xsl:variable>  
                                <xsl:if test="$neuron_id = @tgt-id">
                                    <xsl:for-each select="../neuron">
                                        <xsl:if test="@id = $src_id">
                                            <xsl:variable name="src_layer">
                		                        <xsl:value-of select="@hint-layer" />
                                            </xsl:variable> 
                                            <xsl:variable name="src_position">
                		                        <xsl:value-of select="@hint-position" />
                                            </xsl:variable>
                                            <connection id="{$connection_id}" 
                                                from_layer="{$src_layer}"
                                                from_position="{$src_position}"
                                                recurrent="{$recurrency}"
                                                weight="{$weight}" />                                             
                                        </xsl:if>
                                    </xsl:for-each>

                                </xsl:if>
                            </xsl:for-each>   
                        </neuron>
                    </xsl:if>
                </xsl:for-each>
            </layer> 
            
            
           <layer id="12">
                <xsl:for-each select="neuron">
                    <xsl:variable name="neuron_id">
    		            <xsl:value-of select="@id" />
                    </xsl:variable>
                    <xsl:variable name="neuron_type">
    		            <xsl:value-of select="@type" />
                    </xsl:variable>  
                     <xsl:if test="@hint-layer = 12">
                        <neuron id="{$neuron_id}" type="{$neuron_type}">
                            <xsl:for-each select="../connection">
                                <xsl:variable name="src_id">
    		                        <xsl:value-of select="@src-id" />
                                </xsl:variable>
                                <xsl:variable name="connection_id">
    		                        <xsl:value-of select="@id" />
                                </xsl:variable>
                                <xsl:variable name="recurrency">
    		                        <xsl:value-of select="@recurrent" />
                                </xsl:variable>
                                <xsl:variable name="weight">
    		                        <xsl:value-of select="@weight" />
                                </xsl:variable>  
                                <xsl:if test="$neuron_id = @tgt-id">
                                    <xsl:for-each select="../neuron">
                                        <xsl:if test="@id = $src_id">
                                            <xsl:variable name="src_layer">
                		                        <xsl:value-of select="@hint-layer" />
                                            </xsl:variable> 
                                            <xsl:variable name="src_position">
                		                        <xsl:value-of select="@hint-position" />
                                            </xsl:variable>
                                            <connection id="{$connection_id}" 
                                                from_layer="{$src_layer}"
                                                from_position="{$src_position}"
                                                recurrent="{$recurrency}"
                                                weight="{$weight}" />                                             
                                        </xsl:if>
                                    </xsl:for-each>

                                </xsl:if>
                            </xsl:for-each>   
                        </neuron>
                    </xsl:if>
                </xsl:for-each>
            </layer> 
                                                              
       </network>
  
  </xsl:template>
</xsl:stylesheet>
