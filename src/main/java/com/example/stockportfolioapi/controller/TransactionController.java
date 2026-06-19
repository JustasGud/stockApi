package com.example.stockportfolioapi.controller;

import com.example.stockportfolioapi.dto.TransactionRequest;
import com.example.stockportfolioapi.model.StockTransaction;
import com.example.stockportfolioapi.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

/**
 * REST controller that exposes stock transaction API endpoints.
 */
@RestController
public class TransactionController {

    private final TransactionService transactionService;

    /**
     * Constructor injection of TransactionService.
     *
     * @param transactionService service used for transaction operations
     */
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    /**
     * Handles GET /api/stocks/{stockId}/transactions request.
     *
     * @param stockId stock ID
     * @return all transactions of selected stock
     */
    @GetMapping("/api/stocks/{stockId}/transactions")
    public ResponseEntity<CollectionModel<EntityModel<StockTransaction>>> getTransactionsByStockId(
            @PathVariable Long stockId
    ) {
        List<EntityModel<StockTransaction>> transactions = transactionService.getTransactionsByStockId(stockId)
                .stream()
                .map(this::toModel)
                .toList();

        CollectionModel<EntityModel<StockTransaction>> response = CollectionModel.of(
                transactions,
                linkTo(TransactionController.class).slash("api/stocks")
                        .slash(stockId)
                        .slash("transactions")
                        .withSelfRel()
        );

        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS))
                .body(response);
    }

    /**
     * Handles GET /api/transactions/{id} request.
     *
     * @param id transaction ID
     * @return selected transaction
     */
    @GetMapping("/api/transactions/{id}")
    public ResponseEntity<EntityModel<StockTransaction>> getTransactionById(@PathVariable Long id) {
        StockTransaction transaction = transactionService.getTransactionById(id);

        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS))
                .body(toModel(transaction));
    }

    /**
     * Handles POST /api/stocks/{stockId}/transactions request.
     *
     * @param stockId stock ID
     * @param request transaction data
     * @return created transaction
     */
    @PostMapping("/api/stocks/{stockId}/transactions")
    public ResponseEntity<EntityModel<StockTransaction>> createTransaction(
            @PathVariable Long stockId,
            @Valid @RequestBody TransactionRequest request
    ) {
        StockTransaction createdTransaction = transactionService.createTransaction(stockId, request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(toModel(createdTransaction));
    }

    /**
     * Handles DELETE /api/transactions/{id} request.
     *
     * @param id transaction ID
     * @return empty response with 204 status
     */
    @DeleteMapping("/api/transactions/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Converts a StockTransaction into an EntityModel with HATEOAS links.
     *
     * @param transaction transaction entity
     * @return transaction model with related API links
     */
    private EntityModel<StockTransaction> toModel(StockTransaction transaction) {
        return EntityModel.of(
                transaction,
                linkTo(TransactionController.class)
                        .slash("api/transactions")
                        .slash(transaction.getId())
                        .withSelfRel(),
                linkTo(StockController.class)
                        .slash(transaction.getStock().getId())
                        .withRel("stock"),
                linkTo(TransactionController.class)
                        .slash("api/stocks")
                        .slash(transaction.getStock().getId())
                        .slash("transactions")
                        .withRel("stockTransactions"),
                linkTo(TransactionController.class)
                        .slash("api/transactions")
                        .slash(transaction.getId())
                        .withRel("deleteTransaction")
        );
    }
}