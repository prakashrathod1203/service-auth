package om.auth.helper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import om.auth.library.enums.LogicEnum;
import om.auth.library.model.dto.request.filter.FilterCriterion;
import om.auth.library.model.dto.request.filter.SortRequest;

public class DynamicSpecificationBuilder {

    public static Pageable buildPageRequest(int page, int size, List<SortRequest> sortRequests) {
        if (sortRequests == null || sortRequests.isEmpty()) {
            return PageRequest.of(page, size);
        }

        List<Sort.Order> orders = sortRequests.stream()
                .map(sr -> new Sort.Order(
                        "desc".equalsIgnoreCase(sr.getDirection()) ? Sort.Direction.DESC
                                : Sort.Direction.ASC,
                        sr.getField()))
                .toList();

        return PageRequest.of(page, size, Sort.by(orders));
    }

    public static <T> Specification<T> build(List<FilterCriterion> criteriaList) {
        return (root, query, cb) -> {
            List<Predicate> andPredicates = new ArrayList<>();
            List<Predicate> orPredicates = new ArrayList<>();

            for (FilterCriterion criterion : criteriaList) {
                Path<?> path;
                if (criterion.getField().contains(".")) {
                    // Handle nested fields like translations.title
                    String[] parts = criterion.getField().split("\\.");
                    From<?, ?> join = root;
                    for (int i = 0; i < parts.length - 1; i++) {
                        join = join.join(parts[i], JoinType.LEFT);
                    }
                    path = join.get(parts[parts.length - 1]);
                } else {
                    path = root.get(criterion.getField());
                }

                Predicate predicate = null;

                switch (criterion.getOperator()) {
                    case IN -> {
                        CriteriaBuilder.In<Object> inClause = cb.in(path);
                        criterion.getValues().forEach(val -> inClause.value(convert(path, val)));
                        predicate = inClause;
                    }
                    case LIKE -> {
                        List<Predicate> likePreds = criterion.getValues().stream()
                                .map(val -> cb.like(cb.lower(path.as(String.class)),
                                        "%" + val.toLowerCase() + "%"))
                                .toList();
                        predicate = cb.or(likePreds.toArray(new Predicate[0]));
                    }
                    case EQUALS -> {
                        if (criterion.getValues().size() == 1) {
                            predicate = cb.equal(path, convert(path, criterion.getValues().get(0)));
                        } else {
                            CriteriaBuilder.In<Object> inClause = cb.in(path);
                            criterion.getValues()
                                    .forEach(val -> inClause.value(convert(path, val)));
                            predicate = inClause;
                        }
                    }
                }

                if (predicate != null) {
                    if (criterion.getLogic() == LogicEnum.AND) {
                        andPredicates.add(predicate);
                    } else {
                        orPredicates.add(predicate);
                    }
                }
            }

            Predicate andCombined = andPredicates.isEmpty() ? cb.conjunction()
                    : cb.and(andPredicates.toArray(new Predicate[0]));

            Predicate orCombined = orPredicates.isEmpty() ? cb.conjunction()
                    : cb.or(orPredicates.toArray(new Predicate[0]));

            return cb.and(andCombined, orCombined);
        };
    }

    private static Object convert(Path<?> path, String value) {
        Class<?> type = path.getJavaType();
        if (type.equals(Boolean.class))
            return Boolean.parseBoolean(value);
        if (type.equals(Integer.class))
            return Integer.parseInt(value);
        if (type.equals(Long.class))
            return Long.parseLong(value);
        if (type.equals(Double.class))
            return Double.parseDouble(value);
        if (type.equals(LocalDate.class))
            return LocalDate.parse(value);
        return value;
    }

}
