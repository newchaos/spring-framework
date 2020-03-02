package com.athm.factorybean;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.util.StringUtils;

public class CarFactoryBean implements FactoryBean<Car> {

	private String carInfo;

	@Override
	public Car getObject() throws Exception {
		Car car = new Car();
		if (StringUtils.hasText(carInfo)) {
			String[] infos = carInfo.split(",");
			car.setBrand(infos[0]);
			car.setMaxSpeed(Integer.valueOf(infos[1]));
			car.setPrice(Double.valueOf(infos[2]));
		}else {
			car.setBrand("内部初始化品牌");
			car.setMaxSpeed(10000);
			car.setPrice(2000);
		}

		return car;
	}

	@Override
	public Class<?> getObjectType() {
		return Car.class;
	}

	@Override
	public boolean isSingleton() {
		return false;
	}

	public String getCarInfo() {
		return this.carInfo;
	}

	// 接受逗号分隔设置属性信息;
	public void setCarInfo(String carInfo) {
		this.carInfo = carInfo;
	}
}
