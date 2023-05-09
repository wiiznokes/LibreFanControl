#ifndef SENSORS_PWM_H
#define SENSORS_PWM_H


typedef enum pwm_sub_feature_type {
    // SENSORS_SUBFEATURE_TEMP_AUTO_POINT_PWM,
    // SENSORS_SUBFEATURE_TEMP_AUTO_POINT_TEMP,
    // SENSORS_SUBFEATURE_TEMP_AUTO_POINT_TEMP_HYST,
    //
    //
    SENSORS_SUBFEATURE_PWM_INPUT,
    SENSORS_SUBFEATURE_PWM_ENABLE,
    // SENSORS_SUBFEATURE_PWM_MODE,
    // SENSORS_SUBFEATURE_PWM_FREQ,
    // SENSORS_SUBFEATURE_PWM_AUTO_CHANNELS_TEMP,
    // SENSORS_SUBFEATURE_PWM_AUTO_POINT_PWM,
    // SENSORS_SUBFEATURE_PWM_AUTO_POINT_TEMP,
    // SENSORS_SUBFEATURE_PWM_AUTO_POINT_TEMP_HYST,
    //
    //
    // SENSORS_SUBFEATURE_PWM_CRIT_TEMP_TOLERANCE,
    // SENSORS_SUBFEATURE_PWM_FLOOR,
    // SENSORS_SUBFEATURE_PWM_STEP_UP_TIME,
    // SENSORS_SUBFEATURE_PWM_STOP_TIME,
    // SENSORS_SUBFEATURE_PWM_TARGET_TEMP,
    // SENSORS_SUBFEATURE_PWM_TEMP_SEL,
    // SENSORS_SUBFEATURE_PWM_TEMP_TOLERANCE,
    // SENSORS_SUBFEATURE_PWM_WEIGHT_DUTY_BASE,
    // SENSORS_SUBFEATURE_PWM_WEIGHT_DUTY_STEP,
    // SENSORS_SUBFEATURE_PWM_WEIGHT_TEMP_SEL,
    // SENSORS_SUBFEATURE_PWM_WEIGHT_TEMP_STEP,
    // SENSORS_SUBFEATURE_PWM_WEIGHT_TEMP_STEP_BASE,
    // SENSORS_SUBFEATURE_PWM_WEIGHT_TEMP_STEP_TOL

} pwm_sub_feature_type;


typedef struct pwm_feature {
	char *name;
	int number;

} pwm_feature;

typedef struct pwm_sub_feature {
	char *name;
	int number;
	pwm_sub_feature_type type;
} pwm_sub_feature;




/*
	return 0 on success, < 0 otherwise
*/
int pwm_init();


/*
	need to call pwm_init to reuse this lib after calling this function
*/
void pwm_clean_up();



/* 
	return NULL when nr is out of range 
*/
pwm_feature *pwm_get_features(int* nr);
pwm_sub_feature *pwm_get_sub_features(pwm_feature *feat, int* nr);


    
char *get_pwm_feature_name(pwm_feature *feat);
int get_pwm_feature_number(pwm_feature *feat);


char *get_pwm_sub_feature_name(pwm_sub_feature *feat);
int get_pwm_sub_feature_type(pwm_sub_feature *feat);
int get_pwm_sub_feature_number(pwm_sub_feature *feat);

/* 
	value need to be between 0 and 255
*/
int pwm_set_value(pwm_feature *feat, int value);


/* 
	Fan speed control method:
	0: no fan speed control (i.e. fan at full speed)
	1: manual fan speed control enabled (using pwm[1-*])
	2+: automatic fan speed control enabled
	Check individual chip documentation files for automatic mode
	details.
*/
int pwm_set_enable(pwm_feature *feat, int value);

#endif