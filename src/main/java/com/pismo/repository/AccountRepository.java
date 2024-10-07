package com.pismo.repository;

import com.pismo.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository  extends JpaRepository<Account, Long> {
    boolean existsByDocumentNumber(String documentNumber);
}
