package com.ehxy.tsg.service;

import com.ehxy.tsg.entity.Favorite;
import com.ehxy.tsg.mapper.FavoriteMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FavoriteService {
    @Autowired
    private FavoriteMapper favoriteMapper;

    public Favorite selectById(Long id) {
        return favoriteMapper.selectById(id);
    }

    public List<Favorite> selectByReaderId(Long readerId) {
        return favoriteMapper.selectByReaderId(readerId);
    }

    public Favorite selectByReaderIdAndBookId(Long readerId, Long bookId) {
        return favoriteMapper.selectByReaderIdAndBookId(readerId, bookId);
    }

    public int insert(Favorite favorite) {
        return favoriteMapper.insert(favorite);
    }

    public int deleteById(Long id) {
        return favoriteMapper.deleteById(id);
    }

    public int deleteByReaderIdAndBookId(Long readerId, Long bookId) {
        return favoriteMapper.deleteByReaderIdAndBookId(readerId, bookId);
    }

    public int countByBookId(Long bookId) {
        return favoriteMapper.countByBookId(bookId);
    }

    public int countByReaderId(Long readerId) {
        return favoriteMapper.countByReaderId(readerId);
    }

    public boolean isFavorite(Long readerId, Long bookId) {
        return favoriteMapper.selectByReaderIdAndBookId(readerId, bookId) != null;
    }

    public void toggleFavorite(Long readerId, Long bookId) {
        Favorite existing = favoriteMapper.selectByReaderIdAndBookId(readerId, bookId);
        if (existing != null) {
            favoriteMapper.deleteById(existing.getId());
        } else {
            Favorite favorite = new Favorite();
            favorite.setReaderId(readerId);
            favorite.setBookId(bookId);
            favoriteMapper.insert(favorite);
        }
    }
}
