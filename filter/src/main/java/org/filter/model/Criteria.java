package org.filter.model;

import jakarta.persistence.*;
import lombok.*;
import org.filter.model.enums.CriteriaType;

import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "criterias")
public class Criteria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private CriteriaType type;
    private String comparator;
    private String metric;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "filter_id")
    private Filter filter;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Criteria criteria = (Criteria) o;
        return type == criteria.type
                && Objects.equals(comparator, criteria.comparator)
                && Objects.equals(metric, criteria.metric);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, comparator, metric);
    }
}
