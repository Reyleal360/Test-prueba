package com.coopcredit.application.domain.ports.in.affiliates;

import com.coopcredit.application.domain.model.Affiliate;
import java.util.List;

public interface ListAffiliatesUseCase {
    List<Affiliate> listAll();
}
