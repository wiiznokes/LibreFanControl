#include "sensors_wrapper.h"

char* get_chip_name(const sensors_chip_name* chip)
{
    return chip->prefix;
}


char* get_feature_name(const sensors_feature* feat)
{
    return feat->name;
}


custom_sensors_feature_type convert_feature_type(const sensors_feature_type type)
{
    switch (type)
    {
    case SENSORS_FEATURE_FAN: return sensors_feature_fan;
    case SENSORS_FEATURE_TEMP: return sensors_feature_temp;
    default: return sensors_feature_undefined;
    }
}


int get_feature_type(const sensors_feature* feat)
{
    return convert_feature_type(feat->type);
}

int get_feature_number(const sensors_feature* feat)
{
    return feat->number;
}


char* get_sub_feature_name(const sensors_subfeature* sub_feat)
{
    return sub_feat->name;
}



custom_sensors_sub_feature_type convert_sub_feature_type(const sensors_subfeature_type type)
{
    switch (type)
    {
    case SENSORS_SUBFEATURE_FAN_INPUT: return sensors_sub_feature_fan_input;
    case SENSORS_SUBFEATURE_TEMP_INPUT: return sensors_sub_feature_temp_input;
    default: return sensors_sub_feature_undefined;
    }
}


int get_sub_feature_type(const sensors_subfeature* sub_feat)
{
    return convert_sub_feature_type(sub_feat->type);
}

int get_sub_feature_number(const sensors_chip_name* chip, const sensors_feature* feat,
    custom_sensors_sub_feature_type type)
{
    sensors_subfeature const *sub_feature;
    int nr = 0;

    while ((sub_feature = sensors_get_all_subfeatures(chip, feat, &nr)) != 0) {
        const custom_sensors_sub_feature_type feature_type = convert_sub_feature_type(sub_feature->type);
        if (feature_type == type)
        {
            return sub_feature->number;
        }
    }
    
    return -1;
}
