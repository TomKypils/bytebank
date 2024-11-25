package com.easybytes.cards.service.impl;

import com.easybytes.cards.constants.CardsConstants;
import com.easybytes.cards.dto.CardsDto;
import com.easybytes.cards.entity.Cards;
import com.easybytes.cards.exception.CardAlreadyExistsException;
import com.easybytes.cards.exception.ResourceNotFoundException;
import com.easybytes.cards.mapper.CardsMapper;
import com.easybytes.cards.repository.CardsRepository;
import com.easybytes.cards.service.ICardsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class CardsServiceImpl implements ICardsService {

    private CardsRepository cardsRepository;
    @Override
    public void createCard(String mobileNumber) {
        Optional<Cards> optionalCards = cardsRepository.findByMobileNumber(mobileNumber);
        if(optionalCards.isPresent()) {
            throw new CardAlreadyExistsException("Card already registered with given mobileNumber "
                    +mobileNumber);
        }

        cardsRepository.save(createNewCard(mobileNumber));

    }

    /**
     * @param mobileNumber - Mobile Number of the Customer
     * @return the new card details
     */
    private Cards createNewCard(String mobileNumber) {
        Cards newCard = new Cards();
        long randomLoanNumber = 100000000000L + new Random().nextInt(900000000);
        newCard.setCardNumber(Long.toString(randomLoanNumber));
        newCard.setMobileNumber(mobileNumber);
        newCard.setCardType(CardsConstants.CREDIT_CARD);
        newCard.setTotalLimit(CardsConstants.NEW_CARD_LIMIT);
        newCard.setAmountUsed(0);
        newCard.setAvailableAmount(CardsConstants.NEW_CARD_LIMIT);
        return newCard;
    }

    @Override
    public CardsDto fetchCard(String mobileNumber) {
        Cards card = cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Card","mobileNumber", mobileNumber)
        );
        return CardsMapper.mapToCardsDto(card, new CardsDto());

    }

    @Override
    public boolean updateCard(CardsDto cardsDto) {
        boolean isUpdated = false;
        if(cardsDto !=null ) {
            String cardNumber = cardsDto.getCardNumber();
            Cards card = cardsRepository.findByCardNumber(cardNumber).orElseThrow(
                    () -> new ResourceNotFoundException("Loan", "LoanNumber", cardNumber)
            );
            cardsRepository.save(CardsMapper.mapToCards(cardsDto,card));
            isUpdated = true;
        }
        return isUpdated;

    }

    @Override
    public void deleteCard(String mobileNumber) {
        Cards card = cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber)
        );
        cardsRepository.deleteById(card.getCardId());
    }
}
