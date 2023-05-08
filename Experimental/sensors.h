#ifndef SENSORS_H
#define SENSORS_H



void init();

void clean_up();


sensors_chip_name get_next_chip();
sensors_feature *get_next_feature();
sensors_subfeature *get_next_subfeature();

char *get_chip_name();

int get_feature_type();
int get_feature_number();
char *get_feature_name();

int get_subfeature_type();
int get_subfeature_number();
char *get_subfeature_name();
int get_subfeature_value();


#endif