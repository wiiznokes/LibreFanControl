#include <stdio.h>
#include <sensors/sensors.h>


void fetch_temp()
{
  int res = sensors_init(NULL);
  
  if (res != 0) {
    printf("err sensors_init: %d\n", res);
  }
      
  sensors_chip_name const * cn;
  int c = 0;
  while ((cn = sensors_get_detected_chips(0, &c)) != 0) {
    printf("Chip: %s %s\n",cn->prefix, cn->path);

    sensors_feature const *feat;
    int f = 0;

    while ((feat = sensors_get_features(cn, &f)) != 0) {
      printf("    %d:%s\n", f, feat->name);

      sensors_subfeature const *subf;
      int s = 0;

      while ((subf = sensors_get_all_subfeatures(cn, feat, &s)) != 0) {
        printf("        %d:%d:%s/%d = ", f, s, subf->name, subf->number);
        
        double val;
        if (subf->flags & SENSORS_MODE_R) {
          int rc = sensors_get_value(cn, subf->number, &val);
          if (rc < 0) {
            printf("err: %d\n", rc);
          } else {
            printf("%f\n", val);
          }
        }
      }
    }
    printf("\n");
  }
}


void fetch_temp2()
{
  int res = sensors_init(NULL);

  if (res != 0) {
    printf("err sensors_init: %d\n", res);
  }

  sensors_chip_name const * cn;
  int c = 0;
  while ((cn = sensors_get_detected_chips(0, &c)) != 0) {
    printf("Chip: %s %s\n",cn->prefix, cn->path);

    sensors_feature const *feat;
    int f = 0;

    while ((feat = sensors_get_features(cn, &f)) != 0) {
      printf("    %d:%s\n", f, feat->name);

      sensors_subfeature const *subf;
      int s = 0;
      
      if ((subf = sensors_get_all_subfeatures(cn, feat, &s)) != 0) {
        printf("        %d:%s = ", s, subf->name);

        double val;
        if (subf->flags & SENSORS_MODE_R) {
          int rc = sensors_get_value(cn, subf->number, &val);
          if (rc < 0) {
            printf("err: %d\n", rc);
          } else {
            printf("%f\n", val);
          }
        }
      }
    }
    printf("\n");
  }
}