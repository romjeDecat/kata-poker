package com.javaxpert.labs.poker;


import static org.junit.jupiter.api.Assertions.*;

import io.vavr.control.Either;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HandCheckerTest {
  private static Hand typicalHand;
  private static Hand incompleteHand;
  private static Hand oversizedHand;
  private static Hand dummyHand;
  private static Hand handWithPair;
  private HandChecker checker;

  @BeforeAll
  static void setupHands(){
    typicalHand=new Hand();
    typicalHand.addCard(new Card(Suit.SPADE, Rank.KING));
    typicalHand.addCard(new Card(Suit.SPADE, Rank.TEN));
    typicalHand.addCard(new Card(Suit.SPADE, Rank.TWO));
    typicalHand.addCard(new Card(Suit.CLUB, Rank.KING));
    typicalHand.addCard(new Card(Suit.DIAMOND, Rank.EIGHT));

    incompleteHand=new Hand();
    incompleteHand.addCard(new Card(Suit.CLUB, Rank.ACE));
    incompleteHand.addCard(new Card(Suit.CLUB, Rank.TWO));
    incompleteHand.addCard(new Card(Suit.CLUB, Rank.TEN));

    oversizedHand = new Hand();
    oversizedHand.addCard(new Card(Suit.SPADE, Rank.KING));
    oversizedHand.addCard(new Card(Suit.CLUB, Rank.KING));
    oversizedHand.addCard(new Card(Suit.DIAMOND, Rank.KING));
    oversizedHand.addCard(new Card(Suit.HEART, Rank.KING));
    oversizedHand.addCard(new Card(Suit.SPADE, Rank.TEN));
    oversizedHand.addCard(new Card(Suit.SPADE, Rank.JACK));

    dummyHand  = new Hand();
    dummyHand.addCard(new Card(Suit.CLUB,Rank.ACE));
    dummyHand.addCard(new Card(Suit.CLUB,Rank.TWO));
    dummyHand.addCard(new Card(Suit.CLUB,Rank.TEN));
    dummyHand.addCard(new Card(Suit.DIAMOND,Rank.THREE));
    dummyHand.addCard(new Card(Suit.DIAMOND,Rank.FOUR));

    handWithPair = new Hand();
    handWithPair.addCard(new Card(Suit.CLUB, Rank.JACK));
    handWithPair.addCard(new Card(Suit.DIAMOND, Rank.JACK));
    handWithPair.addCard(new Card(Suit.CLUB, Rank.TEN));
    handWithPair.addCard(new Card(Suit.CLUB, Rank.NINE));
    handWithPair.addCard(new Card(Suit.CLUB, Rank.ACE));

  }

  @BeforeEach
  void setupBeforeTest(){
    checker=new HandChecker();
  }


  @Test
  void handShouldContainsExactly5Cards(){
    assertTrue(typicalHand.isComplete());
  assertFalse(incompleteHand.isComplete());
    assertFalse(oversizedHand.isComplete());
  }


  @Test
  void onlyCompleteHandCanBeQueried(){
    assertEquals(incompleteHand.getCards(), Either.left("invalid hand"));
   assertEquals(typicalHand.getCards().get().size(),5);
  }


  @Test
  void shouldDetectOnePair(){
    assertTrue(checker.containsPair(handWithPair));
    assertFalse(checker.containsPair(dummyHand));
  }


}
