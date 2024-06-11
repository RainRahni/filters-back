package org.filter.model;

import jakarta.persistence.*;
import lombok.*;
import org.filter.model.enums.CriteriaType;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "criteria")
@EqualsAndHashCode(exclude = {"id", "filter"})
public class Criteria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private CriteriaType type;
    private String condition;
    private String metric;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "filter_id")
    private Filter filter;
}
