package Sim;

import org.apdplat.word.analysis.*;

/**
 * Created by jiahuiyu on 2017/9/5.
 */
public interface TextSimilarityInterface extends org.apdplat.word.analysis.Similarity {
    public double similarityScore(Object v_id, Object weightlist);
}
