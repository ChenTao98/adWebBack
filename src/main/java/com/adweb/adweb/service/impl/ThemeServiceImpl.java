package com.adweb.adweb.service.impl;

import com.adweb.adweb.dao.ThemeDao;
import com.adweb.adweb.entity.Theme;
import com.adweb.adweb.entity.ThemeExample;
import com.adweb.adweb.service.ThemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ThemeServiceImpl implements ThemeService{
    @Autowired
    private ThemeDao themeDao;
    @Override
    public List<Theme> getAllTheme() {
        return themeDao.selectByExample(new ThemeExample());
    }
}
