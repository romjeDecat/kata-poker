package com.javaxpert.labs.poker;

public class HandChecker {
    public boolean containsPair(Hand targetHand) {
        return targetHand.getCards().get().groupBy( card -> card.getValue()).filter((value, cards) -> cards.size()==2).size()>0;
    }

    public boolean containsThreeOfAKind(Hand targetHand) {
        return targetHand.getCards().get().groupBy( card -> card.getValue()).filter((value, cards) -> cards.size()==3).size()>0;
    }
}
