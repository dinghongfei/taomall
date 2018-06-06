package com.taomall.pagehelper;

import com.github.pagehelper.PageHelper;
import com.taomall.dao.ItemMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 *
 */
public class PageHelperTest {

    public void testPageHelper() throws Exception{

        PageHelper.startPage(1, 10);
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-dao.xml");
        ItemMapper itemMapper = applicationContext.getBean(ItemMapper.class);



    }


}
