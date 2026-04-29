package com.ehxy.tsg.service;

import com.ehxy.tsg.entity.Review;
import com.ehxy.tsg.mapper.ReviewMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReviewService {
    @Autowired
    private ReviewMapper reviewMapper;

    public Review selectById(Long id) {
        return reviewMapper.selectById(id);
    }

    public List<Review> selectByBookId(Long bookId) {
        return reviewMapper.selectByBookId(bookId);
    }

    public List<Review> selectByReaderId(Long readerId) {
        return reviewMapper.selectByReaderId(readerId);
    }

    public List<Review> selectAll() {
        return reviewMapper.selectAll();
    }

    public Review selectByReaderIdAndBookId(Long readerId, Long bookId) {
        return reviewMapper.selectByReaderIdAndBookId(readerId, bookId);
    }

    public int insert(Review review) {
        return reviewMapper.insert(review);
    }

    public int update(Review review) {
        return reviewMapper.update(review);
    }

    public int deleteById(Long id) {
        return reviewMapper.deleteById(id);
    }

    public Double getAverageRatingByBookId(Long bookId) {
        return reviewMapper.getAverageRatingByBookId(bookId);
    }

    public int countByBookId(Long bookId) {
        return reviewMapper.countByBookId(bookId);
    }

    public void addOrUpdateReview(Long readerId, Long bookId, Integer rating, String content) {
        Review existing = reviewMapper.selectByReaderIdAndBookId(readerId, bookId);
        if (existing != null) {
            existing.setRating(rating);
            existing.setContent(content);
            reviewMapper.update(existing);
        } else {
            Review review = new Review();
            review.setReaderId(readerId);
            review.setBookId(bookId);
            review.setRating(rating);
            review.setContent(content);
            review.setStatus(1);
            reviewMapper.insert(review);
        }
    }
}
