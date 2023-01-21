package mpti.domain.business.application;

import lombok.RequiredArgsConstructor;
import mpti.domain.business.dao.OpinionRepository;
import mpti.domain.business.dao.ReservationRepository;
import mpti.domain.business.entity.Opinion;
import mpti.domain.business.entity.Report;
import mpti.domain.business.entity.Review;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OpinionService {

    private final OpinionRepository opinionRepository;


    public List<Review> loadReviews() {
        return opinionRepository.findAllReviews();
    }

    public List<Review> loadReviewsByUserId(Long userId) {
        return opinionRepository.findAllReviewsByUserId(userId);
    }

    public void writeReview(Long userId, Long trainerId, Double star, String memo) {
        Review review = new Review();
        review.setUserId(userId);
        review.setTrainerId(trainerId);
        review.setStar(star);
        review.setMemo(memo);

        opinionRepository.saveReview(review);

    }


    public Review loadReviewDetail(Long reviewId) {
        return opinionRepository.findOneReview(reviewId);
    }

    public void writeReport(Long userId, Long trainerId, String memo) {
        Report report = new Report();
        report.setUserId(userId);
        report.setTrainerId(trainerId);
        report.setMemo(memo);

        opinionRepository.saveReport(report);
    }

    public List<Report> loadReports() {
        return opinionRepository.findAllReports();
    }

    public Report loadReport(Long reportId) {
        return opinionRepository.findOneReport(reportId);
    }

    public LocalDateTime processReport(Long reportId, int blockPeriod) {
        Report report = this.loadReport(reportId);
        LocalDateTime stopUntil = report.setStopUntil(blockPeriod);
        return stopUntil;
    }
}
