package com.ehxy.tsg.service;

import com.ehxy.tsg.entity.Announcement;
import com.ehxy.tsg.mapper.AnnouncementMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AnnouncementService {
    @Autowired
    private AnnouncementMapper announcementMapper;

    public Announcement selectById(Long id) {
        return announcementMapper.selectById(id);
    }

    public List<Announcement> selectActiveAnnouncements() {
        return announcementMapper.selectActiveAnnouncements();
    }

    public List<Announcement> selectAll() {
        return announcementMapper.selectAll();
    }

    public int insert(Announcement announcement) {
        return announcementMapper.insert(announcement);
    }

    public int update(Announcement announcement) {
        return announcementMapper.update(announcement);
    }

    public int deleteById(Long id) {
        return announcementMapper.deleteById(id);
    }
}
