package com.javaxpert.labs.poker;

import io.vavr.Tuple2;
import io.vavr.collection.Array;
import io.vavr.collection.List;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Collectors;

public class HandChecker {

    private boolean handContainsNCardsWithSameRank(Hand targetHand, int thresholdValue) {
        return targetHand.getCards().get().groupBy(card -> card.getRank()).filter((value, cards) -> cards.size() == thresholdValue).size() > 0;
    }

    public boolean containsPair(Hand targetHand) {
        return handContainsNCardsWithSameRank(targetHand, 2);
    }

    public boolean containsThreeOfAKind(Hand targetHand) {
        return handContainsNCardsWithSameRank(targetHand, 3);
    }

    public boolean containsFourOfAKind(Hand targetHand) {
        return handContainsNCardsWithSameRank(targetHand, 4);
    }

    public boolean handContainsStraightFlush(Hand targetHand) {
        return handContainsFlush(targetHand) && handContainsStraight(targetHand);
    }

    /**
     * use a double storage for ranks list because ACE  rank has 2 values
     *
     * @param targetHand
     * @return
     */
    public boolean handContainsStraight(Hand targetHand) {
        java.util.List<Tuple2<Integer, Integer>> rawRanksList = targetHand.getCards().get().map(card -> RankHelper.valueFromRank(card.getRank())).collect(Collectors.toList());
        java.util.List<Tuple2<Integer, Integer>> copyRanksList = new ArrayList<>(rawRanksList);
        Comparator<Tuple2<Integer, Integer>> compareUsingAceMaxValue = (o, t1) -> o._1().compareTo(t1._1());
        Comparator<Tuple2<Integer, Integer>> compareUsingAceMinValue = (o, t1) -> o._2().compareTo(t1._2());

        Collections.sort(rawRanksList, compareUsingAceMaxValue);
        Collections.sort(copyRanksList, compareUsingAceMinValue);
        //Array<Integer> ranksWithAceMax = ranksList.sortBy(compareUsingAceMaxValue,integerIntegerTuple2 -> integerIntegerTuple2._1);
        // Array<Integer> ranksWithAceMin = ranksList.sortBy((o, t1) -> o._2.compareTo(t1._2()));
        //return (ranksWithAceMax.get(4)-ranksWithAceMax.get(0)==4 || ranksWithAceMin.get(4)-ranksWithAceMin.get(0)==4 );
        return (rawRanksList.get(4)._1 - rawRanksList.get(0)._1 == 4 || copyRanksList.get(4)._2 - copyRanksList.get(0)._2 == 4);
    }


    public boolean handContainsFlush(Hand targetHand) {
        return targetHand.getCards().get().groupBy(card ->card.getSuit()).filter((suit, cards) ->cards.size()==5).size()==1;
    }

    public boolean handContainsRoyalFlush(Hand targetHand) {
        boolean  handContainsAce = targetHand.getCards().get().filter(c-> c.getRank()==Rank.ACE).size()==1;
        return  handContainsStraightFlush(targetHand) && handContainsAce;
    }
}
