package com.masprogtechs.apitownisabel.repositories;

import com.masprogtechs.apitownisabel.models.Month;
import com.masprogtechs.apitownisabel.models.Payment;
import com.masprogtechs.apitownisabel.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface PaymentRepository extends JpaRepository<Payment, Long> {

   /* @Query(value = """
            SELECT EXISTS (
              SELECT 1
              FROM tb_payments AS p
              INNER JOIN payment_month AS pm ON p.id = pm.payment_id
              INNER JOIN tb_months AS m ON pm.month_id = m.id
              INNER JOIN tb_users AS u ON p.user_id = u.id
            );""", nativeQuery = true)
    boolean existsByMonthAndYearAndUser(User user, List<Month> months, Integer year);

    @Query(value = """
        SELECT EXISTS (
          SELECT 1
          FROM tb_payments AS p
          INNER JOIN payment_month AS pm ON p.id = pm.payment_id
          INNER JOIN tb_months AS m ON pm.month_id = m.id
          INNER JOIN tb_users AS u ON p.user_id = u.id
          WHERE u.id = :userId
          AND pm.month_id IN (:monthIds)
          AND p.year = :year
        );""", nativeQuery = true)
    boolean existsByMonthAndYearAndUser(Long userId, List<Long> monthIds, Integer year);

     @Query(value = """
        SELECT m.name
        FROM tb_payments AS p
        INNER JOIN payment_month AS pm ON p.id = pm.payment_id
        INNER JOIN tb_months AS m ON pm.month_id = m.id
        INNER JOIN tb_users AS u ON p.user_id = u.id
        WHERE u.id = :userId
        AND m.id IN (:monthIds)
        AND p.year = :year
        ORDER BY p.created_at
        DESC
        LIMIT :limit;""", nativeQuery = true)
    List<String> findMonthsAlreadyPaidByMonthAndYearAndUser(Long userId, List<Long> monthIds, Integer year, Integer limit);



    @Query(value = """
            SELECT m.name
            FROM tb_payments AS p
            INNER JOIN payment_month AS pm ON p.id = pm.payment_id
            INNER JOIN tb_months AS m ON pm.month_id = m.id
            INNER JOIN tb_users AS u ON p.user_id = u.id
            WHERE u.id = :userId
            AND m.id IN (:monthIds)
            AND p.year = :year;""", nativeQuery = true)
    String findFirstMonthAlreadyPaidByMonthAndYearAndUser(Long userId, List<Long> monthIds, Integer year);*/

    @Query(value = """
        SELECT m.name
        FROM tb_payments AS p
        INNER JOIN payment_month AS pm ON p.id = pm.payment_id
        INNER JOIN tb_months AS m ON pm.month_id = m.id
        INNER JOIN tb_users AS u ON p.user_id = u.id
        WHERE u.id = :userId
        AND m.id IN (:monthIds)
        AND p.year = :year""", nativeQuery = true)
    List<String> findAllMonthsAlreadyPaidByMonthAndYearAndUser(Long userId, List<Long> monthIds, Integer year);




}
