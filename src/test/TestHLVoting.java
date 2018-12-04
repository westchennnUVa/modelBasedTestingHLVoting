package test;

import nz.ac.waikato.modeljunit.LookaheadTester;
import nz.ac.waikato.modeljunit.StopOnFailureListener;
import nz.ac.waikato.modeljunit.Tester;
import nz.ac.waikato.modeljunit.VerboseListener;
import nz.ac.waikato.modeljunit.coverage.ActionCoverage;
import nz.ac.waikato.modeljunit.coverage.StateCoverage;
import nz.ac.waikato.modeljunit.coverage.TransitionCoverage;
import org.junit.Test;

public class TestHLVoting {
    @Test
    public void testFire() throws Exception {
        HLVotingModel hlVotingModel = new HLVotingModel();
//        difference between these two tester
//        Tester tester = new RandomTester(gpaModel);
        Tester tester = new LookaheadTester(hlVotingModel);
//        Tester tester = new AllRoundTester(gpaModel);

        tester.buildGraph();
        tester.addListener(new VerboseListener());
        tester.addListener(new StopOnFailureListener());
        tester.addCoverageMetric(new TransitionCoverage());
        tester.addCoverageMetric(new StateCoverage());
        tester.addCoverageMetric(new ActionCoverage());

        tester.generate(100);
        tester.printCoverage();

    }

}
