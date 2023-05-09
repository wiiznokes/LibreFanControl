#ifndef SENSORS_WRAPPER_H
#define SENSORS_WRAPPER_H

#include <stdio.h>

#include "include/sensors.h"
#include "include/error.h"


typedef enum
{
    sensors_feature_fan = 1,
    sensors_feature_temp = 2,
    sensors_feature_pwm = 3,
    sensors_feature_undefined = INT_MAX
} custom_sensors_feature_type;


typedef enum
{
    sensors_sub_feature_fan_input = 1,
    sensors_sub_feature_temp_input = 2,
    sensors_sub_feature_pwm_io = 3,
    sensors_sub_feature_pwm_enable = 4,
    sensors_sub_feature_undefined = INT_MAX
} custom_sensors_sub_feature_type;

/* CHIP **************/

char* get_chip_name(const sensors_chip_name* chip);


/* FEATURE **************/

char* get_feature_name(const sensors_feature* feat);

int get_feature_type(const sensors_feature* feat);

int get_feature_number(const sensors_feature* feat);


/* SUB_FEATURE **************/

char* get_sub_feature_name(const sensors_subfeature* sub_feat);

int get_sub_feature_type(const sensors_subfeature* sub_feat);

int get_sub_feature_number(const sensors_chip_name* chip, const sensors_feature* feat,
                           custom_sensors_sub_feature_type type);


#endif
