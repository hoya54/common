package mpti.domain.business.dao;

import lombok.RequiredArgsConstructor;
import mpti.domain.business.entity.Opinion;
import mpti.domain.business.entity.Report;
import mpti.domain.business.entity.Reservation;
import mpti.domain.business.entity.Review;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class OpinionRepository {

    private final EntityManager em;

    public List<Review> findAllReviews() {
        return em.createQuery("select r from Review r", Review.class)
                .getResultList();
    }

    public List<Review> findAllReviewsByUserId(Long userId) {
        return em.createQuery("select r from Review r where r.userId = :userId", Review.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    public void saveReview(Review review) {
        em.persist(review);
    }


    public Review findOneReview(Long reviewId) {
        return em.find(Review.class, reviewId);
    }

    public void saveReport(Report report) {
        em.persist(report);
    }

    public List<Report> findAllReports() {
        return em.createQuery("select r from Report r", Report.class)
                .getResultList();
    }

    public Report findOneReport(Long reportId) {
        return em.find(Report.class, reportId);
    }

}
