package com.ntd.challenge.record.v1.repositories.specifications;

import com.ntd.challenge.record.v1.entities.Record;
import jakarta.persistence.criteria.Expression;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.util.List;

public final class RecordSpecifications {

    public static Specification<Record> notDeletedRecord() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.isFalse(root.get("isDeleted"));
    }

    public static Specification<Record> userId(Integer userId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("userId"), userId);
    }

    public static Specification<Record> operationsIdIn(List<Integer> operationsId) {
        return (root, query, criteriaBuilder) -> {
            if (CollectionUtils.isEmpty(operationsId)) {
                return criteriaBuilder.disjunction();
            }

            return root.get("operationId").in(operationsId);
        };
    }

    public static Specification<Record> amountLike(String filter) {
        return (root, query, criteriaBuilder) -> {
            if (filter == null || filter.isEmpty()) {
                return criteriaBuilder.conjunction();
            }

            String normalizedFilter = "%" + filter.toLowerCase() + "%";

            Expression<String> columnString = criteriaBuilder.concat("$", criteriaBuilder.toString(root.get("amount")));

            return criteriaBuilder.like(columnString, normalizedFilter);
        };
    }

    public static Specification<Record> userBalanceLike(String filter) {
        return (root, query, criteriaBuilder) -> {
            if (filter == null || filter.isEmpty()) {
                return criteriaBuilder.conjunction();
            }

            String normalizedFilter = "%" + filter.toLowerCase() + "%";

            Expression<String> columnString = criteriaBuilder.concat("$", criteriaBuilder.toString(root.get("userBalance")));

            return criteriaBuilder.like(columnString, normalizedFilter);
        };
    }

    public static Specification<Record> operationResponseLike(String filter) {
        return (root, query, criteriaBuilder) -> {
            if (filter == null || filter.isEmpty()) {
                return criteriaBuilder.conjunction();
            }

            String normalizedFilter = "%" + filter.toLowerCase() + "%";

            Expression<String> columnString = criteriaBuilder.toString(root.get("operationResponse"));

            return criteriaBuilder.like(columnString, normalizedFilter);
        };
    }

    public static Specification<Record> dateLike(String filter, String timezone) {
        return (root, query, criteriaBuilder) -> {
            if (filter == null || filter.isEmpty()) {
                return criteriaBuilder.conjunction();
            }

            String normalizedFilter = "%" + filter.toLowerCase() + "%";

            Expression<Instant> convertedTimezoneDate = criteriaBuilder.function(
                    "CONVERT_TZ",
                    Instant.class,
                    root.get("date"),
                    criteriaBuilder.literal("UTC"),
                    criteriaBuilder.literal(timezone));

            Expression<String> formattedDate = criteriaBuilder.function(
                    "DATE_FORMAT",
                    String.class,
                    convertedTimezoneDate,
                    criteriaBuilder.literal("%d/%m/%Y %H:%i:%s"));

            return criteriaBuilder.like(formattedDate, normalizedFilter);
        };
    }
}
