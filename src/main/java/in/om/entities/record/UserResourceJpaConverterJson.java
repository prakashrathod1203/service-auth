package in.om.entities.record;

import in.om.utility.CommonUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

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
