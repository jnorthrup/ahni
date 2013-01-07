package ojc.ahni.experiments.objectrecognition;

import ojc.ahni.*;
import ojc.ahni.evaluation.HyperNEATFitnessFunction;
import ojc.ahni.hyperneat.HyperNEATEvolver;
import ojc.ahni.hyperneat.Properties;
import ojc.ahni.nn.GridNet;
import ojc.ahni.transcriber.HyperNEATTranscriber;

import org.apache.log4j.Logger;
import org.jgapcustomised.*;

import com.anji.integration.*;
import com.anji.nn.*;

/**
 * Determines fitness based on how close <code>Activator</code> output is to a target.
 */
public class ObjectRecognitionFitnessFunction2 extends HyperNEATFitnessFunction {
	private double[][][] stimuli;
	private int[][] targetCoords;
	private int maxFitnessValue;

	private final static int numTrials = 100;
	private final static int numSmallSquares = 1;
	private int smallSquareSize = 1;
	private int largeSquareSize = smallSquareSize * 3;

	/**
	 * See <a href=" {@docRoot} /params.htm" target="anji_params">Parameter Details </a> for specific property settings.
	 * 
	 * @param props configuration parameters
	 */
	public void init(Properties props) {
		super.init(props);
		setMaxFitnessValue();
	}

	private void setMaxFitnessValue() {
		int deltaAdjust = 1 + largeSquareSize / 2; // max delta (in x or y dimension) is width or height of the field -1
													// - min distance the centre of the large square can be from the
													// edge of the board
		int maxXDelta = inputWidth - deltaAdjust;
		int maxYDelta = inputHeight - deltaAdjust;
		double maxDistance = (double) Math.sqrt(maxXDelta * maxXDelta + maxYDelta * maxYDelta);
		maxFitnessValue = (int) Math.ceil(maxDistance * 1000); // fitness is given by maxFitnessValue - avg of distances
																// * 1000
	}

	/**
	 * @return maximum possible fitness value for this function
	 */
	public int getMaxFitnessValue() {
		return maxFitnessValue;
	}

	/**
	 * Iterates through chromosomes. For each, transcribe it to an <code>Activator</code> and present the stimuli to the
	 * activator. The stimuli are presented in random order to ensure the underlying network is not memorizing the
	 * sequence of inputs. Calculation of the fitness based on error is delegated to the subclass. This method adjusts
	 * fitness for network size, based on configuration.
	 */
	public void initialiseEvaluation() {
		// generate trials
		stimuli = new double[numTrials][inputHeight][inputWidth];
		targetCoords = new int[numTrials][2];

		for (int t = 0; t < numTrials; t++) {
			// randomly place large square
			int xpos = random.nextInt(inputWidth - largeSquareSize);
			int ypos = random.nextInt(inputHeight - largeSquareSize);
			for (int y = ypos; y < ypos + largeSquareSize; y++) {
				for (int x = xpos; x < xpos + largeSquareSize; x++) {
					stimuli[t][y][x] = 1;
				}
			}
			targetCoords[t][0] = xpos + (int) (largeSquareSize - 1) / 2; // assumes odd size
			targetCoords[t][1] = ypos + (int) (largeSquareSize - 1) / 2;

			// randomly place small squares to not overlap large square or each other
			for (int s = 0; s < numSmallSquares; s++) {
				while (true) {
					xpos = random.nextInt(inputWidth);
					ypos = random.nextInt(inputHeight);
					if (stimuli[t][ypos][xpos] == 0) { // if not overlapping
						stimuli[t][ypos][xpos] = 1;
						break;
					}
				}
			}
		}
	}

	protected int evaluate(Chromosome genotype, Activator activator, int threadIndex) {
		GridNet substrate = (GridNet) activator;
		// if (genotype.size() > 100)
		// return 0;
		double[][][] responses = substrate.nextSequence(stimuli);

		int totalSqrDists = 0;
		double totalDists = 0;
		for (int t = 0; t < numTrials; t++) {
			// find output with highest response
			int xh = 0;
			int yh = 0;
			for (int y = 0; y < inputHeight; y++) {
				for (int x = 0; x < inputWidth; x++) {
					if (responses[t][y][x] > responses[t][yh][xh]) {
						xh = x;
						yh = y;
					}
				}
			}

			int deltaX = xh - targetCoords[t][0];
			int deltaY = yh - targetCoords[t][1];
			int sqrDist = deltaX * deltaX + deltaY * deltaY;
			totalSqrDists += sqrDist;
			totalDists += Math.sqrt(sqrDist);
		}

		genotype.setPerformanceValue(totalDists / numTrials);
		return maxFitnessValue - (int) Math.round(((double) (totalDists / numTrials)) * 1000);
	}

	@Override
	protected void scale(int scaleCount, int scaleFactor, HyperNEATTranscriber transcriber) {
		// adjust shape size
		largeSquareSize *= scaleFactor;
		smallSquareSize *= scaleFactor;
		setMaxFitnessValue();
	}
}
