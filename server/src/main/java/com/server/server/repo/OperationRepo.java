package com.server.server.repo;

import com.server.server.domain.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationRepo extends JpaRepository<Operation, Long> {
}
