/*
 * Copyright 2024-2024 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.grpc.test;

import java.util.List;
import java.util.Objects;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfigurationAttributes;
import org.springframework.test.context.ContextCustomizer;
import org.springframework.test.context.ContextCustomizerFactory;
import org.springframework.test.context.MergedContextConfiguration;
import org.springframework.test.context.TestContextAnnotationUtils;

class InProcessTransportContextCustomizerFactory implements ContextCustomizerFactory {

	static final String AUTO_CONFIGURE_PROPERTY = "spring.grpc.inprocess.auto-configure";

	@Override
	public ContextCustomizer createContextCustomizer(Class<?> testClass,
			List<ContextConfigurationAttributes> configAttributes) {
		AutoConfigureInProcessTransport annotation = TestContextAnnotationUtils.findMergedAnnotation(testClass,
				AutoConfigureInProcessTransport.class);
		return new InProcessTransportContextCustomizer(annotation);
	}

	private static class InProcessTransportContextCustomizer implements ContextCustomizer {

		private final AutoConfigureInProcessTransport annotation;

		InProcessTransportContextCustomizer(AutoConfigureInProcessTransport annotation) {
			this.annotation = annotation;
		}

		@Override
		public void customizeContext(ConfigurableApplicationContext context, MergedContextConfiguration mergedConfig) {
			if (isEnabled(context.getEnvironment())) {
				TestPropertyValues.of("spring.grpc.inprocess.enabled=true").applyTo(context);
			}
		}

		private boolean isEnabled(Environment environment) {
			if (this.annotation != null) {
				return this.annotation.enabled();
			}

			return environment.getProperty(AUTO_CONFIGURE_PROPERTY, Boolean.class, false);
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) {
				return true;
			}
			if (o == null || getClass() != o.getClass()) {
				return false;
			}

			InProcessTransportContextCustomizer that = (InProcessTransportContextCustomizer) o;

			return Objects.equals(this.annotation, that.annotation);
		}

		@Override
		public int hashCode() {
			return Objects.hash(annotation);
		}

	}

}
