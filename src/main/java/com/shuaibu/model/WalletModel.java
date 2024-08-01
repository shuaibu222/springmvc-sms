package com.shuaibu.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class WalletModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double balance;
    private Long studentId;
    private String studentName;
    private String studentClass;
    private String phoneNumber;
    private String regNo;
    private String admissionType;
    private String isActive;
    // New field to track payment status
    @Builder.Default
    private Boolean hasPaid = false;

    @ElementCollection
    private List<Long> transactionIds;
}
