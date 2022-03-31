package com.server.server.service;

import com.server.server.domain.Card;
import com.server.server.domain.Operation;
import com.server.server.dto.OperationDTO;
import com.server.server.repo.CardRepo;
import com.server.server.repo.OperationRepo;
import com.server.server.service.utils.DataObjectParser;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class OperationService {
    private OperationRepo operationRepo;
    private CardRepo cardRepo;

    private DataObjectParser dataObjectParser;

    public synchronized boolean createOperation(OperationDTO operationDTO) {
        Card card = cardRepo.findByNumber(operationDTO.getCardNumber());
        Operation newOperation = dataObjectParser.cardOperationDTOtoCardOperationDAO(operationDTO).setCard(card);

        return addNewOperationToCardAndCorrectBalance(card, newOperation);
    }

    private boolean addNewOperationToCardAndCorrectBalance(Card card, Operation newOperation) {
        card.getOperations().add(newOperation);

        if (newOperation.getType().equals("Withdraw")) {
            if (card.getBalance() > newOperation.getValue()) {
                card.setBalance(card.getBalance() - newOperation.getValue());

                savingValues(card, newOperation);
                return true;
            }
        } else {
            card.setBalance(card.getBalance() + newOperation.getValue());
            savingValues(card, newOperation);
            return true;
        }
        return false;
    }

    private void savingValues(Card card, Operation newOperation) {
        operationRepo.save(newOperation);
        cardRepo.save(card);
    }
}
