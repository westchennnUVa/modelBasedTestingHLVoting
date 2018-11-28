package test;

import nz.ac.waikato.modeljunit.FsmModel;

import java.util.ArrayList;

public class HLVotingModel implements FsmModel {

    private enum GradeBoxState {Empty, NotEmpty}

    // Maybe change NotEmpty to OneGradeBox, TwoGradeBoxes, MoreThanTwoGradeBoxes, then use the gpa method to check its gpa
    private enum State {EmptyGradeBoxEmptySummary, NotEmptyGradeBoxEmptySummary,
        NotEmptyGradeBoxLatestSummary, NotEmptyGradeBoxOldSummary, InvalidState};

    State state = State.EmptyGradeBoxEmptySummary;

    public Object getState() {return state;}

    public void reset(boolean b) {

    }
}
