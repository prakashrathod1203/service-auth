package tech.sarthee.auth.library.model.dto.resource;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import tech.sarthee.auth.library.util.CommonUtils;

/**
 * @author Prakash Rathod
 */
@Converter(autoApply = true)
public class UserResourceJpaConverterJson implements AttributeConverter<UserResource, String> {
    @Override
    public String convertToDatabaseColumn(UserResource userResource) {
        return CommonUtils.objectToJsonString(userResource);
    }

    @Override
    public UserResource convertToEntityAttribute(String s) {
        return CommonUtils.jsonStringToPojoConverter(s, UserResource.class);
    }
}
