package io.gtrain.domain.interfaces;

import io.gtrain.domain.model.Name;
import io.gtrain.domain.model.ScholasticInfo;
import io.gtrain.domain.model.PhoneNumber;
import org.springframework.util.StringUtils;

/**
 * @author William Gentry
 */
public interface ContactableMember extends Contactable {

	PhoneNumber getPhoneNumber();

	ScholasticInfo getScholasticInfo();

	Name getName();
}
