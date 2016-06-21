package net.sourceforge.jabm.imagescorepairwise.strategy;

import java.io.Serializable;
import java.util.Comparator;

public class ThresholdStrategyComparator 
		implements Comparator<ImageScoreThresholdStrategy>, Serializable {

	public int compare(ImageScoreThresholdStrategy o1,
			ImageScoreThresholdStrategy o2) {
		ImageScoreThresholdStrategy s1 = (ImageScoreThresholdStrategy) o1;
		ImageScoreThresholdStrategy s2 = (ImageScoreThresholdStrategy) o2;
		if (s1.getThreshold() > s2.getThreshold()) {
			return 1;
		} else if (s1.getThreshold() < s2.getThreshold()) {
			return -1;
		} else {
			return 0;
		}
	}

}
