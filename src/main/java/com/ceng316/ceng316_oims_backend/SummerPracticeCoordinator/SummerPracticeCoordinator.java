package com.ceng316.ceng316_oims_backend.SummerPracticeCoordinator;

import com.ceng316.ceng316_oims_backend.IztechUser.IztechUser;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class SummerPracticeCoordinator extends IztechUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
