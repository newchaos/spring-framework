package com.athm;

import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

public class DebugSpringAwareBean implements EnvironmentAware {

	Environment environment;

	@Override
	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}
}
