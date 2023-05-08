#include "sensors_wrapper.h"


char* get_chip_name(const sensors_chip_name* chip)
{
    return chip->prefix;
}


char* get_feature_name(const sensors_feature* feat)
{
    return feat->name;
}

int get_feature_type(const sensors_feature* feat)
{
    switch (feat->type)
    {
    case SENSORS_FEATURE_FAN: return sensors_feature_fan;
    case SENSORS_FEATURE_TEMP: return sensors_feature_temp;
    default: return undefined;
    }
}

int get_feature_number(const sensors_feature* feat)
{
    return feat->number;
}


char* get_sub_feature_name(const sensors_subfeature* sub_feat)
{
    return sub_feat->name;
}

int get_sub_feature_type(const sensors_subfeature* sub_feat)
{
    return sub_feat->type;
}

int get_sub_feature_number(const sensors_subfeature* sub_feat)
{
    return sub_feat->number;
}
