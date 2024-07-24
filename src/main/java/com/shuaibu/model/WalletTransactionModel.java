package com.shuaibu.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "TRANSACTION_MODEL")
public class WalletTransactionModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fromAccount;
    private String bankName;
    private String transactionDate;
    private String termName;
    private String sessionName;
    private Double amount;
    private Long walletId;
}
