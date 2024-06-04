package org.filter.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.filter.model.enums.CriteriaType;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Criteria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private CriteriaType type;
    private String comparator;
    private String value;
    @ManyToOne
    @JoinColumn(name = "filter_id")
    private Filter filter;
}
