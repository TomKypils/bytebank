package com.easybytes.cards.mapper;

import com.easybytes.cards.dto.CardsDto;
import com.easybytes.cards.entity.Cards;

public class CardsMapper {

    public static CardsDto mapToCardsDto(Cards cards, CardsDto cardsDto) {

        cardsDto.setCardNumber(cards.getCardNumber());
        cardsDto.setCardType(cards.getCardType());
        cardsDto.setAmountUsed(cards.getAmountUsed());
        cardsDto.setMobileNumber(cards.getMobileNumber());
        cardsDto.setTotalLimit(cards.getTotalLimit());
        cardsDto.setAvailableAmount(cards.getAvailableAmount());
        return cardsDto;

    }

    public static Cards mapToCards(CardsDto cardsDto, Cards cards) {

        cards.setCardNumber(cardsDto.getCardNumber());
        cards.setCardType(cardsDto.getCardType());
        cards.setAmountUsed(cardsDto.getAmountUsed());
        cards.setMobileNumber(cardsDto.getMobileNumber());
        cards.setTotalLimit(cardsDto.getTotalLimit());
        cards.setAvailableAmount(cardsDto.getAvailableAmount());
        return cards;

    }
}
