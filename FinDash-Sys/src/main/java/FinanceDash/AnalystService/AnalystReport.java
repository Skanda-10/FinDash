package FinanceDash.AnalystService;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnalystReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;    // The customer (Subscribed User)
    private Long analystId;

    private String reportMonth;

    //Premium features
    @Column(length = 2000)
    private String curatedSuggestion; // The "Course Correction" advice

    @Column(length = 1000)
    private String stockRecommendations; // Investment advice feature

    private boolean isCallScheduled; // On-call analyst status

    private LocalDateTime generatedAt = LocalDateTime.now();
}
