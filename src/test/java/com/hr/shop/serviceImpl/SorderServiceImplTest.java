package com.hr.shop.serviceImpl;

import com.hr.shop.service.SorderService;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext-*.xml")
public class SorderServiceImplTest {

	@Resource
	private SorderService sorderService;
	

}

