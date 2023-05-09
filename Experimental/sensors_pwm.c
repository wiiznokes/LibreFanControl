#include "sensors_pwm.h"
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <dirent.h>
#include <limits.h>

const char *hwmon_path = "/sys/class/hwmon/hwmon6";





pwm_feature *feat_list;
pwm_sub_feature *sub_feat_list;

int feat_list_count;
int sub_feat_list_count;



void pwm_fatal_error(const char *fun_name, char *msg) {
    fprintf(stderr, "Fatal error in `%s': %s\n", fun_name, msg);
	exit(1);
}

void add_pwm_feature(pwm_feature *feat) {
    
    pwm_feature *new_list = (pwm_feature*) realloc(feat_list, (feat_list_count + 1) * sizeof(pwm_feature));
    if (!new_list) {
        pwm_fatal_error(__func__, "can't realloc");
    }

    feat_list = new_list;
   
    feat_list[feat_list_count] = *feat;
    feat_list_count++;
    
}

void add_pwm_sub_feature(pwm_sub_feature *sub_feat) {
    
    pwm_sub_feature *new_list = (pwm_sub_feature*) realloc(sub_feat_list, (sub_feat_list_count + 1) * sizeof(pwm_sub_feature));
    if (!new_list) {
        pwm_fatal_error(__func__, "can't realloc");
    }

    sub_feat_list = new_list;
   
    sub_feat_list[sub_feat_list_count] = *sub_feat;
    sub_feat_list_count++;
    
}




int pwm_init() {

    DIR *dir;
    struct dirent *ent;
    char file_path[PATH_MAX];

    dir = opendir(hwmon_path);
    if (dir == NULL) {
        perror("Could not open hwmon directory");
        return -1;
    }

    while ((ent = readdir(dir)) != NULL) {

        /* Skip directories and symlinks */
		if (ent->d_type != DT_REG)
			continue;

        sprintf(file_path, "%s/%s", hwmon_path, ent->d_name);

        // Do something with file_path and its contents
        // Example:
        printf("File name: %s\n", ent->d_name);

        FILE *fp = fopen(file_path, "r");
        if (fp != NULL) {
            char buffer[256];
            while (fgets(buffer, sizeof(buffer), fp) != NULL) {
                printf("Content: %s", buffer);
            }
            fclose(fp);
        } else {
            perror("Could not open file");
            return -1;
        }
        
    }

    closedir(dir);
    return 0;

}
