package com.ojcoleman.ahni.hyperneat;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.jgapcustomised.Allele;
import org.jgapcustomised.Chromosome;
import org.jgapcustomised.ChromosomeMaterial;
import org.jgapcustomised.InvalidConfigurationException;

import com.anji.integration.Activator;
import com.anji.integration.ActivatorTranscriber;
import com.anji.integration.AnjiNetTranscriber;
import com.anji.integration.Transcriber;
import com.anji.neat.NeatChromosomeUtility;
import com.anji.neat.NeatConfiguration;
import com.anji.neat.NeuronAllele;
import com.anji.neat.NeuronGene;
import com.anji.neat.NeuronType;
import com.ojcoleman.ahni.transcriber.HyperNEATTranscriber;
import com.ojcoleman.ahni.transcriber.TranscriberAdaptor;

import static com.anji.nn.activationfunction.ActivationFunction.GaussianActivationFunction;
import static com.anji.nn.activationfunction.ActivationFunction.StepActivationFunction;

/**
 * Extension of NEAT configuration with HyperNEAT-specific features added:<ul>
 * <li>Forces correct number of inputs and outputs according to the number
 * required for the CPPN in the initial
 * {@link org.jgapcustomised.ChromosomeMaterial} sample from which an initial
 * population is generated.</li>
 * <li>Adds required hidden nodes to the initial
 * {@link org.jgapcustomised.ChromosomeMaterial} sample from which an initial
 * population is generated if
 * {@link com.ojcoleman.ahni.transcriber.HyperNEATTranscriber#HYPERNEAT_LEO_LOCALITY}
 * is enabled.</li>
 * </ul>
 *
 * @author Oliver Coleman
 */
public class HyperNEATConfiguration extends NeatConfiguration implements Configurable {

    private static final Logger logger = Logger.getLogger(HyperNEATConfiguration.class);
    private static final long serialVersionUID = 1L;

    /**
     * The number of evolution runs to perform. If this is more than one then it
     * is highly recommended that random.seed is not set so that a unique seed
     * can be generated for each run,
     */
    public static final String NUM_RUNS_KEY = "num.runs";

    /**
     * Where to save files generated by one or more runs.
     */
    public static final String OUTPUT_DIR_KEY = "output.dir";

    /**
     * Optional prefix to add to all output files.
     */
    public static final String OUTPUT_PREFIX_KEY = "output.prefix";

    /**
     * Whether to display experiment information graphically.
     */
    public static final String ENABLE_VISUALS_KEY = "visuals.enable";

    /**
     * The file name of a seed chromosome in XML format.
     */
    public static final String SEED_CHROMOSOME_KEY = "chrom.seed";

    private Properties props;

    private boolean enableVisuals;

