package com.javaxpert.labs.poker;

public class HandChecker {

    private boolean handContainsNCardsWithSameRank(Hand targetHand,int thresholdValue){
        return targetHand.getCards().get().groupBy( card -> card.getValue()).filter((value, cards) -> cards.size()==thresholdValue).size()>0;
    }
    public boolean containsPair(Hand targetHand) {
        return handContainsNCardsWithSameRank(targetHand,2);
    }

    public boolean containsThreeOfAKind(Hand targetHand) {
        return handContainsNCardsWithSameRank(targetHand,3);
    }

    public boolean containsFourOfAKind(Hand  targetHand){
        return handContainsNCardsWithSameRank(targetHand,4);
    }
}
