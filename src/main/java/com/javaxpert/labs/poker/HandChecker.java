package com.javaxpert.labs.poker;

import java.util.stream.Collectors;

public class HandChecker {

    private boolean handContainsNCardsWithSameRank(Hand targetHand,int thresholdValue){
        return targetHand.getCards().get().groupBy( card -> card.getRank()).filter((value, cards) -> cards.size()==thresholdValue).size()>0;
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

    public boolean handContainsStraightFlush(Hand targetHand){
        return targetHand.getCards().get().groupBy(card -> card.getSuit()).filter((suit, cards) -> cards.size()==5).size()==1;
    }

  /*  public boolean handContainsStraight(Hand targetHand) {
        java.util.List<Rank> ranksList = targetHand.getCards().get().map(card -> card.getRank()).collect(Collectors.toList());
        ranksList.sort((rank1, rank2) ->rank1.compareTo(rank2) );

        return (ranksList.get(4)-ranksList.get(0) );
    }*/
}