    public void init(Properties newProps) throws InvalidConfigurationException {
        super.init(newProps);
        props = newProps;

        boolean enableLogFiles = props.containsKey(HyperNEATConfiguration.OUTPUT_DIR_KEY);
        if (enableLogFiles) {
            File dirFile = new File(props.getProperty(HyperNEATConfiguration.OUTPUT_DIR_KEY));
            if (!dirFile.exists()) {
                dirFile.mkdirs();
            }
        }

        Transcriber transcriber = (Transcriber) props.singletonObjectProperty(ActivatorTranscriber.TRANSCRIBER_KEY);

        // If a seed chromosome has not been specified, increase the current global ID register
        // to help avoid ID collisions if a Chromsosome from this run is exported and then
        // imported via SEED_CHROMOSOME_KEY in a future run.
        // This is a bit of a hack, we should probably adjust the IDs from imported seed chromosomes
        // to avoid collisions.
        if (!props.containsKey(HyperNEATConfiguration.SEED_CHROMOSOME_KEY)) {
            this.getIdFactory().resetID(this.getIdFactory().nextNoIncrement() + 1000);
            // Sample material has already been generated with non-adjusted IDs, so generate another sample with updated IDs.
            ChromosomeMaterial sample = NeatChromosomeUtility.newSampleChromosomeMaterial(props.getShortProperty(STIMULUS_SIZE_KEY, DEFAULT_STIMULUS_SIZE), props.getShortProperty(INITIAL_TOPOLOGY_NUM_HIDDEN_NEURONS_KEY, DEFAULT_INITIAL_HIDDEN_SIZE), props.getShortProperty(RESPONSE_SIZE_KEY, DEFAULT_RESPONSE_SIZE), this, props.getBooleanProperty(INITIAL_TOPOLOGY_FULLY_CONNECTED_KEY, true));
            setSampleChromosomeMaterial(sample);
        }

        // If a (non-cppn) seed chromosome has been specified.
        // TODO merge this with the INITIAL_CPPN handling code, or discard INITIAL_CPPN?
        if (props.containsKey(HyperNEATConfiguration.SEED_CHROMOSOME_KEY)) {
            try {
                String seedStr = IOUtils.toString(new FileInputStream(props.getProperty(SEED_CHROMOSOME_KEY)), Charset.defaultCharset());
                ChromosomeMaterial seedMaterial = ChromosomeMaterial.fromXML(seedStr);
                if (seedMaterial.getMinInnovationID() < this.getIdFactory().nextNoIncrement()) {
                    throw new InvalidConfigurationException("Invalid property: " + SEED_CHROMOSOME_KEY + ". The smallest innovation ID in the seed has already been assigned.");
                }
                this.getIdFactory().resetID(seedMaterial.getMaxInnovationID() + 1);
                setSampleChromosomeMaterial(ChromosomeMaterial.fromXML(seedStr));
            } catch (FileNotFoundException e) {
                throw new InvalidConfigurationException("Invalid property: " + SEED_CHROMOSOME_KEY + ". Could not find the specified file.");
            } catch (IOException e) {
                throw new InvalidConfigurationException("Invalid property: " + SEED_CHROMOSOME_KEY + ". IOException occurred while reading the specified file:" + e.getMessage());
            }
        } // Else if the transcriber is a TranscriberAdaptor or HyperNEATTranscriber we generate an initial chromosome which encodes 
        // the number of inputs and outputs defined by the transcriber class and/or fitness function and/or hyperneat settings. 
        else if (transcriber instanceof TranscriberAdaptor) {
            TranscriberAdaptor taTranscriber = (TranscriberAdaptor) transcriber;

            short stimulusSize = (short) taTranscriber.getChromosomeInputNeuronCount();
            short responseSize = (short) taTranscriber.getChromosomeOutputNeuronCount();
            boolean fullyConnected = props.getBooleanProperty(INITIAL_TOPOLOGY_FULLY_CONNECTED_KEY, false);

            // Determine extra alleles defined by the transcriber.
            List<Allele> sampleAlleles = taTranscriber.getExtraGenesForInitialChromosome(this);
            if (sampleAlleles == null) {
                sampleAlleles = new ArrayList<Allele>();
            }
            if (!sampleAlleles.isEmpty()) {
                logger.info("Added " + sampleAlleles.size() + " extra genes defined by transcriber.");
            }

            boolean customSampleCreated = false;

            if (transcriber instanceof HyperNEATTranscriber) {
                HyperNEATTranscriber hnTranscriber = ((HyperNEATTranscriber) transcriber);

                String initalCPPNString = props.getProperty(HyperNEATEvolver.INITIAL_CPPN, null);
                if (initalCPPNString != null && props.getBooleanProperty(HyperNEATTranscriber.HYPERNEAT_LEO_LOCALITY, false)) {
                    throw new IllegalArgumentException(HyperNEATEvolver.INITIAL_CPPN + " cannot be specified when " + HyperNEATTranscriber.HYPERNEAT_LEO_LOCALITY + "is enabled.");
                }

                // If a custom initial CPPN has been specified.
                if (initalCPPNString != null) {
                    logger.info("Creating custom Chromosome for initial sample.");
                    if (fullyConnected) {
                        logger.warn("Ignoring " + INITIAL_TOPOLOGY_FULLY_CONNECTED_KEY + " as custom CPPN specified.");
                    }
                    if (props.containsKey(INITIAL_TOPOLOGY_NUM_HIDDEN_NEURONS_KEY) && props.getShortProperty(INITIAL_TOPOLOGY_NUM_HIDDEN_NEURONS_KEY) > 0) {
                        logger.warn("Ignoring specified hidden neuron count for CPPN (" + INITIAL_TOPOLOGY_NUM_HIDDEN_NEURONS_KEY + ") as custom CPPN specified.");
                    }

                    // Generate CPPN Chromosome alleles with only input and output neurons.
                    sampleAlleles.addAll(NeatChromosomeUtility.initAlleles(stimulusSize, (short) 0, responseSize, this, false));
                    // Maps from IDs used in properties file to neuron alleles.
                    HashMap<String, NeuronAllele> neuronMap = new HashMap<String, NeuronAllele>();

                    // Add input neurons to ID/neuron map.
                    int[] idxSrc = new int[]{hnTranscriber.getCPPNIndexSourceX(), hnTranscriber.getCPPNIndexSourceY(), hnTranscriber.getCPPNIndexSourceZ()};
                    int[] idxTrg = new int[]{hnTranscriber.getCPPNIndexTargetX(), hnTranscriber.getCPPNIndexTargetY(), hnTranscriber.getCPPNIndexTargetZ()};
                    int[] idxDlt = new int[]{hnTranscriber.getCPPNIndexDeltaX(), hnTranscriber.getCPPNIndexDeltaY(), hnTranscriber.getCPPNIndexDeltaZ()};
                    List<NeuronAllele> inputNeuronAlleles = new ArrayList<NeuronAllele>(NeatChromosomeUtility.getNeuronMap(sampleAlleles, NeuronType.INPUT).values());
                    // The order of input neuron alleles will match those in a CPPN generated from the Chromosomes.
                    neuronMap.put("b", inputNeuronAlleles.get(hnTranscriber.getCPPNIndexBiasInput()));
                    String[] dimIDs = new String[]{"x", "y", "z"};
                    for (int i = 0; i < 3; i++) {
                        String d = dimIDs[i];
                        if (idxSrc[i] != -1) {
                            neuronMap.put(d + "s", inputNeuronAlleles.get(idxSrc[i]));
                        }
                        if (idxTrg[i] != -1) {
                            neuronMap.put(d + "t", inputNeuronAlleles.get(idxTrg[i]));
                        }
                        if (idxDlt[i] != -1) {
                            neuronMap.put(d + "d", inputNeuronAlleles.get(idxDlt[i]));
                        }
                    }

                    // Add output neurons to ID/neuron map.
                    int[] idxWeight = hnTranscriber.getCPPNIndexWeight();
                    int[] idxBias = hnTranscriber.getCPPNIndexBiasOutput();
                    int[] idxLEO = hnTranscriber.getCPPNIndexLEO();
                    List<NeuronAllele> outputNeuronAlleles = new ArrayList<NeuronAllele>(NeatChromosomeUtility.getNeuronMap(sampleAlleles, NeuronType.OUTPUT).values());
                    // The order of output neuron alleles will match those in a CPPN generated from the Chromosomes.
                    for (int i = 0; i < idxWeight.length; i++) {
                        neuronMap.put("w" + i, outputNeuronAlleles.get(idxWeight[i]));
                        if (i < idxBias.length) {
                            neuronMap.put("b" + i, outputNeuronAlleles.get(idxBias[i]));
                        }
                        if (i < idxLEO.length) {
                            neuronMap.put("l" + i, outputNeuronAlleles.get(idxLEO[i]));
                        }
                    }

                    String[] initialCPPNLines = initalCPPNString.split(",");
                    String[][] initialCPPNValues = new String[initialCPPNLines.length][];
                    // Add specified hidden neurons.
                    for (int i = 0; i < initialCPPNLines.length; i++) {
                        initialCPPNValues[i] = initialCPPNLines[i].split(":");
                        // If this isn't a connection specification then it's a hidden node specification.
                        if (!initialCPPNValues[i][0].trim().equals("c")) {
                            // Create the hidden neuron, add it to the sample alleles and ID/neuron map.
                            NeuronAllele newNeuron = newNeuronAllele(NeuronType.HIDDEN, initialCPPNValues[i][1].trim(), 0);
                            sampleAlleles.add(newNeuron);
                            neuronMap.put(initialCPPNValues[i][0].trim(), newNeuron);
                        }
                    }
                    // Add the connections.
                    for (int i = 0; i < initialCPPNValues.length; i++) {
                        if (initialCPPNValues[i][0].trim().equals("c")) {
                            // Create the connection, add it to sample alleles.
                            String srcID = initialCPPNValues[i][1].trim();
                            NeuronAllele src = neuronMap.get(srcID);
                            if (src == null) {
                                throw new IllegalArgumentException("Could not find specified connection source neuron " + srcID + " in custom CPPN specification in line " + initialCPPNLines[i]);
                            }
                            String trgID = initialCPPNValues[i][2].trim();
                            NeuronAllele trg = neuronMap.get(trgID);
                            if (trg == null) {
                                throw new IllegalArgumentException("Could not find specified connection target neuron " + trgID + " in custom CPPN specification in line " + initialCPPNLines[i]);
                            }
                            double weight = Double.parseDouble(initialCPPNValues[i][3].trim());
                            sampleAlleles.add(newConnectionAllele(src.getInnovationId(), trg.getInnovationId(), weight));
                        }
                    }

                    customSampleCreated = true;
                } // Else if LEO locality seeding is enabled.
                else if (hnTranscriber.leoEnabled() && props.getBooleanProperty(HyperNEATTranscriber.HYPERNEAT_LEO_LOCALITY, false)) {
                    logger.info("Creating initial sample Chromosome with LEO global locality seed.");
                    if (fullyConnected) {
                        logger.warn("It's generally best not to have a fully connected initial CPPN topology (" + INITIAL_TOPOLOGY_FULLY_CONNECTED_KEY + "=true) when LEO locality seeding is enabled (" + HyperNEATTranscriber.HYPERNEAT_LEO_LOCALITY + "=true).");
                    }
                    if (props.containsKey(INITIAL_TOPOLOGY_NUM_HIDDEN_NEURONS_KEY) && props.getShortProperty(INITIAL_TOPOLOGY_NUM_HIDDEN_NEURONS_KEY) > 0) {
                        logger.warn("Ignoring specified hidden neuron count for CPPN (" + INITIAL_TOPOLOGY_NUM_HIDDEN_NEURONS_KEY + ") because LEO locality seeding is enabled (" + HyperNEATTranscriber.HYPERNEAT_LEO_LOCALITY + "=true).");
                    }

                    sampleAlleles.addAll(NeatChromosomeUtility.initAlleles(stimulusSize, (short) 0, responseSize, this, fullyConnected));

                    // Add hidden nodes for locality seed.
                    int[] idxSrc = new int[]{hnTranscriber.getCPPNIndexSourceX(), hnTranscriber.getCPPNIndexSourceY(), hnTranscriber.getCPPNIndexSourceZ()};
                    int[] idxTrg = new int[]{hnTranscriber.getCPPNIndexTargetX(), hnTranscriber.getCPPNIndexTargetY(), hnTranscriber.getCPPNIndexTargetZ()};
                    int[] idxLEO = hnTranscriber.getCPPNIndexLEO();
                    // The order of input and output neuron alleles will match those in a CPPN generated from the Chromosomes.  
                    List<NeuronAllele> inputNeuronAlleles = new ArrayList<NeuronAllele>(NeatChromosomeUtility.getNeuronMap(sampleAlleles, NeuronType.INPUT).values());
                    List<NeuronAllele> outputNeuronAlleles = new ArrayList<NeuronAllele>(NeatChromosomeUtility.getNeuronMap(sampleAlleles, NeuronType.OUTPUT).values());

                    // Create step function neuron.
                    NeuronAllele stepNeuron = newNeuronAllele(NeuronType.HIDDEN, StepActivationFunction.NAME, 0);
                    sampleAlleles.add(stepNeuron);
                    // Connect step function neuron to each leo output.
                    for (int sourceLayer = 0; sourceLayer < idxLEO.length; sourceLayer++) {
                        NeuronAllele leoOutput = outputNeuronAlleles.get(idxLEO[sourceLayer]);
                        sampleAlleles.add(newConnectionAllele(stepNeuron.getInnovationId(), leoOutput.getInnovationId(), 1));
                    }

                    // Create Gaussian neurons for each source and target pair for x, y and z coordinate inputs.
                    int stepNeuronBiasValue = 0;
                    for (int i = 0; i < 3; i++) {
                        if (idxSrc[i] != -1 && idxTrg[i] != -1) {
                            NeuronAllele newNeuron = newNeuronAllele(NeuronType.HIDDEN, GaussianActivationFunction.NAME, 0);
                            sampleAlleles.add(newNeuron);
                            NeuronAllele coordSrc = inputNeuronAlleles.get(idxSrc[i]);
                            NeuronAllele coordTrg = inputNeuronAlleles.get(idxTrg[i]);

                            // Connect it to source and target coordinate inputs.
                            sampleAlleles.add(newConnectionAllele(coordSrc.getInnovationId(), newNeuron.getInnovationId(), 1));
                            sampleAlleles.add(newConnectionAllele(coordTrg.getInnovationId(), newNeuron.getInnovationId(), -1));

                            // Connect it to stepNeuron.
                            sampleAlleles.add(newConnectionAllele(newNeuron.getInnovationId(), stepNeuron.getInnovationId(), 1));

                            stepNeuronBiasValue--; // Bias value for stepNeuron depends on number of inputs; 
                        }
                    }

                    // Add bias to stepNeuron.
                    sampleAlleles.add(newConnectionAllele(inputNeuronAlleles.get(hnTranscriber.getCPPNIndexBiasInput()).getInnovationId(), stepNeuron.getInnovationId(), stepNeuronBiasValue));

                    customSampleCreated = true;
                }
            }

            // Generate default random initial Chromosome if no special-purpose one specified (eg custom or LEO).
            if (!customSampleCreated) {
                sampleAlleles.addAll(NeatChromosomeUtility.initAlleles(stimulusSize, (short) 0, responseSize, this, fullyConnected));
            }

            ChromosomeMaterial sample = new ChromosomeMaterial(sampleAlleles);
            setSampleChromosomeMaterial(sample);
        }

        if (enableLogFiles) {
            try {
                BufferedWriter outputfile = new BufferedWriter(new FileWriter(getOutputDirPath() + getOutputFilesPrefix() + "initial-sample-chromosome.txt"));
                outputfile.write(getSampleChromosomeMaterial().toXML());
                outputfile.close();

                Transcriber cppnTranscriber = (Transcriber) props.singletonObjectProperty(AnjiNetTranscriber.class);
                Activator n = cppnTranscriber.transcribe(new Chromosome(getSampleChromosomeMaterial(), 0L, 0, 0));
                outputfile = new BufferedWriter(new FileWriter(getOutputDirPath() + getOutputFilesPrefix() + "initial-sample-network.txt"));
                outputfile.write(n.toString());
                outputfile.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        enableVisuals = props.getBooleanProperty(ENABLE_VISUALS_KEY, false);
    }

    /**
     * @see HyperNEATConfiguration#init(Properties)
     */
    public HyperNEATConfiguration() {
        super();
    }

    /**
     * See <a href=" {@docRoot} /params.htm" target="anji_params">Parameter
     * Details </a> for specific property settings.
     *
     * @param newProps
     * @see HyperNEATConfiguration#init(Properties)
     * @throws InvalidConfigurationException
     */
    public HyperNEATConfiguration(Properties newProps) throws InvalidConfigurationException {
        super(newProps);
    }

    public boolean visualsEnabled() {
        return enableVisuals;
    }

    /**
     * Returns the path of the directory where files for this experiment should
     * be written to. Includes a trailing slash.
     */
    public String getOutputDirPath() {
        return props.getProperty(HyperNEATConfiguration.OUTPUT_DIR_KEY, null);
    }

    /**
     * Returns the prefix that should be applied to the file names of all output
     * files for this experiment.
     */
    public String getOutputFilesPrefix() {
        return props.getProperty(OUTPUT_PREFIX_KEY, "");
    }

    /**
     * Factory method to construct a new neuron allele with unique innovation
     * ID.
     *
     * @param type The type of neuron allele to create
     * @param activationFunctionType The activation function for the neuron.
     * "random" may be supplied to specify that a function should be randomly
     * selected.
     * @return NeuronAllele
     */
    protected NeuronAllele newNeuronAllele(NeuronType type, String activationFunctionType, double bias) {
        if (activationFunctionType.equals("random")) {
            activationFunctionType = hiddenActivationTypeRandomAllowed[getRandomGenerator().nextInt(hiddenActivationTypeRandomAllowed.length)];
        }
        NeuronGene gene = new NeuronGene(type, nextInnovationId(), activationFunctionType);
        return new NeuronAllele(gene, bias);
    }
}
