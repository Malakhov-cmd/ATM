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

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class OperationService {
    private OperationRepo operationRepo;
    private CardRepo cardRepo;

    private DataObjectParser dataObjectParser;

    public void createOperation (OperationDTO operationDTO) {
        Card card = cardRepo.findByNumber(operationDTO.getCardNumber());
        Operation newOperation = fillingOperation(operationDTO, card);

        operationRepo.save(newOperation);

        addNewOperationToCardAndCorrectBalance(card, newOperation);
    }

    private Operation fillingOperation(OperationDTO operationDTO, Card card) {
        Operation newOperation = dataObjectParser.cardOperationDTOtoCardOperationDAO(operationDTO);

        newOperation.setCard(card);
        return newOperation;
    }

    private void addNewOperationToCardAndCorrectBalance(Card card, Operation newOperation) {
        List<Operation> operationList = card.getOperations();
        operationList.add(newOperation);

        card.setOperations(operationList);

        if (newOperation.getType().equals("Withdraw")) {
            card.setBalance(card.getBalance() - newOperation.getValue());
        } else {
            card.setBalance(card.getBalance() + newOperation.getValue());
        }

        cardRepo.save(card);
    }
}
