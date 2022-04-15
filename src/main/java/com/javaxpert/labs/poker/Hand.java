package com.javaxpert.labs.poker;

import io.vavr.collection.List;
import io.vavr.control.Either;
import java.util.HashSet;
import java.util.Set;

public class Hand {
    private Set<Card> cards;

    public Hand(){
        cards = new HashSet<>();
    }

    public boolean isComplete() {
        return cards.size()==5;
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public Either<String,List<Card>> getCards(){
        if(isComplete()) {
            return Either.right(List.ofAll(cards));
        }
        else{
            System.out.println("hand is not complete");
            return Either.left("invalid hand");
        }

    }
}
