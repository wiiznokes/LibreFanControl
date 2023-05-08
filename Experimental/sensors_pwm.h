#ifndef SENSORS_PWM_H
#define SENSORS_PWM_H


struct sensors_pwm {
	char *name;
	int number;
	sensors_feature_type type;
	/* Members below are for libsensors internal use only */
	int first_subfeature;
	int padding1;
};



#endif